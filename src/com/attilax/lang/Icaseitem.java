/**
 * @author attilax 老哇的爪子
\t@since  Aug 29, 2014 10:15:09 AM$
 */
package com.attilax.lang;
import com.attilax.core;
 import static  com.attilax.core.*;
import java.util.*;
import java.net.*;
import java.io.*;
/**
 * @author  attilax 老哇的爪子
 *@since  Aug 29, 2014 10:15:09 AM$
 */
public interface Icaseitem {
	//  attilax 老哇的爪子 10:15:10 AM   Aug 29, 2014   
	
	/**
	@author attilax 老哇的爪子
		@since  o8s j_g_40$
	
	 * @param b
	 */
//	public   Icaseitem(boolean b) {}

	public Object exec();

	/**
	@author attilax 老哇的爪子
		@since  o8s j_n_t   
	
	 * @return
	 */
	public boolean isConditOk();
}

//  attilax 老哇的爪子