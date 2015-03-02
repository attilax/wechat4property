package com.focustar.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.focustar.entity.TMbTag;
import com.focustar.util.BaseImpl;

public class TagImpl extends BaseImpl{
	
	public List<TMbTag>  selectTagByGroupid(int groupid){
		
		Session sess = getSession();
		
		if(sess != null){
			try{
				String hql = "from TMbTag where groupid = ?";
				Query q = sess.createQuery(hql);
				q.setParameter(0, groupid);
				
				return q.list();
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				sess.close();
			}
		}
		
		return null;
	}
	
}
