package com.yuanhe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.yuanhe.domain.UserOrder;
import com.yuanhe.domain.annotation.Column;
import com.yuanhe.service.UserOrderService;
import com.yuanhe.utils.KDTApiUtils;
import com.yuanhe.utils.WeixinUtils;
import com.yuanhe.utils.WerxinGetUser;
import com.yuanhe.utils.contants;

@Component
public class UserOrderTask {
	private static final String userUrl = "https://api.weixin.qq.com/cgi-bin/user/info";
	@Autowired
	UserOrderService userOrderService;
	@Scheduled(cron = "0/5 * *  * * ? ")
	public void getNewUserOrder() {
		System.out.println("进入订单获取");
		KDTApiUtils apiUtils=new KDTApiUtils();
		List<UserOrder> sendOrderList = apiUtils.sendOrderList();
		List<UserOrder> orderList = userOrderService.getUserOrderList();
		WeixinUtils v = new WeixinUtils();
		String token = v.obtainAccessToken();
		for (UserOrder userOrder : sendOrderList) {
			NewOrder(userOrder, orderList,token);
		}
	}
	private void NewOrder(UserOrder userOrder, List<UserOrder> userOrderOldList,String token) {
		if (userOrder.getOrderStatus().equals("TRADE_CLOSED")) {
			for (UserOrder userOrder2 : userOrderOldList) {
				if (userOrder.getOrderId().equals(userOrder2.getOrderId())) {
					userOrder2.setOrderStatus(contants.REFUND);
					userOrder2.setSalesCommissionMoney("0");
					userOrder2.setMembersCommissionMoney("0");
					userOrder2.setRealPay("0");
					userOrder2.setPostageMoney("0");
					userOrder2.setOrderMoney("0");
					userOrderService.updateOrder(userOrder2);
				}
			}
		} else {
			WerxinGetUser werxinGetUser = new WerxinGetUser();
			String param = "access_token=" + token
					+ "&openid="+userOrder.getWeixin_openid()+"&lang=zh_CN";
			werxinGetUser.sendGet(userUrl, param);

		}
	}
}
