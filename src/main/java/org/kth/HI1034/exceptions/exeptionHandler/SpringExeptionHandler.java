package org.kth.HI1034.exceptions.exeptionHandler;


import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.lang.JoseException;
import org.kth.HI1034.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class SpringExeptionHandler extends ResponseEntityExceptionHandler {

	public SpringExeptionHandler() {
	}


	@ExceptionHandler({ResourceNotFoundException.class})
	protected ResponseEntity<?> handleInvalidRequest(RuntimeException runtimeException) {

		ResourceNotFoundException exeption = (ResourceNotFoundException) runtimeException;

		return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON_UTF8).body(exeption.getMessage());
	}


	@ExceptionHandler({JoseException.class, InvalidJwtException.class})
	protected ResponseEntity<?> handleTokenExeption(Exception exeption) {

		return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON_UTF8).body(exeption.getMessage());

	}


}
