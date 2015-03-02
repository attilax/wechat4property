package com.focustar.Listener;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import com.focustar.dao.impl.GroupImpl;
import com.focustar.dao.impl.NewsImpl;
import com.focustar.dao.impl.TagImpl;
import com.focustar.entity.TMbGroup;
import com.focustar.entity.TMbNews;
import com.focustar.entity.TMbTag;
import com.focustar.thread.Bind2HdJober;
import com.focustar.thread.TokenThread;
import com.focustar.util.ConfigService;
import com.focustar.util.Constant;
import com.focustar.util.MD5;
import com.focustar.util.MyCacher;

/**
 * 获取公众号的所有粉丝列表，并插入到本地数据库中
 * 
 * @author 张春雨
 * @模块
 * @日期 2013-12-14 时间：下午09:13:59
 */
public class MyListener implements ServletContextListener {

	private static Logger logger = Logger.getLogger(MyListener.class);
	
	public static void main(String[] args) {
		new MyListener().contextInitialized(null);
	}
	
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	// 初始化程序，TOMCAT启动后自动执行
	public void contextInitialized(ServletContextEvent arg0) {
		//设置常量
		ConfigService config = new ConfigService();
		
		String appId = config.getWxProperty("APPID");
		String appSecret = config.getWxProperty("APPSECRET");
		
		//获取图文的设置
		String webSite 		 = config.getWxProperty("NEWS_WEBSITE");
		String newsImage 	 = config.getWxProperty("NEWS_IMAGE_PATH");
		String newsHtml 	 = config.getWxProperty("NEWS_HTML_PATH");
		
		String baseUrl       = config.getWxProperty("BASE_URL");
		
		//会员积分来源设置
		String creditFromKey = config.getWxProperty("CREDIT_FROM_KEY");
		String creditFromValue = config.getWxProperty("CREDIT_FROM_VALUE");
		
		//api调用的用户名与密码
		String username = config.getWxProperty("HD_API_USER");
		String password = config.getWxProperty("HD_API_PWD");
		
		String mediaDir = config.getWxProperty("MEDIA_DIR");
		
		String msgKeys = config.getWxProperty("MSG_KEYS");
		
		String[] keyAry = msgKeys.split(",");
		for(String key:keyAry){
			if(key!=null && !"".equals(key)){
				String value = config.getMsgProperty(key);
				Constant.MSG_MAP.put(key, value);
			}
		}
		
		String credit = config.getWxProperty("ADD_CREDIT");
		
		System.out.println(Constant.MSG_MAP);
		
		Constant.APPID = appId;
		Constant.APPSECRET = appSecret;
		
		Constant.WEB_URL = baseUrl;
		
		//初始化读取图文相关设置
		Constant.NEWS_WEB_SITE = webSite;
		Constant.NEWS_HTML_PATH = newsHtml;
		Constant.NEWS_IMAGE_PATH = newsImage;
		
		//初始化api的用户名与密码
		Constant.HD_API_USER = username;
		Constant.HD_API_PWD = password;
		
		//初始化积分的设置
		Constant.CREDIT_FROM_KEY = creditFromKey;
		Constant.CREDIT_FROM_VALUE = creditFromValue;
		Constant.SIGN_CREDIT = Integer.parseInt((credit!=null&&!"".equals(credit))?credit:"10");
		
		
		Constant.MEDIA_FILE_PATH = mediaDir;
		
		//初始化允许发送通知的服务器列表
		
		String noteServerStr = config.getWxProperty("NOTE_SERVER");
		
		if(noteServerStr != null){
		
			String[] serAry = noteServerStr.split(",");
			Constant.NOTE_SERVER.addAll(Arrays.asList(serAry));
			System.out.println(Constant.NOTE_SERVER);
		}
		
		this.createSessionFactory();
		
	//	this.initHDServer();

	//	this.cacheNews();
		
		this.initRetureCode();
		
		// 启动获取微信授权码的定时器
		TokenThread token = new TokenThread();
		//每7170秒后，更新一次accessToken，腾讯设置的有效期为7200,提交30秒请求更新
		try{
			Constant.scheduleJober.scheduleWithFixedDelay(token, 0, 7170,TimeUnit.SECONDS);
		}catch(Exception e){
			e.printStackTrace();
		}
			//加载关键字配置
		
		
		//定时同步已绑定会员到hd的服务器
		long now = System.currentTimeMillis();
		
		//目标时间，零晨2点正
//		GregorianCalendar targetTime = new GregorianCalendar();
//		
//		targetTime.set(Calendar.DAY_OF_YEAR, targetTime.get(Calendar.DAY_OF_YEAR) + 1);
//		targetTime.set(Calendar.HOUR_OF_DAY, 2);
//		targetTime.set(Calendar.MINUTE,0);
//		targetTime.set(Calendar.SECOND,0);
//		targetTime.set(Calendar.MILLISECOND,0);
//		
//		//当前时间到指定时间的间隔
//		long delay = targetTime.getTimeInMillis() - now;
//		
//		long oneDay = 24 * 60 * 60 * 1000;
		
		//Bind2HdJober bindJober = new Bind2HdJober();
		//每天执行一次,并马上执行一次
		//Constant.scheduleJober.scheduleAtFixedRate(bindJober, 0, oneDay + delay, TimeUnit.DAYS);
	}

