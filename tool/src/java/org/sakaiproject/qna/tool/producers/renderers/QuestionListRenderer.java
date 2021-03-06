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
package org.sakaiproject.qna.tool.producers.renderers;

import org.sakaiproject.qna.tool.params.SortPagerViewParams;

import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIForm;

public interface QuestionListRenderer {
	
	/**
	 * Creates question list
	 * 
	 * @param tofill		{@link UIContainer} to fill
	 * @param divID			id of div
	 * @param sortParams	View parameters
	 * @param form			{@link UIForm} to be added to
	 */
	public void makeQuestionList(UIContainer tofill, String divID, SortPagerViewParams sortParams, UIForm form);
}
