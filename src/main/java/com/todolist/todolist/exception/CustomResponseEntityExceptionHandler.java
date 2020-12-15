package com.todolist.todolist.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler
	public final ResponseEntity<Object> handleUsernameAlreadyExistException(UsernameAlreadyExistException ex,
			WebRequest req) {
		UsernameAlreadyExistResponse response = new UsernameAlreadyExistResponse(ex.getMessage());
		return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	public final ResponseEntity<Object> handleInvalidCredentialException(BadCredentialsException ex, WebRequest req) {
		InvalidCredentialException response = new InvalidCredentialException();
		return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
	}

}
