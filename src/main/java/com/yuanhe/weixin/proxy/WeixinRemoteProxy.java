package com.yuanhe.weixin.proxy;

import com.yuanhe.utils.WeixinUtils;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dam on 2014/12/23.
 */
public class WeixinRemoteProxy<M> extends ProxyFactory<M> {

    private HTTPSClient client = new HTTPSClient();

    private Map<String,Object> map = new HashMap<String, Object>();

    public WeixinRemoteProxy<M> putOtherParameters(String parameterName,Object parameterValue){
        map.put(parameterName,parameterValue);
        return this;
    }

    public WeixinRemoteProxy(Class clazz){
        super(clazz);
    }
    @Override
    public void onException(Exception ex){
        ex.printStackTrace();
    }

    @Override
    public Object after(Type returnType) {
        String serviceUri = Classes.parseClassMethodToUri(super.getTargetClassName(), super.getMethodName());
        client.setSERVER_HOST_URL("https://api.weixin.qq.com/cgi-bin/");
        client.setServiceUri(serviceUri);
        System.out.println(client.getContentType());
        String responseStr = client.request();
        return Classes.stringToObject(responseStr,returnType);
    }

    @Override
    public void before(String[] argNames, Object[] args) {
        map.clear();
        if(argNames.length == 0 || argNames == null){//这种情况不是form形式的请求
            client.setJsonBodyParams(args[0]);
            client.setContentType("application/json");
        }
        for(int i = 0;i < argNames.length;i ++){
            String argName = argNames[i];
            map.put(argName,args[i]);
        }
        //添加accesstoken
        WeixinUtils weixinUtils = new WeixinUtils();
        map.put("access_token", weixinUtils.getAccessToken());
        client.setParams(map);
    }
}