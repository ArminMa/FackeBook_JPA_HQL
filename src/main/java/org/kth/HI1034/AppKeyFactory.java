package org.kth.HI1034;


import org.jose4j.jwk.EllipticCurveJsonWebKey;
import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.PublicJsonWebKey;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.lang.JoseException;
import org.kth.HI1034.security.AppKey;
import org.kth.HI1034.security.util.ciperUtil.JsonWebKeyUtil;
import org.kth.HI1034.security.util.ciperUtil.KeyUtil;
import org.kth.HI1034.util.GsonX;

import javax.crypto.SecretKey;
import java.security.Key;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.UUID;

public class AppKeyFactory {

	//todo varje användare ska få en ny App nyckel; appen själv kan få ha kvar sin nyckel som är definerad i
	//konfig fillen.



	private static  KeyPair keyPair;
//	private static final PrivateKey privateKey = keyPair.getPrivate();
//	private static final PublicKey publicKey = keyPair.getPublic();
	private static RsaJsonWebKey rsaWebKey ;
	private static String publicRsaWebKeyAsJson;
	private static SecretKey symmetricKey ;
	private static EllipticCurveJsonWebKey ellipticJsonWebKey;
	private static String publicEllipticWebKeyAsJson;

	public AppKeyFactory()  {
		keyPair = KeyUtil.generateKeyPair();
		rsaWebKey = getJwkPair(keyPair);
		publicRsaWebKeyAsJson = rsaWebKey.toJson(JsonWebKey.OutputControlLevel.PUBLIC_ONLY);

		symmetricKey =  KeyUtil.generateSymmetricKey();
		ellipticJsonWebKey = JsonWebKeyUtil.generateEllipticWebKey();
		publicEllipticWebKeyAsJson = ellipticJsonWebKey.toJson(JsonWebKey.OutputControlLevel.PUBLIC_ONLY);

	}






	public AppKey getpublicKeyAsAppKeyPojo( ){
		return getjsonWebKeyPojo(rsaWebKey.toJson(JsonWebKey.OutputControlLevel.PUBLIC_ONLY));
	}

	public AppKey getPrivateKeyAsAppKeyPojo( ){
		return getjsonWebKeyPojo(rsaWebKey.toJson(JsonWebKey.OutputControlLevel.INCLUDE_PRIVATE));
	}

	public AppKey getSymmetricKeyAsAppKeyPojo(){
		return getjsonWebKeyPojo(rsaWebKey.toJson(JsonWebKey.OutputControlLevel.INCLUDE_SYMMETRIC));
	}



	public AppKey getjsonWebKeyPojo(String jsonWebKey){
		return GsonX.gson.fromJson(jsonWebKey, AppKey.class);
	}

	public static RsaJsonWebKey getRsaWebKey(){

		rsaWebKey.setAlgorithm("RSA");
//			sign_jwk.setUse("sig");
		rsaWebKey.setKeyId("123452");

		return rsaWebKey;
	}

	public static EllipticCurveJsonWebKey getEllipticWebKey() {
		return ellipticJsonWebKey;
	}

	public static String getpublicRsaWebKeyAsJson(){
		return publicRsaWebKeyAsJson;
	}

	public static String getpublicEllipticWebKeyAsJson(){
		return publicEllipticWebKeyAsJson;
	}

	public static PrivateKey getPrivateKey() {
		return keyPair.getPrivate();
	}

	public static PublicKey getPublicKey() {

		return keyPair.getPublic();
	}


	public static Key getSymmetricKey() {
		return symmetricKey;
	}

	public static RsaJsonWebKey getJwkPair(KeyPair keyPair)  {
		try {
			RsaJsonWebKey rsaJwk = (RsaJsonWebKey) PublicJsonWebKey.Factory.newPublicJwk(keyPair.getPublic());
			rsaJwk.setPrivateKey(keyPair.getPrivate());
			if(rsaJwk.getKeyId() == null || rsaJwk.getKeyId().isEmpty()){
				rsaJwk.setKeyId(UUID.randomUUID().toString());
			}

			rsaJwk.setAlgorithm("RSA");
//			sign_jwk.setUse("sig");
			rsaJwk.setKeyId("123452");

			return rsaJwk;
		} catch (JoseException e) {
			e.printStackTrace();
		}

		return null;

	}

	public static RsaJsonWebKey getPublicRsaJwk(PublicKey publicKey) throws JoseException {


			RsaJsonWebKey rsaJwk = (RsaJsonWebKey) PublicJsonWebKey.Factory.newPublicJwk(publicKey);
			if(rsaJwk.getKeyId() != null || rsaJwk.getKeyId().isEmpty()){
				rsaJwk.setKeyId(UUID.randomUUID().toString());
			}

			return rsaJwk;

	}



}
