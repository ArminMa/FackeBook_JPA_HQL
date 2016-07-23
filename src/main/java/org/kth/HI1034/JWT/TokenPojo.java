package org.kth.HI1034.JWT;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.Gson;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

@XmlRootElement
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenPojo implements Serializable, Comparable<TokenPojo>  {
	private String receiverJwk;
	private String senderJwk;
	private long keyCreationTime;
	private String subject;
	private String audience;
	private String Issuer;
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

	public String getSenderJwk() {
		return senderJwk;
	}

	public void setSenderJwk(String senderJwk) {
		this.senderJwk = senderJwk;
	}

	public String getIssuer() {
		return Issuer;
	}

	public void setIssuer(String issuer) {
		Issuer = issuer;
	}

	public String getAudience() {
		return audience;
	}

	public void setAudience(String audience) {
		this.audience = audience;
	}

	public String getReceiverJwk() {
		return receiverJwk;
	}

	public void setReceiverJwk(String receiverJwk) {
		this.receiverJwk = receiverJwk;
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
		if (receiverJwk != null ? !receiverJwk.equals(tokenPojo.receiverJwk) : tokenPojo.receiverJwk != null)
			return false;
		if (senderJwk != null ? !senderJwk.equals(tokenPojo.senderJwk) : tokenPojo.senderJwk != null)
			return false;
		if (subject != null ? !subject.equals(tokenPojo.subject) : tokenPojo.subject != null) return false;
		if (audience != null ? !audience.equals(tokenPojo.audience) : tokenPojo.audience != null) return false;
		if (Issuer != null ? !Issuer.equals(tokenPojo.Issuer) : tokenPojo.Issuer != null) return false;
		return token != null ? token.equals(tokenPojo.token) : tokenPojo.token == null;

	}

	@Override
	public int hashCode() {
		int result = receiverJwk != null ? receiverJwk.hashCode() : 0;
		result = 31 * result + (senderJwk != null ? senderJwk.hashCode() : 0);
		result = 31 * result + (int) (keyCreationTime ^ (keyCreationTime >>> 32));
		result = 31 * result + (subject != null ? subject.hashCode() : 0);
		result = 31 * result + (audience != null ? audience.hashCode() : 0);
		result = 31 * result + (Issuer != null ? Issuer.hashCode() : 0);
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
		Gson gson = new Gson();
		if( extendedInformationMap.isEmpty()){
			extendedInformationMap = null;
			String asJson = gson.toJson(this);
			extendedInformationMap  = new TreeMap<String , Object>();

			return asJson;
		}

		return gson.toJson(this);


	}


}
