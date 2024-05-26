package com.jspider.springbootsimplecrud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jspider.springbootsimplecrud.dao.CustomerDao;
import com.jspider.springbootsimplecrud.dto.Customer;
import com.jspider.springbootsimplecrud.response.ApplicationResponse;
import com.jspider.springbootsimplecrud.service.CustomerService;

@RestController
public class CustomerController {

	@Autowired
	private CustomerDao customerDao;

	@Autowired
	private CustomerService customerService;

	@PostMapping(value = "/saveCustomer")
	public ApplicationResponse<Customer> saveCustomerController(@RequestBody Customer customer) {
		return customerService.saveCustomerService(customer);
	}

	@GetMapping(value = "/getCustomerById/{customerId}")
	public ApplicationResponse<Customer> getCustomerByIdController(@PathVariable int customerId) {
		return customerService.getCustomerByIdService(customerId);
	}

	@GetMapping(value = "/getAllCustomerDetails/{name}")
	public List<Customer> getAllCustomerDetailsSortedByName(@PathVariable String name) {
		return customerDao.getCustomerAllDetails(name);
	}

	@DeleteMapping(value = "/deleteCustomerById/{customerId}")
	public ResponseEntity<String> deleteCustomerByIdController(@PathVariable int customerId) {
		boolean b = customerDao.deleteCustomerByIdDao(customerId);
		if (b) {
			return new ResponseEntity<String>("data deleted", HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<String>("id not found", HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping(value = "/updateCustomer")
	public Customer updateCustomerController(@RequestBody Customer customer) {
		return customerDao.updateCustomerDao(customer);
	}

	@PostMapping(value = "/updateMultipleCustomer")
	public List<Customer> updateMultipleCustomerController(@RequestBody List<Customer> customer) {
		return customerDao.updateMultipleCustomerDao(customer);
	}

	@GetMapping(value = "/getCustomerByEmail/{email}")
	public Customer getCustomerByEmailController(@PathVariable String email) {
		return customerDao.getCustomerByEmailDao(email);
	}

	@GetMapping(value = "/getCustomerByPhone/{phone}")
	public Customer getCustomerByPhoneController(@PathVariable long phone) {
		return customerDao.getCustomerByPhoneDao(phone);
	}

	@GetMapping(value = "/getAllCustomerDetailsFilterByName/{name}")
	public List<Customer> getAllCustomerDetailsFilterByName(@PathVariable String name) {
		return customerService.getAllCustomerDetailsFilterByNameService(name);
	}

	@GetMapping(value = "/getCustomerPage/{pageNumber}/{noOfData}")
	public Page<Customer> fetchCustomerByPageController(@PathVariable int pageNumber, @PathVariable int noOfData) {
		return customerDao.fetchCustomerByPageDao(pageNumber, noOfData);
	}

	@GetMapping(value = "/getCustomerPage/{pageNumber}/{noOfData}/{name}")
	public Page<Customer> fetchCustomerByPageAndSortByNameAscController(@PathVariable int pageNumber,
			@PathVariable int noOfData, @PathVariable String name) {
		return customerDao.fetchCustomerByPageAndSortByNameAscDao(pageNumber, noOfData, name);
	}

}

//What is application.properties and why we use that?
//It is a configuration file which is used to configure database connection,log generation, server configuration(Changing the port of server) 

//What is @pathvariable
//It is used to bind the variable with url.It is used for primitive data-type

//What is @requestbody
//It is used to bind object with the url.It is used for non primitive data-type

//How to connect Spring Boot Application with database
//We can connect spring boot application with database in two ways
//1->By Application.Properties file
//2->By Application.yml file

//Classes and Interfaces of Spring Data JPA

//JPARepositry<T,ID>			T->EntityClassname, ID->PrimaryKey Wrapper ClassName

// save(entity):T
// saveAll(entity):T
// delete(id):void
