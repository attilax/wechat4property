/**
 * @author attilax 老哇的爪子
	@since  2014-6-9 下午03:38:14$
 */
package com.attilax;

import java.util.ArrayList;
import java.util.List;
//import org.apache.ecs.xhtml.object;
//import org.apache.ibatis.session.SqlSession;

import com.attilax.util.HibernateSessionFactory;
import com.attilax.util.god;
//import com.foksda.mass.retryRzt;

 /**
 use age:
 wechatUtil. acc_token()
 */
/**
 * @author  attilax 老哇的爪子
 *@since  2014-6-9 下午03:38:14$
 */
public abstract class retryO7<atiType> {

	private int retryTimes;
	// 
	public retryRzt rzt;
	public void setResult(atiType r) {
		// attilax 老哇的爪子  上午12:04:29   2014年6月10日 
		 this.rzt.result=r;
		 this.result=(atiType) r;
		
	}
	/**
	@author attilax 老哇的爪子
		@since  2014-6-9 下午03:44:15$
	
	 * @param kmprsLenMin
	 */@Deprecated
	public retryO7(int retryTimes) {
		this.retryTimes=retryTimes;
	}
	/**
	@author attilax 老哇的爪子
		@since  2014-6-9 下午04:27:15$
	
	 * @param kmprsLenMin
	 * @param li
	 */@Deprecated
	public retryO7(int retryTimes, List li) {
		// TODO Auto-generated constructor stub
		this.retryTimes=retryTimes;
		this.li=li;
	}
	/**
	@author attilax 老哇的爪子
		@since  2014年6月9日 下午11:43:02$
	
	 * @param kmprsLenMin
	 * @param rzt
	 */
	public retryO7(int retryTimes, retryRzt rzt) {
		//  attilax 老哇的爪子 下午11:43:02   2014年6月9日   
		this.retryTimes=retryTimes;
		this.rzt=rzt;
	}
	/**
	@author attilax 老哇的爪子
		@since  2014-6-10 上午09:06:31$
	
	 * @param kmprsLenMin
	 * @param rzt2
	 */
//	public retry(int i, retryRzt rzt2) {
//		// TODO Auto-generated constructor stub
//	}
	List li;
	/**
	@author attilax 老哇的爪子
		@since  2014-6-9 下午03:38:14$
	
	 * @param args
	 */
	public static void main(String[] args) {
		// attilax 老哇的爪子  下午03:38:14   2014-6-9 
	

	}
	//  attilax 老哇的爪子 下午03:38:14   2014-6-9   
	private boolean log4err = false;
	public atiType result;
	private boolean rztFlag;
	private boolean ok;
	private boolean err;
	private Object resetObj;
	
	@Deprecated
	public atiType $() {
		 if(this.li==null)
		return $(new ArrayList<Boolean>());
		 else
			 return $(this.li);
		
	}
	 /**
	  * 
	 @author attilax 老哇的爪子
	 	@since  2014年6月9日 下午11:44:21$
	 
	  * @return
	  */
	public atiType $O69() {
		 if(this.rzt==null)
		return $(new retryRzt());
		 else
			 return $(this.rzt);
		
	}
	
//	private void reset(final SqlSession sess) {
//		new com.attilax.tryX<String>() {		@Override		 		public String $$(Object t) throws Exception {
//			 // attilax 老哇的爪子     l5953 o78
//			core.log("---o79: conn is close ,now startclose session..");
//			 sess.close();
//			 return null;	}	}.$("");
//	}
	public void setResetObj(Object sess) {
		this.resetObj=sess;
		
	}
	/**
	@author attilax 老哇的爪子
		@since  2014年6月9日 下午11:44:49$
	
	 * @param retryRzt
	 * @return
	 */@Deprecated
	private atiType $(retryRzt retryRzt) {
		// attilax 老哇的爪子 下午11:44:49 2014年6月9日
		// return null;
		{
			boolean flag = false;
			for (int i = 0; i < retryTimes; i++) {
				core.log("--retry index:" + String.valueOf(i));
				try {
					flag = item("");
				} catch (Exception e) {
					// this.log4err = true;
					log(e);
					flag = false;
					// throw new RuntimeException(e);
					//if ex ,reest op

				}
				if (flag) break;
				else { // rest etc..close db conn session
					final Object obj = this.resetObj;
					new com.attilax.tryX<String>() {
						@Override
						public String $$(Object t) throws Exception {
							// attilax 老哇的爪子 mc59 o78
							reset(obj);
							return null;
						}
					}.$("");

				}
				// core.sleep(100);
			}
			this.rztFlag = flag;
			this.rzt.rztFlag = flag;
			if (flag) this.rzt.ok = true;
			else this.rzt.err = true;
			// li.add(flag);
			// li.add(this.result)
			return result;

		}
		// {}
		// {}

	}
	@Deprecated
	public atiType $(List li) {
		// attilax 老哇的爪子  下午04:23:50   2014-6-9 
		boolean flag=false;
		for(int i=0;i<retryTimes;i++)
		{
			
			try {
				flag= item("");
			} catch (Exception e) {
			//	this.log4err = true;
				log(e);
				flag= false;
			//	throw new RuntimeException(e);

			}
			if(flag)break;
			else
			{
//				ss.close();
//				ss= HibernateSessionFactory.getSession();
//				core.log("--conn is newopen ,ssid:"+String.valueOf(ss.hashCode()));
			}
//			core.sleep(100);
		}
		li.add(flag);
	//	li.add(this.result)
		return result;
	}
	public abstract Boolean item(Object t) throws Exception;
	/**
	 * with safe catch ex
	@author attilax 老哇的爪子
		@since  2014-9-2 下午05:56:30   
	
	 * @param t
	 */
	public abstract void reset(Object t) ;
	public Object log(Exception e) {
		// MoodUserIndexService.logger.error(god.getTrace(e));
		if (log4err) {
			core.logger.error(god.getTrace(e));
			return e;
		}
		core.logger.warn("-----catch except la ..");
		core.logger.warn(god.getTrace(e));
		return e;
	}
}

//  attilax 老哇的爪子