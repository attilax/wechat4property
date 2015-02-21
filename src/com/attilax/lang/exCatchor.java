/**
 * 
 */
package com.attilax.lang;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import org.josql.QueryParseException;
import org.jsoup.parser.ParseError;
import com.attilax.core;
import com.attilax.dotnet.System.Web.HttpContext;
import com.attilax.io.filex;
import com.attilax.text.strUtil;
import com.attilax.util.god;
import com.focustar.publish.entity.GvPublish;

//import com.ajun.exception.BsException;
/**
 * 
 */
/** 业务异常过滤器
 * @author ajun
 * @http://blog.csdn.net/ajun_studio */
//WebFilter(filterName = "exFilter", urlPatterns = "/*") 
public class exCatchor implements Filter {
 
	private String errorPage = "/log" + "in.jsp";// 跳转的错误信息页面
 
	public void destroy() {

	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

		HttpContext.Current.set(new HttpContext());
		HttpContext.Request.set(req);
		core.log("--loadorderO9::exFilter");
		
		GvPublish pb = new GvPublish() {
			{
				this.setMome("aa");
			};
		};

		// String extName=filex.getExtName(requestURL);
		// String excpt="png,jpg,gif,css,js";
		// if(excpt.contains(extName))
		//
		// //String[] a=excpt.split(",");
		// strUtil.is
		HttpServletResponse response = (HttpServletResponse) res;
		HttpServletRequest request = (HttpServletRequest) req;
		// 捕获你抛出的业务异常
		String requestURL = request.getRequestURL().toString();
		if(requestURL.contains("dwr/call/plaincall"))
			System.out.println("dbg");
	core.log("--urlO9t:"+requestURL);
		try {
			chain.doFilter(req, res);
		} catch (Exception e) {
			// if(e instanceof BsException){//如果是你定义的业务异常
			boolean exists = new File("C:\\exCatStop").exists();
			if (exists) 				 
			{
				request.getSession().setAttribute("errO9", core.getTrace(e));
				core.log("--eoa1a...");
				core.warn(e);
				filex.saveLog(e, "c:\\e","gbk");
				throw new RuntimeException("--eOa1:"+e.getMessage(), e);
			}
			System.out.println(requestURL);
			request.setAttribute("BsException", e);// 存储业务异常信息类

			request.getRequestDispatcher(errorPage).forward(request, response);
			// 跳转到信息提示页面！！
			// }
			// e.printStackTrace();
		}
		System.out.println(requestURL);
	}
	/**
	 * 
	 */
	// 初始化读取你配置的提示页面路径
	public void init(FilterConfig config) throws ServletException {
		// 读取错误信息提示页面路径
		// errorPage = config.getInitParameter("errorPage");
		// if(null==errorPage || "".equals(errorPage)){
		// throw new
		// RuntimeException("没有配置错误信息跳转页面,请再web.xml中进行配置\n<init-param>\n<param-name>errorPage</param-name>\n<param-value>/error.jsp</param-value>\n </init-param>\n路径可以是你自己设定的任何有效路径页面！！");
		// //System.out.println("没有配置错误信息跳转页面");
		// }
	}

}
