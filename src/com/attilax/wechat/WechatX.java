package com.attilax.wechat;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.attilax.core;
import com.attilax.io.filex;
import com.attilax.io.pathx;
import com.attilax.net.websitex;
import com.attilax.util.tryX;
import com.focustar.entity.TMbNews;
import com.focustar.entity.weixin.pojo.AccessToken;
import com.focustar.entity.weixin.sendMessage.NewsMessage;
import com.focustar.util.ConfigService;
//import com.kunpeng.www.config.DBXMLTool;
import com.focustar.util.Constant;

public class WechatX {

	ConfigService ConfigServiceObj=new ConfigService();
	public static void main(String[] args) {
	System.out.println("aa");
		HttpServletRequest request;
	//	new WechatX().getOpenid2(request);

	}
	
	public String getOpenid2(HttpServletRequest request)
	{
		String appid=ConfigServiceObj.getWxProperty("APPID");
		String APPSECRET=ConfigServiceObj.getWxProperty("APPSECRET");;
		return getOpenid(request, appid, APPSECRET);
		
	}
	
	public String getOpenid(HttpServletRequest request,String defaul4test) {
		String oid=getOpenid(request);
		if(oid==null)
			return defaul4test;
		return oid;
		
	}
	public String getOpenid(HttpServletRequest request) {

	HttpSession sess = request.getSession();

	// request.getSession().getAttribute("openid_ss");
	// 下午05:46:29 2014-4-28
	String code = request.getParameter("code");// 我们要的code
	core.log("--o428 code:" + code);
	String appid = new ConfigService().getWxProperty("APPID");
	String APPSECRET = new ConfigService().getWxProperty("APPSECRET");

	String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
			+ appid + "&secret=" + APPSECRET + "&code=" + code
			+ "&grant_type=authorization_code";
	core.log("--o428 url:" + url);
	String ret = websitex.WebpageContent(url, "utf-8");
	core.log("--o428 ret:" + ret);
	final net.sf.json.JSONObject jo = net.sf.json.JSONObject
			.fromObject(ret);
	String oid = new tryX<String>() {

		@Override
		public String item(Object t) throws Exception {
			// 下午03:29:01 2014-4-29

			return jo.getString("openid");

		}

	}.$(null);

	if (oid == null) {
		core.log("-- oid is null from url");
		if (sess.getAttribute("openid_ss") != null) {
			String oiddx = (String) sess.getAttribute("openid_ss");
			core.log("--o428_14has sess:" + oiddx);
			return oiddx;
		} else
			core.log("--get oid frm sess yash null");
	}
	core.log("--openid:" + oid);
	 

	// save sess
	sess.setAttribute("openid_ss", oid);
	return oid;
	
}

	
	public String getOpenid(HttpServletRequest request,String appid,String APPSECRET ) {

		HttpSession sess = request.getSession();

		// request.getSession().getAttribute("openid_ss");
		// 下午05:46:29 2014-4-28
		String code = request.getParameter("code");// 我们要的code
		if(code==null)
			throw new RuntimeException( "rq param 'code' is null");
		core.log("--o428 code:" + code);
//		String appid = new ConfigService().getWxProperty("APPID");
//		String APPSECRET = new ConfigService().getWxProperty("APPSECRET");

		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
				+ appid + "&secret=" + APPSECRET + "&code=" + code
				+ "&grant_type=authorization_code";
		core.log("--o428 url:" + url);
		String ret = websitex.WebpageContent(url, "utf-8");
		core.log("--o428 ret:" + ret);
		final net.sf.json.JSONObject jo = net.sf.json.JSONObject
				.fromObject(ret);
		String oid = new tryX<String>() {

			@Override
			public String item(Object t) throws Exception {
				// 下午03:29:01 2014-4-29

				return jo.getString("openid");

			}

		}.$(null);

		if (oid == null) {
			core.log("-- oid is null from url");
			if (sess.getAttribute("openid_ss") != null) {
				String oiddx = (String) sess.getAttribute("openid_ss");
				core.log("--o428_14has sess:" + oiddx);
				return oiddx;
			} else
			{
				core.log("--get oid frm sess yash null");
				throw new RuntimeException("no get openid");
			}
		}
		core.log("--openid:" + oid);
		 

		// save sess
		sess.setAttribute("openid_ss", oid);
		return oid;
		
	}

		/**
		@author attilax 老哇的爪子
		@since   p29 g_54_o
		 
		 */
	public void setTokenGlobal(String string) {
		AccessToken at=new AccessToken();
		at.setToken(string);
		Constant.token=at;
		
	}
	
	
	 
	public   NewsMessage buildPicTxtMessage(String openId,List<TMbNews> newsList)
	{
		List<TMbNews> newsList = new ArrayList<TMbNews>();
		TMbNews news=new TMbNews();
		news.setTitle("title");
		news.setDescription("descccc");
		news.set
		return null;
		
	}
	

	public   String  sendPicTxtMessage(String openId,List<TMbNews> newsList)
	{
		return null;
		
	}
	
	
	
}
