package org.kth.HI1034.JWT;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.kth.HI1034.util.GsonX;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

@XmlRootElement
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenPojo implements Serializable, Comparable<TokenPojo>  {
	private String receiverKey;
	private String senderKey;
	private long keyCreationTime;
	private String subject;
	private String audience;
	private String issuer;
	private Map<String, Object> extendedInformationMap;

	private String token;






	public TokenPojo(	) {
		extendedInformationMap = new TreeMap<String , Object>();
	}

	public Map<String, Object> getExtendedInformationMap() {
		return extendedInformationMap;
	}

	public void setExtendedInformationMap(Map<String, Object> extendedInformationMap) {
		this.extendedInformationMap = extendedInformationMap;
	}

	public void putClaimsMap(String subject, Object data){
		extendedInformationMap.put(subject,data);
	}

	public String getSenderKey() {
		return senderKey;
	}

	public void setSenderKey(String theSenderKey) {
		this.senderKey = theSenderKey;
	}


	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public String getAudience() {
		return audience;
	}

	public void setAudience(String audience) {
		this.audience = audience;
	}

	public String getReceiverKey() {
		return receiverKey;
	}

	public void setReceiverKey(String receiverKey) {
		this.receiverKey = receiverKey;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}


	public long getKeyCreationTime() {
		return keyCreationTime;
	}

	public void setKeyCreationTime(long keyCreationTime) {
		this.keyCreationTime = keyCreationTime;
	}




	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		TokenPojo tokenPojo = (TokenPojo) o;

		if (keyCreationTime != tokenPojo.keyCreationTime) return false;
		if (receiverKey != null ? !receiverKey.equals(tokenPojo.receiverKey) : tokenPojo.receiverKey != null)
			return false;
		if (senderKey != null ? !senderKey.equals(tokenPojo.senderKey) : tokenPojo.senderKey != null)
			return false;
		if (subject != null ? !subject.equals(tokenPojo.subject) : tokenPojo.subject != null) return false;
		if (audience != null ? !audience.equals(tokenPojo.audience) : tokenPojo.audience != null) return false;
		if (issuer != null ? !issuer.equals(tokenPojo.issuer) : tokenPojo.issuer != null) return false;
		return token != null ? token.equals(tokenPojo.token) : tokenPojo.token == null;

	}

	@Override
	public int hashCode() {
		int result = receiverKey != null ? receiverKey.hashCode() : 0;
		result = 31 * result + (senderKey != null ? senderKey.hashCode() : 0);
		result = 31 * result + (int) (keyCreationTime ^ (keyCreationTime >>> 32));
		result = 31 * result + (subject != null ? subject.hashCode() : 0);
		result = 31 * result + (audience != null ? audience.hashCode() : 0);
		result = 31 * result + (issuer != null ? issuer.hashCode() : 0);
		result = 31 * result + (token != null ? token.hashCode() : 0);
		return result;
	}

	@Override
	public int compareTo(TokenPojo o) {
		int thisTime = this.hashCode();
		long anotherEntity = o.hashCode();
		return (thisTime < anotherEntity ? -1 : (thisTime == anotherEntity ? 0 : 1));
	}

	@Override
	public String toString() {

		if( extendedInformationMap != null && extendedInformationMap.isEmpty()){
			extendedInformationMap = null;
			String asJson = GsonX.gson.toJson(this);
			extendedInformationMap  = new TreeMap<String , Object>();

			return asJson;
		}

		return GsonX.gson.toJson(this);


	}


}
