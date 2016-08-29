package org.kth.HI1034.model.domain.post;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.kth.HI1034.util.GsonX;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDetachedPojo implements java.io.Serializable, Comparable<UserDetachedPojo> {

    private Long id;
    private String email;
    private Boolean accountRemoved = false;











    public UserDetachedPojo() {
    }

    public UserDetachedPojo(String email) {
        this.email = email;
        this.accountRemoved = false;
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


    public boolean getAccountRemoved() {
        return accountRemoved;
    }

    public void setAccountRemoved(boolean accountRemoved) {
        this.accountRemoved = accountRemoved;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDetachedPojo that = (UserDetachedPojo) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        return accountRemoved != null ? accountRemoved.equals(that.accountRemoved) : that.accountRemoved == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (accountRemoved != null ? accountRemoved.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(UserDetachedPojo o) {
        int thisTime = this.hashCode();
        long anotherEntity = o.hashCode();
        return (thisTime < anotherEntity ? -1 : (thisTime == anotherEntity ? 0 : 1));
    }



    @Override
    public String toString() {

        return GsonX.gson.toJson(this);
    }


}
