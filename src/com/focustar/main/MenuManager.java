package com.focustar.main;

import java.util.List;
import org.apache.log4j.Logger;

import com.attilax.collection.list;
import com.focustar.entity.weixin.pojo.AccessToken;
import com.focustar.entity.weixin.pojo.Button;
import com.focustar.entity.weixin.pojo.CommonButton;
import com.focustar.entity.weixin.pojo.ComplexButton;
import com.focustar.entity.weixin.pojo.Menu;
import com.focustar.entity.weixin.pojo.ViewButton;
import com.focustar.util.ConfigService;
import com.focustar.util.Constant;
import com.focustar.util.WeixinUtil;

/**
 * 菜单管理器类 author:vincente 2013-11-5
 */
public class MenuManager {

	private static Logger log = Logger.getLogger(MenuManager.class);
	
	private static String appId = "";
	private static String appSecret = "";

	private static String webSite = "";
	public static void main(String[] args) {
		
	String TrueCfg=	Constant.path + "WeiXinConfig.properties";

		Class<?> classTypeConfig = ConfigService.class;
		ConfigService configService = null;
		try {
			configService = (ConfigService) classTypeConfig.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String TOKENO5 = configService.getWxProperty("TOKEN",TrueCfg);
		// 第三方用户唯一凭证
		appId = configService.getWxProperty("APPID");
		// 第三方用户唯一凭证密钥
		appSecret = configService.getWxProperty("APPSECRET");

		webSite = configService.getWxProperty("NEWS_WEBSITE");
		// 调用接口获取access_token
		AccessToken accessToken = WeixinUtil.getAccessToken(appId, appSecret);

		if (accessToken != null) {
			// 调用接口创建菜单
			int result = WeixinUtil.createMenu(getMenu(), accessToken.getToken());
			// 判断菜单创建结果
			if (0 == result) {
				log.info("TOKEN>>>		" + TOKENO5);
				log.info("appId>>>		" + appId);
				log.info("appSecret>>>		" + appSecret);
				log.info("菜单创建成功！");
			} else {
				log.info("菜单创建失败，错误码：" + result);
			}
		} else {
			log.info("解析token失败!");
		}
	}

	/**
	 * 组装菜单数据
	 */
	private static Menu getMenu() {
		
		////////////////////menu1
		CommonButton btn11 = new CommonButton();
		btn11.setName("最新促销");
		btn11.setType("click");
		btn11.setKey("11");

		CommonButton btn12 = new CommonButton();
		btn12.setName("Hot热卖");
		btn12.setType("click");
		btn12.setKey("12");

		CommonButton btn13 = new CommonButton();
		btn13.setName("新品上市");
		btn13.setType("click");
		btn13.setKey("13");

		CommonButton btn14 = new CommonButton();
		btn14.setName("门店查询");
		btn14.setType("click");
		btn14.setKey("14");
		
		/**/
		CommonButton btn15 = new CommonButton();
		btn15.setName("贴心客服");
		btn15.setType("click");
		btn15.setKey("15");
		

		
		////////////////////menu2
		

		CommonButton btn21 = new CommonButton();
		btn21.setName("会员绑定");
		btn21.setType("click");
		btn21.setKey("21");
		
		
		/*ViewButton btn21 = new ViewButton();
		btn21.setName("会员绑定");
		btn21.setType("view");
		StringBuilder burl = new StringBuilder();
		burl.append("https://open.weixin.qq.com/connect/oauth2/authorize?");
		burl.append("appid=").append(appId).append("&");
		burl.append("redirect_uri=").append(webSite).append("memberServlet?view=bind");
		burl.append("&response_type=code&scope=snsapi_base&state=1#wechat_redirect");
		btn21.setUrl(burl.toString());*/
		

		CommonButton btn22 = new CommonButton();
		btn22.setName("积分查询");
		btn22.setType("click");
		btn22.setKey("22");

		CommonButton btn23 = new CommonButton();
		btn23.setName("会员资料");
		btn23.setType("click");
		btn23.setKey("23");
		/*ViewButton btn23 = new ViewButton();
		btn23.setName("会员资料");
		btn23.setType("view");
		StringBuilder vurl = new StringBuilder();
		vurl.append("https://open.weixin.qq.com/connect/oauth2/authorize?");
		vurl.append("appid=").append(appId).append("&");
		vurl.append("redirect_uri=").append(webSite).append("memberServlet?view=view");
		vurl.append("&response_type=code&scope=snsapi_base&state=1#wechat_redirect");
		System.out.println("vurl===>"+vurl.toString());
		btn23.setUrl(vurl.toString());*/
		
		CommonButton btn24 = new CommonButton();
		btn24.setName("会员活动");
		btn24.setType("click");
		btn24.setKey("24");
		
		CommonButton btn25 = new CommonButton();
		btn25.setName("门店签到");
		btn25.setType("click");
		btn25.setKey("25");

		
		////////////////////menu3
//		CommonButton btn31 = new CommonButton();
//		btn31.setName("扫码有礼");
//		btn31.setType("click");
//		btn31.setKey("31");
		
		//临时使用
		//CommonButton btn32 = new CommonButton();
		//btn32.setName("三八晒单中奖名单");
		//btn32.setType("click");
		//btn32.setKey("32");

		/*CommonButton btn32 = new CommonButton();
		btn32.setName("周五抽大奖");
		btn32.setType("click");
		btn32.setKey("32");*/
		
		//

		CommonButton btn33 = new CommonButton();
		btn33.setName("超级FUN送");
		btn33.setType("click");
		btn33.setKey("33");
		
		ViewButton btn34 = new ViewButton();
		btn34.setName("微社区");
		btn34.setType("view");
		btn34.setUrl("http://wx.wsq.qq.com/204091781/");
		
		
		/*ViewButton btn21 = new ViewButton();
		btn21.setName("会员绑定");
		btn21.setType("view");
		StringBuilder burl = new StringBuilder();
		burl.append("https://open.weixin.qq.com/connect/oauth2/authorize?");
		burl.append("appid=").append(appId).append("&");
		burl.append("redirect_uri=").append(webSite).append("memberServlet?view=bind");
		burl.append("&response_type=code&scope=snsapi_base&state=1#wechat_redirect");
		btn21.setUrl(burl.toString());*/
		//o4n  jeig btn deush gazi syed . 
		//todox 直接转到。。  direct goto
		ViewButton skrechCard = new ViewButton("刮刮卡,view," +
				"https://open.weixin.qq.com/connect/oauth2/authorize?" +
				"appid="+appId+"&" +
				"redirect_uri="+webSite+"mobile/card.jsp?actid=1" +
				"" +
				"&response_type=code&scope=snsapi_base&state=1" +
				"#wechat_redirect");
		
		 ViewButton smashGoldEgg = new ViewButton("砸金蛋,view," +
				"https://open.weixin.qq.com/connect/oauth2/authorize?" +
				"appid="+appId+"&" +
				"redirect_uri="+webSite+"mobile/tips4sge.jsp?actid=1" +
				"" +
				"&response_type=code&scope=snsapi_base&state=1" +
				"#wechat_redirect");
		
		//http://jmszcy99217.eicp.net/weixin/mobile/eggs.jsp?actid=1

		
		
		CommonButton 微信价 = new CommonButton();
		微信价.setName("微信价");
		微信价.setType("click");
		微信价.setKey("34");

		/*
		CommonButton btn35 = new CommonButton();
		btn35.setName("寻找拼图");
		btn35.setType("click");
		btn35.setKey("35");*/


		// 加入菜单
		ComplexButton mainBtn1 = new ComplexButton();
		mainBtn1.setName("门店精选");
		//mainBtn1.setSub_button(new CommonButton[] { btn11, btn12, btn13, btn14,btn15 });
		mainBtn1.setSub_button(new CommonButton[] { btn11,btn12,btn13,btn14 ,btn15});

		ComplexButton mainBtn2 = new ComplexButton();
		mainBtn2.setName("会员专区");
		mainBtn2.setSub_button(new Button[] { btn21, btn22, btn23, btn24, btn25 });

		// 加入菜单
		ComplexButton mainBtn3 = new ComplexButton();
		mainBtn3.setName("有奖互动");
		//mainBtn3.setSub_button(new Button[] {  btn31, btn32, btn33,btn34, btn35});
		
		
		//   下午1:55:18 2014-6-27  老哇的爪子  Attilax
		//todox o625 per submenu only 5 menus..over then 40023
		String url = "mobile/tips4sge.jsp?actid=10";	  
		list<Button> li2=new com.attilax.collection.list<Button>().add(btn33).add(btn34).add(new ViewButton("方格抽奖,view," +
				url_gene("mobile/grid.jsp"))).add(new ViewButton("删除测试数据,view," +
				url_gene("mobile/awardServlet.jsp?act=delTestData&type=4"))).add(new ViewButton("设置概率,view," +				url_gene("mobile/setRate.jsp?type=4")));
	 
		Button[] btnArr=li2.toArray(Button.class);
//	
//		List<Button>  li2=li.toList();
	
		//mainBtn3.setSub_button(new Button[] {  btn33, btn34,微信价,skrechCard,smashGoldEgg});
		mainBtn3.setSub_button(btnArr);

		/**
		 * 这是公众号xiaoqrobot目前的菜单结构，每个一级菜单都有二级菜单项<br>
		 * 
		 * 在某个一级菜单下没有二级菜单的情况，menu该如何定义呢？<br>
		 * 比如，第三个一级菜单项不是“更多体验”，而直接是“幽默笑话”，那么menu应该这样定义：<br>
		 * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 });
		 */
		Menu menu = new Menu();
		menu.setButton(new Button[] {mainBtn2, mainBtn3,mainBtn1 });

		return menu;
	}

	/**
	@author attilax 老哇的爪子
		@since  2014-6-27 下午2:08:19$
	
	 * @param url
	 * @return
	 */
	private static String url_gene(String url) {
	// attilax 老哇的爪子  下午2:08:19   2014-6-27 
	return "https://open.weixin.qq.com/connect/oauth2/authorize?" +
			"appid="+appId+"&" +
			"redirect_uri="+webSite+url +
			"" +
			"&response_type=code&scope=snsapi_base&state=1" +
			"#wechat_redirect";
 
	}
}
