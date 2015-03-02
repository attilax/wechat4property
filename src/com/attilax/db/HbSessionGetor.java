/**
 * @author attilax 老哇的爪子
	@since  o9f 4_o_o$
 */
package com.attilax.db;
import com.attilax.Closure;
import com.attilax.core;
import com.attilax.retryO7;
import com.attilax.io.filex;
import com.attilax.ioc.IocX;
import com.attilax.persistence.Hbx;
import com.attilax.util.HibernateSessionFactory;
import com.attilax.util.god;
import com.focustar.ServiceLoctor4vod;
import com.focustar.entity.TUserUsers;
import com.foksda.mass.retryRzt;
import com.google.inject.Inject;
import com.google.inject.name.Named;
 import static  com.attilax.core.*;
import java.util.*;
import java.net.*;
import java.io.*;
import org.hibernate.Query;
import org.hibernate.Session;
/**
 * @author  attilax 老哇的爪子
 *@since  o9f 4_o_o$
 */
public class HbSessionGetor {

	/**
	@author attilax 老哇的爪子
		@since  o9f 4_o_p   
	
	 * @param args
	 */
	public static void main(String[] args) {
		// attilax 老哇的爪子  4_o_p   o9f 
		ServiceLoctor4vod.inidb();;
		HbSessionGetor sessGtr = IocX.getBean(HbSessionGetor.class);
		System.out.println("tst::"+sessGtr.testHql);
//		Session sess=  sessGtr.getSession(new Closure() {
//
//			@Override public Object execute(Object arg0) throws Exception {
//				// attilax 老哇的爪子  4_v_y   o9f 
//				return HibernateSessionFactory.sessionFactory.getCurrentSession();
//				
//			}});
//		core.print_wzFmt(sess.get(TUserUsers.class, 20));
		System.out.println("--f");

	}
	//  attilax 老哇的爪子 4_o_o   o9f
	@Inject @Named("thql")
	public String testHql;
	@SuppressWarnings("all") 
	public   Session getSession(final Closure getSessx) {
		// attilax 老哇的爪子 i4148 o78
		retryRzt rzt = new retryRzt();
		return new retryO7<Session>(5, rzt) {

			@Override
			public Boolean item(Object t) throws Exception {
				// attilax 老哇的爪子 下午11:49:37 2014年6月9日
				final Session sess =(Session) getSessx.execute(null);//HibernateSessionFactory.getSession();
				if(sess==null)
					{
					filex.save_SF("sess is null", "c:\\pubSaveWarn"+filex.getUUidName()+".txt");
					
					return false;
					
					}
				if(!sess.isOpen())
					{
					filex.save_SF("sess is nnot open", "c:\\pubSaveWarn"+filex.getUUidName()+".txt");
					
					return false; //o99
					}
				this.setResetObj(sess);

				try {
					Query query = sess.createQuery(testHql);		 
					query.setMaxResults(1);
					List li =query.list();
					// core.ex4test();

					if (li.size() >= 0) {
						this.setResult(sess);
						return true;  //ret suc flag
					}
					return false;
				} catch (Exception e) {
					filex.save_SF(god.getTrace(e), "c:\\pubSaveWarn"+filex.getUUidName()+".txt");					
					core.warn(e);
					throw new RuntimeException(e);
				}
			

				

			}
			// return null;

			@Override
			public void reset(final Object sessObj) {

				core.log("---o79: conn is close ,now startclose session..");
				 Session sess = (Session) sessObj;
				Hbx.closeSession(sess);

			}
		}.$O69();
	}

}

//  attilax 老哇的爪子