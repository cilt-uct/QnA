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


import org.sakaiproject.qna.logic.ExternalLogic;
import org.sakaiproject.qna.logic.PermissionLogic;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PermissionLogicImpl implements PermissionLogic {

	
	@Setter private ExternalLogic externalLogic;

	
	/**
	 * @see PermissionLogic#canUpdate(String, String)
	 */
	public boolean canUpdate(String locationId, String userId) {
		if (externalLogic.isUserAdmin(userId)) {
			return true; // Administrators can update
		} else if (externalLogic.isUserAllowedInLocation(userId, ExternalLogic.QNA_UPDATE, locationId)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @see PermissionLogic#canAddNewQuestion(String, String)
	 */
	public boolean canAddNewQuestion(String locationId, String userId) {
		if (externalLogic.isUserAdmin(userId)) {
			return true; 
		} else if (externalLogic.isUserAllowedInLocation(userId, ExternalLogic.QNA_NEW_QUESTION, locationId)) {
			return true;
		} else {
			return false;
		}		
	}

	/**
	 * @see PermissionLogic#canAddNewCategory(String, String)
	 */
	public boolean canAddNewCategory(String locationId, String userId) {
		if (externalLogic.isUserAdmin(userId)) {
			return true; 
		} else if (externalLogic.isUserAllowedInLocation(userId, ExternalLogic.QNA_NEW_CATEGORY, locationId)) {
			return true;
		} else {
			return false;
		}	
	}

	/**
	 * @see PermissionLogic#canAddNewAnswer(String, String)
	 */
	public boolean canAddNewAnswer(String locationId, String userId) {
		if (externalLogic.isUserAdmin(userId)) {
			return true;
		} else if (externalLogic.isUserAllowedInLocation(userId, ExternalLogic.QNA_NEW_ANSWER, locationId)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean canRead(String locationId, String userId) {
		log.debug("canRead: " + locationId + " user: " + userId);
		if (externalLogic.isUserAdmin(userId)) {
			log.debug("user is super user!");
			return true;
		} else if (externalLogic.isUserAllowedInLocation(userId, ExternalLogic.QNA_READ, locationId)) {
			return true;
		}
		return false;
	}

}
