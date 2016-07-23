package org.kth.HI1034.security.util.ciperUtil;

import org.jose4j.jwk.EcJwkGenerator;
import org.jose4j.jwk.EllipticCurveJsonWebKey;
import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.PublicJsonWebKey;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.keys.EllipticCurves;
import org.jose4j.lang.JoseException;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

import static org.jose4j.jwk.PublicJsonWebKey.Factory.newPublicJwk;

/**
 * Created by Sys on 2016-07-22.
 */
public class JsonWebKeyUtil {

//	public final static Provider provider = new BouncyCastleProvider();

	// ---------------------------------- Json RSA Web Key from org.jose4j.jwk.  ----------------------------------

	public static RsaJsonWebKey produceSecureRsaWebKey() {



		try {

			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(2048);
			KeyPair kp = keyPairGenerator.generateKeyPair();
			RSAPublicKey pub = (RSAPublicKey) kp.getPublic();
			RSAPrivateKey priv = (RSAPrivateKey) kp.getPrivate();
			RsaJsonWebKey rsaJwk = (RsaJsonWebKey) newPublicJwk(pub);
			rsaJwk.setPrivateKey(priv);
			rsaJwk.setKeyId(UUID.randomUUID().toString());
			rsaJwk.setAlgorithm("RSA");
//		sign_jwk.setUse("sig");
			rsaJwk.setKeyId("123452");
			return rsaJwk;
		} catch (NoSuchAlgorithmException | JoseException e) {
			e.printStackTrace();
		}


		return null;

	}

	public static RsaJsonWebKey generateRsaWebKey() throws JoseException {



		try {
			RsaJsonWebKey rsaJsonWebKey = RsaJwkGenerator.generateJwk(2048);
			// Give the JWK a Key ID (kid), which is just the polite thing to do
			rsaJsonWebKey.setKeyId(UUID.randomUUID().toString());
			rsaJsonWebKey.setAlgorithm("RSA");
//		sign_jwk.setUse("sig");
			rsaJsonWebKey.setKeyId("123452");
			return rsaJsonWebKey;
		} catch (JoseException exception) {
			System.err.println("\nJoseException - " + exception.toString() + "\n");
		}

		return null;
	}




	public static RsaJsonWebKey getWebKeyFromKeyPair(KeyPair keyPair) throws JoseException {
		RsaJsonWebKey rsaJwk = (RsaJsonWebKey) newPublicJwk(keyPair.getPublic());
		rsaJwk.setPrivateKey(keyPair.getPrivate());
		rsaJwk.setAlgorithm("RSA");
//		sign_jwk.setUse("sig");
		rsaJwk.setKeyId("123452");
		return rsaJwk;
	}

	/**
	 *
	 * @param rsaJsonWebKey org.jose4j.jwk.JsonWebKey
	 * @return KeyPair
	 * @throws NoSuchAlgorithmException newer
	 */
	public static KeyPair generateKeyPairFromJsonWebKey(RsaJsonWebKey rsaJsonWebKey) throws NoSuchAlgorithmException {
			return new KeyPair(rsaJsonWebKey.getPublicKey(), rsaJsonWebKey.getRsaPrivateKey());
	}

	public static RsaJsonWebKey getRsaJsonWebKeyFromPublicAndPrivateKey(PublicKey publicKey, PrivateKey privateKey) throws JoseException {
		RsaJsonWebKey rsaJwk = (RsaJsonWebKey) newPublicJwk(publicKey);
		rsaJwk.setPrivateKey(privateKey);
		rsaJwk.setAlgorithm("RSA");
//		sign_jwk.setUse("sig");
		rsaJwk.setKeyId("123452");
		return rsaJwk;
	}

	public static RsaJsonWebKey getRsaJsonWebKeyFromPublic(PublicKey publicKey) throws JoseException {
		RsaJsonWebKey sign_jwk = new RsaJsonWebKey((RSAPublicKey) publicKey);
		sign_jwk.setAlgorithm("RSA");
//		sign_jwk.setUse("sig");
		sign_jwk.setKeyId("123452");
		return sign_jwk;

	}

