/**
 * @author attilax 老哇的爪子
\t@since  Aug 29, 2014 9:54:50 AM$
 */
package com.attilax.lang;
import com.attilax.core;
import com.attilax.coll.ListX;
import com.attilax.collection.GvCycleQueueSvs;
//import com.focustar.push.Icase;

import static  com.attilax.core.*;

import java.util.*;
import java.net.*;
import java.io.*;
/**
 * @author  attilax 老哇的爪子
 *@since  Aug 29, 2014 9:54:50 AM$
 */
public class CaseX<atitype> {
	
	List<Icaseitem> li=ListX.<Icaseitem>$().toLi();

	/**
	@author attilax 老哇的爪子
	\t@since  Aug 29, 2014 9:55:04 AM$
	
	 * @param icase
	 * @return
	 */
	public   CaseX<atitype> add(Icaseitem icase) {
		// attilax 老哇的爪子  9:55:04 AM   Aug 29, 2014 
		
		
		{ 
		 
	this.li.add(icase);
	return this;
		 } 
		
		
	}
	//  attilax 老哇的爪子 9:54:51 AM   Aug 29, 2014   
static ThreadLocal<Integer> stat=new ThreadLocal<Integer>();
static ThreadLocal<CaseX> casexThrdloc=new ThreadLocal<CaseX>();
static ThreadLocal<Object> retObjThrdloc=new ThreadLocal<Object>();
private static Integer started=1;
	/**
	@author attilax 老哇的爪子
	\t@since  Aug 29, 2014 10:21:06 AM$
	
	 */
	public static <t> CaseX<t> $() {
		// attilax 老哇的爪子  10:21:06 AM   Aug 29, 2014 
//		if(stat.get()==null)
//			stat.set(0);
//		if(stat.get()==null)
//		{
		
			CaseX<t> caseX = new CaseX<t>();
			CaseX.casexThrdloc.set(caseX);
			stat.set(started);
			return caseX;
//		}
//		if( stat.get()==started)
//		{
//			stat.set(null);
//			return	startProcess(casexThrdloc.get());
//		}
//		return null;
//			
		 
		
	}
	
	
	public   <t>  Object start() {
		// attilax 老哇的爪子  10:21:06 AM   Aug 29, 2014 
 
		Object r = new Object();
	 
		for (Icaseitem it : li) {
			if(it.isConditOk())
			r=	it.exec();
		}
	 
//		if(r instanceof Boolean)
//			 
//			return 
		return   "";
			
		 
		
	}
	/**
	@author attilax 老哇的爪子
	 * @param caseX 
		@since  o8s j_6_3   
	
	 * @return
	 */@Deprecated
	private static CaseX startProcess(CaseX caseX) {
		// attilax 老哇的爪子  j_6_3   o8s 
		Object r = null;
		List<Icaseitem> li=caseX.li;
		for (Icaseitem it : li) {
			if(it.isConditOk())
			r=	it.exec();
		}
	 retObjThrdloc.set(r);
		return caseX;
		
	}
}

//  attilax 老哇的爪子