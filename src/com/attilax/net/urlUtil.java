package com.attilax.net;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.tencent.common.Configure;
import com.tencent.common.MD5;

public class urlUtil {
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		String url="http://localhost/img/QQ%BD%D8%CD%BC20140401175140.jpg?a=1&b=2";
		 Map m=	urlUtil.Param2Map(url);
		 System.out.println(m);
		//QQ%E6%88%AA%E5%9B%BE20140401175140.jpg
		String s="http://localhost/img/QQ%E9%8E%B4%EE%81%84%E6%B5%9820140401175433.jpg";
		int start=s.indexOf("%");
		int end=s.lastIndexOf("%")+2;
		String sub=s.substring(start,end+1);
		System.out.println(sub);
		//http://localhost/img/QQ%E6%88%AA%E5%9B%BE20140401175140.jpg
		////http://localhost/img/QQ%BD%D8%CD%BC20140401175140.jpg
		//%E9%8E%B4%EE%81%84%E6%B5%98
		try {
			String 编码=java.net.URLEncoder.encode("测试","UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String 解码=java.net.URLDecoder.decode("%E6%88%AA%E5%9B%BE","utf-8");
		//%E6%88%AA%E5%9B%BE===截图
		System.out.println( 解码);
		System.out.println(URLEncoder.encode("截图","gbk"));

	}
//	http://localhost/img/QQ%E9%8E%B4%EE%81%84%E6%B5%9820140401175433.jpg
	/**
	 * 解析出url请求的路径，包括页面
	 * 
	 * @param strURL
	 *            url地址
	 * @return url路径
	 */
	public static String UrlPage(String strURL) {
		String strPage = null;
		String[] arrSplit = null;
		strURL = strURL.trim().toLowerCase();
		arrSplit = strURL.split("[?]");
		if (strURL.length() > 0) {
			if (arrSplit.length > 1) {
				if (arrSplit[0] != null) {
					strPage = arrSplit[0];
				}
			}
		}
		return strPage;
	}

	/**
	 * 去掉url中的路径，留下请求参数部分
	 * 
	 * @param strURL
	 *            url地址
	 * @return url请求参数部分
	 */
	private static String TruncateUrlPage(String strURL) {
		String strAllParam = null;
		String[] arrSplit = null;
		strURL = strURL.trim().toLowerCase();
		arrSplit = strURL.split("[?]");
		if (strURL.length() > 1) {
			if (arrSplit.length > 1) {
				if (arrSplit[1] != null) {
					strAllParam = arrSplit[1];
				}
			}else
				return arrSplit[0];
		}
		return strAllParam;
	}

	/**
	 * 解析出url参数中的键值对 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
	 * 
	 * @param URL
	 *            url地址
	 * @return url请求参数部分
	 */
	public static Map<String, Object> Param2Map(String URL) {
		Map<String, Object> mapRequest = new HashMap<String, Object>();
		String[] arrSplit = null;
		String strUrlParam = TruncateUrlPage(URL);
		if (strUrlParam == null) {
			return mapRequest;
		}
		// 每个键值为一组
		arrSplit = strUrlParam.split("[&]");
		for (String strSplit : arrSplit) {
			String[] arrSplitEqual = null;
			arrSplitEqual = strSplit.split("[=]");
			// 解析出键值
			if (arrSplitEqual.length > 1) {
				// 正确解析
				mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);
			} else {
				if (arrSplitEqual[0] != "") {
					// 只有参数没有值，不加入
					mapRequest.put(arrSplitEqual[0], "");
				}
			}
		}
		return mapRequest;
	}
	
	
	
//	public static Map<String, Object> Param2Map(String URL) {
//		Map<String, Object> mapRequest = new HashMap<String, Object>();
//		String[] arrSplit = null;
//		String strUrlParam = TruncateUrlPage(URL);
//		if (strUrlParam == null) {
//			return mapRequest;
//		}
//		// 每个键值为一组
//		arrSplit = strUrlParam.split("[&]");
//		for (String strSplit : arrSplit) {
//			String[] arrSplitEqual = null;
//			arrSplitEqual = strSplit.split("[=]");
//			// 解析出键值
//			if (arrSplitEqual.length > 1) {
//				// 正确解析
//				mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);
//			} else {
//				if (arrSplitEqual[0] != "") {
//					// 只有参数没有值，不加入
//					mapRequest.put(arrSplitEqual[0], "");
//				}
//			}
//		}
//		return mapRequest;
//	}
	public static String urlEncode(String urlParams,String keyname,String enc)
	{
		Map m=Param2Map(urlParams);
		String val=(String) m.get(keyname);
		try {
			String  val_enc=URLEncoder.encode(val, enc);
			m.put(keyname, val_enc);
		} catch (UnsupportedEncodingException e) {
		 
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		String urlparams=Map2urlparams(m);
		return urlparams;
	}
	public static String urlEncode(String urlParams)
	{
		return urlEncode(urlParams,"utf-8");
	}
	
	public static String urlEncode(String urlParams ,String enc)
	{
		Map<String,Object> m=Param2Map(urlParams);
		  for(Map.Entry<String,Object> entry:m.entrySet())
		  {
				String val=(String) entry.getValue();
				try {
					String  val_enc=URLEncoder.encode(val, enc);
					m.put(entry.getKey(), val_enc);
				} catch (UnsupportedEncodingException e) {
				 
					e.printStackTrace();
					throw new RuntimeException(e);
				}
		  }
	
		String urlparams=Map2urlparams(m);
		urlparams=urlparams.substring(0,  urlparams.length()-1);
		return urlparams;
	}
	
	
    public static String Map2urlparams(Map<String,Object> map){
        ArrayList<String> list = new ArrayList<String>();
        for(Map.Entry<String,Object> entry:map.entrySet()){
            if(entry.getValue()!=""){
                list.add(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        int size = list.size();
        String [] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < size; i ++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
//        result += "key=" + Configure.getKey();
//        //Util.log("Sign Before MD5:" + result);
//        result = MD5.MD5Encode(result).toUpperCase();
        //Util.log("Sign Result:" + result);
        return result;
    }

	public static Map Param2Map(HttpServletRequest req) {
		String querystr="http://tst/ka.jpg?"+req.getQueryString();
		return Param2Map(querystr);
	}
	
	public static String getUrl(HttpServletRequest req) {
		String querystr=req.getRequestURL()+ "?"+req.getQueryString();
		return  querystr;
	}
	//req.getQueryString()
	public static String decodeByUtf8(String jpgPart) {
		 
		try {
			return java.net.URLDecoder.decode(jpgPart,"utf-8");
		} catch (UnsupportedEncodingException e) {
			 
			e.printStackTrace();
		}
		return jpgPart;
	}
	/**
	@author attilax 老哇的爪子
	\t@since  Aug 23, 2014 7:06:16 PM$
	
	 * @param requestURI
	 * @param req 
	 * @return
	 */
	public static String getReqUri_rltWebapp(String requestURI, HttpServletRequest req) {
		// attilax 老哇的爪子  7:06:16 PM   Aug 23, 2014 
		
		{  String appdir=req.getContextPath();  //  fmt::  /app
		
		return requestURI.replace(appdir+"/", "/");
		 } 
		
		
	}
	

}