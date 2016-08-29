package org.kth.HI1034.model.domain.faceMail;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.kth.HI1034.util.GsonX;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MailUserReceivedPojo implements Serializable, Comparable<MailUserReceivedPojo> {
  private Long id;
  private String message_read;
  private java.sql.Date received_date;
  private Long mail_id;
  private Long user_id;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getMessage_read() {
    return message_read;
  }

  public void setMessage_read(String message_read) {
    this.message_read = message_read;
  }

  public java.sql.Date getReceived_date() {
    return received_date;
  }

  public void setReceived_date(java.sql.Date received_date) {
    this.received_date = received_date;
  }

  public Long getMail_id() {
    return mail_id;
  }

  public void setMail_id(Long mail_id) {
    this.mail_id = mail_id;
  }

  public Long getUser_id() {
    return user_id;
  }

  public void setUser_id(Long user_id) {
    this.user_id = user_id;
  }

  @Override
  public int compareTo(MailUserReceivedPojo o) {
    int thisObject = this.hashCode();
    long anotherEntity = o.hashCode();
    return (thisObject < anotherEntity ? -1 : (thisObject == anotherEntity ? 0 : 1));
  }


  @Override
  public String toString() {
    return GsonX.gson.toJson(this);
  }
}
