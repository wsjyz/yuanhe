package com.yuanhe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by dam on 2014/12/9.
 */
@Controller
@RequestMapping(value = "/hello")
public class HelloController {

    @ResponseBody
    @RequestMapping(value = "/a")
    public String resetPassword() {
        String string = "hello";

        return string;
    }

}