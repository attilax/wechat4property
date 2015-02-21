package com.focustar.thread;

import java.util.List;

import net.sf.json.JSONObject;

import com.focustar.dao.impl.ActivityImpl;
import com.focustar.dao.impl.NewsImpl;
import com.focustar.dao.impl.UserImpl;
import com.focustar.entity.Activity;
import com.focustar.entity.TMbGroup;
import com.focustar.entity.TMbNews;
import com.focustar.entity.TMbWeixinuser;
import com.focustar.util.ConfigService;
import com.focustar.util.Constant;
import com.focustar.util.WeixinUtil;

public class FocusJober extends Jober {
	
	public FocusJober(String openid){
		this.openId = openid;
	}

	@Override
	protected void startWork() {
		
		//检查是否有相关图文
		NewsImpl newsDao = new NewsImpl();
		
		UserImpl userDao = new UserImpl();
		
		user = userDao.getUserByopenid(openId);
		
		makeResponse();
		
		if(user != null){
		
			TMbGroup curGroup = user.getGroup() != null && user.getGroup().getParent()!=null?user.getGroup().getParent():user.getGroup();
			
			if(curGroup == null){
				logger.info("该用户["+this.openId+"]没有绑定分公司,根据省["+user.getProvince()+"]与城市["+user.getCity()+"]查找");
				curGroup = NewsImpl.checkProvinceAndCity(user.getProvince(),user.getCity());
			}else{
				logger.info("当前用户["+this.openId+"]所在分公司  >>> " + curGroup.getGroupid()+" >>>> " + curGroup.getGroupname());
			}
			
			
			if(curGroup != null){
			
					List<TMbNews> newsList = newsDao.getNewsList(com.focustar.entity.weixin.sendMessage.NewsMessage.NEWS_FOCUS,curGroup.getGroupid());
			
					if(newsList != null && newsList.size() > 0){
						logger.info("有关注相关图文");
						msgObj = WeixinUtil.buildNewsMessage(openId, newsList);
					}else{
						logger.info("无关注相关图文");
						//makeResponse();
					}
			
			}else{
				logger.info("无法确定用户["+this.openId+"]的所在分公司");
				//makeResponse();
			}
		
		}else{
			logger.info("找不到该用户资料");
		}
		
	}
	
	private void makeResponse(){
		ConfigService config = new ConfigService();
		Object focusmsgObj = null;
		if(user.getInsertFlag() != null && user.getInsertFlag().intValue() == 0){
		
				String focusTxt = config.getMsgProperty("FOCUS_TIPS");
		
				if(focusTxt != null && !"".equals(focusTxt)){
				
					
					if(focusTxt.indexOf("MEMBER_CLICK_HREF") != -1){
						StringBuilder hrefCont = new StringBuilder();
						String memberKeyword = config.getMsgProperty("MEMBER_KEYWORD") != null?config.getMsgProperty("MEMBER_KEYWORD"):"会员绑定";
						hrefCont.append("<a href='");
						StringBuilder requrl = new StringBuilder();
						requrl.append(Constant.WEB_URL).append("weixin/").append("memberServlet?view=bind&openid=").append(openId);
						hrefCont.append(requrl.toString()).append("'>").append(memberKeyword).append("</a>");
						focusTxt = focusTxt.replaceAll("MEMBER_CLICK_HREF",hrefCont.toString());
					}
				
					if(focusTxt.indexOf("ACTIVITY_CLICK_HREF") != -1){
						StringBuilder hrefCont = new StringBuilder();
						String activityKeyword = config.getMsgProperty("ACTIVITY_KEYWORD")!=null?config.getMsgProperty("ACTIVITY_KEYWORD"):"抽奖";
						hrefCont.append("<a href='");
						StringBuilder acturl = new StringBuilder();
						acturl.append("https://open.weixin.qq.com/connect/oauth2/authorize?appid=").append(Constant.APPID);
						acturl.append("&redirect_uri=").append(Constant.WEB_URL).append("weixin/")
							  .append("mobile/card.jsp?actid=1").append("&response_type=code&scope=snsapi_base&state=1#wechat_redirect");
						hrefCont.append(acturl.toString()).append("'>").append(activityKeyword).append("</a>");
						
						focusTxt = focusTxt.replaceAll("ACTIVITY_CLICK_HREF",hrefCont.toString());
					}
					
					logger.info("关注时推送  >>> " + focusTxt);
					focusmsgObj = WeixinUtil.buildTextMessage(openId, focusTxt);
				
				}else{
				
					focusmsgObj = WeixinUtil.buildTextMessage(openId, "感谢您的关注！");
				
				}
		
		}else{
			
			String secondFocusTxt = config.getMsgProperty("SECOND_FOCUS_TIPS");
			
			if(secondFocusTxt != null && !"".equals(secondFocusTxt)){
				focusmsgObj = WeixinUtil.buildTextMessage(openId, secondFocusTxt);
			}else{
				focusmsgObj = WeixinUtil.buildTextMessage(openId, "感谢您的关注！");
			}
			
		}
		
		if(focusmsgObj != null){
			String jsonData = JSONObject.fromObject(focusmsgObj).toString();
			WeixinUtil.sendMessage(jsonData,Constant.token.getToken());
		}
	}

}
