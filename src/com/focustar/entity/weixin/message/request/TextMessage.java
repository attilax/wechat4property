package com.focustar.entity.weixin.message.request;

/**
 * 文本消息 TextMessage.java author:vincente 2013-11-1
 */
public class TextMessage extends BaseMessage {
	// 消息内容
	private String content;
	// 以下是业务对象，不属于微信接口返回的对象
	private String imei;// 手机IMEI编号
	private long buyDate;// 购买日期,来源于顾客录入
	private long returnDate;// 会员注册日期，来源于信息部接口返回
	private long dateTime;// 销售通和易用汇返回的最早日期

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public long getBuyDate() {
		return buyDate;
	}

	public void setBuyDate(long buyDate) {
		this.buyDate = buyDate;
	}

	public long getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(long returnDate) {
		this.returnDate = returnDate;
	}

	public long getDateTime() {
		return dateTime;
	}

	public void setDateTime(long dateTime) {
		this.dateTime = dateTime;
	}
}
