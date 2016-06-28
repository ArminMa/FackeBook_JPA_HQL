package org.kth.HI1034.model;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "user_reciveid_mail")
public class UserReceivedMail implements Serializable, Comparable<UserReceivedMail> {


	private Long id;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", insertable=false, updatable=false, unique=true, nullable=false)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

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
	@Embedded
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


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		UserReceivedMail that = (UserReceivedMail) o;

		if (id != null ? !id.equals(that.id) : that.id != null)
			return false;
		if (message_read != null ? !message_read.equals(that.message_read) : that.message_read != null) return false;
		if (receivedDate != null ? !receivedDate.equals(that.receivedDate) : that.receivedDate != null) return false;
		return pk != null ? pk.equals(that.pk) : that.pk == null;

	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (message_read != null ? message_read.hashCode() : 0);
		result = 31 * result + (receivedDate != null ? receivedDate.hashCode() : 0);
		return result;
	}


	@Override
	public int compareTo(UserReceivedMail o) {
		int thisTime = this.hashCode();
		long anotherEntity = o.hashCode();
		return (thisTime<anotherEntity ? -1 : (thisTime==anotherEntity ? 0 : 1));
	}
}
