package com.focustar.thread;

import java.util.Date;

import org.apache.log4j.Logger;

import com.focustar.dao.impl.HistoryImpl;
import com.focustar.entity.TMbNewsHistory;
import com.focustar.entity.TMbWeixinuser;

public class NewsHistoryJober implements Runnable{
	
	private final static Logger log = Logger.getLogger(NewsHistoryJober.class);
	
	private int groupId;
	private String openId;
	private int newsType = 0;
	private int newsId = 0;
	private int operType = 0;
	private TMbWeixinuser user = null;
	private String memberId = null;
	
	public NewsHistoryJober(int newsType,int operType,int newsId){
		this.newsType = newsType;
		this.operType = operType;
		this.newsId = newsId;
	}
	
	public NewsHistoryJober(String openId,int newsType,int operType){
		this.openId = openId;
		this.newsType = newsType;
		this.operType = operType;
	}
	
	public NewsHistoryJober(String openId,int newsType,int operType,int newsId){
		this(openId,newsType,operType);
		this.newsId = newsId;
	}
	
	public NewsHistoryJober(String openId,String memberId,int newsType,int operType,int newsId){
		this(openId,newsType,operType);
		this.newsId = newsId;
		this.memberId = memberId;
	}
	
	public NewsHistoryJober(TMbWeixinuser user,int newsType,int operType){
		this.user = user;
		this.newsType = newsType;
		this.operType = operType;
	}
	
	public NewsHistoryJober(TMbWeixinuser user,int newsType,int operType,int newsId){
		this(user,newsType,operType);
		this.newsId = newsId;
	}

	public void run() {
		
		TMbNewsHistory news = null;
		
		if(TMbNewsHistory.NEWS_OPER_READ == operType){ //阅读
			news =  new TMbNewsHistory();
			if(user != null){
				news.setOpenid(user.getOpenid());
				if(user.getMember() != null){
					news.setMemberId(user.getMember().getMemberId());
				}
			}else{
				if(openId != null){
					news.setOpenid(openId);
					if(memberId != null){
						news.setMemberId(memberId);
					}
				}
			}
			news.setNewsId(newsId);
			news.setNewsType(newsType);
			news.setOperTime(new Date());
			news.setOperType(operType);
			
		}else if(TMbNewsHistory.NEWS_OPER_SEND == operType){//送达
			
			news =  new TMbNewsHistory();
			if(user != null){
				news.setOpenid(user.getOpenid());
				if(user.getMember() != null){
					news.setMemberId(user.getMember().getMemberId());
				}
			}else{
				news.setOpenid(openId);
				if(memberId != null){
					news.setMemberId(memberId);
				}
			}
			news.setNewsId(newsId);
			news.setNewsType(newsType);
			news.setOperType(operType);
			news.setOperTime(new Date());
		}else{
			log.info("操作类型   >>> " + operType);
		}
		
		
		if(news != null){
			news.setGroupId(groupId);
			HistoryImpl historyDao = new HistoryImpl();
			historyDao.addNewsHistory(news);
		}
		
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	
	
	

}
