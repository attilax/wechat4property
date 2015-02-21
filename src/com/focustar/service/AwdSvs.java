/**
 * @author attilax 老哇的爪子
	@since  2014-4-28 上午11:14:21$
 */
package com.focustar.service;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.internal.SessionImpl;
import org.hibernate.transform.Transformers;
import org.springframework.web.WebApplicationInitializer;

import com.alibaba.fastjson.JSONObject;
import com.attilax.core;
import com.attilax.award.Awardx;
import com.attilax.collection.listUtil;
import com.attilax.io.filex;
import com.attilax.io.pathx;
import com.attilax.net.websitex;
import com.attilax.text.strUtil;
import com.attilax.util.DateUtil;
import com.attilax.util.Func_4SingleObj;
import com.attilax.util.Funcx;
import com.attilax.util.tryX;
import com.focustar.requestImp;
import com.focustar.dao.impl.ActivityImpl;
import com.focustar.dao.impl.AwardImpl;
import com.focustar.entity.ActAward;
import com.focustar.entity.Activity;
import com.focustar.entity.AwardWeixin;
import com.focustar.entity.Share;
import com.focustar.util.ConfigService;
//import com.sun.xml.rpc.processor.modeler.j2ee.xml.string;

/**scrach card and public base,pub depared..use awdsvsBase.java
 * @author attilax
 * 
 */
public class AwdSvs extends AwardImpl {
	boolean PerDayCalcMode=false;  // all count mode

	public AwdSvs(HttpServletRequest request) {
	//	WebApplicationInitializer
		String f = pathx.classPath() + File.separator + "scratchCardCfg.txt";
		String s = filex.read(f);
		String[] a = s.split(",");
	//	awdChoiceNum = Integer.parseInt(a[0].trim());
	
		if(PerDayCalcMode)
			CanJoinTimesBasenum= Integer.parseInt(a[0].trim());
		else
		 	CanJoinTimesBasenum=CanJoinTimesBasenum_get(request);
	}
	
	/**\
	 * 
	@author attilax 老哇的爪子
		@since  2014-6-3 下午05:14:15$
	
	 * @param actType
	 * @return
	 */
	public Activity queryActInfo(int actTypeId)
	{
		Session session = getSession();
		String hql = "from Activity where type="+String.valueOf(actTypeId)+ " order by id desc "; ;
		Query q = session .createQuery(hql);
		core.log("--"+hql);
		// q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		// q.setResultTransformer( Transformers.TO_LIST);

		List<Activity> li = q.list();
		if(li.size()==0)
			throw new RuntimeException("err:noSetAct没有设置这个活动:acttypeid:"+String.valueOf(actTypeId));
		Activity activity = li.get(0);
		return activity;
	}
	/**
	@author attilax 老哇的爪子
		@since  2014-5-13 下午07:06:53$
	
	 * @param request
	 * @return
	 */
	private Integer CanJoinTimesBasenum_get(HttpServletRequest request) {
		// attilax 老哇的爪子  下午07:06:53   2014-5-13 
		String activityId = 
			activityId=actID(2);
		ActivityImpl c=new ActivityImpl();
		Activity act=c.getOneActivity(Integer.parseInt(activityId));
		return act.getJoinCount();
	}

	public AwdSvs() {
		//	WebApplicationInitializer
			String f = pathx.classPath() + File.separator + "scratchCardCfg.txt";
			String s = filex.read(f);
			String[] a = s.split(",");
		//	awdChoiceNum = Integer.parseInt(a[0].trim());
		
//			if(PerDayCalcMode)
 			CanJoinTimesBasenum= Integer.parseInt(a[0].trim());
//			else
//			 	CanJoinTimesBasenum=CanJoinTimesBasenum_get();
		}

	public int awdChoiceNum = 0;
	public int awdChoiceNumxx = 0;
	
