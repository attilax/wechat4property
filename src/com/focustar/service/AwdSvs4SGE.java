/**
 * @author attilax 老哇的爪子
	@since  2014-4-29 下午05:21:10$
 */
package com.focustar.service;

import static com.attilax.core.log;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.alibaba.fastjson.JSONObject;
import com.attilax.core;
import com.attilax.award.Awardx;
import com.attilax.collection.listUtil;
import com.attilax.util.DateUtil;
import com.attilax.util.Func_4SingleObj;
import com.focustar.entity.ActAward;
import com.focustar.entity.Activity;
import com.focustar.entity.AwardWeixin;

/**
 * JointimesCanReaddMode
 * @author attilax
 * 
 */
public class AwdSvs4SGE extends AwdSvs4JointimesCanReaddMode {
	AwdSvs awdC=new AwdSvs();
	// todox static var in resin4 not take effie hot deploy..
	// excpt change the strutct (add field,method) then can hotdeplyo
	// public int awdChoiceNum = 19;
	public int awdChoiceNum_o4 = 3;
//	public int awdChoiceNum_o4x = 15;
//	public int awdChoiceNum_o41x = 15;

	/**
	 * @author attilax 老哇的爪子
	 * @since 2014-4-29 下午05:21:10$
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// 下午05:21:10 2014-4-29
		System.out.println("xx2233");
		AwdSvs4SGE.msd3();

	}

	/**
	 * @author attilax 老哇的爪子
	 * @since 2014-4-29 下午06:04:54$
	 */
	private static void msd3() {
		// 下午06:04:54 2014-4-29

	}

	/**
	 * @author attilax 老哇的爪子
	 * @since 2014-4-29 下午05:21:36$
	 * 
	 * @param request
	 * @return
	 */
	public String startBingoxxx(HttpServletRequest request) {
		// 下午05:21:36 2014-4-29
		return null;
	}

	public int bingoedNum(String uid, String actid) {
		// 上午10:40:37 2014-4-29

		Session session = getSession();
		String checkHql = "select count(*) as bingoNum from AwardWeixin where awardId!=null and  openid=? and activityId="
				+ actid;
		Query cq = session.createQuery(checkHql);
		cq.setParameter(0, uid);

		Long count = (Long) cq.uniqueResult();

		return Integer.parseInt(count.toString());
	}

	/**
	 * @author attilax 老哇的爪子
	 * @since 2014-4-28 上午11:23:17$
	 * 
	 * @param request
	 * @return
	 */
	String startBingo(HttpServletRequest request) {
		// 上午11:23:17 2014-4-28
		String uid = request.getParameter("openid");
		core.log("--o429: uid (openid) ::" + uid);// ajax
		String actid = request.getParameter("actid");
		if(NoSetAwd(actid))
		{
			return NoSetAwdError();
		}
		
		if (bingoedNum(uid, actid) >= 1) {

			return binoedRet();
		}
		if (choiceNumOvertime(uid, actid)) {
			return overtime();
		}

		Awardx myAward = startAward(Integer.parseInt(actid), uid);
		if (myAward == null) {
			AwardWeixin joinRec = miniOneAwdChoice(uid, actid);
			return noBingoResult(joinRec);

		}

		clearAllAwdChoice(uid, actid);
		setAwdTimeNid(myAward, uid, actid);

		return binoedResult(myAward);

	}
	
	
	protected Awardx startAward(Integer actid, String uid) {
		log("--start startAward:"+uid+",actid:"+actid.toString());
		if (!uid.equals("testMustBin"))
			if (bingoedNum(uid, actid.toString()) >= perUserMaxBigonNum) {
				return null;
			}

		List<ActAward> li = AwdListByActid(actid);

		// conver to attilax awd list format
		List<Awardx> li_fnl = listUtil.map_generic(li,
				new Func_4SingleObj<ActAward, Awardx>() {

					@Override
					public Awardx invoke(ActAward o) {
						// 上午08:53:09 2014-4-29
						ActAward thisAwd = o;
						Awardx awd = new Awardx();
						awd.id = thisAwd.getId();
						awd.name = thisAwd.getAwardName();
						awd.prbblt = thisAwd.getRate();
						return awd;
					}
				});

		// o5c 4 test mujstbin
		if (uid.equals("testMustBin")) {
			List<Awardx> li2 = listUtil.map_generic(li_fnl,
					new Func_4SingleObj<Awardx, Awardx>() {

						@Override
						public Awardx invoke(Awardx o) {
							// 上午08:53:09 2014-4-29
							Awardx thisAwd = o;
							Awardx awd = new Awardx();
							awd.id = thisAwd.getId();
							awd.name = thisAwd.getName();
							awd.prbblt = 100000;
							return awd;
						}
					});
			li_fnl = li2;
		}

		Awardx awd = com.attilax.award.AwdSvs.getBingoAwd(li_fnl);
		if (awd == null)
			return null;
		if (awdOver(awd)) {
			if (uid.equals("testMustBin"))// o5c
				return awd;
			return null;
		} else
			return awd;
	}

//	protected AwardWeixin miniOneAwdChoice(String uid, String actid) {
//		// 下午12:31:09 2014-4-30
//		AwardWeixin awdJoin = todayJoinRecode(uid, actid);
//		miniOneAwdChoice(uid, actid, awdJoin);
//		return awdJoin;
//		// 根据活动获取奖项列表
//
//		// } else // normal
//		// {
//		//
//		// AwardWeixin uex = awardList.get(0);
//	}

