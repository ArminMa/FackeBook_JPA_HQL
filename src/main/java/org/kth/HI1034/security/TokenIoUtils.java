package org.kth.HI1034.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.kth.HI1034.model.domain.user.FaceuserPojo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mobile.device.Device;

import java.io.Serializable;
import java.security.Key;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Sys on 2016-08-01.
 */
public class TokenIoUtils implements Serializable {


	public static final long TOKEN_DURATION_SECONDS = 60 * 60 * 24 * 7; // 1 week
	public static final long TOKEN_CREATION_BUFFER_SECONDS = 60 * 5; // 5 min
	public static final String ISSUER_ID = "fackeBook.org";


	private static final String CLAIM_KEY_USERNAME = "sub";
	private static final String CLAIM_KEY_AUDIENCE = "audience";
	private static final String CLAIM_KEY_CREATED = "created";

	private static final String AUDIENCE_UNKNOWN = "unknown";
	private static final String AUDIENCE_WEB = "web";
	private static final String AUDIENCE_MOBILE = "mobile";
	private static final String AUDIENCE_TABLET = "tablet";



	@Value("${jwt.expiration}")
	private Long expiration = TOKEN_DURATION_SECONDS;

	@Value("${server.secretKey}")
	public static String serverSecretKey;

	@Value("${token.header}")
	private static String tokenHeader;



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
			return null;
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
			return null;
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
//			Jwts.parser().setSigningKey(key).parseClaimsJws(token.trim());
			Jwts.parser().setSigningKey(key).parseClaimsJws(token);
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



	public String getUsernameFromToken(String token) {
		String username;
		try {
			final Claims claims = getClaimsFromToken(token);
			username = claims.getSubject();
		} catch (Exception e) {
			username = null;
		}
		return username;
	}

	public String getUserEmailFromToken(String token) {
		String username;
		try {
			final Claims claims = getClaimsFromToken(token);
			username = claims.getSubject();
		} catch (Exception e) {
			username = null;
		}
		return username;
	}

	public Date getCreatedDateFromToken(String token) {
		Date created;
		try {
			final Claims claims = getClaimsFromToken(token);
			created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
		} catch (Exception e) {
			created = null;
		}
		return created;
	}

	public static Date getExpirationDateFromToken(String token) {
		Date expiration;
		try {
			final Claims claims = getClaimsFromToken(token);
			expiration = claims.getExpiration();
		} catch (Exception e) {
			expiration = null;
		}
		return expiration;
	}

	public static String getAudienceFromToken(String token) {
		String audience;
		try {
			final Claims claims = getClaimsFromToken(token);
			audience = (String) claims.get(CLAIM_KEY_AUDIENCE);
		} catch (Exception e) {
			audience = null;
		}
		return audience;
	}

	private static Claims getClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parser()
					.setSigningKey(serverSecretKey)
					.parseClaimsJws(token)
					.getBody();
		} catch (Exception e) {
			claims = null;
		}
		return claims;
	}

	private Date generateExpirationDate() {
		return new Date(System.currentTimeMillis() + expiration * 1000);
	}

	private static Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
		return (lastPasswordReset != null && created.before(lastPasswordReset));
	}

	private String generateAudience(Device device) {
		String audience = AUDIENCE_UNKNOWN;
		if (device.isNormal()) {
			audience = AUDIENCE_WEB;
		} else if (device.isTablet()) {
			audience = AUDIENCE_TABLET;
		} else if (device.isMobile()) {
			audience = AUDIENCE_MOBILE;
		}
		return audience;
	}

	private Boolean ignoreTokenExpiration(String token) {
		String audience = getAudienceFromToken(token);
		return (AUDIENCE_TABLET.equals(audience) || AUDIENCE_MOBILE.equals(audience));
	}

	public String generateToken(FaceuserPojo userDetails, Device device) {
		Map<String, Object> claims = new HashMap<>();
		claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
		claims.put(CLAIM_KEY_AUDIENCE, generateAudience(device));
		claims.put(CLAIM_KEY_CREATED, new Date());
		return generateToken(claims);
	}

	private String generateToken(Map<String, Object> claims) {
		return Jwts.builder()
				.setClaims(claims)
				.setExpiration(generateExpirationDate())
				.signWith(SignatureAlgorithm.HS512, serverSecretKey)
				.compact();
	}

	public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
		final Date created = getCreatedDateFromToken(token);
		return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
				&& (!isTokenExpired(token) || ignoreTokenExpiration(token));
	}

	public String refreshToken(String token) {
		String refreshedToken;
		try {
			final Claims claims = getClaimsFromToken(token);
			claims.put(CLAIM_KEY_CREATED, new Date());
			refreshedToken = generateToken(claims);
		} catch (Exception e) {
			refreshedToken = null;
		}
		return refreshedToken;
	}

	public static Boolean validateToken(String token, FaceuserPojo userDetails) {
		return  !isTokenExpired(token) && getAudienceFromToken(token).equals(userDetails.getEmail());
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