	@Deprecated
	public String actID(final int actTypeId) {

		// todox o5 hb sql query
//		return new tryX<String>() {
//
//			@Override
//			public String item(Object t) throws Exception {
//				System.out.println("");
//				// attilax 老哇的爪子 下午04:57:03 2014-5-8
//				Session session = getSession();
////				Query q = session
////						.createSQLQuery("SELECT   [id]             ,[type]   FROM  [t_mb_activity]  where type="
////								+ String.valueOf(actTypeId));
//				String hql = "from Activity where type="+String.valueOf(actTypeId);
//				Query q = session .createQuery(hql);
//				core.log("--"+hql);
//				// q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
//				// q.setResultTransformer( Transformers.TO_LIST);
//
// 			List<Activity> li = q.list();
// 			return li.get(0).getId().toString();
////				Object[] fields = (Object[]) li.get(0);
////				return (String) fields[0].toString();
//			}
//
//			
//
//		}.$();
		
		
		Session session = getSession();
//		Query q = session
//				.createSQLQuery("SELECT   [id]             ,[type]   FROM  [t_mb_activity]  where type="
//						+ String.valueOf(actTypeId));
		String hql = "from Activity where type="+String.valueOf(actTypeId);
		Query q = session .createQuery(hql);
		core.log("--"+hql);
		// q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		// q.setResultTransformer( Transformers.TO_LIST);

		List<Activity> li = q.list();
		if(li.size()==0)
			throw new RuntimeException("err:noSetScratchCardActivity没有设置刮刮卡活动");
		return li.get(0).getId().toString();

	}
	
	
	public String actID(final int actTypeId,String tips) {

		// todox o5 hb sql query
//		return new tryX<String>() {
//
//			@Override
//			public String item(Object t) throws Exception {
//				System.out.println("");
//				// attilax 老哇的爪子 下午04:57:03 2014-5-8
//				Session session = getSession();
////				Query q = session
////						.createSQLQuery("SELECT   [id]             ,[type]   FROM  [t_mb_activity]  where type="
////								+ String.valueOf(actTypeId));
//				String hql = "from Activity where type="+String.valueOf(actTypeId);
//				Query q = session .createQuery(hql);
//				core.log("--"+hql);
//				// q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
//				// q.setResultTransformer( Transformers.TO_LIST);
//
// 			List<Activity> li = q.list();
// 			return li.get(0).getId().toString();
////				Object[] fields = (Object[]) li.get(0);
////				return (String) fields[0].toString();
//			}
//
//			
//
//		}.$();
		
		
		Session session = getSession();
//		Query q = session
//				.createSQLQuery("SELECT   [id]             ,[type]   FROM  [t_mb_activity]  where type="
//						+ String.valueOf(actTypeId));
		String hql = "from Activity where type="+String.valueOf(actTypeId)+ " order by id desc ";
		Query q = session .createQuery(hql);
		core.log("--"+hql);
		// q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		// q.setResultTransformer( Transformers.TO_LIST);

		List<Activity> li = q.list();
		if(li.size()==0)
			throw new RuntimeException(tips);
		return li.get(0).getId().toString();

	}
	
	

	/**
	 * @author attilax 老哇的爪子
	 * @since 2014-5-8 下午06:24:04$
	 * 
	 * @param actid
	 * @return
	 */
	public boolean NoSetAwd(String actid) { 
		// attilax 老哇的爪子 下午06:24:04 2014-5-8
		String sql = "SELECT  *   FROM   t_mb_actaward   where activityId="
				+ actid;
		Session session = getSession();
		Query q = session.createSQLQuery(sql);
		List li = q.list();
		if (li.size() == 0)
			return true;
		return false;
	}

	/**
	 * @author attilax 老哇的爪子
	 * @since 2014-5-8 下午06:24:01$
	 * 
	 * @return
	 */
	public String NoSetAwdError() {
		// attilax 老哇的爪子 下午06:24:01 2014-5-8
		Map<Object, Object> retMap = new HashMap<Object, Object>();
		retMap.put("code", "noSetAwdErr");
		retMap.put("msg", "noSetAwdErr此活动还没设置奖品哟.");
		// retMap.put("willBingoAwardId", 0);
		// retMap.put("willBingoAwardTitle", "nonex");
		// retMap.put("bingoed", 1);
		String jsonStr = JSONObject.toJSONString(retMap);
		return jsonStr;
		// return null;
	}

	/**
	 * @author attilax 老哇的爪子
	 * @since 2014-4-28 上午11:14:21$
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// 上午11:14:21 2014-4-28
		// requestImp ri = new requestImp();
		// ri.setParam("openid", "oid1");
		// ri.setParam("actid", 1);
		// ri.setParam("act", "awardChoiceNum");
		//
		// AwdSvs sv = new AwdSvs();
		// String s = sv.jsonSvs(ri);
		// System.out.println(s);
		// sv.getOpenid("");

		// testBingoArgo();
		AwdSvs c = new AwdSvs();
		// int n = c.bingoedNum("atioid");
		// System.out.println(n);

		// System.out.println(c.todayJoined("atioid", "3"));

		// c.miniOneAwdChoice("atioid", "3");
		// c.geneTodayNewRs("atioid", "3",9);
		// System.out.println(c.myAwd("atioid", "3"));

		// System.out.println(c.bingoedNum("atioid","1"));

		System.out.println(pathx.classPath());
	//	SessionFactoryImpl
	//	c.getSession().getSessionFactory().
		
		//
		System.out.println(c.ifMysql());
		
		c.testShare();
		
		
	}

	/**
	@author attilax 老哇的爪子
		@since  2014年5月11日 下午8:13:03$
	
	 */
	private void testShare() {
		// attilax 老哇的爪子  下午8:13:03   2014年5月11日 
		Session session = getSession();
		String queryString = "from Share  ";
		Query queryObject = session.createQuery(queryString);
		final List list = queryObject.list();
		
	}

