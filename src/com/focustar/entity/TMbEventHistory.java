package com.focustar.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * @category 菜单点击率
 * @author Administrator
 *
 */

public class TMbEventHistory implements Serializable{
	
	private int id;
	private Integer groupId;
	private String eventKey;	//事件key
	private String openid;    //微信号
	private String memberId;  //会员id
	private Date   clickTime; //点击时间
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEventKey() {
		return eventKey;
	}
	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
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
	public Date getClickTime() {
		return clickTime;
	}
	public void setClickTime(Date clickTime) {
		this.clickTime = clickTime;
	}
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	
	
	

}
