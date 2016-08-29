package org.kth.HI1034.model.domain.faceMail.userReceivedMail;


import com.fasterxml.jackson.annotation.JsonInclude;
import org.kth.HI1034.model.domain.faceMail.FaceMail;
import org.kth.HI1034.model.domain.faceMail.FaceMailPojo;
import org.kth.HI1034.model.domain.faceMail.MailUserReceivedPojo;
import org.kth.HI1034.model.domain.user.FaceUser;
import org.kth.HI1034.model.domain.user.FaceUserPojo;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;


@XmlRootElement
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserReceivedMailPojo implements Serializable, Comparable<UserReceivedMailPojo> {

    private Long id;
    private Boolean message_read = false;
    private Date receivedDate;
    private FaceMailPojo receivedMail;
    private FaceUserPojo receivingUser;
    private FaceUserPojo author;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getMessage_read() {
        return message_read;
    }

    public void setMessage_read(Boolean message_read) {
        this.message_read = message_read;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    public FaceMailPojo getReceivedMail() {
        return receivedMail;
    }

    public void setReceivedMail(FaceMailPojo receivedMail) {
        this.receivedMail = receivedMail;
    }

    public FaceUserPojo getReceivingUser() {
        return receivingUser;
    }

    public void setReceivingUser(FaceUserPojo receivingUser) {
        this.receivingUser = receivingUser;
    }

    public FaceUserPojo getAuthor() {
        return author;
    }

    public void setAuthor(FaceUserPojo author) {
        this.author = author;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserReceivedMailPojo that = (UserReceivedMailPojo) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (message_read != null ? !message_read.equals(that.message_read) : that.message_read != null) return false;
        if (receivedDate != null ? !receivedDate.equals(that.receivedDate) : that.receivedDate != null) return false;
        if (receivedMail != null ? !receivedMail.equals(that.receivedMail) : that.receivedMail != null) return false;
        if (receivingUser != null ? !receivingUser.equals(that.receivingUser) : that.receivingUser != null)
            return false;
        return author != null ? author.equals(that.author) : that.author == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 7 * result + (message_read != null ? message_read.hashCode() : 0);
        result = 13 * result + (receivedDate != null ? receivedDate.hashCode() : 0);
        result = 7 * result + (receivedMail != null ? receivedMail.hashCode() : 0);
        result = 7 * result + (receivingUser != null ? receivingUser.hashCode() : 0);
        result = 7 * result + (author != null ? author.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(UserReceivedMailPojo o) {
        int thisObject = this.hashCode();
        long anotherEntity = o.hashCode();
        return (thisObject < anotherEntity ? -1 : (thisObject == anotherEntity ? 0 : 1));
    }
}
