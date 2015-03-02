/**
 * @author attilax 老哇的爪子
	@since  o8l 1_0_45$
 */
package com.attilax.db;
import com.attilax.core;
import com.attilax.api.HandlerChain;
import com.focustar.util.HbX4vod;
 import static  com.attilax.core.*;
import java.util.*;
import java.net.*;
import java.io.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
/**
 * @author  attilax 老哇的爪子
 *@since  o8l 1_0_45$
 */
public class dbx4q {

	public static Session sess;

	/**
	@author attilax 老哇的爪子
		@since  o8l 1_0_45   
	
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// attilax 老哇的爪子  1_0_45   o8l 
		dbx4q.sess=HbX4vod.getSession();
	System.out.println(new dbx4q().handleReq_jsFmt("select top 1 * from t_mb_weixinuser"));	

	}
	
	 public Object handleReq(Object arg) throws Exception {
			// attilax 老哇的爪子  l_43_u   o87 
			
			Query q =sess.createSQLQuery(arg.toString()); 
			 q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
          List li=q.list();
			return li;
			
		}
	 
	 public String handleReq_jsFmt(Object arg) throws Exception {
			// attilax 老哇的爪子  l_43_u   o87 
			
			Query q =sess.createSQLQuery(arg.toString()); 
			 q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
       List li=q.list();
			return  core.toJsonStrO88(li);
			
		}
	//  attilax 老哇的爪子 1_0_45   o8l   
}

//  attilax 老哇的爪子