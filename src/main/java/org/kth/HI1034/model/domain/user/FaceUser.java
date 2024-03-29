package org.kth.HI1034.model.domain.user;

import com.google.common.base.MoreObjects;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.SortNatural;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.kth.HI1034.model.domain.frienRequest.FriendRequest;
import org.kth.HI1034.model.domain.UserFriends.UserFriend;
import org.kth.HI1034.model.domain.faceMail.userReceivedMail.UserReceivedMail;
import org.kth.HI1034.model.domain.authority.UserAuthority;
import org.springframework.core.style.ToStringCreator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;
import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;

//import org.kth.HI1034.model.validators.ExtendedEmailValidator;

@Entity
@Table(name = "face_user", uniqueConstraints = {
		@UniqueConstraint(columnNames = "email"),
		@UniqueConstraint(columnNames = "username")})
public class FaceUser implements Serializable, Comparable<FaceUser> {


	public static final int MAX_LENGTH_EMAIL_ADDRESS = 100;
	public static final int MAX_LENGTH_FIRST_NAME = 25;
	public static final int MAX_LENGTH_LAST_NAME = 50;
	public static final int MAX_LENGTH_USERNAME = 15;
	public static final int MAX_LENGTH_PASSWORD = 20;
	public static final int MAX_LENGTH_PROVIDERID = 25;

	public static final int MIN_LENGTH_USERNAME = 3;
	public static final int MIN_LENGTH_PASSWORD = 2;
	public static final int MIN_LENGTH_FIRST_NAME = 2;
	public static final int MIN_LENGTH_LAST_NAME = 2;

	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String username;





	public FaceUser() {
	}

	/**
	 * the param order
	 *
	 * @param email       1
	 * @param username    2
	 * @param password    3
	 * @param firstName   4
	 * @param lastName    5
	 * @param createdDate 6
	 */
	public FaceUser(String email, String username, String password, String firstName, String lastName, Date createdDate) {
		this.email = email;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.accountCreated = createdDate;
	}

	public void update(String username, String firstName, String lastName, String email, Date createdDate) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		this.accountUpdated = createdDate;
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

	@Transient
	public boolean isNew() {
		return (this.id == null);
	}



	@Column(name = "first_name")
	@NotEmpty
	@Length(max = MAX_LENGTH_FIRST_NAME)
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}



	@Column(name = "last_name")
	@NotEmpty
	@Length(max = MAX_LENGTH_LAST_NAME)
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}



	//	@Basic
//	@ExtendedEmailValidator
	@NotEmpty
	@Length(max = MAX_LENGTH_EMAIL_ADDRESS)
	@Column(unique = true, nullable = false)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}




	@Column
	@NotEmpty
	@Length(min = MIN_LENGTH_PASSWORD)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}



	@Column(unique = true)
	@NotEmpty
	@Length(min = MIN_LENGTH_USERNAME, max = MAX_LENGTH_USERNAME)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	private Boolean accountExpired = false;

	@Column(name = "account_expired")
	public Boolean getAccountExpired() {
		return accountExpired;
	}

	public void setAccountExpired(Boolean accountExpired) {
		this.accountExpired = accountExpired;
	}

	private Boolean accountLocked = false;

	@Column(name = "account_locked")
	public Boolean getAccountLocked() {
		return accountLocked;
	}

	public void setAccountLocked(Boolean accountLocked) {
		this.accountLocked = accountLocked;
	}

	private Boolean credentialsExpired = false;

	@Column(name = "credentials_expired")
	@Basic
	public Boolean getCredentialsExpired() {
		return credentialsExpired;
	}

	public void setCredentialsExpired(Boolean credentialsExpired) {
		this.credentialsExpired = credentialsExpired;
	}


	private Boolean enabled = true;

	@Column(name = "account_enabled")
	@Basic
	public boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	private Date accountCreated;
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd.hh:mm:ss:SSS")
//	@NotNull

	@CreatedDate
	@Column(name = "acount_created_date",
			nullable = false,
			insertable = true,
			updatable = true)
	public Date getAccountCreated() {
		return accountCreated;
	}

	public void setAccountCreated(Date accountCreated) {
		this.accountCreated = accountCreated;
	}

	private Date accountUpdated;
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd.hh:mm:ss.SSS")

	@CreatedDate
	@Column(name = "acount_updated_date",
			nullable = true,
			insertable = true,
			updatable = true)
	public Date getAccountUpdated() {
		return accountUpdated;
	}

	public void setAccountUpdated(Date accountUpdated) {
		this.accountUpdated = accountUpdated;
	}

	/*
	 * User Mapping starts Here
	 */
//---------------------------- authority ------------------------

//	CascadeType.DETACH means that if i disappear if the rows where i i'm in "in between this and other table"
// should be remove. we don't want that

