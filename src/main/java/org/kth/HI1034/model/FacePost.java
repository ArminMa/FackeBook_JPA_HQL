package org.kth.HI1034.model;


import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "face_post")
public class FacePost implements Serializable ,Comparable<FacePost>{

	private static final int MAX_LENGTH_POST = 500;


	public FacePost() {
	}


	/**
	 *
	 * @param postText      1   String
	 * @param sentDate      2   java.util.Date
	 * @param author        3   FaceUser
	 * @param receiver      4   FaceUser
	 */
	public FacePost(String postText, Date sentDate, FaceUser author, FaceUser receiver) {
		this.postText = postText;
		this.sentDate = sentDate;
		this.author = author;
		this.receiver = receiver;
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




	private String postText;
	@Length(max=MAX_LENGTH_POST)
	@Column(name = "post_text")
	public String getPostText() {
		return postText;
	}
	public void setPostText(String postText) {
		this.postText = postText;
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


	public FaceUser author;
	@ManyToOne(fetch= FetchType.LAZY)
	public FaceUser getAuthor() {
		return author;
	}
	public void setAuthor(FaceUser requestFrom) {
		this.author = requestFrom;
	}

	public FaceUser receiver;
	@ManyToOne(fetch= FetchType.LAZY )
	public FaceUser getReceiver() {
		return receiver;
	}
	public void setReceiver(FaceUser requestTo) {
		this.receiver = requestTo;
	}

77777

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		FacePost facePost = (FacePost) o;

		if (id != null ? !id.equals(facePost.id) : facePost.id != null) return false;
		if (postText != null ? !postText.equals(facePost.postText) : facePost.postText != null) return false;
		if (sentDate != null ? !sentDate.equals(facePost.sentDate) : facePost.sentDate != null) return false;
		if (author != null ? !author.equals(facePost.author) : facePost.author != null) return false;
		return receiver != null ? receiver.equals(facePost.receiver) : facePost.receiver == null;

	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (postText != null ? postText.hashCode() : 0);
		result = 31 * result + (sentDate != null ? sentDate.hashCode() : 0);
		result = 31 * result + (author != null ? author.hashCode() : 0);
		result = 31 * result + (receiver != null ? receiver.hashCode() : 0);
		return result;
	}


	@Override
	public int compareTo(FacePost o) {
		int thisTime = this.hashCode();
		long anotherEntity = o.hashCode();
		return (thisTime<anotherEntity ? -1 : (thisTime==anotherEntity ? 0 : 1));
	}
}
