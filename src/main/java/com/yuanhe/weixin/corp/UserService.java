package com.yuanhe.weixin.corp;

import com.yuanhe.weixin.bean.CorpUserResponse;
import com.yuanhe.weixin.proxy.RemoteMethod;

/**
 * Created by dam on 2014/12/18.
 */
public interface UserService {

    @RemoteMethod(methodVarNames={ "department_id","fetch_child","status" })
    CorpUserResponse simplelist(int department_id,int fetch_child,int status);

    @RemoteMethod(methodVarNames={ "code","agentid" })
    UserInfoResponse getuserinfo(String code,String agentId);
}
