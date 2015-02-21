/**
 * @author attilax 老哇的爪子
\t@since  Jul 20, 2014 10:22:57 AM$
 */
package com.attilax.MDA;
import com.attilax.core;
import com.attilax.anno.Conditional;
import com.attilax.anno.displayType;
import com.attilax.ref.cantFindMatchFieldException;
import com.attilax.ref.refx;

import static  com.attilax.core.*;

import java.util.*;
import java.lang.reflect.Field;
import java.net.*;
import java.io.*;
/**
 * @author  attilax 老哇的爪子
 *@since  Jul 20, 2014 10:22:57 AM$
 */
public class MdaUtil {

	/**
	@author attilax 老哇的爪子
	\t@since  Jul 20, 2014 10:23:04 AM$
	
	 * @param fldName
	 * @param saveObjClass
	 * @return
	 * @throws cantFindMatchFieldException 
	 * @throws CantFindConditionalExcept 
	 * @throws CantFindConverterExcept 
	 */
	public static boolean isDsplyRange(String fldName, Class<?> saveObjClass) throws  cantFindMatchFieldException, CantFindConditionalExcept {
		// attilax 老哇的爪子  10:23:04 AM   Jul 20, 2014 
		
		{ 
			 Conditional cdt=getConditional(fldName,saveObjClass);
			 if(cdt==null)
				 throw new CantFindConditionalExcept();
			 
		return cdt.displayType().equals(displayType.rang);
		 } 
		
		
	}
	
	public static boolean isDsplyRange_RE(String fldName, Class<?> saveObjClass)   {
		// attilax 老哇的爪子  10:23:04 AM   Jul 20, 2014 
		
	 
			 Conditional cdt = null;
			try {
				cdt = getConditional(fldName,saveObjClass);
			} catch (cantFindMatchFieldException e) {
				//  attilax 老哇的爪子 l_37_46   o07   
				e.printStackTrace();
				 throw new RuntimeException("cantFindMatchFieldException");
			}
			 if(cdt==null)
				 throw new RuntimeException("CantFindConditionalExcept");
			 
		return cdt.displayType().equals(displayType.rang);
		 
		
		
	}
	
	public static Conditional   getConditional(String fldName,Class<?> saveObjClass) throws  cantFindMatchFieldException  {
		// attilax 老哇的爪子  7:31:58 AM   Jul 20, 2014 
		 
			Field fld=refx.getField(fldName, saveObjClass);
					//this.saveObjClass.getDeclaredField(fldName);
			Conditional c=fld.getAnnotation(Conditional.class);
		return	 c;
 
		 
		
		
	}
	
	private org.apache.commons.beanutils.Converter getConvert(String fldName,Class<?> saveObjClass) throws CantFindConverterExcept, cantFindMatchFieldException  {
		// attilax 老哇的爪子  7:31:58 AM   Jul 20, 2014 
		 
			Field fld=refx.getField(fldName, saveObjClass);
					//this.saveObjClass.getDeclaredField(fldName);
			
		return	 core.getConverter(fld);
 
		 
		
		
	}
	//  attilax 老哇的爪子 10:22:57 AM   Jul 20, 2014   
}

//  attilax 老哇的爪子