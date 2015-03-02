package com.focustar.thread;

import java.net.Authenticator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.focustar.dao.impl.ActivityImpl;
import com.focustar.dao.impl.UserImpl;
import com.focustar.entity.Activity;
import com.focustar.entity.AwardWeixin;
import com.focustar.entity.THDMember;
import com.focustar.entity.TMbShare2another;
import com.focustar.entity.TMbWeixinuser;
import com.focustar.entity.hd.HDCredit;
import com.focustar.entity.hd.HDMember;
import com.focustar.entity.hd.ScoreSubject;
import com.focustar.entity.hd.ScoreType;
import com.focustar.entity.hd.Scoreaccount;
import com.focustar.entity.hd.SubjectAccount;
import com.focustar.service.hd.HDService2;
import com.focustar.service.hd.MyAuthenticator;


/**
 * @category 检查是否有活动举行
 * @author Administrator
 *
 */
public class CheckActivityJober implements Runnable {
	
	private final static Logger logger = Logger.getLogger(CheckActivityJober.class);
	
	private String openid;
	public final static int ACTIVITY_TYPE_GIVE = 0;
	public final static int ACTIVITY_TYPE_CREDIT = 1;
	public final static int ACTIVITY_TYPE_SHARE = 2;
	private boolean isFirstBind = false;
	private int addCount = 0;
	ActivityImpl actDao = new ActivityImpl();
	AwardWeixin curAwardWx = null;
	AwardWeixin toAwardWx = null;
	//private String toOpenid;
	private int shareId = 0;
	
	private String bindServerUrl;
	private TMbShare2another share;
	
	private CountDownLatch countDown;
	
	/**
	 * 0 赠送抽奖次数
	 * 1 赠送积分
	 * 2 分享
	 */
	
	private int  operType = 0;
	
	public CheckActivityJober(String openid){
		logger.info("开始赠送抽奖次数");
		this.openid = openid;
	}
	
	public CheckActivityJober(String openid,int operType){
		this(openid);
		this.operType = operType;
	}
	
	/*public CheckActivityJober(String openid,int operType,String toOpenid){
		this(openid,operType);
		this.toOpenid = toOpenid;
	}*/
	
	public CheckActivityJober(String openid,int operType,int shareId){
		this(openid,operType);
		this.shareId = shareId;
	}
	
	public CheckActivityJober(String openid,int operType,boolean isFirstBind,String bindServerUrl){
		this(openid,operType);
		this.isFirstBind = isFirstBind;
		this.bindServerUrl = bindServerUrl;
	}

