package org.sakaiproject.qna.logic.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.genericdao.api.finders.ByPropsFinder;
import org.sakaiproject.qna.dao.QnaDao;
import org.sakaiproject.qna.logic.AttachmentLogic;
import org.sakaiproject.qna.logic.CategoryLogic;
import org.sakaiproject.qna.logic.ExternalEventLogic;
import org.sakaiproject.qna.logic.ExternalLogic;
import org.sakaiproject.qna.logic.NotificationLogic;
import org.sakaiproject.qna.logic.OptionsLogic;
import org.sakaiproject.qna.logic.PermissionLogic;
import org.sakaiproject.qna.logic.QuestionLogic;
import org.sakaiproject.qna.logic.exceptions.AttachmentException;
import org.sakaiproject.qna.logic.exceptions.QnaConfigurationException;
import org.sakaiproject.qna.logic.utils.ComparatorsUtils;
import org.sakaiproject.qna.model.QnaCategory;
import org.sakaiproject.qna.model.QnaOptions;
import org.sakaiproject.qna.model.QnaQuestion;

public class QuestionLogicImpl implements QuestionLogic {

	private static Log log = LogFactory.getLog(QuestionLogicImpl.class);
	
	private PermissionLogic permissionLogic;
	private OptionsLogic optionsLogic;
	private ExternalLogic externalLogic;
	private CategoryLogic categoryLogic;
	private AttachmentLogic attachmentLogic;
	private NotificationLogic notificationLogic;
	private ExternalEventLogic externalEventLogic;
	private QnaDao dao;

	public void setPermissionLogic(PermissionLogic permissionLogic) {
		this.permissionLogic = permissionLogic;
	}

	public void setOptionsLogic(OptionsLogic optionsLogic) {
		this.optionsLogic = optionsLogic;
	}

	public void setExternalLogic(ExternalLogic externalLogic) {
		this.externalLogic = externalLogic;
	}

	public void setCategoryLogic(CategoryLogic categoryLogic) {
		this.categoryLogic = categoryLogic;
	}
	
	public void setAttachmentLogic(AttachmentLogic attachmentLogic) {
		this.attachmentLogic = attachmentLogic;
	}
	
	public void setNotificationLogic(NotificationLogic notificationLogic) {
		this.notificationLogic = notificationLogic;
	}

	public void setExternalEventLogic(ExternalEventLogic externalEventLogic) {
		this.externalEventLogic = externalEventLogic;
	}
		
	public void setDao(QnaDao dao) {
		this.dao = dao;
	}

	@SuppressWarnings("unchecked")
	public List<QnaQuestion> getNewQuestions(String locationId) {
		return dao.getNewQuestions(locationId);
	}

	@SuppressWarnings("unchecked")
	public List<QnaQuestion> getPublishedQuestions(String locationId) {
		List<QnaQuestion> l = dao.findByProperties(QnaQuestion.class,
				new String[] { "location", "published" }, new Object[] {
						locationId, true }, new int[] { ByPropsFinder.EQUALS,
						ByPropsFinder.EQUALS });
		return l;
	}
	
	@SuppressWarnings("unchecked")
	public List<QnaQuestion> getAllQuestions(String locationId) {
		List<QnaQuestion> l = dao.findByProperties(QnaQuestion.class,
				new String[] { "location" }, new Object[] {
						locationId}, new int[] { ByPropsFinder.EQUALS});
		return l;
	}

	public QnaQuestion getQuestionById(String questionId) {
		return (QnaQuestion) dao.findById(QnaQuestion.class, questionId);
	}

	public List<QnaQuestion> getQuestionsWithPrivateReplies(String locationId) {
		List<QnaQuestion> questions = getAllQuestions(locationId);
		List<QnaQuestion> questionsWithPrivateReplies = new ArrayList<QnaQuestion>();
		
		for (QnaQuestion qnaQuestion : questions) {
			if (qnaQuestion.hasPrivateReplies()) {
				questionsWithPrivateReplies.add(qnaQuestion);
			}
		}
		return questionsWithPrivateReplies;
	}

	public void incrementView(String questionId) {
		QnaQuestion question = getQuestionById(questionId);
		question.setViews(question.getViews() + 1);
		dao.save(question);
	}

	public void publishQuestion(String questionId, String locationId) {
		String userId = externalLogic.getCurrentUserId();
		if (permissionLogic.canUpdate(locationId, userId)) {
			QnaQuestion question = getQuestionById(questionId);
			question.setPublished(true);
			saveQuestion(question, locationId);
		} else {
			throw new SecurityException(
					"Current user cannot save question for " + locationId
							+ " because they do not have permission");
		}
	}

	public boolean existsQuestion(String questionId) {
		if (questionId == null || questionId.equals("")) {
			return false;
		} else {
			if (getQuestionById(questionId) != null) {
				return true;
			} else {
				return false;
			}
		}
	}

	public void removeQuestion(String questionId, String locationId) throws AttachmentException {
		QnaQuestion question = getQuestionById(questionId);
		String userId = externalLogic.getCurrentUserId();
		if (permissionLogic.canUpdate(locationId, userId)) {
			try {
				// delete attachments
				if (question.getContentCollection() != null) {
					attachmentLogic.deleteCollection(question.getContentCollection());
				}
			} finally {
				dao.delete(question);
				externalEventLogic.postEvent(ExternalEventLogic.EVENT_QUESTION_DELETE, question.getId());
				log.info("Question deleted: " + question.getId());
			}
		
		} else {
			throw new SecurityException(
					"Current user cannot remove question for " + locationId
							+ " because they do not have permission");
		}
	}

