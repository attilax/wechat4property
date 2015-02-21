package com.focustar.thread;

import java.util.ArrayList;
import java.util.List;

import com.focustar.dao.impl.NewsImpl;
import com.focustar.entity.TMbGroup;
import com.focustar.entity.TMbNews;
import com.focustar.entity.TMbWeixinuser;
import com.focustar.util.Constant;
import com.focustar.util.MD5;
import com.focustar.util.MyCacher;
import com.focustar.util.WeixinUtil;

public class ClientMsgJober extends Jober {
	
	public ClientMsgJober(String openId,int newsType){
		this.openId = openId;
		this.newsType = newsType;
	}
	
	public ClientMsgJober(String openId,int newsType,String eventKey){
		this(openId,newsType);
		this.eventKey = eventKey;
	}
	

	@Override
	protected void startWork() {
		logger.info("发送图文消息...");
		switch(this.newsType){
		case com.focustar.entity.weixin.sendMessage.NewsMessage.NEWS_NEWSALE://最新促销
		case com.focustar.entity.weixin.sendMessage.NewsMessage.NEWS_HOTSALE://热卖
		case com.focustar.entity.weixin.sendMessage.NewsMessage.NEWS_NEWGOOD://新货上市
		case com.focustar.entity.weixin.sendMessage.NewsMessage.NEWS_WXPRICE://微信价
		case com.focustar.entity.weixin.sendMessage.NewsMessage.NEWS_SCANGIFT://扫码有礼
		case com.focustar.entity.weixin.sendMessage.NewsMessage.NEWS_SUPERFUN://超级fun //客户需求变化
			newsWithGroupId();
			break;
		case com.focustar.entity.weixin.sendMessage.NewsMessage.NEWS_MEMBERDAY://会员日
		case com.focustar.entity.weixin.sendMessage.NewsMessage.NEWS_MEMBERCREDIT://会员积分
		case com.focustar.entity.weixin.sendMessage.NewsMessage.NEWS_ALL:	//群发
		//case com.focustar.entity.weixin.sendMessage.NewsMessage.NEWS_FOCUS:	//关注图文
			newsNoGroupId();
			break;
		}

	}
	
	private void newsNoGroupId(){
		
		if(eventKey != null){
			
			md5UserKey = MD5.getMD5(openId);
			
			user = (TMbWeixinuser) MyCacher.getInstance().getCache(md5UserKey);
			
			if(user == null){
				downloadUser();
			}
			
			if(user != null && user.getMember() != null){
				EventHistoryJober history = new EventHistoryJober(openId,user.getMember().getMemberId(),eventKey);
				Constant.CountJober.execute(history);
			}else{
				EventHistoryJober history = new EventHistoryJober(openId,eventKey);
				Constant.CountJober.execute(history);
			}
		}
		
		NewsImpl newsDao = new NewsImpl();
		List<TMbNews> newsList = newsDao.getNewsList(newsType,0);
		if(newsList != null && newsList.size() > 0){
			msgObj = WeixinUtil.buildNewsMessage(openId, newsList);
		}		
	}
	
	private void newsWithGroupId(){
		
		logger.info("处理图文请求..."+newsType);
		//指纹
		md5UserKey = MD5.getMD5(openId);
		//查询缓存
		user = (TMbWeixinuser) MyCacher.getInstance().getCache(md5UserKey);
		if (user == null) {
			downloadUser();
		}
		
		if(user != null && user.getMember() != null){
			EventHistoryJober history = new EventHistoryJober(openId,user.getMember().getMemberId(),eventKey);
			Constant.CountJober.execute(history);
		}else{
			EventHistoryJober history = new EventHistoryJober(openId,eventKey);
			Constant.CountJober.execute(history);
		}
		

		//键值
		StringBuilder rawKey = new StringBuilder("");
		if (user != null && user.getGroup() != null && user.getGroup().getParent() != null) {
			rawKey.append(newsType);
			rawKey.append("_").append(user.getGroup().getParent().getGroupid());
		}else if(user != null && user.getGroup() != null && user.getGroup().getParent() == null){ 
			rawKey.append(newsType);
			rawKey.append("_").append(user.getGroup().getGroupid());
		}
		
		List<TMbNews> newsList = null;
		String md5Key = "";
		if(!"".equals(rawKey.toString())){
			//指纹
			md5Key = MD5.getMD5(rawKey.toString());
			//查询缓存
			newsList = (List<TMbNews>) MyCacher.getInstance().getCache(md5Key);
		}
		
		if(newsList != null) {
			logger.info("从缓存查询返回...");
			this.newsId = newsList.get(0).getId();
			msgObj = WeixinUtil.buildNewsMessage(openId, newsList);
		} else {
			logger.info("从数据库查询返回...");
			if (user != null) {
				// 获取用户所属的分公司
				NewsImpl newsImpl = new NewsImpl();
				newsList = new ArrayList<TMbNews>();
				if (user.getGroup() != null
						&& user.getGroup().getParent() != null) {// 获取用户所属的分公司
					newsList = newsImpl.getNewsList(newsType, user.getGroup().getParent().getGroupid());
				}else if(user.getGroup() != null && user.getGroup().getParent() == null){
					newsList = newsImpl.getNewsList(newsType, user.getGroup().getGroupid());
				}
				else {
					logger.info("当前用户所在的省 "+user.getProvince()+"  市 "+user.getCity());
					newsList = newsImpl.getNewsList(newsType,user.getProvince(), user.getCity());
				}
				if (newsList != null && newsList.size() > 0) {
					
					this.newsId = newsList.get(0).getId();
					
					msgObj = WeixinUtil.buildNewsMessage(openId, newsList);
					// 缓存图文
					if(!"".equals(md5Key)){
						MyCacher.getInstance().putCache(md5Key, newsList,Constant.A_WEEK_SECONDS);
					}
				} else {
					//如果是最新促销，没有找到相关的默认附送广州的
					logger.info("无法确定用户["+this.openId+"]位置，当前国家["+user.getCountry()+"],省["+user.getProvince()+"]市["+user.getCity()+"]  默认推送 广州分公司");
					if(com.focustar.entity.weixin.sendMessage.NewsMessage.NEWS_NEWSALE == newsType){
						newsList = newsImpl.getNewsList(newsType,1);
						if(newsList != null && newsList.size() > 0){
							//msgObj = WeixinUtil.buildTextMessage(openId,"亲，最近暂无相关的促销消息！");
							msgObj = WeixinUtil.buildNewsMessage(openId, newsList);
						}
					}else{
						logger.info("该 " + newsType+ " 无相关图文！");
					}
					
				}
			}
		}
		
	}

}
