package com.yuanhe.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanhe.dao.CustomerDAO;
import com.yuanhe.domain.Customer;
import com.yuanhe.service.CustomerService;

@Service("CustomerService")
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	CustomerDAO customerDao;

	@Override
	public Customer getCustomerById(String userUnionId) {
		return customerDao.getCustomerById(userUnionId);
	}

	@Override
	public void saveCustomer(Customer customer) {
		customerDao.saveCustomer(customer);
	}

	@Override
	public void updateCustomer(Customer customer) {
		customerDao.updateCustomer(customer);
	}

}
