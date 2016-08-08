package org.kth.HI1034.model.domain.authority;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.kth.HI1034.util.GsonX;
import org.kth.HI1034.util.enums.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Collection;

@XmlRootElement
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthorityPojo implements Serializable, GrantedAuthority,Authentication, Comparable<AuthorityPojo> {


	private Long id;
	private String userRole;
	private boolean isLocked = false;
	private boolean isAuthenticated = false;

    public AuthorityPojo() {}

	public AuthorityPojo(Role roleUser) {
		this.userRole = roleUser.toString();
		this.id = (long) (roleUser.ordinal()+1);
	}


	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	@Override
	public String getAuthority() {
		return getUserRole();
	}




	public boolean isLocked() {
		return isLocked;
	}
	public void setLocked(boolean locked) {
		isLocked = locked;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		AuthorityPojo that = (AuthorityPojo) o;

		if (isLocked != that.isLocked) return false;
		if (id != null ? !id.equals(that.id) : that.id != null) return false;
		return userRole == that.userRole;

	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (userRole != null ? userRole.hashCode() : 0);
		result = 31 * result + (isLocked ? 1 : 0);
		return result;
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public int compareTo(AuthorityPojo o) {
		int thisTime = this.hashCode();
		long anotherEntity = o.hashCode();
		return (thisTime < anotherEntity ? -1 : (thisTime == anotherEntity ? 0 : 1));
	}

	@Override
	public String toString() {
		return GsonX.gson.toJson(this);

	}


	// -----------------------  Authentication interface ------------------------
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getDetails() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return null;
	}

	@Override
	public boolean isAuthenticated() {
		return isAuthenticated;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		this.isAuthenticated = isAuthenticated;
	}
}
