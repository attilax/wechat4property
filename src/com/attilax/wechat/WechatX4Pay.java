/**
 * 
 */
package com.attilax.wechat;

import java.io.ByteArrayInputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.attilax.cfg.IocX;
import com.attilax.io.filex;
import com.attilax.io.pathx;
import com.attilax.time.timeUtil;
import com.attilax.util.Mapx;
import com.attilax.util.god;
import com.focustar.util.MD5;
import com.google.inject.Inject;
import com.tencent.common.Configure;
import com.tencent.common.Signature;

/**
 * @author ASIMO
 *
 */
public class WechatX4Pay {
	// 
	public static void main(String[] args) {
		
		t4();
	//	System.out.println(timeUtil.getTimestamp_strFmt());
		 
//		String t=filex.read(pathx.classPath(WechatX.class)+"/prepay_t.xml");
//		Map mp2=Xml2JsonUtil.xml2map(t);
//	 	t2();
//	 	t3();
	}

		private static void t4() {
			
		//	WechatX4Pay w=new WechatX4Pay();
			WechatX4Pay w = IocX.getBean(WechatX4Pay.class);		
		    w.orderno="123456";
			w.openid="oQe6zt-6iMqK7TWV0oAv672aAYwU";
			w.fee="8";
			w.title="title_test";
			w.notify_url="http://192.157.242.99/wechat/wxpay/notify_url2.php";
		String s=	w.Pay_getPrePayid_replaceVar();
		System.out.println(s);
		
	}

		/**
		@author attilax 老哇的爪子
		@since   p25 f_n_z
		 
		 */
	private static void t3() {
		WechatX4Pay pay= IocX.getBean(WechatX4Pay.class);		 
		pay.prepay_id="preid";
		
		
	}
	 
	private static void t2() {
		WechatX4Pay c=new WechatX4Pay();
		String key="7bedeaa7f238ffe0ac671159b561f716";
		String t=filex.read(pathx.classPath(WechatX.class)+"/prepay.xml");
	
		t=t.replaceAll("@openid", "oQe6zt-6iMqK7TWV0oAv672aAYwU");
		t=t.replaceAll("@orderno", "11223");//filex.getUUidName()
		t=t.replaceAll("@appid", "wx947702f1812d3249");
		t=t.replaceAll("@ip", "10.0.0.2");
		t=t.replaceAll("@mchid", "10084838");
		
		Map mp2=Xml2JsonUtil.xml2map(t);
		
		
		String sign = c.getSign(mp2,key);
		t=t.replaceAll("@sign", sign+"");//todox p23  验证签名 ,,can custom yg sign ,yaosi tips yyeo.zeush sign err..
		//or use api web tool test ..
		
		// p23
		Configure.setKey(key);
		String sign2=	Signature.getSign(mp2);
		System.out.println(sign+"____"+sign2);
	 	System.out.println(c.Pay_getPrePayid(t));
	}
	
		/**
		@author attilax 老哇的爪子
		 * @param key 
		@since   p23 f_0_h
		 
		 */
	private String getSign(Map mp2, String key) {
		mp2.remove("sign");
		String s4sign=Mapx.toUrlstr(mp2)+"&key="+key;
		System.out.println(s4sign);
		return MD5.getMD5(s4sign,"utf-8");
	}
	@Inject
	public	WechatX wechatC;
public String orderno;
public String openid;
private String getPrepayidReqXml;
public String Pay_getPrePayid(HttpServletRequest request)
{ 
	//URLEncoder
	if(openid==null)
	openid = wechatC.getOpenid2(request);
return	Pay_getPrePayid_replaceVar();
	
}
	public String Pay_getPrePayid_replaceVar()
	{
		WechatX4Pay c=new WechatX4Pay();
	//	String key="7bedeaa7f238ffe0ac671159b561f716";
		String t=filex.read(pathx.classPath(WechatX.class)+"/prepay.xml");
	
		t=t.replaceAll("@openid",openid);// "oQe6zt-6iMqK7TWV0oAv672aAYwU");
		t=t.replaceAll("@orderno", orderno);//filex.getUUidName()
		t=t.replaceAll("@appid", appId);
		t=t.replaceAll("@ip", "10.0.0.2");
		t=t.replaceAll("@mchid",Configure.getMchid());
		
		t=t.replaceAll("@notify_url",this.notify_url);
		t=t.replaceAll("@fee",this.fee);
		t=t.replaceAll("@title",this.title);
		
		 
		
		Map mp2=Xml2JsonUtil.xml2map(t);
		
		
		String sign = c.getSign(mp2,key);
		t=t.replaceAll("@sign", sign+"");//todox p23  验证签名 ,,can custom yg sign ,yaosi tips yyeo.zeush sign err..
		
		filex.saveLog(t, "c:\\wechatPayLog");
		this.getPrepayidReqXml=t;
		return Pay_getPrePayid(t);
		 
		//String outputStr = null;
	//	return WeixinUtil.httpRequest(url, "POST", outputStr);
		
	}
	
	public String Pay_getPrePayid(String outputStr)
	{
		String url="https://api.mch.weixin.qq.com/pay/unifiedorder";
	System.out.println(outputStr);
		 
		//String outputStr = null;
		return WeixinUtil.httpRequest(url, "POST", outputStr);
		
	}
	public	String appId=Configure.getAppid();
	public	String timeStamp= timeUtil.getTimestamp_strFmt().substring(0, 10);
	public	String nonceStr="nonceStrnonceStr";
	public	String prepay_id="def";
	public	String packageStr="prepay_id=";
	public	String paySign=null;
	public	String key=Configure.getKey();
	
	public  Map Pay_geneJsapiParam(HttpServletRequest request)
	{
		String s=Pay_getPrePayid(request);
		filex.saveLog(s, "c:\\wechatPayLog");
		Map m=Xml2JsonUtil.xml2map(s);
		try {
			this.prepay_id=m.get("prepay_id").toString();
			
		} catch (NullPointerException e) {
			packageStr=packageStr+this.prepay_id;
			throw new RuntimeException("cant get  prepay_id:"+ this.getPrepayidReqXml +"<p>\r\n" +s);
		
		}
		
		
		return Pay_geneJsapiParam();
		
	}
	public String notify_url;
	public String fee;
	public String title;
	public  Map Pay_geneJsapiParam()
	{
 		Map m=new HashMap();
 		m.put("appId", appId);
		m.put("timeStamp", timeStamp);
		m.put("nonceStr", nonceStr); 		
 		packageStr=packageStr+prepay_id;
 		m.put("package", packageStr);
 		m.put("signType", "MD5");
 		Configure.setKey(key);
		String sign2=	Signature.getSign(m);
 		paySign=sign2;
 		return m;
		
//		
//		getBrandWCPayRequest
//		  'getBrandWCPayRequest', {
//	           "appId" : "wx947702f1812d3249",     //公众号名称，由商户传入     
//	           "timeStamp":"1395712654",         //时间戳，自1970年以来的秒数     
//	           "nonceStr" : "e61463f8efa94090b1f366cccfbbb444", //随机串     
//	           "package" : "prepay_id=u802345jgfjsdfgsdg888---",     
//	           "signType" : "MD5",         //微信签名方式:     
//	           "paySign" : "70EA570631E4BB79628FBCA90534C63FF7FADD89" //微信签名
		
	}

}
