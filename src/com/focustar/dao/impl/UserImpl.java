package com.focustar.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.focustar.entity.THDMember;
import com.focustar.entity.TMbSignHistory;
import com.focustar.entity.TMbWeixinuser;
import com.focustar.service.hd.HDService;
import com.focustar.thread.CheckActivityJober;
import com.focustar.util.BaseImpl;
import com.focustar.util.Constant;

public class UserImpl extends BaseImpl {
	
	private static Logger logger = Logger.getLogger(UserImpl.class);
	/**
	 * @category 更新会员信息
	 * @param member
	 * @return
	 */
	public THDMember updateMember(THDMember member){
		
		Session session = getSession();
		Transaction tx = null;
		if(session != null){
			try{
				tx = session.beginTransaction();
				session.update(member);
				tx.commit();
			}
			catch(Exception e){
				if(tx != null){
					tx.rollback();
				}
				e.printStackTrace();
			}finally{
				session.close();
			}
		}
		return null;
	}
	
	/**
	 * @category 获取会员信息
	 * @param memberId
	 * @return
	 */
	public THDMember getMemberBymemberId(String memberId){
		Session session = getSession();
		if(session != null){
			try{
				String hql = "from THDMember where memberId = ?";
				Query q = session.createQuery(hql);
				q.setParameter(0,memberId);
				
				return (THDMember)q.uniqueResult();
				
			}catch(Exception e){
				e.printStackTrace();
			}
			finally{
				session.close();
			}
		}
		return null;
	}
	
	
	/**
	 * @param openid
	 * @param member
	 * @return
	 */
	public boolean bindMember(String openid,THDMember member,Map<Object,Object> feedBackMap){
		boolean flag = false;
		Session session = getSession();
		Transaction tx = null;
		if(session != null){
			try{
				
				if(feedBackMap == null){
					feedBackMap = new HashMap<Object,Object>();
				}
				
				tx  = session.beginTransaction();
				
				String checkSql = "from TMbWeixinuser where memberId = ?";
				
				Query cq = session.createQuery(checkSql);
				cq.setParameter(0, member.getMemberId());
				
				//检查是否已绑定会员
				TMbWeixinuser tu = (TMbWeixinuser) cq.uniqueResult();
				
				if(tu == null){
					
						logger.info("开始绑定会员...");
					
						String sql = "update t_mb_weixinuser set memberId = ? ,bindDate = ? where openid = ?";
						
						Query q = session.createSQLQuery(sql);
						q.setParameter(0, member.getMemberId());
						q.setTimestamp(1, new Date());
						q.setParameter(2, openid);
						
						if(q.executeUpdate() == 1){//更新成功
							logger.info("绑定成功");
							String hql = "from THDMember where memberId = ?";
							Query chkq = session.createQuery(hql);
							chkq.setParameter(0, member.getMemberId());
							chkq.setMaxResults(1);
							
							THDMember checkMember = (THDMember)chkq.uniqueResult();
							
							if(checkMember == null){
								feedBackMap.put("isFirst", true);
								member.setCreateTime(new Date());
								session.save(member);
							}else{
								//添加新记录或者更新
								checkMember.setCreateTime(new Date());
								checkMember.setAddress(member.getAddress());
								checkMember.setBirthday(member.getBirthday());
								checkMember.setCellPhone(member.getCellPhone());
								checkMember.setCredit(member.getCredit());
								checkMember.setEmail(member.getEmail());
								checkMember.setName(member.getName());
								session.saveOrUpdate(checkMember);
							}
							tx.commit();
							flag = true;
						}else{
							logger.info("绑定失败....回滚事务");
							if(tx != null){
								tx.rollback();
							}
						}
				
				}else{
					feedBackMap.put("dbErrMsg","1");//该会员卡已绑定！
					logger.info("会员已绑定，所以绑定失败");
				}
				
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
	
	/***
	 * @category 根据会员id数组获取微信用户列表
	 * @param memberIds
	 * @return
	 */
	public List<TMbWeixinuser>  getUserListBymemberIds(Object[] memberIds){
		
		if(memberIds != null && memberIds.length > 0){
		
			Session session = getSession();
			
			if(session != null){
				try{
					String hql = "from TMbWeixinuser tu where tu.member.memberId in(:aArray)";
					Query q = session.createQuery(hql);
					q.setParameterList("aArray", memberIds);
					
					return q.list();
					
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					session.close();
				}
			}
		}else{
			logger.info("memberIds === > 为空" );
		}
		return null;
	}
	
	/***
	 * @category 根据memberId来获取微信用户信息
	 * @param memberId
	 * @return
	 */
	public TMbWeixinuser getUserBymemberId(String memberId){
		TMbWeixinuser user = null;
		Session session =  getSession();
		
		if(session != null){
			try{
				
				String hql = "from TMbWeixinuser tu where tu.memberId = ?";
				Query q = session.createQuery(hql);
				q.setParameter(0, memberId);
				user = (TMbWeixinuser) q.uniqueResult();
				
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				session.close();
			}
			
		}
		
		return user;
	}
	
	/***
	 * @category 根据openid获取微信用户信息
	 * @param openid
	 * @return
	 */
	public TMbWeixinuser getUserByopenid(String openid){
		
		TMbWeixinuser user = null;
		Session session = getSession();
		if(session != null){
			try{
				
				String hql = "from TMbWeixinuser tu where openid = ?";
				
				Query q = session.createQuery(hql);
				q.setParameter(0, openid);
				user = (TMbWeixinuser) q.uniqueResult();
				
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				if(session != null){
					session.close();
				}
			}
		}
		
		return user;
		
	}
	
	
	/***
	 * @category 更新微信用户信息
	 * @param upUser
	 * @return
	 */
	public boolean updateUser(TMbWeixinuser upUser,int groupid){
		boolean flag = false;
		Session session = getSession();
		Transaction tx = null;
		if(session != null){
			try{
				tx = session.beginTransaction();
				
				if(HDService.requestDaySign(upUser)){
					session.update(upUser);
					
					//添加签到记录
					TMbSignHistory history = new TMbSignHistory();
					history.setOpenid(upUser.getOpenid());
					history.setGroupid(groupid); //签到的分店id
					if(upUser.getMember() != null){
						history.setMemberId(upUser.getMember().getMemberId());
					}
					history.setSignDate(new Date());
					session.save(history);
					
					tx.commit();
					flag = true;
				}
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

}
