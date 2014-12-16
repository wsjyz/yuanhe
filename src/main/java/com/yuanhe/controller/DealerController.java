package com.yuanhe.controller;

import com.yuanhe.domain.Dealers;
import com.yuanhe.domain.PageModel;
import com.yuanhe.service.DealersService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by dam on 2014/12/16.
 */
@Controller
@RequestMapping("/dealer")
public class DealerController {

    @Autowired
    private DealersService dealersService;

    @RequestMapping(value = "/tolist")
    public String toAddGoods() {
        return "/dealer/list";
    }

    @ResponseBody
    @RequestMapping(value = "/get-list")
    public PageModel getGoodsPage(PageModel ptFromPage,@RequestParam long start,@RequestParam long end){

        if(end == -1){
            end = 0;
        }
        List<Dealers> dealersList = dealersService.findDealerList(start,end);
        int count = dealersService.findDealerCount();
        PageModel pt = new PageModel();
        pt.setsEcho(ptFromPage.getsEcho());
        pt.setiTotalRecords(count);
        pt.setiTotalDisplayRecords(count);
        pt.setAaData(dealersList);
        pt.setiDisplayLength(count);
        return pt;
    }
}
