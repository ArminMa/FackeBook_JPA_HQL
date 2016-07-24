package org.kth.HI1034.JWT;


import org.jose4j.jwe.ContentEncryptionAlgorithmIdentifiers;
import org.jose4j.jwe.JsonWebEncryption;
import org.jose4j.jwe.KeyManagementAlgorithmIdentifiers;
import org.jose4j.jwk.EllipticCurveJsonWebKey;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.lang.JoseException;
import org.kth.HI1034.security.util.ciperUtil.JsonWebKeyUtil;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TokenUtils {

	public static class EllipticJWT{

		/**
		 *
		 * @param issue String
		 * @param audience String
		 * @param subject String
		 * @param senderPrivateEllipticJwt String
		 * @param receiverPublicEllipticJwt String
		 * @param jsonPayload String
		 * @return
		 * @throws Exception
		 */
		public static String ProduceJWT(
				String issue,
				String audience,
				String subject,
				String senderPrivateEllipticJwt,
				String receiverPublicEllipticJwt,
				String jsonPayload) throws Exception {

			// the keys in
			EllipticCurveJsonWebKey senderJWK = JsonWebKeyUtil.getEllipticJwkFromJson(senderPrivateEllipticJwt);

			// Create the Claims, which will be the content of the JWT
			JwtClaims claim = produceClaim( issue,  audience,  subject,  jsonPayload);

			// A JWT is a JWS and/or a JWE with JSON claims as the payload.
			// In this example it is a JWS nested inside a JWE
			// So we first create a JsonWebSignature object.
			JsonWebSignature jws = new JsonWebSignature();

			// The payload of the JWS is JSON content of the JWT Claims
			jws.setPayload(claim.toJson());

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



			return securJWT( innerJwt , receiverPublicEllipticJwt);


			// Now you can do something with the JWT. Like send it to some other party
			// over the clouds and through the interwebs.

		}

		public static String securJWT( String payload, String receiverPublicEllipticJwt) throws JoseException, InvalidJwtException {

			// the keys in
			EllipticCurveJsonWebKey receiverJWK = JsonWebKeyUtil.getEllipticJwkFromJson(receiverPublicEllipticJwt);

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
			jwe.setPayload(payload);

			// Produce the JWE compact serialization, which is the complete JWT/JWE representation,
			// which is a string consisting of five dot ('.') separated
			// base64url-encoded parts in the form Header.EncryptedKey.IV.Ciphertext.AuthenticationTag
			String jwt = jwe.getCompactSerialization();


			System.out.println("JWT-183:\n" + jwt);


			return jwt;


		}


		/***************************RECEIVER'S END ***********************************/
		public static String getPayloadCurveJWK(String issue,
		                                        String audience,
		                                        String senderPrivateEllipticJwt,
		                                        String receiverPublicEllipticJwt,
		                                        String token) throws JoseException, MalformedClaimException {
			try {

				JwtClaims jwtClaims = getJwtClaim( issue,
						 audience,
						 senderPrivateEllipticJwt,
						 receiverPublicEllipticJwt,
						 token);

						System.out.println("JWT validation succeeded! " + jwtClaims);

				return (String) jwtClaims.getClaimValue("payload");
			} catch (InvalidJwtException e) {
				e.printStackTrace();
			}

			return null;
		}

	}


	public static JwtClaims produceClaim(String issue, String audience, String subject, String jsonPayload){
		// Create the Claims, which will be the content of the JWT
		JwtClaims claims = new JwtClaims();
		claims.setIssuer(issue);  // who creates the token and signs it
		claims.setAudience(audience); // to whom the token is intended to be sent
		claims.setSubject(subject); // the subject/principal is whom the token is about
		claims.setExpirationTimeMinutesInTheFuture(10); // time when the token will expire (10 minutes from now)
		claims.setGeneratedJwtId(); // a unique identifier for the token
		claims.setIssuedAtToNow();  // when the token was issued/created (now)
		claims.setNotBeforeMinutesInThePast(2); // time before which the token is not yet valid (2 minutes ago)
		// multi-valued claims work too and will end up as a JSON array
/*		List<String> groups = Arrays.asList("user", "other-group", "group-three");
		claims.setStringListClaim("groups", groups); */
		claims.setClaim("payload", jsonPayload); // additional claims/attributes about the subject can be added
		return claims;
	}

	public static JwtClaims getJwtClaim(
			String issue,
			String audience,
			String senderPrivateEllipticJwt,
			String receiverPublicEllipticJwt,
			String token) throws JoseException, InvalidJwtException {

		EllipticCurveJsonWebKey senderPublicJWK = JsonWebKeyUtil.getEllipticJwkFromJson(senderPrivateEllipticJwt);
		EllipticCurveJsonWebKey receiverPrivateJWK = JsonWebKeyUtil.getEllipticJwkFromJson(receiverPublicEllipticJwt);

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
				.setExpectedIssuer(issue) // whom the JWT needs to have been issued by
				.setExpectedAudience(audience) // to whom the JWT is intended for
				.setDecryptionKey(receiverPrivateJWK.getPrivateKey()) //
				.setVerificationKey(senderPublicJWK.getPublicKey()) // verify the signature with the public key
				.build(); // create the JwtConsumer instance

		return jwtConsumer.processToClaims(token);
	}


	public static class SymmetricJWT{


		public static JwtClaims validateTokenAndProcessClaims(final Key key,
		                                                      final String issuer,
		                                                      final String audience,
		                                                      final String subject,
		                                                      final String token) throws InvalidJwtException {

			final JwtConsumer jwtConsumer = new JwtConsumerBuilder()
					.setRequireExpirationTime() // the JWT must have an expiration time
					.setAllowedClockSkewInSeconds(180) // allow some leeway in validating time based claims to account for clock skew
					.setExpectedIssuer(issuer) // whom the JWT needs to have been issued by
					.setExpectedAudience(audience) // to whom the JWT is intended for
					.setExpectedSubject(subject)
					.setDecryptionKey(key)
					.setEnableRequireEncryption()
					.setDisableRequireSignature()
					.setSkipSignatureVerification()
					.build(); // create the JwtConsumer instance

			//  Validate the JWT and process it to the Claims
			return jwtConsumer.processToClaims(token);
		}




		/**
		 * Convenience method that generates a JWT Token given a set of parameters common to JWT
		 * implementations.
		 *
		 * @param key
		 *            The key key
		 * @param issuer
		 *            The indended Issuer of the generated token
		 * @param audience
		 *            The intended Audience of the generated token
		 * @param subject
		 *            The indended Subject of the generated token
		 * @param json
		 *            JSON snippet that will be inserted into the claim under the
		 *            key 'json'
		 * @param expirationTimeMinutesInTheFuture
		 *            The maximum number of minutes this generated token is valid.
		 * @return JWT token string of the form string.string.string
		 *
		 * @throws JoseException
		 *             if any issue occurs during generation. Mostly likely a key
		 *             issue.
		 */
		public static String generateJWTToken(final Key key,
		                                      final String issuer,
		                                      final String audience,
		                                      final String subject,
		                                      final String json,
		                                      final int expirationTimeMinutesInTheFuture) throws JoseException {


			final Map<String, List<String>> claimsMap = new HashMap<>();

			claimsMap.put("json", Arrays.asList(json));
			return SymmetricJWT.generateJWT_AES128(
					key,
					issuer,
					audience,
					subject,
					claimsMap,
					expirationTimeMinutesInTheFuture
			);
		}


		/**
		 * Generates a JWT token using AES_128_CBC_HMAC_SHA_256.
		 *
		 * @param key Valid cypher key for encryption
		 * @param issuer Corporate Name of the Issuer of this token.
		 * @param audience The audience of the token. That is whom it is meant for. Usually a corporate name.
		 * @param subject The subject of the token. Meaning what you are securing.
		 * @param claimsMap The map of claims in JWT speak
		 * @param expirationTimeMinutesInTheFuture The maximum number of minutes this generated token is valid.
		 * @return JWT token string of the form string.string.string
		 *
		 * @throws JoseException Tossed if there is any failure during generation.
		 */
		public static String generateJWT_AES128(final Key key,
		                                        final String issuer,
		                                        final String audience,
		                                        final String subject,
		                                        final Map<String, List<String>> claimsMap,
		                                        final int expirationTimeMinutesInTheFuture) throws JoseException {

			final JwtClaims claims = new JwtClaims();
			claims.setIssuer(issuer);  // who creates the token and signs it
			claims.setAudience(audience); // to whom the token is intended to be sent
			claims.setExpirationTimeMinutesInTheFuture(expirationTimeMinutesInTheFuture); // time when the token will expire (10 minutes from now)
			claims.setGeneratedJwtId(); // a unique identifier for the token
			claims.setIssuedAtToNow();  // when the token was issued/created (now)
			claims.setNotBeforeMinutesInThePast(2); // time before which the token is not yet valid (2 minutes ago)
			claims.setSubject(subject); // the subject/principal is whom the token is about

			// Each claims key can point to a List of claims
			claimsMap.keySet().stream().forEach(k -> claims.setStringListClaim(k, claimsMap.get(k)));

			final JsonWebEncryption jwe = new JsonWebEncryption();
			jwe.setPayload(claims.toJson());
			jwe.setAlgorithmHeaderValue(KeyManagementAlgorithmIdentifiers.A128KW);
			jwe.setEncryptionMethodHeaderParameter(ContentEncryptionAlgorithmIdentifiers.AES_128_CBC_HMAC_SHA_256);
			jwe.setKey(key);

			return jwe.getCompactSerialization();
		}






	}


}
