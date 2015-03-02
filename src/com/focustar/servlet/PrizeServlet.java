package com.focustar.servlet;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.focustar.dao.impl.UserImpl;
import com.focustar.entity.TMbWeixinuser;
import com.focustar.thread.ActivityHistoryJober;
import com.focustar.util.Constant;
import com.focustar.util.MD5;
import com.focustar.util.MyCacher;
import com.focustar.util.MyHttpUtils;

public class PrizeServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5766182010644240331L;
	private final static Logger log = Logger.getLogger(ReadServlet.class);
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String code = req.getParameter("code");
		//通过授权打开页面
		if(code!=null && !"".equals(code)){
			
			StringBuilder authUrl = new StringBuilder();
			authUrl.append("https://api.weixin.qq.com/sns/oauth2/access_token?appid=").append(Constant.APPID)
				   .append("&secret=").append(Constant.APPSECRET).append("&code=").append(code).append("&grant_type=authorization_code");
			
			log.info(authUrl.toString());
			JSONObject json = MyHttpUtils.httpsRequest(authUrl.toString(), "GET", null);
			log.info("请求结果..." + json.toString());
			String openid = json.getString("openid");
			
			ActivityHistoryJober actJober = null;
			
			if(openid != null && !"".equals(openid)){
				
				TMbWeixinuser user = (TMbWeixinuser)MyCacher.getInstance().getCache(MD5.getMD5(openid));
				if(user == null){
					UserImpl userDao = new UserImpl();
					user = userDao.getUserByopenid(openid);
				}
				
				if(user != null && user.getMember() != null){
					
				}else{
					
				}
				
			}
			
			
		}else{
			
			//非通过授权打开
			
		}
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String flag = req.getParameter("action");
		
		//登录中奖信息
		if(flag != null && "register".equals(flag)){
			
			String formhash = req.getParameter("formhash");
			
			Set<String> formhashSession = (Set<String>) req.getSession().getAttribute("formhashSession");
			
			if(formhashSession != null && formhashSession.contains(formhash)){
				String name = req.getParameter("name");
				String cardNo = req.getParameter("cardNo");
				String phoneNo = req.getParameter("phoneNo");
				String address = req.getParameter("address");
				
				//更新中奖记录
				
				
				//成功后删除
				 formhashSession.remove(formhash);
		         req.getSession().setAttribute("formhashSession", formhashSession);
				
			}else{
				
				log.info("重复提交？");
				
			}
			
			
		}
		
	}
	

}
