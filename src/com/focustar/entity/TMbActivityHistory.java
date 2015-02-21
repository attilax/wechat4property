package com.focustar.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @category 活动访问历史表
 * @author Administrator
 *
 */

public class TMbActivityHistory implements Serializable{
	
	private int id;
	private Integer groupId;
	private int activityId; //活动id
	private String openid;   //微信id
	private String memberId;	//会员id
	private Date operTime;		//操作时间
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getActivityId() {
		return activityId;
	}
	public void setActivityId(int activityId) {
		this.activityId = activityId;
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
	public Date getOperTime() {
		return operTime;
	}
	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	
	
	
	

}
