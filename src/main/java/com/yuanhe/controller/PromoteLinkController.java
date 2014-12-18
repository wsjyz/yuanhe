package com.yuanhe.controller;

import com.yuanhe.domain.Dealers;
import com.yuanhe.domain.PageModel;
import com.yuanhe.domain.PromoteLinks;
import com.yuanhe.service.PromoteLinksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by dam on 2014/12/17.
 */
@Controller
@RequestMapping("/promote-link")
public class PromoteLinkController {

    @Autowired
    PromoteLinksService promoteLinksService;

    @RequestMapping(value = "/to-add")
    public String toAddLink() {
        return "/link/add";
    }
    @ResponseBody
    @RequestMapping(value = "/get-list")
    public PageModel getList(PageModel ptFromPage){

        List<PromoteLinks> promoteLinkses = promoteLinksService.findPromoteLinkList(ptFromPage.getiDisplayStart(),
                ptFromPage.getiDisplayStart()+ptFromPage.getiDisplayLength());
        int count = promoteLinksService.findPromoteLinkCount();
        PageModel pt = new PageModel();
        pt.setsEcho(ptFromPage.getsEcho());
        pt.setiTotalRecords(count);
        pt.setiTotalDisplayRecords(count);
        pt.setAaData(promoteLinkses);
        pt.setiDisplayLength(count);
        return pt;
    }
    @ResponseBody
    @RequestMapping(value = "/add")
    public String addLink(PromoteLinks promoteLinks,Model model) {

        promoteLinksService.addPromoteLink(promoteLinks);

        return "success";
    }
    @ResponseBody
    @RequestMapping(value = "/delete")
    public String deleteLink(@RequestParam String linkId) {
        promoteLinksService.deletePromoteLink(linkId);
        return "success";
    }
}
