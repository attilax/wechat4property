package com.focustar.thread;

import java.util.List;

import org.apache.log4j.Logger;

import com.focustar.dao.impl.NewsImpl;
import com.focustar.dao.impl.TaskImpl;
import com.focustar.dao.impl.UserImpl;
import com.focustar.entity.TMbGroup;
import com.focustar.entity.TMbNews;
import com.focustar.entity.TMbReply;
import com.focustar.entity.TMbWeixinuser;
import com.focustar.util.Constant;
import com.focustar.util.MD5;
import com.focustar.util.MyCacher;
import com.focustar.util.WeixinUtil;

public class KeywordJober extends Jober {
	
	private final static Logger logger = Logger.getLogger(KeywordJober.class);
	
	private String keyword;
	public KeywordJober(String openid,String keyword){
		this.openId = openid;
		this.keyword = keyword;
	}


	@Override
	protected void startWork() {
		
		logger.info("检查关键字  ===> " + this.keyword);
		long startTime = System.currentTimeMillis();
		NewsImpl newsDao = new NewsImpl();
		TaskImpl taskDao = new TaskImpl();
		
		String md5Keyword = MD5.getMD5(this.keyword);
		
		//获取是否管理员关键字
		TMbReply adminReply = newsDao.searchAdminMD5Keyword(md5Keyword);
		
		if(adminReply != null){
			logger.info("该关键字["+this.keyword+"] 为管理员创建");
			int keyCount = taskDao.getUserKeyWord(this.openId,this.keyword);
			long endTime = System.currentTimeMillis();
			logger.info("该用户[" + this.openId+"]   已发送关键字 ["+this.keyword+"]>>>> " + keyCount +"  耗时:" + (endTime - startTime));
			
			if(keyCount <= 1){
				
							logger.info("该关键字["+this.keyword+"]，有相关的自动回复");
							if(adminReply.getNewsId() != null){
								logger.info("自动回复 图文");
								List<TMbNews> newsList = newsDao.getNewsListByNewsId(adminReply.getNewsId());
								
								if(newsList != null && newsList.size() > 0){
									logger.info("找到["+this.keyword+"]相关图文");
									msgObj = WeixinUtil.buildNewsMessage(openId, newsList);
								}else{
									logger.info("找不到["+this.keyword+"]相关图文");
								}
								
							}else{
								msgObj = WeixinUtil.buildTextMessage(openId, adminReply.getContent());
							}
			}else{ //判断是否二次回复
				
				logger.info("已接收处理过");
					
					//搜索md5后的关键字
					
					if(adminReply != null && (adminReply.getMultiReplyContent() != null && !"".equals(adminReply.getMultiReplyContent()))){
						
						logger.info("多次回复内容=====>" + adminReply.getMultiReplyContent());
						
						msgObj = WeixinUtil.buildTextMessage(this.openId,adminReply.getMultiReplyContent());
						
					}else if(adminReply != null && (adminReply.getMultiReplyContent() == null || "".equals(adminReply.getMultiReplyContent()))){
						logger.info("该关键字["+this.keyword+"]，有相关的自动回复");
						
						if(adminReply.getNewsId() != null){
							
							logger.info("自动回复 图文");
							
							List<TMbNews> newsList = newsDao.getNewsListByNewsId(adminReply.getNewsId());
							
							if(newsList != null && newsList.size() > 0){
								logger.info("找到["+this.keyword+"]相关图文");
								
								msgObj = WeixinUtil.buildNewsMessage(openId, newsList);
							}else{
								logger.info("找不到["+this.keyword+"]相关图文");
								//检查工作时间
								CheckWorkJober workJober = new CheckWorkJober(openId);
								Constant.AsyncJober.execute(workJober);
							}
							
						}else{
							msgObj = WeixinUtil.buildTextMessage(openId, adminReply.getContent());
						}
					}
					else{
						logger.info("该关键字["+this.keyword+"]，没有相关的回复！");
						//检查工作时间
						CheckWorkJober workJober = new CheckWorkJober(openId);
						Constant.AsyncJober.execute(workJober);
					}
				
			}
			
		}else{ //end of 检查是否管理员创建
			logger.info("该关键字["+this.keyword+"] 为分公司创建");
		int keyCount = taskDao.getUserKeyWord(this.openId,this.keyword);
		long endTime = System.currentTimeMillis();
		logger.info("该用户[" + this.openId+"]   已发送关键字 ["+this.keyword+"]>>>> " + keyCount +"  耗时:" + (endTime - startTime));
		
		if(keyCount <=1){
					
					long startTime1 = System.currentTimeMillis();
					UserImpl  userDao = new UserImpl();
					TMbWeixinuser user = userDao.getUserByopenid(openId);
					long endTime1 = System.currentTimeMillis();
					
					logger.info("查找用户[" + this.openId+" ] 耗时:" + (endTime1 - startTime1)+" === 用户对象::"+user);
					
					if(user != null){
					
					TMbGroup curGroup = user.getGroup()!=null&&user.getGroup().getParent() != null?user.getGroup().getParent():user.getGroup();
					
					if(curGroup == null){
						logger.info("查找["+this.openId+"]所在的国["+user.getCountry()+"]，省["+user.getProvince()+"]与["+user.getCity()+"]的分公司");
						curGroup =  NewsImpl.checkProvinceAndCity(user.getProvince(),user.getCity());
					}else{
						logger.info("["+this.openId+"] 在绑定分公司 ==> " + curGroup.getGroupname());
					}
					
					
					if(curGroup != null){
						
							//搜索md5后的关键字
							TMbReply  reply = newsDao.searchMD5Keyword(md5Keyword,curGroup.getGroupid());
							
							if(reply != null){
								
								logger.info("该关键字["+this.keyword+"]，有相关的自动回复");
								
								if(reply.getNewsId() != null){
									
									logger.info("自动回复 图文");
									
									List<TMbNews> newsList = newsDao.getNewsListByNewsId(reply.getNewsId());
									
									if(newsList != null && newsList.size() > 0){
										logger.info("找到["+this.keyword+"]相关图文");
										
										msgObj = WeixinUtil.buildNewsMessage(openId, newsList);
									}else{
										logger.info("找不到["+this.keyword+"]相关图文");
										//检查工作时间
										CheckWorkJober workJober = new CheckWorkJober(openId);
										Constant.AsyncJober.execute(workJober);
									}
									
								}else{
									msgObj = WeixinUtil.buildTextMessage(openId, reply.getContent());
								}
								
							}else{
								logger.info("该关键字["+this.keyword+"]，没有相关的回复！");
								
								//检查工作时间
								CheckWorkJober workJober = new CheckWorkJober(openId);
								Constant.AsyncJober.execute(workJober);
								
							}
					}else{
						logger.info("无法确定该用户["+this.openId+"]的所在分公司...");
						
						//检查工作时间
						CheckWorkJober workJober = new CheckWorkJober(openId);
						Constant.AsyncJober.execute(workJober);
					}
					
					}
		
		}else{
			logger.info("已接收处理过");
			UserImpl  userDao = new UserImpl();
			
			TMbWeixinuser user = userDao.getUserByopenid(openId);
			
			if(user != null){
			TMbGroup curGroup = user.getGroup()!=null&&user.getGroup().getParent() != null?user.getGroup().getParent():user.getGroup();
			
			if(curGroup == null){
				logger.info("查找["+this.openId+"]所在的国["+user.getCountry()+"]，省["+user.getProvince()+"]与["+user.getCity()+"]的分公司");
				curGroup =  NewsImpl.checkProvinceAndCity(user.getProvince(),user.getCity());
			}else{
				logger.info("["+this.openId+"] 在绑定分公司 ==> " + curGroup.getGroupname());
			}
			
			
			if(curGroup != null){
				
				logger.info("确定该用户所在分公司["+curGroup.getGroupname()+"]");
				//搜索md5后的关键字
				TMbReply  reply = newsDao.searchMD5Keyword(md5Keyword,curGroup.getGroupid());
				
				if(reply != null && (reply.getMultiReplyContent() != null && !"".equals(reply.getMultiReplyContent()))){
					
					logger.info("多次回复内容=====>" + reply.getMultiReplyContent());
					
					msgObj = WeixinUtil.buildTextMessage(this.openId,reply.getMultiReplyContent());
					
				}else if(reply != null && (reply.getMultiReplyContent() == null || "".equals(reply.getMultiReplyContent()))){
					logger.info("该关键字["+this.keyword+"]，有相关的自动回复");
					
					if(reply.getNewsId() != null){
						
						logger.info("自动回复 图文");
						
						List<TMbNews> newsList = newsDao.getNewsListByNewsId(reply.getNewsId());
						
						if(newsList != null && newsList.size() > 0){
							logger.info("找到["+this.keyword+"]相关图文");
							
							msgObj = WeixinUtil.buildNewsMessage(openId, newsList);
						}else{
							logger.info("找不到["+this.keyword+"]相关图文");
						}
						
					}else{
						msgObj = WeixinUtil.buildTextMessage(openId, reply.getContent());
					}
				}
				else{
					logger.info("该关键字["+this.keyword+"]，没有相关的回复！");
					
					//检查工作时间
					CheckWorkJober workJober = new CheckWorkJober(openId);
					Constant.AsyncJober.execute(workJober);
				}
				
			}else{
				logger.info("无法确定该用户["+this.openId+"]的所在分公司");
				
				//检查工作时间
				CheckWorkJober workJober = new CheckWorkJober(openId);
				Constant.AsyncJober.execute(workJober);
			}
			
			}else{
				logger.info("用户["+this.openId+"]对象为空");
			}
		
		}
		
		}
		
	}
	

}
