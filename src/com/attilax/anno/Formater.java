/**
 * @author attilax 老哇的爪子
	@since  o7g Z56x$
 */
package com.attilax.anno;
import com.attilax.ClosureNoExcpt;
import com.attilax.core;
import com.attilax.MDA.DateAdptr_rang;
import com.attilax.MDA.TimerFormater;
//import com.attilax.convert.defConvert;
 

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
 *@since  o7g Z56x$
 */@Retention(RetentionPolicy.RUNTIME)
 @Inherited
 @Documented
public @interface Formater {

//	Class<?> converter() default defConvert.class;

 

	Class<?> value();
	 
 }