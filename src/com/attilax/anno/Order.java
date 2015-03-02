/**
 * @author attilax 老哇的爪子
	@since  o7h W253$
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
 *@since  o7h W253$
 */@Retention(RetentionPolicy.RUNTIME)
 @Inherited
 @Documented
public @interface Order {

	String value();
	//  attilax 老哇的爪子 W253   o7h   
}

//  attilax 老哇的爪子