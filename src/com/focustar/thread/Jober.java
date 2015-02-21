package com.focustar.thread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import net.sf.json.JSONObject;

import com.focustar.dao.impl.NewsImpl;
import com.focustar.dao.impl.UserImpl;
import com.focustar.entity.TMbGroup;
import com.focustar.entity.TMbNewsHistory;
import com.focustar.entity.TMbWeixinuser;
import com.focustar.util.Constant;
import com.focustar.util.MyCacher;
import com.focustar.util.WeixinUtil;

public abstract class Jober   implements Runnable{
	
	public final static int MAX_RETRY = 100; // 最大尝试次数
	protected static Logger logger = Logger.getLogger(Jober.class);
	protected Object msgObj = null;
	private SimpleDateFormat  sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	protected String openId = null;
	protected String eventKey = null;
	protected TMbWeixinuser user = null;
	protected int    newsType = 0;
	protected int    newsId = 0;
	protected String md5UserKey  = null;
	protected int operType = 0;
	protected int groupId = 0;
	
	public void run() {
		
		String beginTime = sdf.format(new Date());
		logger.info("线程   开始发送 时间[" + beginTime + "]");
		startWork();
		send();
		String endTime = sdf.format(new Date());
		logger.info("线程   结束发送时间 [" + endTime + "]");
		
	}
	
	protected void send(){
		
		if(msgObj != null){
			String jsonData = JSONObject.fromObject(msgObj).toString();
			int retCode = WeixinUtil.sendMessage(jsonData,Constant.token.getToken());
			if(retCode == 0){
				logger.info("发送用户["+this.openId+"]成功！");
				NewsHistoryJober newsHistory = null;
				if(this.newsType != 0){
					if(user != null){
						if(user.getGroup() != null && user.getGroup().getParent() != null){
							groupId = user.getGroup().getParent().getGroupid();
						}else if(user.getGroup() != null && user.getGroup().getParent() == null){
							groupId = user.getGroup().getGroupid();
						}else {
							TMbGroup tmpGroup = NewsImpl.checkProvinceAndCity(user.getProvince(),user.getCity());
							if(tmpGroup != null){
								groupId = tmpGroup.getGroupid();
							}
						}
						if(this.newsId > 0){
							newsHistory = new NewsHistoryJober(user,newsType,TMbNewsHistory.NEWS_OPER_SEND,this.newsId);
							newsHistory.setGroupId(groupId);
						}else{
							newsHistory = new NewsHistoryJober(user,newsType,TMbNewsHistory.NEWS_OPER_SEND);
							newsHistory.setGroupId(groupId);
						}
					}else{
						if(this.newsId > 0){
							newsHistory = new NewsHistoryJober(openId,newsType,TMbNewsHistory.NEWS_OPER_SEND,this.newsId);
						}else{
							newsHistory = new NewsHistoryJober(openId,newsType,TMbNewsHistory.NEWS_OPER_SEND);
						}
					}
					
					if(newsHistory != null){
						Constant.CountJober.execute(newsHistory);
					}
				}
				
			}
			
		}else{
			logger.info("消息对象为空？");
		}
		
	}
	
	protected abstract void startWork();
	
	
	protected void downloadUser(){
		
		if(openId != null && md5UserKey != null){
				UserImpl userDao = new UserImpl();
				//查询数据库
				user = userDao.getUserByopenid(openId);
				if (user != null) {
					MyCacher.getInstance().putCache(md5UserKey, user);
				} else {// 正常情况下，数据库不可能没有当前粉丝的数据
					CountDownLatch downLatch = new CountDownLatch(1);
					//没有微信用户信息,马上下载
					DownloadUserJober uJober = new DownloadUserJober(openId,false);
					uJober.setCountDown(downLatch);
					try {
					     Constant.AsyncJober.execute(uJober);
						downLatch.await(60,TimeUnit.SECONDS);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					//再查一次
					user = userDao.getUserByopenid(openId);
					if (user != null) {
						MyCacher.getInstance().putCache(md5UserKey, user,Constant.TEN_MINUTES);
					}
				}
		}
	}
	

}
