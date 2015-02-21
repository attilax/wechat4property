package com.focustar.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * @category 图文阅读记录
 * @author Administrator
 *
 */

public class TMbNewsHistory implements Serializable{
	
	public final static int  NEWS_OPER_READ = 1;//阅读
	public final static int  NEWS_OPER_SEND = 2;//发送
	public final static int NEWS_OPER_SHARE = 3;//转发，即分享，暂时无法跟踪
	
	private int id;
	private int newsType;	//图文类型
	private int groupId;
	private int newsId;    //图文id
	private String openid;	//
	private String memberId;//会员id
	private Date operTime; //操作时间
	private int operType; //操作类型
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getNewsType() {
		return newsType;
	}
	public void setNewsType(int newsType) {
		this.newsType = newsType;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	
	public int getOperType() {
		return operType;
	}
	public void setOperType(int operType) {
		this.operType = operType;
	}
	public int getNewsId() {
		return newsId;
	}
	public void setNewsId(int newsId) {
		this.newsId = newsId;
	}
	public Date getOperTime() {
		return operTime;
	}
	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	
	
	

}
