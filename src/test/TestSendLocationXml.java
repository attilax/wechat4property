package test;

import com.alibaba.fastjson.JSONObject;
import com.focustar.entity.weixin.message.response.TextMessage;
import com.focustar.util.MessageUtil;

public class TestSendLocationXml {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		TextMessage t = new TextMessage();
		//t.setMsgType(Constant.)
		System.out.println(JSONObject.toJSON(t));
		
		System.out.println(MessageUtil.textMessageToXml(t));

	}

}
