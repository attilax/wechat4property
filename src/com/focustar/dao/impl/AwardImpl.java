package com.focustar.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.attilax.core;
import com.attilax.util.randomx;
import com.focustar.entity.ActAward;
import com.focustar.entity.AwardWeixin;
import com.focustar.util.BaseImpl;

public class AwardImpl extends BaseImpl {
	
	private static Logger logger = Logger.getLogger(AwardImpl.class);

	public static void main(String[] args) {
		// getAwardRand(List<ActAward> awardList)
	AwardImpl awardDao = new AwardImpl();
		 awardDao.getAwardRand(new ArrayList<ActAward>(){{
			 this.add(new ActAward(){{
				 this.setRate(99997);
			 }});
			 
			 this.add(new ActAward(){{
				 this.setRate(99999);
			 }});
			 
		 }});
//		//抽奖记录id
//		int awxId = Integer.parseInt("2");
//		AwardWeixin aWx = awardDao.getOneAwardWeixin(awxId);
//		System.out.println(aWx);
		core.log("---");
	}
	/**
	 * @category 查询用户抽奖记录
	 * @param id
	 * @return
	 */
	public AwardWeixin getOneAwardWeixin(Integer id) {

		Session session = getSession();
		AwardWeixin awardWx = null;
		if (session != null) {
			try {
				awardWx = (AwardWeixin) session.get(AwardWeixin.class, id);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				session.close();
			}
		}
		return awardWx;
	}

	/**
	 * @category 开始抽奖
	 * @param awdWx
	 * @param openId
	 * @return
	 */
	public Map<String,Object> getRandAward(Integer activityId, AwardWeixin awdWx,
			String openId) {
		
		Map<String,Object> retMap = new HashMap<String,Object>();
		Session session = getSession();
		Transaction tx = null;
		if (session != null) {
			try {

				tx = session.beginTransaction();
				// 根据活动获取奖项列表
				String hql = "from ActAward where activityId = ?";
				List<ActAward> awardList = null;
				Query q = session.createQuery(hql);
				q.setParameter(0, activityId);
				awardList = q.list();
				
				//必须有奖品项
				if (awardList != null && awardList.size() > 0) {
					
					
					//开始随机抽奖
					ActAward shootAward = getAwardRand(awardList);
					// 没有中奖
					if (shootAward == null) {
						// 抽奖次数减
						awdWx.setAwardCount(awdWx.getAwardCount() - 1);
						retMap.put("leftCount", awdWx.getAwardCount());
					} else {// 中奖
						if(shootAward.getOrderBy()==null)
							throw new RuntimeException("shootAward order is null,id :"+String.valueOf(shootAward.getId()));
						
						//检查该奖项是否还可以送
						String checkHql = "select count(*) as leftCount from AwardWeixin where awardId = ?";
						session.cancelQuery();
						Query cq = session.createQuery(checkHql);
						
						cq.setParameter(0,shootAward.getId());						
						Long count = (Long) cq.uniqueResult();
						//该奖项还有余额
						if(count < shootAward.getAwardCount()){
							
							awdWx.setAward(shootAward);
							//中奖时间
							awdWx.setAwardTime(new Date());
							// 中奖后，就不能再抽了？
							awdWx.setAwardCount(0);
							retMap.put("leftCount", 0);
						}else{
							//该奖项没有余额
							// 抽奖次数减
							awdWx.setAwardCount(awdWx.getAwardCount() - 1);
							retMap.put("leftCount", awdWx.getAwardCount());
						}
					}
					session.update(awdWx);
					tx.commit();
					
					retMap.put("award", shootAward);
				}
			} catch (Exception e) {
				if(tx != null){
					tx.rollback();
				}
			   throw new RuntimeException(e);
			} finally {
				session.close();
			}
		}
		return retMap;
	}

