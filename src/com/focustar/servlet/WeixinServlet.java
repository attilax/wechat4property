package com.focustar.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;

import com.attilax.core;
import com.attilax.io.pathx;
import com.focustar.IocX;
import com.focustar.dao.impl.NewsImpl;
import com.focustar.service.WeixinService;
import com.focustar.util.SignUtil;

/**
 * 核心请求处理类 WeixinServlet.java
 */
public class WeixinServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	protected static Logger logger = Logger.getLogger("WeixinServlet");

	/** jeig get haosyeo only 4 modify cfg d siheu .test .
	 * 确认请求来自微信服务器
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		core.log("--o66a");
		// 微信加密签名
		String signature = request.getParameter("signature");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		// 随机字符串
		String echostr = request.getParameter("echostr");

		PrintWriter out = response.getWriter();
		// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
		if (SignUtil.checkSignature(signature, timestamp, nonce)) {
			out.print(echostr);
		}
		out.close();
		out = null;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			core.log("--o66b");
			IocX.ini();
			// 将请求、响应的编码均设置为UTF-8（防止中文乱码）
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");

			Date beginDate = new Date();
			String respMessage = "";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			logger.info("------------------------------开始运行用户请求程序------------------------------");
			logger.info("请求开始时间：" + sdf.format(beginDate));

			// 调用WeixinService类的processRequest方法接收、处理消息，并得到处理结果
			// 调用核心业务类接收消息、处理消息
			
			
			try {
				respMessage = WeixinService.processRequest(request);
			} catch (DocumentException e) {
				e.printStackTrace();
				logger.info("ee", e);
			}

			Date endDate = new Date();
			logger.info("请求响应时间：" + sdf.format(endDate));
			long difftime = endDate.getTime() - beginDate.getTime();
			float second = new BigDecimal(difftime).divide(new BigDecimal("1000")).floatValue();
			logger.info("从请求到响应的系统处理时间[" + String.valueOf(second) + "]秒");
			logger.info("------------------------------结束运行用户请求程序------------------------------");

			// 响应消息 调用response.getWriter().write()方法将消息的处理结果返回给用户
			PrintWriter out = response.getWriter();
			out.print(respMessage);
			out.close();
		} catch (Exception e) {
			logger.error("eep28", e);
		}
		
	}

}
