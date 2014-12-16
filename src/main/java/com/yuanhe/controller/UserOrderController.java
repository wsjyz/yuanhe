package com.yuanhe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yuanhe.domain.UserOrder;
import com.yuanhe.service.UserOrderService;

@Controller
@RequestMapping(value = "/userOrder")
public class UserOrderController {

	@Autowired
	UserOrderService userOrderService;
	public String getUserOrderList(){
		return getUserOrderList();
	}
	
	
}