	/**
	@author attilax 老哇的爪子
		@since  2014年5月10日 上午10:24:49$
	
	 * @return
	 */
	private boolean ifMysql() {
		// attilax 老哇的爪子 上午10:24:49 2014年5月10日
		String dialect = ((SessionImpl) getSession()).getFactory().getDialect()
				.getClass().getName();
		// org.hibernate.dialect.MySQLDialect
		System.out.println(dialect);
		if (dialect.contains("MySQLDialect"))
			return true;
		else
			return false;
	}

	/**
	 * @author attilax 老哇的爪子
	 * @since 2014-4-30 下午04:52:33$
	 * 
	 * @param string
	 * @param string2
	 * @return
	 */
	@SuppressWarnings("all")
	private String myAwd(String uid, String actid) {
		// 下午04:52:33 2014-4-30
		Session session = getSession();
		// String checkHql =
		// "select count(*) as leftCount from AwardWeixin where awardId = ?";

		String queryString = "from AwardWeixin where activityId =" + actid
				+ " and  award!=null and openid='" + uid + "'";
		Query queryObject = session.createQuery(queryString);
		final List list = queryObject.list();

		// Session session = getSession();
		// String sqlString =
		// "SELECT TOP 1000 *   FROM  t_mb_awardweixin   where activityId="
		// + actid
		// + " and openId='"
		// + uid
		// + "' and  activityid!=null";
		// // 以SQL语句创建SQLQuery对象
		//
		// List l = session.createSQLQuery(sqlString)
		//
		// // 将查询ss实体关联的User类
		//
		// .addEntity("ss", AwardWeixin.class)
		//
		// // 返回全部的记录集
		//
		// .list();

		final List li = new ArrayList();

		new tryX<Object>() {

			@Override
			public Object item(Object t) throws Exception {
				AwardWeixin awdRec = (AwardWeixin) list.get(0);
				Map m = new HashMap();
				m.put("name", awdRec.getAward().getAwardName());
				Date finalyDate = DateUtil.addDate(awdRec.getAwardTime(), 30);
				m.put("timex", DateUtil.date2str(finalyDate, false));
				m.put("detx", "");
				li.add(m);
				return null;
			}

		}.$(new ArrayList());

		return toStr(li);
		// sess.
		// return null;
	}

	/**
	 * @author attilax 老哇的爪子
	 * @since 2014-4-29 上午09:14:58$
	 */
	private static void testBingoArgo() {
		// 上午09:14:58 2014-4-29
		AwdSvs c = new AwdSvs();
		Awardx myAward = c.startAward(1, "uid");
		if (myAward != null) {
			// retMap.put("willBingoAwardId", myAward.getId());
			// retMap.put("willBingoAwardTitle", myAward.name);
		}
		String jsonStr = JSONObject.toJSONString(myAward);
		System.out.println("---o429:" + jsonStr);
	}

	private int perUserMaxBigonNum = 1;

	/**
	 * @author attilax 老哇的爪子
	 * @since 2014-4-28 下午05:46:29$
	 * 
	 * @param string
	 * @return
	 */
	public String getOpenid(HttpServletRequest request) {

		HttpSession sess = request.getSession();

		// request.getSession().getAttribute("openid_ss");
		// 下午05:46:29 2014-4-28
		String code = request.getParameter("code");// 我们要的code
		core.log("--o428 code:" + code);
		String appid = new ConfigService().getWxProperty("APPID");
		String APPSECRET = new ConfigService().getWxProperty("APPSECRET");

		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
				+ appid + "&secret=" + APPSECRET + "&code=" + code
				+ "&grant_type=authorization_code";
		core.log("--o428 url:" + url);
		String ret = websitex.WebpageContent(url, "utf-8");
		core.log("--o428 ret:" + ret);
		final net.sf.json.JSONObject jo = net.sf.json.JSONObject
				.fromObject(ret);
		String oid = new tryX<String>() {

			@Override
			public String item(Object t) throws Exception {
				// 下午03:29:01 2014-4-29

				return jo.getString("openid");

			}

		}.$(null);

		if (oid == null) {
			core.log("-- oid is null from url");
			if (sess.getAttribute("openid_ss") != null) {
				String oiddx = (String) sess.getAttribute("openid_ss");
				core.log("--o428_14has sess:" + oiddx);
				return oiddx;
			} else
				core.log("--get oid frm sess yash null");
		}
		core.log("--openid:" + oid);
		System.out.println(">>>>>>>>>>>>>>>>>>openid"+oid);
		// save sess
		sess.setAttribute("openid_ss", oid);
		return oid;
	}

