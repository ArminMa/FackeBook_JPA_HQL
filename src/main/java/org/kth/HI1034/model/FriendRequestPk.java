package org.kth.HI1034.model;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class FriendRequestPk implements Serializable, Comparable<FriendRequestPk> {

	private FaceUser requestFrom;
	private FaceUser requestTo;


	public FriendRequestPk() {
	}

	public FriendRequestPk(FaceUser requestFrom, FaceUser requestTo) {
		this.requestFrom = requestFrom;
		this.requestTo = requestTo;
	}


	@ManyToOne(fetch= FetchType.LAZY)
	public FaceUser getRequestFrom() {
		return requestFrom;
	}
	public void setRequestFrom(FaceUser requestFrom) {
		this.requestFrom = requestFrom;
	}


	@ManyToOne(fetch= FetchType.LAZY)
	public FaceUser getRequestTo() {
		return requestTo;
	}
	public void setRequestTo(FaceUser requestTo) {
		this.requestTo = requestTo;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		FriendRequestPk that = (FriendRequestPk) o;

		if (requestFrom != null ? !requestFrom.equals(that.requestFrom) : that.requestFrom != null) return false;
		return requestTo != null ? requestTo.equals(that.requestTo) : that.requestTo == null;

	}

	@Override
	public int hashCode() {
		int result = requestFrom != null ? requestFrom.hashCode() : 0;
		result = 31 * result + (requestTo != null ? requestTo.hashCode() : 0);
		return result;
	}

	@Override
	public int compareTo(FriendRequestPk o) {
		int thisTime = this.hashCode();
		long anotherTime = o.hashCode();
		return (thisTime<anotherTime ? -1 : (thisTime==anotherTime ? 0 : 1));
	}
}
