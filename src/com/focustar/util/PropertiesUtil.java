package com.focustar.util;

import java.util.Properties;

public class PropertiesUtil {

	/**
	 * 微信配置文件(读取APPID与APPSECRET)
	 */
	public static Properties appKey;
	/**
	 * IMEI接口配置文件(读取访问接口的用户名与密码，接口返回的验证信息）
	 */
	public static Properties imeiConfig;
	/**
	 * 读取微信提示信息文件
	 */
	public static Properties message;

	public static Properties getAppKey() {
		return appKey;
	}

	public static void setAppKey(Properties appKey) {
		PropertiesUtil.appKey = appKey;
	}

	public static Properties getImeiConfig() {
		return imeiConfig;
	}

	public static void setImeiConfig(Properties imeiConfig) {
		PropertiesUtil.imeiConfig = imeiConfig;
	}

	public static Properties getMessage() {
		return message;
	}

	public static void setMessage(Properties message) {
		PropertiesUtil.message = message;
	}

}
