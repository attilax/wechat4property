package com.focustar.thread;

import java.util.Date;

import com.focustar.dao.impl.HistoryImpl;
import com.focustar.dao.impl.NewsImpl;
import com.focustar.entity.TMbEventHistory;
import com.focustar.entity.TMbGroup;
import com.focustar.entity.TMbWeixinuser;
import com.focustar.util.MyCacher;

public class EventHistoryJober implements Runnable {

	
	private Integer groupId;
	private String openId;
	private String enentKey;
	private String memberId;
	
	public EventHistoryJober(String openId,String eventKey){
		this.openId = openId;
		this.enentKey = eventKey;
	}
	
	public EventHistoryJober(String openId,String memberId,String eventKey){
		this(openId,eventKey);
		this.memberId = memberId;
	}
	
	
	public void run(){
		
		TMbWeixinuser weixinUser = (TMbWeixinuser)MyCacher.getInstance().getCache(this.openId);
		try{
			
			TMbEventHistory his = new TMbEventHistory();
			his.setEventKey(enentKey);
			his.setOpenid(openId);
			his.setMemberId(memberId);
			his.setClickTime(new Date());
			HistoryImpl historyDao = new HistoryImpl();
			
			TMbGroup curGroup = null;
			
			if(weixinUser != null){
				if(weixinUser.getGroup() != null && weixinUser.getGroup().getParent() == null){
					curGroup = weixinUser.getGroup();
				}else if(weixinUser.getGroup() != null && weixinUser.getGroup().getParent() != null){
					curGroup = weixinUser.getGroup().getParent();
				}else{
					curGroup = NewsImpl.checkProvinceAndCity(weixinUser.getProvince(),weixinUser.getCity());
				}
			}
			if(curGroup != null){
				his.setGroupId(curGroup.getGroupid());
			}
			historyDao.addEventHistory(his);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	

}
