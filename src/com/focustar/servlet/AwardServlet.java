package com.focustar.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.attilax.net.requestImp;
import com.attilax.util.god;
import com.focustar.dao.impl.AwardImpl;
import com.focustar.entity.ActAward;
import com.focustar.entity.AwardWeixin;
import com.focustar.service.AwdSvs4bigwheel4Perdaymode;
import com.focustar.util.AwardConstant;
import com.opensymphony.xwork2.inject.Inject;

public class AwardServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8628160497935366911L;
	
	
	private static Logger logger = Logger.getLogger(AwardServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String awardWxId = req.getParameter("awardWxId");
		
		Map<Object,Object> retMap = new HashMap<Object,Object>();
		try{
		retMap.put("leftCount", leftNums(req));
		}catch(Exception e)
		{
			
			String jsonStr= JSONObject.toJSONString(e);
			outputS(req, resp, jsonStr);
			return;
		}
		if(awardWxId!= null && !"".equals(awardWxId)){
			logger.info("请求抽奖记录的可抽奖次数   >>>> " + awardWxId);
			Integer awxId = Integer.parseInt(awardWxId);
			AwardImpl awardDao = new AwardImpl();
			try{
				// 查询用户抽奖记录
				AwardWeixin aWx = awardDao.getOneAwardWeixin(awxId);
				if(aWx != null){
					//可抽奖次数
					retMap.put("leftCount", aWx.getAwardCount());
					//已中奖
					if(aWx.getAward() != null){
						retMap.put("award",aWx.getAward());
					}
					if(aWx.getActivity() != null){
						retMap.put("activity", aWx.getActivity());
					}
					logger.info("可抽奖次数   >>>> " + aWx.getAwardCount());
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			String jsonStr = JSONObject.toJSONString(retMap);
			
			
			outputS(req, resp, jsonStr);
			
		}
	}

	private void outputS(HttpServletRequest req, HttpServletResponse resp, String jsonStr) throws UnsupportedEncodingException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json;charset=UTF-8");

		
		resp.addHeader("pragma","no-cache");
		resp.addHeader("cache-control","no-cache");
		resp.addHeader("expires","0");
		
		PrintWriter out = resp.getWriter();
		out.print(jsonStr);
		out.flush();
		out.close();
	}
	
	/**
	@author attilax 老哇的爪子
		@since  o0h 5_51_9   
	
	 * @param req
	 * @return
	 */
	private int leftNums(HttpServletRequest req) {
		// attilax 老哇的爪子  5_51_9   o0h 
		String s=this.awdSvs.leftNums(req);
		net.sf.json.JSONObject jo=net.sf.json.JSONObject.fromObject(s);
		if(jo.get("code")!=null)
		{
			if(jo.get("code").toString().trim().startsWith("e:"))
				throw new RuntimeException(jo.get("code").toString()+"  "+jo.get("msg"));
		}
		return jo.getInt("leftCount");
		
	}

	//中奖了
	private final static String AW_GOODLUCK= "88888888";
	
	//没有中奖
	private final static String AW_NOSHOOT = "1";
	
	//可抽奖次数用完
	private final static String  AW_NOCOUNT = "0";
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("application/json;charset=UTF-8");
		
		//活动类型
		String awardType = req.getParameter("awardType");
		//当前抽奖的微信用户
		String openId = req.getParameter("openid");
		//当前抽奖记录id
		String awardWxId = req.getParameter("awardWxId");
		//活动id
		String activityId = req.getParameter("activityId");
		if(activityId==null || activityId.trim().length()==0)
			throw new RuntimeException("act id is null"); 
		
		try {
			String jsonStr = startDraw(awardWxId,activityId,openId);			
			outputS(req, resp, jsonStr);return;
		}catch(Exception e)
		{
			
			String jsonStr= JSONObject.toJSONString(e);
			outputS(req, resp, jsonStr);
			return;
		}
		
		
//		req.setCharacterEncoding("UTF-8");
//		resp.setCharacterEncoding("UTF-8");
//		
//		resp.addHeader("pragma","no-cache");
//		resp.addHeader("cache-control","no-cache");
//		resp.addHeader("expires","0");
//		PrintWriter out = resp.getWriter();
//		out.print(jsonStr);
//		out.flush();
//		out.close();
	}
	
	
	/**
	 * @category 刮刮卡游戏概率计算
	 * @param awardWxId
	 * @param activityId
	 * @param openId
	 * @return
	 */
	private String cardGame(String awardWxId,String activityId,String openId){
		
		return "";
	}
	public static void main(String[] args) {
		RuntimeException e=new RuntimeException("e:noSetAwdErr此活动还没设置奖品哟.");
		 String s= JSONObject.toJSONString(e);
		 System.out.println(s);
		 //{"@type":"java.lang.RuntimeException","localizedMessage":"e:noSetAwdErr此活动还没设置奖品哟.",
		 //"message":"e:noSetAwdErr此活动还没设置奖品哟.",
		 //"stackTrace":[{"className":"com.focustar.servlet.AwardServlet","fileName":"AwardServlet.java",
		 //"lineNumber":132,"methodName":"main","nativeMethod":false}],"suppressed":[]}
		 //{"@type":"java.lang.RuntimeException","localizedMessage":"e:noSetAwdErr此活动还没设置奖品哟.","message":"e:noSetAwdErr此活动还没设置奖品哟.","stackTrace":[{"className":"com.focustar.servlet.AwardServlet","fileName":"AwardServlet.java","lineNumber":132,"methodName":"main","nativeMethod":false}],"suppressed":[]}


	}
	@Inject
	AwdSvs4bigwheel4Perdaymode awdSvs=new AwdSvs4bigwheel4Perdaymode();
	
	/**
	 * @category 大转盘的概率计算
	 * @param awardWxId
	 * @param activityId
	 * @param openId
	 * @return
	 */
	private String  startDraw(String awardWxId,String activityId,String openId){
		requestImp ri=new requestImp();
		ri.setParam("actid", activityId);
		ri.setParam("openid",openId);
		//try {
			return awdSvs.startDraw(activityId,openId);
//		} catch (Exception e) {
//			return god.getTrace(e);
//		}
		
		
	}



	
	

}
