package com.focustar.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;

import com.focustar.entity.HdBindHistory;
import com.focustar.entity.THDMember;
import com.focustar.entity.THDMemberCostHistory;
import com.focustar.util.BaseImpl;

public class MemberImpl extends BaseImpl {
	
	public void addCostRecord(THDMemberCostHistory history){
		
		Session sess = getSession();
		
		if(sess != null){
			Transaction tx = null;
			try{
				tx = sess.beginTransaction();
				sess.save(history);
				tx.commit();
			}catch(Exception e){
				if(tx != null){
					tx.rollback();
				}
				e.printStackTrace();
			}finally{
				sess.close();
			}
			
		}
		
	}
	
	
	public boolean addBindHistory(HdBindHistory oneHis){
		Session sess = getSession();
		if(sess != null){
		Transaction tx = null;
		try{
			tx = sess.beginTransaction();
			sess.save(oneHis);
			tx.commit();
			return true;
		}catch(Exception e){
			if(tx != null){
				tx.rollback();
			}
			e.printStackTrace();
		}finally{
			sess.close();
		}
		}
		
		return false;
	}
	
	
	
	
	
	public void addCostRecordList(List<THDMemberCostHistory> historyList){
		
		Session sess = getSession();
		
		if(sess != null){
			Transaction tx = null;
			try{
				tx = sess.beginTransaction();
				for(THDMemberCostHistory  oneHis:historyList){
					sess.save(oneHis);
				}
				tx.commit();
			}catch(Exception e){
				if(tx != null){
					tx.rollback();
				}
				e.printStackTrace();
			}finally{
				sess.close();
			}
			
		}
		
	}
	
	
	public List<THDMember>  getBindMemberList(String lastTime,String currentTime){
		Session sess = getSession();
		
		if(sess != null){
			try {
				
				String sql = "select b.cellPhone as cellPhone,a.openid as openId,b.cardNo as cardNo from t_mb_weixinuser a left join t_hd_member b on a.memberId = b.memberId where a.memberId is not null and a.createTime >= ? and  a.createTime <= ? order by a.createTime asc";
				
				Query q = sess.createSQLQuery(sql).addScalar("cellPhone", StandardBasicTypes.STRING)
						      .addScalar("openId", StandardBasicTypes.STRING)
						      .addScalar("cardNo", StandardBasicTypes.STRING)
						      .setResultTransformer(Transformers.aliasToBean(THDMember.class));
				
				//获取上一次请求时间到下一次请求时间之间的绑定数据
				
				q.setString(0, lastTime);
				q.setString(1, currentTime);
				
				return q.list();
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				sess.close();
			}
		}
		
		
		return null;
	}

}
