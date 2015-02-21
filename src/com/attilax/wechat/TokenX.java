/**
 * 
 */
package com.attilax.wechat;

import java.io.File;

import com.attilax.util.PropX;
import com.focustar.entity.weixin.UserInfo;
import com.focustar.util.ConfigService;
import com.focustar.util.Constant;
import com.focustar.util.WeixinUtil;
import com.focustar.util.accessTokenException;

/**
 * @author ASIMO
 *
 */
public class TokenX extends TokenThread {

	/**
	@author attilax 老哇的爪子
	@since   p1j c_49_c
	 
	 */
	public static void main(String[] args) {
		System.out.println("aaaa");
	 	 System.out.println(new TokenX().getToken());
//	System.out.println(new TokenX().checktokenOk("tooken","oid22"));	
	}
	
	
		/**{"errcode":40001,"errmsg":"invalid credential"}   if acctoke is ok
		@author attilax 老哇的爪子
		@since   p1j d_47_x
		 
		 */
	public boolean checktokenOk(String accessToken, String any_openid) {
		if(accessToken==null ||accessToken.trim().length()==0 )
			return false;
		try {
			UserInfo user = WeixinUtil.getUserInfo_ex(accessToken, any_openid);
			return true;
		} catch (accessTokenException e) {
			return false;
		}
		
		
	}


	public String getToken()
	{
		//设置常量
		if(Constant.APPID.length()==0 )
		{
			ConfigService config = new ConfigService();
			
			String appId = config.getWxProperty("APPID");
			String appSecret = config.getWxProperty("APPSECRET");
			Constant.APPID = appId;
			Constant.APPSECRET = appSecret;
		}
		try {
			
			 if(Constant.token!=null)
			 {
				String token2=Constant.token.getToken();
				if(checktokenOk(token2,"oid22"))
					return token2;
			 }
			 
			
		
			
			String token1=getTokenFrmTmpFileCache();
			if(checktokenOk(token1,"oid33"))
				return token1;
			
			recatchToken();
			return  Constant.token.getToken();
		} catch (Exception e) {
			e.printStackTrace();
			throw  new getTokenEx(e);
		}
		
		
	}


				/**
				 * 被动刷新access_token的接口，这样便于业务服务器在API调用获知access_token已超时的情况下，可以触发access_token的刷新流程。
		@author attilax 老哇的爪子
		@since   p1j d_3_38
		 
		 */
	private void recatchToken() {
	    this.run();
		
	}


			/**
		@author attilax 老哇的爪子
		@since   p1j d_2_5
		 
		 */
//	private boolean checktokenOk(String token1) {
//		 
//		return false;
//	}


		/**
		@author attilax 老哇的爪子
		@since   p1j d_0_42
		 
		 */
	private String getTokenFrmTmpFileCache() {
		StringBuilder builder = new StringBuilder();
		builder.append(Constant.path);
		builder.append("token.properties");
		String f=builder.toString();
	//	File tmpTokenFile = new File();
		return PropX.getConfig(f, "token");
	}

}
