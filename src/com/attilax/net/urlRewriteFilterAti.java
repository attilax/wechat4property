/**
 * @author attilax 老哇的爪子
	@since  2014-5-28 下午03:44:18$
 */
package com.attilax.net;

//com.attilax.net.urlRewriteFilterAti

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.attilax.core;
//import com.attilax.secury.aes;
 

/**
 * @author  attilax 老哇的爪子
 *@since  2014-5-28 下午03:44:18$
 */
public class urlRewriteFilterAti implements Filter {

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 * @author  attilax 老哇的爪子
	 *@since  2014-5-28 下午03:47:55$
	 */
	@Override
	public void destroy() {
		// attilax 老哇的爪子  下午03:47:55   2014-5-28 
		
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 * @author  attilax 老哇的爪子
	 *@since  2014-5-28 下午03:47:55$
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// attilax 老哇的爪子  下午03:47:55   2014-5-28 
		
		if(!new File("C:\\rwtStart").exists())
		{
			core.log("--rwtStop");
			 chain.doFilter(request, response);
			 return;
		}
		
		   //把ServletRequest和ServletResponse转换成真正的类型
        HttpServletRequest req = (HttpServletRequest)request;
        HttpSession session = req.getSession();
        core.logurl((HttpServletRequest) request);

        //由于web.xml中设置Filter过滤全部请求，可以排除不需要过滤的url
        String requestURI = req.getRequestURI();
        if(requestURI.endsWith("spr.jsp")){
         //  resume to access    chain.doFilter(request, response);
        	core.log("-- endsWith spr.jsp stop the chainfileter");
        	RequestDispatcher requestDispatcher = request.getRequestDispatcher("spr.jsp");
        	requestDispatcher.forward(request, response);//这两句怎么解释啊？
        	//   ((HttpServletResponse)response).flushBuffer()
            return;
        }
		    //except    
		 if(requestURI.contains("spr/txtrwt"))
		 {
			 chain.doFilter(request, response);
			 return;
		 }
		
		 
		 String requestURI_rltWebapp=urlUtil.getReqUri_rltWebapp(requestURI,req);
		 String url_noHost = requestURI_rltWebapp+(req.getQueryString()!=null?"?"+req.getQueryString():"");
		String p6l ="sB8ipKHy5iFBtTfbtsTRm0g8n8D68oqOdZyLl6LgFpk=";//  
	//	p6l=aes.decrypt(p6l, "aeskey");
		
		String url=p6l + url_noHost;
		 url= URLEncoder.encode(url);
		 String url_noHost2=URLEncoder.encode(url_noHost);
	//	 url="aa";
		 if(url_noHost.startsWith("/http://"))
		 {
			String url5=url_noHost.substring(1, url_noHost.length());
			String url5_encode= URLEncoder.encode(url5);
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/spr/imgrwt_abspath?url="+url5_encode+"&urlNoHost="+url_noHost2);
	        	requestDispatcher.forward(request, response);//
	        	return;
		 }
		 
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/spr/txtrwt?url="+url+"&urlNoHost="+url_noHost2);
        	requestDispatcher.forward(request, response);//
 
		
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 * @author  attilax 老哇的爪子
	 *@since  2014-5-28 下午03:47:55$
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// attilax 老哇的爪子  下午03:47:55   2014-5-28 
		
	}
	//  attilax 老哇的爪子 下午03:44:24   2014-5-28   
	
}

//  attilax 老哇的爪子