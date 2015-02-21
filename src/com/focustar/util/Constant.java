package com.focustar.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.hibernate.SessionFactory;

import com.attilax.io.pathx;
import com.focustar.entity.TMbGroup;
import com.focustar.entity.weixin.pojo.AccessToken;

/**
 * com.focustar.util.Constant.token
 * @author ASIMO
 *
 */
public class Constant {
	
	public static void main(String[] args) {
		com.focustar.util.Constant.token.getToken();
	}
	
	public static void getinfo4debug()
	{
		System.out.println( com.focustar.util.Constant.token.getToken());
	}

	/**
	 * SessionFactory工厂
	 */
	public static SessionFactory sessionFactory;
	/**
	 * 文件存放根目录
	 */
	public static String path = pathx.classPath()+"/";
	
	/**
	 * 用于存放菜单key
	 */
	public static Map eventKey = new HashMap();
	
	/***
	 * 一般的消息提示内容
	 */
	public static Map MSG_MAP = new TreeMap();
	
	/***
	 * 的服务器地址
	 */
	public static Map<String,TMbGroup> SERVER_MAP = new TreeMap<String,TMbGroup>();
	
	public static Object[]  SERVER_KEY_ARRAY = null;
	
	
	//请求提交通知的服务器允许列表
	public static List<String>   NOTE_SERVER = new ArrayList<String>();
	
	
	public static Map<String,String>  CODE_MAG = new TreeMap<String,String>();
	
	//请求toekn的设置
	public static String APPID = "";
	public static String APPSECRET = "";

	/**
	 * 存放微信接口授权对象
	 */
	public static AccessToken token;
	
	//用来处理异步消息线程池
	public final static ExecutorService AsyncJober = Executors.newFixedThreadPool(3000);
	//处理消费提示线程
	public final static ExecutorService AsyncMsgJober = Executors.newFixedThreadPool(1000);
	//定时调度线程
	public final static ScheduledExecutorService scheduleJober = Executors.newScheduledThreadPool(5);
	
	//用于启动统计线程
	public final static ExecutorService  CountJober = Executors.newFixedThreadPool(1000);
	//用于更新用户信息的线程
	public final static ExecutorService  UpdateJober = Executors.newFixedThreadPool(1000);
	
	public static String WEB_URL = "";
	
	/**
	 *  图文相关的网址与路径
	 */
	public  static String NEWS_WEB_SITE = "";
	public static String NEWS_HTML_PATH = "";
	public static String NEWS_IMAGE_PATH = "";
	
	public final static int  A_WEEK_SECONDS = 7 * 24 * 60 * 60;
	
	public final static int  TEN_MINUTES = 60 * 10;
	
	/**
	 * @category 会员系统的登录用户与密码
	 * 
	 */
	public static String HD_API_USER = "";
	public static String HD_API_PWD = "";
	
	
	/**
	 * @category 签到失败提示
	 */
	public final static String SIGN_FAIL = "SIGN_FAIL";
	
	/**
	 * @category 签到成功提示
	 */
	public final static String SIGN_OK = "SIGN_OK";
	
	/**
	 * @category 已签到消息提示
	 */
	public final static String INFO_SIGN_TIPS = "SIGN_TIPS";
	
	/**
	 * @category 微信用户未绑定会员的门店签到提示
	 */
	public final static String INFO_SIGN_BUT_NOT_MEMBER = "SIGN_BUT_NOT_MEMBER";
	
	
	/**
	 * @category 门店签到的消息提示
	 */
	public final static String INFO_SIGN_LOCATION = "SIGN_LOCATION";
	
	public final static String  INFO_NO_SIGN_LOCATION = "NO_SIGN_LOCATION";
	
	/**
	 * @category 找不到附近门店的提示
	 */
	public final static String INFO_NO_NEARBY = "NO_NEARBY";
	
	/**
	 * @category 签到日期的替换值
	 */
	public final static String RP_SIGN_DATE = "SIGN_DATE";
	
	/**
	 * @category 新增加积分替换值
	 */
	public final static String RP_ADD_CREDIT = "ADD_CREDIT";
	
	/**
	 * @castor. 替换显示的会员卡号
	 */
	public final static String RP_CARD_NO = "CARD_NO";
	
	/**
	 * @category 使用积分替换值
	 */
	public final static String RP_USE_CREDIT = "USE_CREDIT";
	
	/**
	 * @category 总积分替换值
	 */
	public final static String RP_CREDIT_TOTAL = "CREDIT_TOTAL";
	
	/**
	 * @category 其它积分
	 */
	public final static String RP_OTHER_CREDIT = "OTHER_CREDIT";
	
	/**
	 * @category 门店名称替换值
	 */
	public final static String RP_SHOP_NAME = "SHOP_NAME";
	
	/**
	 * @category 赠送积分值
	 */
	public static int SIGN_CREDIT = 10;
	
	
	/***
	 * @category 会员未绑定的消息提示
	 */
	public final static String MEMBER_UNBIND = "MEMBER_UNBIND";
	
	/**
	 * @category 会员已绑定的消息提示
	 */
	public final static String MEMBER_BINDED = "MEMBER_BINDED";
	
	/**
	 * @category 会员资料查询时的消息 提示
	 */
	public final static String MEMBER_TIPS = "MEMBER_TIPS";
	
	/**
	 * @category 会员已绑定时，返回的会员资料查看消息提示
	 */
	public final static String MEMBER_OK_TIPS = "MEMBER_OK_TIPS";
	
	
	/**
	 * @category 替换提示中的值
	 */
	public final static String RP_BIND_URL = "BIND_URL";
	
	/**
	 * @category 积分查询提示格式
	 */
	public final static String INFO_QUERY_CREDIT = "CREDIT_QUERY";
	
	/***
	 * @category 从海顶推送过来的消费提示格式
	 */
	public final static String INFO_CREDIT_NOTE = "CEDIT_NOTE";
	
	/**
	 * @category 从海顶推送过来的退货提示格式
	 */
	public final static String INFO_CREDIT_NOTE_RETURN = "CREDIT_NOTE_RETURN";
	
	/**
	 * 签到的来源key与value
	 */
	public static String CREDIT_FROM_KEY = "";
	public static String CREDIT_FROM_VALUE = "";
	
	
	/**
	 * @category 微信用户发送图片或其它媒体时的保存路径
	 */
	public static String MEDIA_FILE_PATH = "";
	
	
	public final static Map<String,TMbGroup> UPDATE_LOC_MAP = new TreeMap<String,TMbGroup>();
	
	
	public static String FtpFilePath = "FtpFilePath";
	
	
}
