package com.yuanhe.controller;

import java.util.List;

import com.yuanhe.weixin.bean.WeixinUser;
import com.yuanhe.weixin.util.WeixinOauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuanhe.domain.Customer;
import com.yuanhe.domain.PageModel;
import com.yuanhe.domain.PromoteLinks;
import com.yuanhe.domain.UserOrder;
import com.yuanhe.service.CustomerService;
import com.yuanhe.service.UserOrderService;

@Controller
@RequestMapping(value = "/order")
public class UserOrderController {

	@Autowired
	UserOrderService userOrderService;


	   @RequestMapping(value = "/toOrder")
	    public String toAddGoods() {
	        return "/order/orderlist";
	    }
		@ResponseBody
	    @RequestMapping(value = "/get-orderlist")
	    public PageModel getGoodsPage(PageModel ptFromPage){
	        List<UserOrder> userOrdersList = userOrderService.findOrderList(ptFromPage.getiDisplayStart(),ptFromPage.getiDisplayLength(),ptFromPage.getsSearch());
	        int count = userOrderService.findOrderCount(ptFromPage.getsSearch());
	        PageModel pt = new PageModel();
	        pt.setsEcho(ptFromPage.getsEcho());
	        pt.setiTotalRecords(count);
	        pt.setiTotalDisplayRecords(count);
	        for (UserOrder userOrder : userOrdersList) {
	        	userOrder.setCommodityPic("<img style='width:50px;height: 50px' src='"+userOrder.getCommodityPic()+"'>");
			}
	        pt.setAaData(userOrdersList);
	        pt.setiDisplayLength(count);
	        return pt;
	    }

    @RequestMapping(value = "/oauth")
    public String oauth(@RequestParam String code,@RequestParam String state,Model model){
        WeixinOauth weixinOauth = new WeixinOauth();
        WeixinOauth.AccessTokenBean accessTokenBean = weixinOauth.getOauthAccessToken(code);
        WeixinUser weixinUser = weixinOauth.getUserInfo(accessTokenBean.getAccess_token(), accessTokenBean.getOpenid());
        model.addAttribute("unionId",weixinUser.getUnionid());
        return "/order/orderbind";
    }
    
   @RequestMapping(value = "/toOrderByPhone")
    public String toOrderByPhone(@RequestParam String dealerId,Model model){
        model.addAttribute("dealerId",dealerId);
        return "order/phoneOrderList";
    }
   @ResponseBody
   @RequestMapping(value = "/find-Orderlist")
   public List<UserOrder> findList(@RequestParam String dealerId){
       List<UserOrder> userOrders = userOrderService.findOrderListByDealId(dealerId);
       return userOrders;
   }
}
