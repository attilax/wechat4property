package com.attilax.anno;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * from tapestry5
 * @author Administrator
 *
 */

//@Target(value={FIELD,METHOD})
@Target(value = {ElementType.METHOD, ElementType.FIELD})

@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented

 @UseWith(value={WidthType.NORMAL,WidthType.CUSTOM})
//@UseWith(value={ COMPONENT,MIXIN,PAGE})
public @interface Validate {
	// java.lang.annotation.Retention
	
	String value() default "";
	String regexp() default "";
	
	String regexp_message() default "";  //bnen use splitChar-,only low splitChar_
	String title()  default "";
	int minlength() default 0;
	int maxlength() default 0;
	int max() default 0;
	int min() default 0;
	String type()default "";
	String msg() default "";
	

}
