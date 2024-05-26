package com.jspider.springbootsimplecrud.service;

import java.util.List;

import org.hibernate.annotations.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.jspider.springbootsimplecrud.Exception.IdNotFoundException;
import com.jspider.springbootsimplecrud.dao.CustomerDao;
import com.jspider.springbootsimplecrud.dto.Customer;
import com.jspider.springbootsimplecrud.response.ApplicationResponse;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerDao customerDao;
	
	@Autowired
	private ApplicationResponse<Customer> applicationResponse;

	public ApplicationResponse<Customer> saveCustomerService(Customer customer) {
		Customer savedCustomer = customerDao.saveCustomerDao(customer);
		if(savedCustomer!=null) {
			applicationResponse.setStatusCode(HttpStatus.CREATED.value());
			applicationResponse.setStatusMsg("Data Stored Successfully");
//			applicationResponse.setDescription("I have created save data API");
			applicationResponse.setData(customer);
			return applicationResponse;
		}
		else {
			applicationResponse.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
			applicationResponse.setStatusMsg("Could Not Store Information, Please Varify Your Data.");
//			applicationResponse.setDescription("");
			applicationResponse.setData(savedCustomer);
			return applicationResponse;
		}
	}
	
	public List<Customer> getAllCustomerDetailsFilterByNameService(String name) {
		return customerDao.getAllCustomerDao().stream().filter(a -> a.getName().equals(name)).toList();
	}

	public ApplicationResponse<Customer> getCustomerByIdService(int id){
		Customer customer = customerDao.getCustomerById(id);
		if(customer!=null) {
			applicationResponse.setStatusCode(HttpStatus.CREATED.value());
			applicationResponse.setStatusMsg("Data Fetched Successfully");
//			applicationResponse.setDescription("I have created save data API");
			applicationResponse.setData(customer);
			return applicationResponse;
		}
		else {
			throw new IdNotFoundException("Given Id Not Found...");
		}
	}

}

//@Service-It is used to tell the compiler that we are writing business logic or service login in the class.
//@Autowired-It is used to create instance of class.