package com.focustar.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author 张春雨
 * @模块 微博、微信配置信息
 * @日期 2013-11-12 时间：下午05:58:54
 */
public class ConfigService {

	/**
	 * 获取微信的APPID
	 * 
	 * @return
	 */
	/*public String getWeiXinAppID() {
		String appid = "";
		Properties propert = new Properties();
		try {
			FileInputStream fis = new FileInputStream(Constant.path + "WeiXinConfig.properties");
			propert.load(fis);
			// 取得文件中属性值
			appid = propert.getProperty("APPID").trim();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return appid;
	}

	*//**
	 * 获取微信的APPSECRET
	 * 
	 * @return
	 *//*
	public String getWeiXinAppSecret() {
		String appsecret = "";
		Properties propert = new Properties();
		try {
			FileInputStream fis = new FileInputStream(Constant.path + "WeiXinConfig.properties");
			propert.load(fis);
			// 取得文件中属性值
			appsecret = propert.getProperty("APPSECRET").trim();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return appsecret;
	}
		*/
	/**
	 * 获取微信公众号的token
	 */
	public static String getWeiXinToken() {
		String token = "";
		Properties propert;
		String filePath = Constant.path + "WeiXinConfig.properties"; // 配置文件
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(filePath);
			propert = new Properties();
			propert.load(fis);
			// 取得文件中属性值
			token = propert.getProperty("TOKEN").trim();
		} catch (Exception e1) {
			e1.printStackTrace();
		}finally{
			try{
			if(fis != null){
				fis.close();
			}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return token;
	}
	
	public String getWxProperty(String key){
		String value = "";
		Properties propert = new Properties();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(Constant.path + "WeiXinConfig.properties");
			propert.load(fis);
			// 取得文件中属性值
			value = propert.getProperty(key);
			if(value != null){
				value = value.trim();
				value = new String(value.getBytes("ISO-8859-1"), "UTF-8");
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}finally{
			try{
			if(fis != null){
				fis.close();
			}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return value;
	}
	
	/**
	 * 	public static String path = "d:\\config\\";
	@author attilax 老哇的爪子
		@since  2014-4-28 下午05:53:04$
	
	 * @param key
	 * @param file
	 * @return
	 */
	public static  String getWxProperty(String key,String file){
		String value = "";
		Properties propert = new Properties();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file );
			propert.load(fis);
			// 取得文件中属性值
			value = propert.getProperty(key);
			if(value != null){
				value = value.trim();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}finally{
			try{
			if(fis != null){
				fis.close();
			}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return value;
	}
	

	/**
	 * 读取微信提示信息文件
	 */
	public String getMsgProperty(String key) {
		String info = "";
		Properties message = new Properties();
		String filename = Constant.path + "message.properties"; // 配置文件
		InputStream inputStream = null;
		try {
			// 取得资源文件输入流
			inputStream = new FileInputStream(filename);
			message.load(inputStream);

			info = message.getProperty(key);
			if (info != null && !"".equals(info))
				info = new String(info.getBytes("ISO-8859-1"), "UTF-8");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		finally{
			try{
			if(inputStream != null){
				inputStream.close();
			}
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		return info;
	}


	/**
	 * 获取微信接口返回的错误码
	 */
	public static String getReturnCode(String errcode) {
		String info = "";
		Properties message = new Properties();
		String filename = Constant.path + "returnCode.properties";
		InputStream inputStream = null;
		try {
			// 取得资源文件输入流
			inputStream = new FileInputStream(filename);
			message.load(inputStream);

			info = message.getProperty(errcode);
			if (info != null && !"".equals(info))
				info = new String(info.getBytes("ISO-8859-1"), "UTF-8");
		} catch (Exception e1) {
			e1.printStackTrace();
		}finally{
			try{
			if(inputStream != null){
				inputStream.close();
			}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return info;
	}
	
	

}
