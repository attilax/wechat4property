package com.attilax.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.logging.Logger;
import com.attilax.io.pathx;

/**
 * 工具类
 * @author freeteam
 *
 */
public class PropX {
	
	public static void main(String[] args) {
		//todox p1f prop add field and add time recomme text
//		Properties prop = new Properties();
//		prop.put("token", accessToken.getToken());
//		fos = new FileOutputStream(tmpTokenFile);
//		prop.store(fos,sdf.format(systemDate));
//		fos.close();
		
	}
	private int getConfig(String path,String name,String encode) {
		try {
			String f =path;// pathx.webAppPath() + "/noticerCfg.txt";
			Properties props = new Properties();

			props.load(new InputStreamReader(new FileInputStream(f), encode));

			int retime = Integer.parseInt(props.getProperty("retrytimes"));
			return retime;
		} catch (Exception e) {
			return 7;
		}
	}
	//获取配置文件的配置
	public static String getConfig(String path,String name){
		FileInputStream sins=null;
		String value="";
		try {
			Properties loginprop = new Properties();
			sins = new FileInputStream(path);
			loginprop.load(sins);
			value=loginprop.getProperty(name);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (sins!=null) {
		        try {
					sins.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return value;
		}
	}
}
