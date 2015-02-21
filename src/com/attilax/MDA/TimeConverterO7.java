/**
 * @author attilax 老哇的爪子
\t@since  Jul 19, 2014 5:21:49 AM$
 */
package com.attilax.MDA;

import com.attilax.core;
//import com.attilax.convert.Converter;
import com.attilax.time.timeUtil;
import com.attilax.util.DateUtil;

import static com.attilax.core.*;

import java.text.ParseException;
import java.util.*;
import java.net.*;
import java.io.*;

/**
 * @author attilax 老哇的爪子
 * @since Jul 19, 2014 5:21:49 AM$
 */
public class TimeConverterO7 implements org.apache.commons.beanutils.Converter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.commons.beanutils.Converter#convert(java.lang.Class,
	 * java.lang.Object)
	 * 
	 * @author attilax 老哇的爪子
	 * 
	 * @since Jul 19, 2014 5:24:02 AM$
	 */
	@Override
	public Object convert(Class arg0, Object arg1) {
		// attilax 老哇的爪子 5:24:02 AM Jul 19, 2014
 
			try {
				
				return timeUtil.str2secs(arg1);
			} catch (ParseException e) {
				// attilax 老哇的爪子 5:28:07 AM Jul 19, 2014
				core.log(e);
				return 0;
			}
		 
	}
	// attilax 老哇的爪子 5:21:49 AM Jul 19, 2014
}

// attilax 老哇的爪子