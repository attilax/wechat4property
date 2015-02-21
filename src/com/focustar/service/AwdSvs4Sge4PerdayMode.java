/**
 * @author attilax 老哇的爪子
	@since  2014-5-22 下午08:02:09$
 */
package com.focustar.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.attilax.core;
import com.attilax.award.Awardx;
import com.attilax.util.DateUtil;
import com.attilax.util.tryX;
import com.focustar.dao.impl.ActivityImpl;
import com.focustar.dao.impl.NewsImpl;
import com.focustar.entity.ActAward;
import com.focustar.entity.Activity;
import com.focustar.entity.AwardWeixin;
import com.focustar.entity.TMbActivityHistory;
import com.focustar.entity.TMbGroup;
import com.focustar.entity.TMbWeixinuser;
import static com.attilax.core.*;

/**
 * @author  attilax 老哇的爪子
 *@since  2014-5-22 下午08:02:09$
 */
public class AwdSvs4Sge4PerdayMode  extends AwdSvs4SGE{
	//  attilax 老哇的爪子 下午08:02:09   2014-5-22   
	
	AwdSvs awdC=new AwdSvs();
	
	
	public static void main(String[] args) {
		// 下午05:21:10 2014-4-29
		AwdSvs4Sge4PerdayMode awdSvs4Sge4PerdayModeC = new AwdSvs4Sge4PerdayMode();
//	TMbWeixinuser u=	awdSvs4Sge4PerdayModeC.findUserByOpenid("atioid");
//	System.out.println(u);
		 awdSvs4Sge4PerdayModeC.actHstrLog("atioid", "4");
		System.out.println("xx2233");
	 

	}
	
	private Integer CanJoinTimesBasenum_get(HttpServletRequest request) {
		// attilax 老哇的爪子 下午07:06:53 2014-5-13
		String activityId = activityId = actID(2);
		ActivityImpl c = new ActivityImpl();
		Activity act = c.getOneActivity(Integer.parseInt(activityId));
		return act.getJoinCount();
	}
	
	/**
	 * 
	@author attilax 老哇的爪子
		@since  2014-6-5 下午08:07:10$
	
	 * @param request
	 * @return
	 */
	public String leftNums_4sge(HttpServletRequest request) {
		core.log("leftNums_4sge");
	 	
		String uid = request.getParameter("openid");
		String actid = request.getParameter("actid");
		  String logPix=","+uid+",actid:"+actid;
			
		 actHstrLog(uid,actid);
		int awdChoiceNum_SmashGoldEgg = new AwdSvs4SGE().awdChoiceNum_o4;
		if (NoSetAwd(actid)) {
			return NoSetAwdError();
		}
		if (bingoedNum(uid, actid) >= 1) {
			core.log("--bingo ed ,"+uid+",actid:"+actid );
			return binoedRet();
		}
		if (!awdC.todayJoined(uid, actid)) {
			core.log("--o430: is not today joined"+logPix);
			ActivityImpl c = new ActivityImpl();
			Activity act = c.getOneActivity( Integer.parseInt( actid));
			awdC.geneTodayNewRsO6(uid, actid);
			return defFisrtJoinRet(act.getJoinCount());

		} else {
			core.log("--o430: is  already today joined"+logPix);
			AwardWeixin joinRec =awdC. todayJoinRecode(uid, actid);
			return otherChoiceNum(uid, actid, awdChoiceNum_SmashGoldEgg,
					joinRec);

		}

		//				

		//
		// // kaisi cheujyeo
		// // int aWx=uex.getId();
		//
		// // Map<String, Object> awardMap =
		// // getRandAward(uex.getActivity()
		// // .getId(), uex, uex.getOpenId());
		// //
		// // Integer leftCount = (Integer) awardMap.get("leftCount");
		// //
		// // retMap.put("leftCount", leftCount);
		//
		// Awardx myAward = startAward(uex.getActivity().getId(), uex
		// .getOpenId());
		// if (myAward != null) {
		// retMap.put("willBingoAwardId", myAward.getId());
		// retMap.put("willBingoAwardTitle", myAward.name);
		// }
		// String jsonStr = JSONObject.toJSONString(retMap);
		// return jsonStr;
		// }
		// }

	}

	
	/**
	@author attilax 老哇的爪子
		@since  2014-6-4 上午09:46:30$
	
	 * @param uid
	 * @param actid
	 */
	public void actHstrLog(final String uid,final String actid) {
		// attilax 老哇的爪子  上午09:46:30   2014-6-4 
		new tryX<Object>() {

			@Override
			public Object item(Object t) throws Exception {
				// attilax 老哇的爪子 上午10:30:53 2014-6-4
				TMbWeixinuser user = findUserByOpenid(uid);

				TMbActivityHistory history = new TMbActivityHistory();

				history.setActivityId(Integer.parseInt(actid));
				history.setOperTime(new Date());

				history.setOpenid(user.getOpenid());
				if (user.getMember() != null) {
					history.setMemberId(user.getMember().getMemberId());
				}

				Integer groupid = groupid(user);

				history.setGroupId(groupid);

				ActivityImpl activityDao = new ActivityImpl();
				if (activityDao.addActivityHistory4ati(history)) {
					// log.info("添加活动记录成功");
				}
				return null;
			}
/**
 * 
@author attilax 老哇的爪子
	@since  2014-6-11 下午01:57:09$

 * @param user
 * @return
 */	@SuppressWarnings("all")
 @Deprecated
			private int groupid(final TMbWeixinuser user) {
				// attilax 老哇的爪子 下午01:53:05 2014-6-11

				return new tryX<Integer>() {

				
					@Override
					public Integer item(Object t) throws Exception {
						// attilax 老哇的爪子 下午01:54:24 2014-6-11
						Integer groupid =user.getGroup().getGroupid();
					 
						core.log("-- o6a1 groupid in item:"+String.valueOf(groupid));
						return groupid;
					}
				}.$(new NewsImpl().checkProvinceAndCity(
						user.getProvince(), user.getCity())
						.getGroupid());

			}

		}.$("");
		
	}


