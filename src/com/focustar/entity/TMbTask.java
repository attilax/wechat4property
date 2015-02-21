package com.focustar.entity;

import java.io.Serializable;
import java.util.Date;

public class TMbTask implements Serializable{
	
	public final static int STATE_UNREAD = 0;	//未读
	public final static int STATE_READ = 1;	    //已读
	public final static int STATE_FEEDBACK = 2; 
	
	public final static int MSGTYPE_TEXT = 0;	//文本类型
	public final static int MSGTYPE_IMG = 1;    //图片类型
	public final static int MSGTYPE_VIDEO = 2;  //视频类型
	public final static int MSGTYPE_VOICE = 3;  //语音类型
	public final static int MSGTYPE_LOC = 4;
	public final static int MSGTYPE_LINK = 5;
	
	private Integer 	id;
	private String      openid;
	private String 		msgContent; //消息内容
	private String 		mediaUrl;	//媒体地址
	private Date    	publishTime; //发送时间
	private Integer 	state;		//状态
	private Integer  	userId;		//操作用户id
	private Integer	 	msgType;	//消息类型
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMsgContent() {
		return msgContent;
	}
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	public String getMediaUrl() {
		return mediaUrl;
	}
	public void setMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Date getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getMsgType() {
		return msgType;
	}
	public void setMsgType(Integer msgType) {
		this.msgType = msgType;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}

}
