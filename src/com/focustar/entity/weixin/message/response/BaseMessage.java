package com.focustar.entity.weixin.message.response;

/**
 * 消息基类（公众帐号 -> 普通用户） BaseMessage.java author:vincente 2013-11-1
 */
public class BaseMessage {
	// 接收方帐号,OpenID
	private String toUserName;
	// 发送方帐号,OpenID
	private String fromUserName;
	// 消息创建时间 （整型）
	private long createTime;
	// 消息类型（text/music/news）
	private String msgType;
	// 位0x0001被标志时，星标刚收到的消息
	private int funcFlag;
	// 消息id，64位整型
	private String msgId;

	public String getToUserName() {
		return toUserName;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public long getCreateTime() {
		return createTime;
	}

	public String getMsgType() {
		return msgType;
	}

	public int getFuncFlag() {
		return funcFlag;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public void setFuncFlag(int funcFlag) {
		this.funcFlag = funcFlag;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

}
