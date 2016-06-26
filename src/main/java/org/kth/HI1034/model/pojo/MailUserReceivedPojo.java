package org.kth.HI1034.model.pojo;

public class MailUserReceivedPojo {
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
}
