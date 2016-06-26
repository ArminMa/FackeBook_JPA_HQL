package org.kth.HI1034.model;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "user_reciveid_mail", indexes =
				{
					@Index(name = "user_id_idx", columnList = "user_id") ,
					@Index(name = "mail_id_idx", columnList = "mail_id")
				}
		)
@AssociationOverrides({
		@AssociationOverride(name = "pk.receivingUser",
				joinColumns = @JoinColumn(name = "user_id")),
		@AssociationOverride(name = "pk.receivedMail",
				joinColumns = @JoinColumn(name = "mail_id")) })

public class UserReceivedMail implements Serializable {


	public UserReceivedMail() {
		pk = new UserReceivedMailID();
	}


	public UserReceivedMail(FaceMail receivedMail, FaceUser receivingUser ) {
		pk = new UserReceivedMailID(receivedMail, receivingUser);
	}

	public UserReceivedMailID pk;
	@EmbeddedId
	public UserReceivedMailID getPk() {
		return pk;
	}
	public void setPk(UserReceivedMailID receivedMailID) {
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
	@Past
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


}
