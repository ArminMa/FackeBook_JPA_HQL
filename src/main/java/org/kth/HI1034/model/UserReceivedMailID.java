package org.kth.HI1034.model;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Embeddable
public class UserReceivedMailID implements java.io.Serializable {

	public FaceMail receivedMail;
	public FaceUser receivingUser;

	public UserReceivedMailID() {
	}

	public UserReceivedMailID(FaceMail receivedMail, FaceUser receivingUser) {
		this.receivedMail = receivedMail;
		this.receivingUser = receivingUser;
	}

	@ManyToOne(fetch= FetchType.LAZY , cascade = CascadeType.DETACH)
	public FaceMail getReceivedMail() {
		return receivedMail;
	}
	public void setReceivedMail(FaceMail receivedMails) {
		this.receivedMail = receivedMails;
	}



	@ManyToOne(fetch=FetchType.LAZY  , cascade = CascadeType.DETACH)
	public FaceUser getReceivingUser() {
		return receivingUser;
	}
	public void setReceivingUser(FaceUser receivingUser) {
		this.receivingUser = receivingUser;
	}
}
