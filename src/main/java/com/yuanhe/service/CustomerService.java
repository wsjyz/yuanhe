package com.yuanhe.service;

import java.util.List;

import com.yuanhe.domain.Customer;
import com.yuanhe.weixin.proxy.RemoteMethod;

public interface CustomerService {
	Customer getCustomerById(String userUnionId);

	void saveCustomer(Customer customer);

	void updateCustomer(Customer customer);

	List<Customer> findCustomerList(int getiDisplayStart, int end, String dealersName);

	int findCustomerCount(String dealersName);
    @RemoteMethod(methodVarNames={ "dealId"})
	List<Customer> findCustomerListByDealerId(String dealerId);
	

}
