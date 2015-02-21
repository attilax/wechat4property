/**
 *  * @author  attilax 老哇的爪子
 *@since  2014-6-25 上午11:07:09$
 */
package com.focustar.service;

import static com.attilax.core.log;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import com.alibaba.fastjson.JSONObject;
import com.focustar.dao.impl.AwardImpl;
import com.focustar.entity.ActAward;
import com.focustar.entity.AwardWeixin;
import com.focustar.servlet.AwardServlet;
import com.focustar.util.AwardConstant;

/**
 *  * @author  attilax 老哇的爪子
 *@since  2014-6-25 上午11:07:09$
 *
 */
public class AwdSvs4bigwheel4Perdaymode extends AwdSvs4Sge4PerdayMode {
	private static Logger logger = Logger.getLogger(AwdSvs4bigwheel4Perdaymode.class);
	/**
	 * @param args
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-25 上午11:07:09$
	 */
	public static void main(String[] args) {
		// attilax 老哇的爪子  上午11:07:09   2014-6-25 
		

	}
	//  attilax 老哇的爪子 上午11:07:09   2014-6-25   
	
	public String leftNums(HttpServletRequest request)  {
		 return this.leftNums_4sge(request);
	 
	}


	/**
	@author attilax 老哇的爪子
		@since  2014-6-25 上午11:36:27$
	
	 * @param request
	 * @return
	 */
	public String startDraw( String activityId, String openId) {
		String uid=openId;
		Map<Object,Object> retMap = new HashMap<Object,Object>();
	//	if(awardWxId != null && !"".equals(awardWxId)){
		//	logger.info("抽奖记录存在   " + awardWxId);
		//这个if判断当前活动有没有奖品换
		if (NoSetAwd(activityId)) {
			RuntimeException e=new RuntimeException("e:noSetAwdErr此活动还没设置奖品哟.");
			 String s= JSONObject.toJSONString(e);
			return  s;
			 }
		String actid = activityId;
		String logPix = "," + uid + ",actid:" + actid;
		log("--check bingoedNum," + logPix);
		//这个if判断当前用户有没有中过奖
		if (bingoedNum(uid, actid) >= 1) {
			log("--bioned," + logPix);
			RuntimeException e=new RuntimeException("e:alreadyAwded已经中奖过了");
			 String s= JSONObject.toJSONString(e);
			return  s;
		}
		log("--check choiceNumOvertime," + logPix);
		if (choiceNumOvertime(uid, actid)) {
			log("--choiceNumOvertime ed," + logPix);
			// cant draw ,cause over times
			logger.info("可抽奖次数  0");
			retMap.put("code", AW_NOCOUNT);

			// 请求成功
			retMap.put("flag", true);
			return JSONObject.toJSONString(retMap);
			// return overtime();
		}
		
 			AwardImpl awardDao = new AwardImpl();
//			//抽奖记录id
//			int awxId =0;// Integer.parseInt(awardWxId);
//			AwardWeixin aWx = awardDao.getOneAwardWeixin(awxId);
 			AwardWeixin aWx=	choiceNumOvertimeCheckResult;
//			
//			//for test
////			if(aWx==null)
////			{
////				AwardWeixin
////			}
//			
//			if(aWx.getAwardCount() > 0){//可以抽奖
//				logger.info("可抽奖次数   " + aWx.getAwardCount());
				//活动id
				int actId = Integer.parseInt(activityId);
				
				Map<String,Object> awardMap = awardDao.getRandAward(actId,aWx,openId);
				//oog  {award=null, leftCount=3}   is map txt
				Integer leftCount = (Integer) awardMap.get("leftCount");
				
				retMap.put("leftCount",leftCount);
				
				ActAward  myAward = (ActAward) awardMap.get("award");
				
				List<Integer[]>  angleList = null;
				if(myAward != null){//bingo process
					
					logger.info("中奖    >>> " + myAward.getAwardName());
					logger.info("中奖用户  >>>> " + openId);
					
					retMap.put("award", myAward);
					//取中奖的角度
					StringBuilder key = new StringBuilder();
					key.append("award").append(myAward.getOrderBy());
					angleList = AwardConstant.AWARD_RORATE_MAP.get(key.toString());
					int size = angleList.size();
					
					//多个角度
					if(size > 1){
						logger.info("多个中奖角度");
						Random r = new Random();
						int randNum = r.nextInt(size);
						//多个角度中的一个
						Integer[] oneAngle = angleList.get(randNum);
						r = new Random();
						//开始角度与结束角度之间产生一个随机值
						int angleRand = r.nextInt(oneAngle[1]-oneAngle[0])+oneAngle[0];
						logger.info("随机角度   >>> " + angleRand);
						retMap.put("angle", angleRand);
						
					}else if(size == 1){
						logger.info("一个中奖角度");
						Integer[] oneAngle = angleList.get(0);
						Random r = new Random();
						//开始角度与结束角度之间产生一个随机值
						int angleRand = r.nextInt(oneAngle[1]-oneAngle[0])+oneAngle[0];
						retMap.put("angle", angleRand);
						
						logger.info("随机角度   >>> " + angleRand);
					}
					retMap.put("code", AW_GOODLUCK);
					
				}else{
					
					logger.info("取没有中奖的角度");
					//取没有中奖的角度  award4 zeush ma ward ang
					angleList = AwardConstant.AWARD_RORATE_MAP.get("award4");
					int size = angleList.size();
					Random r = new Random();
					int randNum = r.nextInt(size);
					Integer[] oneAngle = angleList.get(randNum);
					r = new Random();
					int angleRand = r.nextInt(oneAngle[1]-oneAngle[0])+oneAngle[0];
					logger.info("没有中奖的随机角度  >>>> " + angleRand);
					retMap.put("angle", angleRand);
					retMap.put("code", AW_NOSHOOT);
				}

		 
			//请求成功
			retMap.put("flag",true);
	//	}  //endif
		
		return JSONObject.toJSONString(retMap);
	}
	
	//中奖了
	private final static String AW_GOODLUCK= "88888888";
	
	//没有中奖
	private final static String AW_NOSHOOT = "1";
	
	//可抽奖次数用完
	private final static String  AW_NOCOUNT = "0";
}

//  attilax 老哇的爪子