//	CascadeType.REMOVE wants to remove the other side Entity if this is removed, that is not god hear.

	private SortedSet<UserAuthority> authorities = new TreeSet<>();
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER, mappedBy = "pk.user")
	@BatchSize(size=3)
	@LazyCollection(LazyCollectionOption.FALSE)
	@SortNatural
	public SortedSet<UserAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(SortedSet<UserAuthority> authorities) {
		this.authorities = authorities;
	}

	//----------------User Received Mail--------------------------

	private SortedSet<UserReceivedMail> receivedFaceMails = new TreeSet<>();
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER, mappedBy = "pk.receivingUser")
	@BatchSize(size=25)
	@LazyCollection(LazyCollectionOption.FALSE)
	@SortNatural
	public SortedSet<UserReceivedMail> getReceivedFaceMails() {
		return receivedFaceMails;
	}
	public void setReceivedFaceMails(SortedSet<UserReceivedMail> myReceivedFaceMails) {
		this.receivedFaceMails = myReceivedFaceMails;
	}

	private SortedSet<UserReceivedMail> sentFaceMails = new TreeSet<>();
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "pk.author")
//	@BatchSize(size=25)
//	@LazyCollection(LazyCollectionOption.FALSE)
	@SortNatural
	public SortedSet<UserReceivedMail> getSentFaceMails() {
		return sentFaceMails;
	}
	public void setSentFaceMails(SortedSet<UserReceivedMail> mailSentFaceMails) {
		this.sentFaceMails = mailSentFaceMails;
	}




	//---------------- Friend Request to and from user --------------------------

	private SortedSet<FriendRequest> sentFriendRequests = new TreeSet<>();
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "pk.requestFrom")
	@SortNatural
	public SortedSet<FriendRequest> getSentFriendRequests() {
		return sentFriendRequests;
	}
	public void setSentFriendRequests(SortedSet<FriendRequest> sentFriendRequests) {
		this.sentFriendRequests = sentFriendRequests;
	}

	private SortedSet<FriendRequest> receivedFriendRequests = new TreeSet<>();
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER,  mappedBy = "pk.requestTo")
	@LazyCollection(LazyCollectionOption.FALSE)
	@SortNatural
	public SortedSet<FriendRequest> getReceivedFriendRequests() {
		return receivedFriendRequests;
	}
	public void setReceivedFriendRequests(SortedSet<FriendRequest> receivedFriendRequests) {
		this.receivedFriendRequests = receivedFriendRequests;
	}


	//--------------------------------Friends---------------------------------------------



	private SortedSet<UserFriend> acceptedFriends = new TreeSet<>();
	@OneToMany( cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "pk.accepter")
	@LazyCollection(LazyCollectionOption.FALSE)
	@SortNatural
	public SortedSet<UserFriend> getAcceptedFriends() {
		return acceptedFriends;
	}
	public void setAcceptedFriends(SortedSet<UserFriend> acceptedFriends) {
		this.acceptedFriends = acceptedFriends;
	}

	private SortedSet<UserFriend> requestedFriends = new TreeSet<>();
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "pk.requester")
	@LazyCollection(LazyCollectionOption.FALSE)
	@SortNatural
	public SortedSet<UserFriend> getRequestedFriends() {
		return requestedFriends;
	}
	public void setRequestedFriends(SortedSet<UserFriend> requestedFriends) {
		this.requestedFriends = requestedFriends;
	}





	@Override
	public String toString() {
		return (MoreObjects.toStringHelper(this)
				.add("id", id)
				.add("email", email)
				.add("firstName", firstName)
				.add("lastName", lastName)
				.add("password", password)
				.add("userName", username)
//				.add("\n\tSentMails", "\t\t" + sentFaceMails)
//				.add("\n\tmyReceivedFaceMailFails", "\t\t" + receivedFaceMails)
				.toString());
	}

	public String toString2() {
		return new ToStringCreator(this)
				.append("id", this.getId())
				.append("new", this.isNew())
				.append("lastName", this.getLastName())
				.append("firstName", this.getFirstName())
				.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		FaceUser faceUser = (FaceUser) o;

		if (accountExpired != faceUser.accountExpired) return false;
		if (accountLocked != faceUser.accountLocked) return false;
		if (credentialsExpired != faceUser.credentialsExpired) return false;
		if (id != null ? !id.equals(faceUser.id) : faceUser.id != null) return false;
		if (firstName != null ? !firstName.equals(faceUser.firstName) : faceUser.firstName != null) return false;
		if (lastName != null ? !lastName.equals(faceUser.lastName) : faceUser.lastName != null) return false;
		if (email != null ? !email.equals(faceUser.email) : faceUser.email != null) return false;
		if (password != null ? !password.equals(faceUser.password) : faceUser.password != null) return false;
		if (username != null ? !username.equals(faceUser.username) : faceUser.username != null) return false;
		if (enabled != null ? !enabled.equals(faceUser.enabled) : faceUser.enabled != null) return false;
		if (accountCreated != null ? !accountCreated.equals(faceUser.accountCreated) : faceUser.accountCreated != null)
			return false;
		return accountUpdated != null ? accountUpdated.equals(faceUser.accountUpdated) : faceUser.accountUpdated == null;

	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 7 * result + (firstName != null ? firstName.hashCode() : 0);
		result = 7 * result + (lastName != null ? lastName.hashCode() : 0);
		result = 7 * result + (email != null ? email.hashCode() : 0);
		result = 7 * result + (password != null ? password.hashCode() : 0);
		result = 7 * result + (username != null ? username.hashCode() : 0);
		result = 7 * result + (accountExpired ? 1 : 0);
		result = 7 * result + (accountLocked ? 1 : 0);
		result = 7 * result + (credentialsExpired ? 1 : 0);
		result = 7 * result + (enabled != null ? 1 : 0);
		result = 7 * result + (accountCreated != null ? accountCreated.hashCode() : 0);
		result = 7 * result + (accountUpdated != null ? accountUpdated.hashCode() : 0);
		return result;
	}



	@Override
	public int compareTo(FaceUser o) {
		int thisTime = this.hashCode();
		long anotherEntity = o.hashCode();
		return (thisTime < anotherEntity ? -1 : (thisTime == anotherEntity ? 0 : 1));
	}
}