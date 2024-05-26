package com.jspider.springbootsimplecrud.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Repository;

import com.jspider.springbootsimplecrud.dto.Customer;
import com.jspider.springbootsimplecrud.repositry.CustomerRepositry;

@Repository
public class CustomerDao {

	@Autowired
	private CustomerRepositry customerRepositry;

	public Customer saveCustomerDao(Customer customer) {
		try {
			return customerRepositry.save(customer);
		} catch (IllegalArgumentException | OptimisticLockingFailureException e) {
			e.printStackTrace();
			return null;
		}

	}

	public List<Customer> updateMultipleCustomerDao(List<Customer> customers) {
		return customerRepositry.saveAll(customers);
	}

	public Customer getCustomerById(Integer customerId) {
		Optional<Customer> customer = customerRepositry.findById(customerId);
		if (customer.isPresent()) {
			return customer.get();
		} else {
			return null;
		}
	}

	public List<Customer> getCustomerAllDetails(String name) {
		List<Customer> customers = customerRepositry.findAll(Sort.by(name).reverse());
		return customers;
	}

	public List<Customer> getAllCustomerDao() {
		return customerRepositry.findAll();

	}

	public boolean deleteCustomerByIdDao(int customerId) {
		Customer customer = getCustomerById(customerId);
		if (customer != null) {
			customerRepositry.delete(customer);
			return true;
		} else {
			return false;
		}
	}

	public Customer updateCustomerDao(Customer customer) {
		Customer customer1 = getCustomerById(customer.getId());
		if (customer1 != null) {
			customerRepositry.save(customer);
			return customer;
		} else {
			return null;
		}
	}

	public Customer getCustomerByEmailDao(String email) {
		return customerRepositry.findByEmail(email);
	}

	public Customer getCustomerByPhoneDao(long phone) {
		return customerRepositry.getCustomerByPhone(phone);
	}

	public Page<Customer> fetchCustomerByPageDao(int pageNumber, int noOfData) {
		return customerRepositry.findAll(PageRequest.of(pageNumber, noOfData));
	}

	public Page<Customer> fetchCustomerByPageAndSortByNameAscDao(int pageNumber, int noOfData, String name) {
		return customerRepositry.findAll(PageRequest.of(pageNumber, noOfData, Sort.by(Direction.ASC, name)));
	}

}