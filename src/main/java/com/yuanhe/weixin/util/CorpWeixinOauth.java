package com.yuanhe.weixin.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yuanhe.utils.Contants;
import com.yuanhe.weixin.bean.WeixinUser;
import com.yuanhe.weixin.proxy.HTTPSClient;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dam on 2015/1/4.
 */
public class CorpWeixinOauth {

    public static final String USERINFOURL = "https://api.weixin.qq.com/sns/";



    public WeixinUser getUserInfo(String accessToken,String openId){
        HTTPSClient client = new HTTPSClient();
        client.setSERVER_HOST_URL(USERINFOURL);
        client.setServiceUri("userinfo");
        Map<String,Object> paramsMap = new HashMap<String,Object>();
        paramsMap.put("access_token", accessToken);
        paramsMap.put("openid",openId);
        paramsMap.put("lang","zh_CN");
        client.setParams(paramsMap);
        String response = client.request();
        WeixinUser weixinUser = JSON.parseObject(response,new TypeReference<WeixinUser>(){});
        return weixinUser;
    }
}
