package org.kth.HI1034.model;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Embeddable
public class FacePostPk implements java.io.Serializable, Comparable<FacePostPk>{
	public FacePostPk() {
	}

	public FacePostPk(FaceUser author, FaceUser receiver) {
		this.author = author;
		this.receiver = receiver;
	}

	private FaceUser author;
	@ManyToOne(fetch= FetchType.EAGER)
	public FaceUser getAuthor() {
		return author;
	}
	public void setAuthor(FaceUser requestFrom) {
		this.author = requestFrom;
	}

	private FaceUser receiver;
	@ManyToOne(fetch= FetchType.EAGER )
//	@JoinTable(name = "post_received_user",
//			joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"),
//			inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
	public FaceUser getReceiver() {
		return receiver;
	}
	public void setReceiver(FaceUser requestTo) {
		this.receiver = requestTo;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		FacePostPk that = (FacePostPk) o;

		if (author != null ? !author.equals(that.author) : that.author != null) return false;
		return receiver != null ? receiver.equals(that.receiver) : that.receiver == null;

	}

	@Override
	public int hashCode() {
		int result = author != null ? author.hashCode() : 0;
		result = 31 * result + (receiver != null ? receiver.hashCode() : 0);
		return result;
	}

	@Override
	public int compareTo(FacePostPk o) {
		int thisTime = this.hashCode();
		long anotherEntity = o.hashCode();
		return (thisTime<anotherEntity ? -1 : (thisTime==anotherEntity ? 0 : 1));
	}
}
