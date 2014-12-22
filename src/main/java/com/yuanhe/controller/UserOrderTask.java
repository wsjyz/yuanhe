package com.yuanhe.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.yuanhe.domain.Customer;
import com.yuanhe.domain.UserOrder;
import com.yuanhe.service.CustomerService;
import com.yuanhe.service.UserAccessRecordService;
import com.yuanhe.service.UserOrderService;
import com.yuanhe.utils.KDTApiUtils;
import com.yuanhe.utils.WerxinGetUser;
import com.yuanhe.utils.contants;

@Component
public class UserOrderTask {
	@Autowired
	UserOrderService userOrderService;
	@Autowired
	CustomerService customerService;
	@Autowired
	UserAccessRecordService userAccessRecordService;

	@Scheduled(cron = "0/5 * *  * * ? ")
	public void getNewUserOrder() {
		System.out.println("进入订单获取");
		KDTApiUtils apiUtils = new KDTApiUtils();
		List<UserOrder> sendOrderList = apiUtils.sendOrderList();
		List<UserOrder> orderList = userOrderService.getUserOrderList();
		for (UserOrder userOrder : sendOrderList) {
			NewOrder(userOrder, orderList);
		}
	}

	private void NewOrder(UserOrder userOrder, List<UserOrder> userOrderOldList) {
		if (userOrder.getOrderStatus().equals("TRADE_CLOSED")) {
			// 如果订单被退回则修改订单信息
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
			setUserOrderByCustomer(userOrder,werxinGetUser);
			String params="";
			String sendGet = werxinGetUser.sendPostByEmail(params,werxinGetUser.getTokenByStore());
		}
	}

	private void setUserOrderByCustomer(UserOrder userOrder,WerxinGetUser werxinGetUser) {
		// 如果是新订单则生成订单且检查订单用户
		// 获取订单用户信息
	
		String param = "access_token=" + werxinGetUser.getTokenByStore()
				+ "&openid=" + userOrder.getWeixin_openid() + "&lang=zh_CN";
		String UserJson = werxinGetUser.sendGetByUser(param);
		JSONObject jsonObj = JSONObject.parseObject(UserJson);
		String userUnionId = jsonObj.getString("unionid");
		String visiterDealersId = null;
		// 检查买家是否是第一次购买
		Customer customer = customerService.getCustomerById(userUnionId);
		String xiaoshouId = null;
		String huiyuanID = null;
		if (customer == null) {
			visiterDealersId = userAccessRecordService
					.getDealersIdByUnionId(userUnionId);
			if (StringUtils.isEmpty(visiterDealersId)) {
				visiterDealersId = "yuanhe";
			}
			customer = new Customer();
			customer.setCustomerUnionId(userUnionId);
			customer.setCustomerNick(jsonObj.getString("nickname"));
			customer.setCustomerPic(jsonObj.getString("headimgurl"));
			customer.setCustomerProvice(jsonObj.getString("province"));
			customer.setCustomerDealers(visiterDealersId);
			customer.setOpenId(jsonObj.getString("openid"));
			// 待确定
			customer.setOuathOpenId(userOrder.getWeixin_openid());
			customerService.saveCustomer(customer);
			xiaoshouId = visiterDealersId;
			huiyuanID = visiterDealersId;
		} else {
			customer.setCustomerNick(jsonObj.getString("nickname"));
			customer.setCustomerPic(jsonObj.getString("headimgurl"));
			customer.setCustomerProvice(jsonObj.getString("province"));
			customer.setOpenId(jsonObj.getString("openid"));
			// 待确定
			customer.setOuathOpenId(userOrder.getWeixin_openid());
			customerService.updateCustomer(customer);
			visiterDealersId = userAccessRecordService
					.getDealersIdByUnionId(userUnionId);
			if (StringUtils.isEmpty(visiterDealersId)) {
				visiterDealersId = "yuanhe";
			}
			xiaoshouId = customer.getCustomerDealers();
			huiyuanID = visiterDealersId;
		}
		Double money = new Double(0);
		if (StringUtils.isNotEmpty(userOrder.getRealPay())) {
			money = (double) (Integer
					.parseInt(userOrder.getRealPay() == null ? "0"
							: userOrder.getRealPay()) - Integer
					.parseInt(userOrder.getPostageMoney() == null ? "0"
							: userOrder.getPostageMoney()));
			money = money - money / 17 * 100;
		}
		userOrder.setOrderMoney(money.toString());
		userOrder.setBelongsSalesCommission(xiaoshouId);
		userOrder.setSalesCommissionMoney((money * 0.16) + "");
		userOrder.setBelongsMembersCommission(huiyuanID);
		userOrder.setMembersCommissionMoney(money * 0.04 + "");
		userOrderService.updateOrder(userOrder);
	}
}
