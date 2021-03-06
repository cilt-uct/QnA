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
 * Comparator to sort questions by category text
 */
public class QuestionsByCategoryTextComparator implements
		Comparator<QnaQuestion> {

	/**
	 * @see Comparator#compare(Object, Object)	
	 */
	public int compare(QnaQuestion o1, QnaQuestion o2) {
		if (o1.getCategory() == null && o2.getCategory() == null) {
			return 0;
		} else if (o2.getCategory() == null) {
			return 1;
		} else if (o1.getCategory() == null) {
			return -1;
		} else {
			return o1.getCategory().getCategoryText().compareToIgnoreCase(o2.getCategory().getCategoryText());
		}
	}

}
