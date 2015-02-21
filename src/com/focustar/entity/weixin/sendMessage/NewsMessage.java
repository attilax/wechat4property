package com.focustar.entity.weixin.sendMessage;

import java.util.List;

/** 
 * 发送图文消息
 * com.focustar.entity.weixin.sendMessage
 * NewsMessage.java 
 * author:vincente  2013-11-18 
 */
public class NewsMessage extends BaseMessage{
	
	public final static int NEWS_NEWSALE = 1;//最新促销
	public final static int NEWS_HOTSALE = 2;//top热卖
	public final static int NEWS_NEWGOOD = 3;//新品上市
	
	public final static int NEWS_MEMBERDAY = 4; //会员日
	public final static int NEWS_SCANGIFT = 5;//扫码有礼
	public final static int NEWS_WXPRICE = 6; //微信价
	
	public final static int NEWS_MEMBERCREDIT = 7;//会员积分
	
	public final static int NEWS_MEMBERGOOD = 8;//会员积分兑换
	
	public final static int NEWS_SHOP_BEST = 9;//门店精选
	
	public final static int NEWS_SUPERFUN = 10;//超级fun
	
	public final static int NEWS_FOCUS = 11;//关注时推送的图文
	
	public final static int NEWS_RULE = 12; // 抽奖的规则图文
	
	public final static int NEWS_KEYWORD = 13;//关键字类型图文
	
	public final static int NEWS_ALL  = 99;//群发消息
	
	private transient int newsType = 0;
	
	private News news;

	public News getNews() {
		return news;
	}

	public void setNews(News news) {
		this.news = news;
	}

	public int getNewsType() {
		return newsType;
	}

	public void setNewsType(int newsType) {
		this.newsType = newsType;
	}
	
	
}
