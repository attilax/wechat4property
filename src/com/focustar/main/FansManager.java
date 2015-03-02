package com.focustar.main;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import com.attilax.Closure;
import com.attilax.io.filex;
import com.attilax.io.pathx;
import com.attilax.wechat.TokenX;
import com.attilax.wechat.WechatX;
 
import com.focustar.dao.impl.ListenerImpl;
import com.focustar.dao.impl.UserImpl;
import com.focustar.entity.TMbGroup;
import com.focustar.entity.TMbWeixinuser;
import com.focustar.entity.weixin.UserInfo;
import com.focustar.entity.weixin.UserList;
import com.focustar.entity.weixin.pojo.AccessToken;
import com.focustar.thread.CheckActivityJober;
import com.focustar.thread.EventHistoryJober;
import com.focustar.thread.FocusJober;
import com.focustar.thread.TextMsgJober;
import com.focustar.util.ConfigService;
import com.focustar.util.Constant;
import com.focustar.util.MD5;
import com.focustar.util.MyCacher;
import com.focustar.util.WeixinUtil;

public class FansManager {

	private static Logger logger = Logger.getLogger(FansManager.class);
	private ListenerImpl listenerDao;
	private UserImpl	 userDao;
	private SimpleDateFormat sdf;
	
	private int groupid = 0; //没有分组
	private int shareId = 0;//分享id
	private int fromtype = 0;//默认主动搜索关注
	private boolean isFoucs = false;
	private boolean isShare = false;
	private boolean isUpdate = false;
	public Closure<TMbWeixinuser,Object>  getUserinfoFrmWechatHandler;
	public static void main(String[] args) {
		AccessToken at=new AccessToken();
		at.setToken(new TokenX().getToken());
		Constant.token =at;
		
		FansManager fm = new FansManager();
		fm.createUser("oQe6zt-6iMqK7TWV0oAv672aAYwU");
		
		//p1f();
	}

	private static void p1f() {
		//测试
		
		List<String> list = new ArrayList<String>();
		ConfigService config = new ConfigService();
		Constant.token = WeixinUtil.getAccessToken(config.getWxProperty("APPID"), config.getWxProperty("APPSECRET"));
		Configuration cfg = new Configuration().configure();
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(cfg.getProperties()).buildServiceRegistry();
		Constant.sessionFactory = cfg.buildSessionFactory(serviceRegistry);
		
		String lastOpenId = ""; //获取最后一条记录的openId ?
		FansManager fm = new FansManager();
		fm.getUserOpenid(Constant.token.getToken(),list,lastOpenId);
		for(String oneId:list){
			if(oneId != null && !"".equals(oneId)){
				fm.createUser(oneId);
			}
		}
	}

