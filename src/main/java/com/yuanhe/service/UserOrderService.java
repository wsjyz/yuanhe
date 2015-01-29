package com.yuanhe.service;

import java.util.Date;
import java.util.List;

import com.yuanhe.domain.UserOrder;
import com.yuanhe.weixin.proxy.RemoteMethod;

public interface UserOrderService {

	List<UserOrder> getUserOrderList();
	void saveOrder(UserOrder userOrder);
	void updateOrder(UserOrder userOrder);
	List<UserOrder> findOrderList(int getiDisplayStart, int i, String getsSearch,String startTime,String endTime);
	int findOrderCount(String getsSearch, String startTime, String endTime);
	List<UserOrder> findOrderListByDealId(String dealid, String year, String month);
    @RemoteMethod(methodVarNames={ "dealId","startTime","endTime" })
	List<UserOrder> findOrderListByDealIdAndStartDay(String dealId,String startTime,String endTime);
}
