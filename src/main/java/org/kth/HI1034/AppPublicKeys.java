package org.kth.HI1034;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.kth.HI1034.util.GsonX;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;


@XmlRootElement
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppPublicKeys implements Serializable, Comparable<AppPublicKeys> {

	private String publicKey;
	private String publicRsaWebKeyAsJson;
	private String publicEllipticWebKeyAsJson;

	/**
	 * @param rsaJsonWebKey String
	 * @param ellipticCurveJsonWebKey String
	 */
	public AppPublicKeys(
			String rsaJsonWebKey,
			String ellipticCurveJsonWebKey,
			String publicKey ) {
		this.publicRsaWebKeyAsJson = rsaJsonWebKey;
		this.publicEllipticWebKeyAsJson = ellipticCurveJsonWebKey;
		this.publicKey = publicKey;

	}

	public String getPublicRsaWebKeyAsJson() {
		return publicRsaWebKeyAsJson;
	}

	public void setPublicRsaWebKeyAsJson(String publicRsaWebKeyAsJson) {
		this.publicRsaWebKeyAsJson = publicRsaWebKeyAsJson;
	}

	public String getPublicEllipticWebKeyAsJson() {
		return publicEllipticWebKeyAsJson;
	}

	public void setPublicEllipticWebKeyAsJson(String publicEllipticWebKeyAsJson) {
		this.publicEllipticWebKeyAsJson = publicEllipticWebKeyAsJson;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	@Override
	public int compareTo(AppPublicKeys o) {
		int thisTime = this.hashCode();
		long anotherEntity = o.hashCode();
		return (thisTime < anotherEntity ? -1 : (thisTime == anotherEntity ? 0 : 1));
	}

	@Override
	public String toString() {
		return GsonX.gson.toJson(this);
	}

}
