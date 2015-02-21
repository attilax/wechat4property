package com.focustar.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class THDMember implements Serializable{
	
	private String memberId;
	private String cardNo;
	private String cellPhone;
	private String name;
	private Date   birthday;
	private String address;
	private String email;
	private Double credit;
	private Date updateTime;
	private Date createTime;
	
	
	//extends
	private String openId;
	
	
	private String birthdayStr;
	
	
	private String remark;
	
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getCellPhone() {
		return cellPhone;
	}
	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}
	public String getBirthdayStr() {
		if(birthday != null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			birthdayStr = sdf.format(birthday);
		}
		return birthdayStr;
	}
	public Double getCredit() {
		return credit;
	}
	public void setCredit(Double credit) {
		this.credit = credit;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	

}
