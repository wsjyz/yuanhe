package com.yuanhe.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by dam on 2014/12/9.
 */
@Controller
@RequestMapping(value = "/")
public class HelloController {


    @RequestMapping(value = "/to-login")
    public String toLogin() {
        String string = "login";

        return string;
    }
    @ResponseBody
    @RequestMapping(value = "/login")
    public String login(@RequestParam String userName,@RequestParam String password,HttpServletRequest request) {
        String result = "error";
        if(StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(password)){
            if(userName.equals("admin") && password.equals("yh_Admin0")){
                result = "success";
                request.getSession().setAttribute("loginName",userName);
            }
        }
        return result;
    }

}
