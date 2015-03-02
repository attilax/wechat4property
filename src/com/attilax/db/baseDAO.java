package com.attilax.db;

import com.attilax.Closure;
import com.attilax.core;
import com.attilax.retryO7;
import com.attilax.collection.CollectionUtils;
import com.attilax.concur.dataISEmptyEx;
import com.attilax.io.filex;
import com.attilax.persistence.Hbx;
import com.attilax.ref.cantFindIDFieldEx;
import com.attilax.ref.cantFindMatchFieldException;
import com.attilax.ref.refx;
import com.attilax.util.HibernateSessionFactory;
import com.focustar.dao.impl.EquipmentDAOImpl;
import com.focustar.downtask.GvDownloadTask;
 
import com.focustar.pojo.Equipment;
import com.focustar.util.HbX4vod;
import com.foksda.mass.retryRzt;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Id;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.DataException;
import static org.hibernate.criterion.Example.create;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * recomm dbx <p> \r\n
 	* A data access object (DAO) providing persistence and search support for GvPlayRecord entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.focustar.playRec.GvPlayRecord
  * @author MyEclipse Persistence Tools 
 */
 @Deprecated 
public class baseDAO    {
	     private static final Logger log = LoggerFactory.getLogger(baseDAO.class);
		//property constants
	public static final String SCREEN = "screen";
	public static final String PUBLISH_TYPE = "publishType";
	public static final String MATERIAL_ID = "materialId";
	public static final String EQUIPMENT_ID = "equipmentId";

public static void main(String[] args) {
	String hql = "from Equipment e where 1=1 order by equipmentId desc  ";
	final EquipmentDAOImpl equipmentDAOImpl = new EquipmentDAOImpl();
	Equipment e = (Equipment) equipmentDAOImpl.getTop(hql, 1, HbX4vod.getSession());
	System.out.println(e.getEquipmentId());

}

public   Session getSession() {
	// attilax 老哇的爪子 i4148 o78
	retryRzt rzt = new retryRzt();
	return new retryO7<Session>(5, rzt) {

		@Override
		public Boolean item(Object t) throws Exception {
			// attilax 老哇的爪子 下午11:49:37 2014年6月9日
			final Session sess =HibernateSessionFactory.getSession();
			if(sess==null)return false;
			this.setResetObj(sess);

			Query query = sess.createQuery(" from  GvCycleQueue ");		 
			query.setMaxResults(1);
			List li =query.list();
			// core.ex4test();

			if (li.size() >= 0) {
				this.setResult(sess);
				return true;  //ret suc flag
			}

			return false;

		}
		// return null;

		@Override
		public void reset(final Object sessObj) {

			core.log("---o79: conn is close ,now startclose session..");
			 Session sess = (Session) sessObj;
			 if(sess!=null)
				 sess.close();

		}
	}.$O69();
}
@Deprecated
public Session session;
public baseDAO(Session session) {
	//  attilax 老哇的爪子 7:18:37 AM   Aug 8, 2014   
	this.session=session;
}
    
     /**
@author attilax 老哇的爪子
\t@since  Aug 30, 2014 8:02:34 AM$

 */
public baseDAO() {
	//  attilax 老哇的爪子 8:02:34 AM   Aug 30, 2014   
}

	public void save(Object transientInstance) {
	 // synchronized( baseDAO.class)
	  {
        log.debug("saving Object instance");
        Session session = null ;
        try {
        	session= getSession();
        	
        	  
               System.out.println("--4save get sess:"+String.valueOf(session.hashCode()));
        	Transaction tx = session.beginTransaction();
        	session.save(transientInstance);
        	
        	tx.commit();
            log.debug("save successful");
        } 
        catch(DataException de)
        {
        	if(de.getMessage().contains("截断字符串或二进制数据"))
        	{
        		core.log(de);
        	}
        	//return transientInstance;
        }
    	 
    	 catch (Exception re) {
            log.error("merge failed", re);
            try {
            	if(session!=null)
            	session.close();
			} catch (Exception e) {
				core.log(e);
			}
            throw new RuntimeException(re);
        }
        
        
	  }
    }
    /**
     * 
    @author attilax 老哇的爪子
    	@since  o7n h_44_c$
    
     * @param hql
     * @param num
     * @param session
     * @return
     */
	public Object getTop(String hql, int num, Session session) {

		Query query =  getSession().createQuery(hql.toString());

		query.setMaxResults(num);
		List li=query.list();
		if (num == 1)
		{
			if(li.size()==0)
				return null;
			return li.get(0);
		}
		else
			return query.list();
	}
	
