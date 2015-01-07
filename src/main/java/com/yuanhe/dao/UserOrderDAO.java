package com.yuanhe.dao;

import java.util.List;

import com.yuanhe.domain.UserOrder;

public interface UserOrderDAO {
	List<UserOrder> getOrderList();
	void saveOrder(UserOrder userOrder);
	void updateOrder(UserOrder userOrder);
	List<UserOrder> findOrderList(int getiDisplayStart, int i, String getsSearch);
	int findOrderCount(String getsSearch);
}
