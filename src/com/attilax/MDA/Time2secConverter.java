/**
 * @author attilax 老哇的爪子
	@since  o7g [351$
 */
package com.attilax.MDA;
import com.attilax.ClosureNoExcpt;
import com.attilax.core;
import com.attilax.time.timeUtil;
 
 import static  com.attilax.core.*;
import java.text.ParseException;
import java.util.*;
import java.net.*;
import java.io.*;
/**
 * @author  attilax 老哇的爪子
 *@since  o7g [351$
 */
public class Time2secConverter implements org.apache.commons.beanutils.Converter {

	/* (non-Javadoc)
	 * @see com.sun.org.apache.commons.beanutils.Converter#convert(java.lang.Class, java.lang.Object)
	 * @author  attilax 老哇的爪子
	 *@since  o7g [4e$
	 */
	@Override public Object convert(Class arg0, Object arg1) {
		// attilax 老哇的爪子  [4e   o7g 
		try {
			return  timeUtil.str2secs(arg1);
		} catch (ParseException e) {
			//  attilax 老哇的爪子 [5l   o7g   
			core.log(e);
			e.printStackTrace();
		}
		throw new convertExcept();
		 
		
	}

	/* (non-Javadoc)
	 * @see com.attilax.ClosureNoExcpt#execute(java.lang.Object)
	 * @author  attilax 老哇的爪子
	 *@since  o7g [351$
	 */
	 
	//  attilax 老哇的爪子 [351   o7g   
}

//  attilax 老哇的爪子