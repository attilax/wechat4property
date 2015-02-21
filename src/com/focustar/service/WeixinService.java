package com.focustar.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;

import com.attilax.Closure;
import com.attilax.core;
import com.attilax.io.filex;
import com.attilax.util.god;
import com.focustar.IocX;
import com.focustar.entity.TMbTask;
import com.focustar.entity.TMbWeixinuser;
import com.focustar.entity.weixin.message.request.TextMessage;
import com.focustar.thread.CheckMemberJober;
import com.focustar.thread.ClientMsgJober;
import com.focustar.thread.CreditJober;
import com.focustar.thread.DaySignJober;
import com.focustar.thread.DownloadUserJober;
import com.focustar.thread.EventHistoryJober;
import com.focustar.thread.Jober;
import com.focustar.thread.LocationMsgJober;
import com.focustar.thread.LocationSignJober;
import com.focustar.thread.LocationUpdateJober;
import com.focustar.thread.MemberInfoJober;
import com.focustar.thread.NineJober;
import com.focustar.thread.SaveMsgJober;
import com.focustar.thread.TextMsgJober;
import com.focustar.thread.UnsubscribleJober;
import com.focustar.util.ConfigService;
import com.focustar.util.Constant;
import com.focustar.util.MD5;
import com.focustar.util.MessageUtil;
import com.focustar.util.MyCacher;
import com.focustar.util.WeixinUtil;

/**
 * 核心服务类 WeixinService.java author:vincente 2013-11-1
 */
public class WeixinService {

	protected static Logger logger = Logger.getLogger("WeixinService");

	// 消息id缓存5分钟
	private static int MSGID_CACHE_TIME = 1000 * 60 * 5;

	/**
	 * 清除缓存变量
	 * 
	 * @param resqText
	 */
	public static void deleCache(TextMessage resqText) {
		Constant.eventKey.remove(resqText.getFromUserName());
	}

	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return String
	 * @throws DocumentException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static String processRequest(HttpServletRequest request)
			throws IOException, DocumentException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		SaveMsgJober msgJober = null;
		
		Jober   myJober = null;
		
		String respMessage = null;
		// 默认返回的文本消息内容
		String respContent = "";
		// xml请求解析
		Map<String, String> requestMap = MessageUtil.parseXml(request);
		// 开发者微信号
		String toUserName = requestMap.get("ToUserName");
		// 发送方帐号（一个OpenID）
		String fromUserName = requestMap.get("FromUserName");
		// 消息创建时间 （整型）
		String createTime = requestMap.get("CreateTime");
		// 消息类型
		String msgType = requestMap.get("MsgType");
		// 文本消息内容
		String content = requestMap.get("Content");
		// 消息id
		String msgId = requestMap.get("MsgId");

		String Event2 = requestMap.get("Event");
		String EventKey2 = requestMap.get("EventKey");
		logger.info("开发者帐号(OpenID)：toUserName>>" + toUserName);
		logger.info("发送方帐号(OpenID)：FromUserName>>" + fromUserName);
		logger.info("消息类型：msgType>>" + msgType);
		logger.info("消息内容：content>>" + content);
		logger.info("消息id：msgId>>" + msgId);
		logger.info("事件类型：>>" + Event2);
		logger.info("事件菜单ID：>>" + EventKey2);

		// 下面的textMessage是响应对象 就是收到用户的微信信息, 然后我给与什么样的回应
		// 接收消息对象
		TextMessage resqText = new TextMessage();
		resqText.setToUserName(toUserName);
		resqText.setFromUserName(fromUserName);
		resqText.setCreateTime(Long.valueOf(createTime));
		resqText.setMsgType(msgType);
		resqText.setContent(content);
		resqText.setMsgId(msgId);
		resqText.setFuncFlag(0);
		
		

		// 回复文本消息
		com.focustar.entity.weixin.message.response.TextMessage respText = new com.focustar.entity.weixin.message.response.TextMessage();
		respText.setToUserName(fromUserName);
		respText.setFromUserName(toUserName);
		respText.setCreateTime(Long.valueOf(createTime));
		respText.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
		respText.setContent("");
		respText.setMsgId(msgId);
		respText.setFuncFlag(0);