	public void run() {
		
		try{
		
		TMbWeixinuser user = null;//(TMbWeixinuser)MyCacher.getInstance().getCache(MD5.getMD5(openid));
		
		if(user == null){
			UserImpl userDao = new UserImpl();
			user = userDao.getUserByopenid(openid);
		}
		
		//Activity curActivity = actDao.getCurrentActivity();
		
		List<Activity> activityList = actDao.getActivityList();
		
		if(activityList != null && activityList.size() > 0){
		
			for(Activity curActivity : activityList){
		
			if(curActivity != null){
			logger.info("正在执行活动  >>> " + curActivity.getActName());
			logger.info("开始检查活动的相关设置  >>>>>>");
			
			logger.info("赠送抽奖形式  >>>> " + curActivity.getJoinType());
			logger.info("赠送抽奖次数 >>>> " + curActivity.getJoinCount());
			
			if(this.shareId >= 100000){
				share = actDao.getSharebyId(this.shareId);
			}
			
			switch(curActivity.getJoinType()){
			case Activity.ACT_JOIN_EVERYBODY://每人
				
				//获取某人在某个活动中的可抽奖次数
				AwardWeixin awPerson = actDao.getAwardWeixinByOpenid(curActivity.getId(),this.openid);
				if(awPerson != null){
					curAwardWx = awPerson;
				}
				
				//判断是否分享
				if(this.shareId >= 100000 && share != null){
					toAwardWx = actDao.getAwardWeixinByOpenid(curActivity.getId(),share.getOpenid());
				}
				
				break;
			case Activity.ACT_JOIN_EVERYDAY://每天
				
				//获取某人在某个活动中，当天可抽奖次数
				AwardWeixin awDay = actDao.getAwardWeixinByDay(curActivity.getId(),this.openid);
				if(awDay != null){
					curAwardWx = awDay;
				}
				
				//判断是否分享
				if(this.shareId >= 100000 && share != null){
					toAwardWx = actDao.getAwardWeixinByOpenid(curActivity.getId(),share.getOpenid());
				}
				
				break;
			default:
				logger.info("未知类型 ===>"+curActivity.getJoinType());
				break;
		}
			
			if(this.operType == ACTIVITY_TYPE_GIVE){
				logger.info("检查是否举行活动...");
						if(curAwardWx == null){
							logger.info("当前用户["+this.openid+"]，还没有抽奖记录..开始添加");
							//添加抽奖记录
							addAwardWeixin(curActivity);
						}else{
							logger.info("当前用户["+this.openid+"]，已有抽奖记录");
						}
			}else if(this.operType == ACTIVITY_TYPE_CREDIT){
				logger.info("检查是否赠送积分...");
				//检查该会员是否已绑定过
				if(isFirstBind){
					logger.info("该用户["+user.getOpenid()+"],首次绑定会员["+user.getMember().getCardNo()+"],赠送抽奖");
						//赠送一次抽奖？
						if(curAwardWx == null){
							logger.info("添加抽奖记录，并多加一次");
							addAwardWeixinMore(curActivity,1);
						}else{
							logger.info("原有的抽奖记录上，加一次");
							curAwardWx.setAwardCount(curAwardWx.getAwardCount() + 1);
							actDao.updateAwardWeixin(curAwardWx);
						}
						//检查是否开启赠送积分设置
						if(curActivity.getIsCredit() != null && curActivity.getIsCredit() == 1){
							logger.info("设置赠送积分..");
							//logger.info("是否赠送积分  >>>> " + )
							if(user.getMember() != null){
								logger.info("当前粉丝["+user.getOpenid()+"],会员号["+user.getMember().getCardNo()+"]");
								int giveCredit = curActivity.getCredit();
								addCredit(user.getMember(),giveCredit);
							}else{
								logger.info("此粉丝===>没有绑定会员,不应该到这个逻辑处理！");
							}
						}else{
							logger.info("设置无赠送积分..");
						}
				
				}else{
					logger.info("该用户["+user.getOpenid()+"],已经绑定过会员["+user.getMember().getCardNo()+"],不能再赠送抽奖");
				}
				
			}else if(this.operType == ACTIVITY_TYPE_SHARE){
				logger.info("检查是否分享...");
				if(curActivity.getIsShare() != null && curActivity.getIsShare().intValue() == 1){
					
					if( this.shareId >= 100000 && share != null){
							logger.info("该分享id["+this.shareId+"] 找到源分享用户["+share.getOpenid()+"]分享记录");
								
										//赠送一次抽奖？
										if(curAwardWx == null){
											logger.info("当前用户["+this.openid+"]添加抽奖记录，并多加一次");
											addAwardWeixinMore(curActivity,1);
										}else{
											logger.info("当前用户["+this.openid+"]，原有的抽奖记录上，加一次");
											int newAwardCount = curAwardWx.getAwardCount() + 1;
											logger.info("当前用户["+this.openid+"]，最新抽奖次数 >>> " + newAwardCount);
											curAwardWx.setAwardCount(newAwardCount);
											if(actDao.updateAwardWeixin(curAwardWx)){
												logger.info("更新数据库成功====>" + curAwardWx.getId());
											}else{
												logger.info("更新数据库失败====>" + curAwardWx.getId());
											}
										}
										
										if(toAwardWx == null){
											logger.info("源分享用户["+share.getOpenid()+"]，添加抽奖记录，并多加一次");
											addAwardWeixinMore(curActivity,1);
										}else{
											logger.info("源分享用户["+share.getOpenid()+"]，原有抽奖记录上，加一次");
											logger.info("源分享用户["+share.getOpenid()+"]，当前的次数>>>"+toAwardWx.getAwardCount());
											int newAwardCount = toAwardWx.getAwardCount() + 1;
											logger.info("源分享用户["+share.getOpenid()+"]，最新抽奖次数 >>> " + newAwardCount);
											toAwardWx.setAwardCount(newAwardCount);
											if(actDao.updateAwardWeixin(toAwardWx)){
												logger.info("更新数据库成功====>"+toAwardWx.getId());
											}else{
												logger.info("更新数据库失败====>"+toAwardWx.getId());
											}
										}
							
					}else{
						logger.info("该分享id["+this.shareId+"] 找不到分享记录");
					}
					
				}
			}
			
			logger.info("结束检查活动的相关设置  <<<<<<");
			
			}//end if curActivity != null
			
			//清除上一次处理的记录
			curAwardWx = null;
			toAwardWx = null;
			
		}//end for
			
		}else{
			logger.info("没有相关的活动正在执行 ...");
		}
		
		}catch(Exception e){
			e.printStackTrace();
			
		}finally{
			if(this.countDown != null){
				logger.info("结束等待...");
				this.countDown.countDown();
			}
		}

	}
	
