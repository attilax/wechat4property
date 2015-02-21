package com.focustar.entity;

import java.util.Date;

public class HdBindHistory {
	
	private Integer id;
	private String openId;
	private String memberId;
	private Integer bindStatus;
	private Date asyncTime;
	private String remark;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public Integer getBindStatus() {
		return bindStatus;
	}
	public void setBindStatus(Integer bindStatus) {
		this.bindStatus = bindStatus;
	}
	public Date getAsyncTime() {
		return asyncTime;
	}
	public void setAsyncTime(Date asyncTime) {
		this.asyncTime = asyncTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	

}
