package org.kth.HI1034.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.Key;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Sys on 2016-08-01.
 */
public class TokenIoUtils {


	public static final long TOKEN_DURATION_SECONDS = 60 * 60 * 24 * 7; // 1 week
	public static final long TOKEN_CREATION_BUFFER_SECONDS = 60 * 5; // 5 min
	public static final String ISSUER_ID = "fackeBook.org";


/*	public static String getSigndJwtHS256(String username, String[] roles, int version, Date expires, Key key, String jsonPayload) {
		// Issue a token (can be a random String persisted to a database or a JWT token)
		// The issued token must be associated to a user
		// Return the issued token
		if (username == null) {
			throw new NullPointerException("null username is illegal");
		}
		if (roles == null) {
			throw new NullPointerException("null roles are illegal");
		}
		if (expires == null) {
			throw new NullPointerException("null expires is illegal");
		}
		if (key == null) {
			throw new NullPointerException("null key is illegal");
		}


		String jwtString = Jwts
				.builder()
				.setIssuer("Jersey-Security-Basic")
				.setSubject(username)
				.setAudience(StringUtils.join(Arrays.asList(roles), ","))
				.setExpiration(expires)
				.setIssuedAt(new Date())
				.setId(String.valueOf(version))
				.setPayload(jsonPayload)
				.signWith(SignatureAlgorithm.HS256, key)
				.compact();
		return jwtString;
	}*/

	public String createJwtHS512(String username, String secretKey) {
		return Jwts.builder()
				.setSubject(username)
				.setIssuer(ISSUER_ID)
				.setIssuedAt(Date.from(Instant.now()))
				.setExpiration(Date.from(Instant.now().plusSeconds(TOKEN_DURATION_SECONDS)))
				.setNotBefore(Date.from(Instant.now().minusSeconds(TOKEN_CREATION_BUFFER_SECONDS)))
				.signWith(SignatureAlgorithm.HS512, secretKey)
				.compact();
	}

	public static String createAuthJwt(
			String issue,
			String audience,
			String subject,
			List<String> roles,
			String jsonPayload,
			Key key) {



		return Jwts
				.builder()
				.setIssuer(issue)
				.setSubject(subject)
				.setAudience(audience)
				.claim("roles", roles)
				.claim("payload",jsonPayload)
				.setExpiration(addDays(new Date(System.nanoTime()), 1))
				.setIssuedAt(new Date(System.nanoTime()))
				.setId(UUID.randomUUID().toString())
				.signWith(SignatureAlgorithm.HS256, key)

				.compact();

	}


	public static String getPayloadFromJwt(String token, Key key) {

		Claims claims;
		try {
			claims = Jwts.parser()
					.setSigningKey(key)
					.parseClaimsJws(token)
					.getBody();
		} catch (Exception e) {
			claims = null;
		}

		return (String) claims.get("payload");

	}

	public static String getPayloadFromJwt(String token) {

		Claims claims;
		try {
			claims = Jwts.parser()
					.parseClaimsJws(token)
					.getBody();
		} catch (Exception e) {
			claims = null;
		}

		return (String) claims.get("payload");

	}


	private static Claims getClaimsFromToken(String token, Key key) {
		Claims claims;
		try {
			claims = Jwts.parser()
					.setSigningKey(key)
					.parseClaimsJws(token)
					.getBody();
		} catch (Exception e) {
			claims = null;
		}

		return claims;
	}

	public static boolean isValid(String token, Key key) {
		try {
			Jwts.parser().setSigningKey(key).parseClaimsJws(token.trim());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static String getName(String jwsToken, Key key) {
		if (isValid(jwsToken, key)) {
			Jws<Claims> claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(jwsToken);
			return claimsJws.getBody().getSubject();
		}
		return null;
	}

	public static List<String> getRoles(String jwsToken, Key key) {
		if (isValid(jwsToken, key)) {
			Jws<Claims> claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(jwsToken);
			return (List<String>) claimsJws.getBody().get("role");
		}
		return null;
	}

	public static int getVersion(String jwsToken, Key key) {
		if (isValid(jwsToken, key)) {
			Jws<Claims> claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(jwsToken);
			return Integer.parseInt(claimsJws.getBody().getId());
		}
		return -1;
	}




	/*

	To add one day, per the question asked, call it as follows:

	String sourceDate = "2012-02-29";
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	Date myDate = format.parse(sourceDate);
	myDate = DateUtil.addDays(myDate, 1);

*/
	public static Date addDays(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days); //minus number would decrement the days
		return cal.getTime();
	}

}
