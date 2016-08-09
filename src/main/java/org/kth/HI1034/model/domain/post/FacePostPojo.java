package org.kth.HI1034.model.domain.post;

import org.kth.HI1034.model.domain.user.FaceuserPojo;
import org.kth.HI1034.util.GsonX;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Sys on 2016-08-08.
 */
public class FacePostPojo implements Serializable,Comparable<FacePostPojo>{

	public FacePostPojo() {
//		pk = new FacePostPk();
	}



	private Long id;
	private FaceuserPojo faceUserAuthor;
	private String postText;
	private Date sentDate;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}


	public FaceuserPojo getFaceUserAuthor() {
		return faceUserAuthor;
	}

	public void setFaceUserAuthor(FaceuserPojo faceUserAuthor) {
		this.faceUserAuthor = faceUserAuthor;
	}

	public String getPostText() {
		return postText;
	}
	public void setPostText(String postText) {
		this.postText = postText;
	}


	public Date getSentDate() {
		return sentDate;
	}
	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		FacePostPojo that = (FacePostPojo) o;

		if (id != null ? !id.equals(that.id) : that.id != null) return false;
		if (faceUserAuthor != null ? !faceUserAuthor.equals(that.faceUserAuthor) : that.faceUserAuthor != null)
			return false;
		if (postText != null ? !postText.equals(that.postText) : that.postText != null) return false;
		return sentDate != null ? sentDate.equals(that.sentDate) : that.sentDate == null;

	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (faceUserAuthor != null ? faceUserAuthor.hashCode() : 0);
		result = 31 * result + (postText != null ? postText.hashCode() : 0);
		result = 31 * result + (sentDate != null ? sentDate.hashCode() : 0);
		return result;
	}

	@Override
	public int compareTo(FacePostPojo o) {
		int thisObject = this.hashCode();
		long anotherEntity = o.hashCode();
		return (thisObject<anotherEntity ? -1 : (thisObject==anotherEntity ? 0 : 1));
	}

	@Override
	public String toString() {
		return GsonX.gson.toJson(this);
	}

}
