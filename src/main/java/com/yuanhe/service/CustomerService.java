package com.yuanhe.service;

import java.util.List;

import com.yuanhe.domain.Customer;

public interface CustomerService {
	Customer getCustomerById(String userUnionId);

	void saveCustomer(Customer customer);

	void updateCustomer(Customer customer);

	List<Customer> findCustomerList(int getiDisplayStart, int end, String dealersName);

	int findCustomerCount(String dealersName);
	List<Customer> findCustomerListByDealerId(String dealerId);

}
