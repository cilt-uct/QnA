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
package org.sakaiproject.qna.logic.test.stubs;

import java.util.Locale;

import org.sakaiproject.qna.logic.QnaBundleLogic;

public class QnaBundleLogicStub implements QnaBundleLogic {

	private Object[] lastParameters;
	
	public String getFormattedMessage(String key, Object[] parameters) {
		lastParameters = parameters;
		return key;
	}
	
	public Object[] getLastParameters() {
		return lastParameters;
	}

	public Locale getLocale() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getString(String key) {
		if ("qna.default-category.text".equals(key)) {
			return "general";
		}
		return key;
	}

}
