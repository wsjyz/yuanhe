package com.yuanhe.weixin.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yuanhe.utils.Contants;
import com.yuanhe.weixin.bean.ResponseBean;
import com.yuanhe.weixin.bean.WeixinUser;
import com.yuanhe.weixin.proxy.HTTPSClient;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dam on 2014/12/27.
 */
public class WeixinOauth {

    public static final String ACCESSTOKENURL = "https://api.weixin.qq.com/sns/oauth2/";
    public static final String USERINFOURL = "https://api.weixin.qq.com/sns/";

    /**
     * 这一步获取access_token和openid
     * @param code
     */
    public AccessTokenBean getOauthAccessToken(String code){
        HTTPSClient client = new HTTPSClient();
        client.setSERVER_HOST_URL(ACCESSTOKENURL);
        client.setServiceUri("access_token");
        Map<String,Object> paramsMap = new HashMap<String,Object>();
        paramsMap.put("appid", Contants.APPID);
        paramsMap.put("secret",Contants.SECRET);
        paramsMap.put("code",code);
        paramsMap.put("grant_type","authorization_code");
        client.setParams(paramsMap);
        String response = client.request();
        AccessTokenBean accessTokenBean = JSON.parseObject(response,new TypeReference<WeixinOauth.AccessTokenBean>(){});
        return accessTokenBean;
    }

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

    public static void main(String[] args) {
        WeixinOauth oauth = new WeixinOauth();
        AccessTokenBean accessTokenBean = oauth.getOauthAccessToken("021e55a213feae41a75eb312e8e8a58R");
        System.out.println(accessTokenBean.getErrcode());
//        WeixinUser weixinUser = oauth.getUserInfo(accessTokenBean.getAccess_token(), accessTokenBean.getOpenid());
//        System.out.println(weixinUser.getOpenId() + "--"+weixinUser.getUnionid());


//        try {
//            String s = "http://115.29.47.23:8081/yh/promote-link/oauth";//http://115.29.47.23:8081/yh/promote-link/oauth
//            String url = URLEncoder.encode(s, "UTF-8");
//            String url2 = URLEncoder.encode("http://www.baidu.com", "UTF-8");
//            System.out.println("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx805e0d1e1ff4c357&redirect_uri="+url+"&response_type=code&scope=snsapi_userinfo&state="+url2+"#wechat_redirect");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }


    }



    public static class AccessTokenBean {

        public String access_token;
        public int expires_in;
        public String refresh_token;
        public String openid;
        public String scope;
        private int errcode;
        private String errmsg;

        public int getErrcode() {
            return errcode;
        }

        public void setErrcode(int errcode) {
            this.errcode = errcode;
        }

        public String getErrmsg() {
            return errmsg;
        }

        public void setErrmsg(String errmsg) {
            this.errmsg = errmsg;
        }

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }

        public int getExpires_in() {
            return expires_in;
        }

        public void setExpires_in(int expires_in) {
            this.expires_in = expires_in;
        }

        public String getRefresh_token() {
            return refresh_token;
        }

        public void setRefresh_token(String refresh_token) {
            this.refresh_token = refresh_token;
        }

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public String getScope() {
            return scope;
        }

        public void setScope(String scope) {
            this.scope = scope;
        }
    }

}
