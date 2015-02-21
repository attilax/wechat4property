/**
 * @author attilax 老哇的爪子
\t@since  Jul 19, 2014 9:34:41 PM$
 */
package com.attilax.MDA;
import com.attilax.core;

import static  com.attilax.core.*;

import java.util.*;
import java.net.*;
import java.io.*;

import org.apache.commons.beanutils.Converter;
/**
 * @author  attilax 老哇的爪子
 *@since  Jul 19, 2014 9:34:41 PM$
 */
public class DwrConverter implements Converter {

	/* (non-Javadoc)
	 * @see org.apache.commons.beanutils.Converter#convert(java.lang.Class, java.lang.Object)
	 * @author  attilax 老哇的爪子
	 *@since  Jul 19, 2014 9:36:45 PM$
	 */
	@Override
	public Object convert(Class arg0, Object arg1) {
		// attilax 老哇的爪子  9:36:45 PM   Jul 19, 2014 
		
		{ 
			if(arg1==null || arg1.toString().equals("0"))
				return null;
			else
				return arg1;
		 } 
		
		
	}
	//  attilax 老哇的爪子 9:34:41 PM   Jul 19, 2014   
}

//  attilax 老哇的爪子