package com.jspider.springbootsimplecrud.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.jspider.springbootsimplecrud.dao.CustomerDao;
import com.jspider.springbootsimplecrud.dto.Customer;
import com.jspider.springbootsimplecrud.exception.IdNotFoundException;
import com.jspider.springbootsimplecrud.response.ApplicationResponse;

@Service
public class CustomerService {

	private final CustomerDao customerDao;
	private final ApplicationResponse<Customer> applicationResponse;

	@Autowired
	public CustomerService(CustomerDao customerDao, ApplicationResponse<Customer> applicationResponse) {
		this.customerDao = customerDao;
		this.applicationResponse = applicationResponse;
	}

	/**
	 * Saves a new customer to the database.
	 *
	 * @param customer The customer object to be saved.
	 * @return An ApplicationResponse containing the result of the operation.
	 */
	public ApplicationResponse<Customer> saveCustomerService(Customer customer) {
		Customer existingCustomer = customerDao.getCustomerByEmailDao(customer.getEmail());
		if (existingCustomer != null) {
			return createResponse(HttpStatus.BAD_REQUEST.value(), "Duplicate Data Entry.", customer);
		}

		Customer processedCustomer = ServiceUtils.trimExtraSpaces(customer);
		Customer savedCustomer = customerDao.saveCustomerDao(processedCustomer);

		if (savedCustomer == null) {
			return createResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "We're Sorry. Please Try Again Later",
					null);
		}
		return createResponse(HttpStatus.OK.value(), "Data Stored Successfully", savedCustomer);
	}

	/**
	 * Retrieves a customer by their ID.
	 *
	 * @param id The ID of the customer to retrieve.
	 * @return An ApplicationResponse containing the result of the operation.
	 * @throws IdNotFoundException if the customer with the given ID is not found.
	 */
	public ApplicationResponse<Customer> getCustomerByIdService(int id) {
		Customer customer = customerDao.getCustomerById(id);
		if (customer != null) {
			return createResponse(HttpStatus.OK.value(), "Data Fetched Successfully", customer);
		} else {
			throw new IdNotFoundException("Given Id Not Found...");
		}
	}

	/**
	 * Retrieves all customer details with pagination and sorting.
	 *
	 * @param page          The page number (zero-based) to retrieve.
	 * @param size          The number of items per page.
	 * @param sortBy        The field to sort by.
	 * @param sortDirection The direction of the sort ("asc" or "desc").
	 * @return A Page object containing the requested Customer objects.
	 * @throws IllegalArgumentException if the sortBy field does not exist in the
	 *                                  Customer entity.
	 */
	public Page<Customer> getAllCustomerDetails(int page, int size, String sortBy, String sortDirection) {
		if (page < 0 || size < 0) {
			throw new IllegalArgumentException("Page and size parameters must be non-negative.");
		}

		Sort.Direction direction = sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
		Sort sort = Sort.by(direction, sortBy);
		Pageable pageRequest = PageRequest.of(page, size, sort);

		return customerDao.fetchCustomerByPageDao(pageRequest);
	}

	/**
	 * Deletes a customer by their ID.
	 *
	 * @param customerId The ID of the customer to delete.
	 * @return An ApplicationResponse containing the result of the operation.
	 */
	public ApplicationResponse<Customer> deleteCustomerByIdService(int customerId) {
		Customer existingCustomer = customerDao.getCustomerById(customerId);
		if (existingCustomer == null) {
			return createResponse(HttpStatus.NOT_FOUND.value(), "Customer Not Found", null);
		}

		boolean isDeleted = customerDao.deleteCustomerByIdDao(customerId);
		if (isDeleted) {
			return createResponse(HttpStatus.OK.value(), "Customer Deleted Successfully", existingCustomer);
		} else {
			return createResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to Delete Customer",
					existingCustomer);
		}
	}

	/**
	 * Updates an existing customer's information.
	 *
	 * @param customer The Customer object containing updated information.
	 * @return An ApplicationResponse containing the result of the update operation.
	 */
	public ApplicationResponse<Customer> updateCustomerService(Customer customer) {
		Customer existingCustomer = customerDao.getCustomerById(customer.getId());
		if (existingCustomer == null) {
			return createResponse(HttpStatus.NOT_FOUND.value(), "Customer not found", customer);
		}

		Customer processedCustomer = ServiceUtils.trimExtraSpaces(customer);
		Customer updatedCustomer = customerDao.updateCustomerDao(processedCustomer);

		if (updatedCustomer == null) {
			return createResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to update customer", customer);
		}

		return createResponse(HttpStatus.OK.value(), "Customer updated successfully", updatedCustomer);
	}

	/**
	 * Helper method to create a standardized ApplicationResponse.
	 * 
	 * @param statusValue The HTTP status code of the response
	 * @param message     A descriptive message about the response
	 * @param data        The Customer data associated with the response
	 * @return An ApplicationResponse object with the provided details
	 */
	private ApplicationResponse<Customer> createResponse(int statusValue, String message, Customer data) {
		applicationResponse.setStatusCode(statusValue);
		applicationResponse.setStatusMsg(message);
		applicationResponse.setData(data);
		return applicationResponse;
	}

}