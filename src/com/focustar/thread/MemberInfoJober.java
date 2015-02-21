package com.focustar.thread;

import com.focustar.dao.impl.UserImpl;
import com.focustar.entity.TMbWeixinuser;
import com.focustar.util.Constant;
import com.focustar.util.MD5;
import com.focustar.util.MyCacher;
import com.focustar.util.WeixinUtil;

public class MemberInfoJober extends Jober {
	
	public MemberInfoJober(String openId){
		this.openId = openId;
	}
	
	public MemberInfoJober(String openId,String eventKey){
		this(openId);
		this.eventKey = eventKey;
	}

	@Override
	protected void startWork() {
		
		logger.info("查询会员资料...");
		//指纹
		md5UserKey = MD5.getMD5(openId);
		user = (TMbWeixinuser) MyCacher.getInstance().getCache(md5UserKey);
		if (user == null) {
			UserImpl userDao = new UserImpl();
			user = userDao.getUserByopenid(openId);
			MyCacher.getInstance().putCache(md5UserKey, user,Constant.TEN_MINUTES);
		}
		if (user != null && user.getMember() != null) {// 判断是否绑定会员
			
			String tips = (String)Constant.MSG_MAP.get(Constant.MEMBER_OK_TIPS);
			
			EventHistoryJober history = new EventHistoryJober(openId,user.getMember().getMemberId(),eventKey);
			
			Constant.AsyncJober.execute(history);
			
			StringBuilder bUrl = new StringBuilder();
			bUrl.append("<a href='");
			bUrl.append(Constant.WEB_URL).append("weixin");
			bUrl.append("/memberServlet?view=view&openid=");
			bUrl.append(openId);
			bUrl.append("' >");
			bUrl.append("个人资料").append("</a>");
			
			tips = tips.replaceAll(Constant.RP_BIND_URL,bUrl.toString());

			msgObj = WeixinUtil.buildTextMessage(openId, tips);

		} else {
			
			
			EventHistoryJober history = new EventHistoryJober(openId,eventKey);
			
			Constant.AsyncJober.execute(history);
			
			String tips = (String)Constant.MSG_MAP.get(Constant.MEMBER_TIPS);
			
			StringBuilder bUrl = new StringBuilder();
			bUrl.append("<a href='");
			bUrl.append(Constant.WEB_URL).append("weixin");
			bUrl.append("/memberServlet?view=bind&openid=");
			bUrl.append(openId);
			bUrl.append("' >");
			bUrl.append("绑定页面").append("</a>");
			
			tips = tips.replaceAll(Constant.RP_BIND_URL,bUrl.toString());
			
			msgObj = WeixinUtil.buildTextMessage(openId,tips);
		}

	}

}
