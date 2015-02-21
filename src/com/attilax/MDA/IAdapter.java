/**
 * @author attilax 老哇的爪子
\t@since  Jul 18, 2014 10:20:21 PM$
 */
package com.attilax.MDA;
import com.attilax.core;

import static  com.attilax.core.*;

import java.util.*;
import java.lang.reflect.Field;
import java.net.*;
import java.io.*;
/**
 * @author  attilax 老哇的爪子
 *@since  Jul 18, 2014 10:20:21 PM$
 */
public interface IAdapter {
	//  attilax 老哇的爪子 10:20:21 PM   Jul 18, 2014   
	public List convert(	String propertyName,String valFrmUi);

	/**
	@author attilax 老哇的爪子
		@since  o9p i_39_57   
	
	 * @param fld
	 * @param queryPropertyssMap
	 * @return
	 */
	public List convert(Field fld, Map queryPropertyssMap);
}

//  attilax 老哇的爪子