	@Deprecated
	public String getOpenid(HttpServletRequest request,String defaul4test) {
		String oid=getOpenid(request);
		if(oid==null)
			return defaul4test;
		return oid;
		
	}
	/**
	 * @author attilax 老哇的爪子
	 * @since 2014-4-28 上午11:19:05$
	 * 
	 * @param string
	 * @return
	 */@Deprecated
	public String jsonSvs(HttpServletRequest Request) {
		// 上午11:19:05 2014-4-28
		String act = Request.getParameter("act");
		if (act.equals("smash_goldegg_start")) {
			AwdSvs4Sge4PerdayMode c = new AwdSvs4Sge4PerdayMode();
			return c.startBingo(Request);
		} else if (act.equals("awardChoiceNum_sge"))
		{
			AwdSvs4Sge4PerdayMode c=new AwdSvs4Sge4PerdayMode();
			return c.leftNums_4sge(Request);
		}

		else if (act.equals("lookmyawd"))

			return myAwd(Request);

		if (act.equals("postuserinfo")) {
			return postuserinfo(Request);
		}
		// for card only
		if (act.equals("awardChoiceNum"))
			return leftNums(Request);
		return null;
	}

	/**
	 * @author attilax 老哇的爪子
	 * @since 2014-5-8 上午11:01:58$
	 * 
	 * @param request
	 * @return
	 */
	private String postuserinfo(HttpServletRequest request) {
		// attilax 老哇的爪子 上午11:01:58 2014-5-8
		String uid = request.getParameter("openid");
		String actid = request.getParameter("actid");
		AwardWeixin bingoRec = bingoRecode(uid, actid);
		bingoRec.setAwardAddress(request.getParameter("addr"));
		bingoRec.setAwardPhone(request.getParameter("cp"));
		bingoRec.setAwardUserName(request.getParameter("uname"));
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		session.update(bingoRec);
		tx.commit();
		Map<Object, Object> retMap = new HashMap<Object, Object>();
		retMap.put("code", "ok");
		retMap.put("msg", "ok msg..");

		String jsonStr = JSONObject.toJSONString(retMap);
		return jsonStr;

	}

	/**
	 * @author attilax 老哇的爪子
	 * @since 2014-4-30 下午05:35:35$
	 * 
	 * @param request
	 * @return
	 */
	private String myAwd(HttpServletRequest request) {
		// 下午05:35:35 2014-4-30
		String uid = request.getParameter("openid");
		String actid = request.getParameter("actid");
		return myAwd(uid, actid);
	}

