package com.focustar.thread;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

import com.focustar.util.ConfigService;
import com.focustar.util.MD5;
import com.focustar.util.MyCacher;
import com.focustar.util.WeixinUtil;


/***
 * @category 检查当前设置的工作时间，如果在工作时间后，默认返回一句回复
 * @author Administrator
 *
 */

public class CheckWorkJober extends Jober {
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
	private final static Logger log = Logger.getLogger(CheckWorkJober.class);
	
	public CheckWorkJober(String openId){
		this.openId = openId;
	}

	@Override
	protected void startWork() {
		
		//是否需要缓存若干分钟后，再显示？
		
		StringBuilder chkKey = new StringBuilder();
		chkKey.append(this.openId).append("off_work");
		
		String chkCache = (String)MyCacher.getInstance().getCache(MD5.getMD5((chkKey.toString())));
		
		if(chkCache == null){
		
				if(checkOffWork()){
				
						ConfigService cfg = new ConfigService();
						//获取下班的回复语
						String feedbackStr = cfg.getMsgProperty("OFF_WORD");
						
						if(feedbackStr != null && !"".equals(feedbackStr)){
							
							msgObj = WeixinUtil.buildTextMessage(this.openId, feedbackStr);
							
							//缓存10分钟
							MyCacher.getInstance().putCache(MD5.getMD5(chkKey.toString()),"1",60 * 20);
						}
						else{
							logger.info("无设置，下班回复语！");
						}
				
				
				}else{
					logger.info("现在是工作时间!!");
					
					ConfigService cfg = new ConfigService();
					String feedbackStr = cfg.getMsgProperty("ON_WORD");
					
					if(feedbackStr != null && !"".equals(feedbackStr)){
						msgObj = WeixinUtil.buildTextMessage(this.openId, feedbackStr);
					}else{
						logger.info("无设置上班回复语！");
					}
					
				}
		
		}else{
			logger.info("已缓存处理过...");
		}

	}
	
	private static boolean checkOffWork(){
		ConfigService cfg = new ConfigService();
		String startWork = cfg.getWxProperty("WORK_START");
		String endWork = cfg.getWxProperty("WORK_END");
		Date currentDate = new Date();
		
		GregorianCalendar  cal = new GregorianCalendar();
		cal.setTime(currentDate);
		cal.set(Calendar.YEAR, 1970);
		cal.set(Calendar.MONTH, 1);
		cal.set(Calendar.DAY_OF_YEAR, 1);
		long curLong = cal.getTime().getTime();
		long startLong = 0;
		long endLong = 0;
		try {
			System.out.println(cal.getTime());
			Date startDate = (Date) sdf.parseObject(startWork);
			System.out.println(startDate);
			Date endDate = (Date) sdf.parseObject(endWork);
			System.out.println(endDate);
			startLong = startDate.getTime();
			endLong = endDate.getTime();
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(startWork != null && !"".equals(startWork)
				&& endWork != null && !"".equals(endWork)){
			if(curLong <  startLong || curLong > endLong){
				return true;
			}else{
				logger.info("已经上班了...");
			}
			
		}else{
			log.info("没有设置正确的上班时间与下班时间");
		}
		return false;
	}
	
	public static void  main(String args[]){
		
		checkOffWork();
		
	}

}
