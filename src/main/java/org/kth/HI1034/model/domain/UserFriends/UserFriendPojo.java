package org.kth.HI1034.model.domain.UserFriends;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.kth.HI1034.model.domain.post.FacePostPojo;
import org.kth.HI1034.util.GsonX;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserFriendPojo implements Serializable, Comparable<UserFriendPojo> {


    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String username;

    private List<FacePostPojo> myPost = new ArrayList<>();

    public UserFriendPojo() {
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<FacePostPojo> getMyPost() {
        return myPost;
    }

    public void setMyPost(List<FacePostPojo> myPost) {
        this.myPost = myPost;
    }



    @Override
    public int compareTo(UserFriendPojo o) {
        int thisObject = this.hashCode();
        long anotherEntity = o.hashCode();
        return (thisObject < anotherEntity ? -1 : (thisObject == anotherEntity ? 0 : 1));
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserFriendPojo that = (UserFriendPojo) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        return myPost != null ? myPost.equals(that.myPost) : that.myPost == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        return GsonX.gson.toJson(this);
    }
}