	/**
	 * 读取Hibernate配置文件,并生成SessionFactory
	 */
	public void createSessionFactory() {
		logger.info("系统正在读取Hibernate配置文件，并生成SessionFactory");
		Configuration cfg = new Configuration().configure();
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(cfg.getProperties()).buildServiceRegistry();
		Constant.sessionFactory = cfg.buildSessionFactory(serviceRegistry);
	}
	
	
	private void initHDServer(){
		logger.info("初始化会员服务器...");
		GroupImpl groupDao = new GroupImpl();
		TagImpl   tagDao = new TagImpl();
		//获取相关分公司的服务器url
		List<TMbGroup> serverList = groupDao.selectTopGroup();
		if(serverList != null && serverList.size() > 0){
			StringBuffer bKey = new StringBuffer();
			for(TMbGroup one:serverList){
				List<TMbTag> tagList = tagDao.selectTagByGroupid(one.getGroupid());
				one.setTagList(tagList);
				if(one.getServerUrl() != null){
					bKey.setLength(0);
					logger.info(one.getGroupname() + " >>> " + one.getServerUrl());
					bKey.append("group_").append(one.getGroupid());
					Constant.SERVER_MAP.put(bKey.toString(),one);
				}
			}
			
			if(Constant.SERVER_MAP.size() > 0){
				Constant.SERVER_KEY_ARRAY = Constant.SERVER_MAP.keySet().toArray();
			}
		}
	}
	
	private void cacheNews(){
		logger.info("开始缓存图文...");
		//缓存相关图文
		NewsImpl newsDao = new NewsImpl();
		List<TMbNews> newsList = newsDao.getNewsListForCache();
		if(newsList != null && newsList.size() > 0){
			StringBuilder builderKey = null;
			String md5Key = null;
			
			Map<String,List<TMbNews>> tmpMap = new TreeMap<String,List<TMbNews>>();
			
			for(TMbNews oneNews:newsList){
				builderKey = new StringBuilder();
				builderKey.append(oneNews.getNewsType())
						  .append("_")
						  .append(oneNews.getGroupId());
				
				List<TMbNews> obj = tmpMap.get(builderKey.toString());
				if(obj == null){
					List<TMbNews> cacheList = new ArrayList<TMbNews>();
					cacheList.add(oneNews);
					tmpMap.put(builderKey.toString(), cacheList);
					
				}else{
					obj.add(oneNews);
				}
			}
			for(Map.Entry<String,List<TMbNews>> cacheOne:tmpMap.entrySet()){
				
				md5Key = MD5.getMD5(cacheOne.getKey());
				//缓存一星期
				MyCacher.getInstance().putCache(md5Key,cacheOne.getValue(),Constant.A_WEEK_SECONDS);
			}
			
		}
	}
	
	
	private void initRetureCode(){
		
		
		try {
			Properties message = new Properties();
			
			String filename = Constant.path + "returnCode.properties";
			InputStream inputStream = null;
			try {
				// 取得资源文件输入流
				inputStream = new FileInputStream(filename);
				message.load(inputStream);
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
			
			if(!message.isEmpty()){
				for(Map.Entry<Object,Object> oneEntry:message.entrySet()){
					try{
					if(oneEntry.getKey() != null && oneEntry.getValue() != null){
						Constant.CODE_MAG.put((String)oneEntry.getKey(), new String(((String)oneEntry.getValue()).getBytes("ISO-8859-1"),"UTF-8"));
					}
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
			logger.info(Constant.CODE_MAG);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	/***
	 * @category 加载每个分公司设置的二维码
	 * 分别是
	 */
	
	private void loadGroupQRCode(){
		//GroupImpl groupDao = new GroupImpl();
		/*List<TMbGroupQrcode> qrcodeList = groupDao.selectGroupQrode();
		System.out.println(qrcodeList);
		if(qrcodeList != null && qrcodeList.size() > 0){
			
		}*/
	}

}
