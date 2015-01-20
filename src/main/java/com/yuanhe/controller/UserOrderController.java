package com.yuanhe.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yuanhe.domain.PageModel;
import com.yuanhe.domain.UserOrder;
import com.yuanhe.service.UserOrderService;
import com.yuanhe.weixin.bean.WeixinUser;
import com.yuanhe.weixin.util.WeixinOauth;

@Controller
@RequestMapping(value = "/order")
public class UserOrderController {

	@Autowired
	UserOrderService userOrderService;

	@RequestMapping(value = "/toOrder")
	public ModelAndView toAddGoods(@RequestParam String startTime, @RequestParam String endTime) {
		ModelAndView view = new ModelAndView();
		view.setViewName("/order/orderlist");
		Map<String, Object> model = view.getModel();
		model.put("startTime", startTime);
		model.put("endTime", endTime);
		return view;
	}

	@ResponseBody
	@RequestMapping(value = "/get-orderlist")
	public PageModel getGoodsPage(PageModel ptFromPage,
			@RequestParam String startTime, @RequestParam String endTime) {
		List<UserOrder> userOrdersList = userOrderService.findOrderList(
				ptFromPage.getiDisplayStart(), ptFromPage.getiDisplayLength(),
				ptFromPage.getsSearch(), startTime, endTime);
		int count = userOrderService.findOrderCount(ptFromPage.getsSearch(), startTime, endTime);
		PageModel pt = new PageModel();
		pt.setsEcho(ptFromPage.getsEcho());
		pt.setiTotalRecords(count);
		pt.setiTotalDisplayRecords(count);
		float sumMoney = 0;
		for (UserOrder userOrder : userOrdersList) {
			userOrder
					.setCommodityPic("<img style='width:50px;height: 50px' src='"
							+ userOrder.getCommodityPic() + "'>");
			sumMoney = sumMoney
					+ Float.parseFloat(userOrder.getMembersCommissionMoney());
			sumMoney = sumMoney
					+ Float.parseFloat(userOrder.getSalesCommissionMoney());
		}
		pt.setAaData(userOrdersList);
		pt.setiDisplayLength(count);
		return pt;
	}

	@RequestMapping(value = "/oauth")
	public String oauth(@RequestParam String code, @RequestParam String state,
			Model model) {
		WeixinOauth weixinOauth = new WeixinOauth();
		WeixinOauth.AccessTokenBean accessTokenBean = weixinOauth
				.getOauthAccessToken(code);
		WeixinUser weixinUser = weixinOauth.getUserInfo(
				accessTokenBean.getAccess_token(), accessTokenBean.getOpenid());
		model.addAttribute("unionId", weixinUser.getUnionid());
		return "/order/orderbind";
	}

	@RequestMapping(value = "/toOrderByPhone")
	public String toOrderByPhone(@RequestParam String dealerId, Model model) {
		model.addAttribute("dealerId", dealerId);
		return "order/phoneOrderList";
	}

	@ResponseBody
	@RequestMapping(value = "/find-Orderlist")
	public List<UserOrder> findList(@RequestParam String dealerId,@RequestParam String year,@RequestParam String month ) {
		List<UserOrder> userOrders = userOrderService
				.findOrderListByDealId(dealerId,year,month);
		if (!CollectionUtils.isEmpty(userOrders)) {
			for (UserOrder userOrder : userOrders) {
				if (!dealerId.equals(userOrder.getBelongsMembersCommission())) {
					userOrder.setMembersCommissionMoney("0.00");
				}
				if (!dealerId.equals(userOrder.getBelongsSalesCommission())) {
					userOrder.setSalesCommissionMoney("0.00");
				}
				
			}
		}
		return userOrders;
	}
}
