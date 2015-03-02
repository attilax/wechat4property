/**
 * 
 */
package com.attilax.db;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import com.attilax.ioc.IocX;
import com.attilax.persistence.PX;
import com.attilax.util.HibernateSessionFactory;

/**
 * @author ASIMO
 *
 */
public class connLeakTest {

	/**
	@author attilax 老哇的爪子
	@since   oaa a_p_1
	 
	 */
	 @SuppressWarnings("all")
	public static void main(String[] args) {
	      PX px=IocX.getBean(PX.class);
	

	 String sql=" select top 10 * from gv_materialxx ";
	List li=new ArrayList ();
	for(int i=0;i<200;i++)
	{
		Session s=   HibernateSessionFactory.sessionFactory. openSession();
		 System.out.println("---sessid:"+String.valueOf( s.hashCode()));
		List li2=  s.createSQLQuery(sql).list();
		li.add(li2);
		li.add(s);
		
	}
		System.out.println("===");	
			

	}

}
