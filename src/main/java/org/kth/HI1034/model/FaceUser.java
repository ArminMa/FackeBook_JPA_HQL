package org.kth.HI1034.model;

import com.google.common.base.MoreObjects;
import org.hibernate.annotations.SortNatural;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
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
import javax.validation.constraints.Past;
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

	private Long id;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	private String firstName;

	@Column(name = "first_name")
	@NotEmpty
	@Length(max = MAX_LENGTH_FIRST_NAME)
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	private String lastName;

	@Column(name = "last_name")
	@NotEmpty
	@Length(max = MAX_LENGTH_LAST_NAME)
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	private String email;

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


	private String password;

	@Column
	@NotEmpty
	@Length(min = MIN_LENGTH_PASSWORD)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private String username;

	@Column(unique = true)
	@NotEmpty
	@Length(min = MIN_LENGTH_USERNAME, max = MAX_LENGTH_USERNAME)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	private boolean accountExpired = false;

	@Column(name = "account_expired")
	public boolean getAccountExpired() {
		return accountExpired;
	}

	public void setAccountExpired(boolean accountExpired) {
		this.accountExpired = accountExpired;
	}

	private boolean accountLocked = false;

	@Column(name = "account_locked")
	public boolean getAccountLocked() {
		return accountLocked;
	}

	public void setAccountLocked(boolean accountLocked) {
		this.accountLocked = accountLocked;
	}

	private boolean credentialsExpired = false;

	@Column(name = "credentials_expired")
	@Basic
	public boolean getCredentialsExpired() {
		return credentialsExpired;
	}

	public void setCredentialsExpired(boolean credentialsExpired) {
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

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy.MM.dd hh.mm.ss.SSS")
//	@NotNull
	@Past
	@CreatedDate
	@Column(name = "acount_created_date",
			nullable = false,
			insertable = true,
			updatable = true)
	public Date getCreatedUser() {
		return accountCreated;
	}

	public void setCreatedUser(Date createdUser) {
		accountCreated = createdUser;
	}

	private Date accountUpdated;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy.MM.dd hh.mm.ss.SSS")
	@Past
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


//	private SortedSet<UserFriend> acceptedFriends = new TreeSet<UserFriend>();
//	@OneToMany(mappedBy = "pk.accepter", fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
//	@LazyCollection(LazyCollectionOption.FALSE)
//	@SortNatural
//	public SortedSet<UserFriend> getAcceptedFriends() {
//		return acceptedFriends;
//	}
//	public void setAcceptedFriends(SortedSet<UserFriend> acceptedFriends) {
//		this.acceptedFriends = acceptedFriends;
//	}
//
//	private SortedSet<UserFriend> requestedFriends = new TreeSet<UserFriend>();
//	@OneToMany(mappedBy = "pk.requester", fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
//	@LazyCollection(LazyCollectionOption.FALSE)
//	public SortedSet<UserFriend> getRequestedFriends() {
//		return requestedFriends;
//	}
//	public void setRequestedFriends(SortedSet<UserFriend> requestedFriends) {
//		this.requestedFriends = requestedFriends;
//	}
//
//	private SortedSet<FacePost> receivedFacePost = new TreeSet<FacePost>();
//	@OneToMany(mappedBy = "receiver", fetch=FetchType.EAGER)
//	@Fetch(value = FetchMode.SUBSELECT)
//	public SortedSet<FacePost> getReceivedFacePost() {
//		return receivedFacePost;
//	}
//	public void setReceivedFacePost(SortedSet<FacePost> receivedFacePost) {
//		this.receivedFacePost = receivedFacePost;
//	}
//
//	private SortedSet<FacePost> sentFacePost = new TreeSet<FacePost>();
//	@OneToMany(mappedBy = "author", fetch=FetchType.EAGER)
//	@Fetch(value = FetchMode.SUBSELECT)
//	public SortedSet<FacePost> getSentFacePost() {
//		return sentFacePost;
//	}
//	public void setSentFacePost(SortedSet<FacePost> sentFacePost) {
//		this.sentFacePost = sentFacePost;
//	}

	private SortedSet<UserReceivedMail> receivedFaceMails = new TreeSet<>();
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "pk.receivingUser")
//	@BatchSize(size=25)
//	@LazyCollection(LazyCollectionOption.FALSE)
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



	private SortedSet<FriendRequest> sentFriendRequests = new TreeSet<FriendRequest>();
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "pk.requestFrom")
	@SortNatural
	public SortedSet<FriendRequest> getSentFriendRequests() {
		return sentFriendRequests;
	}
	public void setSentFriendRequests(SortedSet<FriendRequest> sentFriendRequests) {
		this.sentFriendRequests = sentFriendRequests;
	}

	private SortedSet<FriendRequest> receivedFriendRequests = new TreeSet<FriendRequest>();
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY,  mappedBy = "pk.requestTo")
	@SortNatural
	public SortedSet<FriendRequest> getReceivedFriendRequests() {
		return receivedFriendRequests;
	}
	public void setReceivedFriendRequests(SortedSet<FriendRequest> receivedFriendRequests) {
		this.receivedFriendRequests = receivedFriendRequests;
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
		result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
		result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
		result = 31 * result + (email != null ? email.hashCode() : 0);
		result = 31 * result + (password != null ? password.hashCode() : 0);
		result = 31 * result + (username != null ? username.hashCode() : 0);
		result = 31 * result + (accountExpired ? 1 : 0);
		result = 31 * result + (accountLocked ? 1 : 0);
		result = 31 * result + (credentialsExpired ? 1 : 0);
		result = 31 * result + (enabled != null ? enabled.hashCode() : 0);
		result = 31 * result + (accountCreated != null ? accountCreated.hashCode() : 0);
		result = 31 * result + (accountUpdated != null ? accountUpdated.hashCode() : 0);
		return result;
	}



	@Override
	public int compareTo(FaceUser o) {
		int thisTime = this.hashCode();
		long anotherTime = o.hashCode();
		return (thisTime < anotherTime ? -1 : (thisTime == anotherTime ? 0 : 1));
	}
}