	public void saveQuestion(QnaQuestion question, String locationId) {
		String userId = externalLogic.getCurrentUserId();
		if (existsQuestion(question.getId())) {

			if (permissionLogic.canUpdate(locationId, userId)) {
				if (question.isAnonymous()) {
					if (!optionsLogic.getOptions(locationId)
							.getAnonymousAllowed()) {
						throw new QnaConfigurationException("Location: "
								+ locationId
								+ " does not allow anonymous questions");
					}
				}
				question.setDateLastModified(new Date());
				question.setLastModifierId(userId);
				dao.save(question);
				externalEventLogic.postEvent(ExternalEventLogic.EVENT_QUESTION_UPDATE, question.getId());
			} else {
				throw new SecurityException(
						"Current user cannot save question for "
								+ question.getLocation()
								+ " because they do not have permission");
			}
		} else {
			if (permissionLogic.canAddNewQuestion(locationId, userId)) {
				QnaOptions options = optionsLogic.getOptions(locationId);

				if (question.isAnonymous() == null) {
					question.setAnonymous(options.getAnonymousAllowed()); // default for location
				} else {
					if (question.isAnonymous()) {
						if (!options.getAnonymousAllowed()) {
							throw new QnaConfigurationException("Location: "
									+ locationId
									+ " does not allow anonymous questions");
						}
					}
				}

				Date now = new Date();
				question.setDateCreated(now);
				question.setDateLastModified(now);

				if (options.isModerated()) {
					question.setPublished(false);
				} else {
					question.setPublished(true);
				}

				question.setLocation(locationId);
				question.setOwnerId(userId);
				question.setLastModifierId(userId);
				question.setViews(0);
				question.setSortOrder(0);
				
				if (question.getNotify() == null) {
					question.setNotify(true);
				}

				dao.save(question);
				externalEventLogic.postEvent(ExternalEventLogic.EVENT_QUESTION_CREATE, question.getId());
				
				// Notification
				if (options.getEmailNotification()) {
					String[] emails = (String[])optionsLogic.getNotificationSet(locationId).toArray(new String[]{});
					notificationLogic.sendNewQuestionNotification(emails, question.getQuestionText());
				}
			} else {
				throw new SecurityException(
						"Current user cannot save new question for "
								+ question.getLocation()
								+ " because they do not have permission");
			}

		}

	}

	public void addQuestionToCategory(String questionId, String categoryId,
			String locationId) {
		
		String userId = externalLogic.getCurrentUserId();
		QnaCategory category = categoryLogic.getCategoryById(categoryId);
		QnaQuestion question = getQuestionById(questionId);

		if (category == null) {
			throw new IllegalArgumentException("Category (" + categoryId
					+ ") does not exist");
		}

		if (question == null) {
			throw new IllegalArgumentException("Question (" + questionId
					+ ") does not exist");
		}

		if (!category.getLocation().equals(question.getLocation())) {
			throw new QnaConfigurationException(
					"The locations of the category (" + category.getLocation() + ") and the question (" + question.getLocation() + ") are not equal");
		} else if (!category.getLocation().equals(locationId)) {
			throw new QnaConfigurationException("Location supplied ("+locationId+") does not match location of category and question ("+question.getLocation()+")");
		}

		category.addQuestion(question);
		categoryLogic.saveCategory(category, locationId);
	}

	public void linkCollectionToQuestion(String questionId, String collectionId) {
		QnaQuestion question = getQuestionById(questionId);
		
		if (question != null) {
			question.setContentCollection(collectionId);
			saveQuestion(question, externalLogic.getCurrentLocationId());
		}
	}
	
	@SuppressWarnings("unchecked")
	public void filterPopulateAndSortQuestionList(List<QnaQuestion> questionList, int currentStart, int currentCount, String sortBy, boolean sortDir) {
		questionList = filterListForPaging(questionList, currentStart, currentCount);
        sortQuestions(questionList, sortBy, sortDir);
	}
	
	public List filterListForPaging(List myList, int begIndex, int numItemsToDisplay) {
        if (myList == null || myList.isEmpty())
        	return myList;
        
        int endIndex = begIndex + numItemsToDisplay;
        if (endIndex > myList.size()) {
        	endIndex = myList.size();
        }

		return myList.subList(begIndex, endIndex);
	}
	
	
	public void sortQuestions(List<QnaQuestion> questionList, String sortBy, boolean ascending) {
		Comparator<QnaQuestion> comp;
		if(QuestionLogic.SORT_BY_QUESTION_TEXT.equals(sortBy)) {
			comp = new ComparatorsUtils.QuestionQuestionTextComparator();
		} else if(QuestionLogic.SORT_BY_VIEWS.equals(sortBy)) {
			comp = new ComparatorsUtils.QuestionViewsComparator();
		} else if(QuestionLogic.SORT_BY_ANSWERS.equals(sortBy)) {
			comp = new ComparatorsUtils.QuestionAnswersComparator();
		} else if(QuestionLogic.SORT_BY_CREATED_DATE.equals(sortBy)){
			comp = new ComparatorsUtils.QuestionCreatedDateComparator();
		} else if(QuestionLogic.SORT_BY_MODIFIED_DATE.equals(sortBy)){
			comp = new ComparatorsUtils.QuestionModifiedDateComparator();
		} else if(QuestionLogic.SORT_BY_CATEGORY.equals(sortBy)) {
			comp = new ComparatorsUtils.QuestionCategoryComparator();
		}else
			comp = new ComparatorsUtils.QuestionViewsComparator();

		Collections.sort(questionList, comp);
		if(!ascending) {
			Collections.reverse(questionList);
		}
	}

}
