/**
 * 
 */
package com.attilax.wechat;

import com.attilax.io.filex;
import com.attilax.io.pathx;
import com.attilax.lang.core;
import com.focustar.thread.TextMsgJober;
import com.focustar.util.Constant;
import com.focustar.util.WeixinUtil;

/**
 * @author ASIMO
 *
 */
public class txtMsgTest {

	/**
	@author attilax 老哇的爪子
	@since   p29 g_46_42
	 
	 */
	public static void main(String[] args) {
		WechatX wx=new WechatX();
		wx.setTokenGlobal("XaDFrIu2et24BOr-e5TKrGB0ClH_qaLf6Tqbfgg4ZqN6G5pz5XuNB6RUrnC8QoC4lJWw37xR8NEjxA6o__QHQpieNrJl5LDerNT_57mOaXw");
		
		
	   String t=filex.read(pathx.classPath(txtMsgTest.class)+"/txt.htm");
	   System.out.println(t);
		String openId="oQe6zt-6iMqK7TWV0oAv672aAYwU";
		Object msgObj = WeixinUtil.buildTextMessage(openId, t);
		System.out.println(core.toJsonStrO88(msgObj ));
//		{
//			  "msgtype": "text",
//			  "text": {"content": "<p>other text<\/p>\r\n<p><img src=\"http://www.baidu.com/img/bd_logo1.png\" width=\"399\" height=\"199\" /> <\/p>\r\n<p><a href=\"http://www.qq.com/\">linkl<\/a><\/p>\r\n"},
//			  "touser": "oQe6zt-6iMqK7TWV0oAv672aAYwU"
//			}
		 
			TextMsgJober asyncMsg = new TextMsgJober(msgObj);
			Constant.AsyncJober.execute(asyncMsg);
	}

}
