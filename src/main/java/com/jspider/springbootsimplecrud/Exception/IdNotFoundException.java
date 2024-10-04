package com.jspider.springbootsimplecrud.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IdNotFoundException extends RuntimeException {

	private String msg;
	
	public IdNotFoundException(String message) {
		super(message);
	}
}
