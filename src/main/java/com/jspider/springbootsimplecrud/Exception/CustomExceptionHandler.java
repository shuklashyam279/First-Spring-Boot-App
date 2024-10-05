package com.jspider.springbootsimplecrud.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.jspider.springbootsimplecrud.response.ApplicationResponse;

@ControllerAdvice
public class CustomExceptionHandler {

	@ExceptionHandler(IdNotFoundException.class)
	public ResponseEntity<ApplicationResponse<String>> idNotFoundException(IdNotFoundException exception) {

		return ResponseEntity.status(HttpStatus.NOT_FOUND)
							 .body(ApplicationResponse.create(HttpStatus.NOT_FOUND, "Id Not Found", exception.getMessage()));
	}
}