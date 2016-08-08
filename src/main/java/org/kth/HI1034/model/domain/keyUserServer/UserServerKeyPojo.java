package org.kth.HI1034.model.domain.keyUserServer;


import com.fasterxml.jackson.annotation.JsonInclude;
import org.kth.HI1034.util.GsonX;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;


@XmlRootElement
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserServerKeyPojo  implements Serializable, Comparable<UserServerKeyPojo>  {


	private String email;
	private String sharedKey;
	private String tokenKey;
	private Long id;





	public UserServerKeyPojo(String email, String sharedKey) {
		this.email = email;
		this.sharedKey = sharedKey;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSharedKey() {
		return sharedKey;
	}

	public void setSharedKey(String sharedKey) {
		this.sharedKey = sharedKey;
	}

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

		UserServerKeyPojo that = (UserServerKeyPojo) o;

		if (email != null ? !email.equals(that.email) : that.email != null) return false;
		return sharedKey != null ? sharedKey.equals(that.sharedKey) : that.sharedKey == null;

	}

	@Override
	public int hashCode() {
		int result = email != null ? email.hashCode() : 0;
		result = 31 * result + (sharedKey != null ? sharedKey.hashCode() : 0);
		return result;
	}

	@Override
	public int compareTo(UserServerKeyPojo o) {
		int thisTime = this.hashCode();
		long anotherEntity = o.hashCode();
		return (thisTime < anotherEntity ? -1 : (thisTime == anotherEntity ? 0 : 1));
	}

	@Override
	public String toString() {

		return GsonX.gson.toJson(this);
	}


}


