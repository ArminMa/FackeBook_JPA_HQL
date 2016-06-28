//package org.kth.HI1034.model;
//
//
//import org.hibernate.validator.constraints.Length;
//import org.springframework.data.annotation.CreatedDate;
//import org.springframework.format.annotation.DateTimeFormat;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.ManyToOne;
//import javax.persistence.OrderColumn;
//import javax.persistence.Table;
//import javax.persistence.Temporal;
//import javax.persistence.TemporalType;
//import javax.validation.constraints.Past;
//import java.io.Serializable;
//import java.util.Date;
//
//@Entity
//@Table(name = "face_post")
//public class FacePost implements Serializable {
//
//	private static final int MAX_LENGTH_POST = 500;
//
//	private Long id;
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	public Long getId() {
//		return id;
//	}
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	private String postText;
//	@Length(max=MAX_LENGTH_POST)
//	@Column(name = "post_text")
//	public String getPostText() {
//		return postText;
//	}
//	public void setPostText(String postText) {
//		this.postText = postText;
//	}
//
//	private Date sentDate;
//	@Temporal(TemporalType.DATE)
//	@DateTimeFormat(pattern = "yyyy.MM.dd hh.mm.ss.SSS")
////	@NotNull
//	@Past
//	@CreatedDate
//	@Column(name = "sent_date",
//			nullable = true,
//			insertable = true,
//			updatable = true)
//	public Date getSentDate() {
//		return sentDate;
//	}
//	public void setSentDate(Date sentDate) {
//		this.sentDate = sentDate;
//	}
//
//	private FaceUser author;
//	@ManyToOne(fetch= FetchType.LAZY )
//	@OrderColumn(name="index_author_id", nullable = false, columnDefinition = "author")
//	public FaceUser getAuthor() {
//		return author;
//	}
//	public void setAuthor(FaceUser auther) {
//		this.author = auther;
//	}
//
//	private FaceUser receiver;
//	@ManyToOne(fetch= FetchType.LAZY )
//	@OrderColumn(name="index_receiver_id", nullable = false, columnDefinition = "receiver")
//	public FaceUser getReceiver() {
//		return receiver;
//	}
//	public void setReceiver(FaceUser postTo) {
//		this.receiver = postTo;
//	}
//}
