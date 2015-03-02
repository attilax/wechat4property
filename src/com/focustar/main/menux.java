/**
 * @author attilax 老哇的爪子
	@since  2014-4-28 下午05:23:05$
 */
package com.focustar.main;

/**
 * @author attilax
 *
 */
public class menux {

	/**
	@author attilax 老哇的爪子
		@since  2014-4-28 下午05:23:05$
	
	 * @param args
	 */
	public static void main(String[] args) {
		// 下午05:23:05   2014-4-28 
		String s=	"https://open.weixin.qq.com/connect/oauth2/authorize?" +
		"appid=wx94d1460e167fff4e&" +
		"redirect_uri=http://jmszcy99217.eicp.net/weixin/mobile/card.jsp" +
		"" +
		"&response_type=code&scope=snsapi_base&state=1" +
		"#wechat_redirect";
		System.out.println(s);

	}

}
