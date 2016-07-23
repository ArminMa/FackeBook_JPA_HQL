package org.kth.HI1034.security;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.Gson;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RsaJsonWebKeyPojo implements Serializable,  Comparable<RsaJsonWebKeyPojo>{

	private String alg;             // max 10 characters
	private String n;               //max 350 characters 350 bytes
	private String d;           	//max 350 characters
	private String e;


	private String p;               //max 200 characters 200 bytes
	private String q;
	private String dp;
	private String dq;
	private String qi;
	private String kty;
	private String use;
	private String kid;



	public RsaJsonWebKeyPojo() {
	}

	public String getAlg() {
		return alg;
	}

	public void setAlg(String alg) {
		this.alg = alg;
	}

	public String getD() {
		return d;
	}

	public void setD(String d) {
		this.d = d;
	}

	public String getE() {
		return e;
	}

	public void setE(String e) {
		this.e = e;
	}

	public String getN() {
		return n;
	}

	public void setN(String n) {
		this.n = n;
	}

	public String getKty() {
		return kty;
	}

	public void setKty(String kty) {
		this.kty = kty;
	}

	public String getUse() {
		return use;
	}

	public void setUse(String use) {
		this.use = use;
	}

	public String getKid() {
		return kid;
	}


	public void setKid(String kid) {
		this.kid = kid;
	}

	public String getP() {
		return p;
	}

	public void setP(String p) {
		this.p = p;
	}

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

	public String getDp() {
		return dp;
	}

	public void setDp(String dp) {
		this.dp = dp;
	}

	public String getDq() {
		return dq;
	}

	public void setDq(String dq) {
		this.dq = dq;
	}

	public String getQi() {
		return qi;
	}

	public void setQi(String qi) {
		this.qi = qi;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		RsaJsonWebKeyPojo appKey = (RsaJsonWebKeyPojo) o;

		if (alg != null ? !alg.equals(appKey.alg) : appKey.alg != null) return false;
		if (d != null ? !d.equals(appKey.d) : appKey.d != null) return false;
		if (e != null ? !e.equals(appKey.e) : appKey.e != null) return false;
		if (n != null ? !n.equals(appKey.n) : appKey.n != null) return false;
		if (p != null ? !p.equals(appKey.p) : appKey.p != null) return false;
		if (q != null ? !q.equals(appKey.q) : appKey.q != null) return false;
		if (dp != null ? !dp.equals(appKey.dp) : appKey.dp != null) return false;
		if (dq != null ? !dq.equals(appKey.dq) : appKey.dq != null) return false;
		if (qi != null ? !qi.equals(appKey.qi) : appKey.qi != null) return false;
		if (kty != null ? !kty.equals(appKey.kty) : appKey.kty != null) return false;
		if (use != null ? !use.equals(appKey.use) : appKey.use != null) return false;
		return kid != null ? kid.equals(appKey.kid) : appKey.kid == null;

	}

	@Override
	public int hashCode() {
		int result = alg != null ? alg.hashCode() : 0;
		result = 31 * result + (d != null ? d.hashCode() : 0);
		result = 31 * result + (e != null ? e.hashCode() : 0);
		result = 31 * result + (n != null ? n.hashCode() : 0);
		result = 31 * result + (p != null ? p.hashCode() : 0);
		result = 31 * result + (q != null ? q.hashCode() : 0);
		result = 31 * result + (dp != null ? dp.hashCode() : 0);
		result = 31 * result + (dq != null ? dq.hashCode() : 0);
		result = 31 * result + (qi != null ? qi.hashCode() : 0);
		result = 31 * result + (kty != null ? kty.hashCode() : 0);
		result = 31 * result + (use != null ? use.hashCode() : 0);
		result = 31 * result + (kid != null ? kid.hashCode() : 0);
		return result;
	}

	@Override
	public int compareTo(RsaJsonWebKeyPojo o) {
		int thisTime = this.hashCode();
		long anotherEntity = o.hashCode();
		return (thisTime < anotherEntity ? -1 : (thisTime == anotherEntity ? 0 : 1));
	}

	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}



}