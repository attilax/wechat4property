package com.focustar.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * @category 签到历史表
 * @author Administrator
 *
 */

public class TMbSignHistory implements Serializable{
	
	private int id;
	private String openid;
	private String memberId;
	private Integer    groupid;
	private Date   signDate;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public Date getSignDate() {
		return signDate;
	}
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}
	public Integer getGroupid() {
		return groupid;
	}
	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}
	
	
	

}
