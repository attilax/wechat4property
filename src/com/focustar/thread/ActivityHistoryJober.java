package com.focustar.thread;

import java.util.Date;

import org.apache.log4j.Logger;

import com.focustar.dao.impl.ActivityImpl;
import com.focustar.dao.impl.NewsImpl;
import com.focustar.entity.TMbActivityHistory;
import com.focustar.entity.TMbGroup;
import com.focustar.entity.TMbWeixinuser;

public class ActivityHistoryJober implements Runnable {
	
	private int activityId;
	private int groupId;
	private String openid;
	private String memberId;
	private TMbWeixinuser user;
	private final static Logger log = Logger.getLogger(ActivityHistoryJober.class);
	
	public ActivityHistoryJober(int activityId){
		this.activityId = activityId;
	}
	
	public ActivityHistoryJober(int activityId,String openid){
		this(activityId);
		this.openid = openid;
	}
	
	public ActivityHistoryJober(int activityId,String openid,String memberId){
		this(activityId,openid);
		this.memberId = memberId;
	}
	
	public ActivityHistoryJober(int activityId,TMbWeixinuser user,boolean tt){
		this(activityId);
		this.user = user;
		
	}
	

	public void run() {
		log.info("添加活动相关访问记录");
		try{
			TMbActivityHistory history = new TMbActivityHistory();
			history.setActivityId(activityId);
			history.setOperTime(new Date());
			if(user != null){
				history.setOpenid(user.getOpenid());
				if(user.getMember() != null){
					history.setMemberId(user.getMember().getMemberId());
				}
				
				if(user.getGroup() != null && user.getGroup().getParent() != null){
					groupId = user.getGroup().getParent().getGroupid();
				}else if(user.getGroup() != null && user.getGroup().getParent() == null){
					groupId = user.getGroup().getGroupid();
				}else{
					TMbGroup tmpGroup = NewsImpl.checkProvinceAndCity(user.getProvince(),user.getCity());
					
					if(tmpGroup != null){
						groupId = tmpGroup.getGroupid();
					}
				}
				
				history.setGroupId(groupId);
				
			}else{
				history.setOpenid(openid);
				history.setMemberId(memberId);
			}
			
			ActivityImpl activityDao = new ActivityImpl();
			if(activityDao.addActivityHistory(history)){
				log.info("添加活动记录成功");
			}else{
				//log.info("")
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
