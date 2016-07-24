package org.kth.HI1034;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.kth.HI1034.util.GsonX;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;


@XmlRootElement
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppPublicKeys implements Serializable, Comparable<AppPublicKeys> {

	private String publicRsaWebKeyAsJson;
	private String publicEllipticWebKeyAsJson;

	/**
	 * @param rsaJsonWebKey String
	 * @param ellipticCurveJsonWebKey String
	 */
	public AppPublicKeys(String rsaJsonWebKey, String ellipticCurveJsonWebKey) {
		this.publicRsaWebKeyAsJson = rsaJsonWebKey;
		this.publicEllipticWebKeyAsJson = ellipticCurveJsonWebKey;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		AppPublicKeys that = (AppPublicKeys) o;

		if (publicRsaWebKeyAsJson != null ? !publicRsaWebKeyAsJson.equals(that.publicRsaWebKeyAsJson) : that.publicRsaWebKeyAsJson != null) return false;
		return publicEllipticWebKeyAsJson != null ? publicEllipticWebKeyAsJson.equals(that.publicEllipticWebKeyAsJson) : that.publicEllipticWebKeyAsJson == null;

	}

	@Override
	public int hashCode() {
		int result = publicRsaWebKeyAsJson != null ? publicRsaWebKeyAsJson.hashCode() : 0;
		result = 31 * result + (publicEllipticWebKeyAsJson != null ? publicEllipticWebKeyAsJson.hashCode() : 0);
		return result;
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