	/**
	@author attilax 老哇的爪子
		@since  2014-4-30 下午03:42:33$
	
	 * @return
	 */
	public String overtime() {
		// 下午03:42:33   2014-4-30 
		
		Map<Object, Object> retMap = new HashMap<Object, Object>();
		retMap.put("leftCount",0);
		retMap.put("alreadyJoinNum", 0);
		retMap.put("willBingoAwardId", 0);
		retMap.put("willBingoAwardTitle", "nonex");
	//	retMap.put("bingoed", 1);
		String jsonStr = JSONObject.toJSONString(retMap);
		return jsonStr;
	//	return null;
	}

	public String binoedResult(Awardx myAward) {
		Map<Object, Object> retMap = new HashMap<Object, Object>();
		retMap.put("leftCount", 0);
		retMap.put("alreadyJoinNum", 999);
		retMap.put("willBingoAwardId", myAward.getId());
		retMap.put("willBingoAwardTitle", myAward.getName());
		retMap.put("bingoed", 1);
		String jsonStr = JSONObject.toJSONString(retMap);
		return jsonStr;
	}

	/**
	 * @author attilax 老哇的爪子
	 * @since 2014-4-30 下午03:23:42$
	 * 
	 * @param myAward
	 * @param actid
	 * @param uid
	 */
//	private void setAwdTimeNid(Awardx myAward, String uid, String actid) {
//		// 下午03:23:42 2014-4-30
//		AwardWeixin joinRec = todayJoinRecode(uid, actid);
//		joinRec.setAwardTime(new Date());
//		ActAward awd = new ActAward();
//		awd.setId(myAward.id);
//		joinRec.setAward(awd);
//
//		Session session = getSession();
//
//		Transaction tx = session.beginTransaction();
//		session.update(joinRec);
//		tx.commit();
//
//	}

	// PrintWriter out = resp.getWriter();
	// out.print(jsonStr);
	// out.flush();
	// out.close();
	// return jsonStr;
	/**
	 * @author attilax 老哇的爪子
	 * @param joinRec
	 * @since 2014-4-30 下午03:19:26$
	 * 
	 * @return
	 */
	protected String noBingoResult(AwardWeixin joinRec) {
		// 下午03:19:26 2014-4-30
		// int alreadyJoinNum = SafeVal.val(uex.getAwardCount(), 0);
		Map<Object, Object> retMap = new HashMap<Object, Object>();
		retMap.put("leftCount",  joinRec.getAwardCount());
		retMap.put("alreadyJoinNum",0);
		retMap.put("willBingoAwardId", 0);
		retMap.put("willBingoAwardTitle", "nonex");
		String jsonStr = JSONObject.toJSONString(retMap);
		return jsonStr;
	}
	
	AwardWeixin choiceNumOvertimeCheckResult;
	/**
	 * @author attilax 老哇的爪子
	 * @since 2014-4-30 下午02:49:17$
	 * 
	 * @param uid
	 * @param actid
	 * @return
	 */
//	private boolean choiceNumOvertime(String uid, String actid) {
//		// 下午02:49:17 2014-4-30
//		AwardWeixin joinRec = todayJoinRecode(uid, actid);
//		choiceNumOvertimeCheckResult=joinRec;
//		if( joinRec.getAwardCount()>=awdChoiceNum_o4)
//			
//		return true;
//		else return false;
//	}

}
