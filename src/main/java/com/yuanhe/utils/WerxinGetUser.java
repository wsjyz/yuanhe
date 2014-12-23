package com.yuanhe.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

public class WerxinGetUser {
	private static final String store_APPID = "wx51e688b02487dfc4";
	private static final String store_APPSECRET = "073d77dfeee8c1fef0df4ec9a83a4de5";
	private static final String shop_APPID = "wx805e0d1e1ff4c357";
	private static final String shop_APPSECRET = "e4be73256423d9c76b941d815ff735df";
	private static final String token_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=";
	private static final String user_URL = "https://api.weixin.qq.com/cgi-bin/user/info";

	protected static Logger logger = Logger.getLogger(WerxinGetUser.class);

	public String sendGetByUser(String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = user_URL + "?" + param;
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
//			// 获取所有响应头字段
//			Map<String, List<String>> map = connection.getHeaderFields();
//			// 遍历所有的响应头字段
//			for (String key : map.keySet()) {
//				System.out.println(key + "--->" + map.get(key));
//			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
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
		WerxinGetUser werxinGetUser = new WerxinGetUser();
		String token =werxinGetUser.getTokenByStore();
		String params = "{\"touser\": \""+2+"\",\"toparty\": \""+3+"\",\"totag\": \""+3+"\",\"msgtype\": \"text\",\"agentid\": \"1\",\"text\": {\"content\": \"Holiday Request For Pony(http://xxxxx)\"},\"safe\":\"0\"}";
		String sendGet = werxinGetUser.sendPostByEmail(params,token);
		System.out.println(sendGet);
	}
	
	
	public String getTokenByStore() {
		String result = "";
		String url =token_URL+store_APPID+"&secret="+store_APPSECRET;
		BufferedReader in = null;
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 遍历所有的响应头字段
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
			JSONObject jsonObj=JSONObject.parseObject(result);
			result=jsonObj.getString("access_token");
		} catch (Exception e) {
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
	public String getTokenByShop() {
		String result = "";
		String url ="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+shop_APPID+"&secret="+shop_APPSECRET;
		BufferedReader in = null;
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 遍历所有的响应头字段
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
			JSONObject jsonObj=JSONObject.parseObject(result);
			result=jsonObj.getString("access_token");
		} catch (Exception e) {
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
	
	/**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public  String sendPostByEmail(String param,String token) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL("https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token="+token);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
			logger.info(e);
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
    			logger.info(ex);
            }
        }
        return result;
    }    

}
