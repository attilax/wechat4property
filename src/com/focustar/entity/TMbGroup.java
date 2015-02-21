package com.focustar.entity;

import java.util.Date;
import java.util.List;

public class TMbGroup implements java.io.Serializable{
	
	private int groupid;
	private String groupname;
	private String remark;
	private Date  createtime;
	
	private TMbGroup parent;
	
	private Double latitude;
	private Double longitude;
	
	private String serverUrl;
	
	private int distance;
	
	
	private List<TMbTag> tagList;
	
	public int getGroupid() {
		return groupid;
	}
	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public TMbGroup getParent() {
		return parent;
	}
	public void setParent(TMbGroup parent) {
		this.parent = parent;
	}

	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
	
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	public String getServerUrl() {
		return serverUrl;
	}
	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}
	public List<TMbTag> getTagList() {
		return tagList;
	}
	public void setTagList(List<TMbTag> tagList) {
		this.tagList = tagList;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	

}
