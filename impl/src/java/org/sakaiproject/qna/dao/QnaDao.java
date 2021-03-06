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
package org.sakaiproject.qna.dao;

import java.util.List;

import org.sakaiproject.genericdao.api.GeneralGenericDao;
import org.sakaiproject.qna.model.QnaQuestion;
import org.sakaiproject.qna.model.QnaAnswer;

/**
 * This is a specialized DAO that allows the developer to extend the functionality of the generic dao package
*/
public interface QnaDao extends GeneralGenericDao {
	
	/**
	 * Searches for new questions in location
	 * 
	 * @param locationId Id of location
	 * @return List of new Question
	 */
	public List<QnaQuestion> getNewQuestions(String locationId);

	/**
	 * Searches answers for search string
	 * 
	 * @param search Search string
	 * @param location location to search in
	 * @return list of answers containing search string
	 */
	public List<QnaAnswer> getSearchAnswers(String search, String location);

}
