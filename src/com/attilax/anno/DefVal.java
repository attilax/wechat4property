/**
 * @author attilax 老哇的爪子
\t@since  Jul 19, 2014 8:01:56 PM$
 */
package com.attilax.anno;
import com.attilax.core;
import com.attilax.MDA.DateTimeDefVal;

import static  com.attilax.core.*;

import java.util.*;
import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.*;
import java.io.*;
/**
 * @author  attilax 老哇的爪子
 *@since  Jul 19, 2014 8:01:56 PM$
 */@Retention(RetentionPolicy.RUNTIME)
 @Inherited
 @Documented
public @interface DefVal {

	Class<?> value();
	//  attilax 老哇的爪子 8:01:56 PM   Jul 19, 2014   
}

//  attilax 老哇的爪子