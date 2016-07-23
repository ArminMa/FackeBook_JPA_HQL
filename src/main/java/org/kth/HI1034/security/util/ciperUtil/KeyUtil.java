package org.kth.HI1034.security.util.ciperUtil;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by Sys on 2016-07-22.
 */
public class KeyUtil {

	protected static final String RSA_ALGORITHM = "RSA";
	protected static final String AES_ALGORITHM = "AES";

//	SecureRandom secureRandom = JCAUtil.getSecureRandom();



	// ---------||||||||||||||||| generate Key util |||||||||||||||||---------

	public static SecretKey generateSymmetricKey() {
		try {

			SecureRandom secureRandom = new SecureRandom();
			secureRandom.setSeed(System.nanoTime());

			KeyGenerator generator = KeyGenerator.getInstance(AES_ALGORITHM);
			generator.init(128, secureRandom);
			return generator.generateKey();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Generates Private Key from BASE64 encoded string
	 *
	 * @param key BASE64 encoded string which represents the key
	 * @return The PrivateKey
	 * @throws java.lang.Exception
	 */
	public static PrivateKey getPrivateKeyFromString(String key) throws Exception {
		KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
		EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(decodeBASE64(key));
		PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
		return privateKey;
	}


	/**
	 * Generates Public Key from BASE64 encoded string
	 *
	 * @param key BASE64 encoded string which represents the key
	 * @return The PublicKey
	 * @throws java.lang.Exception
	 */
	public static PublicKey getPublicKeyFromString(String key) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
		EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(decodeBASE64(key));
		PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
		return publicKey;
	}


	/**
	 * Encode bytes array to BASE64 string
	 *
	 * @param bytes
	 * @return Encoded string
	 */
	private static String encodeBASE64(byte[] bytes) {
		// BASE64Encoder b64 = new BASE64Encoder();
		// return b64.encode(bytes, false);
		return Base64.encodeBase64String(bytes);
	}

	/**
	 * Decode BASE64 encoded string to bytes array
	 *
	 * @param text The string
	 * @return Bytes array
	 * @throws IOException
	 */
	private static byte[] decodeBASE64(String text) throws IOException {
		// BASE64Decoder b64 = new BASE64Decoder();
		// return b64.decodeBuffer(text);
		return Base64.decodeBase64(text);
	}



	/**
	 * Convert a Key to string encoded as BASE64
	 *
	 * @param key The key (private or public)
	 * @return A string representation of the key
	 */
	public static String getKeyAsString(Key key) {
		// Get the bytes of the key
		byte[] keyBytes = key.getEncoded();
		return encodeBASE64(keyBytes);
	}


	/**
	 * Generate key which contains a pair of privae and public key using 1024 bytes
	 *
	 * @return key pair
	 * @throws NoSuchAlgorithmException
	 */
	public static KeyPair generateKeyPair() {
		KeyPairGenerator keyGen;
		try {
			keyGen = KeyPairGenerator.getInstance(RSA_ALGORITHM);
			keyGen.initialize(1024);
			return keyGen.generateKeyPair();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}


}
