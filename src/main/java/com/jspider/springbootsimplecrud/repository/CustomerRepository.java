package com.jspider.springbootsimplecrud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.jspider.springbootsimplecrud.dto.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	
	/**
	 * Finds a customer by their email address.
	 * This method generates a custom query to fetch the customer.
	 *
	 * @param email The email address of the customer to find.
	 * @return The Customer object if found, null otherwise.
	 */
	public Customer findByEmail(String email);

	/**
	 * Retrieves a customer by their phone number using a custom JPQL query.
	 *
	 * @param phone The phone number of the customer to retrieve.
	 * @return The Customer object if found, null otherwise.
	 */
	@Query(value = "SELECT c FROM Customer c WHERE c.phone=?1")
	public Customer getCustomerByPhone(long phone);

	/**
	 * Deletes a customer by their ID.
	 * 
	 * This method uses both @Transactional and @Modifying annotations:
	 * - @Transactional ensures the method executes within a database transaction.
	 * - @Modifying indicates that this query modifies the database.
	 *
	 * @param customerId The ID of the customer to delete.
	 */
	@Transactional
	@Modifying
	@Query(value="DELETE from Customer c where c.id=?1")
	public void deleteCustomerById(int customerId);

	

}