			try {
				// 文本消息 此时用户输入的是文本信息，
				if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
					
					if (content != null && !"".equals(content)) {
						
						String cacheMsgId =(String) MyCacher.getInstance().getCache(msgId);
						if(cacheMsgId == null){
							
								content = content.trim();
								content = content.toUpperCase();
								String eventKey = (String) Constant.eventKey.get(fromUserName);
								// 用户没有点击任何菜单，直接输入 的消息，此时系统抓取下来，保存到本地，将来会把这些数据分发给坐席员处理
								if (eventKey == null) {
									eventKey = "0";
									logger.info("用户没有点击菜单！");
								}
								
								if(content.indexOf("GROUPID=") != -1){
									logger.info("正在请求更新分店坐标");
									try{
										Integer groupId = Integer.parseInt(content.replaceAll("GROUPID=",""));
										LocationUpdateJober locUpdater = new LocationUpdateJober(fromUserName,groupId,0,0.0,0.0);
										Constant.AsyncJober.execute(locUpdater);
									}catch(Exception e){
										e.printStackTrace();
									}
								}
								
								//记录消息
								msgJober = new SaveMsgJober(TMbTask.MSGTYPE_TEXT,requestMap);
								Constant.AsyncJober.execute(msgJober);
								MyCacher.getInstance().putCache(msgId, fromUserName,MSGID_CACHE_TIME);
								
								
								//检查9周年？
								ConfigService config = new ConfigService();
								
								String nineValue = config.getWxProperty("NINE_STAR");
								
								if(nineValue != null && !"".equals(nineValue)){
									
									if("1".equals(nineValue.trim())){
										
										logger.info("开启9周年,开始检查获取用户["+fromUserName+"]请求的抽奖码");
										NineJober nJober = new NineJober(fromUserName,content);
										Constant.AsyncJober.execute(nJober);
									}else{
										logger.info("无开启9周年");
									}
									
								}else{
									logger.info("无九周年开关设置");
								}
								
						}else{
							logger.info("该文字消息【"+msgId+"】记录已接收处理，忽略");
						}
					}
				}
				// 图片消息
				else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
					respContent = "";
					String cacheMsgId =(String) MyCacher.getInstance().getCache(msgId);
					if (cacheMsgId == null){
							logger.info("第一次处理该消息【"+msgId+"】");
							//缓存接收消息的id记录，并缓存5分钟
							MyCacher.getInstance().putCache(msgId, fromUserName,MSGID_CACHE_TIME);
							
							msgJober = new SaveMsgJober(TMbTask.MSGTYPE_IMG,requestMap);
							
							Constant.AsyncJober.execute(msgJober);
							
					}else{
						logger.info("该图片消息【"+msgId+"】记录已接收处理，忽略");
					}
				}
				// 地理位置消息
				else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
					respContent = "";
					if(msgId != null && !"".equals(msgId)){//消息id不为空
						//检查该条消息是否已经接收过
						String cacheMsgId =(String) MyCacher.getInstance().getCache(msgId);
						if (cacheMsgId == null){
								logger.info("第一次处理该消息【"+msgId+"】");
								//缓存接收消息的id记录，并缓存5分钟
								MyCacher.getInstance().putCache(msgId,fromUserName,MSGID_CACHE_TIME);
								
								Double latitude = Double.parseDouble(requestMap.get("Location_X").toString());
								Double longitude = Double.parseDouble(requestMap.get("Location_Y").toString());

								logger.info("接收到的地址消息   >>> Location_X:" + latitude+ "  Location_Y:" + longitude);
								
								
								if(Constant.UPDATE_LOC_MAP.containsKey(fromUserName)){
									LocationUpdateJober locUpdater = new LocationUpdateJober(fromUserName,0,1,latitude,longitude);
									Constant.AsyncJober.execute(locUpdater);
								}
								
								
								String cacheEventKey = (String) Constant.eventKey.get(fromUserName);
								if (cacheEventKey != null) {
									// 地理位置结果，可以做缓存
									// 横纬竖经
									// 测试
									// Double latitude = 23.133528006786;
									// Double longitude = 113.28037765886;
									
									if(cacheEventKey.equals("25")){//门店签到
										myJober = new LocationSignJober(fromUserName,latitude,longitude);
									}else if(cacheEventKey.equals("14")){
										// 回复后，删除用户的点击菜单记录与消息id
										myJober =  new LocationMsgJober(fromUserName,latitude,longitude);
									}
									if(myJober != null){
										Constant.AsyncJober.execute(myJober);
									}
									Constant.eventKey.remove(fromUserName);
								} else {
									// 回复默认内容，当用户在没有触发菜单时
									respContent = "";
								}
						}else{
							logger.info("该地理消息【"+msgId+"】记录已接收处理，忽略");
						}
					}
				}
				// 链接消息
				else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
					msgJober = new SaveMsgJober(TMbTask.MSGTYPE_LINK,requestMap);
					Constant.AsyncJober.execute(msgJober);
					respContent = "";
				}
				// 音频消息
				else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
					msgJober = new SaveMsgJober(TMbTask.MSGTYPE_VOICE,requestMap);
					Constant.AsyncJober.execute(msgJober);
					respContent = "";
				} // 事件推送
				else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)){
					msgJober = new SaveMsgJober(TMbTask.MSGTYPE_VIDEO,requestMap);
					Constant.AsyncJober.execute(msgJober);
					respContent = "";
				}
				else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
					logger.info("事件推送");
					// 事件类型
					String eventType = requestMap.get("Event");
					// 订阅
					if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
						// 将首次关注的用户添加到本地数据库中
						logger.info("首次关注程序开始执行");
						// 如果是重新关注用户,数据库中已有数据，只需要更新？
						logger.info("场景id  >>> " + EventKey2);
						if (EventKey2 == null || "".equals(EventKey2)) {
							logger.info("扫描二维码的场景id不正确 或为空，该用户是通过搜索关注  >>> " + EventKey2);
						}
						String groupid = "";
						DownloadUserJober uJober = null;
						//ati recomm p1g for microshop wechat
