package org.kth.HI1034.model.domain.authority;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.SortNatural;
import org.kth.HI1034.util.GsonX;
import org.kth.HI1034.util.enums.Role;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.SortedSet;
import java.util.TreeSet;

@XmlRootElement
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "authorities")
public class Authority implements Serializable, Comparable<Authority> {


	private Long id = 1L;
	private String userRole;


    public Authority() {

    }

    public Authority(Role theRoles) {
        this.userRole = theRoles.toString();
        this.id = (long) (theRoles.ordinal()+1);

    }




    @Id
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }


	@Column(name = "user_role")
    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String role) {
        this.userRole = role;
    }


    private SortedSet<UserAuthority> authorities = new TreeSet<>();
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "pk.authority")
    @BatchSize(size=50)
//    @LazyCollection(LazyCollectionOption.FALSE)
    @SortNatural
    public SortedSet<UserAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(SortedSet<UserAuthority> authorities) {
        this.authorities = authorities;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Authority authority = (Authority) o;


        if (id != null ? !id.equals(authority.id) : authority.id != null) return false;
        return userRole == authority.userRole;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (userRole != null ? userRole.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Authority o) {
        int thisTime = this.hashCode();
        long anotherEntity = o.hashCode();
        return (thisTime < anotherEntity ? -1 : (thisTime == anotherEntity ? 0 : 1));
    }


    @Override
    public String toString() {

       if(getAuthorities() != null){
           SortedSet<UserAuthority> authorities = getAuthorities();
           setAuthorities(null);
           String thisJsonString = GsonX.gson.toJson(this);
           setAuthorities(authorities);
           return thisJsonString;
       }

        return GsonX.gson.toJson(this);

    }


}
