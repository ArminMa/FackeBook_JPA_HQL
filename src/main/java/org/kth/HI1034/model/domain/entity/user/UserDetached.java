package org.kth.HI1034.model.domain.entity.user;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "user_detached", uniqueConstraints = {
		@UniqueConstraint(columnNames = "username")})
public class UserDetached implements java.io.Serializable, Comparable<UserDetached>{

	public UserDetached() {
	}

	public UserDetached(String email, String username ) {
		this.username = username;
		this.email = email;
		this.accountRemoved = false;
	}



	private String email;
	@Id
	@Column(name = "email", insertable = true, updatable = true, unique = true, nullable = false)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	private String username;
	@Column(name = "username", insertable = true, updatable = true, unique = true, nullable = false)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}



//	private SortedSet<FacePost> receivedPosts = new TreeSet<>();
//	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER,  mappedBy = "receivers")
//	@LazyCollection(LazyCollectionOption.FALSE)
//	@SortNatural
//	public SortedSet<FacePost> getReceivedPosts() {
//		return receivedPosts;
//	}
//
//	public void setReceivedPosts(SortedSet<FacePost> receivedPosts) {
//		this.receivedPosts = receivedPosts;
//	}

	private Boolean accountRemoved = false;
	@Column(name = "account_removed")
	@Basic
	public boolean getAccountRemoved() {
		return accountRemoved;
	}

	public void setAccountRemoved(boolean accountRemoved) {
		this.accountRemoved = accountRemoved;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		UserDetached userDetached = (UserDetached) o;

		if (email != null ? !email.equals(userDetached.email) : userDetached.email != null) return false;
		return username != null ? username.equals(userDetached.username) : userDetached.username == null;

	}

	@Override
	public int hashCode() {
		int result = email != null ? email.hashCode() : 0;
		result = 31 * result + (username != null ? username.hashCode() : 0);
		return result;
	}

	@Override
	public int compareTo(UserDetached o) {
		int thisTime = this.hashCode();
		long anotherEntity = o.hashCode();
		return (thisTime<anotherEntity ? -1 : (thisTime==anotherEntity ? 0 : 1));
	}
}
