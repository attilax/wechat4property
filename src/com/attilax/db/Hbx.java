package com.attilax.db;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.internal.SessionImpl;

import com.attilax.core;
import com.attilax.util.HibernateSessionFactory;
import com.focustar.util.BaseImpl;

public class Hbx {

	public static boolean isMysql() {
		String dialect = ((SessionImpl) HibernateSessionFactory.getSession()).getFactory().getDialect()
				.getClass().getName();
		// org.hibernate.dialect.MySQLDialect
		System.out.println(dialect);
		if (dialect.contains("MySQLDialect"))
			return true;
		else
			return false;
	//	return false;
	}
	
	private boolean ifMysql() {
		// attilax 老哇的爪子 上午10:24:49 2014年5月10日
//		String dialect = ((SessionImpl) getSession()).getFactory().getDialect()
//				.getClass().getName();
//		// org.hibernate.dialect.MySQLDialect
//		System.out.println(dialect);
//		if (dialect.contains("MySQLDialect"))
//			return true;
//		else
			return false;
	}
	public	Session session;
	public void save(Object o) {
		// attilax 老哇的爪子  6:21:28 AM   Sep 3, 2014 
		
		// log.debug("saving GvPlayRecord instance");
	        try {
	        //	Session session = getSession();
	        	Transaction tx = session.beginTransaction();
	        	session.save(o);
	        	
	        	tx.commit();
	         //   log.debug("save successful");
	        } catch (RuntimeException re) {
	           // log.error("save failed", re);
	        	closeSession(session);
	            throw re;
	        }
		
		
	}
	
	public static void closeSession(Session session) {
		// attilax 老哇的爪子  i_g_58   o90 
		if(session!=null)
		{
			try {
				session.close();
			} catch (Exception e) {
				core.log(e);
			}
		}
		
	}


}
