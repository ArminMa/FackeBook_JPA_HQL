package org.kth.HI1034.exceptions;

import org.kth.HI1034.exceptions.exeptionHandler.GenericException;


public class InvalidTokenException extends GenericException {

	public InvalidTokenException(String msg) {
		super(System.currentTimeMillis() + ": " + msg);
	}


	public InvalidTokenException(String message, Throwable cause) {
		super(message, cause);
	}

}
