/**
 * @author attilax 老哇的爪子
	@since  o90 j_4_38$
 */
package com.attilax.db;
import com.attilax.core;
import com.attilax.ioc.IocX;
import com.attilax.log.logRec;
import com.attilax.persistence.PX;
import com.focustar.push.PrgrmNoticer;
import com.focustar.push.TaskNoticer;
 import static  com.attilax.core.*;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.net.*;
import java.io.*;
/**
 * @author  attilax 老哇的爪子
 *@since  o90 j_4_38$
 */
public class hbCacheT {

	/**
	@author attilax 老哇的爪子
		@since  o90 j_4_38   
	
	 * @param args
	 */
	public static void main(String[] args) {
		//todox o99 timer 
		 Executors.newScheduledThreadPool(2).scheduleWithFixedDelay(new Runnable() {
				
				@Override public void run() {
					PX px=IocX.getBean(PX.class);
					logRec l=(logRec) px.get(logRec.class, 195);
					l.setCate("aaaa");
					px.merge(l);
				//	System.out.println(l.getLevel());
				 
				}
			},0, 7, TimeUnit.SECONDS);
		// attilax 老哇的爪子  j_4_38   o90 
		

	}
	//  attilax 老哇的爪子 j_4_38   o90   
}

//  attilax 老哇的爪子