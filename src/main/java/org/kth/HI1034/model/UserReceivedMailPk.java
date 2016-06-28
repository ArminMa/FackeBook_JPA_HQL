package org.kth.HI1034.model;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Embeddable
public class UserReceivedMailPk implements java.io.Serializable, Comparable<UserReceivedMailPk> {





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
	@ManyToOne(fetch= FetchType.EAGER )
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


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		UserReceivedMailPk that = (UserReceivedMailPk) o;

		if (receivedMail != null ? !receivedMail.equals(that.receivedMail) : that.receivedMail != null) return false;
		if (receivingUser != null ? !receivingUser.equals(that.receivingUser) : that.receivingUser != null)
			return false;
		return author != null ? author.equals(that.author) : that.author == null;

	}

	@Override
	public int hashCode() {
		int result = receivedMail != null ? receivedMail.hashCode() : 0;
		result = 31 * result + (receivingUser != null ? receivingUser.hashCode() : 0);
		result = 31 * result + (author != null ? author.hashCode() : 0);
		return result;
	}



	@Override
	public int compareTo(UserReceivedMailPk o) {
		int thisTime = this.hashCode();
		long anotherTime = o.hashCode();
		return (thisTime<anotherTime ? -1 : (thisTime==anotherTime ? 0 : 1));
	}
}
