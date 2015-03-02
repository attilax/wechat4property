/**
 * @author attilax 老哇的爪子
\t@since  Jul 19, 2014 8:10:02 AM$
 */
package com.attilax.anno;
import com.attilax.core;

import static  com.attilax.core.*;

import java.util.*;
import java.net.*;
import java.io.*;
/**
 * @author  attilax 老哇的爪子
 *@since  Jul 19, 2014 8:10:02 AM$
 */
public @interface ConditionFieldCfg {

	String order() default "";
	//  attilax 老哇的爪子 8:10:02 AM   Jul 19, 2014   

	int columns() default 3;
}

//  attilax 老哇的爪子