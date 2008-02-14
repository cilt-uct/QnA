
package org.sakaiproject.qna.model;

import java.util.Date;


/**
 * This is a the options table entity
 * 
 * @author Psybergate
 */
public class QnaCustomEmail {

    private String id;
    
//  The options this custom email is linked to
    private QnaOptions options;

//	The user (sakai userid) that posted this question
	private String ownerId;
	
//	The email address to which notification should be sent
	private String email;
	
//	The date this answer was created
	private Date dateCreated;

	
	/**
	 * 
	 */
	public QnaCustomEmail() {
	}


	/**
	 * @param id
	 * @param options
	 * @param ownerId
	 * @param email
	 * @param dateCreated
	 */
	public QnaCustomEmail(String id, QnaOptions options, String ownerId,
			String email, Date dateCreated) {
		this.id = id;
		this.options = options;
		this.ownerId = ownerId;
		this.email = email;
		this.dateCreated = dateCreated;
	}


	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}


	/**
	 * @return the options
	 */
	public QnaOptions getOptions() {
		return options;
	}


	/**
	 * @param options the options to set
	 */
	public void setOptions(QnaOptions options) {
		this.options = options;
	}


	/**
	 * @return the ownerId
	 */
	public String getOwnerId() {
		return ownerId;
	}


	/**
	 * @param ownerId the ownerId to set
	 */
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}


	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}


	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}


	/**
	 * @return the dateCreated
	 */
	public Date getDateCreated() {
		return dateCreated;
	}


	/**
	 * @param dateCreated the dateCreated to set
	 */
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	
	
	
}