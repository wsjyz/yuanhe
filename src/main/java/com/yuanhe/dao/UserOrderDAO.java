package com.yuanhe.dao;

import java.util.List;

import com.yuanhe.domain.UserOrder;

public interface UserOrderDAO {
	List<UserOrder> getOrderList();
	void saveOrder(UserOrder userOrder);
	void updateOrder(UserOrder userOrder);
	List<UserOrder> findOrderList(int getiDisplayStart, int i, String getsSearch,String startTime,String endTime);
	int findOrderCount(String getsSearch, String startTime, String endTime);
	List<UserOrder> findOrderListByDealId(String dealid, String year, String month);
	List<UserOrder> findOrderListByDealIdAndStartDay(String dealId,
			String format, String format2);
}
