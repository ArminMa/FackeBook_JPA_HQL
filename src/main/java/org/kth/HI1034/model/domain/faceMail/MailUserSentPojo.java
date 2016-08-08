package org.kth.HI1034.model.domain.faceMail;

/*
 * POJO stands for Plain Old Java Object, and would be used to describe the same things as a
 * "Normal Class" whereas a JavaBean follows a set of rules.
 *
 */

public class MailUserSentPojo {
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
}
