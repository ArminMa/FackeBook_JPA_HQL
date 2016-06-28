package org.kth.HI1034.model;

import com.google.common.base.MoreObjects;
import org.hibernate.annotations.BatchSize;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "face_mail")
public class FaceMail implements Serializable {

	private static final int MAX_LENGTH_HEADER = 254;
	private static final int MAX_LENGTH_MAIL = 5000;

	public FaceMail() {
	}

	public FaceMail(String header, String text) {
		this.header = header;
		this.mailText = text;
	}


	/**
	 *
	 * @param header            1   String
	 * @param mailText          2   String
	 * @param sentDate          3   Java.Util.Date
	 */
	public FaceMail(String header, String mailText, Date sentDate) {
		this.header = header;
		this.mailText = mailText;
		this.sentDate = sentDate;

	}

	private Long id;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	private String header;
	@Length(max=MAX_LENGTH_HEADER)
	@Column(name = "header")
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}

	private String mailText;
	@Length(max=MAX_LENGTH_MAIL)
	@Column(name = "mailText")
	public String getMailText() {
		return mailText;
	}
	public void setMailText(String mailText) {
		this.mailText = mailText;
	}


	private Date sentDate;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy.MM.dd hh.mm.ss.SSS")
//	@NotNull
	@CreatedDate
	@Column(name = "sent_date",
			nullable = true,
			insertable = true,
			updatable = true)
	public Date getSentDate() {
		return sentDate;
	}
	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}

	private List<UserReceivedMail> receiversFaceMail = new ArrayList<>();
	@OneToMany(cascade= CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy="pk.receivedMail")
	@BatchSize(size=25)
	public List<UserReceivedMail> getReceiversFaceMail() {
		return receiversFaceMail;
	}
	public void setReceiversFaceMail(List<UserReceivedMail> receiversFaceMail) {
		this.receiversFaceMail = receiversFaceMail;
	}


	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("mailID", id)
				.add("header", header)
				.add("mailText", mailText)
				.toString();
	}
}
