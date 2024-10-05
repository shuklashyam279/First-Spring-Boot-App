package com.jspider.springbootsimplecrud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import com.jspider.springbootsimplecrud.dao.CustomerDao;
import com.jspider.springbootsimplecrud.dto.Customer;
import com.jspider.springbootsimplecrud.response.ApplicationResponse;
import com.jspider.springbootsimplecrud.service.CustomerService;

@RestController
public class CustomerController {

	private final CustomerDao customerDao;
	private final CustomerService customerService;

	@Autowired
	public CustomerController(CustomerDao customerDao, CustomerService customerService) {
		this.customerDao = customerDao;
		this.customerService = customerService;
	}

	/**
	 * Saves a new customer to the database.
	 *
	 * @param customer The Customer object to be saved, provided in the request body.
	 * @return An ApplicationResponse containing the saved Customer object and relevant status information.
	 */
	@PostMapping(value = "/saveCustomer")
	public ApplicationResponse<Customer> saveCustomerController(@RequestBody Customer customer) {
		return customerService.saveCustomerService(customer);
	}

	/**
	 * Retrieves a customer from the database by their ID.
	 *
	 * @param customerId The ID of the customer to retrieve, provided as a path variable.
	 * @return An ApplicationResponse containing the retrieved Customer object and relevant status information.
	 *         If the customer is not found, the response will contain appropriate error details.
	 */
	@GetMapping(value = "/getCustomerById/{customerId}")
	public ApplicationResponse<Customer> getCustomerByIdController(@PathVariable int customerId) {
		return customerService.getCustomerByIdService(customerId);
	}



	/**
	 * Retrieves all customer details with pagination and sorting options.
	 *
	 * @param page The page number (zero-based) of the results to fetch. Defaults to 0.
	 * @param size The number of items to return per page. Defaults to 10.
	 * @param sortBy The field to sort the results by. Defaults to "id".
	 * @param sortDirection The direction to sort the results. Can be "asc" for ascending or "desc" for descending. Defaults to "asc".
	 * @return A ResponseEntity containing a Page of Customer objects and HTTP status OK (200).
	 */
	@GetMapping(value = "/getAllCustomerDetails")
	public ResponseEntity<Page<Customer>> getAllCustomerDetails(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(defaultValue = "asc") String sortDirection) {
		Page<Customer> customers = customerService.getAllCustomerDetails(page, size, sortBy, sortDirection);
		return ResponseEntity.ok(customers);
	}

	/**
	 * Deletes a customer from the database by their ID.
	 *
	 * @param customerId The ID of the customer to delete, provided as a path variable.
	 * @return An ApplicationResponse containing the deleted Customer object (if successful) and relevant status information.
	 *         If the customer is not found, the response will contain appropriate error details.
	 */
	@DeleteMapping(value = "/deleteCustomerById/{customerId}")
	public ApplicationResponse<Customer> deleteCustomerByIdController(@PathVariable int customerId) {
		return customerService.deleteCustomerByIdService(customerId);
	}

	/**
	 * Updates an existing customer in the database.
	 *
	 * @param customer The Customer object with updated information, provided in the request body.
	 * @return The updated Customer object if the customer exists, null if the customer was not found.
	 */
	@PutMapping(value = "/updateCustomer")
	public Customer updateCustomerController(@RequestBody Customer customer) {
		return customerDao.updateCustomerDao(customer);
	}

	/**
	 * Retrieves a customer from the database by their email address.
	 *
	 * @param email The email address of the customer to retrieve, provided as a path variable.
	 * @return The Customer object if found, or null if not found.
	 */
	@GetMapping(value = "/getCustomerByEmail/{email}")
	public Customer getCustomerByEmailController(@PathVariable String email) {
		return customerDao.getCustomerByEmailDao(email);
	}

	/**
	 * Retrieves a customer from the database by their phone number.
	 *
	 * @param phone The phone number of the customer to retrieve, provided as a path variable.
	 * @return The Customer object if found, or null if not found.
	 */
	@GetMapping(value = "/getCustomerByPhone/{phone}")
	public Customer getCustomerByPhoneController(@PathVariable long phone) {
		return customerDao.getCustomerByPhoneDao(phone);
	}
	
	/**
	 * Retrieves a page of customers sorted by name in ascending order.
	 *
	 * @param pageNumber The number of the page to retrieve (zero-based).
	 * @param noOfData The number of customers to include per page.
	 * @param name The name of the field to sort by (assumed to be "name" in this case).
	 * @return A Page object containing the requested customers.
	 */
	@GetMapping(value = "/getCustomerPage/{pageNumber}/{noOfData}/{name}")
	public Page<Customer> fetchCustomerByPageAndSortByNameAscController(@PathVariable int pageNumber,
	@PathVariable int noOfData, @PathVariable String name) {
		return customerDao.fetchCustomerByPageAndSortByNameAscDao(pageNumber, noOfData, name);
	}
	

}