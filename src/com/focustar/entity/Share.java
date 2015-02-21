package com.focustar.entity;

import java.io.Serializable;
import java.util.Date;

public class Share implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7558526337911160347L;
	
	/**
	 * @category 每天
	 */
	public final static int  ACT_JOIN_EVERYDAY = 1;
	
	/**
	 * @category 每人
	 */
	public final static int ACT_JOIN_EVERYBODY = 2;
	
	private Integer 	pid;
	private String 		openid;	//活动名称
	private String 		actTitle; //活动标题
	private String 		actImg; //活动图片
	/**
	 * @return the pid
	 */
	public Integer getPid() {
		return pid;
	}
	/**
	 * @param pid the pid to set
	 */
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	/**
	 * @return the openid
	 */
	public String getOpenid() {
		return openid;
	}
	/**
	 * @param openid the openid to set
	 */
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	private Date   		beginTime;//开始时间
	private Date   		endTime;  //结束时间
	private String 		remark;   //活动说明
	private String 		actUrl;  //抽奖地址
	private String   	ruleRemark; //抽奖规则文字说明
	private TMbNews  	ruleNews; //规则的图文
	
	/**
	 * @category 抽奖次数
	 */
	private Integer     	joinCount = 1;//参与次数
	
	/***
	 * @category 抽奖形式  1 每天，2每人，3每分享
	 */
	private Integer  		joinType = 0; 
	
	/**
	 * @category 该抽奖活动的状态  状态 0 未启用 1 启用，一般情况，同一时间只能有一个活动在执行
	 */
	private Integer     	flag = 0;
	
	/**
	 * @category 是否赠送积分
	 */
	private Integer         isCredit = 0;
	
	/**
	 * @category 积分
	 */
	private Integer         credit = 0;
	
	/**
	 * @category 是否只限会员参与
	 */
	private Integer         isOnlyMember = 0; 
	
	/**
	 * @category 是否需要分享赠送
	 */
	private Integer         isShare = 0;
	
	
	public Integer getId() {
		return pid;
	}
	public void setId(Integer id) {
		this.pid = id;
	}
	public String getActName() {
		return openid;
	}
	public void setActName(String actName) {
		this.openid = actName;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getActUrl() {
		return actUrl;
	}
	public void setActUrl(String actUrl) {
		this.actUrl = actUrl;
	}
	public String getActTitle() {
		return actTitle;
	}
	public void setActTitle(String actTitle) {
		this.actTitle = actTitle;
	}
	public String getActImg() {
		return actImg;
	}
	public void setActImg(String actImg) {
		this.actImg = actImg;
	}
	public String getRuleRemark() {
		return ruleRemark;
	}
	public void setRuleRemark(String ruleRemark) {
		this.ruleRemark = ruleRemark;
	}
	public TMbNews getRuleNews() {
		return ruleNews;
	}
	public void setRuleNews(TMbNews ruleNews) {
		this.ruleNews = ruleNews;
	}
	public Integer getJoinCount() {
		return joinCount;
	}
	public void setJoinCount(Integer joinCount) {
		this.joinCount = joinCount;
	}
	public Integer getJoinType() {
		return joinType;
	}
	public void setJoinType(Integer joinType) {
		this.joinType = joinType;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public Integer getIsCredit() {
		return isCredit;
	}
	public void setIsCredit(Integer isCredit) {
		this.isCredit = isCredit;
	}
	public Integer getCredit() {
		return credit;
	}
	public void setCredit(Integer credit) {
		this.credit = credit;
	}
	public Integer getIsOnlyMember() {
		return isOnlyMember;
	}
	public void setIsOnlyMember(Integer isOnlyMember) {
		this.isOnlyMember = isOnlyMember;
	}
	public Integer getIsShare() {
		return isShare;
	}
	public void setIsShare(Integer isShare) {
		this.isShare = isShare;
	}
	
	
	
	
	 
	
	
	
}
