package com.focustar.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.focustar.entity.TMbGroup;
import com.focustar.entity.TMbGroupQrcode;
import com.focustar.util.BaseImpl;

public class GroupImpl extends BaseImpl{
	
	
	/**
	 * @category 获取指定的分公司
	 * @param groupid
	 * @return
	 */
	public TMbGroup selectOneGroup(int groupid){
		
		Session session = getSession();
		if(session != null){
			try{
			String hql = "from TMbGroup where id = "+groupid;
			Query q = session.createQuery(hql);
			TMbGroup one = (TMbGroup) q.uniqueResult();
			return one;
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				session.close();
			}
		}
		return null;
	}
	
	
	public boolean updateGroup(TMbGroup group){
		
		boolean flag = false;
		
		Session session = getSession();
		Transaction tx = null;
		if(session != null){
			try {
				tx = session.beginTransaction();
				session.update(group);
				tx.commit();
				flag = true;
			} catch (Exception e) {
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
	
	/**
	 * @category 获取所有分公司
	 * @return
	 */
	public List<TMbGroup> selectTopGroup(){
		Session session = getSession();
		if(session != null){
			try{
				String hql = "from TMbGroup where parentId = 0";
				Query q = session.createQuery(hql);
				return q.list();
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				session.close();
			}
		}
		return null;
	}
	
	/**
	 * @category 获取最近的门店
	 * @param groupid 暂不使用
	 * @param olat  原始纬度
	 * @param olong  原始经度
	 * @param slat  开始纬度
	 * @param elat  结束纬度
	 * @param slong  开始经度
	 * @param elong   结束经度
	 * @return
	 */
	public List<TMbGroup>  selectGroupNearByDistance(int groupid,double olat,double olong,double slat,double elat,double slong,double elong){
		
		List<TMbGroup>  groupList = new ArrayList<TMbGroup>();
		Session session = getSession();
		if(session != null){
			try{
				
				StringBuilder sqlBuilder = new StringBuilder();
				
				sqlBuilder.append("select top 10 ");
				sqlBuilder.append("groupid,groupname,remark,createtime,parentId,longitude,latitude,");
				sqlBuilder.append("(");
				sqlBuilder.append("6371 * acos(");
					sqlBuilder.append("cos(radians(?)) * cos( radians( latitude ))");//37
					sqlBuilder.append("* cos(");
							sqlBuilder.append("radians( longitude )");
							sqlBuilder.append("- radians(?)");//121
					sqlBuilder.append(")");
					sqlBuilder.append("+ sin( radians(?))");//37
					sqlBuilder.append("* sin( radians( latitude ))");
					sqlBuilder.append(")");
				sqlBuilder.append(") as distance,serverUrl ");
				sqlBuilder.append("from t_mb_group ");
				sqlBuilder.append("where (latitude > ? and latitude < ?) and (longitude > ? and longitude < ?)");
				sqlBuilder.append(" order by distance asc");
				
				Query q = session.createSQLQuery(sqlBuilder.toString()).addEntity(TMbGroup.class);
				q.setParameter(0, olat);
				q.setParameter(1, olong);
				q.setParameter(2, olat);
				q.setParameter(3, slat);
				q.setParameter(4, elat);
				q.setParameter(5, slong);
				q.setParameter(6, elong);
				
				groupList = q.list();
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				session.close();
			}
		}
		return groupList;
	}
	
	
	
	//检查经纬度，查询范围内的门店
	public List<TMbGroup>  selectGroupNearBy(int groupid,double slat,double elat,double slong,double elong){
		
		List<TMbGroup>  groupList = new ArrayList<TMbGroup>();
		Session session = getSession();
		if(session != null){
			try{
				String hql = "from TMbGroup where (latitude > ? and latitude < ?) and (longitude > ? and longitude < ?)";
				Query q = session.createQuery(hql);
				q.setParameter(0, slat);
				q.setParameter(1, elat);
				q.setParameter(2, slong);
				q.setParameter(3, elong);
				q.setMaxResults(10);
				groupList = q.list();
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				session.close();
			}
		}
		return groupList;
	}
	
	
	/**
	 * @category 获取每个分公司下的二维码
	 * @return
	 */
	/*public List<TMbGroupQrcode>  selectGroupQrode(){
		Session sess = getSession();
		if(sess != null){
			try{
				
				
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return null;
	}*/
	
	public boolean updateGroup(List<TMbGroup> updateGroupList){
		
		Session sess = getSession();
		Transaction tx = null;
		if(sess != null){
			try{
				tx = sess.beginTransaction();
				for(TMbGroup one:updateGroupList){
					sess.update(one);
				}
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
	
	public List<TMbGroup> selectGroups(){
		Session sess = getSession();
		
		if(sess != null){
			try{
				String hql = "from TMbGroup where parentId = 1 and createTime < '2014-03-05 00:00:00'";
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
	
	
	/*
	private static TMbGroup Convert2Group(Object[] obj){
		
		if(obj != null){
			TMbGroup newGroup = new TMbGroup();
			if(null != obj[0]){
				newGroup.setGroupid(Integer.parseInt(obj[0].toString()));
			}
			
			if(null != obj[1]){
				newGroup.setGroupname((String)(obj[1]));
			}
			
			if(null != obj[2]){
				newGroup.setRemark((String)obj[2]);
			}
			
			if(null != obj[3]){
				newGroup.setCreatetime((Date) obj[3]);
			}
			
			if(null != obj[4]){
				TMbGroup parent = new TMbGroup();
				parent.setGroupid(Integer.parseInt(obj[4].toString()));
				
				newGroup.setParent(parent);
			}
			
			if(null != obj[5]){
				newGroup.setLatitude(Double.parseDouble(obj[5].toString()));
			}
			
			if(null != obj[6]){
				newGroup.setLongitude(Double.parseDouble(obj[6].toString()));
			}
			
			if(null != obj[7]){
				Double d = Double.parseDouble(obj[7].toString()) * 1000;
				newGroup.setDistance(d.intValue());
			}
			
			return newGroup;
		}
		return null;
	}*/

}
