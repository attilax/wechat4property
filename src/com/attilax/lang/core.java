package com.attilax.lang;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Arrays;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.regex.Pattern;

import javax.persistence.Id;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.StringEscapeUtils;
 
import org.hibernate.Session;
 
 

import com.attilax.MDA.CantFindConverterExcept;
import com.attilax.anno.Conditional;
import com.attilax.anno.Converter;
import com.attilax.collection.CollectionUtils;
import com.attilax.collection.listUtil;
import com.attilax.io.CopyFile;
import com.attilax.io.filex;
import com.attilax.ref.cantFindMatchFieldException;
import com.attilax.ref.refx;
import com.attilax.text.strUtil;
import com.attilax.time.Handler;
import com.attilax.util.god;
import com.attilax.util.tryX;
import com.attilax.util.urlUtil;
 

public class core {
	
	public static  Map obj2map(final Object o) {
		Map m = new BeanMap(o);
		return m;
	}
	public static   String basename_noext(String filepath) {
		   File tempFile =new File( filepath.trim());  
		//   tempFile.
	        String fileName = tempFile.getName();  
	        int lastDotPostion=fileName.lastIndexOf(".");
	        
		return fileName.substring(0,lastDotPostion);
	}


	// public static Object logger;
	public static Logger logger = Logger.getLogger("corex");

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String fName =" G:\\Java_Source\\navigation_tigra_menu\\demo1\\img\\lev1_arrow.gif ";  
	System.out.println(basename_noext(fName));
	System.out.println("");
	}
	
	
	public static <T> List<T>  list(T... a) {
		// TODO Auto-generated method stub
		return Arrays.asList(a);
	}
 

	public static void sleep(int millSecs) {
		try {
			Thread.sleep(millSecs);
		} catch (InterruptedException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	public static void sleep_safe(int millSecs) {
		try {
			Thread.sleep(millSecs);
		} catch (InterruptedException e) {

			e.printStackTrace();
			//throw new RuntimeException(e);
		}

	}
	
	public static void openPort(final int port) {
		god.newThread(new Runnable() {

			public void run() {
				server( port);

			}
		}, "open port thrd");

	}

	// 服务端Socket
	public static void server(int port) {
		try {
			ServerSocket ss = new ServerSocket(port);
			// accept接受连接请求,接受到请求返回一个Socket套接字
			Socket s = ss.accept();
			OutputStream os = s.getOutputStream(); // 创建socket输出流
			InputStream is = s.getInputStream(); // 创建socket输入流
			os.write("The information comes from server!".getBytes());// 将字符串转为字节
			byte[] buf = new byte[100];

			while (true) {
				int len = is.read(buf);
				System.out.println(new String(buf, 0, len)); // 将字节数组转为字符串
				core.sleep(1000);
			}
			// os.close();
			// is.close();
			// s.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * {"remark":["aa"],"joincount":["1"],"endTime":["2014-06-17"],"awd":["a"],"regur":["a"],"cash":["a"],"beginTime":["2014-06-11"],"type":["1"],"actName":["a"]} 
	@author attilax 老哇的爪子
		@since  2014-6-25 下午12:37:05$
	
	 * @param request
	 * @param obj
	 * @return
	 */
	public static <atitype> atitype request2obj(HttpServletRequest request,
			final atitype obj) {
		// todox Apache的BeanUtils的使用入门 enhance effice
		Map parameterMap = request.getParameterMap();
		
		
		//  下午12:09:41 2014-6-25 老哇的爪子 Attilax
		final Map parameterMap2=parameterMap;
	 new com.attilax.tryX<String>() {

		@Override
		 
		public String $$(Object t) throws Exception {
			// attilax 老哇的爪子  下午12:08:10   2014-6-25 
			core.log("---o625 show req map ");
			core.log( core.obj2jsonO5(parameterMap2) );
			return null;
		 
			
		}
	}.$("");
		
		final Map mp = listUtil.caseIngor(parameterMap);
		// TProbeInvite o = new TProbeInvite();
		return new tryX<atitype>() {

			@Override
			public atitype item(Object t) throws Exception {
				// attilax 老哇的爪子 下午02:47:29 2014-5-28
				BeanUtils.copyProperties(obj, mp);
				// BeanUtils.populate(formBean, formMap); 
				return obj;
			}
		}.$(obj);

	}
	/**
	 * 
	@author attilax 老哇的爪子
		@since  o7e g5156$
	
	 * @param reqMap
	 * @param obj
	 * @return
	 */@Deprecated
	public static <atitype> atitype Map2obj(Map reqMap, final atitype obj) {
		// todox Apache的BeanUtils的使用入门 enhance effice
		Map parameterMap = reqMap;

		// 下午12:09:41 2014-6-25 老哇的爪子 Attilax
		final Map parameterMap2 = parameterMap;
		new com.attilax.tryX<String>() {

			@Override
			public String $$(Object t) throws Exception {
				// attilax 老哇的爪子 下午12:08:10 2014-6-25
				core.log("---o625 show req map ");
				core.log(core.obj2jsonO5(parameterMap2));
				return null;

			}
		}.$("");

		// final Map mp = listUtil.caseIngor(parameterMap);
		final Map mp = parameterMap;
		// TProbeInvite o = new TProbeInvite();
		return new tryX<atitype>() {

			@Override
			public atitype item(Object t) throws Exception {
				// attilax 老哇的爪子 下午02:47:29 2014-5-28
			//	BeanUtils.
		 	BeanUtils.copyProperties(obj, mp);
			//	BeanUtils.populate(obj, mp);
				// BeanUtils.populate(formBean, formMap);
				return obj;
			}
		}.$(obj);

	}
	
	/**
	 * 
	@author attilax 老哇的爪子
	\t@since  Jul 19, 2014 12:13:14 AM$
	
	 * @param reqMap
	 * @param obj
	 * @return
	 */
	public static <atitype> atitype Map2obj_safe(final Map reqMap, final atitype obj) {
		 
		// todox Apache的BeanUtils的使用入门 enhance effice
		 
		logMap4dbg(reqMap);

		// final Map mp = listUtil.caseIngor(parameterMap);
	 
		// TProbeInvite o = new TProbeInvite();
		CollectionUtils.each_safe_o7(reqMap, new Closure() {
			
			@Override
			public Object execute(Object arg0) throws Exception {
				// attilax 老哇的爪子  12:17:06 AM   Jul 19, 2014 
				
				{Object[] a=(Object[]) arg0;
				String key=(String) a[0];
				BeanUtils.copyProperty(obj, key, reqMap.get(key));
					return null;
				}
				
			}
		});
		return obj;
//		 

	}
	
	public static <atitype> atitype Map2obj_safeO7(final Object reqMap, final atitype obj) {
		 
		// todox Apache的BeanUtils的使用入门 enhance effice
		 
		final Map mp = (Map)reqMap;
		logMap4dbg(mp);

		// final Map mp = listUtil.caseIngor(parameterMap);
	 
		// TProbeInvite o = new TProbeInvite();
		CollectionUtils.each_safe_o7(mp, new Closure() {
			
			@Override
			public Object execute(Object arg0) throws Exception {
				// attilax 老哇的爪子  12:17:06 AM   Jul 19, 2014 
				
				{Object[] a=(Object[]) arg0;
				String key=(String) a[0];
				BeanUtils.copyProperty(obj, key, mp.get(key));
					return null;
				}
				
			}
		});
		return obj;
//		 

	}
	
	public static void setProperty(Object obj,String properName,Object val)  {
		try {
			//BeanUtils.copyProperty(obj, properName, val);
		Field fld=	obj.getClass().getDeclaredField(properName);
 fld.setAccessible(true);
		fld.set(obj, val);
		//	BeanUtils.setProperty
		} catch (IllegalAccessException e) {
			//  attilax 老哇的爪子 8:23:56 PM   Jul 19, 2014   
			core.warn(e);
		} catch (SecurityException e) {
			//  attilax 老哇的爪子 11:15:33 PM   Jul 19, 2014   
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			//  attilax 老哇的爪子 11:15:33 PM   Jul 19, 2014   
			e.printStackTrace();
		}  
	}
	
	/**
	@author attilax 老哇的爪子
	\t@since  Jul 19, 2014 8:24:35 PM$
	
	 * @param e
	 */
	public static void warn( Exception e) {
		// attilax 老哇的爪子  8:24:35 PM   Jul 19, 2014 
		
		{ 
			String emltxt=god.getTrace(e);
			StackTraceElement ste=refx.preMethod(3);
				
				core.logger.warn("---"+emltxt+" ["+ste.getClassName()+"."+ste.getMethodName()+"():"+ste.getLineNumber()+"]");

		 } 
		
		
	}
	private static void logMap4dbg(final Map reqMap) {
		new com.attilax.tryX<String>() {

			@Override
			public String $$(Object t) throws Exception {
				// attilax 老哇的爪子 下午12:08:10 2014-6-25
				core.log("---o625 show req map ");
				core.log(core.obj2jsonO5(reqMap));
				return null;

			}
		}.$("");
	}
	
	
/**
 * 	if(List) JSONArray.fromObject(obj)
@author attilax 老哇的爪子
	@since  2014-6-11 上午10:49:28$

 * @param obj
 * @return
 */
	public static String obj2jsonO5(Object obj)
	{		
		if(obj instanceof List)
			return	JSONArray.fromObject(obj).toString();  
		return	JSONObject.fromObject(obj).toString();
	}
	public static String obj2json_wzFmt(Object obj)
	{		
		if(obj instanceof List)
			return	JSONArray.fromObject(obj).toString(2);  
		return	JSONObject.fromObject(obj).toString(2);
	}
	@Deprecated
	public static String obj2json(Object obj)
	{		
		//if(obj instanceof List)
		return	JSONObject.fromObject(obj).toString();
	}
	
	public static String requestx(final HttpServletRequest req,String para)
	{
		if( req.getParameter(para)==null)
			return "";
		return req.getParameter(para);
	}
	@Deprecated
	public static void print(Object obj) {
		System.out.println(  	core.toJsonStrO88(obj)  );
	}
	/**
	 * 
	@author attilax 老哇的爪子
		@since  o8d m_1_38   
	
	 * @param obj
	 */
	public static void print_wzFmt(Object obj) {
		System.out.println(  	core.toJsonStrO88(obj)  );
	}

	public static void log(String emltxt) {
		StackTraceElement ste=refx.preMethod();
		
		core.logger.info("---"+emltxt+" ["+ste.getClassName()+"."+ste.getMethodName()+"():"+ste.getLineNumber()+"]");
		
	}
//o42
	public static void copy(String oldPath, String newPath) {
		CopyFile f=new CopyFile();
	 	f.copyFile(oldPath, newPath);
		
	}

	public static void debug(String string) {
		// TODO Auto-generated method stub
		
	}

	public static void log(Exception e1) {
		String emltxt=god.getTrace(e1);
	StackTraceElement ste=refx.preMethod(3);
		
		core.logger.info("---"+emltxt+" ["+ste.getClassName()+"."+ste.getMethodName()+"():"+ste.getLineNumber()+"]");

		
	}

	public static Object newx(Class class1) {
		 
		try {
			return class1.newInstance();
		} catch (Exception e) {
			core.log(e);
			return null;
		}
	}

	public static void err(RuntimeException re) {
		core.logger.error("---", re) ;
		
	}

	/**
	@author attilax 老哇的爪子
		@since  2014-5-12 下午02:08:01$
	
	 * @param accTok
	 * @return
	 */@Deprecated
	public static String toJsonStr(Object accTok) {
		// attilax 老哇的爪子  下午02:08:01   2014-5-12 
		String t = JSONArray.fromObject(accTok).toString(2);
		return t;
	}
	@Deprecated
	
	public static String toJsonStrO7(Object accTok) {
		// attilax 老哇的爪子  下午02:08:01   2014-5-12 
		String t = JSONObject.fromObject(accTok).toString(2);
		return t;
	}
	 
	/**
	 * 
	@author attilax 老哇的爪子
		@since  2014-9-1 下午08:04:05   
	
	 * @param accTok
	 * @param cfg
	 * @return
	 */
	public static String toJsonStrO7(Object accTok,JsonConfig cfg) {
		// attilax 老哇的爪子  下午02:08:01   2014-5-12 
		String t = JSONObject.fromObject(accTok,cfg).toString(2);
		return t;
	}


	/**
	 * 
	@author attilax 老哇的爪子
		@since  o87 i_c_4$
	
	 * @param accTok
	 * @return
	 */
	public static String toJsonStrO88(final Object objOrArr) {
		// attilax 老哇的爪子 下午02:08:01 2014-5-12
//   下午07:17:11 2014-9-1  老哇的爪子  Attilax
		return new com.attilax.tryX<String>() {
			@Override public String $$(Object t) throws Exception {
				// attilax 老哇的爪子 i_d_d o87
				return JSONObject.fromObject(objOrArr).toString(2);
			}
		}.$(JSONArray.fromObject(objOrArr).toString(2));

	}
	
	public static String toJsonStrO88(final Object objOrArr,final JsonConfig cfg) {
		// attilax 老哇的爪子 下午02:08:01 2014-5-12
//   下午07:17:11 2014-9-1  老哇的爪子  Attilax
		return new com.attilax.tryX<String>() {
			@Override public String $$(Object t) throws Exception {
				// attilax 老哇的爪子 i_d_d o87
				return JSONObject.fromObject(objOrArr,cfg).toString(2);
			}
		}.$(JSONArray.fromObject(objOrArr,cfg).toString(2));

	}


	/**
	@author attilax 老哇的爪子
		@since  2014-5-12 下午05:51:04$
	
	 * @param string
	 */
	public static void warn(String string ) {
		// attilax 老哇的爪子  下午05:51:04   2014-5-12 
		core.logger.warn("---"+string) ;
	}

	/**
	@author attilax 老哇的爪子
		@since  2014-5-13 下午03:18:53$
	
	 * @param request
	 */
	public static void logurl(HttpServletRequest request) {
		// attilax 老哇的爪子  下午03:18:53   2014-5-13 
		core.log("-- this urlo5c:"+urlUtil.getUrl(request));
	}
	/**
	@author attilax 老哇的爪子
		@since  o78 m338$
	
	 */
	public static void ex4test() {
		if(2>1)
		throw new RuntimeException("eee****************");
	}
	/**
	@author attilax 老哇的爪子
		@since  o7e W85$
	
	 * @param x
	 */
	public static void err(String x) {
		// attilax 老哇的爪子  W85   o7e 
		core.logger.error(x);
		
	}
	/**
	@author attilax 老哇的爪子
		@since  o7e Wa50$
	
	 * @param e
	 */
	public static void err(Exception e) {
		// attilax 老哇的爪子  Wa50   o7e 
		core.logger.error("---", e) ;
		
	}
	
	public static void err(String msg,Exception e) {
		// attilax 老哇的爪子  Wa50   o7e 
		core.logger.error(msg, e) ;
		
	}
	/**
	@author attilax 老哇的爪子
		@since  o7e mwa$
	
	 * @param queryPropertyssMap
	 */
	public static void logMap(Map queryPropertyssMap) {
		// attilax 老哇的爪子  mwa   o7e 
	 
			String t = JSONObject.fromObject(queryPropertyssMap).toString(2);
	 
		core.logger.info(t);
		
	}
	
	public static String map2str(Map reqMap) {
		// attilax 老哇的爪子  mwa   o7e 
	 
			String t = JSONObject.fromObject(reqMap).toString(2);
	 
		return t;
		
	}
	public static void logObj(Object queryPropertyssMap) {
		// attilax 老哇的爪子  mwa   o7e 
	 
			String t = JSONObject.fromObject(queryPropertyssMap).toString(2);
	 
		core.logger.info(t);
		
	}
	/**
	@author attilax 老哇的爪子
		@since  o7f i9h$
	
	 * @param arg0
	 * @return
	 */
	public static int toInt(Object arg0) {
		// attilax 老哇的爪子  i9h   o7f 
		return Integer.parseInt(arg0.toString());
		 
		
	}
	/**
	@author attilax 老哇的爪子
		@since  o7f j4059$
	
	 * @param js
	 * @return
	 * @throws JSONException 
	 */
//	@SuppressWarnings("all") public static Map json2map(String js) throws JSONException {
//		// attilax 老哇的爪子  j4059   o7f 
//        Object o = JSONUtil.deserialize(js);
//	 //	Map m = new BeanMap(o);
//		return (Map) o;
//		
//	}

//	public static Handler new_cglib(final Class<Handler> class1) {
//		   final Enhancer enhancer = new Enhancer();
////		   class xx{
////			  public Object getProxy(Class clazz){
//				  //设置需要创建子类的类
//				  enhancer.setSuperclass(class1);
//				   enhancer.setCallbacks(new Callback[]{NoOp.INSTANCE});
//				  //通过字节码技术动态创建子类实例
//				  return (Handler) enhancer.create();
//// 			 }
//		 //  }
//		
////		return null;
//	}
	/**
	@author attilax 老哇的爪子
	\t@since  Jul 19, 2014 8:35:27 PM$
	
	 * @param obj
	 * @return
	 * @throws cantFindMatchFieldException 
	 * @throws cantGetValExcept 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
//	public static Boolean isIdValEmpty(Object obj) throws cantFindMatchFieldException, cantGetValExcept {
//		// attilax 老哇的爪子 8:35:27 PM Jul 19, 2014
//
//		Field fld;
//		 
//			fld = refx.anyFld(obj, new Closure() {
//
//				@Override
//				public Object execute(Object arg0) throws Exception {
//					// attilax 老哇的爪子 8:40:55 PM Jul 19, 2014
//					Field fld = (Field) arg0;
//					Id id = fld.getAnnotation(Id.class);
//					if (id != null)
//						return true;
//					return false;
//
//				}
//			});
//		 
//		Object v;
//		try {
//			v = fld.get(obj);
//		} catch (IllegalArgumentException e) {
//			//  attilax 老哇的爪子 9:47:12 PM   Jul 19, 2014   
//			throw new cantGetValExcept();
//		} catch (IllegalAccessException e) {
//			//  attilax 老哇的爪子 9:47:12 PM   Jul 19, 2014   
//			throw new cantGetValExcept();
//		}
//		if (v == null)
//			return true;
//		else
//			return false;
//	}
	/**
	@author attilax 老哇的爪子
	\t@since  Jul 19, 2014 9:41:42 PM$
	
	 * @param fld
	 * @return
	 * @throws CantFindConverterExcept 
	 */
	public static org.apache.commons.beanutils.Converter getConverter(Field fld) throws CantFindConverterExcept {
		// attilax 老哇的爪子  9:41:42 PM   Jul 19, 2014 
		
		{ 
			Converter ct=fld.getAnnotation(Converter.class);
			if(ct==null)throw new CantFindConverterExcept(fld.getName());
		return (org.apache.commons.beanutils.Converter) core.newx(ct.value()) ;
		 } 
		
		
	}
	/**
	@author attilax 老哇的爪子
		@since  o7t 0_51_45$
	
	 * @param target
	 * @return
	 */
	public static String[] toStrArr(Object... target) {
		// attilax 老哇的爪子  0_51_45   o7t 
		Object[] a=target;
		String[] sa=new String[a.length];
		List li=new ArrayList();
		for (Object o : a) {
			try {
				 String s=o.toString();
				 li.add(s);
			} catch (Exception e) {
				core.log(e);
			}
			
		}
		return  (String[]) li.toArray(new String[li.size()]) ;
		 
		
	}
	/**
	@author attilax 老哇的爪子
		@since  o7t 0_58_b$
	
	 * @param target
	 * @return
	 */
	public static String toStr(Object[] target) {
		// attilax 老哇的爪子  0_58_b   o7t 
	//	strUtil.join(target,",");
	
		return null;
		 
		
	}
	/**
	@author attilax 老哇的爪子
		@since  o83 j_h_7$
	
	 * @param date
	 * @return
	 */
	public static Timestamp toTimeStamp(Date date) {
		// attilax 老哇的爪子  j_h_7   o83 
		
		
		return  new Timestamp(date.getTime()); 
	 
		
	}
	/**save ver ....apache d not safe
	@author attilax 老哇的爪子
		@since  o8j 1_54_8   
	
	 * @param tsk
	 * @param mtr
	 */
	public static void copyProperties(Object dest, Object orig) {
		// attilax 老哇的爪子  1_54_8   o8j 
		try {
			BeanUtils.copyProperties(dest, orig);
		} catch (IllegalAccessException e) {
			//  attilax 老哇的爪子 1_54_j   o8j   
			core.warn(e);
		} catch (InvocationTargetException e) {
			//  attilax 老哇的爪子 1_54_j   o8j   
			core.warn(e);
		}
	}
	/**
	@author attilax 老哇的爪子
		@since  o8q 0_51_40   
	
	 * @param e
	 * @return
	 */
	public static String toJsonStr_NF(final Object objOrArr) {
		// attilax 老哇的爪子  0_51_40   o8q 
		return new com.attilax.tryX<String>() {
			@Override public String $$(Object t) throws Exception {
				// attilax 老哇的爪子 i_d_d o87
				return JSONObject.fromObject(objOrArr).toString();
			}
		}.$(JSONArray.fromObject(objOrArr).toString());
	 
		
	}
	/**
	 * PropertyUtils.copyProperties
	 * 
	 * 除BeanUtils外还有一个名为PropertyUtils的工具类，它也提供copyProperties()方法，
	 * 作用与 BeanUtils的同名方法十分相似，主要的区别在于后者(BeanUtils) 提供类型转换功能，即发现两个JavaBean的同名属性为不同类型时，在支持的数据类型范围内进行转换
	 * ，而前者不支持这个功能，但是速度会更快一些。BeanUtils支持的转换类型如下：
	@author attilax 老哇的爪子
		@since  o8r i_k_54   
	
	 * @param tsk
	 * @param mtr
	 */
	@Deprecated
	//@Conditional(displayType="jeig b suppt type auto vonvert ..should use beans .cpy")
	public static void copyPropertiesO8q(Object dest, Object orig) {
		// attilax 老哇的爪子  i_k_54   o8r 
		try {
			PropertyUtils.copyProperties(dest, orig);
		} catch (IllegalAccessException e) {
			//  attilax 老哇的爪子 i_l_47   o8r   
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			//  attilax 老哇的爪子 i_l_47   o8r   
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			//  attilax 老哇的爪子 i_l_47   o8r   
			e.printStackTrace();
		}
	}
	/**
	@author attilax 老哇的爪子
		@since  o8s 0_g_l   
	
	 */
	public static void execMeth_Ays(final Runnable runnable, final String threadName) {
		// attilax 老哇的爪子  0_g_l   o8s 
		try {
			newThread(runnable,threadName);
		} catch (Exception e) {
			core.err(e);
		}
		
	}
	
	public static void submit(final FutureTask  callable, final String threadName) {
		// attilax 老哇的爪子  0_g_l   o8s 
		try {
			newThread(callable,threadName);
		} catch (Exception e) {
			core.err(e);
		}
		
	}
	
	public static Thread newThread(final Runnable runnable, final String threadName) {
		return	new com.attilax.tryX<Thread>() {
				@Override public Thread $$(Object t) throws Exception {
					// attilax 老哇的爪子 h_i_r o7n

					Thread Thread_ini_fentsiController = new Thread(runnable);
					Thread_ini_fentsiController.setName(threadName);
					Thread_ini_fentsiController.setPriority(Thread.MAX_PRIORITY);
					Thread_ini_fentsiController.start();
					// System.out.println("--thrd:"+threadName);
					return Thread_ini_fentsiController;
				}
			}.$(new Thread());
			
		
		}
	public static Thread newThread(final FutureTask  futTask, final String threadName) {
		return	new com.attilax.tryX<Thread>() {
				@Override public Thread $$(Object t) throws Exception {
					// attilax 老哇的爪子 h_i_r o7n

					Thread Thread_ini_fentsiController = new Thread(futTask);
					Thread_ini_fentsiController.setName(threadName);
					Thread_ini_fentsiController.setPriority(Thread.MAX_PRIORITY);
					Thread_ini_fentsiController.start();
					// System.out.println("--thrd:"+threadName);
					return Thread_ini_fentsiController;
				}
			}.$(new Thread());
			
		
		}
	/**
	@author attilax 老哇的爪子
		@since  o9f 7_y_e   
	
	 * @param s
	 * @return
	 */
	public static String objID(Object s) {
		// attilax 老哇的爪子  7_y_e   o9f 
		try{
		return String.valueOf( s.hashCode());
		}catch(Exception e){ return "";}
		
	}
	/**
	@author attilax 老哇的爪子
		@since  o03 0_s_39   
	
	 * @param e
	 * @return
	 */
	public static String getTrace(Exception e) {
		// attilax 老哇的爪子  0_s_39   o03 
		return god.getTrace(e);
		
	}
	/**
	@author attilax 老哇的爪子
		@since  o03 2_f_42   
	
	 * @param s2
	 * @return
	 */
	public static String Htmlencode(Object s2) {
		//org.json.simple.ItemList
		// attilax 老哇的爪子  2_f_42   o03 
		String s = s2.toString().replaceAll("\r\n", "<br>");
	  s=	StringEscapeUtils.escapeHtml4(s);
		return s;
		
	}
	/**
	@author attilax 老哇的爪子
		@since  o03 2_r_1   
	
	 * @param s2
	 * @return
	 */
	public static String txt2html(Object s2) {
		// attilax 老哇的爪子  2_r_1   o03 
		String	  s=	StringEscapeUtils.escapeHtml4(s2.toString());
		 s = s.toString().replaceAll("\r\n", "<br>\r\n");
		 s = s.toString().replaceAll(" ", "&nbsp;");
		 s = s.toString().replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
		return s;
		
	}
	/**
	@author attilax 老哇的爪子
		@since  o09 2_55_d   
	
	 * @param req
	 * @return
	 */
	public static String req2str(HttpServletRequest req) {
		// attilax 老哇的爪子  2_55_d   o09 
		String t = JSONObject.fromObject(req.getParameterMap()).toString(2);
		 
		return t;
	//	return null;
		
	}
	/**
	@author attilax 老哇的爪子
	 * @return 
		@since  o09 h_k_59   
	
	 */
	public static String getUUidName() {
		// attilax 老哇的爪子  h_k_59   o09 
		return filex.getUUidName();
	}
	
	
	 // Map --> Bean 2: 利用org.apache.commons.beanutils 工具类实现 Map --> Bean  
    public static void Map2Bean2(Map<String, Object> map, Object obj) {  
        if (map == null || obj == null) {  
            return;  
        }  
        try {  
            BeanUtils.populate(obj, map);  
        } catch (Exception e) {  
            System.out.println("transMap2Bean2 Error " + e);  
        }  
    }  
    public static Map<String, Object> toMap(Object obj)
    {
    	return Bean2Map(obj);
    }
    
    // Bean --> Map 1: 利用Introspector和PropertyDescriptor 将Bean --> Map  
    public static Map<String, Object> Bean2Map(Object obj) {  
  
    	
        if(obj == null){  
        	new HashMap<String, Object>();
        }          
        Map<String, Object> map = new HashMap<String, Object>();  
        try {  
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());  
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
            for (PropertyDescriptor property : propertyDescriptors) {  
                String key = property.getName();  
  
                // 过滤class属性  
                if (!key.equals("class")) {  
                    // 得到property对应的getter方法  
                    Method getter = property.getReadMethod();  
                    Object value = getter.invoke(obj);  
  
                    map.put(key, value);  
                }  
  
            }  
        } catch (Exception e) {  
            System.out.println("transBean2Map Error " + e);  
        }  
  
        return map;  
  
    }
		/**
		@author attilax 老哇的爪子
		 * @return 
		@since   oac d_g_38
		 
		 */
	public static Object retry3(Closure closure,
			errEventProcess errEventProcess, String logRootDir) {
		try {// retry1
			return closure.execute(null);
		} catch (Exception e) {
			filex.save_SF(core.getTrace(e),
					logRootDir + "\\" + filex.getUUidName() + "w1.txt");

			try {// retry2
				return closure.execute(null);
			} catch (Exception e2) {
				filex.save_SF(core.getTrace(e2),
						logRootDir + "\\" + filex.getUUidName() + "w2.txt");

				try {// retry3
					return closure.execute(null);
				} catch (Exception e3) {
					filex.save_SF(core.getTrace(e3),
							logRootDir + "\\" + filex.getUUidName() + "w3e.txt");
					core.err(e);
					try {
						return errEventProcess.execute(null);
					} catch (Exception e1) {
						return new Object();
					}
				}
			}
		}

	}
			/**
			@author attilax 老哇的爪子
			@since   oao 10_0_59
			 
			 */
		public static int toInt(Object object, int i) {
			 try {
					return (Integer)object;
			} catch (Exception e) {
				return i;
			}
		
		}


} 
