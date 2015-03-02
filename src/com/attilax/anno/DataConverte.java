///**
// * @author attilax 老哇的爪子
//	@since  o7g Z56x$
// */
//package com.attilax.anno;
//import com.attilax.ClosureNoExcpt;
//import com.attilax.core;
//import com.attilax.convert.defConvert;
// 
// import static  com.attilax.core.*;
//import java.util.*;
//import java.lang.annotation.Documented;
//import java.lang.annotation.Inherited;
//import java.lang.annotation.Retention;
//import java.lang.annotation.RetentionPolicy;
//import java.net.*;
//import java.io.*;
///**
// * @author  attilax 老哇的爪子
// *@since  o7g Z56x$
// */@Retention(RetentionPolicy.RUNTIME)
// @Inherited
// @Documented
//public @interface DataConverte {
//
//	Class<?> converter() default defConvert.class;
//	//  attilax 老哇的爪子 Z56x   o7g   
//
//	int nullVal() default 0;
////   if convert (from ui val) 2 db val fail or empty/null   g48m o7g  老哇的爪子  Attilax
//	int defValInt() default 0;
//
//	String defValStr() default "";
//
//	boolean defVal4OtherTypeAsNull() default true;
//
//	boolean valideFailNotAdd_Querymode() default true;
//}
//
////  attilax 老哇的爪子