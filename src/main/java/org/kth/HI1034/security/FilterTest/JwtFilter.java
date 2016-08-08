package org.kth.HI1034.security.FilterTest;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.kth.HI1034.model.domain.user.FaceuserPojo;
import org.kth.HI1034.security.TokenIoUtils;
import org.kth.HI1034.security.util.KeyUtil;
import org.kth.HI1034.util.GsonX;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// todo fix so all this classes in folder FilterTest can filter the @RequestMapping("/api")


public class JwtFilter extends GenericFilterBean {





	private String serverSecretKey;


	private String tokenHeader;

	public JwtFilter( String serverSecretKey, String tokenHeader) {

		this.serverSecretKey = serverSecretKey;
		this.tokenHeader = tokenHeader;
	}

	private final Logger LOGGER = LogManager.getLogger(this.getClass());



	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {



		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String authToken = httpRequest.getHeader(tokenHeader);

		System.out.println("\n\n\n\n" +
				"----------------------------------- JwtFilter.36 -------------------------------------" +
				"\n\nhttpRequest.getRequestURI() = " + httpRequest.getRequestURI() +
				"\n\n------------------------------------------------------------------------\n\n\n\n\n");

		LOGGER.info("\n\n\n\n" +
				"----------------------------------- JwtFilter.36 -------------------------------------" +
				"\n\nhttpRequest.getRequestURI() = " + httpRequest.getRequestURI() +
				"\n\n------------------------------------------------------------------------\n\n\n\n\n");

		// authToken.startsWith("Bearer ")
		// String authToken = header.substring(7);

//		Assert.notNull(faceuserPojo, "\n\n JwtFilter.GenericFilterBean the user could not be pars from token \n\n");

		boolean isTokenValid = TokenIoUtils.isValid(authToken, KeyUtil.SymmetricKey.getSecretKeyFromString(serverSecretKey));

		if (isTokenValid && SecurityContextHolder.getContext().getAuthentication() == null) {

			String faceUserPojoJson = TokenIoUtils.getPayloadFromJwt(authToken);

			FaceuserPojo faceuserPojo = GsonX.gson.fromJson(faceUserPojoJson, FaceuserPojo.class);

			if (faceuserPojo != null && faceuserPojo.getEmail() != null) {


				FaceuserPojo userDetails = getAuthentication(httpRequest);
//				FaceuserPojo userDetails = this.authService.loadUserFromRepository(faceuserPojo);


				if (userDetails!= null && TokenIoUtils.validateToken(authToken, userDetails)) {
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
		}

		httpResponse.setHeader("Access-Control-Allow-Origin", "*");
		httpResponse.setHeader("Access-Control-Allow-Methods", "POST, PUT, PATCH, GET, OPTIONS, DELETE");
		httpResponse.setHeader("Access-Control-Max-Age", "3600");
		httpResponse.setHeader("Access-Control-Allow-Headers", "x-requested-with");
		httpResponse.setHeader(tokenHeader, "*");

		chain.doFilter(request, response);
	}


	public FaceuserPojo faceFilter(String authToken)  {

		System.out.println("\n\n\n\n" +
				"----------------------------------- JwtFilter.110 -------------------------------------" +
				"\n\nauthToken = " + authToken +
				"\n\nserverSecretKey = " + serverSecretKey +
				"\n\n------------------------------------------------------------------------\n\n\n\n\n");

		boolean isTokenValid = TokenIoUtils.isValid(authToken, KeyUtil.SymmetricKey.getSecretKeyFromString(serverSecretKey));

		if (isTokenValid && SecurityContextHolder.getContext().getAuthentication() == null) {

			String faceUserPojoJson = TokenIoUtils.getPayloadFromJwt(authToken);

			FaceuserPojo faceuserPojo = GsonX.gson.fromJson(faceUserPojoJson, FaceuserPojo.class);

			if (faceuserPojo != null && faceuserPojo.getEmail() != null) {
				return faceuserPojo;
			}
		}
		return null;
	}

	public FaceuserPojo getAuthentication(HttpServletRequest httpRequest) {
		String token = httpRequest.getHeader(tokenHeader);

		if ( TokenIoUtils.isValid(token, KeyUtil.getSecretKeyFromString(serverSecretKey)) ){
			String payload = TokenIoUtils.getPayloadFromJwt(token);
			FaceuserPojo faceuserPojo = GsonX.gson.fromJson(payload, FaceuserPojo.class);
			return faceuserPojo;
		}


		return null;
	}

	public FaceuserPojo getAuthentication(String tokenHeader) {

		if ( TokenIoUtils.isValid(tokenHeader, KeyUtil.getSecretKeyFromString(serverSecretKey)) ){
			String payload = TokenIoUtils.getPayloadFromJwt(tokenHeader);
			FaceuserPojo faceuserPojo = GsonX.gson.fromJson(payload, FaceuserPojo.class);
			return faceuserPojo;
		}


		return null;
	}


	@Override
	public void destroy() {

	}


}


