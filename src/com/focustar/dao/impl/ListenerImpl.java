package com.focustar.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.focustar.entity.TMbWeixinuser;
import com.focustar.util.BaseImpl;

public class ListenerImpl extends BaseImpl {
	
	private static Logger logger = Logger.getLogger(ListenerImpl.class);

	/**
	 * 微信-插入用户记录
	 * 
	 * @param wxUserList
	 *            存放用户的集合
	 * @throws Exception
	 *             保存失败时抛出异常
	 */
	public boolean insertWeiXinUser(List<TMbWeixinuser> wxUserList) throws Exception {
		boolean flag = false;
		Transaction tx = null;
		Session session = getSession();
		if(session != null){
			try {
				tx = session.beginTransaction();
				Iterator<TMbWeixinuser> it = wxUserList.iterator();
				while (it.hasNext()) {
					TMbWeixinuser det = it.next();
					session.save(det);
				}
				tx.commit();
				flag = true;
			}catch (Exception e) {
				if(tx != null){
					tx.rollback();
				}
				e.printStackTrace();
				logger.info("微信粉丝记录保存失败，请检查字段类型、长度、数据是否规范！" + e.getMessage());
				//throw new Exception("微信粉丝记录保存失败，请检查字段类型、长度、数据是否规范！" + e.getMessage());
			}finally{
				if(session != null && session.isOpen()){
					session.close();
				}
			}
		}
		
		return flag;
	}

	/**
	 * 获取数据库中最近关注的粉丝openid
	 * 
	 * @return
	 */
	public String getMaxTimeOpenid() {
		String openid = "";
		Session session = getSession();
			if(session != null){
			try {
				String hql = "select t.openid from t_mb_weixinuser t where t.subscribe_time=(select MAX(d.subscribe_time) from t_mb_weixinuser d) ";
				Query query = getSession().createSQLQuery(hql);
				List<String> detail = query.list();
				if (detail != null && detail.size() > 0) {
					Iterator<String> it = detail.iterator();
					while (it.hasNext()) {
						openid = it.next();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.info(e.getMessage());
			}finally{
				if(session != null && session.isOpen()){
						session.close();
				}
			}
		}
		return openid;
	}
	
	/**
	 * @category 只更新 用户信息，不清除会员相关信息
	 * @param updateUser
	 */
	public void updateUserInfo(TMbWeixinuser updateUser){
		Transaction tx = null;
		Session session = getSession();
		if(session != null){
			try{
				tx = session.beginTransaction();
				session.update(updateUser);
				tx.commit();
			}catch(Exception e){
				logger.info("重新关注异常！",e);
				if(tx != null){
					tx.rollback();
				}
				e.printStackTrace();
			}finally{
				if(session != null ){
					session.close();
				}
			}
		}
	}
	
	/**
	 * @category 重新关注,并且更新数据
	 */
	public void resubscrible(TMbWeixinuser updateUser){
		Transaction tx = null;
		Session session = getSession();
		if(session != null){
			try{
				tx = session.beginTransaction();
				updateUser.setBindDate(null);
				updateUser.setMember(null);
				updateUser.setGroup(null);
				updateUser.setIsSign(0);
				session.update(updateUser);
				tx.commit();
			}catch(Exception e){
				logger.info("重新关注异常！",e);
				if(tx != null){
					tx.rollback();
				}
				e.printStackTrace();
			}finally{
				if(session != null ){
					session.close();
				}
			}
		}
	}

	/**
	 * 将粉丝设置为取消关注  
	 * 
	 * @param openid
	 * @throws Exception
	 */
	public void unsubscribleWeiXinUser(String openid) throws Exception {
		Transaction tx = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Session session = getSession();
		if(session != null){
			try {
				tx = session.beginTransaction();
				Object[] args = new Object[1];
				args[0] = openid;
				//更新标记与解除会员绑定,解除所属分公司
				//String hql = "update t_mb_weixinuser set subscribe=0,memberId=null,bindDate=null,isSign=0,groupid = null where openid = ?";
				String hql = "update t_mb_weixinuser set subscribe=0,updateTime = ?  where openid = ?";
				Query query = session.createSQLQuery(hql);
				query.setTimestamp(0, new Date());
				query.setParameter(1,openid);
				query.executeUpdate();
				tx.commit();
			} catch (Exception e) {
				if(tx != null){
					tx.rollback();
				}
				e.printStackTrace();
				logger.info("粉丝取消关注失败！" ,e);
			}finally{
				if(session != null ){
					session.close();
				}
			}
		}
	}

	/**
	 * 删除粉丝
	 * 
	 * @param openid
	 *            开发者id
	 * @throws Exception
	 */
	public void deleteWeiXinUser(String openid) throws Exception {
		Transaction tx = null;
		Session session = getSession();
		if(session != null){
			try {
				tx = session.beginTransaction();
				Object[] args = new Object[1];
				args[0] = openid;
				String hql = "delete from t_mb_weixinuser where openid = ?";
				Query query = session.createSQLQuery(hql);
				query.setParameter(0,openid);
				query.executeUpdate();
				tx.commit();
			} catch (Exception e) {
				logger.info("粉丝删除失败！" + e.getMessage());
				if(tx != null){
					tx.rollback();
				}
				e.printStackTrace();
				logger.info("粉丝删除失败！", e);
				
			}finally{
				if(session != null ){
					session.close();
				}
			}
		}
	}

}
