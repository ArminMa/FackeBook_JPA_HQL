package org.kth.HI1034.model.domain.post;


import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.SortNatural;
import org.hibernate.validator.constraints.Length;
import org.kth.HI1034.model.domain.user.FaceUser;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;

@XmlRootElement
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "face_post")
public class FacePost implements Serializable ,Comparable<FacePost>{

	private static final int MAX_LENGTH_POST = 500;

	private Long id;
	private UserDetached author;
	private SortedSet<UserDetached> receivers = new TreeSet<>();
	private String postText;
	private Date sentDate;


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
		this.author = new UserDetached( author.getEmail());
		this.receivers.add( new UserDetached( receiver.getEmail()) );

	}

	public FacePost(String postText, Date sentDate, UserDetached author, UserDetached receiver) {
		this.postText = postText;
		this.sentDate = sentDate;
		this.author = author;
		this.receivers.add( receiver );

	}


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", insertable = false, updatable = false, unique = true, nullable = false)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}



	@ManyToOne(fetch= FetchType.EAGER )
	@JoinTable(name = "post_author",
			joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "user_email", referencedColumnName = "email" ))
	public UserDetached getAuthor() {
		return author;
	}

	public void setAuthor(UserDetached author) {
		this.author = author;
	}


	@ManyToMany( fetch = FetchType.EAGER)
	@LazyCollection(LazyCollectionOption.FALSE)
	@SortNatural
	@JoinTable(name = "post_receivers",
			joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "user_email", referencedColumnName = "email"))
	public SortedSet<UserDetached> getReceivers() {
		return receivers;
	}
	public void setReceivers(SortedSet<UserDetached> receivedFacePost) {
		this.receivers = receivedFacePost;
	}





	@Length(max=MAX_LENGTH_POST)
	@Column(name = "post_text" , length = MAX_LENGTH_POST)
	public String getPostText() {
		return postText;
	}
	public void setPostText(String postText) {
		this.postText = postText;
	}



	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd.hh:mm:ss:SSS")
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
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		FacePost facePost = (FacePost) o;

		if (id != null ? !id.equals(facePost.id) : facePost.id != null) return false;
		if (postText != null ? !postText.equals(facePost.postText) : facePost.postText != null) return false;
		return sentDate != null ? sentDate.equals(facePost.sentDate) : facePost.sentDate == null;

	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (author != null ? author.hashCode() : 0);
		result = 31 * result + (receivers != null ? receivers.hashCode() : 0);
		result = 31 * result + (postText != null ? postText.hashCode() : 0);
		result = 31 * result + (sentDate != null ? sentDate.hashCode() : 0);
		return result;
	}

	@Override
	public int compareTo(FacePost o) {
		int thisTime = this.hashCode();
		long anotherEntity = o.hashCode();
		return (thisTime<anotherEntity ? -1 : (thisTime==anotherEntity ? 0 : 1));
	}


}
