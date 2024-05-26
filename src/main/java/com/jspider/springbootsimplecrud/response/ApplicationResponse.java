package com.jspider.springbootsimplecrud.response;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class ApplicationResponse<T> {
	
	public int statusCode;
	public String statusMsg;
//	public String description;
	public T data;
}