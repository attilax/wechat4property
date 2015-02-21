package com.focustar.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.focustar.entity.TMbTask;
import com.focustar.util.BaseImpl;

public class TaskImpl extends BaseImpl {
	
	public boolean addTask(TMbTask task){
		Session session = getSession();
		 Transaction tx = null;
		 boolean flag = false;
		if(session != null){
			
			try{
				tx = session.beginTransaction();
				session.save(task);
				tx.commit();
				flag = true;
			}catch(Exception e){
				if(tx != null){
					tx.rollback();
				}
				e.printStackTrace();
			}finally{
				session.close();
			}
			
		}
		return flag;
	}
	
	public TMbTask getTaskById(int id){
		TMbTask task = null;
		Session session = getSession();
		
		if(session != null){
			try{
				
				task  = (TMbTask) session.get(TMbTask.class,id);
				
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				session.close();
			}
			
		}
		return task;
	}
	
	
	public int getUserKeyWord(String openId,String keyword){
		
		Session sess = getSession();
		
		if(sess != null){
			try{
				String sql = "select count(*) from t_mb_task tt where tt.openid = ? and tt.msgType = 0 and tt.msgContent = ? ";
				Query q = sess.createSQLQuery(sql);
				q.setParameter(0, openId);
				q.setParameter(1, keyword);
				
				Object res = q.uniqueResult();
				
				return Integer.parseInt(res.toString());
				
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				sess.close();
			}
			
		}
		
		return 0;
		
		
	}
	
	

}
