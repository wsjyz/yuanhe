package com.yuanhe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuanhe.domain.Customer;
import com.yuanhe.domain.PageModel;
import com.yuanhe.service.CustomerService;
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
	  @RequestMapping(value = "/oauth")
	    public String oauth(@RequestParam String code,@RequestParam String state,Model model){
	        WeixinOauth weixinOauth = new WeixinOauth();
	        WeixinOauth.AccessTokenBean accessTokenBean = weixinOauth.obtainOauthAccessToken(code);
	        WeixinUser weixinUser = weixinOauth.getUserInfo(accessTokenBean.getAccess_token(), accessTokenBean.getOpenid());
	        model.addAttribute("unionId",weixinUser.getUnionid());
	        return "/customer/orderCustomer";
	    }
	    
	   @RequestMapping(value = "/tocustomerByPhone")
	    public String tocustomerByPhone(@RequestParam String dealerId,Model model){
	        model.addAttribute("dealerId",dealerId);
	        return "/customer/phonecustomerList";
	    }
	   @ResponseBody
	   @RequestMapping(value = "/find-customerlist")
	   public List<Customer> findList(@RequestParam String dealerId){
	       List<Customer> customers = customerService.findCustomerListByDealerId(dealerId);
	       return customers;
	   }
}
