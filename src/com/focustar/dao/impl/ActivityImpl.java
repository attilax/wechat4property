package com.focustar.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.focustar.entity.ActAward;
import com.focustar.entity.Activity;
import com.focustar.entity.AwardWeixin;
import com.focustar.entity.TMbActivityHistory;
import com.focustar.entity.TMbShare2another;
import com.focustar.util.BaseImpl;

public class ActivityImpl extends BaseImpl {
	
	
	private final static Logger log = Logger.getLogger(ActivityImpl.class);

	
	public TMbShare2another getSharebyId(int pid){
		
		Session sess = getSession();
		
		TMbShare2another shareit  = null;
		
		if(sess != null){
			try {
				shareit = (TMbShare2another) sess.get(TMbShare2another.class,pid);
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				sess.close();
			}
		}
		
		return shareit;
	}
	
	public boolean updateAwardWeixin(AwardWeixin updateAwx){
		boolean flag = false;
		
		Session sess = getSession();
		Transaction tx = null;
		if(sess != null){
			try{
				tx = sess.beginTransaction();
				sess.update(updateAwx);
				tx.commit();
				flag = true;
			}catch(Exception e){
				if(tx != null){
					tx.rollback();
				}
				e.printStackTrace();
			}finally{
				sess.close();
			}
		}
		
		return flag;
	}
	
	
	public boolean addAwardWeixin(AwardWeixin newWx){
		boolean flag = false;
		Session sess =getSession();
		Transaction tx = null;
		if(sess != null){
			try {
				tx = sess.beginTransaction();
				
				sess.save(newWx);
				
				tx.commit();
				
				flag = true;
			} catch (Exception e) {
				if(tx != null){
					tx.rollback();
				}
				e.printStackTrace();
			}finally{
				sess.close();
			}
		}
		return flag;
	}
	
	
	public Activity  getOneActivity(int activityId){
		
		Session sess = getSession();
		if(sess != null){
			try{
				String hql = "from Activity where id = ?";
				
				Query q = sess.createQuery(hql);
				q.setParameter(0, activityId);
				
				return (Activity) q.uniqueResult();
				
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				sess.close();
			}
		}
		
		return null;
	}
	
	
	/**
	 * @category 获取当前执行的活动
	 * @return
	 */
	public Activity getCurrentActivity(){
		
		Session sess = getSession();
		if(sess != null){
			try{
				//是否加入时间范围检查
				//Date d = new Date();
				String hql = "from Activity where flag = 1";
				Query q = sess.createQuery(hql);
				q.setMaxResults(1);

				return (Activity) q.uniqueResult();
				
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				sess.close();
			}
		}
		return null;
	}
	
	
	/**
	 * @category 获取可用的活动列表
	 * @return
	 */
	public List<Activity> getActivityList(){
		
		Session sess = getSession();
		if(sess != null){
			try{
				//是否加入时间范围检查
				//Date d = new Date();
				String hql = "from Activity where flag = 1";
				Query q = sess.createQuery(hql);
				return q.list();
				
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				sess.close();
			}
		}
		return null;
	}
	
	
	public AwardWeixin getAwardWeixinByOpenid(int activityId,String openId){
		
		Session sess = getSession();
		
		if(sess != null){
			try {
				
				String hql = "from AwardWeixin where activity.id = ? and openId = ?";
				Query q = sess.createQuery(hql);
				q.setParameter(0, activityId);
				q.setParameter(1, openId);
				q.setMaxResults(1);
				
				return (AwardWeixin)q.uniqueResult();
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				sess.close();
			}
		}
		
		return null;
	}
	
	/**
	 * @category  获取指定活动每天的抽奖记录
	 * @param openId
	 * @return
	 */
	public AwardWeixin  getAwardWeixinByDay(int activityId,String openId){
		
		Session sess = getSession();
		
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		GregorianCalendar cal =new GregorianCalendar();
		cal.setTime(d);
		cal.set(Calendar.HOUR,0);
		cal.set(Calendar.MINUTE,0);
		cal.set(Calendar.SECOND,0);
		cal.set(Calendar.MILLISECOND, 0);
		
		if(sess != null){
			try{
				
				//String hql = "from AwardWeixin where activity.id = ? and  openId = ? and (createTime >= ? and createTime <=?)";
				
				String sql = "select * from t_mb_awardweixin where activityId = ? and openId = ? and convert(varchar(10), createTime, 120 )  = ?";
				
				Query q = sess.createSQLQuery(sql);
				q.setParameter(0, activityId);
				q.setParameter(1, openId);
				q.setParameter(2, sdf.format(cal.getTime()));
				q.setMaxResults(1);
				
				Object[] result = (Object[])q.uniqueResult();
				
				if(result != null && result.length > 0){
					AwardWeixin aw = new AwardWeixin();
					
					//0 id
					if(result[0] != null){
						Integer id = Integer.parseInt(result[0].toString());
						aw.setId(id);
					}
					//1 activityId
					if(result[1] != null){
						Integer curActId = Integer.parseInt(result[1].toString());
						Activity curAct = new Activity();
						curAct.setId(curActId);
						aw.setActivity(curAct);
					}
					//2 awardId
					if(result[2] != null){
						Integer curAwId = Integer.parseInt(result[2].toString());
						ActAward curAw = new ActAward();
						curAw.setId(curAwId);
						aw.setAward(curAw);
					}
					//3 openId
					if(result[3]!= null){
						aw.setOpenId(result[3].toString());
					}
					//4 awardTime
					if(result[4] != null){
						Date awdTime = (Date)result[4];
						aw.setAwardTime(awdTime);
					}
					//5 awardCount
					if(result[5] != null){
						Integer awCount = Integer.parseInt(result[5].toString());
						aw.setAwardCount(awCount);
					}
					//6 createtime
					if(result[6] != null){
						Date ctDate = (Date)result[6];
						aw.setCreateTime(ctDate);
					}
				
					return aw;
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				sess.close();
			}
		}
		
		return null;
	}
	
	public boolean  addActivityHistory(TMbActivityHistory history){
		boolean flag = false;
		Session sess = getSession();
		Transaction tx = null;
		if(sess != null){
			try{
				//tx = sess.beginTransaction();
				sess.save(history);
				//tx.commit();
				flag = true;
			}
			catch(Exception e){
				if(tx != null){
					//tx.rollback();
				}
				e.printStackTrace();
			}finally{
				sess.close();
			}
		}
		
		return flag;
	}
	
	/**
	 * 
	@author attilax 老哇的爪子
		@since  2014-6-4 上午10:54:15$
	
	 * @param history
	 * @return
	 */
	public boolean  addActivityHistory4ati(TMbActivityHistory history){
		boolean flag = false;
		Session sess = getSession();
		Transaction tx = null;
		if(sess != null){
			try{
				 tx = sess.beginTransaction();
				sess.save(history);
				 tx.commit();
				flag = true;
			}
			catch(Exception e){
				if(tx != null){
					//tx.rollback();
				}
				e.printStackTrace();
			}finally{
				sess.close();
			}
		}
		
		return flag;
	}
	
}
