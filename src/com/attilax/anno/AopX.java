/**
 * @author attilax 老哇的爪子
	@since  o7n 0_4_w$
 */
package com.attilax.anno;
import com.attilax.Closure;
import com.attilax.core;
import com.attilax.api.Handler;
 import static  com.attilax.core.*;
import java.util.*;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

//import org.apache.ecs.xhtml.object;
import org.apache.ecs.xhtml.object;
import org.apache.log4j.Logger;
//import org.springframework.aop.ThrowsAdvice;

import com.attilax.core;
import com.attilax.util.god;
/**
 * @author  attilax 老哇的爪子
 *@since  o7n 0_4_w$
 */
public abstract class AopX {
	
	  public static Map mp=new HashMap<String,Handler>();
synchronized public static void  inj(String key,Closure handler)
{
	 mp.put(key,handler);
	 
}

	/**
	@author attilax 老哇的爪子
		@since  o7n 0_4_w$
	
	 * @param args
	 */
 



 
 
public	Object para1x;
 

	/**
	@author attilax 老哇的爪子
		@since  2014-5-28 下午01:28:11$
	
	 */
 

//	tryX curTryx;
	// private Logger loger;
	private String para1;
	private boolean log4err = false;

	/**
	 * @param args
	 */
//	public static void main(String[] args) {
//		String x = new tryX<String>() {
//
//			@Override
//			public String $$(Object t) throws Exception {
//				// int i=(Integer) t;
//				return "my def";
//			}
//		}.$("my ");
//		System.out.println(x);
//	}
  public String errmsg="..";
  public boolean flagErr=false;
	// public abstract void m() ;
	@Deprecated
	public Object itemWrap(Object t) throws Exception {
	 	try {
		
			return $$(t);
		} catch (Exception e) {
			log(e);
			this.errmsg=god.getTrace(e);
			this.flagErr=true;
			Object[] a=(Object[]) this.defaultReturnValue;
			Closure hd = (Closure) AopX.mp.get(a[0]);
			return  hd.execute(a);

		}

	}
	
	@Deprecated
	public Object itemWrap4retErrmsg(Object t) {
		try {
			return $$(t);
		} catch (Exception e) {
			log(e);
			this.errmsg=god.getTrace(e);
			this.flagErr=true;
			return this.errmsg;

		}

	}
	
	/**
	 * yva error getTrace(e);
	@author attilax 老哇的爪子
		@since  2014-6-9 下午05:19:38$
	
	 * @return
	 */
	public Object $defValIsErrmsg() {
		// attilax 老哇的爪子  上午11:02:11   2014-5-28 
		return this.itemWrap4retErrmsg("");
//		this.itemWrap("");
//		if(this.flagErr)
//		{
//		this.defaultReturnValue = (atiType) this.errmsg;
//		return  (atiType) this.errmsg;
//		}return this.defaultReturnValue;
	//	return null;
	}

	public Object $defValIsErrmsgSmp() {
		// attilax 老哇的爪子  下午06:42:00   2014-6-9 
		  return this.itemWrap4retErrmsgSmp(""); 
	}
	
	/**
	@author attilax 老哇的爪子
		@since  2014-6-9 下午06:42:50$
	
	 * @param string
	 * @return
	 */
	private Object itemWrap4retErrmsgSmp(Object t) {
		// attilax 老哇的爪子  下午06:42:50   2014-6-9 
		try {
			return $$(t);
		} catch (Exception e) {
			log(e);
			this.errmsg=e.getMessage();
			return  this.errmsg;

		}

	 
	}
 
/**
 * 
@author attilax 老哇的爪子
	@since  o7n 0_h_6$

 * @param t
 * @return
 */
	public abstract Object $$(Object t)  ;

 

	/**
	 * @deprecated
	 * @param tryImpt
	 * @return
	 */
	// public static Object $(tryX tryImpt) {
	//		
	// // tryImpt.itemWrap("");
	// tryImpt.curTryx = tryImpt;
	// return tryImpt.itemWrap("");
	// // return tryImpt;
	// }
	/**
	 * set default value
	 * log err2warn and catch
	 * @param
	 * @return
	 * @throws Exception 
	 */
	public Object $(Object... defaultValue)  {
	//	Object[] a=defaultValue;
	 	this.defaultReturnValue = defaultValue;
	 
		
		try {
			return this.itemWrap("");
		} catch (Exception e) {
			//  attilax 老哇的爪子 3_0_39   o7n   
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * only log except and  rethrow
	@author attilax 老哇的爪子
		@since  2014-5-9 上午09:59:42$
	
	 * @return
	 */
	public Object $() {
		// attilax 老哇的爪子  上午09:58:58   2014-5-9 
	
		try {
			return $$("");
		} catch (Exception e) {
			this.log4err = true;
			log(e);

			throw new RuntimeException(e);

		}
	}

	/**
	 * 
	 * @category   defaultValue_log4Err
	 * o3i invoke if ex show err log, other same Name Method is warn
	 * log err2err and catch
	 * @param defaultValue
	 * @return
	 * @throws Exception 
	 */
	public Object $(Object defaultValue, int log4Err) throws Exception {
		this.defaultReturnValue = defaultValue;
		this.log4err = true;
		return this.itemWrap("");
	}

	// public t $() {
	//			
	// t o = (t)new Object();
	// // t tObj = o.newInstance();
	// if( o instanceof List )
	// this.defaultReturnValue= (t) new ArrayList();
	// else if (o instanceof String)
	// this.defaultReturnValue=(t) " raw str";
	//		 
	// return this.itemWrap("");
	// }

	// public tryX setLogger(Logger logger) {
	// this.loger = logger;
	// return this;
	// }

	// public Object end () {
	// return this.itemWrap("");
	//
	// }

	private Object getObj() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object log(Exception e) {
		// MoodUserIndexService.logger.error(god.getTrace(e));
		if (log4err) {
			core.logger.error(god.getTrace(e));
			return e;
		}
		core.logger.warn("-----catch except la ..");
		core.logger.warn(god.getTrace(e));
		return e;
	}

	public Object defaultReturnValue;
	// public Object $() {
	// return this.itemWrap("");
	// };

 

 
	//  attilax 老哇的爪子 0_4_w   o7n   
}

//  attilax 老哇的爪子