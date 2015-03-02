/**
 * @author attilax 老哇的爪子
	@since  o7g h4439$
 */
package com.attilax.anno;
import com.attilax.core;
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
 *@since  o7g h4439$
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface ValidateSvrside {

	int min() default -1 ;
	int max() default -1;
	int minLen() default -1 ;
	int maxLen() default -1;
	//  attilax 老哇的爪子 h4439   o7g   

	//String valideFailOp_Querymode();

	//boolean valideFailNotAdd_Querymode () 

 
 
}

//  attilax 老哇的爪子