	public static String getPublicRsaWebKeyAsJson(RsaJsonWebKey rsaJwk) {
		return rsaJwk.toJson(JsonWebKey.OutputControlLevel.PUBLIC_ONLY);
	}

	public static String getPrivateRsaWebKeyAsJson(RsaJsonWebKey rsaJwk) {
		return rsaJwk.toJson(JsonWebKey.OutputControlLevel.INCLUDE_PRIVATE);
	}

	public static String getSymmetricRsaWebKeyAsJson(RsaJsonWebKey rsaJwk) {
		return rsaJwk.toJson(JsonWebKey.OutputControlLevel.INCLUDE_PRIVATE);
	}


	public static RsaJsonWebKey getPublicRSAJwkFromJson(String publicRsaJsonJwk) throws JoseException {
		// parse and convert into PublicJsonWebKey object
		RsaJsonWebKey rsaJsonWebKey =	(RsaJsonWebKey) newPublicJwk(publicRsaJsonJwk);
		rsaJsonWebKey.setAlgorithm("RSA");
//		sign_jwk.setUse("sig");
		rsaJsonWebKey.setKeyId("123452");

		return rsaJsonWebKey;
	}


	public static RsaJsonWebKey getPrivateRsaJwkFromJson(String PrivateRsaJsonJwk) throws JoseException {
		// parse and convert into JsonWebKey object
		RsaJsonWebKey rsaJsonWebKey =	(RsaJsonWebKey) newPublicJwk(PrivateRsaJsonJwk);
//		rsaJsonWebKey.setAlgorithm("RSA");
//		sign_jwk.setUse("sig");
		rsaJsonWebKey.setKeyId("123452");

		return rsaJsonWebKey;
	}

	public static String convertPrivateRsaJwtToPublicRsaJwt(String PrivateRsaJsonJwk) throws JoseException {

		return getPublicRsaWebKeyAsJson((RsaJsonWebKey) PublicJsonWebKey.Factory.newPublicJwk(PrivateRsaJsonJwk));

	}





	public static EllipticCurveJsonWebKey generateEllipticWebKey()  {



		try {
			// Generate an EC key pair, which will be used for signing and verification of the JWT, wrapped in a JWK
			EllipticCurveJsonWebKey Jwk = EcJwkGenerator.generateJwk(EllipticCurves.P256);

			// Give the JWK a Key ID (kid), which is just the polite thing to do
			Jwk.setKeyId(UUID.randomUUID().toString());

			return Jwk;
		} catch (JoseException exception) {
			System.err.println("\nJoseException - " + exception.toString() + "\n");
		}

		return null;
	}


	public static String getPublicEcllipticWebKeyAsJson(EllipticCurveJsonWebKey rsaJwk) {
		return rsaJwk.toJson(JsonWebKey.OutputControlLevel.PUBLIC_ONLY);
	}

	public static String getPrivateEcllipticWebKeyAsJson(EllipticCurveJsonWebKey asJWK) {
		return asJWK.toJson(JsonWebKey.OutputControlLevel.INCLUDE_PRIVATE);
	}

	public static String getSymmetricEcllipticWebKeyAsJson(EllipticCurveJsonWebKey rsaJwk) {
		return rsaJwk.toJson(JsonWebKey.OutputControlLevel.INCLUDE_PRIVATE);
	}

	public static EllipticCurveJsonWebKey getEllipticJwkFromJson(String ellipticJsonWebKey) throws JoseException {
		EllipticCurveJsonWebKey curvedJsonWebKey = (EllipticCurveJsonWebKey) EllipticCurveJsonWebKey.Factory.newPublicJwk(ellipticJsonWebKey);
		if(curvedJsonWebKey.getKeyId() == null || curvedJsonWebKey.getKeyId().isEmpty()){
			curvedJsonWebKey.setKeyId(UUID.randomUUID().toString());
		}

		curvedJsonWebKey.setAlgorithm("EC");

		return curvedJsonWebKey;
	}


	public static SecureRandom getSecurRandom() {
		return new SecureRandom( (new Long(System.nanoTime())).toString().getBytes() );
	}
}
