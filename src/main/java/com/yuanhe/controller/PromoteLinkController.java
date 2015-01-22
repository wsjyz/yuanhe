package com.yuanhe.controller;

import com.yuanhe.domain.PageModel;
import com.yuanhe.domain.PromoteLinks;
import com.yuanhe.domain.UserAccessRecord;
import com.yuanhe.service.PromoteLinksService;
import com.yuanhe.service.UserAccessRecordService;
import com.yuanhe.weixin.bean.WeixinUser;
import com.yuanhe.weixin.util.WeixinOauth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by dam on 2014/12/17.
 */
@Controller
@RequestMapping("/promote-link")
public class PromoteLinkController {

    @Autowired
    PromoteLinksService promoteLinksService;
    @Autowired
    UserAccessRecordService userAccessRecordService;

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

    /**
     * https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx805e0d1e1ff4c357&redirect_uri=http%3A%2F%2F115.29.47.23%3A8081%2Fyh%2Fpromote-link%2Foauth&response_type=code&scope=snsapi_userinfo&state=http%3A%2F%2Fwww.baidu.com#wechat_redirect
     * @param request
     * @return
     */
    @RequestMapping(value = "/oauth")
    public String oauth(HttpServletRequest request){
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        String dealerId = request.getParameter("dealerId");
        WeixinOauth weixinOauth = new WeixinOauth();
        WeixinOauth.AccessTokenBean accessTokenBean = weixinOauth.obtainOauthAccessToken(code);
        WeixinUser weixinUser = weixinOauth.getUserInfo(accessTokenBean.getAccess_token(), accessTokenBean.getOpenid());
        UserAccessRecord userAccessRecord = new UserAccessRecord();
        userAccessRecord.setAccessUrl(state);

        userAccessRecord.setVisiterDealersId(dealerId);
        userAccessRecord.setVisiterUnionId(weixinUser.getUnionid());
        userAccessRecordService.saveAccessRecord(userAccessRecord);
        return "redirect:"+state;
    }
    @RequestMapping(value = "/to-list")
    public String toList(@RequestParam String dealerId,Model model){
        model.addAttribute("dealerId",dealerId);
        return "link/list";
    }
    @ResponseBody
    @RequestMapping(value = "/find-list")
    public List<PromoteLinks> findList(){
        List<PromoteLinks> promoteLinkses = promoteLinksService.findPromoteLinkList();
        return promoteLinkses;
    }
}
