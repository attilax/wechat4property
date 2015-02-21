package com.focustar.thread;

import org.apache.log4j.Logger;

import m.datepkg.LOGGER;

import com.focustar.dao.impl.GroupImpl;
import com.focustar.entity.TMbGroup;
import com.focustar.util.Constant;
import com.focustar.util.WeixinUtil;

public class LocationUpdateJober extends Jober {
	
	private int operType = 0; //操作类型 0 记录更新请求 1开始更新
	private int groupId;
	private Double latitude;
	private Double longitude;
	private String openId;
	
	private final static Logger log = Logger.getLogger(LocationUpdateJober.class);
	
	public LocationUpdateJober(String openId,int groupId,int operType,Double latitude,Double longitude){
		this.openId = openId;
		this.groupId = groupId;
		this.operType = operType;
		this.latitude = latitude;
		this.longitude = longitude;
		logger.info("该用户  ["+this.openId+"] 请求更新分店坐标！");
	}
	


	@Override
	protected void startWork() {
		switch(this.operType){
		case 0:
			LOGGER.info("记录当前需要更新的分店编号  >>>>" + groupId);
			if(groupId > 0){
				GroupImpl groupDao = new GroupImpl();
				TMbGroup group = groupDao.selectOneGroup(this.groupId);
				if(group != null){
					log.info("找到该分店");
					Constant.UPDATE_LOC_MAP.put(this.openId,group);
					String text = "开始更新坐标程序，请上传您的坐标！";
					msgObj = WeixinUtil.buildTextMessage(this.openId,text);
				}else{
					log.info("无该分店！");
					msgObj = WeixinUtil.buildTextMessage(this.openId,"找不到该分店，请重新输入！");
				}
			}
			break;
		case 1:
				
					TMbGroup group = Constant.UPDATE_LOC_MAP.get(this.openId);
				
					if(group != null){
						
						log.info("取得记录更新的分店["+group.getGroupname()+"]信息");
						
						GroupImpl groupDao = new GroupImpl();
						double oldLat = group.getLatitude();
						double oldLng = group.getLongitude();
						group.setLatitude(latitude);
						group.setLongitude(longitude);
						if(groupDao.updateGroup(group)){
							log.info("更新分店["+group.getGroupname()+"]座标成功！");
							Constant.UPDATE_LOC_MAP.remove(this.openId);
							msgObj = WeixinUtil.buildTextMessage(this.openId,"更新["+group.getGroupname()+"]的原始latitude["+oldLat+"]与longitude["+oldLng+"]为latitude["+group.getLatitude()+"]与longitude["+group.getLongitude()+"]坐标成功");
						}else{
							log.info("更新分店["+group.getGroupname()+"]座标失败！");
							msgObj = WeixinUtil.buildTextMessage(this.openId,"更新坐标失败!");
						}
					
					}else{
						log.info("无更新分店信息");
					}
				
			break;
		default:
			 log.info("无操作类型");
			break;
		
	
	}
		
	}

}
