package com.yuanhe.controller;

import com.yuanhe.domain.Customer;
import com.yuanhe.domain.Dealers;
import com.yuanhe.domain.PageModel;
import com.yuanhe.service.CustomerService;
import com.yuanhe.service.DealersService;
import com.yuanhe.weixin.QrcodeService;
import com.yuanhe.weixin.UserService;
import com.yuanhe.weixin.bean.*;
import com.yuanhe.weixin.proxy.WeixinRemoteProxy;
import com.yuanhe.weixin.util.MessageUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
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
    public String findQrcodeTicket(String dealerId){
        QrcodeService qrcodeService = new WeixinRemoteProxy<QrcodeService>(QrcodeService.class).getProxy();
        QrcodeParams qrcodeParams = new QrcodeParams();
        qrcodeParams.setAction_name("QR_LIMIT_SCENE");
        QrcodeParams.ActionInfo actionInfo = new QrcodeParams().new ActionInfo();
        QrcodeParams.ActionInfo.Scene scene = new QrcodeParams().new ActionInfo().new Scene();
        scene.setScene_id(dealerId);
        actionInfo.setScene(scene);
        qrcodeParams.setAction_info(actionInfo);
        QrcodeResponse qrcodeResponse = qrcodeService.create(qrcodeParams);
        System.out.println(qrcodeResponse.getTicket());
        return qrcodeResponse.getUrl();
    }
    @ResponseBody
    @RequestMapping(value="/qrcode-event")
    public String qrcodeEvent(HttpServletRequest request){
        CustomerBean customerBean = processRequest(request);
        if(StringUtils.isNotBlank(customerBean.getDealerId())){
            //获取unionid
            UserService userService = new WeixinRemoteProxy<UserService>(UserService.class).getProxy();
            WeixinUser weixinUser = userService.info(customerBean.getOpenId(),"zh_CN");
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
    }
}
