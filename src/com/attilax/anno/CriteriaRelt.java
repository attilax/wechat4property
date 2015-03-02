/**
 * @author attilax 老哇的爪子
	@since  o9q 5_48_v$
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
/**for subobj porp as  conditon to query
 * @author  attilax 老哇的爪子
 *@since  o9q 5_48_v$
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface CriteriaRelt {

	String fld() default "";
	//  attilax 老哇的爪子 5_48_v   o9q   

	String uiFld()  default "";
}

//  attilax 老哇的爪子