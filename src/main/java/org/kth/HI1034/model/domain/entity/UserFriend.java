package org.kth.HI1034.model.domain.entity;


import org.kth.HI1034.model.domain.entity.user.FaceUser;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
public class UserFriend implements Serializable, Comparable<UserFriend> {

	public UserFriend() {

	}

	public UserFriend(FaceUser accepter, FaceUser requester, Date beginningOfFriendship) {
		this.pk = new UserFriendID(accepter, requester);
		this.friendshipBegan = beginningOfFriendship;
	}

	private Date friendshipBegan;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy.MM.dd.hh.mm.ss.SSS")
	@NotNull
	@CreatedDate
	@Column(name = "friendship_began",
			nullable = true,
			insertable = true,
			updatable = true)
	public Date getFriendshipBegan() {
		return friendshipBegan;
	}
	public void setFriendshipBegan(Date friendshipBegan) {
		this.friendshipBegan = friendshipBegan;
	}

	private Date friendshipEndedn;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy.MM.dd.hh.mm.ss.SSS")
	@CreatedDate
	@Column(name = "friendship_endedn",
			nullable = true,
			insertable = true,
			updatable = true)
	public Date getFriendshipEndedn() {
		return friendshipEndedn;
	}
	public void setFriendshipEndedn(Date friendshipEndedn) {
		this.friendshipEndedn = friendshipEndedn;
	}


	private UserFriendID pk;
	@EmbeddedId
	public UserFriendID getPk() {
		return pk;
	}
	public void setPk(UserFriendID userFriendID) {
		this.pk = userFriendID;
	}

	@Transient
	public FaceUser getRequester() {
		return getPk().getRequester();
	}
	public void setRequester(FaceUser requester) {
		this.getPk().setRequester(requester);
	}

	@Transient
	public FaceUser getAccepter() {
		return getPk().getAccepter();
	}
	public void setAccepter(FaceUser accepter) {
		this.getPk().setAccepter(accepter);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		UserFriend that = (UserFriend) o;

		if (friendshipBegan != null ? !friendshipBegan.equals(that.friendshipBegan): that.friendshipBegan != null)
			return false;
		if (friendshipEndedn != null ? !friendshipEndedn.equals(that.friendshipEndedn) : that.friendshipEndedn != null)
			return false;
		return pk != null ? pk.equals(that.pk) : that.pk == null;
	}

	@Override
	public int hashCode() {
		int result = friendshipBegan != null ? friendshipBegan.hashCode() : 0;
		result = 31 * result + (friendshipEndedn != null ? friendshipEndedn.hashCode() : 0);
		result = 31 * result + (pk != null ? pk.hashCode() : 0);
		return result;
	}

	@Override
	public int compareTo(UserFriend o) {
		int thisTime = this.hashCode();
		long anotherEntity = o.hashCode();
		return (thisTime < anotherEntity ? -1 : (thisTime == anotherEntity ? 0 : 1));
	}
}
