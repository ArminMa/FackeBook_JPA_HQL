package org.kth.HI1034.model;


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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "face_post")
public class FacePost implements Serializable ,Comparable<FacePost>{

	private static final int MAX_LENGTH_POST = 500;


	public FacePost() {
//		pk = new FacePostPk();
	}

	/**
	 *	<code>FacePost</code>
	 * @param postText      1   String
	 * @param sentDate      2   java.util.Date
	 * @param author        3   FaceUser
	 * @param receiver      4   FaceUser
	 */
	public FacePost(String postText, Date sentDate, FaceUser author, FaceUser receiver) {
		this.postText = postText;
		this.authorUserName = author.getUsername();
		this.authorEmail = author.getEmail();
		this.sentDate = sentDate;
		this.author = (author);
		this.receiver.add(receiver);

	}

	private Long id;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", insertable = false, updatable = false, unique = true, nullable = false)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}


	private FaceUser author;
	@ManyToOne(fetch= FetchType.EAGER )
	@JoinTable(name = "post_author_user",
			joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id" ))
	public FaceUser getAuthor() {
		return author;
	}

	public void setAuthor(FaceUser author) {
		this.author = author;
	}


	private List<FaceUser> receiver = new ArrayList<>();
	@OneToMany(fetch= FetchType.EAGER , orphanRemoval = false , cascade = CascadeType.DETACH)
	@JoinTable(name = "post_received_user",
			joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
	public List<FaceUser> getReceiver() {
		return receiver;
	}

	public void setReceiver(List<FaceUser> receiver) {
		this.receiver = receiver;
	}


	private String postText;
	@Length(max=MAX_LENGTH_POST)
	@Column(name = "post_text" , length = MAX_LENGTH_POST)
	public String getPostText() {
		return postText;
	}
	public void setPostText(String postText) {
		this.postText = postText;
	}


	private String authorUserName;
	@Column(name = "author_user_name")
	public String getAuthorUserName() {
		return authorUserName;
	}

	public void setAuthorUserName(String authorUserName) {
		this.authorUserName = authorUserName;
	}


	private String authorEmail;
	@Column(name = "author_email")
	public String getAuthorEmail() {
		return authorEmail;
	}

	public void setAuthorEmail(String senderEmail) {
		this.authorEmail = senderEmail;
	}

	private Date sentDate;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy.MM.dd.hh.mm.ss.SSS")
	@NotNull
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


//	public FacePostPk pk;
//	@Embedded
//	public FacePostPk getPk() {
//		return pk;
//	}
//	public void setPk(FacePostPk receivedMailID) {
//		this.pk = receivedMailID;
//	}
//	@Transient
//	public FaceUser getAuthor() {
//		return getPk().getAuthor();
//	}
//	public void setAuthor(FaceUser requestFrom) {
//		this.getPk().setAuthor(requestFrom);
//	}
//	@Transient
//	public FaceUser getReceiver() {
//		return getPk().getReceiver();
//	}
//	public void setReceiver(FaceUser requestTo) {
//		this.getPk().setReceiver(requestTo);
//	}



	@Override
	public int compareTo(FacePost o) {
		int thisTime = this.hashCode();
		long anotherEntity = o.hashCode();
		return (thisTime<anotherEntity ? -1 : (thisTime==anotherEntity ? 0 : 1));
	}
}
