package org.kth.HI1034.model.domain.keyUserServer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.Gson;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
public class UserServerKey implements Serializable, Comparable<UserServerKey> {


	private Long id;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", insertable=false, updatable=false, unique=true, nullable=false)
	@JsonIgnore
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	private String email;
	@NotEmpty
	@Column(name = "email", insertable = true, updatable = true, unique = true, nullable = false)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	private String sharedKey;
	@Column(unique = true)
/*	@NotEmpty*/
	@Length( max = 2048)
	public String getSharedKey() {
		return sharedKey;
	}

	public void setSharedKey(String serverJwk) {
		this.sharedKey = serverJwk;
	}

	private String tokenKey;
	/*	@NotEmpty*/
	@Length( max = 2048)
	public String getTokenKey() {
		return tokenKey;
	}

	public void setTokenKey(String tokenKey) {
		this.tokenKey = tokenKey;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		UserServerKey that = (UserServerKey) o;

		if (id != null ? !id.equals(that.id) : that.id != null) return false;
		if (email != null ? !email.equals(that.email) : that.email != null) return false;
		return sharedKey != null ? sharedKey.equals(that.sharedKey) : that.sharedKey == null;

	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (email != null ? email.hashCode() : 0);
		result = 31 * result + (sharedKey != null ? sharedKey.hashCode() : 0);
		return result;
	}

	@Override
	public int compareTo(UserServerKey o) {
		int thisTime = this.hashCode();
		long anotherEntity = o.hashCode();
		return (thisTime < anotherEntity ? -1 : (thisTime == anotherEntity ? 0 : 1));
	}

	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}

}
