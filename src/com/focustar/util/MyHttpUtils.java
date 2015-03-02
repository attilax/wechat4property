package com.focustar.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

public class MyHttpUtils {
	
	private static Logger logger = Logger.getLogger(MyHttpUtils.class);
	
	
	public static Map<Object,Object> httpStream(String requestUrl,String requestMethod,String outputStr){
		
		HttpURLConnection httpUrlConn = null;
		InputStream inputStream = null;
		
		Map<Object,Object> retMap = null;
		
		try{
			URL url = new URL(requestUrl);
			httpUrlConn = (HttpURLConnection) url.openConnection();
	
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			
			httpUrlConn.setRequestMethod(requestMethod);
			httpUrlConn.setRequestProperty("Cache-Control","no-cache");
			httpUrlConn.setConnectTimeout(30);
			
			httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr && !"".equals(outputStr)) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
			// 将返回的输入流转换成字符串
			inputStream = httpUrlConn.getInputStream();
			
			retMap = new HashMap<Object,Object>();
			
			retMap.put("contentType", httpUrlConn.getContentType());
			retMap.put("inputStream", inputStream);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return retMap;
	}
	
	
	public static String httpRequest(String requestUrl,String requestMethod,String outputStr){
		
		StringBuffer buffer = new StringBuffer();
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		HttpURLConnection httpUrlConn = null;
		BufferedReader bufferedReader = null;

		try {

			URL url = new URL(requestUrl);
			httpUrlConn = (HttpURLConnection) url.openConnection();

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			httpUrlConn.setConnectTimeout(60);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);
			//httpUrlConn.setRequestProperty("Content-Type","multipart/form-data");
			if(requestMethod.equalsIgnoreCase("post")){
				httpUrlConn.setRequestProperty("Content-Type","application/json");
				//application/x-www-form-urlencoded charset=utf-8
				//httpUrlConn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			}
			httpUrlConn.setRequestProperty("Cache-Control","no-cache");
			/*
			 * if ("GET".equalsIgnoreCase(requestMethod)){
			 * httpUrlConn.connect(); }
			 */
			httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			inputStream = httpUrlConn.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			
		} catch (ConnectException ce) {
			logger.error("Weixin server connection timed out.");
		} catch (Exception e) {
			logger.error("https request error:{}", e);
		}finally{
			
			try{
				if(httpUrlConn != null){
					httpUrlConn.disconnect();
				}
				if(inputStreamReader != null){
					inputStreamReader.close();
				}
				if(bufferedReader != null){
					bufferedReader.close();
				}
				if(inputStream != null){
					inputStream.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		return buffer.toString();
	}
	
	public static JSONObject httpsRequest2(String requestUrl, String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		HttpsURLConnection httpUrlConn = null;
		BufferedReader bufferedReader = null;
		System.setProperty ("jsse.enableSNIExtension", "false");

		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			httpUrlConn.setConnectTimeout(30);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			/*
			 * if ("GET".equalsIgnoreCase(requestMethod)){
			 * httpUrlConn.connect(); }
			 */
			httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			inputStream = httpUrlConn.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (ConnectException ce) {
			logger.error("Weixin server connection timed out.");
		} catch (Exception e) {
			logger.error("https request error:{}", e);
		}finally{
			
			try{
				if(httpUrlConn != null){
					httpUrlConn.disconnect();
				}
				if(inputStreamReader != null){
					inputStreamReader.close();
				}
				if(bufferedReader != null){
					bufferedReader.close();
				}
				if(inputStream != null){
					inputStream.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		return jsonObject;
	}
	
	public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		HttpsURLConnection httpUrlConn = null;
		BufferedReader bufferedReader = null;
		
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			httpUrlConn.setConnectTimeout(60);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			/*
			 * if ("GET".equalsIgnoreCase(requestMethod)){
			 * httpUrlConn.connect(); }
			 */
			httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			inputStream = httpUrlConn.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (ConnectException ce) {
			logger.error("Weixin server connection timed out.");
		} catch (Exception e) {
			logger.error("https request error:{}", e);
		}finally{
			
			try{
				if(httpUrlConn != null){
					httpUrlConn.disconnect();
				}
				if(inputStreamReader != null){
					inputStreamReader.close();
				}
				if(bufferedReader != null){
					bufferedReader.close();
				}
				if(inputStream != null){
					inputStream.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		return jsonObject;
	}
	
	
	public static JSONObject httpRequestJSON(String requestUrl, String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		HttpURLConnection httpUrlConn = null;
		BufferedReader bufferedReader = null;
		
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			//TrustManager[] tm = { new MyX509TrustManager() };
			//SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			//sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			//SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			httpUrlConn =  (HttpURLConnection) url.openConnection();
			//httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			httpUrlConn.setConnectTimeout(60);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			/*
			 * if ("GET".equalsIgnoreCase(requestMethod)){
			 * httpUrlConn.connect(); }
			 */
			httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			inputStream = httpUrlConn.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (ConnectException ce) {
			logger.error("Weixin server connection timed out.");
		} catch (Exception e) {
			logger.error("https request error:{}", e);
		}finally{
			
			try{
				if(httpUrlConn != null){
					httpUrlConn.disconnect();
				}
				if(inputStreamReader != null){
					inputStreamReader.close();
				}
				if(bufferedReader != null){
					bufferedReader.close();
				}
				if(inputStream != null){
					inputStream.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		return jsonObject;
	}
	
	
public JSONObject doHttpGet(String requestUrl,Map<Object,Object> paramsMap){
	JSONObject jsonObject = null;
	StringBuffer buffer = new StringBuffer();
	InputStream inputStream = null;
	InputStreamReader inputStreamReader = null;
	HttpURLConnection httpUrlConn = null;
	BufferedReader bufferedReader = null;
	
	try {
		URL url = new URL(requestUrl);
		httpUrlConn =  (HttpURLConnection)url.openConnection();
		httpUrlConn.setDoOutput(true);
		httpUrlConn.setDoInput(true);
		httpUrlConn.setUseCaches(false);
		httpUrlConn.setRequestMethod("GET");
		httpUrlConn.setConnectTimeout(30);
		httpUrlConn.connect();
		//System.out.println(httpUrlConn.getURL().getQuery());
		// 将返回的输入流转换成字符串
		inputStream = httpUrlConn.getInputStream();
		inputStreamReader = new InputStreamReader(inputStream, "utf-8");
		bufferedReader = new BufferedReader(inputStreamReader);

		String str = null;
		while ((str = bufferedReader.readLine()) != null) {
			buffer.append(str);
		}
		bufferedReader.close();
		inputStreamReader.close();
		// 释放资源
		inputStream.close();
		inputStream = null;
		httpUrlConn.disconnect();
		jsonObject = JSONObject.fromObject(buffer.toString());
	} catch (ConnectException ce) {
		logger.error("Weixin server connection timed out.");
	} catch (Exception e) {
		logger.error("https request error:{}", e);
	}finally{
		
		try{
			if(httpUrlConn != null){
				httpUrlConn.disconnect();
			}
			if(inputStreamReader != null){
				inputStreamReader.close();
			}
			if(bufferedReader != null){
				bufferedReader.close();
			}
			if(inputStream != null){
				inputStream.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	return jsonObject;
		
	}

}
