package com.focustar.thread;

import net.sf.json.JSONObject;

import com.focustar.util.ConfigService;
import com.focustar.util.MyHttpUtils;
import com.focustar.util.WeixinUtil;

public class NineJober extends Jober {
	
	private String keyword = "";
	public NineJober(String openid,String keyword){
		this.openId = openid;
		this.keyword = keyword;
	}

	@Override
	protected void startWork() {
		ConfigService config = new ConfigService();
		
		String nineKeyword = config.getWxProperty("NINE_KEYWORD");
		
		if(nineKeyword != null && !"".equals(nineKeyword)){
			
			logger.info("9周年关键字["+nineKeyword+"]");
			
			if(nineKeyword.trim().equals(this.keyword)){
				//获取请求的服务器地址
				String serverIp = config.getWxProperty("NINE_SERVER");
				
				//String tips = config.getWxProperty("NINE_TIPS");
				
				StringBuilder requestUrl = new StringBuilder();
				requestUrl.append(serverIp).append("?openid=").append(this.openId);
				
				logger.info("开始请求抽奖码 地址>>>>  " + requestUrl.toString());
				
				JSONObject  jsonObj = MyHttpUtils.httpRequestJSON(requestUrl.toString(),"GET" , null);
				
				logger.info("请求结果  >>> " + jsonObj);
				
				if(jsonObj != null && jsonObj.containsKey("IsResult") && jsonObj.containsKey("Result")){
				
					boolean isResult = jsonObj.getBoolean("IsResult");
					
					if(isResult){
						
						JSONObject jsonDataObj = jsonObj.getJSONObject("Result");
						
						String errcode = jsonDataObj.getString("errorcode");
						
						if("0".equals(errcode) || "001".equals(errcode)){
							String awardCode = jsonDataObj.getString("AwardCode");
							//tips = tips.replaceAll("AWARDCODE", awardCode);
							logger.info("当前状态>>>"+errcode+"  目标粉丝["+this.openId+"] >>> " + awardCode);
							msgObj = WeixinUtil.buildTextMessage(this.openId,awardCode);
						}
						else{
							logger.info("请求返回结果  >>> 错误代码" + errcode +" , 错误原因>>> " + jsonDataObj.getString("msg"));
						}
						
					}else{
						
						logger.info("请求返回不成功===>" + jsonObj);
						
						/*JSONObject jsonDataObj = jsonObj.getJSONObject("Result");
						String errcode = jsonDataObj.getString("errorcode");
						String returnOpenid = jsonDataObj.getString("OPENID");
						String msg = jsonDataObj.getString("msg");
						if("0".equals(errcode)){
							logger.info("请求返回结果成功？ 但isResult=false");
						}else{
							logger.info("请求返回结果出错  >>> 错误代码" + errcode +" , 错误原因>>> " + msg+" 请求openid["+this.openId+"],返回openid["+returnOpenid+"]");
						}*/
					}
				
				}else{
					logger.info("请求结果为空 ===> " + jsonObj);
					//String text = "网络出错或服务器繁忙，请稍候再试！";
					//msgObj = WeixinUtil.buildTextMessage(this.openId, text);
				}
			}else{
				logger.info("用户["+this.openId+"]输入关键字["+this.keyword+"] 与  9周年关键字["+nineKeyword+"]不匹配。");
			}
		
		}else{
			logger.info("没有设置9周年关键字！");
		}
	}

}
