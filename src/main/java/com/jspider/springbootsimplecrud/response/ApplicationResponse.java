package com.jspider.springbootsimplecrud.response;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
@Component
public class ApplicationResponse<T> {
	
	private HttpStatus statusCode;
	private String statusMsg;
	private String description;
	private T data;

	@Autowired
	public ApplicationResponse(HttpStatus statusCode, String statusMsg, T data) {
		this.statusCode = statusCode;
		this.statusMsg = statusMsg;
		this.data = data;
	}
	public ApplicationResponse(HttpStatus statusCode, String statusMsg, String description, T data) {
		this(statusCode, statusMsg, data);
		this.description = description;
	}

	/**
	 * Helper method to create a standardized ApplicationResponse.
	 * 
	 * @param statusValue The HTTP status code of the response
	 * @param message     A descriptive message about the response
	 * @param data        The Customer data associated with the response
	 * @return An ApplicationResponse object with the provided details
	 */
	public static <T> ApplicationResponse<T> create(HttpStatus statusValue, String message, T data) {
        return new ApplicationResponse<>(statusValue, message, data); 
    }
}