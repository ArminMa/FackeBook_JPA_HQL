package org.kth.HI1034.model.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.Gson;
import org.kth.HI1034.model.domain.entity.Authority;
import org.kth.HI1034.model.domain.entity.UserFriend;
import org.kth.HI1034.model.domain.jwt.UserServerKeyPojo;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

@XmlRootElement
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FaceuserPojo implements Serializable, Comparable<FaceuserPojo> {
	private Long id;
	private Boolean accountExpired;
	private Boolean accountLocked;
	private Boolean enabled;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd hh.mm.ss.SSS")
	private Date accountCreated;
	//	@JsonFormat(shape=JsonFormat.Shape.STRING,with= JsonFormat.Feature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, pattern="yyyy.MM.dd hh.mm.ss.SSS")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd hh.mm.ss.SSS")
	private Date credentials_expired;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd hh.mm.ss.SSS")
	private Date lastTokenGenerated;
	private String email;

	private String firstName;
	private String lastName;
	private String password;
	private String username;

	private UserServerKeyPojo userServerKeyPojo;



	private List<FaceuserPojo> sentFriendRequests = new ArrayList<>();
	private List<FaceuserPojo> receivedFriendRequests = new ArrayList<>();
	private List<UserFriend> friends = new ArrayList<>();

	private SortedSet<Authority> authorities = new TreeSet<>();

	public FaceuserPojo() {
	}

	public FaceuserPojo(String email, String username, String password, String firstName0, String lastName0, Date createdDate) {

		this.email = email;
		this.username = username;
		this.password = password;
		this.accountCreated = createdDate;
		this.firstName = firstName0;
		this.lastName = lastName0;
		this.authorities.add(new Authority("user"));


	}

	public List<FaceuserPojo> getSentFriendRequests() {
		return sentFriendRequests;
	}

	public void setSentFriendRequests(List<FaceuserPojo> sentFriendRequests) {
		this.sentFriendRequests = sentFriendRequests;
	}

	public List<FaceuserPojo> getReceivedFriendRequests() {
		return receivedFriendRequests;
	}

	public void setReceivedFriendRequests(List<FaceuserPojo> receivedFriendRequests) {
		this.receivedFriendRequests = receivedFriendRequests;
	}

	public List<UserFriend> getFriends() {
		return friends;
	}

	public void setFriends(List<UserFriend> friends) {
		this.friends = friends;
	}

	public UserServerKeyPojo getUserServerKeyPojo() {
		return userServerKeyPojo;
	}

	public void setUserServerKeyPojo(UserServerKeyPojo userServerKeyPojo) {
		this.userServerKeyPojo = userServerKeyPojo;
	}

	public SortedSet<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(SortedSet<Authority> authorities) {
		this.authorities = authorities;
	}

	public Date getAccountCreated() {
		return accountCreated;
	}

	public void setAccountCreated(Date accountCreated) {
		this.accountCreated = accountCreated;
	}



	private List<FaceMailPojo> sentMail = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getLastTokenGenerated() {
		return lastTokenGenerated;
	}

	public void setLastTokenGenerated(Date lastTokenGenerated) {
		this.lastTokenGenerated = lastTokenGenerated;
	}

	public List<FaceMailPojo> getSentMail() {
		return sentMail;
	}

	public void setSentMail(List<FaceMailPojo> sentMail) {
		this.sentMail = sentMail;
	}

	public Boolean getAccountExpired() {
		return accountExpired;
	}

	public void setAccountExpired(Boolean accountExpired) {
		this.accountExpired = accountExpired;
	}

	public Boolean getAccountLocked() {
		return accountLocked;
	}

	public void setAccountLocked(Boolean accountLocked) {
		this.accountLocked = accountLocked;
	}

	public Date getCredentials_expired() {
		return credentials_expired;
	}

	public void setCredentials_expired(Date credentials_expired) {
		this.credentials_expired = credentials_expired;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}



	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		FaceuserPojo that = (FaceuserPojo) o;

		if (accountExpired != null ? !accountExpired.equals(that.accountExpired) : that.accountExpired != null)
			return false;
		if (accountLocked != null ? !accountLocked.equals(that.accountLocked) : that.accountLocked != null)
			return false;
		if (credentials_expired != null ? !credentials_expired.equals(that.credentials_expired) : that.credentials_expired != null)
			return false;
		if (!email.equals(that.email)) return false;
		if (enabled != null ? !enabled.equals(that.enabled) : that.enabled != null) return false;
		if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
		if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
		if (password != null ? !password.equals(that.password) : that.password != null) return false;
		return username.equals(that.username);

	}

	@Override
	public int hashCode() {
		int result = accountExpired != null ? 1 : 0;
		result = 31 * result + (accountLocked != null ? 1 : 0);
		result = 31 * result + (credentials_expired != null ? credentials_expired.hashCode() : 0);
		result = 31 * result + email.hashCode();
		result = 31 * result + (enabled != null ? enabled.hashCode() : 0);
		result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
		result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
		result = 31 * result + (password != null ? password.hashCode() : 0);
		result = 31 * result + username.hashCode();
		return result;
	}

	@Override
	public int compareTo(FaceuserPojo o) {
		int thisTime = this.hashCode();
		long anotherEntity = o.hashCode();
		return (thisTime < anotherEntity ? -1 : (thisTime == anotherEntity ? 0 : 1));
	}

	@Override
	public String toString() {
		Gson gson = new Gson();

		if(this.sentFriendRequests.isEmpty()){
			sentFriendRequests = null;
		}
		if(this.receivedFriendRequests.isEmpty()){
			this.receivedFriendRequests = null;
		}
		if(this.friends.isEmpty()){
			this.friends = null;
		}

		String thisJsonString = gson.toJson(this);



		if(this.sentFriendRequests == null){
			sentFriendRequests = new ArrayList<>();
		}
		if(this.receivedFriendRequests == null){
			this.receivedFriendRequests = new ArrayList<>();
		}
		if(this.friends== null){
			this.friends = new ArrayList<>();
		}



		return thisJsonString;
	}


}
