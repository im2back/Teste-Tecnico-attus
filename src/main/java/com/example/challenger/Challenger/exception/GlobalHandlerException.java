package com.example.challenger.Challenger.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.challenger.Challenger.service.exceptions.ResourceNotFound;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalHandlerException {

	

	@ExceptionHandler(ResourceNotFound.class)
	public ResponseEntity<?> resourceNotFound(ResourceNotFound ex, HttpServletRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;		
		
		StandardError error = new StandardError(status.value(), "Bad Request", ex.getMessage(), request.getRequestURI());
		
		return ResponseEntity.status(status).body(error);
}
	
}
