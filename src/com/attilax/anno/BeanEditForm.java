/**
 * @author attilax 老哇的爪子
\t@since  Jul 19, 2014 3:56:49 AM$
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
 *@since  Jul 19, 2014 3:56:49 AM$
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface BeanEditForm {

	String tmplt();

	// attilax 老哇的爪子 3:56:49 AM Jul 19, 2014
	/**
	 * A comma-separated list of property names to be added to the
	 * org.apache.tapestry5.beaneditor.BeanModel (only used when a default model
	 * is created automatically).
	 * 
	 * @author attilax 老哇的爪子 \t@since Jul 19, 2014 4:00:04 AM$
	 * 
	 * @return
	 */
	String add() default "";
	/**
	 * If set to true, then the form will include an additional button after the submit button labeled "Cancel". The cancel button will submit the form, bypassing client-side validation. The BeanEditForm will fire a org.apache.tapestry5.EventConstants#CANCELED event (before the form's org.apache.tapestry5.EventConstants#VALIDATE event).
	@author attilax 老哇的爪子
	\t@since  Jul 19, 2014 4:01:19 AM$
	
	 * @return
	 */
	boolean cancel() default false;
	String submitLabel() default "提交";
	String cancelLable() default "取消";
	/**	A comma-separated list of property names indicating the order in which the properties should be presented. The names are case insensitive. Any properties not indicated in the list will be appended to the end of the display orde. Only used when a default model is created automatically.*/
	String reorder()  default "";

}

//  attilax 老哇的爪子