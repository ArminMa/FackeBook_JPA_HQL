package org.kth.HI1034.model;


import org.hibernate.validator.constraints.Length;
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
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

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
		this.sentDate = sentDate;
		this.pk = new FacePostPk( author, receiver);
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


	public FacePostPk pk;
	@Embedded
	public FacePostPk getPk() {
		return pk;
	}
	public void setPk(FacePostPk receivedMailID) {
		this.pk = receivedMailID;
	}





	@Override
	public int compareTo(FacePost o) {
		int thisTime = this.hashCode();
		long anotherEntity = o.hashCode();
		return (thisTime<anotherEntity ? -1 : (thisTime==anotherEntity ? 0 : 1));
	}
}
