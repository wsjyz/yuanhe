package com.yuanhe.dao;

import java.util.List;

import com.yuanhe.domain.Customer;

public interface CustomerDAO {
	List<Customer> getCustomerList(String userUnionId);
	void saveCustomer(Customer customer);
	void updateCustomer(Customer customer);
}
