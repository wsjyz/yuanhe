package com.yuanhe.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class WerxinGetUser {
	protected static Logger logger = Logger.getLogger(WerxinGetUser.class);

	public String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
			System.out.println(result);
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			logger.info(e);
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				logger.info(e2);
			}
		}
		return result;
	}

	public static void main(String[] args) {
		String token = "JWZ7ykXgS3fVqW6lc7xnT8E9Aei2hCRnoo9-hDFcy_M";
		String openId = "oJMS3t--di-opPKtkZYY8iNj5Drg";
		String params = "access_token=" + token + "&openid=" + openId
				+ "&lang=zh_CN";
		WerxinGetUser werxinGetUser = new WerxinGetUser();
		werxinGetUser.sendGet("https://api.weixin.qq.com/cgi-bin/user/info", params);
	}
}
