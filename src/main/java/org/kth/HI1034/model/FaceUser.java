package org.kth.HI1034.model;

import com.google.common.base.MoreObjects;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import org.kth.HI1034.model.validators.ExtendedEmailValidator;

@Entity
@Table(name = "face_user", uniqueConstraints = {
		@UniqueConstraint(columnNames = "email"),
		@UniqueConstraint(columnNames = "username") })
public class FaceUser implements Serializable {




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
	 *
	 * the param order
	 *
	 * @param email             1
	 * @param username          2
	 * @param password          3
	 * @param firstName         4
	 * @param lastName          5
	 * @param createdDate       6
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
	@Column(name = "id", insertable=false, updatable=false, unique=true, nullable=false)
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
	@Length(max=MAX_LENGTH_FIRST_NAME)
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	private String lastName;
	@Column(name = "last_name")
	@NotEmpty
	@Length(max=MAX_LENGTH_LAST_NAME)
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
	@Length(max=MAX_LENGTH_EMAIL_ADDRESS)
	@Column(unique=true, nullable = false)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}


	private String password;
	@Column
	@NotEmpty
	@Length(min=MIN_LENGTH_PASSWORD)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	private String username;
	@Column(unique = true)
	@NotEmpty
	@Length(min=MIN_LENGTH_USERNAME, max=MAX_LENGTH_USERNAME)
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
	@Column
	@Basic
	public boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	private Date  accountCreated;
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


	private List<FaceUser> friends = new ArrayList<FaceUser>();
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name = "user_friends",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "user_friend_id") ,
			indexes =
					{
							@Index(name = "userF_id_idx1", columnList = "user_id") ,
							@Index(name = "userF_id_idx2", columnList = "user_friend_id")
					}
	)
	public List<FaceUser> getFriends() {
		return friends;
	}
	public void setFriends(List<FaceUser> friends) {
		this.friends = friends;
	}

	private List<UserReceivedMail> receivedFaceMails = new ArrayList<UserReceivedMail>();
	@OneToMany( mappedBy = "pk.receivedMail", fetch = FetchType.LAZY, orphanRemoval=true)
	public List<UserReceivedMail> getReceivedFaceMails() {
		return receivedFaceMails;
	}
	public void setReceivedFaceMails(List<UserReceivedMail> myReceivedFaceMails) {
		this.receivedFaceMails = myReceivedFaceMails;
	}

	private List<FaceMail> sentFaceMails = new ArrayList<FaceMail>();
	@OneToMany(mappedBy = "author", fetch=FetchType.EAGER, orphanRemoval=true)
	public List<FaceMail> getSentFaceMails() {
		return sentFaceMails;
	}
	public void setSentFaceMails(List<FaceMail> mailSentFaceMails) {
		this.sentFaceMails = mailSentFaceMails;
	}

	private List<FriendRequest> sentFriendRequests = new ArrayList<FriendRequest>();
	@OneToMany(mappedBy = "pk.requestFrom", fetch=FetchType.LAZY , orphanRemoval=true)
	public List<FriendRequest> getSentFriendRequests() {
		return sentFriendRequests;
	}
	public void setSentFriendRequests(List<FriendRequest> sentFriendRequests) {
		this.sentFriendRequests = sentFriendRequests;
	}

	private List<FriendRequest> receivedFriendRequests = new ArrayList<FriendRequest>();
	@OneToMany(mappedBy = "pk.requestTo", fetch=FetchType.EAGER , orphanRemoval=true)
	public List<FriendRequest> getReceivedFriendRequests() {
		return receivedFriendRequests;
	}
	public void setReceivedFriendRequests(List<FriendRequest> receivedFriendRequests) {
		this.receivedFriendRequests = receivedFriendRequests;
	}

	@Override
	public String toString() {
		return(MoreObjects.toStringHelper(this)
				.add("id", id)
				.add("email", email)
				.add("firstName", firstName)
				.add("lastName", lastName)
				.add("password", password)
				.add("userName", username)
				.add("\n\tSentMails", "\t\t" + sentFaceMails)
				.add("\n\tmyReceivedFaceMailFails", "\t\t" + receivedFaceMails)
				.toString() );
	}
}