package org.kth.HI1034.exceptions.restExeption;

import org.springframework.http.HttpStatus;

public enum RestExceptionCode {

	// warnings
	WC_FE_001(HttpStatus.METHOD_NOT_ALLOWED, "you are not to allowed to make this call", false),
	WC_FE_008(HttpStatus.BAD_REQUEST, "the formatting of the call was wrong", false),
	// errors
	EC_FE_001(HttpStatus.UNAUTHORIZED, "Username and PW do not match", false),
	EC_FE_002(HttpStatus.NOT_ACCEPTABLE, "The user is already logged in", false),
	EC_FE_003(HttpStatus.SERVICE_UNAVAILABLE, "App is not currently available", true),
	EC_FE_004(HttpStatus.NOT_FOUND, "This item can not be accessed", false),
	EC_FE_006(HttpStatus.UNAUTHORIZED, "User is not enabled", true),
	EC_FE_014(HttpStatus.BAD_REQUEST, "AuthToken or UserToken header does not exist in the request", false),

	// fatal errors
	FC_RE_001(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error", true);

	private HttpStatus httpStatus;
	private String error;
	private boolean logError;

	RestExceptionCode(HttpStatus httpStatus, String error, boolean logError) {
		this.httpStatus = httpStatus;
		this.error = error;
		this.logError = logError;
	}

	public static RestExceptionCode fromString(String code) {
		try {
			return RestExceptionCode.valueOf(code.trim().toUpperCase());
		} catch (NullPointerException ex) {
			return null;
		} catch (IllegalArgumentException ex) {
			return null;
		}
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public String getError() {
		return error;
	}

	public boolean isLogError() {
		return logError;
	}
}