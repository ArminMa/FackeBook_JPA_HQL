package org.kth.HI1034.model.pojo;

import java.util.ArrayList;
import java.util.List;

public class FaceuserPojo {
  private Long id;
  private String account_expired;
  private String account_locked;
  private String credentials_expired;
  private String email;
  private String enabled;
  private String first_name;
  private String last_name;
  private String password;
  private String username;

  private List<FaceMailPojo> sentMail = new ArrayList<FaceMailPojo>();

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getAccount_expired() {
    return account_expired;
  }

  public void setAccount_expired(String account_expired) {
    this.account_expired = account_expired;
  }

  public String getAccount_locked() {
    return account_locked;
  }

  public void setAccount_locked(String account_locked) {
    this.account_locked = account_locked;
  }

  public String getCredentials_expired() {
    return credentials_expired;
  }

  public void setCredentials_expired(String credentials_expired) {
    this.credentials_expired = credentials_expired;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getEnabled() {
    return enabled;
  }

  public void setEnabled(String enabled) {
    this.enabled = enabled;
  }

  public String getFirst_name() {
    return first_name;
  }

  public void setFirst_name(String first_name) {
    this.first_name = first_name;
  }

  public String getLast_name() {
    return last_name;
  }

  public void setLast_name(String last_name) {
    this.last_name = last_name;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
