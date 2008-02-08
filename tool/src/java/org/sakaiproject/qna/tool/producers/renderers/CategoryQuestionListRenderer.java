package org.sakaiproject.qna.tool.producers.renderers;

import org.sakaiproject.qna.tool.producers.AnswersProducer;
import org.sakaiproject.qna.tool.producers.QuestionsListProducer;
import org.sakaiproject.qna.tool.producers.QueuedQuestionProducer;
import org.sakaiproject.qna.tool.producers.ViewPrivateReplyProducer;

import uk.org.ponder.rsf.components.UIBoundBoolean;
import uk.org.ponder.rsf.components.UIBranchContainer;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIInitBlock;
import uk.org.ponder.rsf.components.UIInternalLink;
import uk.org.ponder.rsf.components.UIJointContainer;
import uk.org.ponder.rsf.components.UILink;
import uk.org.ponder.rsf.components.UIMessage;
import uk.org.ponder.rsf.components.UIOutput;
import uk.org.ponder.rsf.viewstate.SimpleViewParameters;

public class CategoryQuestionListRenderer implements QuestionListRenderer {

    public void makeQuestionList(UIContainer tofill, String divID) {
    	// Front-end customization regarding permissions/options will come here
    	UIJointContainer listTable = new UIJointContainer(tofill,divID,"question-list-table:");
		UIMessage.make(listTable, "categories-title", "qna.view-questions.categories");
		UIMessage.make(listTable, "answers-title", "qna.view-questions.answers");
		
		UIInternalLink.make(listTable,"views-link",new SimpleViewParameters(QuestionsListProducer.VIEW_ID));
		UILink.make(listTable,"views-icon","/library/image/sakai/sortascending.gif");	
		UIMessage.make(listTable, "views-msg", "qna.view-questions.views");
		
		UIInternalLink.make(listTable, "modified-link", new SimpleViewParameters(QuestionsListProducer.VIEW_ID));
		UILink.make(listTable, "modified-icon", "/library/image/sakai/sortascending.gif");
		UIMessage.make(listTable, "modified-msg", "qna.view-questions.modified");
		
		UIMessage.make(listTable, "remove-title", "qna.view-questions.remove");
		
		// TODO: Get from database, use proper objects, etc. etc. 
		String[][] categoryValues = {{"Assignments","2007-08-22"},
									{"Exams","2007-12-11"},
									{"New Questions","2008-02-05"},
									{"Private Replies","2008-01-13"}};
		
		// This is ONLY for the mock ups, do it properly for real data
		String[][] questionValues = {{"Assignments","How many assignments must be done?","3","88","2007-08-22"},
									 {"Assignments","Is this another question under assignments?","1","37","2007-01-12"},
									 {"Exams","Is this an exam question?","2","99","2007-06-15"},
									 {"Exams","How is the year mark composed?","6","175","2007-03-11"},
									 {"Exams","What if I can't write the exam?","3","111","2007-12-11"},
									 {"New Questions","Are you sure?","0","0","2008-01-15"},
									 {"New Questions","Is RSF a good technology?","1","3","2008-02-01"},
									 {"Private Replies","Is it worth studying?","5","33","2008-01-05"},
									 {"Private Replies","Which clown college is best?","1","2","2008-01-13"},
									};
		
		for (int i=0;i<categoryValues.length;i++) {
			UIBranchContainer entry = UIBranchContainer.make(listTable, "table-entry:");		
			UIBranchContainer category = UIBranchContainer.make(entry,"category-entry:",Integer.toString(i));
			
			UILink icon = UILink.make(category, "toggle-questions-icon", "/library/image/sakai/expand.gif");
			UIInitBlock.make(category,"onclick-init","init_questions_toggle", new Object[]{icon,entry});
			
			UIOutput.make(category,"category-name",categoryValues[i][0]);
			UIOutput.make(category,"modified-date",categoryValues[i][1]);
			UIBoundBoolean.make(category, "remove-checkbox",false);
			
			for (int j=0;j<questionValues.length;j++) {
				if (questionValues[j][0].equals(categoryValues[i][0])) {
					UIBranchContainer question = UIBranchContainer.make(entry, "question-entry:",Integer.toString(j));
					if (categoryValues[i][0].equals("New Questions")) {
						UIInternalLink.make(question,"question-link",questionValues[j][1],new SimpleViewParameters(QueuedQuestionProducer.VIEW_ID));
					}
					else if (categoryValues[i][0].equals("Private Replies")) {
						UIInternalLink.make(question, "question-link", questionValues[j][1],new SimpleViewParameters(ViewPrivateReplyProducer.VIEW_ID));					
					}
					else {
						UIInternalLink.make(question,"question-link",questionValues[j][1],new SimpleViewParameters(AnswersProducer.VIEW_ID));
					}
						
					UIOutput.make(question,"answers-nr",questionValues[j][2]);
					UIOutput.make(question,"views-nr",questionValues[j][3]);
					UIOutput.make(question,"question-modified-date",questionValues[j][4]);
					UIBoundBoolean.make(question, "remove-checkbox",false);
				}
			}
		}
		
		
    }
}