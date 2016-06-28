package org.kth.HI1034.model;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Embeddable
public class UserReceivedMailPk implements java.io.Serializable {





	public UserReceivedMailPk() {
	}


	/**
	 *

	 * @param receivingUser     2
	 * @param author            3
	 */
	public UserReceivedMailPk(FaceMail receivedMail, FaceUser receivingUser, FaceUser author) {
		this.receivedMail = receivedMail;
		this.receivingUser = receivingUser;
		this.author = author;
	}

	private FaceMail receivedMail;
	@ManyToOne(fetch= FetchType.LAZY )
	public FaceMail getReceivedMail() {
		return receivedMail;
	}
	public void setReceivedMail(FaceMail receivedMails) {
		this.receivedMail = receivedMails;
	}


	private FaceUser receivingUser;
	@ManyToOne(fetch = FetchType.LAZY)
	public FaceUser getReceivingUser() {
		return receivingUser;
	}
	public void setReceivingUser(FaceUser receivingUser) {
		this.receivingUser = receivingUser;
	}

	private FaceUser author;
	@ManyToOne(fetch = FetchType.LAZY)
	public FaceUser getAuthor() {
		return author;
	}
	public void setAuthor(FaceUser author) {
		this.author = author;
	}

}
