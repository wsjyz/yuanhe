package com.yuanhe.dao;

import java.util.List;

import com.yuanhe.eighth.domain.UserOrder;

public interface UserOrderDAO {
	List<UserOrder> getOrderList();
	void saveOrderList(List<UserOrder> userOrderList);
	void updateOrder(UserOrder userOrder);
}
