/**
 * 
 */
package com.attilax.anno;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author ASIMO
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Orderby {

	String value() default "asc";

	int order() default 1;

}
