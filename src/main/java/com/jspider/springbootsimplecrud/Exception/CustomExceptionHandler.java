package com.jspider.springbootsimplecrud.Exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.jspider.springbootsimplecrud.response.ApplicationResponse;

@ControllerAdvice
public class CustomExceptionHandler {

	@Autowired
	private ApplicationResponse<String> applicationResponse;

	@ExceptionHandler
	public ResponseEntity<ApplicationResponse<String>> idNotFoundException(IdNotFoundException exception) {

		applicationResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
//		applicationResponse.setDescription("Please Pass Valid Id");
		applicationResponse.setStatusMsg("Id Not Found");
		applicationResponse.setData(exception.getMessage());

		return new ResponseEntity<ApplicationResponse<String>>(applicationResponse, HttpStatus.NOT_FOUND);
	}
}