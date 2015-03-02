package com.focustar.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.focustar.dao.impl.NewsImpl;
import com.focustar.dao.impl.UserImpl;
import com.focustar.entity.TMbNews;
import com.focustar.entity.TMbNewsHistory;
import com.focustar.entity.TMbWeixinuser;
import com.focustar.thread.NewsHistoryJober;
import com.focustar.util.Constant;
import com.focustar.util.MD5;
import com.focustar.util.MyCacher;
import com.focustar.util.MyHttpUtils;

public class ReadServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6613319181574113579L;
	private final static Logger log = Logger.getLogger(ReadServlet.class);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		log.info("直接在微信打开，记录图文的阅读次数...");
		String code = req.getParameter("code");
		if(code != null && !"".equals(code)){
			String newsIdStr = req.getParameter("newsId");
			
			if(newsIdStr != null && !"".equals(newsIdStr)){
			int newsId = Integer.parseInt(newsIdStr);
			NewsImpl newsDao = new NewsImpl();
			TMbNews news = newsDao.getNewsById(newsId);
			if(news != null){
					//构建获取openid的url
					StringBuilder authUrl = new StringBuilder();
					authUrl.append("https://api.weixin.qq.com/sns/oauth2/access_token?appid=").append(Constant.APPID)
						   .append("&secret=").append(Constant.APPSECRET).append("&code=").append(code).append("&grant_type=authorization_code");
					log.info(authUrl.toString());
					JSONObject json = MyHttpUtils.httpsRequest(authUrl.toString(), "GET", null);
					log.info("请求结果..." + json.toString());
					String openid = json.getString("openid");
					NewsHistoryJober historyJober = null;
					if(openid != null && !"".equals(openid)){
					
						TMbWeixinuser user = (TMbWeixinuser)MyCacher.getInstance().getCache(MD5.getMD5(openid));
						if(user == null){
							UserImpl userDao = new UserImpl();
							user = userDao.getUserByopenid(openid);
						}
						
						if(user != null && user.getMember() != null){
							historyJober = new NewsHistoryJober(user,news.getNewsType(),TMbNewsHistory.NEWS_OPER_READ,newsId);
						}else{
							historyJober = new NewsHistoryJober(openid,news.getNewsType(),TMbNewsHistory.NEWS_OPER_READ,newsId);
						}
						
					}else{
						//有人阅读，但不知道openid ???
						historyJober = new NewsHistoryJober(news.getNewsType(),TMbNewsHistory.NEWS_OPER_READ,newsId);
					}
					
					if(historyJober != null){
						Constant.CountJober.execute(historyJober);
					}
					
					req.setCharacterEncoding("UTF-8");
					resp.setCharacterEncoding("UTF-8");
					resp.setContentType("text/html");
					resp.setHeader("Pragma", "no-cache");
					resp.setHeader("Cache-Control","no-cache");
					resp.setDateHeader("Expires", 0);
					
					StringBuilder newsPath = new StringBuilder();
					newsPath.append(Constant.NEWS_WEB_SITE).append(Constant.NEWS_HTML_PATH).append(news.getHtmlName());
					
					log.info(newsPath.toString());
					
					
					resp.sendRedirect(newsPath.toString());
				
			}
			}else{
				
				log.info("newId=="+newsIdStr);
			}
			
			
			
		}else{
			
			log.info("无法取得页面授权！");
			
		}
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		log.info("ajax提交，记录图文的阅读次数...");
		
		//图文类型
		String newsTypeStr = req.getParameter("newsType");
		//图文编号
		String newsIdStr = req.getParameter("newsId");
		
		if(newsIdStr != null &&!"".equals(newsIdStr) && newsTypeStr != null && !"".equals(newsTypeStr)){
			
			log.info("newsId="+newsIdStr+"   newsType="+newsTypeStr);
			
			int newsId = Integer.parseInt(newsIdStr);
			int newsType = Integer.parseInt(newsTypeStr);
			
			NewsHistoryJober newsHistory = new NewsHistoryJober(newsType,TMbNewsHistory.NEWS_OPER_READ,newsId);
			
			if(newsHistory != null){
				Constant.CountJober.execute(newsHistory);
			}
		
		}else{
			log.info("newsId="+newsIdStr+"   newsType="+newsTypeStr);
		}
		
	}

}
