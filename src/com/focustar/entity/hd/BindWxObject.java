package com.focustar.entity.hd;

public class BindWxObject{
	
	private final static String  BIND_URL = "/member/bind_wx_by_mobilenum?";
	private String xid = "0000000000001";	//业务流水
	private String orgcode = "3";	//相当于积分来源，默认为3,表示 来自jvsiny
	private String mobilenum;	//手机号
	private String wx;  //微信号
	
	public String getXid() {
		return xid;
	}
	public void setXid(String xid) {
		this.xid = xid;
	}
	public String getOrgcode() {
		return orgcode;
	}
	public void setOrgcode(String orgcode) {
		this.orgcode = orgcode;
	}
	public String getMobilenum() {
		return mobilenum;
	}
	public void setMobilenum(String mobilenum) {
		this.mobilenum = mobilenum;
	}
	public String getWx() {
		return wx;
	}
	public void setWx(String wx) {
		this.wx = wx;
	}
	
	
	public String toString(){
		
		StringBuilder builder = new StringBuilder();
		
		builder.append(BIND_URL).append("xid=").append(this.xid)
		.append("&orgcode=").append(this.orgcode)
		.append("&mobilenum=").append(this.mobilenum)
		.append("&wx=").append(this.wx);
		return builder.toString();
		
		
	}
	
}