package com.focustar.entity;

import java.io.Serializable;
import java.util.Date;

public class THDMemberCostHistory implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6194185899739015552L;
	private int id;
	private String memberId;//会员号
	private String shopName;//店铺名称
	private int operType;  //消费类型
	private double credit; //积分
	private Date costTime; //消费时间
	
	private Integer stateCode; // 0 成功 其它值失败
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public double getCredit() {
		return credit;
	}
	public void setCredit(double credit) {
		this.credit = credit;
	}
	public Date getCostTime() {
		return costTime;
	}
	public void setCostTime(Date costTime) {
		this.costTime = costTime;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public Integer getStateCode() {
		return stateCode;
	}
	public void setStateCode(Integer stateCode) {
		this.stateCode = stateCode;
	}
	
	
	

}
