//package org.kth.HI1034.model;
//
//import com.google.common.base.MoreObjects;
//import org.hibernate.validator.constraints.Length;
//import org.hibernate.validator.constraints.NotEmpty;
//import org.springframework.security.core.GrantedAuthority;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.ManyToMany;
//import javax.persistence.Table;
//import java.io.Serializable;
//import java.util.Collection;
//import java.util.LinkedHashSet;
//
//@Entity
//@Table(name = "authorities")
//public class Authority implements Serializable, GrantedAuthority {
//
//	public static final long serialVersionUID = 420947L;
//    public static final int MAX_LENGTH_AUTHORITY = 30;
//    public static final int MIN_LENGTH_AUTHORITY = 4;
//
//
//
//
//
//    public Authority() {
//        faceUsers = new LinkedHashSet<FaceUser>();
//    }
//
//    public Authority(String authority) {
//        this.authority = authority;
//    }
//
//
//
//
//    public Authority(Long roleId, String authority) {
//        this.id= roleId;
//        this.authority = authority;
//    }
//
//    public Long id;
//    @Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "id", unique = true, nullable = false)
//    public void setId(Long id) {
//        this.id = id;
//    }
//    public Long getId() {
//        return id;
//    }
//
//
//	public String authority;
//	@Override
//    @Column(unique=true)
//    @NotEmpty
//    @Length(min = Authority.MIN_LENGTH_AUTHORITY, max = Authority.MAX_LENGTH_AUTHORITY)
//    public String getAuthority() {
//        return authority;
//    }
//    public void setAuthority(String authority) {
//        this.authority = authority;
//    }
//
//
//
//	private boolean isLocked = false;
//	@Column(name = "is_locked")
//    public boolean isLocked() {
//        return isLocked;
//    }
//    public void setLocked(boolean locked) {
//        isLocked = locked;
//    }
//
//    public Collection<FaceUser> faceUsers;
//
//    @ManyToMany(mappedBy = "authorities")
//    public Collection<FaceUser> getAuthorities() {
//        return faceUsers;
//    }
//    public void setAuthorities(Collection<FaceUser> faceUsers) {
//        this.faceUsers = faceUsers;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        Authority authority1 = (Authority) o;
//
//        if (isLocked != authority1.isLocked) return false;
//        if (authority != null ? !authority.equals(authority1.authority) : authority1.authority != null) return false;
//        return id != null ? id.equals(authority1.id) : authority1.id == null;
//
//    }
//
//    @Override
//    public int hashCode() {
//        int result = authority != null ? authority.hashCode() : 0;
//        result = 31 * result + (id != null ? id.hashCode() : 0);
//        result = 31 * result + (isLocked ? 1 : 0);
//        return result;
//    }
//
//
//
//    @Override
//    public String toString() {
//        return MoreObjects.toStringHelper(this)
//                .add("authority", authority)
//                .toString();
//    }
//
//
//
//}
