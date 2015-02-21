package com.attilax.wechat;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.LogXF;
import org.apache.log4j.Logger;

import com.attilax.core;
import com.attilax.util.DateUtil;
import com.focustar.entity.weixin.pojo.AccessToken;
import com.focustar.util.ConfigService;
//import com.focustar.util.this;
import com.focustar.util.WeixinUtil;

public class TokenGetterT extends Thread {
	
	public static void main(String[] args) {
		
	
		ExecutorService es=	Executors.newFixedThreadPool(100);
		
		es.execute(new Runnable() {
			
			@Override public void run() {
				// attilax 老哇的爪子  2_q_l   o0n 
				
			}
		});
	  ScheduledExecutorService scheduleExetor = Executors.newScheduledThreadPool(5);
	//  scheduleExetor.scheduleAtFixedRate(command, initialDelay, period, unit)
	  scheduleExetor.scheduleWithFixedDelay(new Runnable() {
		
		@Override public void run() {
			// attilax 老哇的爪子  0_53_1   o0j 
			
			TokenGetterT c=new TokenGetterT();
			c.APPID="wx94d1460e167fff4e";//focsd
			c.APPSECRET="60c45ce913d302a2030371d581215801";
			c.run();
			
		}
	}, 0, 100, TimeUnit.MILLISECONDS);
	  
	}
	private static Logger logger = Logger.getLogger(TokenGetterT.class);
	
	private CountDownLatch countDown;

	private String APPSECRET;

	private String APPID;

	public static AccessToken accTok;
	public void run() {
		
		FileOutputStream fos = null;
		try {
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			
//			StringBuilder builder = new StringBuilder();
//			builder.append(this.path);
//			builder.append("token.properties");
//			File tmpTokenFile = new File(builder.toString());
//			if(!tmpTokenFile.exists()){
//				tmpTokenFile.createNewFile();
//			}

				Date systemDate = new Date();
				logger.info("---------------[微信授权码定时器]任务开始运行..." + DateUtil.date2str(new Date(),true) + "---------------");
				AccessToken accessToken = WeixinUtil.getAccessToken(this.APPID, this.APPSECRET);
				 
				if (accessToken != null) {
					logger.info("---------------[微信授权码获取成功]--------");
				} else {
					logger.info("---------------[微信授权码获取失败]--------");
				}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try{
				if(fos != null){
					fos.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
			if(this.countDown != null){
				this.countDown.countDown();
			}
		}
	}

	public void setCountDown(CountDownLatch countDown) {
		this.countDown = countDown;
	}
	
	

}
