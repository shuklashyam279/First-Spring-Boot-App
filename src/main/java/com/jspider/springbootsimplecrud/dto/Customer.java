package com.jspider.springbootsimplecrud.dto;

import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Transactional
public class Customer {

	@Id
	private int id;
	private String name;
	private String email;
	private long phone;
}