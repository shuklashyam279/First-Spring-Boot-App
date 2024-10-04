package com.jspider.springbootsimplecrud.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jspider.springbootsimplecrud.dto.Customer;
import com.jspider.springbootsimplecrud.repository.CustomerRepository;

@Repository
public class CustomerDao {

	private final CustomerRepository customerRepository;

	@Autowired
	public CustomerDao(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	/**
	 * Saves a customer to the database.
	 *
	 * @param customer The customer object to be saved.
	 * @return The saved customer object.
	 * @throws IllegalArgumentException If the customer data is invalid.
	 * @throws RuntimeException If there's a concurrent modification issue.
	 */
	public Customer saveCustomerDao(Customer customer) {
		try {
			return customerRepository.save(customer);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid customer data: " + e.getMessage());
		} catch (OptimisticLockingFailureException e) {
			throw new RuntimeException("Failed to save customer due to concurrent modification", e);
		}
	}

	/**
	 * Updates multiple customers in the database.
	 *
	 * @param customers A list of Customer objects to be updated.
	 * @return A list of updated Customer objects.
	 * @throws IllegalArgumentException If the list is null or contains invalid customer data.
	 * @throws RuntimeException If there's a concurrent modification issue.
	 */
	public List<Customer> updateMultipleCustomersDao(List<Customer> customers) {
		if (customers == null || customers.isEmpty()) {
			throw new IllegalArgumentException("Customer list cannot be null or empty");
		}
		try {
			return customerRepository.saveAll(customers);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid customer data in the list: " + e.getMessage());
		} catch (OptimisticLockingFailureException e) {
			throw new RuntimeException("Failed to update customers due to concurrent modification", e);
		}
	}

	/**
	 * Retrieves a customer from the database by their ID.
	 *
	 * @param customerId The ID of the customer to retrieve.
	 * @return The Customer object if found, or null if not found.
	 */
	public Customer getCustomerById(Integer customerId) {
		Optional<Customer> customer = customerRepository.findById(customerId);
		if (customer.isPresent()) {
			return customer.get();
		} else {
			return null;
		}
	}

	/**
	 * Retrieves all customer details sorted by the specified field in descending order.
	 *
	 * @param name The name of the field to sort by.
	 * @return A list of all customers sorted in descending order by the specified field.
	 */
	public List<Customer> getCustomerAllDetails(String name) {
		return customerRepository.findAll(Sort.by(name).reverse());
	}

	/**
	 * Retrieves all customers from the database.
	 *
	 * @return A list of all Customer objects in the database.
	 */
	public List<Customer> getAllCustomerDao() {
		return customerRepository.findAll();
	}

	/**
	 * Deletes a customer from the database by their ID.
	 *
	 * @param customerId The ID of the customer to delete.
	 * @return true if the customer was successfully deleted, false if the customer was not found.
	 */
	public boolean deleteCustomerByIdDao(int customerId) {
		Customer customer = getCustomerById(customerId);
		if (customer != null) {
			customerRepository.delete(customer);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Updates an existing customer in the database.
	 *
	 * @param customer The Customer object with updated information.
	 * @return The updated Customer object if the customer exists, null if the customer was not found.
	 */
	public Customer updateCustomerDao(Customer customer) {
		Customer customer1 = getCustomerById(customer.getId());
		if (customer1 != null) {
			customerRepository.save(customer);
			return customer;
		} else {
			return null;
		}
	}

	/**
	 * Retrieves a customer from the database by their email address.
	 *
	 * @param email The email address of the customer to retrieve.
	 * @return The Customer object if found, or null if not found.
	 */
	public Customer getCustomerByEmailDao(String email) {
		return customerRepository.findByEmail(email);
	}

	/**
	 * Retrieves a customer from the database by their phone number.
	 *
	 * @param phone The phone number of the customer to retrieve.
	 * @return The Customer object if found, or null if not found.
	 */
	public Customer getCustomerByPhoneDao(long phone) {
		return customerRepository.getCustomerByPhone(phone);
	}

	/**
	 * Retrieves a page of customers from the database.
	 *
	 * @param pageable The Pageable object containing pagination information.
	 * @return A Page of Customer objects.
	 */
	public Page<Customer> fetchCustomerByPageDao(Pageable pageable) {
		return customerRepository.findAll(pageable);
	}

	/**
	 * Retrieves a page of customers from the database, sorted by name in ascending order.
	 *
	 * @param pageNumber The page number to retrieve (zero-based).
	 * @param noOfData The number of customers per page.
	 * @param name The name of the field to sort by (assumed to be "name" in this case).
	 * @return A Page of Customer objects sorted by name in ascending order.
	 */
	public Page<Customer> fetchCustomerByPageAndSortByNameAscDao(int pageNumber, int noOfData, String name) {
		return customerRepository.findAll(PageRequest.of(pageNumber, noOfData, Sort.by(Direction.ASC, name)));
	}

}