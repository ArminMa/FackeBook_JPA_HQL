package org.kth.HI1034.JWT;


import org.apache.log4j.Logger;
import org.jose4j.jwe.ContentEncryptionAlgorithmIdentifiers;
import org.jose4j.jwe.JsonWebEncryption;
import org.jose4j.jwe.KeyManagementAlgorithmIdentifiers;
import org.jose4j.jwk.EllipticCurveJsonWebKey;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.lang.JoseException;
import org.kth.HI1034.security.ApiKeyFactory;
import org.kth.HI1034.security.util.ciperUtil.JsonWebKeyUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.security.Key;
import java.security.PublicKey;

@Component
public class TokenUtils {


	private static final ApiKeyFactory apiKeyFactory = new ApiKeyFactory();

	private RsaJsonWebKey apikeyPair;
	public static String apiPrivateKey;
	public static String apiPublicKey;

	private final Logger logger = Logger.getLogger(this.getClass());

	private final String AUDIENCE_UNKNOWN = "unknown";
	private final String AUDIENCE_WEB = "web";
	private final String AUDIENCE_MOBILE = "mobile";
	private final String AUDIENCE_TABLET = "tablet";

//	@Value("${jwt.private.symmetricKey}")
//	private String secret;


//	private String generateWebEncryption(String encrypt) throws JoseException {
//
//		Gson gson = new Gson();
//
//		//get UserServerKey for faceuserPojo
//
//
//		RsaJsonWebKey rsaJsonWebKey = generateJsonWebKey();
//
//
//		Key publicKey = rsaJsonWebKey.getPublicKey();
//		Key privateKey = rsaJsonWebKey.getRsaPrivateKey();
//		Key keyPair = rsaJsonWebKey.getKey();
//
//		JsonWebEncryption jwe = new JsonWebEncryption();
//		jwe.setPayload(encrypt);
//		jwe.setAlgorithmHeaderValue(KeyManagementAlgorithmIdentifiers.A128KW);
//		jwe.setEncryptionMethodHeaderParameter(ContentEncryptionAlgorithmIdentifiers.AES_128_CBC_HMAC_SHA_256);
//		jwe.setKey(publicKey);
//
//		String serializedJwe = jwe.getCompactSerialization();
//		System.out.println("Serialized Encrypted JWE: " + serializedJwe);
//
//
//		return jwe.getCompactSerialization();
//	}


	public static TokenPojo ProduceJWT(TokenPojo tokenPojo, String jsonPayload) throws Exception {

		// the keys in
		EllipticCurveJsonWebKey senderJWK = JsonWebKeyUtil.getEllipticJwkFromJson(tokenPojo.getSenderJwk());

		//
		// JSON Web Token is a compact URL-safe means of representing claims/attributes to be transferred between two parties.
		// This example demonstrates producing and consuming a signed JWT
		//

		// Generate an RSA key pair, which will be used for signing and verification of the JWT, wrapped in a JWK
		RsaJsonWebKey rsaJsonWebKey = apiKeyFactory.getJsonWebKey();

		// Create the Claims, which will be the content of the JWT
		JwtClaims claims = new JwtClaims();
		claims.setIssuer(tokenPojo.getIssuer());  // who creates the token and signs it
		claims.setAudience(tokenPojo.getAudience()); // to whom the token is intended to be sent
		claims.setExpirationTimeMinutesInTheFuture(10); // time when the token will expire (10 minutes from now)
		claims.setGeneratedJwtId(); // a unique identifier for the token
		claims.setIssuedAtToNow();  // when the token was issued/created (now)
		claims.setNotBeforeMinutesInThePast(2); // time before which the token is not yet valid (2 minutes ago)
		claims.setSubject(tokenPojo.getSubject()); // the subject/principal is whom the token is about
		claims.setClaim("payload", jsonPayload); // additional claims/attributes about the subject can be added

		// multi-valued claims work too and will end up as a JSON array
/*		List<String> groups = Arrays.asList("user", "other-group", "group-three");
		claims.setStringListClaim("groups", groups); */


		// A JWT is a JWS and/or a JWE with JSON claims as the payload.
		// In this example it is a JWS nested inside a JWE
		// So we first create a JsonWebSignature object.
		JsonWebSignature jws = new JsonWebSignature();

		// The payload of the JWS is JSON content of the JWT Claims
		jws.setPayload(claims.toJson());

		// The JWT is signed using the sender's private key
		jws.setKey(senderJWK.getPrivateKey());


		// Set the Key ID (kid) header because it's just the polite thing to do.
		// We only have one signing key in this example but a using a Key ID helps
		// facilitate a smooth key rollover process
		jws.setKeyIdHeaderValue(senderJWK.getKeyId());
		jws.setContentTypeHeaderValue("JWS");

		// Set the signature algorithm on the JWT/JWS that will integrity protect the claims
		jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.ECDSA_USING_P256_CURVE_AND_SHA256);

		// Sign the JWS and produce the compact serialization, which will be the inner JWT/JWS
		// representation, which is a string consisting of three dot ('.') separated
		// base64url-encoded parts in the form Header.Payload.Signature
		String innerJwt = jws.getCompactSerialization();

