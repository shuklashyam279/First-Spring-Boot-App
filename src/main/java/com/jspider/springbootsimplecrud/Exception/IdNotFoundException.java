package com.jspider.springbootsimplecrud.Exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

public class IdNotFoundException extends RuntimeException {

	private String msg;
	public IdNotFoundException(String msg) {
		super();
		this.msg = msg;
	}
}
