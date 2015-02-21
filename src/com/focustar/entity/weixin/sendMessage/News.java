package com.focustar.entity.weixin.sendMessage;

import java.util.List;

/** 
 * com.focustar.entity.weixin.sendMessage
 * News.java 
 * author:vincente  2013-11-18 
 */
public class News {
	
	private List<Article> articles;
	
	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}
}
