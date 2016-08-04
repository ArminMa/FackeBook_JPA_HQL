package org.kth.HI1034.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Sys on 2016-07-20.
 */
@ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
public class UserNameOrEmailExistExeption extends RuntimeException {

	private String msg;

	private static final long serialVersionUID = -1402436282978052954L;

	public UserNameOrEmailExistExeption() {
		super();
	}

	public UserNameOrEmailExistExeption(String msg) {
		super(System.currentTimeMillis()
				+ ": " + msg);



	}

}
