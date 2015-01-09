package com.yuanhe.dao;

import java.util.List;

import com.yuanhe.domain.Customer;

public interface CustomerDAO {
	Customer getCustomerById(String userUnionId);
	void saveCustomer(Customer customer);
	void updateCustomer(Customer customer);
	int findCustomerCount(String dealersName);
	List<Customer> findCustomerList(int getiDisplayStart, int end,
			String dealersName);
	List<Customer> findCustomerListByDealerId(String dealerId);
}
