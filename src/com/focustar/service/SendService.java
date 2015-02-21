package com.focustar.service;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import com.focustar.entity.ReturnInfo;
import com.focustar.entity.weixin.sendMessage.Text;
import com.focustar.entity.weixin.sendMessage.TextMessage;
import com.focustar.util.ConfigService;
import com.focustar.util.Constant;
import com.focustar.util.MessageUtil;
import com.focustar.util.WeixinUtil;

/**
 * 微信发送接口处理程序
 * 
 */
public class SendService {

	/**
	 * 
	 * 处理坐席发送来的数据
	 * 
	 * @param request
	 * @return 发送结果
	 */
	public static String processRequest(HttpServletRequest request) {
		String respMessage = "";
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		String openid = request.getParameter("openid");
		String msgType = request.getParameter("msgType");
		String content = request.getParameter("content");
		int type = msgType != null && !msgType.equals("") ? Integer.valueOf(msgType) : 0;

		TextMessage textMessage = new TextMessage();
		textMessage.setTouser(openid);
		switch (type) {
		case 1:// 文本
			Text text = new Text();
			text.setContent(content);
			textMessage.setMsgtype(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setText(text);
			break;
		case 2:// 图片
			break;
		case 3:// 语音
			break;
		}

		int errcode = 0;
		if (openid != null && !"".equals(openid)) {
			try {
//				String appid = ConfigService.getWeiXinAppID();
//				String appsecret = ConfigService.getWeiXinAppSecret();
//				String accessToken = WeixinUtil.getAccessToken(appid, appsecret).getToken();
				String accessToken = Constant.token.getToken();
				JSONObject json = JSONObject.fromObject(textMessage);
				String jsonData = json.toString();
				System.out.println(accessToken);
				System.out.println(jsonData);
				System.out.println(content);
				System.out.println(Constant.token.getExpiresIn());
				// 调用微信接口开始发送消息
				errcode = WeixinUtil.sendMessage(jsonData, accessToken);
				String message = ConfigService.getReturnCode(errcode + "");
				message = new String(message.getBytes(), "iso-8859-1");
				// 根据接口返回值 封装对象，并将些对象传回给坐席端
				ReturnInfo returnInfo = new ReturnInfo();
				returnInfo.setErrcode(errcode);
				returnInfo.setMessage(message);
				json = JSONObject.fromObject(returnInfo);
				respMessage = json.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return respMessage;
	}
}