	/**
	@author attilax 老哇的爪子
		@since  2014-6-4 上午10:23:34$
	
	 * @param uid
	 * @return
	 */
	@Deprecated
	public TMbWeixinuser findUserByOpenid(String uid) {
		// attilax 老哇的爪子  上午10:23:34   2014-6-4 
		Session session = getSession();
		String today_end=DateUtil.getTodayStr()+" 23:59:59";
		String hql = "from TMbWeixinuser where     openid='"+uid+"'";
		 
		List<TMbWeixinuser> awardList = null;
		Query q = session.createQuery(hql);
//		q.setParameter(0, actid);
//		q.setParameter(1, uid);
//		q.setParameter(2, DateUtil.today_notime());
		awardList = q.list();

		// if (awardList.size() == 0) // new user add 3tsi
		// {}
		// System.out.println(awardList.size());
		return awardList.get(0);
	//	return null;
	}


	/**
	 * perday mode
	 */
	protected AwardWeixin miniOneAwdChoice(String uid, String actid) {
	// 下午12:31:09 2014-4-30
	AwardWeixin awdJoin = awdC.todayJoinRecode(uid, actid);
	miniOneAwdChoice(uid, actid, awdJoin);
	return awdJoin;
	// 根据活动获取奖项列表

	// } else // normal
	// {
	//
	// AwardWeixin uex = awardList.get(0);
}
	
	/**
	 * 
	@author attilax 老哇的爪子
		@since  2014-5-22 下午08:41:44$
	
	 * @param myAward
	 * @param uid
	 * @param actid
	 */
	public void setAwdTimeNid(Awardx myAward, String uid, String actid) {
	log("--setAwdTimeNid"+uid+",actid:"+actid);
	// 下午03:23:42 2014-4-30
	AwardWeixin joinRec =awdC. todayJoinRecode(uid, actid);
	joinRec.setAwardTime(new Date());
	ActAward awd = new ActAward();
	awd.setId(myAward.id);
	joinRec.setAward(awd);

	Session session = getSession();

	Transaction tx = session.beginTransaction();
	session.update(joinRec);
	tx.commit();

}
	
	
	/**
	 * perday MOde
	 */
	public boolean choiceNumOvertime(String uid, String actid) {
	// 下午02:49:17 2014-4-30
	AwardWeixin joinRec =awdC. todayJoinRecode(uid, actid);
	choiceNumOvertimeCheckResult=joinRec;
	core.log("--choice getAwardCount()  :"+joinRec.getAwardCount().toString()+ "  for "+uid );
	if (joinRec.getAwardCount() <= 0)
		return true;
	else return false;
}
	
	/**
 * 
 */
	public String startBingo(HttpServletRequest request) {
		// 上午11:23:17 2014-4-28
		String uid = request.getParameter("openid");
		
		core.log("--o429: uid (openid) ::" + uid);// ajax
		String actid = request.getParameter("actid");
		  String logPix=","+uid+",actid:"+actid;
		if(NoSetAwd(actid))
		{
			return NoSetAwdError();
		}
		
		log("--check bingoedNum,"+logPix);
		if (bingoedNum(uid, actid) >= 1) {
log("--bioned,"+logPix);
			return binoedRet();
		}
		
		log("--check choiceNumOvertime,"+logPix);
		if (choiceNumOvertime(uid, actid)) {
			log("--choiceNumOvertime ed,"+logPix);
			return overtime();
		}

		Awardx myAward = startAward(Integer.parseInt(actid), uid);
		if (myAward == null) {  //no bingo process
			log("--nobioned,"+logPix);
			AwardWeixin joinRec = miniOneAwdChoice(uid, actid);
			return noBingoResult(joinRec);

		}
		
		
		///   bingo process 
		log("--bining,"+logPix);
awdC.miniAllAwdChoice(uid, actid);
 
		setAwdTimeNid(myAward, uid, actid);

		return binoedResult(myAward);

	}
	
}



//  attilax 老哇的爪子