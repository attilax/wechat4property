package com.focustar.thread;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.focustar.entity.TMbNews;
import com.focustar.entity.weixin.sendMessage.ImageMessage;
import com.focustar.entity.weixin.sendMessage.MusicMessage;
import com.focustar.entity.weixin.sendMessage.News;
import com.focustar.entity.weixin.sendMessage.NewsMessage;
import com.focustar.entity.weixin.sendMessage.Text;
import com.focustar.entity.weixin.sendMessage.TextMessage;
import com.focustar.entity.weixin.sendMessage.VideoMessage;
import com.focustar.entity.weixin.sendMessage.VoiceMessage;
import com.focustar.util.Constant;
import com.focustar.util.WeixinUtil;

/***
 * 异步发送 客服消息
 *  implements Runnable
 * @author Administrator
 * 
 */// implements Runnable
public class TextMsgJober extends Jober {
// implements Runnable
	
	//private Object asyncObj;
//	private int retryCount; // 记录重试次数
//	private long sleeptime = 2000; // 默认休眠2秒
//	private volatile boolean isRunning = true;

/**
 * jeig exe d sh run();;
 * startwork shg befRun();
 * @param msgObj
 */
	public TextMsgJober(Object msgObj) {
		this.msgObj = msgObj;
	}

	public void startWork() {
		/*String jsonData = JSONObject.fromObject(asyncObj).toString();
			int retCode = WeixinUtil.sendMessage(jsonData,Constant.token.getToken());
			if (retCode != 0) {
				
				logger.info("发送消息 >>> " + jsonData+" 成功");
				
				// 判断是否继续重试
				if (retryCount <= TextMsgJober.MAX_RETRY) {
					logger.info("线程   发送消息,正在重试...已重试次数[" + retryCount + "]");
					retryCount++;
					try {
						Thread.sleep(sleeptime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					startWork();
				} else {
					// 停止线程
					isRunning = false;
				}
			}*/
	}
}
