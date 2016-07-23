package org.kth.HI1034.model.domain.jwt;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.Gson;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;


@XmlRootElement
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserServerKeyPojo  implements Serializable, Comparable<UserServerKeyPojo>  {


	private String email;
	private String clientJwk;
	private String serverJwk;


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public String getClientJwk() {
		return clientJwk;
	}

	public void setClientJwk(String clientJwk) {
		this.clientJwk = clientJwk;
	}

	public String getServerJwk() {
		return serverJwk;
	}

	public void setServerJwk(String serverJwk) {
		this.serverJwk = serverJwk;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		UserServerKeyPojo that = (UserServerKeyPojo) o;

		return email != null ? email.equals(that.email) : that.email == null;

	}

	@Override
	public int hashCode() {
		return email != null ? email.hashCode() : 0;
	}

	@Override
	public int compareTo(UserServerKeyPojo o) {
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


