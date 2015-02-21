package com.focustar.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import com.focustar.dao.impl.NewsImpl;
import com.focustar.dao.impl.UserImpl;
import com.focustar.entity.TMbNews;
import com.focustar.entity.TMbWeixinuser;
import com.focustar.service.hd.HDService;
import com.focustar.util.Constant;
import com.focustar.util.MD5;
import com.focustar.util.MyCacher;
import com.focustar.util.WeixinUtil;

public class CreditJober extends Jober {
	
	
	public CreditJober(String openId){
		this.openId = openId;
	}
	
	public CreditJober(String openId,String eventKey){
		this(openId);
		this.eventKey = eventKey;
	}

	@Override
	protected void startWork() {
		logger.info("查询积分...");
		md5UserKey = MD5.getMD5(openId);
		user = (TMbWeixinuser) MyCacher.getInstance().getCache(md5UserKey);
		if (user == null) {
			UserImpl userImpl = new UserImpl();
			user = userImpl.getUserByopenid(openId);
			if(user != null){
				MyCacher.getInstance().putCache(md5UserKey, user,Constant.TEN_MINUTES);
			}else{
				
					CountDownLatch downLatch = new CountDownLatch(1);
					//没有微信用户信息？马上下载
					DownloadUserJober uJober = new DownloadUserJober(openId,false);
					uJober.setCountDown(downLatch);
					try {
					     Constant.AsyncJober.execute(uJober);
						downLatch.await(60,TimeUnit.SECONDS);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					//再查一次
					user = userImpl.getUserByopenid(openId);
					if (user != null) {
						MyCacher.getInstance().putCache(md5UserKey, user,Constant.TEN_MINUTES);
					}
					
				
			}
		}
		
		if (user != null && user.getMember() == null) {
			logger.info("推送积分 图文");
			//推送积分兑换图文
			//ClientMsgJober msg = new ClientMsgJober(openId,com.focustar.entity.weixin.sendMessage.NewsMessage.NEWS_MEMBERCREDIT);
			//Constant.AsyncJober.execute(msg);
			
			
			
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
			
			
			EventHistoryJober history = new EventHistoryJober(openId,eventKey);
			
			Constant.CountJober.execute(history);
			
		} else if(user != null && user.getMember() != null){
			
			EventHistoryJober history = new EventHistoryJober(openId,user.getMember().getMemberId(),eventKey);
			
			Constant.CountJober.execute(history);
			
			logger.info("正在查询会员积分 ");
			//调用海鼎的接口获取
			Map<Object,Object> creditMap =  HDService.requestMemberCredit(user);
			if(creditMap != null){
				StringBuilder resBuilder = new StringBuilder();
				String myCredit = (String) Constant.MSG_MAP.get(Constant.INFO_QUERY_CREDIT);
				myCredit = myCredit.replaceAll(Constant.RP_CARD_NO, user.getMember().getCardNo());
				myCredit = myCredit.replaceAll(Constant.RP_CREDIT_TOTAL, creditMap.containsKey(Constant.RP_CREDIT_TOTAL)?creditMap.get(Constant.RP_CREDIT_TOTAL).toString():"");
				
				resBuilder.append(myCredit);
				//如果用户不属于任何一个分公司，这个积分兑换无法添加？
				if(user.getGroup() != null){
					//添加积分兑换连接
					NewsImpl newsDao = new NewsImpl();
					List<TMbNews> newsList = new ArrayList<TMbNews>();
					if(user.getGroup().getParent() != null){
						newsList = newsDao.getNewsList(com.focustar.entity.weixin.sendMessage.NewsMessage.NEWS_MEMBERGOOD,user.getGroup().getParent().getGroupid());
					}else{
						newsList = newsDao.getNewsList(com.focustar.entity.weixin.sendMessage.NewsMessage.NEWS_MEMBERGOOD,user.getGroup().getGroupid());
					}
					if(newsList != null && newsList.size() > 0){
						resBuilder.append("  ").append("查看本期【<a href='").append(Constant.NEWS_WEB_SITE).append(Constant.NEWS_HTML_PATH);
						TMbNews creditNews = newsList.get(0);
						resBuilder.append(creditNews.getHtmlName()).append("'>积分兑换产品</a>】");
					}
				}else{
					logger.info("无法直接确认用户所属分公司，查找用户所在【"+user.getProvince()+"】省   【"+user.getCity()+"】市");
					NewsImpl newsDao = new NewsImpl();
					List<TMbNews>  newsList = newsDao.getNewsList(com.focustar.entity.weixin.sendMessage.NewsMessage.NEWS_MEMBERGOOD, user.getProvince(),user.getCity());
					
					if(newsList != null && newsList.size() > 0){
						
						resBuilder.append("  ").append("查看本期【<a href='").append(Constant.NEWS_WEB_SITE).append(Constant.NEWS_HTML_PATH);
						TMbNews creditNews = newsList.get(0);
						resBuilder.append(creditNews.getHtmlName()).append("'>积分兑换产品</a>】");
						
					}else{
						logger.info("无法确定用户属于那个分公司，所以无法推送指定分公司的积分兑换产品图片！");
					}
					
				}
				logger.info(resBuilder.toString());
				msgObj = WeixinUtil.buildTextMessage(openId, resBuilder.toString());
			}
		}

	}

}
