package com.focustar.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.focustar.util.BarCodeUtil;


public class BarCoderServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public BarCoderServlet() {
		super();
	}

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

		String barCode = request.getParameter("barCode");
		
		String strHeight = request.getParameter("height");
		String strWidth = request.getParameter("width");
		
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		if(barCode != null && !"".equals(barCode)){
			
			
			response.setContentType("image/png");
			
			OutputStream output = response.getOutputStream();
			
			try{
				if(strHeight != null && !"".equals(strHeight)
						&& strWidth != null && !"".equals(strWidth)){
					BarCodeUtil.makeBarCoder(barCode,Integer.parseInt(strHeight),Integer.parseInt(strWidth),output);
				}
				else{
					BarCodeUtil.makeBarCoder(barCode,output);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			output.flush();
			
			output.close();
			
		}else{
			
			
			response.setContentType("text/html");
			
			PrintWriter writer = response.getWriter();
			
			writer.write("缺少主要参数！ ");
			
			writer.flush();
			
			writer.close();
			
		}
		
	}

}
