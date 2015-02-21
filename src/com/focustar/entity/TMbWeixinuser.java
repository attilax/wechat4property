package com.focustar.entity;

import java.util.Date;

/**
 * TMbWeixinuser entity. @author MyEclipse Persistence Tools
 */

public class TMbWeixinuser implements java.io.Serializable {
	
	
	public final static int SIGNED = 1;
	
	public final static int UNSIGNE = 0;

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -4250635319859007036L;
	private Integer userId;
	private Integer subscribe;
	private String openid;
	private Integer sex;
	private String city;
	private String country;
	private String province;
	private String language;
	private String headimgurl;
	private Date subscribeTime;
	private String nickname;
	private Integer insertFlag;

	private Integer fromtype = 0;
	// Constructors
	private TMbGroup group;
	
	//private String memberId;//绑定会员编号
	
	private THDMember member;
	private Date bindDate;
	private Integer isSign = 0;//是否已签到
	
	private Date    signDate;//签到日期
	private Date    createTime;
	
	private Date    updateTime;
	
	/** default constructor */
	public TMbWeixinuser() {
	}

	/** minimal constructor */
	public TMbWeixinuser(String nickname) {
		this.nickname = nickname;
	}

	/** full constructor */
	public TMbWeixinuser(Integer subscribe, String openid, Integer sex, String city, String country, String province, String language,
			String headimgurl, Date subscribeTime, String nickname, Integer insertFlag) {
		this.subscribe = subscribe;
		this.openid = openid;
		this.sex = sex;
		this.city = city;
		this.country = country;
		this.province = province;
		this.language = language;
		this.headimgurl = headimgurl;
		this.subscribeTime = subscribeTime;
		this.nickname = nickname;
		this.insertFlag = insertFlag;
	}
	
	
	

	// Property accessors

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getSubscribe() {
		return this.subscribe;
	}

	public void setSubscribe(Integer subscribe) {
		this.subscribe = subscribe;
	}

	public String getOpenid() {
		return this.openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public Integer getSex() {
		return this.sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getLanguage() {
		return this.language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getHeadimgurl() {
		return this.headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public Date getSubscribeTime() {
		return this.subscribeTime;
	}

	public void setSubscribeTime(Date subscribeTime) {
		this.subscribeTime = subscribeTime;
	}

	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getInsertFlag() {
		return this.insertFlag;
	}

	public void setInsertFlag(Integer insertFlag) {
		this.insertFlag = insertFlag;
	}

	

	public Integer getFromtype() {
		return fromtype;
	}

	public void setFromtype(Integer fromtype) {
		this.fromtype = fromtype;
	}


	public TMbGroup getGroup() {
		return group;
	}

	public void setGroup(TMbGroup group) {
		this.group = group;
	}

	
	
	public THDMember getMember() {
		return member;
	}

	public void setMember(THDMember member) {
		this.member = member;
	}

	public Integer getIsSign() {
		return isSign;
	}

	public void setIsSign(Integer isSign) {
		this.isSign = isSign;
	}

	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	public Date getBindDate() {
		return bindDate;
	}

	public void setBindDate(Date bindDate) {
		this.bindDate = bindDate;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
	

}