package com.focustar.entity;

import java.util.Date;

/**
 * TMbMessage entity. @author MyEclipse Persistence Tools
 */

public class TMbMessage implements java.io.Serializable {

	public final static int MSGTYPE_TEXT = 0; //文本
	public final static int MSGTYPE_IMG = 1;  //图片
	public final static int MSGTYPE_VEDIO = 2; //视频
	public final static int MSGTYPE_VOICE = 3;  //声音
	
	public final static int MSG_CLIENT = 1; //客服消息
	public final static int MSG_CENTER = 2; //座席回复
	public final static int MSG_EVENT = 3; //事件点击
	public final static int MSG_AUTO = 4; //系统回复
	public final static int MSG_USER_REPLY = 5;//自助查询
	
	
	public final static int  MSG_STATUS_NEW = 1;  //新数据
	public final static int MSG_STATUS_QUEUE = 2; //排除中
	public final static int MSG_STATUS_SEND = 3;  //发送中
	public final static int MSG_STATUS_OK = 4;    //推送成功
	public final static int MSG_STATUS_FAIL = 5;  //推送失败
	
	// Fields

	private static final long serialVersionUID = -6516597437651869356L;
	private Integer id;
	private String openId;
	private String nickName;
	private Date createTime;
	private String msgType;
	private String content;
	private String msgId;
	private Integer sessionType;// 会话类型 1客户 2坐席 3 自动回复
	private Integer push;// 1已推送 0未推送
	private String agentId = "0";// 0未分配
	private String agentName;
	private String answerTime;
	private String sessionId;
	
	private String ifRead = "F";
	
	private String ifEnd = "F";

	// Constructors

	/** default constructor */
	public TMbMessage() {
	}

	/** full constructor */
	public TMbMessage(String openId, String nickName, Date createTime, String msgType, String content, String msgId, String agentId,
			String agentName, String answerTime) {
		this.openId = openId;
		this.nickName = nickName;
		this.createTime = createTime;
		this.msgType = msgType;
		this.content = content;
		this.msgId = msgId;
		this.agentId = agentId;
		this.agentName = agentName;
		this.answerTime = answerTime;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOpenId() {
		return this.openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getNickName() {
		return this.nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getMsgType() {
		return this.msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMsgId() {
		return this.msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getAgentId() {
		return this.agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getAgentName() {
		return this.agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getAnswerTime() {
		return this.answerTime;
	}

	public void setAnswerTime(String answerTime) {
		this.answerTime = answerTime;
	}

	public Integer getPush() {
		return push;
	}

	public void setPush(Integer push) {
		this.push = push;
	}

	public Integer getSessionType() {
		return sessionType;
	}

	public void setSessionType(Integer sessionType) {
		this.sessionType = sessionType;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getIfRead() {
		return ifRead;
	}

	public void setIfRead(String ifRead) {
		this.ifRead = ifRead;
	}

	public String getIfEnd() {
		return ifEnd;
	}

	public void setIfEnd(String ifEnd) {
		this.ifEnd = ifEnd;
	}
	
	
	
	

}