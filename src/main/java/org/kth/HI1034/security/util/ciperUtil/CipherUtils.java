package org.kth.HI1034.security.util.ciperUtil;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;

/**
 * <p>Title: CipherUtils</p>
 * <p>Description: Utility class that helps encryptWithPublicKey and decryptWithPrivateKey strings using RSA algorithm</p>
 *
 * @author Aviran Mordo http://aviran.mordos.com
 * @version 1.0
 */
public class CipherUtils {

	public final static Provider provider = new BouncyCastleProvider();

	public CipherUtils() {
		init();
	}

	/**
	 * Init java security to add BouncyCastle as an RSA provider
	 */
	public static void init() {
		Security.addProvider(new BouncyCastleProvider());

	}

	public String sighnData(PrivateKey privateKey, String dataToSign) throws InvalidKeyException, SignatureException, NoSuchAlgorithmException {

		Signature signer = Signature.getInstance("SHA1withRSA", provider);
		signer.initSign(privateKey); // PKCS#8 is preferred
		signer.update(Base64.decodeBase64(dataToSign) );
		return Base64.encodeBase64String( signer.sign() ) ;

	}

	public boolean verifieSigndData(PublicKey publicKey, String data, String signedData) throws NoSuchAlgorithmException {
		byte[] decodedSighnData = Base64.decodeBase64(signedData);

		MessageDigest messageDigest = MessageDigest.getInstance("SHA-256", provider);
		messageDigest.update(decodedSighnData);

		return false;
	}



	public static String encrypt (String payLoad, String password, String salt) {
		TextEncryptor encryptor = Encryptors.text(password, salt);
		return  encryptor.encrypt(payLoad);
	}


	public static String decrypt (String encryptedPayLoad, String password, String salt) {
		TextEncryptor encryptor = Encryptors.text(password, salt);
		return  encryptor.decrypt(encryptedPayLoad);
	}


	/**
	 * Encrypt a text using public key.
	 *
	 * @param text The original unencrypted text
	 * @param key  The public key
	 * @return Encrypted text
	 * @throws java.lang.Exception
	 */
	public static byte[] encryptWithPublicKey(byte[] text, PublicKey key) throws Exception {
		byte[] cipherText = null;
		//
		// get an RSA cipher object and print the provider
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

		// encryptWithPublicKey the plaintext using the public key
		cipher.init(Cipher.ENCRYPT_MODE, key);
		cipherText = cipher.doFinal(text);
		return cipherText;
	}

	/**
	 * Encrypt a text using public key. The result is enctypted BASE64 encoded text
	 *
	 * @param text The original unencrypted text
	 * @param key  The public key
	 * @return Encrypted text encoded as BASE64
	 * @throws java.lang.Exception
	 */
	public static String encryptWithPublicKey(String text, PublicKey key) throws Exception {
		String encryptedText;
		byte[] cipherText = encryptWithPublicKey(text.getBytes("UTF8"), key);
		encryptedText = encodeBASE64(cipherText);
		return encryptedText;
	}

