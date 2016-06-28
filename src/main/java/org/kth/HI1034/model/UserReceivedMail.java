package org.kth.HI1034.model;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "user_reciveid_mail")
public class UserReceivedMail implements Serializable {


	public UserReceivedMail() {
		pk = new UserReceivedMailPk();
	}

	public UserReceivedMail(FaceMail receivedMail, FaceUser receivingUser , FaceUser author) {
		this.pk = new UserReceivedMailPk(receivedMail, receivingUser, author);

	}



	private Boolean message_read = false;
	@NotNull
	@Column(name = "message_read",
			nullable = false,
			insertable = true,
			updatable = true)
	public Boolean getRead() {
		return message_read;
	}
	public void setRead(Boolean read) {
		this.message_read = read;
	}

	private Date receivedDate;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy.MM.dd hh.mm.ss.SSS")
//	@NotNull
	@CreatedDate
	@Column(name = "received_date",
			nullable = true,
			insertable = true,
			updatable = true)
	public Date getReceivedDate() {
		return receivedDate;
	}
	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}



	// ------------------------------------------------------------------------------------------





	public UserReceivedMailPk pk;
	@EmbeddedId
	public UserReceivedMailPk getPk() {
		return pk;
	}
	public void setPk(UserReceivedMailPk receivedMailID) {
		this.pk = receivedMailID;
	}


	@Transient
	public FaceMail getReceivedMail() {
		return getPk().getReceivedMail();
	}
	public void setReceivedMail(FaceMail receivedMails) {
		getPk().setReceivedMail(receivedMails);
	}

	@Transient
	public FaceUser getReceivingUser() {
		return getPk().getReceivingUser();
	}

	public void setReceivingUser(FaceUser receivingUser) {
		getPk().setReceivingUser(receivingUser);
	}

	@Transient
	public FaceUser getAuthor() {
		return getPk().getAuthor();
	}
	public void setAuthor(FaceUser author) {
		getPk().setAuthor(author);
	}

}
