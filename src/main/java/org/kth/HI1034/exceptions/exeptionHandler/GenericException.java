package org.kth.HI1034.exceptions.exeptionHandler;

import org.springframework.validation.Errors;

/**
 * Created by Sys on 2016-07-21.
 */
public class GenericException extends RuntimeException {
	private Errors errors;

	public GenericException(String message) {
		super(message);
	}

	public GenericException(String message, Errors errors) {
		super(message);
		this.errors = errors;
	}

	public Errors getErrors() { return errors; }
}