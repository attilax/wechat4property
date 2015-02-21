/**
 *  * @author  attilax 老哇的爪子
 *@since  2014-6-25 上午11:07:09$
 *
 */
package com.focustar.service;

import static com.attilax.core.log;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.hibernate.Session;
import com.attilax.core;
import com.attilax.award.Awardx;
import com.attilax.util.DateUtil;
import com.focustar.dao.impl.ActivityImpl;
import com.focustar.entity.Activity;
import com.focustar.entity.AwardWeixin;

/**
 *  * @author  attilax 老哇的爪子
 *@since  2014-6-25 上午11:07:09$
 *
 */
@RemoteProxy(name="AwdSvs_shake_PerdaymodeProx") 
public class AwdSvs_shake_Perdaymode extends AwdSvs4Sge4PerdayMode {

	/**
	 * @param args
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-25 上午11:07:09$
	 */
	public static void main(String[] args) {
		// attilax 老哇的爪子  上午11:07:09   2014-6-25 

	}
	//  attilax 老哇的爪子 上午11:07:09   2014-6-25   
	@RemoteMethod @Deprecated
	public String leftNums(HttpServletRequest request)  {
	
	core.debugReq(request);
	
	core.log("leftNums_4sge");
 	
	String uid = request.getParameter("openid");
	String actid = request.getParameter("actid");
	  return leftNum_noReq(uid, actid);

	 
	 
	}
	//   下午5:31:28 2014-7-2  老哇的爪子  Attilax
	@RemoteMethod public String leftNum_noReq(String uid, String actid) {
		String logPix = "," + uid + ",actid:" + actid;

		actHstrLog(uid, actid);
		int awdChoiceNum_SmashGoldEgg = new AwdSvs4SGE().awdChoiceNum_o4;
		if (NoSetAwd(actid)) { return NoSetAwdError(); }
		if (bingoedNum(uid, actid) >= 1) {
			core.log("--bingo ed ," + uid + ",actid:" + actid);
			return binoedRet();
		}
		if (!todayJoined(uid, actid)) {
			core.log("--o430: is not today joined" + logPix);
			ActivityImpl c = new ActivityImpl();
			Activity act = c.getOneActivity(Integer.parseInt(actid));
			awdC.geneTodayNewRsO6(uid, actid);
			return defFisrtJoinRet(act.getJoinCount());

		} else {
			core.log("--o430: is  already today joined" + logPix);
			AwardWeixin joinRec = awdC.todayJoinRecode(uid, actid);
			return otherChoiceNum(uid, actid, awdChoiceNum_SmashGoldEgg, joinRec);

		}
	}

	/**
	 * 
	@author attilax 老哇的爪子
		@since  o72 m39a$
	
	 * @param uid
	 * @param actid
	 * @return
	 */
	boolean todayJoined(String uid, String actid) {
		// 上午11:53:53 2014-4-30

		String doday = DateUtil.today();
		Session session = getSession();
		//
		String sqlString =String.format(  "SELECT   *   FROM  t_mb_awardweixin   where activityId=" + actid + " and openId='" +uid
				+ "' and   createTime='%s'",DateUtil.today_Start());
		// 以SQL语句创建SQLQuery对象
		if (ifMysql()) sqlString = "SELECT   *   FROM  t_mb_awardweixin   where activityId=" + actid + " and openId='" + uid
				+ "' and  date(createTime)   =   curdate(); ";
core.log(sqlString);
		List l = session.createSQLQuery(sqlString)

		// 将查询ss实体关联的User类

		.addEntity("ss", AwardWeixin.class)

		// 返回全部的记录集

		.list();

		// 遍历结果集

		// Iterator it = l.iterator();
		//
		// while (it.hasNext()){
		//
		// //因为将查询结果与Student类关联，因此返回的是Student集合
		//
		// AwardWeixin s = (AwardWeixin)it.next();
		//
		// String a = s.getName();
		//	        
		// System.out.println(a);
		//
		// }
		if (l.size() == 0)
			return false;
		else
			return true;
	}

	/**
	@author attilax 老哇的爪子
		@since  2014-6-25 上午11:36:27$
	
	 * @param request
	 * @return
	 */
	@RemoteMethod
	public String startDraw(HttpServletRequest request) {
		// attilax 老哇的爪子  上午11:36:27   2014-6-25 
		return this.startBingo(request);
	 
		
	}
	
	/**
	 * 
	@author attilax 老哇的爪子
		@since  o73 kjq$
	
	 * @param request
	 * @return
	 */
	@RemoteMethod
	 
	public String tMultiThrd() {
		// attilax 老哇的爪子  上午11:36:27   2014-6-25 
		return  String.valueOf ( this.hashCode());
	 
		
	}
	
	@RemoteMethod
	public String startDraw_noreq(String uid,	String actid) {
		// 上午11:23:17 2014-4-28
		//String uid = request.getParameter("openid");
		
		core.log("--o429: uid (openid) ::" + uid);// ajax
	// = request.getParameter("actid");
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
		if (myAward == null) {
			log("--nobioned,"+logPix);
			AwardWeixin joinRec = miniOneAwdChoice(uid, actid);
			return noBingoResult(joinRec);

		}
		
		log("--bining,"+logPix);
awdC.miniAllAwdChoice(uid, actid);
 
		setAwdTimeNid(myAward, uid, actid);

		return binoedResult(myAward);

	}
	
	
	
}

//  attilax 老哇的爪子