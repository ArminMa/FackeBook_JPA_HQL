package org.kth.HI1034.model.domain.entity.UserFriends;

import org.kth.HI1034.model.domain.entity.user.FaceUser;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class UserFriendID implements Serializable, Comparable<UserFriendID>{

	public FaceUser accepter;
	public FaceUser requester;

	public UserFriendID() {

	}

	public UserFriendID(FaceUser user, FaceUser friend) {
		this.accepter = user;
		this.requester = friend;
	}

	@ManyToOne(fetch=FetchType.LAZY )
	public FaceUser getRequester() {
		return requester;
	}
	public void setRequester(FaceUser requester) {
		this.requester = requester;
	}

	@ManyToOne(fetch= FetchType.LAZY )
	public FaceUser getAccepter() {
		return accepter;
	}
	public void setAccepter(FaceUser accepter) {
		this.accepter = accepter;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		UserFriendID that = (UserFriendID) o;

		if (accepter != null ? !accepter.equals(that.accepter) : that.accepter != null) return false;
		return requester != null ? requester.equals(that.requester) : that.requester == null;

	}

	@Override
	public int hashCode() {
		int result = accepter != null ? accepter.hashCode() : 0;
		result = 31 * result + (requester != null ? requester.hashCode() : 0);
		return result;
	}

	@Override
	public int compareTo(UserFriendID o) {
		int thisTime = this.hashCode();
		long anotherEntity = o.hashCode();
		return (thisTime < anotherEntity ? -1 : (thisTime == anotherEntity ? 0 : 1));
	}
}
