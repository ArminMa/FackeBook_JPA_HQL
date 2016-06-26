package org.kth.HI1034.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.MoreObjects;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "face_mail")
public class FaceMail implements Serializable {

	public static final int MAX_LENGTH_HEADER = 254;
	public static final int MAX_LENGTH_MAIL = 1500;

	public FaceMail() {
	}

	public FaceMail(String header, String text) {
		this.header = header;
		this.mailText = text;
	}

	public FaceMail(String header, String mailText, Date sentDate) {
		this.header = header;
		this.mailText = mailText;
		this.sentDate = sentDate;
	}

	/**
	 *
	 * @param header            1   String
	 * @param mailText          2   String
	 * @param sentDate          3   Java.Util.Date
	 * @param author            4   FaceUser
	 */
	public FaceMail(String header, String mailText, Date sentDate, FaceUser author) {
		this.header = header;
		this.mailText = mailText;
		this.sentDate = sentDate;
		this.author = author;

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
	@Length(max=MAX_LENGTH_HEADER)
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
	@Past
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

	private FaceUser author;
	@ManyToOne(fetch= FetchType.EAGER)
	@JoinTable(name = "mail_user_sent",
			joinColumns = @JoinColumn(name = "mail_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id") ,
			indexes =
					{
							@Index(name = "user_id_idx", columnList = "user_id") ,
							@Index(name = "mail_id_idx", columnList = "user_friend_id")
					}
	)
	public FaceUser getAuthor() {
		return author;
	}
	public void setAuthor(FaceUser author) {
		this.author = author;
	}

	private List<UserReceivedMail> receiversFaceMails = new ArrayList<UserReceivedMail>();
	@OneToMany( mappedBy = "pk.receivedMail", fetch = FetchType.LAZY, orphanRemoval=true)
	public List<UserReceivedMail> getReceiversFaceMails() {
		return receiversFaceMails;
	}
	public void setReceiversFaceMails(List<UserReceivedMail> myReceivedFaceMails) {
		this.receiversFaceMails = myReceivedFaceMails;
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
