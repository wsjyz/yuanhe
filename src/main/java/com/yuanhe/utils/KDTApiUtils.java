package com.yuanhe.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yuanhe.domain.UserOrder;
import com.yuanhe.domain.annotation.Column;


/*
 * 这是个例子
 */
public class KDTApiUtils {
	private static final  String APP_ID = "50f9027f6583b5f314"; //这里换成你的app_id
	private static final String APP_SECRET = "43b9e64a063e9978bcc89e77c01cc969"; //这里换成你的app_secret
	

	
	public List<UserOrder> sendOrderList(){
		String method = "kdt.trades.sold.get";
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("end_update", new Date().toString());
		KdtApiClient kdtApiClient;
		HttpResponse response;
		List<UserOrder> userOrderList=new ArrayList<UserOrder>();
		try {
			kdtApiClient = new KdtApiClient(APP_ID, APP_SECRET);
			response = kdtApiClient.get(method, params);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = bufferedReader.readLine()) != null) {
				result.append(line);
			}
			JSONObject  dataJson=JSONObject.parseObject(result.toString());
			JSONObject  responseObject=dataJson.getJSONObject("response");
			Integer total_results = responseObject.getInteger("total_results");
			if (total_results!=null && total_results>0) {
				JSONArray data=responseObject.getJSONArray("trades");
				for (int i =0; i < data.size(); i++) {
					JSONObject info=data.getJSONObject(i);
					String status=info.getString("status");
					//只有买家已付款 或者卖家已
					if ("WAIT_SELLER_SEND_GOODS".equals(status) || "TRADE_CLOSED".equals(status)) {
						UserOrder userOrder=new UserOrder();
						userOrder.setOrderStatus(info.getString("status"));
						userOrder.setOrderId(info.getString("num_iid"));
						userOrder.setRealPay(info.getString("payment"));
						userOrder.setPostageMoney(info.getString("post_fee"));
						userOrder.setWeixin_user_id(info.getString("weixin_user_id"));
						sendUserByWeixinUserId(userOrder.getWeixin_user_id(), userOrder);
					}
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userOrderList;
	}
	
	private  void  sendUserByWeixinUserId(String weiXinUserId,UserOrder userOrder){
		String method = "kdt.users.weixin.follower.get";
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("user_id", weiXinUserId);
		KdtApiClient kdtApiClient;
		HttpResponse response;
		
		try {
			kdtApiClient = new KdtApiClient(APP_ID, APP_SECRET);
			response = kdtApiClient.get(method, params);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = bufferedReader.readLine()) != null) {
				result.append(line);
			}
			JSONObject  dataJson=JSONObject.parseObject(result.toString());
			JSONObject  responseObject=dataJson.getJSONObject("response");
			JSONObject data=responseObject.getJSONObject("user");
			userOrder.setWeixin_openid((String) data.get("weixin_openid")); 
			userOrder.setPaymentWeixinNick((String) data.get("nick"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
