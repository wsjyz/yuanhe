package com.yuanhe.controller;

import java.util.Date;
import java.util.List;

import com.yuanhe.weixin.util.AccessTokenBean;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yuanhe.domain.Customer;
import com.yuanhe.domain.Dealers;
import com.yuanhe.domain.PageModel;
import com.yuanhe.domain.UserOrder;
import com.yuanhe.service.CustomerService;
import com.yuanhe.service.DealersService;
import com.yuanhe.weixin.bean.WeixinUser;
import com.yuanhe.weixin.util.WeixinOauth;

@Controller
@RequestMapping(value = "/Customer")
public class CustomerController {


   @RequestMapping(value = "/toCustomer")
    public String toAddGoods() {
        return "/customer/customerlist";
    }
	@Autowired
	CustomerService customerService;
	@Autowired
	DealersService dealersService;

	@ResponseBody
    @RequestMapping(value = "/get-customerlist")
    public PageModel getGoodsPage(PageModel ptFromPage){
        List<Customer> customersList = customerService.findCustomerList(ptFromPage.getiDisplayStart(),ptFromPage.getiDisplayLength(),ptFromPage.getsSearch());
        int count = customerService.findCustomerCount(ptFromPage.getsSearch());
        PageModel pt = new PageModel();
        pt.setsEcho(ptFromPage.getsEcho());
        pt.setiTotalRecords(count);
        pt.setiTotalDisplayRecords(count);
        for (Customer customer : customersList) {
			customer.setCustomerPic("<img style='width:50px;height: 50px' src='"+customer.getCustomerPic()+"'>");
		}
        pt.setAaData(customersList);
        pt.setiDisplayLength(count);
        return pt;
    }
	  @RequestMapping(value = "/find-union-id")
	    public String oauth(@RequestParam String code,@RequestParam String state,Model model){
	        WeixinOauth weixinOauth = new WeixinOauth();
	        AccessTokenBean accessTokenBean = weixinOauth.obtainOauthAccessToken(code);
	        WeixinUser weixinUser = weixinOauth.getUserInfo(accessTokenBean.getAccess_token(), accessTokenBean.getOpenid());
	        model.addAttribute("unionId",weixinUser.getUnionid());
	        return "/customer/orderCustomer";
	    }
	  @RequestMapping(value = "/oauth")
	    public String oauth(Model model,RedirectAttributes attr){
	        AccessTokenBean accessTokenBean = WeixinOauth.weixinOauthAccessTokenBean;
	        long currentTime = new Date().getTime();

	        if(accessTokenBean == null  ){//这时accesstoken已经失效了，或者是第一次
	            return "/customer/snsapi_userinfo_oauth";
	        }else{
	        //这时accesstoken并未失效，但是要获取openid，获取openid的时候，
	        // 要用刷新accesstoken的方法来获取，而不能用snsapi_base，否则acesstoken就变了，垃圾微信api
	            WeixinOauth weixinOauth = new WeixinOauth();
	            String openId = weixinOauth.refreshAccessToken(accessTokenBean.getRefresh_token());
	            WeixinUser weixinUser = weixinOauth.getUserInfo(accessTokenBean.getAccess_token(), openId);
	            if(weixinUser == null || StringUtils.isBlank(weixinUser.getUnionid())){
	            //每次刷新token的时候过期时间都是7200，并不像他说的那样有7天什么的，所以你根本没法知道什么时候到期，垃圾微信api
	                return "/customer/snsapi_userinfo_oauth";
	            }
	            Dealers dealers = dealersService.findDealerByUnionId(weixinUser.getUnionid());
	            if(dealers != null){
	                attr.addAttribute("dealerId", dealers.getDealersId());
	                return "redirect:/customer/tocustomerByPhone";
	            }
	            model.addAttribute("unionId",weixinUser.getUnionid());
	            System.out.println("unionId is "+weixinUser.getUnionid());
	            return "/customer/orderCustomer";
	        }
	    }
	    
	   @RequestMapping(value = "/tocustomerByPhone")
	    public String tocustomerByPhone(@RequestParam String dealerId,Model model){
	        model.addAttribute("dealerId",dealerId);
	        return "/customer/phonecustomerList";
	}

	@ResponseBody
	@RequestMapping(value = "/find-customerlist")
	public List<Customer> findList(@RequestParam String dealerId) {
		List<Customer> customers = customerService.findCustomerListByDealerId(dealerId);
		return customers;
	}
}
