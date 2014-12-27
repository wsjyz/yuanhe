package com.yuanhe.weixin;

import com.yuanhe.weixin.bean.WeixinUser;
import com.yuanhe.weixin.proxy.RemoteMethod;

/**
 * Created by dam on 2014/12/24.
 */
public interface UserService {

    @RemoteMethod(methodVarNames={ "openid","lang" })
    WeixinUser info(String openId,String lang);
}
