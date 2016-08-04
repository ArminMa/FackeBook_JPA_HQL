package org.kth.HI1034;


import org.jose4j.jwk.EllipticCurveJsonWebKey;
import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.PublicJsonWebKey;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.lang.JoseException;
import org.kth.HI1034.JWT.TokenJose4jUtils;
import org.kth.HI1034.security.util.KeyUtil;

import javax.crypto.SecretKey;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.UUID;

public class AppKeyFactory {

	private static  KeyPair keyPair;
	private static RsaJsonWebKey rsaWebKey ;
	private static String publicRsaWebKeyAsJson;
	private static SecretKey symmetricKey ;
	private static EllipticCurveJsonWebKey ellipticJsonWebKey;
	private static String publicEllipticWebKeyAsJson;

	public AppKeyFactory()  {
		try {
			keyPair = KeyUtil.generateKeyPair();
			ellipticJsonWebKey = TokenJose4jUtils.JsonWebKeyUtil.generateEllipticWebKey();
			rsaWebKey = getJwkPair(keyPair);
		} catch (GeneralSecurityException | JoseException e) {
			e.printStackTrace();
		}

		symmetricKey =  KeyUtil.SymmetricKey.generateSecretAesKey(16);

		assert rsaWebKey != null;
		publicRsaWebKeyAsJson = rsaWebKey.toJson(JsonWebKey.OutputControlLevel.PUBLIC_ONLY);
		publicEllipticWebKeyAsJson = ellipticJsonWebKey.toJson(JsonWebKey.OutputControlLevel.PUBLIC_ONLY);




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

	public static KeyPair getKeyPair(){
		return keyPair;
	}


	public static Key getSymmetricKey() {
		return symmetricKey;
	}

	public static RsaJsonWebKey getJwkPair(KeyPair keyPair) throws JoseException {
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
			throw new JoseException(e.getMessage(), e.getCause());
		}



	}

	public static RsaJsonWebKey getPublicRsaJwk(PublicKey publicKey) throws JoseException {
			RsaJsonWebKey rsaJwk = (RsaJsonWebKey) PublicJsonWebKey.Factory.newPublicJwk(publicKey);
			if(rsaJwk.getKeyId() != null || rsaJwk.getKeyId().isEmpty()){
				rsaJwk.setKeyId(UUID.randomUUID().toString());
			}

			return rsaJwk;

	}



}
