/**
 * @author attilax 老哇的爪子
\t@since  Jul 18, 2014 10:10:38 PM$
 */
package com.attilax.MDA;
 

import com.attilax.core;
import com.attilax.time.timeUtil;
import com.attilax.util.DateUtil;

import static  com.attilax.core.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.*;
import java.lang.reflect.Field;
import java.net.*;
import java.io.*;

import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
/**
 * @author  attilax 老哇的爪子
 *@since  Jul 18, 2014 10:10:38 PM$
 */
public class DateAdptr_rang implements IAdapter   {
	//  attilax 老哇的爪子 10:10:38 PM   Jul 18, 2014   
	public static void main(String[] args) {
		 
		
	}
	
	/**
	@author attilax 老哇的爪子
	\t@since  Jul 18, 2014 10:16:53 PM$

	 */
	public List convert(	String propertyName,String valFrmUi) {
		// attilax 老哇的爪子  10:16:53 PM   Jul 18, 2014 
		java.util.List  li=new ArrayList ();
//		Object[] a=(Object[]) obj;
//		String propertyName=(String) a[0];
//		String valFrmUi=(String) a[1];
		{
			String s=valFrmUi.toString();
			String s1=s+" 00:00:01";
			String s2=s+" 23:59:59";
			;
			Timestamp ts1;
			Timestamp ts2;
			 
			try {
				ts1 = DateUtil.toTimeStamp(s1, true);
				 
		
				ts2 = DateUtil.toTimeStamp(s2, true);
				SimpleExpression startCondt = Restrictions.gt(propertyName, ts1);
				li.add(startCondt);
				li.add(Restrictions.lt(propertyName, ts2));
			} catch (ParseException e) {
				//  attilax 老哇的爪子 1:28:28 AM   Jul 19, 2014   
				core.log(e);
			}
		
			
		}
		return li;
		 

	}

	/* (non-Javadoc)
	 * @see com.attilax.MDA.IAdapter#convert(java.lang.reflect.Field, java.util.Map)
	 * @author  attilax 老哇的爪子
	 *@since  o9p i_42_46$
	 */
	@Override public List convert(Field fld, Map queryPropertyssMap) {
		// attilax 老哇的爪子  i_42_46   o9p 
		// attilax 老哇的爪子  10:16:53 PM   Jul 18, 2014 
				java.util.List  li=new ArrayList ();
//				Object[] a=(Object[]) obj;
//				String propertyName=(String) a[0];
//				String valFrmUi=(String) a[1];
				{
					Object valFrmUi=queryPropertyssMap.get(fld.getName());
					String s=valFrmUi.toString();
					String s1=s+" 00:00:01";
					String s2=s+" 23:59:59";
					;
					Timestamp ts1;
					Timestamp ts2;
					 
					try {
						ts1 = DateUtil.toTimeStamp(s1, true);
						 
				
						ts2 = DateUtil.toTimeStamp(s2, true);
						String propertyName=fld.getName();
						SimpleExpression startCondt = Restrictions.gt(propertyName, ts1);
						li.add(startCondt);
						li.add(Restrictions.lt(propertyName, ts2));
					} catch (ParseException e) {
						//  attilax 老哇的爪子 1:28:28 AM   Jul 19, 2014   
						core.log(e);
					}
				
					
				}
				return li;
		//return null;
		
	}
}

//  attilax 老哇的爪子