package org.kth.HI1034.model.domain.authority;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.kth.HI1034.model.domain.user.FaceUser;
import org.kth.HI1034.util.GsonX;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@JsonInclude(JsonInclude.Include.NON_NULL)
@Embeddable
public class UserAuthorityPk implements java.io.Serializable, Comparable<UserAuthorityPk> {

	private FaceUser user;
	private Authority authority;


	public UserAuthorityPk() {
	}



	public UserAuthorityPk(FaceUser theUser, Authority withAuthority) {
		this.user = theUser;
		this.authority = withAuthority;
	}



	@ManyToOne(fetch= FetchType.LAZY )
	public FaceUser getUser() {
		return user;
	}

	public void setUser(FaceUser user) {
		this.user = user;
	}



	@ManyToOne(fetch = FetchType.EAGER)
	public Authority getAuthority() {
		return authority;
	}

	public void setAuthority(Authority authority) {
		this.authority = authority;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		UserAuthorityPk that = (UserAuthorityPk) o;

		if ( (user != null && user.getId() != null) ? !user.getId().equals(that.user.getId()) : that.user != null) return false;
		return (authority != null && authority.getId() != null) ? authority.getId().equals(that.authority.getId()) : that.authority == null;

	}

	@Override
	public int hashCode() {
		int result = user != null ? user.hashCode() : 0;
		result = 31 * result + (authority != null ? authority.hashCode() : 0);
		return result;
	}

	@Override
	public int compareTo(UserAuthorityPk o) {
		int thisTime = this.hashCode();
		long anotherEntity = o.hashCode();
		return (thisTime<anotherEntity ? -1 : (thisTime==anotherEntity ? 0 : 1));
	}


	@Override
	public String toString() {
		return GsonX.gson.toJson(this);
	}


}
