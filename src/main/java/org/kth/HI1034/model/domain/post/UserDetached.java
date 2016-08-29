package org.kth.HI1034.model.domain.post;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "user_detached", uniqueConstraints = {
		@UniqueConstraint(columnNames = "email")})
public class UserDetached implements java.io.Serializable, Comparable<UserDetached>{



	private Long id;
	private String email;
	private Boolean accountRemoved = false;






	public UserDetached() {
	}

	public UserDetached(String email ) {
		this.email = email;
		this.accountRemoved = false;
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


	@Column(name = "email", insertable = true, updatable = true, unique = true, nullable = false)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

		UserDetached that = (UserDetached) o;

		if (id != null ? !id.equals(that.id) : that.id != null) return false;
		if (email != null ? !email.equals(that.email) : that.email != null) return false;
		return accountRemoved != null ? accountRemoved.equals(that.accountRemoved) : that.accountRemoved == null;

	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (email != null ? email.hashCode() : 0);
		result = 31 * result + (accountRemoved != null ? accountRemoved.hashCode() : 0);
		return result;
	}

	@Override
	public int compareTo(UserDetached o) {
		int thisTime = this.hashCode();
		long anotherEntity = o.hashCode();
		return (thisTime<anotherEntity ? -1 : (thisTime==anotherEntity ? 0 : 1));
	}
}
