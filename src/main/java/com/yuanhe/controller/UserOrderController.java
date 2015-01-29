package com.yuanhe.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yuanhe.weixin.util.AccessTokenBean;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yuanhe.domain.Dealers;
import com.yuanhe.domain.PageModel;
import com.yuanhe.domain.UserOrder;
import com.yuanhe.service.DealersService;
import com.yuanhe.service.UserOrderService;
import com.yuanhe.weixin.bean.WeixinUser;
import com.yuanhe.weixin.util.WeixinOauth;

@Controller
@RequestMapping(value = "/order")
public class UserOrderController {

	@Autowired
	UserOrderService userOrderService;
	 @Autowired
	    private DealersService dealersService;

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
	    public String oauth(Model model,RedirectAttributes attr){
	        AccessTokenBean accessTokenBean = WeixinOauth.weixinOauthAccessTokenBean;
	        long currentTime = new Date().getTime();

	        if(accessTokenBean == null  ){//这时accesstoken已经失效了，或者是第一次
	            return "/order/snsapi_userinfo_oauth";
	        }else{
	        //这时accesstoken并未失效，但是要获取openid，获取openid的时候，
	        // 要用刷新accesstoken的方法来获取，而不能用snsapi_base，否则acesstoken就变了，垃圾微信api
	            WeixinOauth weixinOauth = new WeixinOauth();
	            String openId = weixinOauth.refreshAccessToken(accessTokenBean.getRefresh_token());
	            WeixinUser weixinUser = weixinOauth.getUserInfo(accessTokenBean.getAccess_token(), openId);
	            if(weixinUser == null || StringUtils.isBlank(weixinUser.getUnionid())){
	            //每次刷新token的时候过期时间都是7200，并不像他说的那样有7天什么的，所以你根本没法知道什么时候到期，垃圾微信api
	                return "/order/snsapi_userinfo_oauth";
	            }
	            Dealers dealers = dealersService.findDealerByUnionId(weixinUser.getUnionid());
	            if(dealers != null){
	                attr.addAttribute("dealerId", dealers.getDealersId());
	                return "redirect:/order/toOrderByPhone";
	            }
	            model.addAttribute("unionId",weixinUser.getUnionid());
	            System.out.println("unionId is "+weixinUser.getUnionid());
	            return "/order/orderbind";
	        }
	    }

	@RequestMapping(value = "/find-union-id")
	public String oauth(@RequestParam String code, @RequestParam String state,
			Model model) {
		WeixinOauth weixinOauth = new WeixinOauth();
		AccessTokenBean accessTokenBean = weixinOauth
				.obtainOauthAccessToken(code);
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
	@ResponseBody
	@RequestMapping(value = "/findOrderList")
	public List<UserOrder> findOrderListByDealIdAndStartDay(@RequestParam String dealerId,@RequestParam String startTime,@RequestParam String endTime){
		return userOrderService.findOrderListByDealIdAndStartDay(dealerId, startTime, endTime);
	}
	
}
