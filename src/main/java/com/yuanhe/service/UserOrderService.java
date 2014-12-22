package com.yuanhe.service;

import java.util.List;

import com.yuanhe.domain.UserOrder;

public interface UserOrderService {

	List<UserOrder> getUserOrderList();
	void saveOrder(UserOrder userOrder);
	void updateOrder(UserOrder userOrder);
}
