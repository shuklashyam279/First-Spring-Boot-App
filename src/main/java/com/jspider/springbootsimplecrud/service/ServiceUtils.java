package com.jspider.springbootsimplecrud.service;

import com.jspider.springbootsimplecrud.dto.Customer;

public class ServiceUtils {
	
	private ServiceUtils() {
        // Prevent instantiation
    }
	
	static Customer trimExtraSpaces(Customer customer) {
	    String name = customer.getName().trim().replaceAll("\\s+", " ");
	    String email = customer.getEmail().trim();
	    customer.setName(name);
	    customer.setEmail(email);
	    return customer;
	}
}