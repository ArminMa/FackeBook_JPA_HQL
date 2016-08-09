package org.kth.HI1034.security.FilterTest;

import org.kth.HI1034.exceptions.restExeption.RestExceptionCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JsonErrorBuilder {

	public static void buildJsonError(HttpServletResponse response, RestExceptionCode exceptionCode, String error, HttpStatus httpStatus) throws IOException {
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(httpStatus.value());
		response.getOutputStream().println(buildJsonError(exceptionCode, error));
	}

	public static String buildJsonError(RestExceptionCode exceptionCode, String error) {
		StringBuilder sb = new StringBuilder();
		sb.append("{ \"code\": \"");
		sb.append(exceptionCode.name());
		sb.append("\", \"error\": \"");
		sb.append(error);
		sb.append("\" }");
		return sb.toString();
	}

}
