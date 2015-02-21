package com.focustar.entity;

import java.io.Serializable;
import java.util.Date;

public class ActAward implements Serializable{
	
	private Integer  id;
	private Integer  activityId; //所属活动
	private String   awardName;	//奖品名称
	private Integer  awardCount;//奖品数量
	private Integer  rate;		//中将比率
	private Integer  type;      //奖品类型
	private String   remark;	//奖品说明
	private Integer  orderBy;	//排序，也作奖品的等级
	private Date     createTime; //创建时间
	
	private String  awardImg; //奖品图片
	
	private String awardImgEx1;
	private String awardImgEx2;
	private String awardImgEx3;
	private String awardImgEx4;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getActivityId() {
		return activityId;
	}
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}
	public String getAwardName() {
		return awardName;
	}
	public void setAwardName(String awardName) {
		this.awardName = awardName;
	}
	public Integer getAwardCount() {
		return awardCount;
	}
	public void setAwardCount(Integer awardCount) {
		this.awardCount = awardCount;
	}
	public Integer getRate() {
		return rate;
	}
	public void setRate(Integer rate) {
		this.rate = rate;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(Integer orderBy) {
		this.orderBy = orderBy;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getAwardImg() {
		return awardImg;
	}
	public void setAwardImg(String awardImg) {
		this.awardImg = awardImg;
	}
	
	
	
}
