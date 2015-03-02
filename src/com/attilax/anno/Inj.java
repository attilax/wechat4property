/**
 * @author attilax 老哇的爪子
	@since  o7m j_c_3$
 */
package com.attilax.anno;
import com.attilax.core;
import com.focustar.util.HbX4vod;
 import static  com.attilax.core.*;
import java.util.*;
import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.net.*;
import java.io.*;
/**
 * @author  attilax 老哇的爪子
 *@since  o7m j_c_3$
 */@Retention(RetentionPolicy.RUNTIME)
 @Inherited
 @Documented
// @Target()
public @interface Inj {

	Class<HbX4vod> value();
	//  attilax 老哇的爪子 j_c_3   o7m   
}

//  attilax 老哇的爪子