package com.attilax.wechat;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.attilax.io.filex;
import com.attilax.net.urlUtil;

public class aaa {

	public static void main(String[] args) throws UnsupportedEncodingException {
		//http://m.hualip.com:8090/wechat/pay.jsp?orderno=0208_090812_098&fee=0.01&callback_url=http%3A%2F%2Fwww.baidu.com%2Fxx.htm&name=%E6%B5%8B%E8%AF%95%E5%95%86%E5%93%81
System.out.println(com.attilax.wechat.TokenThread.class);
		String call="http://www.baidu.com/xx.htm";
String url="http://m.hualip.com:8090/wechat/pay.jsp?orderno="+filex.getUUidName()+"&fee=0.01&callback_url="+URLEncoder.encode(call, "utf-8")+"&name="+URLEncoder.encode("测试商品", "utf-8");
System.out.println(url);
//HttpServletRequest request;
 	 HttpSession session;
 	 System.out.println(   urlUtil.urlEncode("name=名称&back=http://aaaa", "utf-8"));
 	
//		 session.setAttribute(arg0, arg1);
//		 
//		 request.getParameter("orderid");
//		 request.getParameter("fee");
//		 request.getParameter("name");
//		 request.getParameter("callbackurl");
//		 request.getQueryString()

	}

}
