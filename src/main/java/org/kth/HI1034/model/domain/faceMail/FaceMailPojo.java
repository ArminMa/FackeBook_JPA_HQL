package org.kth.HI1034.model.domain.faceMail;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.MoreObjects;
import org.kth.HI1034.util.GsonX;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FaceMailPojo implements Serializable, Comparable<FaceMailPojo> {
  private Long id;
  private String header;
  private String mailText;

	public FaceMailPojo() {
	}

	public FaceMailPojo(Long id, String header, String mailText) {
		this.id = id;
		this.header = header;
		this.mailText = mailText;
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

  public String getMailText() {
    return mailText;
  }

  public void setMailText(String mailText) {
    this.mailText = mailText;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    FaceMailPojo faceMail = (FaceMailPojo) o;

    if (id != null ? !id.equals(faceMail.id) : faceMail.id != null) return false;
    if (header != null ? !header.equals(faceMail.header) : faceMail.header != null) return false;
    return mailText != null ? mailText.equals(faceMail.mailText) : faceMail.mailText == null;

  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (header != null ? header.hashCode() : 0);
    result = 31 * result + (mailText != null ? mailText.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return GsonX.gson.toJson(this);
  }





  @Override
  public int compareTo(FaceMailPojo o) {
    int thisObject = this.hashCode();
    long anotherEntity = o.hashCode();
    return (thisObject < anotherEntity ? -1 : (thisObject == anotherEntity ? 0 : 1));
  }

}
