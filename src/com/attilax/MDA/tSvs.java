/**
 * @author attilax 老哇的爪子
	@since  o7d g59h$
 */
package com.attilax.MDA;
import com.attilax.core;
import com.focustar.elmt.GvMaterialSvs;
import com.focustar.util.HibernateSessionFactory;
 import static  com.attilax.core.*;
import java.util.*;
import java.net.*;
import java.io.*;
/**
 * @author  attilax 老哇的爪子
 *@since  o7d g59h$
 */
public class tSvs {

	/**
	@author attilax 老哇的爪子
		@since  o7d g59h$
	
	 * @param args
	 */
	public static void main(String[] args) {
		// attilax 老哇的爪子  g59h   o7d 
		System.out.println(HibernateSessionFactory.CONFIG_FILE_LOCATION);
		GvMaterialSvs c=new GvMaterialSvs();
		System.out.println(c.findAll(10,"kewword").size());
		System.out.println("---");

	}
	//  attilax 老哇的爪子 g59h   o7d   
}

//  attilax 老哇的爪子