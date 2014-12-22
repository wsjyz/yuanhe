package com.yuanhe.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanhe.dao.UserOrderDAO;
import com.yuanhe.domain.UserOrder;
import com.yuanhe.service.UserOrderService;

@Service("UserOrderService")
public class UserOrderServiceImpl implements UserOrderService {

	@Autowired
	UserOrderDAO userOrderDAO;

	public List<UserOrder> getUserOrderList() {

		return userOrderDAO.getOrderList();
	}

	@Override
	public void saveOrder(UserOrder userOrder) {
		userOrderDAO.saveOrder(userOrder);
	}

	@Override
	public void updateOrder(UserOrder userOrder) {
		userOrderDAO.updateOrder(userOrder);
	}

}