	/**
	 * Decrypt text using private key
	 *
	 * @param text The encrypted text
	 * @param key  The private key
	 * @return The unencrypted text
	 * @throws java.lang.Exception
	 */
	public static byte[] decryptWithPrivateKey(byte[] text, PrivateKey key) throws Exception {
		byte[] dectyptedText = null;
		// decryptWithPrivateKey the text using the private key
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, key);
		dectyptedText = cipher.doFinal(text);
		return dectyptedText;

	}

	/**
	 * Decrypt BASE64 encoded text using private key
	 *
	 * @param text The encrypted text, encoded as BASE64
	 * @param key  The private key
	 * @return The unencrypted text encoded as UTF8
	 * @throws java.lang.Exception
	 */
	public static String decryptWithPrivateKey(String text, PrivateKey key) throws Exception {
		String result;
		// decryptWithPrivateKey the text using the private key
		byte[] dectyptedText = decryptWithPrivateKey(decodeBASE64(text), key);
		result = new String(dectyptedText, "UTF8");
		return result;

	}


	/**
	 * Encrypt file using 1024 RSA encryption
	 *
	 * @param srcFileName  Source file name
	 * @param destFileName Destination file name
	 * @param key          The key. For encryption this is the Private Key and for decryption this is the public key
	 * @throws Exception
	 */
	public static void encryptFile(String srcFileName, String destFileName, PublicKey key) throws Exception {
		encryptDecryptFile(srcFileName, destFileName, key, Cipher.ENCRYPT_MODE);
	}

	/**
	 * Decrypt file using 1024 RSA encryption
	 *
	 * @param srcFileName  Source file name
	 * @param destFileName Destination file name
	 * @param key          The key. For encryption this is the Private Key and for decryption this is the public key
	 * @throws Exception
	 */
	public static void decryptFile(String srcFileName, String destFileName, PrivateKey key) throws Exception {
		encryptDecryptFile(srcFileName, destFileName, key, Cipher.DECRYPT_MODE);
	}

	/**
	 * Encrypt and Decrypt files using 1024 RSA encryption
	 *
	 * @param srcFileName  Source file name
	 * @param destFileName Destination file name
	 * @param key          The key. For encryption this is the Private Key and for decryption this is the public key
	 * @param cipherMode   Cipher Mode
	 * @throws Exception
	 */
	public static void encryptDecryptFile(String srcFileName, String destFileName, Key key, int cipherMode) throws Exception {
		OutputStream outputWriter = null;
		InputStream inputReader = null;
		try {
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			String textLine = null;
			//RSA encryption data size limitations are slightly less than the key modulus size,
			//depending on the actual padding scheme used (e.g. with 1024 bit (128 byte) RSA key,
			//the size limit is 117 bytes for PKCS#1 v 1.5 padding. (http://www.jensign.com/JavaScience/dotnet/RSAEncrypt/)
			byte[] buf = cipherMode == Cipher.ENCRYPT_MODE ? new byte[100] : new byte[128];
			int bufl;
			// init the Cipher object for Encryption...
			cipher.init(cipherMode, key);

			// start FileIO
			outputWriter = new FileOutputStream(destFileName);
			inputReader = new FileInputStream(srcFileName);
			while ((bufl = inputReader.read(buf)) != -1) {
				byte[] encText = null;
				if (cipherMode == Cipher.ENCRYPT_MODE) {
					encText = encryptWithPublicKey(copyBytes(buf, bufl), (PublicKey) key);
				} else {
					encText = decryptWithPrivateKey(copyBytes(buf, bufl), (PrivateKey) key);
				}
				outputWriter.write(encText);
			}
			outputWriter.flush();

		} finally {
			try {
				if (outputWriter != null) {
					outputWriter.close();
				}
				if (inputReader != null) {
					inputReader.close();
				}
			} catch (Exception e) {
				// do nothing...
			} // end of inner try, catch (Exception)...
		}
	}

	public static byte[] copyBytes(byte[] arr, int length) {
		byte[] newArr = null;
		if (arr.length == length) {
			newArr = arr;
		} else {
			newArr = new byte[length];
			for (int i = 0; i < length; i++) {
				newArr[i] = (byte) arr[i];
			}
		}
		return newArr;
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


// --------- encrypt and decrypt With SymmetricKey ---------

	public static String encryptWithSymmetricKey(String plaintext, Key symmetricKey) throws Exception {
		return encryptWithSymmetricKey(generateIV(), plaintext, symmetricKey);
	}

	public static String decryptWithSymmetricKey(String ciphertext, Key symmetricKey) throws Exception {
		String[] parts = ciphertext.split(":");
		byte[] iv = Base64.decodeBase64(parts[0]);
		byte[] encrypted = Base64.decodeBase64(parts[1]);
		byte[] decrypted = decryptWithSymmetricKey(iv, encrypted, symmetricKey);
		return new String(decrypted);
	}

	public static String encryptWithSymmetricKey(byte[] iv, String plaintext, Key symmetricKey) throws Exception {

		byte[] decrypted = plaintext.getBytes();
		byte[] encrypted = encryptWithSymmetricKey(iv, decrypted, symmetricKey);

		StringBuilder ciphertext = new StringBuilder();

		ciphertext.append(Base64.encodeBase64String(iv));
		ciphertext.append(":");
		ciphertext.append(Base64.encodeBase64String(encrypted));

		return ciphertext.toString();

	}


	public static byte[] generateIV() {
		SecureRandom random = new SecureRandom();
		byte[] iv = new byte[16];
		random.nextBytes(iv);
		return iv;
	}


	public static byte[] encryptWithSymmetricKey(byte[] iv, byte[] plaintext, Key symmetricKey) throws Exception {
		Cipher cipher = Cipher.getInstance(symmetricKey.getAlgorithm() + "/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, symmetricKey, new IvParameterSpec(iv));
		return cipher.doFinal(plaintext);
	}

	public static byte[] decryptWithSymmetricKey(byte[] iv, byte[] ciphertext, Key symmetricKey) throws Exception {
		Cipher cipher = Cipher.getInstance(symmetricKey.getAlgorithm() + "/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, symmetricKey, new IvParameterSpec(iv));
		return cipher.doFinal(ciphertext);
	}








}
