package com.focustar.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class FileUtil {

	private static String filename = "C:\\config\\WeiXin.properties";

	/**
	 * 文件初始化
	 */
	public static void init(String openid) {
		OutputStream outputStream = null;
		try {
			Properties properties = new Properties();
			outputStream = new FileOutputStream(filename);
			properties.store(outputStream, "ZCY");
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try{
			if(outputStream != null){
				outputStream.close();
			}}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	/**
	 * 读取文件，并返回查询字段的值
	 * 
	 * @param openid
	 *            用户的openid
	 * @param field
	 *            要获取的字段
	 * @return
	 */
	public static String read(String openid, String field) {
		String result = "";
		Properties properties = new Properties();
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(filename);
			properties.load(inputStream);
			inputStream.close(); // 关闭流
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(inputStream != null){
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	/**
	 * 写入文件，并写入指定字段的值
	 * 
	 * @param openid
	 * @param field
	 * @param value
	 */
	public static void writ(String openid, String field, String value) {
		Properties properties = new Properties();
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(filename);
			properties.load(inputStream);
			inputStream.close(); // 关闭流
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(inputStream != null){
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
