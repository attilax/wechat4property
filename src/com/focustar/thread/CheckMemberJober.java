package com.focustar.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import com.focustar.dao.impl.UserImpl;
import com.focustar.entity.TMbWeixinuser;
import com.focustar.util.Constant;
import com.focustar.util.WeixinUtil;

public class CheckMemberJober extends Jober {
	
	public CheckMemberJober(String openId){
		logger.info("初始化检查会员信息的线程...");
		this.openId = openId;
	}
	
	public CheckMemberJober(String openId,String eventKey){
		this.openId = openId;
		this.eventKey = eventKey;
	}
	

	@Override
	protected void startWork(){
		logger.info("会员绑定检查...");
		String bindTips = "";
		UserImpl userDao = new UserImpl();
		//查询用户信息
		user = userDao.getUserByopenid(openId);
		
		if(user == null){
			CountDownLatch downLatch = new CountDownLatch(1);
			//没有微信用户信息？马上下载
			DownloadUserJober uJober = new DownloadUserJober(openId,false);
			uJober.setCountDown(downLatch);
			try {
			     Constant.AsyncJober.execute(uJober);
				downLatch.await(60,TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			user = userDao.getUserByopenid(openId);
		}
		
		//检查是否绑定
		if(user != null && user.getMember() == null){//未绑定
			bindTips = (String)Constant.MSG_MAP.get(Constant.MEMBER_UNBIND);
			
			StringBuilder bUrl = new StringBuilder();
			bUrl.append("<a href='");
			bUrl.append(Constant.WEB_URL).append("weixin");
			bUrl.append("/memberServlet?view=bind&openid=");
			bUrl.append(openId);
			bUrl.append("' >");
			bUrl.append("绑定页面入口").append("</a>");
			
			bindTips = bindTips.replaceAll(Constant.RP_BIND_URL, bUrl.toString());
			logger.info(bindTips);
			msgObj = WeixinUtil.buildTextMessage(openId, bindTips);
			
			if(this.eventKey != null){
				EventHistoryJober history = new EventHistoryJober(openId,eventKey);
				Constant.CountJober.execute(history);
			}
		}else if(user != null && user.getMember() != null){
			bindTips = (String)Constant.MSG_MAP.get(Constant.MEMBER_BINDED);
			msgObj = WeixinUtil.buildTextMessage(openId, bindTips);
			
			if(this.eventKey != null){
				EventHistoryJober history = new EventHistoryJober(openId,user.getMember().getMemberId(),eventKey);
				Constant.CountJober.execute(history);
			}
			
		}else{
			logger.info("用户信息为空！无法从网络下载！");
		}

	}

}
