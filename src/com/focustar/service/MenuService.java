package com.focustar.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.focustar.entity.weixin.message.response.Article;
import com.focustar.entity.weixin.message.response.NewsMessage;
import com.focustar.util.Constant;
import com.focustar.util.MessageUtil;

/**
 * 根据菜单ID返回相应的图文界面
 * 
 * @author 张春雨
 * @模块
 * @日期 2013-12-17 时间：上午10:51:41
 */
public class MenuService {

	private static Logger logger = Logger.getLogger(MenuService.class);

	/**
	 * 单图文界面显示，主要实现关键字自动回复功能，系统根据菜单ID读取相应的配置文件
	 * 
	 * @param fromUserName
	 *            用户openid
	 * @param toUserName
	 *            公众号openid
	 * @param content
	 *            要查询的关键字
	 * @param eventKey
	 *            菜单ID
	 * @return
	 */
	public String showOneNews(String fromUserName, String toUserName, String content, String eventKey, String searchKey) {
		String respMessage = "";
		String title = "";
		String description = "";
		String picUrl = "";
		String url = "";
		String filePath = Constant.path;
		String encoding = "UTF-8";

		if (!title.equals("")) {
			// 创建图文消息
			NewsMessage newsMessage = new NewsMessage();
			newsMessage.setToUserName(fromUserName);
			newsMessage.setFromUserName(toUserName);
			newsMessage.setCreateTime(new Date().getTime());
			newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
			newsMessage.setFuncFlag(0);

			List<Article> articleList = new ArrayList<Article>();
			// 单图文消息
			Article article = new Article();
			article.setTitle(title);
			article.setDescription(description);
			article.setPicUrl(picUrl);
			article.setUrl(url);
			articleList.add(article);

			// 设置图文消息个数
			newsMessage.setArticleCount(articleList.size());
			// 设置图文消息包含的图文集合
			newsMessage.setArticles(articleList);
			// 将图文消息对象转换成xml字符串
			respMessage = MessageUtil.newsMessageToXml(newsMessage);
			respMessage = respMessage.replace("Item", "item");
		}
		return respMessage;
	}

	/**
	 * 多图文界面显示，系统根据菜单ID读取相应的配置文件
	 * 
	 * @param fromUserName
	 *            用户openid
	 * @param toUserName
	 *            公众号openid
	 * @param eventKey
	 *            菜单ID
	 * @return
	 */
	public String showManyNews(String fromUserName, String toUserName, String eventKey) {
		String respMessage = "";
		String title = "";
		String description = "";
		String picUrl = "";
		String url = "";
		String filePath = Constant.path;
		String encoding = "UTF-8";
		String lineTxt = null;
		int xh = 0;

		// 根据菜单ID加载不同的菜单图文配置文件
		if (eventKey.equals("12")) {
			// 三包与配件
			filePath = filePath + "three.txt";
		} else if (eventKey.equals("13")) {
			// 手机常见问题
			filePath = filePath + "problem.txt";
		} else if (eventKey.equals("22")) {
			// 小编推荐
			filePath = filePath + "recommend.txt";
		} else if (eventKey.equals("23")) {
			// 特色功能
			filePath = filePath + "features.txt";
		} else if (eventKey.equals("24")) {
			// 手机名词解释
			filePath = filePath + "mobileName.txt";
		} else if (eventKey.equals("32")) {
			// 售后风采
			filePath = filePath + "serviceAfter.txt";
		} else if (eventKey.equals("33")) {
			// 平台服务手册
			filePath = filePath + "notebook.txt";
		}

		// 创建图文消息
		NewsMessage newsMessage = new NewsMessage();
		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
		newsMessage.setFuncFlag(0);
		List<Article> articleList = new ArrayList<Article>();

		// 读取网点配置文件
		try {
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);

				while ((lineTxt = bufferedReader.readLine()) != null) {
					xh++;
					lineTxt = lineTxt.trim();
					if (lineTxt.indexOf("title") > -1) {
						title = lineTxt.substring(6, lineTxt.length());
					} else if (lineTxt.indexOf("description") > -1) {
						description = lineTxt.substring(12, lineTxt.length());
					} else if (lineTxt.indexOf("picUrl") > -1) {
						picUrl = lineTxt.substring(7, lineTxt.length());
					} else if (lineTxt.indexOf("url") > -1) {
						url = lineTxt.substring(4, lineTxt.length());
					}
					if (xh == 4) {
						Article article = new Article();
						article.setTitle(title);
						article.setDescription(description);
						article.setPicUrl(picUrl);
						article.setUrl(url);
						articleList.add(article);
						title = "";
						description = "";
						picUrl = "";
						url = "";
						xh = 0;
					}
				}
				read.close();
			} else {
				logger.info("找不到指定的文件");
			}
		} catch (Exception e) {
			logger.info("读取文件内容出错");
			e.printStackTrace();
		}
		// 设置图文消息个数
		newsMessage.setArticleCount(articleList.size());
		// 设置图文消息包含的图文集合
		newsMessage.setArticles(articleList);
		// 将图文消息对象转换成xml字符串
		respMessage = MessageUtil.newsMessageToXml(newsMessage);
		respMessage = respMessage.replace("Item", "item");
		return respMessage;
	}
}
