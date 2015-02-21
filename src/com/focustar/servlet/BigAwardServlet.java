package com.focustar.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.focustar.dao.impl.AwardImpl;
import com.focustar.entity.AwardWeixin;

public class BigAwardServlet extends HttpServlet {

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
		System.out.println("GET>>>>>>>>>>>>>>>>");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		String openid = request.getParameter("openid");
		String actid = request.getParameter("actid");
		AwardImpl awardDao = new AwardImpl();
		AwardWeixin result =  awardDao.getMyActAward(openid,actid);
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
		request.setAttribute("uid",result.getId());
		request.setAttribute("awardName",result.getAwardName());
		String time = result.getAwardTime()==null ? "":formatDate.format(result.getAwardTime());
		request.setAttribute("awardTime", time);
		request.setAttribute("name", result.getAwardUserName());
		request.setAttribute("phone",result.getAwardPhone());
		request.setAttribute("memcard", result.getMemcard());
		request.setAttribute("address", result.getAwardAddress());
		request.getRequestDispatcher("mobile/bigAwd.jsp").forward(request, response);
		//System.out.println(">>>>>>>>>"+result.getAward().getAwardName());
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
		System.out.println("Post>>>>>>>>>>>>>>>>");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		String openid = request.getParameter("openid");
		String actid = request.getParameter("actid");
		AwardImpl awardDao = new AwardImpl();
		AwardWeixin result =  awardDao.getMyActAward(openid,actid);
		Map<String,String> map = new HashMap<String,String>();		
		if(result != null){
			if(result.getAwardName()==null){
				map.put("result", "no");
			}else{
				map.put("result", result.getAwardName());
			}
			
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
