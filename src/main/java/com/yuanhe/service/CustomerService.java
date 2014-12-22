package com.yuanhe.service;

import java.util.List;

import com.yuanhe.domain.Customer;

public interface CustomerService {
	Customer getCustomerById(String userUnionId);
	void saveCustomer(Customer customer);
	void updateCustomer(Customer customer);
}
