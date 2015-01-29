package com.yuanhe.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
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

	@Override
	public List<UserOrder> findOrderList(int getiDisplayStart, int i,
			String getsSearch,String startTime,String endTime) {
		return userOrderDAO.findOrderList(getiDisplayStart,i,getsSearch,startTime,endTime);
	}

	@Override
	public int findOrderCount(String getsSearch,String startTime,String endTime) {
		return userOrderDAO.findOrderCount(getsSearch,startTime,endTime);
	}

	@Override
	public List<UserOrder> findOrderListByDealId(String dealid,String year,String month) {
		return userOrderDAO.findOrderListByDealId(dealid,year,month);
	}

	@Override
	public List<UserOrder> findOrderListByDealIdAndStartDay(String dealId,
			String startTime, String endTime) {
		return userOrderDAO.findOrderListByDealIdAndStartDay(dealId,startTime,endTime);
	}

}
