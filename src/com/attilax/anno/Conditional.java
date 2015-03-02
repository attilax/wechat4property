/**
 * @author attilax 老哇的爪子
	@since  o7g Xd40$
 */
package com.attilax.anno;
import com.attilax.core;
import com.attilax.MDA.DateAdptr_rang;

import static  com.attilax.core.*;

import java.util.*;
import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.*;
import java.io.*;

import org.apache.tapestry5.validator.None;
/**
 * def cond process ...if gene view ...condition_reder
 * @author  attilax 老哇的爪子
 *@since  o7g Xd40$
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Conditional  {

	/**
	 * only for gene ui and some for  condti query.
	@author attilax 老哇的爪子
		@since  o8j 0_50_e   
	
	 * @return
	 */
	String displayType()  default "single";
	//  attilax 老哇的爪子 Xd40   o7g   

	String op() default "=" ;

	String rangStart() default "start";

	String rangEnd()default "end";

//	String adptrMode() default "";

	/**
	 * cstm mode
	@author attilax 老哇的爪子
		@since  o8j 0_51_f   
	
	 * @return
	 */
	Class<?> adptr() default None.class;

	/**
	 * set except condion   as floow   except="null,0"
	@author attilax 老哇的爪子
		@since  o07 k_51_p   
	
	 * @return
	 */
	String except() default "";

	String uifld() default "";

	int nullval() default -1;

	String fld() default "";

	//String adptrMode();
}

//  attilax 老哇的爪子