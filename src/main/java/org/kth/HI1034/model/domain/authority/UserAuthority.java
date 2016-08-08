package org.kth.HI1034.model.domain.authority;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.kth.HI1034.model.domain.user.FaceUser;
import org.kth.HI1034.util.GsonX;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
public class UserAuthority  implements Serializable, Comparable<UserAuthority> {

	private boolean isLocked = false;
	private Long id;
	private UserAuthorityPk pk;

	public UserAuthority() {

	}

	public UserAuthority(FaceUser faceUser, Authority authority) {
		this.pk = new UserAuthorityPk(faceUser, authority);
	}


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", insertable=false, updatable=false, unique=true, nullable=false)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "is_locked")
	public boolean isLocked() {
		return isLocked;
	}
	public void setLocked(boolean locked) {
		isLocked = locked;
	}


	@Embedded
	public UserAuthorityPk getPk() {
		return pk;
	}
	public void setPk(UserAuthorityPk userAuthorityPk) {
		this.pk = userAuthorityPk;
	}

	@Transient
	public FaceUser getUser() {
		return getPk().getUser();
	}
	public void setUser(FaceUser faceUser) {
		this.getPk().setUser(faceUser);
	}

	@Transient
	public Authority getAuthority() {
		return getPk().getAuthority();
	}
	public void setAccepter(Authority authority) {
		this.getPk().setAuthority(authority);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		UserAuthority that = (UserAuthority) o;

		return pk != null ? pk.equals(that.pk) : that.pk == null;

	}

	@Override
	public int hashCode() {
		int result = (isLocked ? 1 : 0);
		result = 31 * result + (pk != null ? pk.hashCode() : 0);
		return result;
	}

	@Override
	public int compareTo(UserAuthority o) {
		int thisTime = this.hashCode();
		long anotherEntity = o.hashCode();
		return (thisTime<anotherEntity ? -1 : (thisTime==anotherEntity ? 0 : 1));
	}

	@Override
	public String toString() {
		return GsonX.gson.toJson(this);
	}
}
