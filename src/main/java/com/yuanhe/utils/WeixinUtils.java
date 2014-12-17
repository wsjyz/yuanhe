package com.yuanhe.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yuanhe.weixin.proxy.HTTPSClient;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dam on 2014/12/16.
 */
public class WeixinUtils {

    protected static Logger logger = Logger.getLogger(WeixinUtils.class);

    public static WeixinCorpAccessTokenBean weixinCorpAccessTokenBean;

    private static final String CORPID = "wxfb5ee1afc560a3be";

    private static final String CORPSECRET = "y29rsSXWXEShXkmyBx8mskVSNonvSqvqzQWqMWGbRIXtMkiWxbKRVLRDYqykB2tI";

    public String obtainAccessToken(){

        weixinCorpAccessTokenBean = new WeixinCorpAccessTokenBean();
        //获取accesstoken
        HTTPSClient client = new HTTPSClient();
        client.setServiceUri("gettoken");
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("corpid",CORPID);
        params.put("corpsecret",CORPSECRET);
        client.setParams(params);
        String responseStr = client.request();
        Map<String,String> responseMap = JSON.parseObject(responseStr,new TypeReference<Map<String, String>>(){});

        if(responseMap.get("errcode") == null){
            String accessToken = responseMap.get("access_token");
            weixinCorpAccessTokenBean.setAccessToken(accessToken);
            weixinCorpAccessTokenBean.setExpireTime(new Date().getTime() + 1000 * 7200);
            logger.info("获取企业号AccessToken成功");
        }else{
            logger.info("获取企业号AccessToken错误:"+responseStr);
        }
        return weixinCorpAccessTokenBean.getAccessToken();
    }

    public String getAccessToken(){
        WeixinCorpAccessTokenBean tokenBean = WeixinUtils.weixinCorpAccessTokenBean;
        long currentTime = new Date().getTime();

        if(tokenBean != null && tokenBean.getExpireTime() > currentTime){
            return tokenBean.getAccessToken();
        }else{
            return obtainAccessToken();
        }
    }

    private class WeixinCorpAccessTokenBean {

        private String accessToken;
        private long expireTime;

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public long getExpireTime() {
            return expireTime;
        }

        public void setExpireTime(long expireTime) {
            this.expireTime = expireTime;
        }
    }
    public static void main(String[] args) {
        WeixinUtils v = new WeixinUtils();
        v.obtainAccessToken();
        System.out.println(v.getAccessToken());
    }
}
