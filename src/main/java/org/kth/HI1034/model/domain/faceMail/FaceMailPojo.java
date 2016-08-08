package org.kth.HI1034.model.domain.faceMail;

import com.google.common.base.MoreObjects;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FaceMailPojo {
  private Long id;
  private String header;
  private String mail_text;

	public FaceMailPojo() {
	}

	public FaceMailPojo(Long id, String header, String mail_text) {
		this.id = id;
		this.header = header;
		this.mail_text = mail_text;
	}

	public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getHeader() {
    return header;
  }

  public void setHeader(String header) {
    this.header = header;
  }

  public String getMail_text() {
    return mail_text;
  }

  public void setMail_text(String mail_text) {
    this.mail_text = mail_text;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    FaceMailPojo faceMail = (FaceMailPojo) o;

    if (id != null ? !id.equals(faceMail.id) : faceMail.id != null) return false;
    if (header != null ? !header.equals(faceMail.header) : faceMail.header != null) return false;
    return mail_text != null ? mail_text.equals(faceMail.mail_text) : faceMail.mail_text == null;

  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (header != null ? header.hashCode() : 0);
    result = 31 * result + (mail_text != null ? mail_text.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
            .add("id", id)
            .add("header", header)
            .add("mailText", mail_text)
            .toString();
  }
}
