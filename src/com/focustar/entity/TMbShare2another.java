package com.focustar.entity;

import java.io.Serializable;
import java.util.Date;

public class TMbShare2another implements Serializable{

	private int pid;
	private String openid;
	private Date   createTime;
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	
}
