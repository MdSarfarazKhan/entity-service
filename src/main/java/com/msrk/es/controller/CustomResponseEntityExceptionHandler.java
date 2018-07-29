package com.msrk.es.controller;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.msrk.es.custom.exception.EntityNotFoundException;
import com.msrk.es.custom.exception.EntityUpdateFailedException;
import com.msrk.es.custom.exception.ExceptionDetails;


/**
 * @author mdsarfarazkhan
 *
 */
	
@RestControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	
	@ExceptionHandler(EntityNotFoundException.class)
	public final ResponseEntity<ExceptionDetails> handleEntityUpdateFailedException(EntityNotFoundException ex, WebRequest request) {
		ExceptionDetails details = new ExceptionDetails(ex.getMessage(),request.getDescription(false), new Date());
		return new ResponseEntity<>(details, HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(EntityUpdateFailedException.class)
	public final ResponseEntity<ExceptionDetails> handleEntityUpdateFailedException(EntityUpdateFailedException ex, WebRequest request) {
		ExceptionDetails details = new ExceptionDetails(ex.getMessage(),request.getDescription(false), new Date());
		return new ResponseEntity<>(details, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	
	@ExceptionHandler(RuntimeException.class)
	public final ResponseEntity<ExceptionDetails> handleAllRuntimeException(Exception ex, WebRequest request) {
		ExceptionDetails details = new ExceptionDetails(ex.getMessage(),request.getDescription(false), new Date());
		return new ResponseEntity<ExceptionDetails>(details, HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ExceptionDetails> handleAllException(Exception ex, WebRequest request) {
		ExceptionDetails details = new ExceptionDetails(ex.getMessage(),request.getDescription(false), new Date());
		return new ResponseEntity<ExceptionDetails>(details, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	
	
	
}
