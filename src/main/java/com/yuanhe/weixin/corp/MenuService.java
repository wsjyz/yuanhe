package com.yuanhe.weixin.corp;

import com.yuanhe.utils.WeixinUtils;
import com.yuanhe.weixin.bean.ResponseBean;
import com.yuanhe.weixin.proxy.HTTPSClient;
import com.yuanhe.weixin.proxy.RemoteMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dam on 2014/12/30.
 */
public class MenuService {


    public static String create(String menuStr,int agentId){
        HTTPSClient client = new HTTPSClient();
        WeixinUtils weixinUtils = new WeixinUtils();
        client.setSERVER_HOST_URL("https://qyapi.weixin.qq.com/cgi-bin/menu/create?access_token="
                +weixinUtils.getCorpAccessToken()+"&agentid="+agentId);
        client.setServiceUri("");
        client.setJsonBodyParams(menuStr);
        String response = client.request();
        return response;
    }

    public static void main(String[] args) {
//        String menuStr = "{\"button\":[ {\"type\":\"click\", \"name\":\"今日歌曲\", \"key\":\"V1001_TODAY_MUSIC\" }, { \"name\":\"菜单\", \"sub_button\":[ { \"type\":\"view\", \"name\":\"搜索\", \"url\":\"http://www.soso.com/\" }, { \"type\":\"click\", \"name\":\"赞一下我们\", \"key\":\"V1001_GOOD\" } ] } ] }";
//
//        MenuService.create(menuStr,1);
        HTTPSClient client = new HTTPSClient();
        WeixinUtils weixinUtils = new WeixinUtils();
        client.setSERVER_HOST_URL("https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=j5sIqbfmqUfb6c9Zknyj9tXikAc991e1gi6tEhw_xP3PgJg3KOWdu1VTV3EYOZXDbmlmRf0AvqSL_CQM7-xZGZyNX21cNAwifyIGnPJlSZM");
        client.setServiceUri("");
        client.setJsonBodyParams("{\"action_info\":{\"scene\":{\"scene_id\":1}},\"action_name\":\"QR_LIMIT_SCENE\"}");
        String response = client.request();
        System.out.println(response);
    }

}