//						if (EventKey2 != null
//								&& EventKey2.indexOf("qrscene_") != -1) {// 扫描的是永久二维码,关注用户
//							groupid = EventKey2.replaceAll("qrscene_", "");
//							int iGroupId = Integer.parseInt(groupid);
//							
//							if(iGroupId < 100000){
//								logger.info("参数是永久二维码,应该是关注分公司");
//								uJober = new DownloadUserJober(fromUserName,iGroupId,true);
//								logger.info("当前扫描关键用户["+fromUserName+"] 的 所属分公司  >> " + iGroupId);
//							}else{
//								logger.info("参数是临时二维码,应该是分享");
//								uJober = new DownloadUserJober(fromUserName,true,true,iGroupId);
//							}
//							
//						}
//						else {
							
							logger.info("当前用户["+fromUserName+"]无所属分公司");
							uJober = new DownloadUserJober(fromUserName,true);//getUserinfoFrmWechatHandler
							uJober.getUserinfoFrmWechatHandler=(Closure<TMbWeixinuser, Object>) IocX.map.get("getUserinfoFrmWechatHandler");
				//		}
						
						if(uJober != null){
							logger.info("启动用户信息下载");
							Constant.AsyncJober.execute(uJober);
						}
						
						respContent = "";
					}
					// 取消订阅
					else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
						myJober = new UnsubscribleJober(fromUserName);
						Constant.AsyncJober.execute(myJober);
					}
					// 自定义菜单点击事件
					else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
						
						updateUserInfo(fromUserName);
						
						//检查用户输入过key值
						String checkKey = (String) Constant.eventKey.get(fromUserName);
						// 事件KEY值，与创建自定义菜单时指定的KEY值对应
						String eventKey = requestMap.get("EventKey");
						//用户没有输入过key值
						if(checkKey == null){
							//记录用户输入key值
							Constant.eventKey.put(fromUserName,eventKey);
						}else{
							//如果输入键值不一样，覆盖原来的键值
							if(!checkKey.equals(eventKey)){
								Constant.eventKey.put(fromUserName,eventKey);
							}
						}
						if (eventKey.equals("11")) {
							// 最新促销
							if(checkKey == null || !"11".equals(checkKey)){
							buildEventKeyAllNews(
									fromUserName,
									com.focustar.entity.weixin.sendMessage.NewsMessage.NEWS_NEWSALE,"11");
							}else{
								logger.info("微信用户  【"+fromUserName+"】>>> 重复key值" + eventKey);
							}
						} else if (eventKey.equals("12")) {
							// Top热卖
							if(checkKey == null || !"12".equals(checkKey)){
							buildEventKeyAllNews(
									fromUserName,
									com.focustar.entity.weixin.sendMessage.NewsMessage.NEWS_HOTSALE,"12");
							}else{
								logger.info("微信用户  【"+fromUserName+"】>>> 重复key值" + eventKey);
							}
						} else if (eventKey.equals("13")) {
							// 新品上市
							if(checkKey == null || !"13".equals(checkKey)){
							buildEventKeyAllNews(
									fromUserName,
									com.focustar.entity.weixin.sendMessage.NewsMessage.NEWS_NEWGOOD,"13");
							}else{
								logger.info("微信用户  【"+fromUserName+"】>>> 重复key值" + eventKey);
							}
						} else if (eventKey.equals("14")) {
							// 门店查询
								buildEventKey14(fromUserName);
						} else if (eventKey.equals("15")) {
							// 贴心客服
							if(checkKey == null || !"15".equals(checkKey)){
								buildEventKey15(fromUserName);
							}else{
								logger.info("微信用户  【"+fromUserName+"】>>> 重复key值" + eventKey);
							}
						} else if (eventKey.equals("21")) {
							// 会员绑定
							if(checkKey == null || !"21".equals(checkKey)){
								buildEventKey21(fromUserName);
							}else{
								logger.info("微信用户  【"+fromUserName+"】>>> 重复key值" + eventKey);
							}
						} else if (eventKey.equals("22")) {
							// 积分查询
							if(checkKey == null || !"22".equals(checkKey)){
								buildEventKey22(fromUserName);
							}else{
								logger.info("微信用户  【"+fromUserName+"】>>> 重复key值" + eventKey);
							}
						} else if (eventKey.equals("23")) {
							// 会员资料
							if(checkKey == null || !"23".equals(checkKey)){
								builderEventKey23(fromUserName);
							}else{
								logger.info("微信用户  【"+fromUserName+"】>>> 重复key值" + eventKey);
							}
						} else if (eventKey.equals("24")) {
							// 会员日
							if(checkKey == null || !"24".equals(checkKey)){
								buildEventKey24(fromUserName,com.focustar.entity.weixin.sendMessage.NewsMessage.NEWS_MEMBERDAY);
							}else{
								logger.info("微信用户  【"+fromUserName+"】>>> 重复key值" + eventKey);
							}
						} else if (eventKey.equals("25")) {
							// 门店签到
								builderEventKey25(fromUserName);
						} else if (eventKey.equals("31")) {
							// 扫码有礼
							if(checkKey == null || !"31".equals(checkKey)){
							buildEventKeyAllNews(
									fromUserName,
									com.focustar.entity.weixin.sendMessage.NewsMessage.NEWS_SCANGIFT,"31");
							}else{
								logger.info("微信用户  【"+fromUserName+"】>>> 重复key值" + eventKey);
							}
						} else if (eventKey.equals("32")) {
							// 周五抽大奖
							
							//临时使用，以后要修改，用来公布中奖名单
							//buildEventKeyAllNews(fromUserName,com.focustar.entity.weixin.sendMessage.NewsMessage.NEWS_ALL,"32");
						} else if (eventKey.equals("33")) {
							// 超级FUN送
							if(checkKey == null || !"33".equals(checkKey)){
								builderEventKey33(fromUserName);
							}else{
								logger.info("微信用户  【"+fromUserName+"】>>> 重复key值" + eventKey);
							}
						} else if (eventKey.equals("34")) {
							// 微信价
							if(checkKey == null || !"34".equals(checkKey)){
							buildEventKeyAllNews(
									fromUserName,
									com.focustar.entity.weixin.sendMessage.NewsMessage.NEWS_WXPRICE,"34");
							}else{
								logger.info("微信用户  【"+fromUserName+"】>>> 重复key值" + eventKey);
							}
						} else if (eventKey.equals("35")) {
							// 寻找拼图
							EventHistoryJober historyJober = null;
							TMbWeixinuser user = (TMbWeixinuser)MyCacher.getInstance().getCache(MD5.getMD5(fromUserName));
							if(user != null && user.getMember() != null){
								historyJober = new EventHistoryJober(fromUserName,user.getMember().getMemberId(),"35");
							}else{
								historyJober = new EventHistoryJober(fromUserName,"35");
							}
							if(historyJober != null){
								Constant.CountJober.execute(historyJober);
							}
						}
					} else if (eventType.equals(MessageUtil.EVENT_TYPE_SCAN)) {
							logger.info("用户已关注，开始处理扫描二维码事件   >>> " + EventKey2);
							if (EventKey2 != null && !"".equals(EventKey2)) {// 二维码的场景id不为空
								//用户再次扫描时，更新当前用户数据?
								DownloadUserJober updateUserJober = new DownloadUserJober(fromUserName,false);
								if(updateUserJober != null){
									Constant.AsyncJober.execute(updateUserJober);
								}
							}else{
								logger.info("无事件 enentKey >>> " + EventKey2);
							}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally{
				// 将响应信息转换成XML格式，并传给response对象
				if(respContent != null){
					respText.setContent(respContent);
				}
				respMessage = MessageUtil.textMessageToXml(respText);
			}
		return respMessage;
	}

	/**
	 * @category 最新促销 /Top热卖/新品上市 根据微信用户所属的分公司或所在省市，获取相关图文消息
	 */
	private static void buildEventKeyAllNews(String openId, int newsType,String eventKey) {
		ClientMsgJober msgJober = null;
		if(eventKey == null){
			msgJober = new ClientMsgJober(openId,newsType);
		}else{
			msgJober = new ClientMsgJober(openId,newsType,eventKey);
		}
		if(msgJober != null){
			Constant.AsyncJober.execute(msgJober);
			Constant.eventKey.remove(openId);
		}
	}

	/**
	 * @category 门店查询 响应微信用户请求,返回微信用户所在区域的附近门店信息
	 */
	private static void buildEventKey14(String openId){
		logger.info("门店查询...");
		
		EventHistoryJober historyJober = null;
		TMbWeixinuser user = (TMbWeixinuser)MyCacher.getInstance().getCache(MD5.getMD5(openId));
		if(user != null && user.getMember() != null){
			historyJober = new EventHistoryJober(openId,user.getMember().getMemberId(),"14");
		}else{
			historyJober = new EventHistoryJober(openId,"14");
		}
		if(historyJober != null){
			Constant.CountJober.execute(historyJober);
		}
		
		
		Object msgObj = WeixinUtil.buildTextMessage(openId,
				"您好，请你点击文字输入旁边的\"+\"，把您的位置发给小娇，即可查看附近门店啦~");
		TextMsgJober asyncMsg = new TextMsgJober(msgObj);
		if(asyncMsg != null){
			Constant.AsyncJober.execute(asyncMsg);
		}
	}
 
	//   上午8:45:39 2014-6-26  老哇的爪子  Attilax
	/**
	 * @category 贴心客服
	 */
	private static void buildEventKey15(String openId) {
		logger.info("贴心客服...");
		String msg =tyesinCusvs();// "亲，该功能建设中...";
		EventHistoryJober historyJober = null;
		TMbWeixinuser user = (TMbWeixinuser)MyCacher.getInstance().getCache(MD5.getMD5(openId));
		if(user != null && user.getMember() != null){
			historyJober = new EventHistoryJober(openId,user.getMember().getMemberId(),"15");
		}else{
			historyJober = new EventHistoryJober(openId,"15");
		}
		
		if(historyJober != null){
			Constant.CountJober.execute(historyJober);
		}
		
		Object msgObj = WeixinUtil.buildTextMessage(openId, msg);
		if(msgObj != null){
			TextMsgJober asyncMsg = new TextMsgJober(msgObj);
			Constant.AsyncJober.execute(asyncMsg);
			Constant.eventKey.remove(openId);
		}
	}

	/**
	@author attilax 老哇的爪子
		@since  2014-6-25 下午4:35:08$
	
	 * @return
	 */
	private static String tyesinCusvs() {
	// attilax 老哇的爪子  下午4:35:08   2014-6-25 
	
	{
	
	return	new com.attilax.tryX<String>() {		@Override		 		public String $$(Object t) throws Exception {
		 // attilax 老哇的爪子     下午4:35:35 2014-6-25
		String s=filex.read(	Constant.path+"teysinCusvs.txt","gbk");
		core.log("tyesincstmsvs txt:"+s);
		return s;	}	}.$("...");
		
	} 
 
 
	}
	
	public static void main(String[] args) {
 
//	for(int i=0;i<20;i++){	// attilax 老瓦老哇的爪子  
//		
//	}
//	for(int i=0;i<20;i++){	// attilax 老哇的爪子  下午5:15:262014-6-26
//			
//		}
//  
//		
//	char[] i;
//	final	String threadName = "threadName"+String.valueOf(i);
//		god.newThread(new Runnable() {
//			
//			@Override public void run() {
//			// attilax 老哇的爪子  下午4:39:24   2014-6-26 
//		 
//				System.out.println(	"--"+threadName+  filex.read(	Constant.path+"teysinCusvs.txt","gbk"));
//			}
//		}, threadName);
//		
//		
// 
//	
	
	}

	/**
	 * 
	 * @category 会员绑定 根据微信用户提供的会员信息，进行绑定
	 */
	private static void buildEventKey21(String openId) {
		CheckMemberJober ckJober = new CheckMemberJober(openId,"21");
		if(ckJober != null){
			Constant.AsyncJober.execute(ckJober);
			Constant.eventKey.remove(openId);
		}
		
	}

	/**
	 * 
	 * @category 查询积分,根据微信openid，查询绑定会员的帐号积分
	 */
	private static void buildEventKey22(String openId) {
		CreditJober creditJober = new CreditJober(openId,"22");
		if(creditJober != null){
			Constant.AsyncJober.execute(creditJober);
			Constant.eventKey.remove(openId);
		}
	}

	/**
	 * @category 会员资料
	 * @param openId
	 */
	public static void builderEventKey23(String openId) {
		MemberInfoJober infoJober = new MemberInfoJober(openId,"23");
		if(infoJober != null){
			Constant.AsyncJober.execute(infoJober);
			Constant.eventKey.remove(openId);
		}
	}
	
	/**
	 * @category 会员日
	 * @param openId
	 * @param newsType
	 */
	public static void buildEventKey24(String openId,int newsType){
		ClientMsgJober msgJober = new ClientMsgJober(openId,newsType,"24");
		if(msgJober != null){
			Constant.AsyncJober.execute(msgJober);
			Constant.eventKey.remove(openId);
		}
	}
	

	/**
	 * @category 门店签到
	 * @param openId
	 */
	private static void builderEventKey25(String openId) {
		DaySignJober signJober = new DaySignJober(openId,"25");
		if(signJober != null){
			Constant.AsyncJober.execute(signJober);
		}
	}
	
	
	/***
	 * @category 超级fun
	 * @param openId
	 */
	public static void builderEventKey33(String openId){
		ClientMsgJober msgJober = new ClientMsgJober(openId,com.focustar.entity.weixin.sendMessage.NewsMessage.NEWS_SUPERFUN,"33");
		if(msgJober != null){
			Constant.AsyncJober.execute(msgJober);
			Constant.eventKey.remove(openId);
		}
	}
	
	
	private static void updateUserInfo(String openid){
		StringBuilder updateKey = new StringBuilder();
		updateKey.append(openid).append("_").append("update");
		
		String md5Key = MD5.getMD5(updateKey.toString());
		
		String value = (String)MyCacher.getInstance().getCache(md5Key);
		
		if(value == null){
			//缓存一小时
			MyCacher.getInstance().putCache(md5Key, "1",3600);
			
			DownloadUserJober uJober = new DownloadUserJober(openid,false,true);
			
			Constant.UpdateJober.execute(uJober);
			
		}else{
			logger.info("已做更新...");
		}
		
	}


}
