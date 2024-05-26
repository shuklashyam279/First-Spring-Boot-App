package com.jspider.springbootsimplecrud.repositry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.jspider.springbootsimplecrud.dto.Customer;

public interface CustomerRepositry extends JpaRepository<Customer, Integer>{


	// It will generate custom query to fetch customer by email.

	public Customer findByEmail(String email);

	// @Query annotation is used to convey that it's our custom JPQL query.
	@Query(value = "SELECT c FROM Customer c WHERE c.phone=?1")
	public Customer getCustomerByPhone(long phone);

//	In Spring Data JPA, the @Transactional and @Modifying annotations are often used together, especially when you’re dealing with modifying queries.
	
//	@Transactional annotation is used to define the scope of a single database transaction. 
//	The database transaction happens within the boundaries of a persistence context.
//	@Transactional is not strictly required with @Modifying, but it’s often used to ensure that the method executes within a transaction


//	@Modifying annotation is used with the @Query annotation to indicate that a query changes data.
//	2. It tells Spring Data JPA to call Query.executeUpdate() instead of Query.getSingleResult() or Query.getResultList()

	@Transactional
	@Modifying
	@Query(value="DELETE from Customer c where c.id=?1")
	public void deleteCustomerById(int customerId);

}
