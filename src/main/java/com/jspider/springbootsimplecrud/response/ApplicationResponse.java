package com.jspider.springbootsimplecrud.response;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.Data;

@Data
@Component
public class ApplicationResponse<T> {
	
	private int statusCode;
	private String statusMsg;
	private String description;
	private T data;

	@Autowired
	public ApplicationResponse(int statusCode, String statusMsg, T data) {
		this.statusCode = statusCode;
		this.statusMsg = statusMsg;
		this.data = data;
	}
	public ApplicationResponse(int statusCode, String statusMsg, String description, T data) {
		this(statusCode, statusMsg, data);
		this.description = description;
	}
}