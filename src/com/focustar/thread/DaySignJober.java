package com.focustar.thread;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.focustar.dao.impl.UserImpl;
import com.focustar.entity.TMbWeixinuser;
import com.focustar.util.Constant;
import com.focustar.util.MD5;
import com.focustar.util.MyCacher;
import com.focustar.util.WeixinUtil;

public class DaySignJober extends Jober {
	
	public DaySignJober(String openId,String eventKey){
		this.openId = openId;
		this.eventKey = eventKey;
	}

	@Override
	protected void startWork() {
		logger.info("门店签到...");
		//指纹
		md5UserKey = MD5.getMD5(openId);
		//获取会员绑定的会员号
		user = (TMbWeixinuser) MyCacher.getInstance().getCache(md5UserKey);
		if (user == null){
			UserImpl userImpl = new UserImpl();
			user = userImpl.getUserByopenid(openId);
			if(user != null){
			//缓存30分钟
				MyCacher.getInstance().putCache(md5UserKey, user, Constant.TEN_MINUTES);
			}
		}
		
		if(user != null){
			// 检查会员id是否绑定
			if(user.getMember() == null || "".equals(user.getMember().getMemberId())){ //没有绑定会员，推送积分介绍图文
				logger.info("该微信用户没有绑定会员,文字提示！");
				String signTips = (String)Constant.MSG_MAP.get(Constant.INFO_SIGN_BUT_NOT_MEMBER);
				StringBuilder bindUrl = new StringBuilder();
				bindUrl.append("<a href='");
				bindUrl.append(Constant.WEB_URL).append("weixin");
				bindUrl.append("/memberServlet?view=bind&openid=");
				bindUrl.append(openId);
				bindUrl.append("' >");
				bindUrl.append("绑定页面").append("</a>");
				
				signTips = signTips.replaceAll(Constant.RP_BIND_URL, bindUrl.toString());
				msgObj = WeixinUtil.buildTextMessage(openId, signTips);
				
				if(this.eventKey != null){
					EventHistoryJober eventHistory = new EventHistoryJober(openId,eventKey);
					Constant.CountJober.execute(eventHistory);
				}
				
			}else{
				//已绑定会员
				logger.info("该微信用户已绑定会员..");
				
				if(this.eventKey != null){
					EventHistoryJober eventHistory = new EventHistoryJober(openId,user.getMember().getMemberId(),eventKey);
					Constant.CountJober.execute(eventHistory);
				}
				
				if(user.getIsSign() == null || user.getIsSign() == TMbWeixinuser.UNSIGNE){
					//执行签到
					String signTips = (String)Constant.MSG_MAP.get(Constant.INFO_SIGN_LOCATION);
					logger.info("signTips >>> "+signTips);
					msgObj = WeixinUtil.buildTextMessage(openId,signTips);
				}else{
					//提示已签到
					logger.info("该会员已签到 ");
					String tipsMsg = (String)Constant.MSG_MAP.get(Constant.INFO_SIGN_TIPS);
					Date signDate = user.getSignDate();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					
					tipsMsg = tipsMsg.replaceAll(Constant.RP_SIGN_DATE,sdf.format(signDate));
					msgObj = WeixinUtil.buildTextMessage(openId,tipsMsg);
				}
			}
		}
	}

}
