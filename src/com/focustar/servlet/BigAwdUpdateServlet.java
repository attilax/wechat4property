package com.focustar.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.focustar.dao.impl.AwardImpl;
import com.focustar.entity.AwardWeixin;

public class BigAwdUpdateServlet extends HttpServlet {

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the GET method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		String id = request.getParameter("uid");
		String uname = request.getParameter("uname");
		String memcard = request.getParameter("memcard");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		
		AwardWeixin awd = new AwardWeixin();
		awd.setId(Integer.parseInt(id));
		awd.setAwardUserName(uname);
		awd.setMemcard(memcard);
		awd.setAwardPhone(phone);
		awd.setAwardAddress(address);
		AwardImpl awardDao = new AwardImpl();
		int result = awardDao.updateAwdweixin(awd);
		Map<String,String> map = new HashMap<String,String>();
		
		if(result == 1){
			map.put("result", "ok");
		}else{
			map.put("result", "no");
		}
		PrintWriter out = response.getWriter();
		String resStr = JSONObject.fromObject(map).toString();
		out.print(resStr);		
		out.flush();		
		out.close();
		
	}

}
