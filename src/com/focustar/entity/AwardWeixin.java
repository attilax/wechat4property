package com.focustar.entity;

import java.io.Serializable;
import java.util.Date;

public class AwardWeixin implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5627097323811101696L;
	private Integer 	id;
	private Activity  	activity;
	private ActAward 	award;
	private String 		openId; //中奖微信用户
	private Date    	awardTime;//中奖时间
	private String  	awardPhone; //中奖联系人手机号码
	private String  	awardUserName;// 中奖联系人姓名
	private String  	awardAddress; //中奖人地址
	private Integer 	awardCount;//抽奖次数    today can join times 
	private Date   		createTime; //创建时间
	//   下午12:07:01 2014-6-27  老哇的爪子  Attilax
	private String  	memcard; //中奖人地址
	private Integer  awardId;
	private String    awardName; //奖品名称
	private Integer  activityId;
	private String 	activityName;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	
	
	public ActAward getAward() {
		return award;
	}
	public void setAward(ActAward award) {
		this.award = award;
	}
	public Date getAwardTime() {
		return awardTime;
	}
	public void setAwardTime(Date awardTime) {
		this.awardTime = awardTime;
	}
	public Integer getAwardCount() {
		return awardCount;
	}
	public void setAwardCount(Integer awardCount) {
		this.awardCount = awardCount;
	}
	
	public Activity getActivity() {
		return activity;
	}
	public void setActivity(Activity activity) {
		this.activity = activity;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getAwardPhone() {
		return awardPhone;
	}
	public void setAwardPhone(String awardPhone) {
		this.awardPhone = awardPhone;
	}
	public String getAwardUserName() {
		return awardUserName;
	}
	public void setAwardUserName(String awardUserName) {
		this.awardUserName = awardUserName;
	}
	public String getAwardAddress() {
		return awardAddress;
	}
	public void setAwardAddress(String awardAddress) {
		this.awardAddress = awardAddress;
	}
	public String getAwardName() {
		return awardName;
	}
	public void setAwardName(String awardName) {
		this.awardName = awardName;
	}
	public Integer getActivityId() {
		return activityId;
	}
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}
	public Integer getAwardId() {
		return awardId;
	}
	public void setAwardId(Integer awardId) {
		this.awardId = awardId;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
		/**
	//  attilax 老哇的爪子 下午12:07:17   2014-6-27   
	 * @return the memcard
	 */
	public String getMemcard() {
		return memcard;
	}
		/**
	//  attilax 老哇的爪子 下午12:07:17   2014-6-27   
	 * @param memcard the memcard to set
	 */
	public void setMemcard(String memcard) {
		this.memcard = memcard;
	}
	
	
	

}