	// 构造函数，自动执行
	public FansManager() {
		try {
			Class<?> classType = ListenerImpl.class;
			listenerDao = (ListenerImpl) classType.newInstance();
			
			Class<?> userType = UserImpl.class;
			userDao = (UserImpl)userType.newInstance();
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	


	/**
	 * 获取关注者的openid
	 */
	public void getUserOpenid(String accessToken, List<String> openidList, String next_openid) {
		UserList user = WeixinUtil.getUserList(accessToken, next_openid);
		if (user != null) {
			String[] openid = user.getOpenid();
			if (openid != null) {
				int size = openid.length;
				for (int i = 0; i < size; i++) {
					openidList.add(openid[i]);
				}
				next_openid = openid[size - 1];
				this.getUserOpenid(accessToken, openidList, next_openid);
			}
		}
	}
	
	public void createUserByScan(String openid,int groupId,boolean isFocus){
		this.groupid = groupId;	//关注时推送过来的场景id
		this.fromtype = 1; //来自扫描二维码关注
		createUser(openid,isFocus);
	}
	
	public void createUser(String openid,boolean isFocus){
		this.isFoucs = isFocus;
		createUser(openid);
	}
	
	public void createUser(String openid,boolean isFocus,boolean isUpdate){
		this.isFoucs = isFocus;
		this.isUpdate = isUpdate;
		createUser(openid);
	}
	
	public void createUser(String openid,boolean isFocus,boolean isShare,int shareId){
		this.isFoucs = isFocus;
		this.isShare = isShare;
		this.shareId = shareId;
		createUser(openid);
	}
	public static Logger focusLogger = Logger.getLogger("focusLogger");
	/**
	 * 创建用户记录，用户首次关注时调用此方法
	 * 
	 * @param openid
	 */
	public void createUser(String openid) {
		try {
			focusLogger.info(" start createUser");
			String accessToken = Constant.token != null ? Constant.token.getToken() : null;
			TokenX tokenX = new TokenX();
			if(!tokenX.checktokenOk(accessToken, "anyopenid"))
			{
				focusLogger.info("  reget token ");
				accessToken=tokenX.getToken();
			}
 
				TMbWeixinuser localUser =  this.userDao.getUserByopenid(openid);
				if(localUser == null){ //新用户
						
						UserInfo userinfoFrmWechat =getuserinfoFrmWechat(accessToken, openid);					 
						focusLogger.info("微信接口[获取用户信息]调用成功");
							logger.info("微信接口[获取用户信息]调用成功");
							TMbWeixinuser det = new TMbWeixinuser();	
							userConvert(openid,userinfoFrmWechat,det);
							//ati sys to website 
							getUserinfoFrmWechatProcess(det);								
						 	adduserToLocal(openid, userinfoFrmWechat, det);
						 
				}else{//重新关注   ///end 	if(checkUser == null){ //新用户
					
					refocus(openid, accessToken, localUser);
					
				}
				welcome4focus(openid);
				//启动关注时的处理响应
//				if(isFoucs){
//					logger.info("通过关注...启动关注图文");
//					FocusJober fJober = new FocusJober(openid);
//					Constant.AsyncJober.execute(fJober);
//				}
				
			 
		} catch (Throwable e) {
			e.printStackTrace();
			focusLogger.error("ep28d",e);
			//logger.info("调用微信接口[getUserInfo]获取用户基本信息，并将用户保存到本地库中时发生错误 >>> " + e.getMessage());
		//	filex.saveLog(e, "c:\\wechatEx");
		}
	}

	private void adduserToLocal(String openid, UserInfo userinfoFrmWechat,
			TMbWeixinuser det) {
		userConvert(openid, userinfoFrmWechat, det);							
		List<TMbWeixinuser> wxUserList = new ArrayList<TMbWeixinuser>();
		wxUserList.add(det);		              
		if (wxUserList != null && wxUserList.size() > 0) {
			try {
				if(this.listenerDao.insertWeiXinUser(wxUserList)){
				
					logger.info("添加微信用户信息到数据库成功");
//										if(this.isFoucs){
//											logger.info("通过关注...检查是否有活动举行");
//											CountDownLatch countDown = new CountDownLatch(1);
//											CheckActivityJober chkJober = new CheckActivityJober(openid);
//											chkJober.setCountDown(countDown);
//											Constant.AsyncJober.execute(chkJober);
//											countDown.await(60,TimeUnit.SECONDS);
//											if(this.isShare){
//												logger.info("是分享关注...检查是否有活动举行 >>>分享id>>>" + this.shareId);
//												CheckActivityJober chkShareJober = new CheckActivityJober(openid,CheckActivityJober.ACTIVITY_TYPE_SHARE,this.shareId);
//												Constant.AsyncJober.execute(chkShareJober);
//											}
//										}
				}
				
			} catch (Exception e) {
				logger.info("微信用户插入时发生异常！" , e);
				e.printStackTrace();
			}
		}
	}

		/**
		@author attilax 老哇的爪子
		@since   p1j e_w_x
		 
		 */
	private UserInfo getuserinfoFrmWechat(String accessToken, String openid) {
		UserInfo userinfoFrmWechat =		WeixinUtil.getUserInfo(accessToken, openid);
		if (userinfoFrmWechat == null) {
			for (int i = 1; i < 20; i++) {
				logger.info("微信接口[获取用户信息]发生超时连接，微信服务器重新连接第[" + i + "]次");
				userinfoFrmWechat = WeixinUtil.getUserInfo(accessToken, openid);
				if (userinfoFrmWechat != null) {
					break;
				}
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return userinfoFrmWechat;
	}

	private void userConvert(String openid, UserInfo user, TMbWeixinuser det) {
		det.setCity(user.getCity());
		det.setCountry(user.getCountry());
		det.setHeadimgurl(user.getHeadimgurl());
		det.setLanguage(user.getLanguage());
		det.setNickname(user.getNickname());
		det.setOpenid(openid);
		det.setProvince(user.getProvince());
		det.setSex(user.getSex());
		det.setSubscribe(user.getSubscribe());
		det.setSubscribeTime(user.getSubscribe_time());
		det.setInsertFlag(0);
		det.setFromtype(fromtype);
		det.setCreateTime(new Date());
		if(groupid != 0){
			logger.info("设置分公司  >>> " + groupid);
			TMbGroup g = new TMbGroup();
			g.setGroupid(groupid);
			det.setGroup(g);
		}else{
			det.setGroup(null);
		}
	}
	
	private static void welcome4focus(String openId) {
		System.out.println("aa");
		try {
			System.out.println("--");
			logger.info("贴心客服...");
			String msg = filex.read(pathx.classPath(WechatX.class)+"/followAutoReply.txt", "utf-8");
					//"欢迎加入...<a href=\"http://m.hualip.com/index.aspx\">请进入主页面</a>";
					//tyesinCusvs();//
//			EventHistoryJober historyJober = null;
//			TMbWeixinuser user = (TMbWeixinuser)MyCacher.getInstance().getCache(MD5.getMD5(openId));
//			if(user != null && user.getMember() != null){
//				historyJober = new EventHistoryJober(openId,user.getMember().getMemberId(),"15");
//			}else{
//				historyJober = new EventHistoryJober(openId,"15");
//			}
//			
//			if(historyJober != null){
//				Constant.CountJober.execute(historyJober);
//			}
			
			Object msgObj = WeixinUtil.buildTextMessage(openId, msg);
			if(msgObj != null){
				TextMsgJober asyncMsg = new TextMsgJober(msgObj);
				Constant.AsyncJober.execute(asyncMsg);
			//	Constant.eventKey.remove(openId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		   focusLogger.error("welcome4focus meth err",e);
		}
		
	}

	private void refocus(String openid, String accessToken,
			TMbWeixinuser checkUser) throws InterruptedException {
		logger.info("用户["+checkUser.getNickname()+"]重新关注 ");
		UserInfo user = WeixinUtil.getUserInfo(accessToken, openid);
		if (user == null) {
			for (int i = 1; i < 20; i++) {
				logger.info("微信接口[获取用户信息]发生超时连接，微信服务器重新连接第[" + i + "]次");
				user = WeixinUtil.getUserInfo(accessToken, openid);
				if (user != null) {
					break;
				}
				Thread.sleep(3000);
			}
		}
		logger.info("用户["+checkUser.getNickname()+"] 更新用户信息 ");
		
		checkUser.setSubscribe(1);
		checkUser.setInsertFlag(2);//更新标志
		checkUser.setNickname(user.getNickname());
		checkUser.setCountry(user.getCountry());
		checkUser.setProvince(user.getProvince());
		checkUser.setCity(user.getCity());
		checkUser.setLanguage(user.getLanguage());
		checkUser.setSex(user.getSex());
		checkUser.setSubscribeTime(user.getSubscribe_time());
		checkUser.setUpdateTime(new Date());
		if(groupid != 0){
			logger.info("更新分公司>>>" + groupid);
			TMbGroup g = new TMbGroup();
			g.setGroupid(groupid);
			checkUser.setGroup(g);
		}else{
			checkUser.setGroup(null);
		}
		
		if(this.isUpdate){
			//更新
			this.listenerDao.updateUserInfo(checkUser);
			
		}else{
			this.listenerDao.resubscrible(checkUser);
		}
		//重新关注。。清除一下缓存
	//	MyCacher.getInstance().removeCache(MD5.getMD5(checkUser.getOpenid()));
	}

	private void getUserinfoFrmWechatProcess(TMbWeixinuser det) {
	//	IocX.map;
	//.get("getUserinfoFrmWechatHandler");
	   if(this.getUserinfoFrmWechatHandler!=null)
		try {
			getUserinfoFrmWechatHandler.execute(det);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}

	/**
	 * 批量生成微信记录
	 */
	public void createFansList(List<String> openidList) {

		String accessToken = Constant.token.getToken();
		List<TMbWeixinuser> wxUserList = new ArrayList<TMbWeixinuser>();
		if (openidList != null && openidList.size() > 0) {
			Iterator<String> it = openidList.iterator();
			while (it.hasNext()) {
				String openid = it.next();
				UserInfo user = WeixinUtil.getUserInfo(accessToken, openid);
				if (user == null) {
					for (int i = 1; i < 20; i++) {
						logger.info("微信接口[获取用户信息]发生超时连接，微信服务器重新连接第[" + i + "]次");
						user = WeixinUtil.getUserInfo(accessToken, openid);
						if (user != null) {
							break;
						}
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				if (user != null) {
					TMbWeixinuser det = new TMbWeixinuser();
					det.setCity(user.getCity());
					det.setCountry(user.getCountry());
					det.setHeadimgurl(user.getHeadimgurl());
					det.setLanguage(user.getLanguage());
					det.setNickname(user.getNickname());
					det.setOpenid(openid);
					det.setProvince(user.getProvince());
					det.setSex(user.getSex());
					det.setSubscribe(user.getSubscribe());
					det.setSubscribeTime(user.getSubscribe_time());
					det.setInsertFlag(0);
					wxUserList.add(det);
				}
			}
		}

		int userCount = wxUserList != null ? wxUserList.size() : 0;
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		logger.info("------------------------本次抓取微信用户结果[" + sdf.format(new Date()) + "]-------------");
		logger.info("-----------微信授权码  [" + accessToken + "]");
		logger.info("-----------用户总数量 [" + userCount + "]");
		logger.info("------------------------本次抓取结束,系统将数据保存到数据库中--------------");

		if (wxUserList != null && wxUserList.size() > 0) {
			try {
				this.listenerDao.insertWeiXinUser(wxUserList);
			} catch (Exception e) {
				e.printStackTrace();
				sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				logger.info("微信用户表保存失败[" + sdf.format(new Date()) + "]" + e.getMessage());
			}
		}
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		FansManager.logger = logger;
	}

	public ListenerImpl getListenerDao() {
		return listenerDao;
	}

	public void setListenerDao(ListenerImpl listenerDao) {
		this.listenerDao = listenerDao;
	}

	public SimpleDateFormat getSdf() {
		return sdf;
	}

	public void setSdf(SimpleDateFormat sdf) {
		this.sdf = sdf;
	}

}
