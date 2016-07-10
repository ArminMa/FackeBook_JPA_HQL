package org.kth.HI1034.model;

import org.hibernate.annotations.SortNatural;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.kth.HI1034.model.entity.user.FaceUser;
import org.kth.HI1034.util.enums.Role;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.SortedSet;
import java.util.TreeSet;

;

@Entity
@Table(name = "authorities")
public class Authority implements Serializable, GrantedAuthority, Comparable<Authority> {


	public static final long serialVersionUID = 420947L;
    public static final int MAX_LENGTH_AUTHORITY = 30;
    public static final int MIN_LENGTH_AUTHORITY = 4;





    public Authority() {

    }

    public Authority(String authority) {
        this.authority = authority;
    }




    public Authority(Long roleId, String authority) {
        this.id= roleId;
        this.authority = authority;
    }



    public Long id;
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    private Role role;
    @Enumerated(EnumType.STRING)
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String authority;
	@Override
    @Column(unique=true)
    @NotEmpty
    @Length(min = Authority.MIN_LENGTH_AUTHORITY, max = Authority.MAX_LENGTH_AUTHORITY)
    public String getAuthority() {
        return authority;
    }
    public void setAuthority(String authority) {
        this.authority = authority;
    }



	private boolean isLocked = false;
	@Column(name = "is_locked")
    public boolean isLocked() {
        return isLocked;
    }
    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public SortedSet<FaceUser> usersAuthorities = new TreeSet<>();
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_authorities",
            joinColumns = @JoinColumn(name = "authority", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    @SortNatural
    public SortedSet<FaceUser> getUsersAuthorities() {
        return usersAuthorities;
    }

    public void setUsersAuthorities(SortedSet<FaceUser> usersAuthorities) {
        this.usersAuthorities = usersAuthorities;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Authority authority1 = (Authority) o;

        if (isLocked != authority1.isLocked) return false;
        if (authority != null ? !authority.equals(authority1.authority) : authority1.authority != null) return false;
        return id != null ? id.equals(authority1.id) : authority1.id == null;

    }

    @Override
    public int hashCode() {
        int result = authority != null ? authority.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (isLocked ? 1 : 0);
        return result;
    }

    @Override
    public int compareTo(Authority o) {
        int thisTime = this.hashCode();
        long anotherEntity = o.hashCode();
        return (thisTime < anotherEntity ? -1 : (thisTime == anotherEntity ? 0 : 1));
    }


}
