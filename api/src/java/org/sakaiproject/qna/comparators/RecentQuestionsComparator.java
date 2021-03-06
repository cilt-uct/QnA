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
package org.sakaiproject.qna.comparators;

import java.util.Comparator;

import org.sakaiproject.qna.model.QnaQuestion;

/**
 *	Comparator to sort list of QnaQuestion by date created (descending)
 */
public class RecentQuestionsComparator implements Comparator<QnaQuestion> {

	/**
	 * @see Comparator#compare(Object, Object)	
	 */
	public int compare(QnaQuestion q1, QnaQuestion q2) {
		return q1.getDateCreated().compareTo(q2.getDateCreated());
	}

}
