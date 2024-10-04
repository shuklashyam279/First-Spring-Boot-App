package com.jspider.springbootsimplecrud.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.jspider.springbootsimplecrud.response.ApplicationResponse;

@ControllerAdvice
public class CustomExceptionHandler {

	private final ApplicationResponse<String> applicationResponse;

	@Autowired
	public CustomExceptionHandler(ApplicationResponse<String> applicationResponse) {
		this.applicationResponse = applicationResponse;
	}

	@ExceptionHandler(IdNotFoundException.class)
	public ResponseEntity<ApplicationResponse<String>> IdNotFoundException(IdNotFoundException exception) {
		applicationResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
		applicationResponse.setStatusMsg("Id Not Found");
		applicationResponse.setData(exception.getMessage());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(applicationResponse);
	}
}