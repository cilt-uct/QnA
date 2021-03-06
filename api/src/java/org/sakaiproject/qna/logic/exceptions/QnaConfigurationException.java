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
package org.sakaiproject.qna.logic.exceptions;

/**
 * RuntimeException specific to QNA configurations
 */
public class QnaConfigurationException extends RuntimeException {
	
	private static final long serialVersionUID = -4134063107758895700L;

	/**
	 * Constructor
	 * @param throwable Throwable caught that caused exception
	 */
	public QnaConfigurationException(String message) {
		super(message);
	}
}
