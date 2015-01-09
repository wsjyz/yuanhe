package com.yuanhe.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.yuanhe.domain.Customer;
import com.yuanhe.domain.Dealers;
import com.yuanhe.domain.UserOrder;
import com.yuanhe.service.CustomerService;
import com.yuanhe.service.DealersService;
import com.yuanhe.service.UserAccessRecordService;
import com.yuanhe.service.UserOrderService;
import com.yuanhe.utils.KDTApiUtils;
import com.yuanhe.utils.WeixinByUserUtils;
import com.yuanhe.utils.WerxinGetUser;
import com.yuanhe.utils.Contants;

@Component
public class UserOrderTask {
	protected static Logger logger = Logger.getLogger(UserOrderTask.class);
	@Autowired
	UserOrderService userOrderService;
	@Autowired
	CustomerService customerService;
	@Autowired
	DealersService dealersService;
	@Autowired
	UserAccessRecordService userAccessRecordService;

	@Scheduled(cron = "0 0/5 *  * * ? ")
	public void getNewUserOrder() {
		logger.info("进入订单获取");
		try {
			KDTApiUtils apiUtils = new KDTApiUtils();
			List<UserOrder> sendOrderList = apiUtils.sendOrderList();
			List<UserOrder> orderList = userOrderService.getUserOrderList();
			for (UserOrder userOrder : sendOrderList) {
				NewOrder(userOrder, orderList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("订单获取完毕 ");
	}

	private void NewOrder(UserOrder userOrder, List<UserOrder> userOrderOldList) {
		if (userOrder.getOrderStatus().equals(Contants.REFUND)
				&& !CollectionUtils.isEmpty(userOrderOldList)) {
			// 如果订单被退回则修改订单信息
			for (UserOrder userOrder2 : userOrderOldList) {
				if (userOrder.getOrderId().equals(userOrder2.getOrderId()) && !userOrder.getOrderStatus().equals(Contants.REFUND)) {
					userOrder2.setSalesCommissionMoney("0");
					userOrder2.setMembersCommissionMoney("0");
					userOrder2.setRealPay("0");
					userOrder2.setPostageMoney("0");
					userOrder2.setOrderMoney("0");
					userOrderService.updateOrder(userOrder2);
					refundOrderSend(userOrder, userOrder2);
					logger.info("有新订单更新 ");
				}
			}
		} else {
			boolean isCheck = false;
			if (!CollectionUtils.isEmpty(userOrderOldList)) {
				for (UserOrder userOrder2 : userOrderOldList) {
					if (userOrder.getOrderId().equals(userOrder2.getOrderId())) {
						isCheck = true;
						break;
					}
				}
			}
			if (!isCheck) {
				WerxinGetUser werxinGetUser = new WerxinGetUser();
				Customer customer = new Customer();
				setUserOrderByCustomer(userOrder, werxinGetUser, customer);
				addOrderSend(userOrder, werxinGetUser, customer);
				logger.info("有新订单添加");
			}
		}
	}

	private void refundOrderSend(UserOrder userOrder, UserOrder userOrder2) {
		WerxinGetUser werxinGetUser = new WerxinGetUser();
		Customer customer=customerService.getCustomerById(userOrder2.getPaymentUnionId());
		Dealers dealers = dealersService.getDealersByID(userOrder.getBelongsMembersCommission());
		if (dealers!=null && StringUtils.isNotEmpty(dealers.getDealersId()) && !"0".equals(dealers.getDealersId())) {
			String params = "{\"touser\": \""
					+ userOrder.getBelongsMembersCommission()
					+ "\",\"toparty\": \""
					+ customer.getCustomerDealers()
					+ "\",\"totag\": \""
					+ 3
					+ "\",\"msgtype\": \"text\",\"agentid\": \"1\",\"text\": {\"content\": \"尊敬的"+dealers.getDealersName()+",客户"
					+customer.getCustomerNick()+"购买的"+userOrder.getCommodityName()
					+",做了退款处理，原来获得的销售佣金变为0!\"},\"safe\":\"0\"}";
			werxinGetUser.sendPostByEmail(params,werxinGetUser.getTokenByShop());

		}
		Dealers dealers1 = dealersService.getDealersByID(userOrder.getBelongsSalesCommission());
		if (dealers1!=null && StringUtils.isNotEmpty(dealers1.getDealersId()) && !"0".equals(dealers1.getDealersId())) {
			String params1 = "{\"touser\": \""
					+ userOrder.getBelongsMembersCommission()+"|"+userOrder.getBelongsSalesCommission()
					+ "\",\"toparty\": \""
					+ customer.getCustomerDealers()
					+ "\",\"totag\": \""
					+ 3
					+ "\",\"msgtype\": \"text\",\"agentid\": \"1\",\"text\":  {\"content\": \"尊敬的"+dealers1.getDealersName()+",客户"
					+customer.getCustomerNick()+"购买的"+userOrder.getCommodityName()
					+",做了退款处理，原来获得的会员佣金变为0!\"},\"safe\":\"0\"}";
			werxinGetUser.sendPostByEmail(params1,
					werxinGetUser.getTokenByShop());
		
		}
	}

	private void addOrderSend(UserOrder userOrder, WerxinGetUser werxinGetUser,
			Customer customer) {
		Dealers dealers = dealersService.getDealersByID(userOrder.getBelongsMembersCommission());
		Calendar cal=Calendar.getInstance();
		if (dealers!=null && StringUtils.isNotEmpty(dealers.getDealersId()) && !"0".equals(dealers.getDealersId())) {
		String params = "{\"touser\": \""
				+ userOrder.getBelongsMembersCommission()
				+ "\",\"toparty\": \""
				+ customer.getCustomerDealers()
				+ "\",\"totag\": \""
				+ 3
				+ "\",\"msgtype\": \"text\",\"agentid\": \"1\",\"text\": {\"content\": \"尊敬的"+dealers.getDealersName()+",恭喜您获得一笔新佣金，客户"
				+customer.getCustomerNick()+"购买的"+userOrder.getCommodityName()
				+"已经于"+cal.get(Calendar.YEAR)+"年"+(cal.get(Calendar.MONTH)+1)+"月"+cal.get(Calendar.DAY_OF_MONTH)+"日签收，您获得佣金："+userOrder.getMembersCommissionMoney()+"元，佣金类型：会员佣金\"},\"safe\":\"0\"}";
		werxinGetUser.sendPostByEmail(params,
				werxinGetUser.getTokenByShop());
		}
		Dealers dealers1 = dealersService.getDealersByID(userOrder.getBelongsSalesCommission());
		if (dealers1!=null && StringUtils.isNotEmpty(dealers1.getDealersId()) && !"0".equals(dealers1.getDealersId())) {
			String params1 = "{\"touser\": \""
					+ userOrder.getBelongsMembersCommission()+"|"+userOrder.getBelongsSalesCommission()
					+ "\",\"toparty\": \""
					+ customer.getCustomerDealers()
					+ "\",\"totag\": \""
					+ 3
					+ "\",\"msgtype\": \"text\",\"agentid\": \"1\",\"text\":  {\"content\": \"尊敬的"+dealers1.getDealersName()+",恭喜您获得一笔新佣金，客户"
					+customer.getCustomerNick()+"购买的"+userOrder.getCommodityName()
					+"已经于"+cal.get(Calendar.YEAR)+"年"+(cal.get(Calendar.MONTH)+1)+"月"+cal.get(Calendar.DAY_OF_MONTH)+"日签收，您获得佣金："+userOrder.getSalesCommissionMoney()+"元，佣金类型：销售佣金\"},\"safe\":\"0\"}";
			werxinGetUser.sendPostByEmail(params1,
					werxinGetUser.getTokenByShop());
		}
	}

	private void setUserOrderByCustomer(UserOrder userOrder,
			WerxinGetUser werxinGetUser, Customer customer) {
		// 如果是新订单则生成订单且检查订单用户
		// 获取订单用户信息

		String param = "access_token=" + werxinGetUser.getTokenByStore()
				+ "&openid=" + userOrder.getWeixin_openid() + "&lang=zh_CN";
		String UserJson = werxinGetUser.sendGetByUser(param);
		JSONObject jsonObj = JSONObject.parseObject(UserJson);
		String userUnionId = jsonObj.getString("unionid");
		String visiterDealersId = null;
		// 检查买家是否是第一次购买
		customer = customerService.getCustomerById(userUnionId);
		String xiaoshouId = null;
		String huiyuanID = null;
		if (customer == null) {
			visiterDealersId = userAccessRecordService
					.getDealersIdByUnionId(userUnionId);
			if (StringUtils.isEmpty(visiterDealersId)) {
				visiterDealersId = dealersService.getYuanHeDealersId();
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
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			customer.setCteateTime(sdf.format(cal.getTime()));
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
				//如果没有访问的经销商ID，就用原来的元和的ID
				visiterDealersId =  dealersService.getYuanHeDealersId();
			}
			xiaoshouId = customer.getCustomerDealers();
			huiyuanID = visiterDealersId;
		}
		userOrder.setPaymentWeixinNick(customer.getCustomerNick());
		userOrder.setPaymentUnionId(customer.getCustomerUnionId());
		Double money = new Double(0);
		if (StringUtils.isNotEmpty(userOrder.getRealPay())
				&& !money.equals("0.0")) {
			money = (double) (Double
					.parseDouble(userOrder.getRealPay() == null ? "0"
							: userOrder.getRealPay()) - Double
					.parseDouble(userOrder.getPostageMoney() == null ? "0"
							: userOrder.getPostageMoney()));
			money = money - money * 0.17;
		}
		userOrder.setOrderMoney(money.toString().substring(0,
				money.toString().indexOf(".") + 3));
		userOrder.setBelongsSalesCommission(xiaoshouId);
		userOrder.setSalesCommissionMoney(((money * 0.16) + "").substring(0,
				((money * 0.16) + "").indexOf(".") + 3));
		userOrder.setBelongsMembersCommission(huiyuanID);
		userOrder.setMembersCommissionMoney((money * 0.04 + "").substring(0,
				((money * 0.04) + "").indexOf(".") + 3));
		userOrderService.saveOrder(userOrder);
	}
}
