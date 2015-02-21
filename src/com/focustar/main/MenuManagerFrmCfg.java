package com.focustar.main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.attilax.core;
import com.attilax.collection.list;
import com.attilax.collection.listUtil;
import com.attilax.io.filex;
import com.attilax.io.pathx;
import com.focustar.entity.weixin.pojo.AccessToken;
import com.focustar.entity.weixin.pojo.Button;
import com.focustar.entity.weixin.pojo.CommonButton;
import com.focustar.entity.weixin.pojo.ComplexButton;
import com.focustar.entity.weixin.pojo.Menu;
import com.focustar.entity.weixin.pojo.ViewButton;
import com.focustar.util.ConfigService;
import com.focustar.util.Constant;
import com.focustar.util.WeixinUtil;

/**
 * 菜单管理器类 author:vincente 2013-11-5
 */
public class MenuManagerFrmCfg {

	private static Logger log = Logger.getLogger(MenuManagerFrmCfg.class);
	
	private static String appId = "";
	private static String appSecret = "";

	private static String webSite = "";

	private static String scrchStat;

	private static String sgeStat;

	public String menuFilePath ;

	public String cfgFile ;
	
	public static  String frsh()
	{
		main(null);
		return scrchStat+":"+sgeStat;
	}
	public static void main(String[] args) {
		System.out.println("--start");
	//	org.apache.log4j.Logger

	//	exec();
		MenuManagerFrmCfg mc=new MenuManagerFrmCfg();
		mc.menuFilePath	= pathx.classPath(MenuManagerFrmCfg.class)+File.separator+ "Menu.json";
		mc.cfgFile= Constant.path + "WeiXinConfig.properties";
		mc.exec();
	System.out.println("--");	
		
	}
	private  void exec() {
		
		String menu_txt=com.attilax.io.filex.read(menuFilePath);
		System.out.println(menu_txt);
//		if(!"2".equals("bb"))
//			return;
		Class<?> classTypeConfig = ConfigService.class;
		ConfigService configService = null;
		try {
			configService = (ConfigService) classTypeConfig.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String TOKENO5 = configService.getWxProperty("TOKEN",cfgFile);
		// 第三方用户唯一凭证
		appId = configService.getWxProperty("APPID",cfgFile);
		// 第三方用户唯一凭证密钥
		appSecret = configService.getWxProperty("APPSECRET",cfgFile);

		webSite = configService.getWxProperty("NEWS_WEBSITE",cfgFile);
		// 调用接口获取access_token
		AccessToken accessToken = WeixinUtil.getAccessToken(appId, appSecret);

		if (accessToken != null) {

			
			core.log(menuFilePath);
			// 调用接口创建菜单
			int result = com.attilax.wechat.WeixinUtil.createMenu_jsonFile(menu_txt, accessToken.getToken());
				// 判断菜单创建结果
			if (0 == result) {
				log.info("TOKEN>>>		" + TOKENO5);
				log.info("appId>>>		" + appId);
				log.info("appSecret>>>		" + appSecret);
				log.info("菜单创建成功！");
			} else {
				log.info("菜单创建失败，错误码：" + result);
			}
		} else {
			log.info("解析token失败!");
		}
	}
}