		tokenPojo.setToken(innerJwt);

		return tokenPojo;


		// Now you can do something with the JWT. Like send it to some other party
		// over the clouds and through the interwebs.

	}

	public static TokenPojo securJWT(TokenPojo tokenPojo) throws JoseException, InvalidJwtException {

		// the keys in
		EllipticCurveJsonWebKey receiverJWK = JsonWebKeyUtil.getEllipticJwkFromJson(tokenPojo.getReceiverJwk());

		// The outer JWT is a JWE
		JsonWebEncryption jwe = new JsonWebEncryption();

		// The output of the ECDH-ES key agreement will encrypt a randomly generated content encryption key
		jwe.setAlgorithmHeaderValue(KeyManagementAlgorithmIdentifiers.ECDH_ES_A128KW);

		// The content encryption key is used to encrypt the payload
		// with a composite AES-CBC / HMAC SHA2 encryption algorithm
		String encAlg = ContentEncryptionAlgorithmIdentifiers.AES_128_CBC_HMAC_SHA_256;
		jwe.setEncryptionMethodHeaderParameter(encAlg);

		// We encrypt to the receiver using their public key
		jwe.setKey(receiverJWK.getPublicKey());
		jwe.setKeyIdHeaderValue(receiverJWK.getKeyId());

		// A nested JWT requires that the cty (Content Type) header be set to "JWT" in the outer JWT
		jwe.setContentTypeHeaderValue("JWT");

		// The inner JWT is the payload of the outer JWT
		jwe.setPayload(tokenPojo.getToken());

		// Produce the JWE compact serialization, which is the complete JWT/JWE representation,
		// which is a string consisting of five dot ('.') separated
		// base64url-encoded parts in the form Header.EncryptedKey.IV.Ciphertext.AuthenticationTag
		String jwt = jwe.getCompactSerialization();


		System.out.println("JWT-183:\n" + jwt);

		tokenPojo.setToken(jwt);
		return tokenPojo;


	}

	public static String getPayloadCurveJWK(TokenPojo tokenPojo) throws JoseException, MalformedClaimException {
		try {


			// the keys in
			EllipticCurveJsonWebKey senderJWK = JsonWebKeyUtil.getEllipticJwkFromJson(tokenPojo.getSenderJwk());
			EllipticCurveJsonWebKey receiverJWK = JsonWebKeyUtil.getEllipticJwkFromJson(tokenPojo.getReceiverJwk());


			/***************************RECEIVER'S END ***********************************/
			// Use JwtConsumerBuilder to construct an appropriate JwtConsumer, which will
			// be used to validate and process the JWT.
			// The specific validation requirements for a JWT are context dependent, however,
			// it typically advisable to require a (reasonable) expiration time, a trusted issuer, and
			// and audience that identifies your system as the intended recipient.
			// If the JWT is encrypted too, you need only provide a decryption key or
			// decryption key resolver to the builder.
			JwtConsumer jwtConsumer = new JwtConsumerBuilder()
					.setRequireExpirationTime() // the JWT must have an expiration time
					.setRequireSubject() // the JWT must have a subject claim
					.setExpectedIssuer(tokenPojo.getIssuer()) // whom the JWT needs to have been issued by
					.setExpectedAudience(tokenPojo.getAudience()) // to whom the JWT is intended for
					.setDecryptionKey(receiverJWK.getPrivateKey()) //
					.setVerificationKey(senderJWK.getPublicKey()) // verify the signature with the public key
					.build(); // create the JwtConsumer instance


			System.out.println("JWT validation Sooooooo close 208 ");



			JwtClaims jwtClaims = jwtConsumer.processToClaims(tokenPojo.getToken());
			System.out.println("JWT validation succeeded! " + jwtClaims);

			return (String) jwtClaims.getClaimValue("payload");
		} catch (InvalidJwtException e) {
			e.printStackTrace();
		}

		return null;
	}

//	public Boolean validateToken(String token, FaceuserPojo userDetails) {
//		FaceuserPojo user = (FaceuserPojo) userDetails;
//		final String username = this.getUsernameFromToken(token);
//		final Date created = this.getCreatedDateFromToken(token);
//		final Date expiration = this.getExpirationDateFromToken(token);
//		return (username.equals(user.getUsername()) && !(this.isTokenExpired(token)) &&
//				!(this.isCreatedBeforeLastPasswordReset(created, user.getLastTokenGenerated())));
//	}


	private void verify(PublicKey verificationKey, String cs, boolean expectedSignatureStatus) throws JoseException {
		JsonWebSignature consumerJws = new JsonWebSignature();
		consumerJws.setDoKeyValidation(false); // check even with this being lax
		consumerJws.setCompactSerialization(cs);
		consumerJws.setKey(verificationKey);
		try {
			Assert.isTrue(consumerJws.verifySignature() == (expectedSignatureStatus));
		} catch (JoseException e) {

			throw new JoseException("expected valid signature but got " + e.toString());
		}
	}


	public static TokenPojo ProduceJWT2(TokenPojo tokenPojo, String jsonPayload, Key encryptionKey) {


		return tokenPojo;
	}


}
