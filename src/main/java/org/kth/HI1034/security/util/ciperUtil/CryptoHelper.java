//package org.kth.HI1034.security.util;
//
//import org.apache.commons.codec.binary.Base64;
//
//import javax.crypto.Cipher;
//import javax.crypto.KeyGenerator;
//import javax.crypto.SecretKey;
//import javax.crypto.spec.IvParameterSpec;
//import java.security.Key;
//import java.security.NoSuchAlgorithmException;
//import java.security.SecureRandom;
//
//public class CryptoHelper {
//
//	private Key SymmetricKey;
//
//	public static void main( String [] args ) throws Exception {
//
//		CryptoHelper crypto = new CryptoHelper();
//
//		String plaintext = "This is a good secret.";
//		System.out.println( plaintext );
//
//		String ciphertext = crypto.encryptWithSymmetricKey( plaintext );
//		System.out.println( ciphertext );
//
//		String decrypted = crypto.decryptWithSymmetricKey( ciphertext );
//		System.out.println( decrypted );
//
//	}
//
//	public CryptoHelper( Key SymmetricKey) {
//		this.SymmetricKey = SymmetricKey;
//	}
//
//	public CryptoHelper() throws Exception {
//		this( generateSymmetricKey() );
//	}
//
//	public String encryptWithSymmetricKey( String plaintext ) throws Exception {
//		return encryptWithSymmetricKey( generateIV(), plaintext );
//	}
//
//	public String encryptWithSymmetricKey( byte [] iv, String plaintext ) throws Exception {
//
//		byte [] decrypted = plaintext.getBytes();
//		byte [] encrypted = encryptWithSymmetricKey( iv, decrypted );
//
//		StringBuilder ciphertext = new StringBuilder();
//
//		ciphertext.append( Base64.encodeBase64String( iv ) );
//		ciphertext.append( ":" );
//		ciphertext.append( Base64.encodeBase64String( encrypted ) );
//
//		return ciphertext.toString();
//
//	}
//
//	public String decryptWithSymmetricKey( String ciphertext ) throws Exception {
//		String [] parts = ciphertext.split( ":" );
//		byte [] iv = Base64.decodeBase64( parts[0] );
//		byte [] encrypted = Base64.decodeBase64( parts[1] );
//		byte [] decrypted = decryptWithSymmetricKey( iv, encrypted );
//		return new String( decrypted );
//	}
//
//
//
//
//
//	public Key getSymmetricKey() {
//		return SymmetricKey;
//	}
//
//	public void setSymmetricKey(Key SymmetricKey) {
//		this.SymmetricKey = SymmetricKey;
//	}
//
//	public static byte [] generateIV() {
//		SecureRandom random = new SecureRandom();
//		byte [] iv = new byte [16];
//		random.nextBytes( iv );
//		return iv;
//	}
//
//	public static Key generateSymmetricKey() {
//		try {
//			KeyGenerator generator = KeyGenerator.getInstance( "AES" );
//			SecretKey key = generator.generateKeyPair();
//			return key;
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		}
//
//		return null;
//	}
//
//	public byte [] encryptWithSymmetricKey( byte [] iv, byte [] plaintext ) throws Exception {
//		Cipher cipher = Cipher.getInstance( SymmetricKey.getAlgorithm() + "/CBC/PKCS5Padding" );
//		cipher.init( Cipher.ENCRYPT_MODE, SymmetricKey, new IvParameterSpec( iv ) );
//		return cipher.doFinal( plaintext );
//	}
//
//	public byte [] decryptWithSymmetricKey( byte [] iv, byte [] ciphertext ) throws Exception {
//		Cipher cipher = Cipher.getInstance( SymmetricKey.getAlgorithm() + "/CBC/PKCS5Padding" );
//		cipher.init( Cipher.DECRYPT_MODE, SymmetricKey, new IvParameterSpec( iv ) );
//		return cipher.doFinal( ciphertext );
//	}
//
//}
