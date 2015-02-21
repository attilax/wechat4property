/**
 * @author attilax 老哇的爪子
	@since  2014-6-11 下午02:12:31$
 */
package com.focustar.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.alibaba.fastjson.JSONObject;
import com.attilax.core;
import com.attilax.err.errx;
import com.attilax.net.websitex;
import com.attilax.util.DateUtil;
import com.attilax.util.tryX;
import com.focustar.dao.impl.ActivityImpl;
import com.focustar.dao.impl.NewsImpl;
import com.focustar.entity.ActAward;
import com.focustar.entity.Activity;
import com.focustar.entity.Share;
import com.focustar.entity.TMbActivityHistory;
import com.focustar.entity.TMbWeixinuser;
import com.focustar.util.BaseImpl;
import com.focustar.util.ConfigService;

/**
 * @author attilax 老哇的爪子
 *@since 2014-6-11 下午02:12:31$
 */
public class AwdSvsBase extends BaseImpl {

	/**
	 * @author attilax 老哇的爪子
	 * @since 2014-6-11 下午02:12:31$
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// attilax 老哇的爪子 下午02:12:31 2014-6-11
		
//	new AwdSvsBase().	setRate_byActid (999,10);
	System.out.println(new AwdSvsBase().	actByTypeid (5).getActName()); 
	
	
	System.out.println("---");

	}

	/**
	 * @author attilax 老哇的爪子
	 * @since 2014-6-4 上午10:23:34$
	 * 
	 * @param uid
	 * @return
	 */
	public TMbWeixinuser findUserByOpenid(String uid) {
		// attilax 老哇的爪子 上午10:23:34 2014-6-4
		Session session = getSession();
		String today_end = DateUtil.getTodayStr() + " 23:59:59";
		String hql = "from TMbWeixinuser where     openid='" + uid + "'";

		List<TMbWeixinuser> awardList = null;
		Query q = session.createQuery(hql);
		// q.setParameter(0, actid);
		// q.setParameter(1, uid);
		// q.setParameter(2, DateUtil.today_notime());
		awardList = q.list();

		// if (awardList.size() == 0) // new user add 3tsi
		// {}
		// System.out.println(awardList.size());
		return awardList.get(0);
		// return null;
	}

	/**
	 * /**
	 * 
	 * @author attilax 老哇的爪子
	 * @since 2014-6-4 上午09:46:30$
	 * 
	 * @param uid
	 * @param actid
	 */
	public void actHstrLog(final String uid, final String actid) {
		// attilax 老哇的爪子 上午09:46:30 2014-6-4
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
			 * @since 2014-6-11 下午01:57:09$
			 * 
			 * @param user
			 * @return
			 */
			@SuppressWarnings("all")
		 
			private int groupid(final TMbWeixinuser user) {
				// attilax 老哇的爪子 下午01:53:05 2014-6-11

				return new tryX<Integer>() {

					@Override
					public Integer item(Object t) throws Exception {
						// attilax 老哇的爪子 下午01:54:24 2014-6-11
						Integer groupid = user.getGroup().getGroupid();

						core.log("-- o6a1 groupid in item:"
								+ String.valueOf(groupid));
						return groupid;
					}
				}.$(new NewsImpl().checkProvinceAndCity(user.getProvince(),
						user.getCity()).getGroupid());

			}

		}.$("");

	}
	// attilax 老哇的爪子 下午02:12:31 2014-6-11

	/**
	@author attilax 老哇的爪子
		@since  2014-6-27 下午2:13:09$
	
	 * @param request
	 * @return 
	 */
	public String delTestData(HttpServletRequest request) {
	// attilax 老哇的爪子  下午2:13:09   2014-6-27 
		String type=request.getParameter("type");
		String	openid=getOpenid(request,"atioid");
		delTestDate(openid);
		com.attilax.err.errx e=new com.attilax.err.errx();
	return  com.attilax.err.errx.defOK();
	}
	

	/**
	@author attilax 老哇的爪子
		@since  2014-6-27 下午3:01:26$
	
	 * @return
	 */
 

	/**
	@author attilax 老哇的爪子
		@since  2014-6-27 下午2:25:07$
	
	 * @param openid
	 */
	private void delTestDate(String openid) {
	// attilax 老哇的爪子 下午2:25:07 2014-6-27

	Session session = null;
	Transaction tx = null;
	try {
		session = getSession();
		tx = session.getTransaction();
		session.beginTransaction();
		//todox o622 cant del  ori  is delete AwardWeixin s where s.openid=
		//must delete alias then can take eff..
		String hql = "delete AwardWeixin  where openid='" + openid + "'";
		core.log(hql);
		Query query = session.createQuery(hql);
		// query.setInteger(0, 1);
		query.executeUpdate();
		tx.commit();
	} catch (HibernateException e) {
		tx.rollback();
		e.printStackTrace();
	} finally {
		// HibernateUtils.closeSession(session);
	}

	}

	public String getOpenid(HttpServletRequest request,String defaul4test) {
		String oid=getOpenid(request);
		if(oid==null)
			return defaul4test;
		return oid;
		
	}
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
	 

	// save sess
	sess.setAttribute("openid_ss", oid);
	return oid;
	
}

	/**
	@author attilax 老哇的爪子
		@since  2014-6-27 下午2:30:26$
	
	 * @param request
	 * @return 
	 */
	public String setrate(HttpServletRequest request) {
	// attilax 老哇的爪子 下午2:30:26 2014-6-27
	Integer rate = Integer.parseInt(request.getParameter("rate"));
	Integer acttype = Integer.parseInt(request.getParameter("acttype"));
//   上午8:50:52 2014-6-30  老哇的爪子  Attilax
	// ActAward
	Activity  act=actByTypeid(acttype);
	setRate_byActid(rate,act.getId());
	// TMbActAward
//	int awdID = 2;
//	if(acttype==5)
//		awdID=8;
//	setRate(rate, awdID);
	//
	return errx.defOK();
	}

	private void setRate(Integer rate, int awdID) {
		ActAward o = (ActAward) getSession().get(ActAward.class, awdID);
		Session session = getSession();
		Transaction tx = session.beginTransaction();

		o.setRate(rate);

		session.update(o);
		tx.commit();
	}
	
	/**
	 * 
	@author attilax 老哇的爪子
		@since  o74 W425$
	
	 * @param rate
	 * @param actID
	 */
	public void setRate_byActid(Integer rate, int actID) {
	// attilax 老哇的爪子 下午2:25:07 2014-6-27

	Session session = null;
	Transaction tx = null;
	 
		session = getSession();
		tx = session.getTransaction();
		session.beginTransaction();
		//todox o622 cant del  ori  is delete AwardWeixin s where s.openid=
		//must delete alias then can take eff..
		String hql = "update ActAward set rate="+rate.toString()+"  where activityid=" + String.valueOf(actID) + "";
		core.log(hql);
		Query query = session.createQuery(hql);
		// query.setInteger(0, 1);
		query.executeUpdate();
		tx.commit();
	 

	}
	
	
	/**
	 * 
	@author attilax 老哇的爪子
		@since  o74 Wr58$
	
	 * @param actTypeId
	 * @return
	 */
	public Activity actByTypeid(final int actTypeId ) {

	 
		
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
		 
		return li.get(0);

	}

}

// attilax 老哇的爪子