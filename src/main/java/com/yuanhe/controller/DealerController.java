package com.yuanhe.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yuanhe.domain.*;
import com.yuanhe.service.CustomerService;
import com.yuanhe.service.DealersService;
import com.yuanhe.service.PromoteLinksService;
import com.yuanhe.utils.Contants;
import com.yuanhe.utils.WeixinUtils;
import com.yuanhe.weixin.QrcodeService;
import com.yuanhe.weixin.UserService;
import com.yuanhe.weixin.bean.*;
import com.yuanhe.weixin.corp.MenuService;
import com.yuanhe.weixin.proxy.HTTPSClient;
import com.yuanhe.weixin.proxy.WeixinRemoteProxy;
import com.yuanhe.weixin.util.MessageUtil;
import com.yuanhe.weixin.util.WeixinOauth;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by dam on 2014/12/16.
 */
@Controller
@RequestMapping("/dealer")
public class DealerController {

    @Autowired
    private DealersService dealersService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private PromoteLinksService promoteLinksService;

    @RequestMapping(value = "/tolist")
    public String toAddGoods() {
        return "/dealer/list";
    }

    @ResponseBody
    @RequestMapping(value = "/sync-from-weixin")
    public String syncFromWeixin() {

        return dealersService.saveDelearBatch()+"";
    }
    @ResponseBody
    @RequestMapping(value = "/get-list")
    public PageModel getGoodsPage(PageModel ptFromPage){

        List<Dealers> dealersList = dealersService.findDealerList(ptFromPage.getiDisplayStart(),ptFromPage.getiDisplayStart()+ptFromPage.getiDisplayLength());
        int count = dealersService.findDealerCount();
        PageModel pt = new PageModel();
        pt.setsEcho(ptFromPage.getsEcho());
        pt.setiTotalRecords(count);
        pt.setiTotalDisplayRecords(count);
        pt.setAaData(dealersList);
        pt.setiDisplayLength(count);
        return pt;
    }
    @ResponseBody
    @RequestMapping(value = "/get-qrcode")
    public String findQrcodeTicket(@RequestParam String dealerId){
        HTTPSClient client = new HTTPSClient();
        WeixinUtils weixinUtils = new WeixinUtils();
        client.setSERVER_HOST_URL("https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+weixinUtils.getWomanAccessToken());
        client.setServiceUri("");
        client.setJsonBodyParams("{\"action_info\":{\"scene\":{\"scene_id\":"
                +Integer.parseInt(dealerId)+"}},\"action_name\":\"QR_LIMIT_SCENE\"}");
        String response = client.request();
        QrcodeResponse qrcodeResponse = JSON.parseObject(response, new TypeReference<QrcodeResponse>(){});
        String ticket = null;
        try {
            ticket = URLEncoder.encode(qrcodeResponse.getTicket(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return ticket;
    }

    @ResponseBody
    @RequestMapping(value = "/create-menu")
    public String createMenu(String menuStrJson){
        return MenuService.create(menuStrJson,1);
    }
    @ResponseBody
    @RequestMapping(value = "/bind-union-id")
    public String bindUnionId(@RequestParam String mobile,
                              @RequestParam String name,
                              @RequestParam String unionId){
        String result = "";
        Dealers dealers = dealersService.findDealerByMobileAndName(mobile,name);
        if(dealers != null && StringUtils.isNotBlank(dealers.getUnionId())){
            result = "already_bind";
        }else if(dealers != null && StringUtils.isBlank(dealers.getUnionId())){
            dealers.setUnionId(unionId);
            dealersService.updateDealerUnionId(dealers);

            result = dealers.getDealersId();
        }else{
            result = "bind_error";
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value="/sanctus",method = RequestMethod.POST)
    public String qrcodeEvent(HttpServletRequest request){
        CustomerBean customerBean = processRequest(request);
        if(StringUtils.isNotBlank(customerBean.getDealerId())){
            //获取unionid
//            UserService userService = new WeixinRemoteProxy<UserService>(UserService.class).getProxy();
//            WeixinUser weixinUser = userService.info(customerBean.getOpenId(),"zh_CN");
            HTTPSClient client = new HTTPSClient();
            WeixinUtils weixinUtils = new WeixinUtils();
            client.setSERVER_HOST_URL("https://api.weixin.qq.com/cgi-bin/user/info?access_token="
                    + weixinUtils.getWomanAccessToken() + "&openid=" + customerBean.getOpenId() );
            client.setServiceUri("");
            String response = client.request();
            System.out.println(response);
            WeixinUser weixinUser = JSON.parseObject(response,new TypeReference<WeixinUser>(){});
            //根据unionId查找
            String unionId = weixinUser.getUnionid();
            Customer customer = customerService.getCustomerById(unionId);
            if(StringUtils.isBlank(customer.getCustomerDealers())){
                customer.setCustomerDealers(customerBean.getDealerId());
                customerService.updateCustomer(customer);
            }
        }
        return "success";
    }
    @ResponseBody
    @RequestMapping(value = "/sanctus",method = RequestMethod.GET)
    public String sanctus(@RequestParam String signature,@RequestParam String timestamp,
                          @RequestParam String nonce,@RequestParam String echostr) {
        String result = "";

        String token = "SanctusII";
        // 1. 将token、timestamp、nonce三个参数进行字典序排序
        String[] strArr = new String[] { token, timestamp, nonce };
        java.util.Arrays.sort(strArr);
        // 2. 将三个参数字符串拼接成一个字符串进行sha1加密
        StringBuffer sb = new StringBuffer();
        for (String str : strArr){
            sb.append(str);
        }
        MessageDigest mdSha1 = null;
        try{
            mdSha1 = MessageDigest.getInstance("SHA-1");
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        mdSha1.update(sb.toString().getBytes());
        byte[] codedBytes = mdSha1.digest();
        String codedString = new BigInteger(1, codedBytes).toString(16);
        System.out.println(codedString);
        System.out.println(signature);
        // 3. 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
        if (codedString.equals(signature)){
            result = echostr;
        }
        return result;
    }
    public  CustomerBean processRequest(HttpServletRequest request) {
        String respMessage = null;

        CustomerBean customerBean = new CustomerBean();

        try {
            // 默认返回的文本消息内容
            String respContent = "请求处理异常，请稍候尝试！";

            // xml请求解析
            Map<String, String> requestMap = MessageUtil.parseXml(request);

            // 发送方帐号（open_id）
            String fromUserName = requestMap.get("FromUserName");
            // 公众帐号
            String toUserName = requestMap.get("ToUserName");
            // 消息类型
            String msgType = requestMap.get("MsgType");

             if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
                // 事件类型
                 String eventType = requestMap.get("Event");
                 String eventKey = requestMap.get("EventKey");
                 customerBean.setDealerId(eventKey);
                 customerBean.setOpenId(fromUserName);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return customerBean;
    }

    /**
     * https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx805e0d1e1ff4c357&redirect_uri=http%3A%2F%2F115.29.47.23%3A8081%2Fyh%2Fdealer%2Foauth&response_type=code&scope=snsapi_userinfo&state=http%3A%2F%2Fwww.baidu.com#wechat_redirect
     * @param code
     * @param state
     * @param model
     * @return
     */
    @RequestMapping(value = "/oauth")
    public String oauth(@RequestParam String code,@RequestParam String state,Model model){

        WeixinOauth weixinOauth = new WeixinOauth();
        WeixinOauth.AccessTokenBean accessTokenBean = weixinOauth.getOauthAccessToken(code);
        WeixinUser weixinUser = weixinOauth.getUserInfo(accessTokenBean.getAccess_token(), accessTokenBean.getOpenid());

        model.addAttribute("unionId",weixinUser.getUnionid());
        System.out.println("unionId is "+weixinUser.getUnionid());
        return "/dealer/bind";
    }
    @RequestMapping(value = "/to-share-link")
    public String toShareLink(@RequestParam String linkId,@RequestParam String dealerId,Model model){

        if(StringUtils.isNotBlank(linkId)){

            PromoteLinks promoteLinks = promoteLinksService.findPromoteLinkById(linkId);

            String oauthUri = null;
            try {
                oauthUri = URLEncoder.encode("http://115.29.47.23:8081/yh/promote-link/oauth?dealerId="+dealerId, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String linkUrl = null;
            try {
                linkUrl = URLEncoder.encode(promoteLinks.getLinkUrl(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String shareLinkStr = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ Contants.APPID
                    +"&redirect_uri=" + oauthUri+
                    "&response_type=code&scope=snsapi_userinfo" +
                    "&state="+linkUrl+"#wechat_redirect";
            model.addAttribute("shareLink",shareLinkStr);
        }

        return "/dealer/toshare";

    }

    public class CustomerBean{

        private String openId;
        private String dealerId;

        public String getOpenId() {
            return openId;
        }

        public void setOpenId(String openId) {
            this.openId = openId;
        }

        public String getDealerId() {
            return dealerId;
        }

        public void setDealerId(String dealerId) {
            this.dealerId = dealerId;
        }

        @Override
        public String toString() {
            return "CustomerBean{" +
                    "openId='" + openId + '\'' +
                    ", dealerId='" + dealerId + '\'' +
                    '}';
        }
    }
}
