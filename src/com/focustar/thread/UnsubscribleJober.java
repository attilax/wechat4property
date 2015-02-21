package com.focustar.thread;

import com.focustar.dao.impl.ListenerImpl;
import com.focustar.util.MD5;
import com.focustar.util.MyCacher;

public class UnsubscribleJober extends Jober {
	
	public UnsubscribleJober(String openId){
		this.openId = openId;
	}

	@Override
	protected void startWork() {
		// TODO Auto-generated method stub
		logger.info("取消关注程序开始执行");
		logger.info("微信用户  【"+openId+"】 取消关注");
		ListenerImpl listenerDao = new ListenerImpl();
		try {
			listenerDao.unsubscribleWeiXinUser(openId);
			//同时清除缓存,如果存在的话
			MyCacher.getInstance().removeCache(MD5.getMD5(openId));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
