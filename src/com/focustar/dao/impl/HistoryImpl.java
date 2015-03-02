package com.focustar.dao.impl;


import org.hibernate.Session;
import org.hibernate.Transaction;

import com.focustar.entity.TMbEventHistory;
import com.focustar.entity.TMbNewsHistory;
import com.focustar.entity.TMbSignHistory;
import com.focustar.util.BaseImpl;

public class HistoryImpl extends BaseImpl {
	
	public void addEventHistory(TMbEventHistory event){
		Session sess = getSession();
		Transaction tx = null;
		if(sess != null){
			try{
				tx = sess.beginTransaction();
				sess.save(event);
				tx.commit();
			}catch(Exception e){
				if(tx != null){
					tx.rollback();
				}
				e.printStackTrace();
			}finally{
				if(sess != null){
					sess.close();
				}
			}
		}
		
	}
	
	
	public void addNewsHistory(TMbNewsHistory news){
		Session sess = getSession();
		Transaction tx = null;
		if(sess != null){
			try{
				tx = sess.beginTransaction();
				sess.save(news);
				tx.commit();
			}catch(Exception e){
				if(tx != null){
					tx.rollback();
				}
				e.printStackTrace();
			}finally{
				if(sess != null ){
					sess.close();
				}
			}
		}
	}
	
	
	public void addSignHistory(TMbSignHistory sign){
		Session sess = getSession();
		Transaction tx = null;
		if(sess != null){
			try{
				tx = sess.beginTransaction();
				sess.save(sign);
				tx.commit();
			}catch(Exception e){
				if(tx != null){
					tx.rollback();
				}
				e.printStackTrace();
			}finally{
				if(sess != null ){
					sess.close();
				}
			}
		}
	}

}