	public <t> t getTop(String hql, int num ) throws EmptyEx {

		Query query = getSession().createQuery(hql.toString());

		query.setMaxResults(num);
		if (num == 1)
			try{
			return (t) query.list().get(0);
			}catch (IndexOutOfBoundsException e) {
			 throw new EmptyEx();
			}
		else
			return (t) query.list();
	}
	
public	static ThreadLocal<Session> sessThrdloc=new ThreadLocal<Session>();
//	public Session getSession() {
//		if(this.session==null)
//			return sessThrdloc.get();
//		return  this.session;
//	}
	public void delete(Object persistentInstance) {
        log.debug("deleting   instance");
        try {
        	Session session = getSession();
        	Transaction tx = session.beginTransaction();
        	session.delete(persistentInstance);
        	
        	tx.commit();
           
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    /**
     * 
    @author attilax 老哇的爪子
    	@since  o7t h_47_8$
    
     * @param cls
     * @param id
     * @return
     */
    public Object findById(Class<?> cls,java.lang.Integer id) {
        log.debug("getting   instance with id: " + id);
        try {
            Object instance =   getSession()
                    .get(cls, id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    
    /**
	@author attilax 老哇的爪子
		@since  o8r k_43_43   
	
	 * @param string
	 * @return
	 */
	public List findByIds(final Class<?> cls,String idsx) {
		// attilax 老哇的爪子  k_43_43   o8r 
		String[] ids=idsx.split(",");
		List li=Arrays.asList(ids);
		return (List) CollectionUtils.each_safe(li, new Closure(){

			@Override public Object execute(Object arg0) throws Exception {
				// attilax 老哇的爪子  h572   o7g 
				
				Integer id =Integer.parseInt(arg0.toString());
				Object o=findById(cls,id);
				return o;
				 
				
			}});
	//	return null;
		
	}
    /**
     * 
    @author attilax 老哇的爪子
    	@since  o7t h_47_5$
    
     * @param cls
     * @param id
     * @return
     */
    public Object get(Class<?> cls,java.lang.Integer id) {
        log.debug("getting   instance with id: " + id);
        try {
            Object instance =   getSession()
                    .get(cls, id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
//    public List<Object> findByExample(Object instance) {
//        log.debug("finding Object instance by example");
//        try {
//            List<Object> results = (List<Object>) getSession()
//                    .createCriteria("com.focustar.playRec.Object")
//                    .add( create(instance) )
//            .list();
//            log.debug("find by example successful, result size: " + results.size());
//            return results;
//        } catch (RuntimeException re) {
//            log.error("find by example failed", re);
//            throw re;
//        }
//    }    
//    
        public List findByProperty(Class<?> cls,String propertyName, Object value) {
    	// synchronized( baseDAO.class)
   	  {
      log.debug("finding Object instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from "+cls.getName()+" as model where model." 
         						+ propertyName + "= ?";
         core.log(queryString);
         Session s=getSession();
         System.out.println("--get sess:"+String.valueOf(s.hashCode()));
         Query queryObject = s.createQuery(queryString);
		 queryObject.setParameter(0, value);
		 List list = queryObject.list();
		// getSession().close();
		return list;
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
   	  }
	}
    
	public int maxID(Class<?> o) throws cantFindIDFieldEx, dataISEmptyEx {

		String idfld;
		try {
			idfld = refx.getIdFld_EX(o).getName();
		} catch (cantFindMatchFieldException e) {
			// attilax 老哇的爪子 j_57_q o8o
			throw new cantFindIDFieldEx();
		}
		String cls = o.getName();
		Integer c = (Integer) getSession().createQuery("select max(a." + idfld + ") from " + cls + " a ").uniqueResult();
		if (c == null) throw new dataISEmptyEx();
		return c;
	}
    
    public List findByProperty(String propertyName, Object value) {
      log.debug("finding Object instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from Object as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

    public Object findByProperty_singleObj(Class cls,String propertyName, Object value) throws Exception {
        log.debug("finding Object instance with property: " + propertyName
              + ", value: " + value);
        try {
           String queryString = "from "+cls.getCanonicalName()+" as model where model." 
           						+ propertyName + "= ?";
           Session s=getSession();
           System.out.println("--get sess:"+String.valueOf(s.hashCode()));
           Query queryObject = s.createQuery(queryString);
  		 queryObject.setParameter(0, value );
  		 List list = queryObject.list();
  		 if(list.size()==0) return null;
		return list.get(0);
        } catch (Exception re) {
           log.error("find by property name failed", re);
           throw re;
        }
  	}

	public List<Object> findByScreen(Object screen
	) {
		return findByProperty(SCREEN, screen
		);
	}
	
	public List<Object> findByPublishType(Object publishType
	) {
		return findByProperty(PUBLISH_TYPE, publishType
		);
	}
	
	 

	public List findAll() {
		log.debug("finding all Object instances");
		try {
			String queryString = "from Object";
	         Query queryObject = getSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public Object merge(Object detachedInstance) {
		Session session = null;
		try {
			session = getSession();
			Transaction tx = session.beginTransaction();
			Object r = session.merge(detachedInstance);

			tx.commit();
			log.debug("merging   instance");

			return r;
		} catch (DataException de) {
			if (de.getMessage().contains("截断字符串或二进制数据")) {
				core.log(de);
			}
			return detachedInstance;
		}
		catch (Exception re) {
			log.error("merge failed", re);
			throw new RuntimeException(re);
		} finally {
			Hbx.closeSession(session);
		}
	}

    public void attachDirty(Object instance) {
        log.debug("attaching dirty Object instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
  synchronized  public void attachClean(Object instance) {
        log.debug("attaching clean Object instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

	/**
	@author attilax 老哇的爪子
	\t@since  Aug 30, 2014 8:09:36 AM$
	
	 * @param hql
	 * @return
	 */
	public GvDownloadTask getTopOne(String hql) {
		// attilax 老哇的爪子  8:09:36 AM   Aug 30, 2014 
		
		{ 
		return (GvDownloadTask) getTop(hql, 1, getSession());
		 } 
		
		
	}

	/**
	@author attilax 老哇的爪子
		@since  o8t 0_4_45   
	
	 * @param t
	 */
	public   void  merge_syn(Object t) {
		// attilax 老哇的爪子  0_4_45   o8t 
		synchronized (baseDAO.class) {
			merge(t);
		}
		
	}


	/**
	@author attilax 老哇的爪子
	\t@since  Aug 30, 2014 10:10:35 PM$
	
	 * @param string
	 * @return
	 */
	public  <t> List<t> findByHql(String hql) {
		// attilax 老哇的爪子  10:10:35 PM   Aug 30, 2014 
		
		{ 
		try {
			return getTop(hql, 888);
		} catch (EmptyEx e) {
			//  attilax 老哇的爪子 10:11:23 PM   Aug 30, 2014   
			e.printStackTrace();
		}
		 }
		return new ArrayList<t>(); 
		
		
	}
	
	public List getOverdue(Integer progarmmeId, String endTime) {
		String sql = "select m.material_id,m.material_description,m.failure_time from gv_programme_detail p,gv_material m where m.failure_time<'"+endTime+"' and p.material_id=m.material_id and p.programme_id="+progarmmeId+"";
		Query query = getSession().createSQLQuery(sql);
		List list = query.list();
		List rsList = new ArrayList();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Object[] obj = (Object[]) iterator.next();
			Map m = new HashMap();
			m.put("material_id", String.valueOf(obj[0]));
			m.put("material_description", String.valueOf(obj[1]));
			m.put("failure_time", String.valueOf(obj[2]).substring(0,19));
			rsList.add(m);
		}
		return rsList;
	}
	
	
}