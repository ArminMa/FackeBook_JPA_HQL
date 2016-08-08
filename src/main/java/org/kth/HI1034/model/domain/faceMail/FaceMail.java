package org.kth.HI1034.model.domain.faceMail;

import com.google.common.base.MoreObjects;
import org.hibernate.annotations.SortNatural;
import org.hibernate.validator.constraints.Length;
import org.kth.HI1034.model.domain.faceMail.userReceivedMail.UserReceivedMail;
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
import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;

@Entity
@Table(name = "face_mail")
public class FaceMail implements Serializable ,Comparable<FaceMail> {

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

	private SortedSet<UserReceivedMail> receiversFaceMail = new TreeSet<>();
	@OneToMany(cascade= CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy="pk.receivedMail")
	@SortNatural
	public SortedSet<UserReceivedMail> getReceiversFaceMail() {
		return receiversFaceMail;
	}
	public void setReceiversFaceMail(SortedSet<UserReceivedMail> receiversFaceMail) {
		this.receiversFaceMail = receiversFaceMail;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		FaceMail faceMail = (FaceMail) o;

		if (id != null ? !id.equals(faceMail.id) : faceMail.id != null) return false;
		if (header != null ? !header.equals(faceMail.header) : faceMail.header != null) return false;
		if (mailText != null ? !mailText.equals(faceMail.mailText) : faceMail.mailText != null) return false;
		return sentDate != null ? sentDate.equals(faceMail.sentDate) : faceMail.sentDate == null;

	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (header != null ? header.hashCode() : 0);
		result = 31 * result + (mailText != null ? mailText.hashCode() : 0);
		result = 31 * result + (sentDate != null ? sentDate.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("mailID", id)
				.add("header", header)
				.add("mailText", mailText)
				.toString();
	}


	@Override
	public int compareTo(FaceMail o) {
		int thisTime = this.hashCode();
		long anotherEntity = o.hashCode();
		return (thisTime<anotherEntity ? -1 : (thisTime==anotherEntity ? 0 : 1));
	}
}