	/**
	 * @category 获取中奖概率
	 * 
	 */
	private static ActAward getAwardRand(List<ActAward> awardList) {
		ActAward shootAward = null;
		
		/*return shootAward = awardList.get(0);*/
		//测试
		if (awardList != null && awardList.size() > 0) {

			int size = awardList.size();
			int rateBase = 100000;
			// 中奖总概率
			for (ActAward awd : awardList) {
				//seed += aa.getRate();
				int rdm=randomx.random(rateBase);
				//	core.log("--o42910: rdmAwdIndex_may--rdm--awd.prbblt"+String.valueOf(rdmAwdIndex_may)+"--"+String.valueOf(rdm)+"--"+String.valueOf(awd.prbblt));
					if(rdm<awd.getRate())
					{
						//bingo
						return awd;
						
					}
			}
			// 循环奖项
//			for (int i = 0; i < size; i++) {
//				ActAward one = awardList.get(i);
//				Random rand = new Random();
//				// 获取1-100之间的概率
//				int randNum = rand.nextInt(seed);
//				logger.info("随机概率  >>> " + randNum);
//				// 中奖了
//				if (randNum <= i) {
//					shootAward = one;
//					break;
//				} else {
//					// 继续
//					seed -= one.getRate();
//				}
//			}
		}
		return shootAward;
	}

	/*public static void main(String[] args) {
		List<ActAward> awardList = new ArrayList<ActAward>();

		ActAward oneAwd = new ActAward();
		oneAwd.setAwardName("一等奖");
		oneAwd.setRate(1);
		awardList.add(oneAwd);

		ActAward twoAwd = new ActAward();
		twoAwd.setAwardName("二等奖");
		twoAwd.setRate(3);
		awardList.add(twoAwd);

		ActAward threeAwd = new ActAward();
		threeAwd.setAwardName("三等奖");
		threeAwd.setRate(5);
		awardList.add(threeAwd);

		ActAward noAwd = new ActAward();
		noAwd.setAwardName("安慰奖");
		noAwd.setRate(92);
		awardList.add(noAwd);

		for (int i = 0; i < 10; i++) {
			ActAward act = getAwardRand(awardList);
			if (act != null) {
				System.out.println("中奖  ===> " + act.getAwardName());
			} else {
				System.out.println("无中奖");
			}
		}
	}*/
	/**
	 * 我的奖品
	 */
	public AwardWeixin getMyActAward(String openid,String actid){
		Session session = getSession();
		AwardWeixin awardWeixin = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("select A.id,A.awardTime,B.awardName,A.awardUserName,A.awardPhone,A.awardAddress,A.memcard From t_mb_awardweixin A left join t_mb_actaward B on A.awardId = B.id");
			sql.append(" Where A.openId = '").append(openid).append("' ");
			sql.append("And A.activityId = ").append(actid);
			//String  = "select * From t_mb_awardweixin A left join t_mb_actaward B on A.activityId = B.id";
			//String hql = "select *  from AwardWeixin where openId = ? and activity = ?";
			core.log(sql.toString());
			Query cq = session.createSQLQuery(sql.toString());
		/*	cq.setParameter(0,openid);
			cq.setParameter(1,actid);*/			
			List list = cq.list(); 
			SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(list!=null&&list.size()>0){
				awardWeixin = new AwardWeixin();
				Object[] obj = (Object[])list.get(0);
				if(obj[0] !=null){
					awardWeixin.setId(Integer.parseInt(obj[0].toString()));
				}
				if(obj[1] != null){
					awardWeixin.setAwardTime(formatDate.parse(obj[1].toString()));
				}
				if(obj[2] != null){
					awardWeixin.setAwardName(obj[2].toString());
					
				}
				if(obj[3] != null){
					awardWeixin.setAwardUserName(obj[3].toString());
					
				}
				if(obj[4] != null){
					awardWeixin.setAwardPhone(obj[4].toString());
				}
				if(obj[5] != null){
					awardWeixin.setMemcard(obj[5].toString());
				}
				if(obj[6] != null){
					awardWeixin.setAwardAddress(obj[6].toString());
				}			
				
			}
			//awardWeixin = list.get(0);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			session.close();
			
		}
		return awardWeixin;
	}
	public int updateAwdweixin(AwardWeixin awd){
		Session session = getSession();
		Transaction tx = null;
		int result = 0;
		try {
			tx = session.beginTransaction();
			
			StringBuilder sql = new StringBuilder();
			sql.append("update t_mb_awardweixin set awardUserName='").append(awd.getAwardUserName()).append("'");
			sql.append(",awardPhone ='").append(awd.getAwardPhone()).append("'");
			sql.append(",memcard ='").append(awd.getMemcard()).append("'");
			sql.append(",awardAddress ='").append(awd.getAwardAddress()).append("'");
			sql.append(" Where id =").append(awd.getId());
			System.out.println(sql.toString());
			Query query = session.createSQLQuery(sql.toString());
			query.executeUpdate();
			tx.commit();
			result= 1;
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}finally{
			session.close();
			
		}
		
		return result;
	}

}
