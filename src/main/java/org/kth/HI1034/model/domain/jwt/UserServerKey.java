package org.kth.HI1034.model.domain.jwt;

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


	private String serverJwk;
	@Column(unique = true)
/*	@NotEmpty*/
	@Length( max = 2048)
	public String getServerJwk() {
		return serverJwk;
	}

	public void setServerJwk(String serverJwk) {
		this.serverJwk = serverJwk;
	}



	private String clientJwk;
	@Column(unique = true)
	@NotEmpty
	@Length( max = 500)
	public String getClientJwk() {
		return clientJwk;
	}

	public void setClientJwk(String clientJwk) {
		this.clientJwk = clientJwk;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		UserServerKey userServerKey = (UserServerKey) o;

		if (email != null ? !email.equals(userServerKey.email) : userServerKey.email != null) return false;
		if (serverJwk != null ? !serverJwk.equals(userServerKey.serverJwk) : userServerKey.serverJwk != null)
			return false;
		return clientJwk != null ? clientJwk.equals(userServerKey.clientJwk) : userServerKey.clientJwk == null;

	}

	@Override
	public int hashCode() {
		int result = email != null ? email.hashCode() : 0;
		result = 31 * result + (serverJwk != null ? serverJwk.hashCode() : 0);
		result = 31 * result + (clientJwk != null ? clientJwk.hashCode() : 0);
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
