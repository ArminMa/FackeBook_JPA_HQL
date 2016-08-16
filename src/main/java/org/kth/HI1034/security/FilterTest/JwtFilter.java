package org.kth.HI1034.security.FilterTest;

import org.kth.HI1034.exceptions.restExeption.RestException;
import org.kth.HI1034.exceptions.restExeption.RestExceptionCode;
import org.kth.HI1034.model.converters.Converter;
import org.kth.HI1034.model.domain.keyUserServer.UserKeyRepository;
import org.kth.HI1034.model.domain.keyUserServer.UserServerKey;
import org.kth.HI1034.model.domain.keyUserServer.UserServerKeyPojo;
import org.kth.HI1034.model.domain.user.FaceUser;
import org.kth.HI1034.model.domain.user.FaceUserRepository;
import org.kth.HI1034.model.domain.user.FaceuserPojo;
import org.kth.HI1034.security.JWT.TokenIoUtils;
import org.kth.HI1034.security.util.KeyUtil;
import org.kth.HI1034.util.GsonX;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;


// todo fix so all this classes in folder FilterTest can filter the @RequestMapping("/api")


public class JwtFilter extends GenericFilterBean {
	private String serverSecretKey;
	private static String tokenHeaderKey;
	private static String userTokenHeaderKey;
	private UserKeyRepository userKeyRepo;
	private FaceUserRepository faceUserRepos;

	private static final Logger logger = LoggerFactory.getLogger( JwtFilter.class );




	public JwtFilter(
			UserKeyRepository keyRepository,
			FaceUserRepository faceUserRepos,
			String tokenAuthHeaderKey,
			String userTokenHeaderKey) {

		this.userKeyRepo = keyRepository;
		this.faceUserRepos = faceUserRepos;
		JwtFilter.tokenHeaderKey = tokenAuthHeaderKey;
		JwtFilter.userTokenHeaderKey = userTokenHeaderKey;

	}



	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		if (!(request instanceof HttpServletRequest)) {
			chain.doFilter(request, response);
		}

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.setHeader("Access-Control-Allow-Origin", "*");
		httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT, HEAD");
		httpResponse.setHeader("Access-Control-Max-Age", "3600");
		httpResponse.setHeader("Access-Control-Allow-Headers", tokenHeaderKey + ", " + userTokenHeaderKey);
		httpResponse.setHeader("Access-Control-Expose-Headers", userTokenHeaderKey);

		String authTokenHeader = httpRequest.getHeader(tokenHeaderKey);
		String userInfoTokenHeader = httpRequest.getHeader(userTokenHeaderKey);


		if (authTokenHeader != null && userInfoTokenHeader != null) {
			try {
				// extract JSON UserDetails from the token
				String jsonUserDetails = TokenIoUtils.getPayloadFromJwt(userInfoTokenHeader);
				logger.debug("Extracted JsonUserDetails from Token in Request: {}", jsonUserDetails);
				FaceuserPojo faceuserPojo = getUserFromToken(jsonUserDetails, null);
				if (faceuserPojo != null && faceuserPojo.getEmail() != null) {

					UserServerKeyPojo userServerKeyPojo = findUserServerKey(faceuserPojo.getEmail());

					if (userServerKeyPojo != null && userServerKeyPojo.getTokenKey() != null) {
						logger.debug("\nfaceuserPojo: \n", faceuserPojo.toString());
						SecretKey secretTokenKey = KeyUtil.getSecretKeyFromString(userServerKeyPojo.getTokenKey());
						String jsonUserAuthDetails = TokenIoUtils.getPayloadFromJwt(authTokenHeader);

						faceuserPojo = getUserFromToken(jsonUserDetails, secretTokenKey);


						if(faceuserPojo != null && faceuserPojo.getEmail() != null){
							Date expirationDate = TokenIoUtils.getExpirationDateFromToken(authTokenHeader, secretTokenKey);
							logger.debug("Token expirationDate: {}", expirationDate);

							if (expirationDate != null && expirationDate.getTime() > System.currentTimeMillis()) {

								logger.debug("The token isn't expired, authenticate the user from the token for this request " +
										"add an updated token to the response so the client-side session will be refreshed");

								// if the token isn't expired, authenticate the user from the token for this request
								// and add an updated token to the response so the client-side session will be refreshed
								FaceUser faceUserEntity = faceUserRepos.findOne(faceuserPojo.getId());
								Authentication authentication;
								if  (
										faceUserEntity.getPassword().equals(faceuserPojo.getPassword()) &&
												faceUserEntity.getEmail().equals(faceuserPojo.getEmail()) &&
												!faceuserPojo.getAuthorities().isEmpty()
										) {
									authentication = faceuserPojo.getAuthorities().get(0);
									SecurityContextHolder.getContext().setAuthentication(authentication);
								}

								httpResponse.addHeader(tokenHeaderKey, authTokenHeader);
								httpResponse.addHeader(userTokenHeaderKey, userInfoTokenHeader);


								logger.debug("Authenticated user: {} ", faceuserPojo.toString());
							} else {
								if(faceuserPojo.getEmail() != null){
									logger.debug("User athentication expired: {}", faceuserPojo.getEmail());
								}else{
									logger.debug("User athentication expired: {}");
								}

								throw new RestException("authorization expired", RestExceptionCode.EC_FE_014, HttpStatus.UNAUTHORIZED);
							}
						}


					} else {
						logger.debug("the user is not registered properly");
						throw new RestException("the user is not registered properly", RestExceptionCode.FC_RE_001, HttpStatus.UNAUTHORIZED);
					}
				}
			} catch ( Exception e) {
				logger.debug("Invalid signature passed; continue filter chain\n Exeption? : \n"  + Arrays.toString(e.getStackTrace()));

			}
		}
		logger.debug("httpRequest Method: {}", httpRequest.getMethod());
		if (!httpRequest.getMethod().equalsIgnoreCase("OPTIONS")) {
			chain.doFilter(request, response);
		}
	}

	private FaceuserPojo getUserFromToken(String token, Key key){

			// extract JSON UserDetails from the token
			String jsonUserDetails;
			if(key == null){
				 jsonUserDetails = TokenIoUtils.getPayloadFromJwt(token);
			}else{
				jsonUserDetails = TokenIoUtils.getPayloadFromJwt(token, key);
			}

			logger.debug("Extracted JsonUserDetails from Token in Request: {}", jsonUserDetails);

			if (jsonUserDetails != null) {
				FaceuserPojo faceuserPojo = GsonX.gson.fromJson(jsonUserDetails, FaceuserPojo.class);


				if (faceuserPojo != null) {
					logger.debug("\nfaceuserPojo: \n", faceuserPojo.toString());
					return faceuserPojo;
				}
			}
		return null;
	}


	public UserServerKeyPojo findUserServerKey(String email) {


		UserServerKey userServerKey = userKeyRepo.findByEmail(email);

		if(userServerKey != null ){
			return Converter.convert(userServerKey);
		}

		return null;

	}

}


