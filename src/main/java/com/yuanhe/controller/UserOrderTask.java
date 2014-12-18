package com.yuanhe.controller;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.yuanhe.domain.UserOrder;
import com.yuanhe.utils.KDTApiUtils;

@Component
public class UserOrderTask {
	 @Scheduled(cron="0/5 * 0/5  * * ? ")   //每5秒执行一次
	public void getNewUserOrder() {
		System.out.println("进入订单获取");
		KDTApiUtils apiUtils=new KDTApiUtils();
		apiUtils.sendOrderList();
	}
	 private void NewOrder(UserOrder userOrder,List<UserOrder> userOrderOldList ){
			for (UserOrder userOrder2 : userOrderOldList) {
				if (userOrder.getOrderId().equals(userOrder2.getOrderId()) && userOrder2.getOrderStatus().equals("REFUND")) {
					
				}
			}
		}
}