	/**perfer day mode
	 * @author attilax 老哇的爪子
	 * @since 2014-4-29 下午05:08:17$
	 * 
	 * @param request
	 * @return
	 */
	private String leftNums_4sge(HttpServletRequest request) {
		core.log("leftNums_4sge");
		String uid = request.getParameter("openid");
		String actid = request.getParameter("actid");
		int awdChoiceNum_SmashGoldEgg = new AwdSvs4SGE().awdChoiceNum_o4;
		if (NoSetAwd(actid)) {
			return NoSetAwdError();
		}
		if (bingoedNum(uid, actid) >= 1) {
			return binoedRet();
		}
		if (!todayJoined(uid, actid)) {
			core.log("--o430: is not today joined");
			geneTodayNewRs(uid, actid, awdChoiceNum_SmashGoldEgg);
			return defFisrtJoinRet(awdChoiceNum_SmashGoldEgg);

		} else {
			core.log("--o430: is  already today joined");
			AwardWeixin joinRec = todayJoinRecode(uid, actid);
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
	 * @author attilax 老哇的爪子
	 * @since 2014-4-30 下午12:31:09$
	 * 
	 * @param uid
	 * @param actid
	 */
	protected AwardWeixin miniOneAwdChoice(String uid, String actid) {
		// 下午12:31:09 2014-4-30
		AwardWeixin awdJoin = todayJoinRecode(uid, actid);
		miniOneAwdChoice(uid, actid, awdJoin);
		return awdJoin;
		// 根据活动获取奖项列表

		// } else // normal
		// {
		//
		// AwardWeixin uex = awardList.get(0);
	}

	/**
	 * @author attilax 老哇的爪子
	 * @since 2014-4-30 下午12:53:37$
	 * 
	 * @param uid
	 * @param actid
	 * @return
	 */
	public AwardWeixin todayJoinRecode(String uid, String actid) {
		// 下午12:53:37 2014-4-30
		Session session = getSession();
		String today_end=DateUtil.getTodayStr()+" 23:59:59";
		String hql = "from AwardWeixin where activityId = "+actid+" and openid='"+uid+"' and createtime>='"+DateUtil.today_Start()+"' and createtime<='"+today_end+"'";
		core.log("--todayJoinRecode hql:"+hql);
		List<AwardWeixin> awardList = null;
		Query q = session.createQuery(hql);
//		q.setParameter(0, actid);
//		q.setParameter(1, uid);
//		q.setParameter(2, DateUtil.today_notime());
		awardList = q.list();

		// if (awardList.size() == 0) // new user add 3tsi
		// {}
		// System.out.println(awardList.size());
		return awardList.get(0);
	}

	public AwardWeixin bingoRecode(String uid, String actid) {
		// 下午12:53:37 2014-4-30
		Session session = getSession();
		String queryString = "from AwardWeixin where activityId =" + actid
				+ " and  award!=null and openid='" + uid + "'";
		Query queryObject = session.createQuery(queryString);
		final List list = queryObject.list();
		AwardWeixin o = (AwardWeixin) list.get(0);

		return o;
	}

	/**
	 * @author attilax 老哇的爪子
	 * @since 2014-4-30 上午11:57:37$
	 * 
	 * @param uid
	 * @param actid
	 * @param awdChoiceNum_SmashGoldEgg
	 * @param joinRec
	 * @return
	 */
	private String otherChoiceNum(String uid, String actid,
			int awdChoiceNum_SmashGoldEgg, AwardWeixin joinRec) {
		// 上午11:57:37 2014-4-30
		Map<Object, Object> retMap = new HashMap<Object, Object>();
		retMap.put("leftCount",  joinRec.getAwardCount());
		retMap.put("alreadyJoinNum",0);
		retMap.put("willBingoAwardId", 0);
		retMap.put("willBingoAwardTitle", "nonex");
		// retMap.put("bingoed", 1);
		String jsonStr = JSONObject.toJSONString(retMap);
		return jsonStr;
		// return null;
	}

	/**
	 * @author attilax 老哇的爪子
	 * @since 2014-4-30 上午11:55:55$
	 * 
	 * @param awdChoiceNum_SmashGoldEgg
	 * @return
	 */
	private String defFisrtJoinRet(int awdChoiceNum_SmashGoldEgg) {
		// 上午11:55:55 2014-4-30

		Map<Object, Object> retMap = new HashMap<Object, Object>();
		retMap.put("leftCount", awdChoiceNum_SmashGoldEgg);
		retMap.put("alreadyJoinNum", 0);
		retMap.put("willBingoAwardId", 0);
		retMap.put("willBingoAwardTitle", "nonex");
		// retMap.put("bingoed", 1);
		String jsonStr = JSONObject.toJSONString(retMap);
		return jsonStr;
		// return null;
	}

	/**
	 * todox query today record
	 * o73 when web pc and db pc datetime is not same ,then alwary ret false
	 * 
	 * @author attilax 老哇的爪子
	 * @since 2014-4-30 上午11:53:53$
	 * 
	 * @param uid
	 * @param actid
	 * @return
	 */@Deprecated
	boolean todayJoined(String uid, String actid) {
		// 上午11:53:53 2014-4-30

		String doday = DateUtil.today();
		Session session = getSession();
		String sqlString = "SELECT   *   FROM  t_mb_awardweixin   where activityId="
				+ actid
				+ " and openId='"
				+ uid
				+ "' and   DATEDIFF(d,createTime,GETDATE())=0";
		// 以SQL语句创建SQLQuery对象
		if(ifMysql())
			sqlString= "SELECT   *   FROM  t_mb_awardweixin   where activityId="
					+ actid
					+ " and openId='"
					+ uid
					+ "' and  date(createTime)   =   curdate(); ";

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
	Integer CanJoinTimesBasenum=3;;
	/**
	 * @author attilax 老哇的爪子
	 * @param awdChoiceNum_SmashGoldEgg
	 * @param actid
	 * @param uid
	 * @since 2014-4-30 上午11:53:50$
	 */
	@Deprecated
	void geneTodayNewRs(String uid, String actid,
			int awdChoiceNum_SmashGoldEgg) {
		// 上午11:53:50 2014-4-30
		Session session = getSession();
		AwardWeixin obj = new AwardWeixin();
		Activity a = new Activity();
		int actid_int = Integer.parseInt(actid);
		a.setId(actid_int);
		obj.setActivity(a);
		obj.setOpenId(uid);
		
		obj.setAwardCount(CanJoinTimesBasenum);
		obj.setCreateTime(DateUtil.today_notime());
		Transaction tx = session.beginTransaction();
		session.save(obj);
		tx.commit();

	}
	
	void geneTodayNewRsO6(String uid, String actid
			) {
		int CanJoinTimesBasenum=CanJoinTimesBasenum_get_byActid(Integer.parseInt(actid));
		// 上午11:53:50 2014-4-30
		Session session = getSession();
		AwardWeixin obj = new AwardWeixin();
		Activity a = new Activity();
		int actid_int = Integer.parseInt(actid);
		a.setId(actid_int);
		obj.setActivity(a);
		obj.setOpenId(uid);
		
		obj.setAwardCount(CanJoinTimesBasenum);
		obj.setCreateTime(DateUtil.today_notime());
		Transaction tx = session.beginTransaction();
		session.save(obj);
		tx.commit();

	}
	
	public Integer CanJoinTimesBasenum_get_byActid(int actid) {
		// attilax 老哇的爪子 下午07:06:53 2014-5-13
		 
		ActivityImpl c = new ActivityImpl();
		Activity act = c.getOneActivity(actid);
		return act.getJoinCount();
	}
	private Integer CanJoinTimesBasenum_get(int actTypeid) {
		// attilax 老哇的爪子 下午07:06:53 2014-5-13
		String activityId = actID(actTypeid,"erro65:nosetAct");
		ActivityImpl c = new ActivityImpl();
		Activity act = c.getOneActivity(Integer.parseInt(activityId));
		return act.getJoinCount();
	}
	
	
/**
 * show alreay bingo and this time cant use..
@author attilax 老哇的爪子
	@since  2014-5-12 上午09:55:19$

 * @return
 */
	public String binoedRet() {
		Map<Object, Object> retMap = new HashMap<Object, Object>();
		retMap.put("leftCount", 0);
		retMap.put("alreadyJoinNum", 999);
		retMap.put("willBingoAwardId", 0);
		retMap.put("willBingoAwardTitle", "nonex");
		retMap.put("bingoed", 1);
		retMap.put("curtimescan",0);
		String jsonStr = JSONObject.toJSONString(retMap);
		return jsonStr;
	}

	AwardWeixin choiceNumOvertimeCheckResult;

	public String overtime() {
		// 下午03:42:33 2014-4-30

		Map<Object, Object> retMap = new HashMap<Object, Object>();
		retMap.put("leftCount",0);
		retMap.put("alreadyJoinNum",99977 );
		retMap.put("willBingoAwardId", 0);
		retMap.put("curtimescan",0 );
		retMap.put("willBingoAwardTitle", "nonex");
		// retMap.put("bingoed", 1);
		String jsonStr = JSONObject.toJSONString(retMap);
		return jsonStr;
		// return null;
	}

	private boolean choiceNumOvertime(String uid, String actid) {
		// 下午02:49:17 2014-4-30
		AwardWeixin joinRec = todayJoinRecode(uid, actid);
		
 		choiceNumOvertimeCheckResult = joinRec;
 		if(joinRec.getAwardCount()<=0)
 			return true;else return false;
//		if (joinRec.getAwardCount() >= awdChoiceNum)
//
//			return true;
//		else
//			return false;
	}

	private String noBingoResult(AwardWeixin joinRec) {
		// 下午03:19:26 2014-4-30
		// int alreadyJoinNum = SafeVal.val(uex.getAwardCount(), 0);
		Map<Object, Object> retMap = new HashMap<Object, Object>();
		retMap.put("leftCount",   joinRec.getAwardCount());
		retMap.put("alreadyJoinNum", 0);
		retMap.put("curtimescan", 1);
		retMap.put("willBingoAwardId", 0);
		retMap.put("willBingoAwardTitle", "nonex");
		retMap.put("noBingoResult", "noBingoResult");
		String jsonStr = JSONObject.toJSONString(retMap);
		return jsonStr;
	}
	/**
	 *  
	 */
boolean testShare=false;
	/**
	 * @author attilax 老哇的爪子
	 * @since 2014-4-28 上午11:23:17$
	 * 
	 * @param request
	 * @return
	 */
@Deprecated
	private String leftNums(HttpServletRequest request) {
		core.log("--o5cc");
		core.logurl(request);
		// 上午11:23:17 2014-4-28
		String uid = request.getParameter("openid");
		core.log("--o429: uid (openid) ::" + uid);// ajax
		String actid = request.getParameter("actid");
		if (NoSetAwd(actid)) {
			return NoSetAwdError();
		}
		if(!testShare)
		if (bingoedNum(uid, actid) >= 1) {
			
		 	return binoedRet();
		}

		if (!todayJoined(uid, actid)) {
			core.log("--o54: is not today joined");
			geneTodayNewRs(uid, actid, awdChoiceNum);
		}

		if (choiceNumOvertime(uid, actid)) {
			return overtime();
		}

		Awardx myAward = startAward(Integer.parseInt(actid), uid);
		if (myAward == null) {
			AwardWeixin joinRec = todayJoinRecode(uid, actid);
			String ret=noBingoResult(joinRec);
			 miniOneAwdChoice(uid, actid);
			return  ret;

		}

		//o5c feodg fornt ,if bingo ,can get ori choice number.
	String binoedResultO5_s=	binoedResultO5(myAward,uid,actid);
		if(!testShare)
		{
		miniAllAwdChoice(uid, actid);
		}
		
		setAwdTimeNid(myAward, uid, actid);

		return	binoedResultO5_s;
	//	return binoedResult(myAward);

	}

	/**
	@author attilax 老哇的爪子
		@since  2014-5-13 下午05:40:08$
	
	 * @param myAward
	 * @param uid
	 * @param actid
	 */
	private String binoedResultO5(Awardx myAward, String uid, String actid) {
		// attilax 老哇的爪子  下午05:40:08   2014-5-13 
		
		AwardWeixin  JoinRecode =todayJoinRecode(uid, actid);
		
		 
		Map<Object, Object> retMap = new HashMap<Object, Object>();
		retMap.put("leftCount", JoinRecode.getAwardCount());
		retMap.put("alreadyJoinNum", 0);
		retMap.put("willBingoAwardId", myAward.getId());
		retMap.put("willBingoAwardTitle", myAward.getName());
		retMap.put("bingoed", 1);
		retMap.put("curtimescan", 1);
		
		String jsonStr = JSONObject.toJSONString(retMap);
		return jsonStr;
	}

	/**
	@author attilax 老哇的爪子
		@since  2014-5-12 上午10:00:56$
	
	 * @param uid
	 * @param actid
	 */
	public void miniAllAwdChoice(String uid, String actid) {
		// attilax 老哇的爪子  上午10:00:56   2014-5-12 
		AwardWeixin awdJoin = todayJoinRecode(uid, actid);
	//	miniOneAwdChoice(uid, actid, awdJoin);
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		 
		awdJoin.setAwardCount(0);
		session.update(awdJoin);
		tx.commit();
	//	return awdJoin;
	}

	/**
	 * show bino and thid time can use ..
	@author attilax 老哇的爪子
		@since  2014-5-12 上午09:55:50$
	
	 * @param myAward
	 * @return
	 */
	public String binoedResult(Awardx myAward) {
		Map<Object, Object> retMap = new HashMap<Object, Object>();
		retMap.put("leftCount", 0);
		retMap.put("alreadyJoinNum", 0);
		retMap.put("willBingoAwardId", myAward.getId());
		retMap.put("willBingoAwardTitle", myAward.getName());
		retMap.put("bingoed", 1);
		retMap.put("curtimescan", 1);
		
		String jsonStr = JSONObject.toJSONString(retMap);
		return jsonStr;
	}

	private void setAwdTimeNid(Awardx myAward, String uid, String actid) {
		// 下午03:23:42 2014-4-30
		AwardWeixin joinRec = todayJoinRecode(uid, actid);
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
	 * rondam awd and jude awdOver
	 * 
	 * @author attilax 老哇的爪子
	 * @param actid
	 * @param uid
	 * @since 2014-4-29 上午08:48:17$
	 * 
	 * @return
	 */
	protected Awardx startAward(Integer actid, String uid) {

		if(!testShare)
		if (bingoedNum(uid, actid.toString()) >= perUserMaxBigonNum) {
			return null;
		}

		List<ActAward> li = AwdListByActid(actid);
		
		//conver to attilax awd list format
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
		
		
		//o5c 4 test mujstbin
		if(uid.equals("testMustBin"))
		{
			List<Awardx> li2=listUtil.map_generic(li_fnl,
					new Func_4SingleObj<Awardx, Awardx>() {

				@Override
				public Awardx invoke(Awardx o) {
					// 上午08:53:09 2014-4-29
					Awardx thisAwd = o;
					Awardx awd = new Awardx();
					awd.id = thisAwd.getId();
					awd.name = thisAwd.getName();
					awd.prbblt = 100;
					return awd;
				}
			});
			li_fnl=li2;
		}
		
		
		Awardx awd = com.attilax.award.AwdSvs.getBingoAwd(li_fnl);
		if (awd == null)
			return null;
		if (awdOver(awd)) {
			if(uid.equals("testMustBin"))//o5c
				return awd;
			return null;
		} else
			return awd;
	}

	/**
	 * @author attilax 老哇的爪子
	 * @since 2014-4-29 上午10:40:37$
	 * 
	 * @param uid
	 * @return
	 */
	public int bingoedNum(String uid, String actID) {
		// 上午10:40:37 2014-4-29
		// 检查该奖项是否还可以送
		Session session = getSession();
		String checkHql = "select count(*) as bingoNum from AwardWeixin where awardId!=null and  openid=? and activityId="
				+ actID;
		Query cq = session.createQuery(checkHql);
		cq.setParameter(0, uid);
		// cq.list()
		Long count = (Long) cq.uniqueResult();
		// 该奖项还有余额

		return Integer.parseInt(count.toString());
	}

	/**
	 * @author attilax 老哇的爪子
	 * @since 2014-4-29 上午09:56:22$
	 * 
	 * @param awd
	 * @return
	 */
	private boolean awdOver(Awardx shootAward) {
		// 上午09:56:22 2014-4-29
		// 检查该奖项是否还可以送
		Session session = getSession();
		String checkHql = "select count(*) as leftCount from AwardWeixin where awardId = ?";
		Query cq = session.createQuery(checkHql);
		cq.setParameter(0, shootAward.getId());

		Long count = (Long) cq.uniqueResult();
		// 该奖项还有余额
		ActAward awd = (ActAward) session.get(ActAward.class, shootAward.id);
		// int cfCount=
		core.log(("--o42909: nowSendCount--AllCfgCount:" + count.toString()
				+ "---" + awd.getAwardCount().toString()));
		if (count < awd.getAwardCount())
			return false;
		else
			return true;

	}

	/**
	 * @author attilax 老哇的爪子
	 * @since 2014-4-28 下午02:24:17$
	 * 
	 * @param uid
	 * @param actid
	 * @param uex
	 */
	protected void miniOneAwdChoice(String uid, String actid, AwardWeixin uex) {
		// 下午02:24:17 2014-4-28
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		int nowArdNum = SafeVal.val(uex.getAwardCount(), 0);
		int NExtNum;
		if (nowArdNum < 0)
			NExtNum = 0;
		else
			NExtNum = uex.getAwardCount() - 1;

		uex.setAwardCount(NExtNum);
		session.update(uex);
		tx.commit();

	}
	
	protected int addShare(String uid) {
		// 下午02:24:17 2014-4-28
		Session session = getSession();
		Transaction tx = session.beginTransaction();
	Share o=new Share();
	o.setOpenid(uid);
	 
		session.save(o);
		tx.commit();
		return o.getId();

	}

	/**
	 * @author attilax 老哇的爪子
	 * @since 2014-4-28 下午01:45:55$
	 * 
	 * @param retMap
	 * @return
	 */
	protected String toStr(Object retMap) {
		String jsonStr = JSONObject.toJSONString(retMap);
		return jsonStr;
	}

	public List<ActAward> AwdListByActid(int activityId) {
		Session session = getSession();
		Transaction tx = null;

		// tx = session.beginTransaction();
		// 根据活动获取奖项列表
		String hql = "from ActAward where activityId = ?";
		List<ActAward> awardList = null;
		Query q = session.createQuery(hql);
		q.setParameter(0, activityId);
		awardList = q.list();
		return awardList;
	}

}
