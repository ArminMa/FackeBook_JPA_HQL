package org.kth.HI1034.security;


import com.google.gson.Gson;
import org.jose4j.jwk.EllipticCurveJsonWebKey;
import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.PublicJsonWebKey;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.lang.JoseException;
import org.kth.HI1034.security.util.ciperUtil.JsonWebKeyUtil;
import org.kth.HI1034.security.util.ciperUtil.KeyUtil;

import javax.crypto.SecretKey;
import java.security.Key;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.UUID;

public class ApiKeyFactory {

	//todo varje användare ska få en ny App nyckel; appen själv kan få ha kvar sin nyckel som är definerad i
	//konfig fillen.



	private static  KeyPair keyPair;
//	private static final PrivateKey privateKey = keyPair.getPrivate();
//	private static final PublicKey publicKey = keyPair.getPublic();
	private static RsaJsonWebKey rsaWebKey ;
	private static SecretKey symmetricKey ;
	private static EllipticCurveJsonWebKey ellipticJsonWebKey;
	private Gson gson = new Gson();

	public  ApiKeyFactory()  {
		keyPair = KeyUtil.generateKeyPair();
		rsaWebKey = getJwkPair(keyPair);
		symmetricKey =  KeyUtil.generateSymmetricKey();
		ellipticJsonWebKey = JsonWebKeyUtil.generateEllipticWebKey();
	}


	public static EllipticCurveJsonWebKey getEllipticJsonWebKey() {
		return ellipticJsonWebKey;
	}



	public AppKey getpublicKeyAsPojo( ){
		return getjsonWebKeyPojo(rsaWebKey.toJson(JsonWebKey.OutputControlLevel.PUBLIC_ONLY));
	}

	public AppKey getPrivateKeyAsPojo( ){
		return getjsonWebKeyPojo(rsaWebKey.toJson(JsonWebKey.OutputControlLevel.INCLUDE_PRIVATE));
	}

	public AppKey getSymmetricKeyAsPojo(){
		return getjsonWebKeyPojo(rsaWebKey.toJson(JsonWebKey.OutputControlLevel.INCLUDE_SYMMETRIC));
	}



	public AppKey getjsonWebKeyPojo(String jsonWebKey){
		return gson.fromJson(jsonWebKey, AppKey.class);
	}

	public RsaJsonWebKey getJsonWebKey(){

		rsaWebKey.setAlgorithm("RSA");
//			sign_jwk.setUse("sig");
		rsaWebKey.setKeyId("123452");

		return rsaWebKey;
	}

	public PrivateKey getPrivateKey() {
		return keyPair.getPrivate();
	}

	public PublicKey getPublicKey() {

		return keyPair.getPublic();
	}


	public Key getSymmetricKey() {
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

	public static RsaJsonWebKey getPublicJwk(PublicKey publicKey) throws JoseException {


			RsaJsonWebKey rsaJwk = (RsaJsonWebKey) PublicJsonWebKey.Factory.newPublicJwk(publicKey);
			if(rsaJwk.getKeyId() != null || rsaJwk.getKeyId().isEmpty()){
				rsaJwk.setKeyId(UUID.randomUUID().toString());
			}

			return rsaJwk;

	}



}