	private void addAwardWeixin(Activity curActivity){
		//添加抽奖记录
		AwardWeixin newAw = new AwardWeixin();
		newAw.setActivity(curActivity);
		newAw.setOpenId(openid);
		newAw.setAwardCount(curActivity.getJoinCount() + this.addCount);
		newAw.setCreateTime(new Date());
		if(actDao.addAwardWeixin(newAw)){
			logger.info("用户["+this.openid+"] 添加抽奖记录成功啦...");
		}else{
			logger.info("用户["+this.openid+"] 添加抽奖记录失败啦...");
		}
	}
	
	private void addAwardWeixinMore(Activity curActivity,int addCount){
		this.addCount = addCount;
		addAwardWeixin(curActivity);
	}
	
	private boolean addCredit(THDMember member,int giveCredit){
		
		logger.info("活动增送积分");
		
		boolean flag = false;
		
		Authenticator.setDefault(new MyAuthenticator());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuilder paramsStr = new StringBuilder();
		
		String serverUrl = this.bindServerUrl;
		HDCredit hdcredit = new HDCredit(HDCredit.CREDIT_TYPE_ADD);
		hdcredit.setXid("123");
		hdcredit.setAccount_accesscode(member.getCardNo());
		StringBuilder builder = new StringBuilder();
		builder.append(serverUrl).append(hdcredit.toString());
		
		// 组装请求参数副本
		paramsStr.append(HDMember.MEMBER_QUERY_BYCARD).append("cardnum=").append(member.getCardNo());
		
		List<SubjectAccount> jsonList = new ArrayList<SubjectAccount>();

		ScoreType scoreType = new ScoreType();
		scoreType.setCode("0001");
		scoreType.setName("积分");
		scoreType.setUuid("2c9e86b33e3f94b7013e462e30621378");

		ScoreSubject scoreSubject = new ScoreSubject();
		// scoreSubject.setUuid("");
		scoreSubject.setName("调整");
		scoreSubject.setCode("104");

		SubjectAccount subjectAccount = new SubjectAccount();
		subjectAccount.setRemark("活动赠送");
		subjectAccount.setScoreType(scoreType);
		subjectAccount.setScore(giveCredit);
		subjectAccount.setScoreSubject(scoreSubject);
		
		Scoreaccount scoreaccount = new Scoreaccount();
		jsonList.add(subjectAccount);
		scoreaccount.setSubjectAccounts(jsonList);

		String postJson = JSONObject.toJSONString(scoreaccount);
		
		
		logger.info("请求参数===>" + postJson);
		
		Date now = new Date();
		logger.info("开始请求服务器  【" + sdf.format(now) + "】");
		String creditJson = HDService2.recheckRequest(0, builder.toString(),hdcredit.getRequestMethod(), postJson, paramsStr.toString());
		Date end = new Date();
		logger.info("结束请求服务器  【" + sdf.format(end) + "】");
		long costTime = end.getTime() - now.getTime();
		logger.info("总请求耗时 【" + costTime + "】毫秒");
		logger.info("请求增加积分结果===>"+creditJson);
		if (creditJson != null && !"".equals(creditJson)) {
			// 检查返回的内容是否正确
			logger.info("活动赠送积分成功？");
			flag = true;
		}else{
			//如果失败怎么处理？
			logger.info("活动赠送积分失败？");
		}

		
		return flag; 
		
	}

	public void setCountDown(CountDownLatch countDown) {
		this.countDown = countDown;
	}
	
	
	

}
