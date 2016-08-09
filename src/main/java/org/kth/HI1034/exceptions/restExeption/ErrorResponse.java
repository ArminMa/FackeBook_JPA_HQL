package org.kth.HI1034.exceptions.restExeption;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.kth.HI1034.util.GsonX;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class ErrorResponse {

	private final String code;
	private final String error;

	public ErrorResponse(RestException ex) {
		this(ex.getExceptionCode(), ex.getMessage());
	}

	public ErrorResponse(RestExceptionCode exceptionCode) {
		this(exceptionCode.name(), exceptionCode.getError());
	}

	public ErrorResponse(RestExceptionCode exceptionCode, String error) {
		this(exceptionCode.name(), error);
	}

	public ErrorResponse(String code, String error) {
		this.code = code;
		this.error = error;
	}

	public String getCode() {
		return code;
	}

	public String getError() {
		return error;
	}

	@Override
	public String toString() {
		return GsonX.gson.toJson(this);
	}
}