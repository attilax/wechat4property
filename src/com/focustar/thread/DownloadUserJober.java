package com.focustar.thread;

import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;

import com.attilax.Closure;
import com.focustar.entity.TMbWeixinuser;
import com.focustar.main.FansManager;

public class DownloadUserJober implements Runnable {

	private static Logger logger = Logger.getLogger(DownloadUserJober.class);
	
	public static Logger focusLogger = Logger.getLogger("focusLogger");
	private String openid;
	private Integer groupid = 0;
	private int     shareId = 0;
	private boolean isFocus =false;
	private boolean isShare = false;
	private boolean isUpdate = false;
	private CountDownLatch countDown;
	
	public DownloadUserJober(String openid,boolean isFocus){
		this.openid = openid;
		this.isFocus = isFocus;
	}
	
	public DownloadUserJober(String openid,boolean isFocus,boolean isUpdate){
		this(openid,isFocus);
		this.isUpdate = isUpdate;
	}
	
	public DownloadUserJober(String openid,boolean isFocus,boolean isShare,int shareId){
		this(openid,isFocus);
		this.isShare = isShare;
		this.shareId = shareId;
	}
	
	public DownloadUserJober(String openid,Integer groupid,boolean isFocus){
		this(openid,isFocus);
		this.groupid = groupid;
	}
	public Closure<TMbWeixinuser,Object>  getUserinfoFrmWechatHandler;
	public void run() {
		logger.info("开始下载用户信息  【"+openid+"】");
		focusLogger.info("开始下载用户信息  【"+openid+"】");
		try{
			FansManager fansManager = new FansManager();
			if(this.groupid != 0){
				fansManager.createUserByScan(openid, groupid,isFocus);
			}else{
				if(this.shareId > 0){
					fansManager.createUser(openid,isFocus,isShare,shareId);
				}else{
					if(isUpdate){
						fansManager.createUser(openid,isFocus,isUpdate);
					}else{// default if this  ati p1h
						focusLogger.info("开始 invoke fansManager.createUser(openid,isFocus)");
						fansManager.getUserinfoFrmWechatHandler=getUserinfoFrmWechatHandler;
						fansManager.createUser(openid,isFocus);
					 
						//Closure<TMbWeixinuser,Object>  getUserinfoFrmWechatHandler;
					}
					
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("ep28a", e);
			focusLogger.error("ep28a",e);
		}finally{
			//确保线程不为死锁，无限等待
			if(countDown != null){
				logger.info("倒计时结束");
				countDown.countDown();
			}
		}
		
	}

	public void setCountDown(CountDownLatch countDown) {
		this.countDown = countDown;
	}
	
	

}
