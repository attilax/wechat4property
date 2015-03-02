package com.attilax.db;

import com.focustar.util.HibernateSessionFactory;
import org.hibernate.Session;


/**
 * Data access object (DAO) for domain model
 * @author MyEclipse Persistence Tools
 */
 
public class BaseHibernateDAO2  {
	
	public static HibernateSessionFactory hbntSessFktr;
 	public Session getSession() {
 		return hbntSessFktr.getSession();
 	}
	
}