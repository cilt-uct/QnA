/**
 * Copyright (c) 2007-2009 The Sakai Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *             http://www.opensource.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sakaiproject.qna.logic.impl;

import java.util.List;

import org.sakaiproject.content.api.ContentHostingService;
import org.sakaiproject.entity.api.Reference;
import org.sakaiproject.qna.logic.AttachmentLogic;
import org.sakaiproject.qna.logic.exceptions.AttachmentException;
import org.sakaiproject.qna.model.QnaAttachment;
import org.sakaiproject.qna.model.QnaQuestion;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AttachmentLogicImpl implements AttachmentLogic {


	@Setter private ContentHostingService contentHostingService;

	
	/**
	 * @see AttachmentLogic#deleteAttachment(String)
	 */
	public void deleteAttachment(String attachmentId) throws AttachmentException {
		try {
			if (attachmentId.toLowerCase().startsWith("/attachment"))
		          contentHostingService.removeResource(attachmentId);
		} catch (Exception e) {
			log.error("Error deleting attachment: " + attachmentId , e);
			throw new AttachmentException(e);
		}
		
	}	
	
	/**
	 * @see AttachmentLogic#synchAttachmentList(QnaQuestion, List)
	 */
	public void synchAttachmentList(QnaQuestion question, List<Reference> attachments) {
		if (question != null) {
			question.getAttachments().clear(); // clear current attachments
			
			// Add all new attachments
			for (Reference ref : attachments) {
				QnaAttachment attachment = new QnaAttachment();
				attachment.setAttachmentId(ref.getId());
				question.addAttachment(attachment);
			}
		}
	}

}
