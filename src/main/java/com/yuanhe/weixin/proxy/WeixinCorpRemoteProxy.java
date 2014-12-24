package com.yuanhe.weixin.proxy;




import com.yuanhe.utils.WeixinUtils;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dam on 2014/6/26.
 */
public class WeixinCorpRemoteProxy<M> extends ProxyFactory<M> {

    private HTTPSClient client = new HTTPSClient();

    private Map<String,Object> map = new HashMap<String, Object>();

    public WeixinCorpRemoteProxy<M> putOtherParameters(String parameterName,Object parameterValue){
        map.put(parameterName,parameterValue);
        return this;
    }

    public WeixinCorpRemoteProxy(Class clazz){
        super(clazz);
    }
    @Override
    public void onException(Exception ex){
        ex.printStackTrace();
    }

    @Override
    public Object after(Type returnType) {
        String serviceUri = Classes.parseClassMethodToUri(super.getTargetClassName(), super.getMethodName());
        client.setSERVER_HOST_URL("https://qyapi.weixin.qq.com/cgi-bin/");
        client.setServiceUri(serviceUri);
        String responseStr = client.request();
        return Classes.stringToObject(responseStr,returnType);
    }

    @Override
    public void before(String[] argNames, Object[] args) {
        map.clear();
        for(int i = 0;i < argNames.length;i ++){
            String argName = argNames[i];
            map.put(argName,args[i]);
        }
        //添加accesstoken
        WeixinUtils weixinUtils = new WeixinUtils();
        map.put("access_token", weixinUtils.getCorpAccessToken());
        client.setParams(map);
    }
}
