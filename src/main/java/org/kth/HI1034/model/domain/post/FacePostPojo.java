package org.kth.HI1034.model.domain.post;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.kth.HI1034.model.domain.user.FaceUserPojo;
import org.kth.HI1034.util.GsonX;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;

@XmlRootElement
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FacePostPojo implements Serializable,Comparable<FacePostPojo>{

	public FacePostPojo() {
//		pk = new FacePostPk();
	}



	private Long id;
	private UserDetachedPojo author;
	private SortedSet<UserDetachedPojo> receivers = new TreeSet<>();
	private String postText;
	private Date sentDate;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}


    public UserDetachedPojo getAuthor() {
        return author;
    }

    public void setAuthor(UserDetachedPojo author) {
        this.author = author;
    }

    public SortedSet<UserDetachedPojo> getReceivers() {
        return receivers;
    }

    public void setReceivers(SortedSet<UserDetachedPojo> receivers) {
        this.receivers = receivers;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    @Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		FacePostPojo that = (FacePostPojo) o;

		if (id != null ? !id.equals(that.id) : that.id != null) return false;
		if (author != null ? !author.equals(that.author) : that.author != null)
			return false;
		if (postText != null ? !postText.equals(that.postText) : that.postText != null) return false;
		return sentDate != null ? sentDate.equals(that.sentDate) : that.sentDate == null;

	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (author != null ? author.hashCode() : 0);
		result = 31 * result + (postText != null ? postText.hashCode() : 0);
		result = 31 * result + (sentDate != null ? sentDate.hashCode() : 0);
		return result;
	}

	@Override
	public int compareTo(FacePostPojo o) {
		int thisObject = this.hashCode();
		long anotherEntity = o.hashCode();
		return (thisObject<anotherEntity ? -1 : (thisObject==anotherEntity ? 0 : 1));
	}

	@Override
	public String toString() {

		if(receivers.isEmpty()){
			receivers = null;
		}

		String toString  = GsonX.gson.toJson(this);

		receivers =  new TreeSet<>();

		return toString;
	}

}
