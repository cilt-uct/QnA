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
package org.sakaiproject.qna.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class QNAUtils {

	private static Log log = LogFactory.getLog(QNAUtils.class);
	
	

	   public static final String ENDING_P_SPACE_TAGS = "<p>&nbsp;</p>";
	   public static final String STARTING_P_TAG = "<p>";
	   public static final String ENDING_P_TAG = "</p>";

	   /**
	    * Attempts to remove all unnecessary P tags from html strings
	    * 
	    * @param cleanup an html string to cleanup
	    * @return the cleaned up string
	    */
	   public static String cleanupHtmlPtags(String cleanup) {
		      if (cleanup == null) {
		         // nulls are ok
		         return null;
		      } else if (cleanup.trim().length() == 0) {
		         // nothing to do
		         return cleanup;
		      }
		      cleanup = cleanup.trim();

		      if (cleanup.length() > ENDING_P_SPACE_TAGS.length()) {
		         // (remove trailing blank lines)
		         // - While (cleanup ends with "<p>&nbsp;</p>") remove trailing "<p>&nbsp;</p>".
		         while (cleanup.toLowerCase().endsWith(ENDING_P_SPACE_TAGS)) {
		            // chop off the end
		            cleanup = cleanup.substring(0, cleanup.length() - ENDING_P_SPACE_TAGS.length()).trim();
		         }
		      }

		      if (cleanup.length() > (STARTING_P_TAG.length() + ENDING_P_TAG.length())) {
		         // (remove a single set of <p> tags)
		         // if cleanup starts with "<p>" and cleanup ends with "</p>" and, remove leading "<p>" and trailing "</p>" from cleanup
		         String lcCheck = cleanup.toLowerCase();
		         if (lcCheck.startsWith(STARTING_P_TAG) 
		               && lcCheck.endsWith(ENDING_P_TAG)) {
		            if (lcCheck.indexOf(STARTING_P_TAG, STARTING_P_TAG.length()) == -1 
		                  && lcCheck.lastIndexOf(ENDING_P_TAG, lcCheck.length() - ENDING_P_TAG.length() - 1) == -1) {
		               // chop off the front and end P tags
		               cleanup = cleanup.substring(STARTING_P_TAG.length(), cleanup.length() - ENDING_P_TAG.length()).trim();
		            }
		         }
		      }
		      //remove the bad <br type="_moz" />
		      cleanup = cleanup.replace("<br type=\"_moz\" />", "");
		      return cleanup;
		   }
	   
	   /**
	    * Check if String is valid e-mail address
	    * 
	    * @param email {@link String} to check
	    * @return true if valid, false otherwise
	    */
		public static boolean isValidEmail(String email) {
			// TODO: Use a generic Sakai utility class (when a suitable one exists)
			
			if (email == null || "".equals(email))
				return false;
			
			email = email.trim();
			//must contain @
			if (email.indexOf("@") == -1)
				return false;
			
			//an email can't contain spaces
			if (email.indexOf(" ") > 0)
				return false;
			
			//"^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*$" 
			if (email.matches("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*$")) 
				return true;
		
			log.warn(email + " is not a valid eamil address");
			return false;
		}
	   
}
