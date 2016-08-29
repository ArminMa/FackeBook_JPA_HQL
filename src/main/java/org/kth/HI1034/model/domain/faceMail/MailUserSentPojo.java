package org.kth.HI1034.model.domain.faceMail;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.kth.HI1034.model.domain.UserFriends.UserFriendPojo;
import org.kth.HI1034.util.GsonX;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/*
 * POJO stands for Plain Old Java Object, and would be used to describe the same things as a
 * "Normal Class" whereas a JavaBean follows a set of rules.
 *
 */
@XmlRootElement
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MailUserSentPojo implements Serializable, Comparable<MailUserSentPojo> {
  private Long id;
  private java.sql.Date sent_date;
  private Long user_id;
  private Long mail_id;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public java.sql.Date getSent_date() {
    return sent_date;
  }

  public void setSent_date(java.sql.Date sent_date) {
    this.sent_date = sent_date;
  }

  public Long getUser_id() {
    return user_id;
  }

  public void setUser_id(Long user_id) {
    this.user_id = user_id;
  }

  public Long getMail_id() {
    return mail_id;
  }

  public void setMail_id(Long mail_id) {
    this.mail_id = mail_id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    MailUserSentPojo that = (MailUserSentPojo) o;

    if (id != null ? !id.equals(that.id) : that.id != null) return false;
    if (sent_date != null ? !sent_date.equals(that.sent_date) : that.sent_date != null) return false;
    if (user_id != null ? !user_id.equals(that.user_id) : that.user_id != null) return false;
    return mail_id != null ? mail_id.equals(that.mail_id) : that.mail_id == null;

  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (sent_date != null ? sent_date.hashCode() : 0);
    result = 31 * result + (user_id != null ? user_id.hashCode() : 0);
    result = 31 * result + (mail_id != null ? mail_id.hashCode() : 0);
    return result;
  }

  @Override
  public int compareTo(MailUserSentPojo o) {
    int thisObject = this.hashCode();
    long anotherEntity = o.hashCode();
    return (thisObject < anotherEntity ? -1 : (thisObject == anotherEntity ? 0 : 1));
  }


  @Override
  public String toString() {
    return GsonX.gson.toJson(this);
  }
}
