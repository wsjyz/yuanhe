package com.yuanhe.dao;

import java.util.List;

import com.yuanhe.domain.Customer;

public interface CustomerDAO {
	Customer getCustomerById(String userUnionId);
	void saveCustomer(Customer customer);
	void updateCustomer(Customer customer);
}
