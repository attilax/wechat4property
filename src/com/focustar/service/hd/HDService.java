package com.focustar.service.hd;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.focustar.entity.THDMember;
import com.focustar.entity.TMbGroup;
import com.focustar.entity.TMbWeixinuser;
import com.focustar.entity.hd.BindWxObject;
import com.focustar.entity.hd.HDCredit;
import com.focustar.entity.hd.HDMember;
import com.focustar.entity.hd.ScoreSubject;
import com.focustar.entity.hd.ScoreType;
import com.focustar.entity.hd.Scoreaccount;
import com.focustar.entity.hd.SubjectAccount;
import com.focustar.entity.hd.SubjectAccounts;
import com.focustar.util.Constant;
import com.focustar.util.MyHttpUtils;

public class HDService {

	private final static Logger logger = Logger.getLogger(HDService.class);

	// 设置验证用户名与密码
	public static void author() {
		Authenticator.setDefault(new MyAuthenticator());
	}

	/**
	 * @category 请求会员信息
	 * @param wxUser
	 * @return
	 */
	public static HDMember requestMemberByCardNo(TMbWeixinuser wxUser) {

		logger.info("开始请求会员数据...");
		author();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		HDMember member = null;
		String curServer = loadServerUrl(wxUser);
		StringBuilder paramsStr = new StringBuilder();

		StringBuilder serverUrl = new StringBuilder();
		serverUrl.append(curServer).append(HDMember.MEMBER_QUERY_BYCARD).append("cardnum=").append(wxUser.getMember().getCardNo());

		// 组装请求参数副本
		paramsStr.append(HDMember.MEMBER_QUERY_BYCARD).append("cardnum=").append(wxUser.getMember().getCardNo());

		Date now = new Date();
		logger.info("开始请求服务器  【" + sdf.format(now) + "】");
		String jsonStr = recheckRequest(-1, serverUrl.toString(), "GET", null,paramsStr.toString());
		Date end = new Date();
		logger.info("结束请求服务器  【" + sdf.format(end) + "】");
		long costTime = end.getTime() - now.getTime();
		logger.info("总请求耗时 【" + costTime + "】毫秒");
		logger.info("请求结果 >>> " + jsonStr);

		if (jsonStr != null && !"".equals(jsonStr)) {
			try {
				//转json对象
				JSONObject jsonObj = JSONObject.parseObject(jsonStr);
				
				if(jsonObj.containsKey("cardinfo")){
				
					String stateStr = jsonObj.getJSONObject("cardinfo").getString("state");
					
					if("已核对".equals(stateStr)){
						
						logger.info("该卡号为已核对，重新查找！");
						int fromIndex = Constant.SERVER_MAP.size();
						String tmpUrl = Constant.SERVER_MAP.get(Constant.SERVER_KEY_ARRAY[fromIndex-1]).getServerUrl();
						serverUrl.append(tmpUrl).append(HDMember.MEMBER_QUERY_BYCARD).append("cardnum=").append(wxUser.getMember().getCardNo());
						jsonStr = recheckFromBackRequest(fromIndex, serverUrl.toString(), "GET", null,paramsStr.toString());
						
						if(jsonStr != null && !"".equals(jsonStr)){
							jsonObj = JSONObject.parseObject(jsonStr);
							
							member = getCardInfoMember(jsonObj);
							
							if(member != null){
								//计算会员总积分
								double score = totalGialenCredit(jsonObj);
								member.setScore(score);
								//设置会员卡号
								member.setCardNo(wxUser.getMember().getCardNo());
							}else{
								logger.info("重新获取也没有会员信息？");
							}
						}
						
					}else{
						 member = getCardInfoMember(jsonObj);
						// 会员总积分
						if (member != null) {
							//计算会员总积分
							double score = totalGialenCredit(jsonObj);
							member.setScore(score);
							//设置会员卡号
							member.setCardNo(wxUser.getMember().getCardNo());
						} else {
							logger.info("无会员信息？");
						}
					}
				}
			} catch (Exception e) {
				logger.info("会员JSON转换对象异常  >>>" + jsonStr);
				e.printStackTrace();
			}
		}

		return member;
	}

	/**
	 * @category 获取会员积分
	 * @param memberId
	 * @return
	 */
	public static Map<Object, Object> requestMemberCredit(TMbWeixinuser wxUser) {
		
		author();
		
		Integer passIndex = -1;
		Integer fromIndex = Constant.SERVER_MAP.size();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//选获取会员信息，获取成功后，取成功获取会员信息的url请求积分
		
		HDMember member = null;
		String curServer = loadServerUrl(wxUser);
		StringBuilder paramsStr = new StringBuilder();

		StringBuilder serverUrl = new StringBuilder();
		serverUrl.append(curServer).append(HDMember.MEMBER_QUERY_BYCARD).append("cardnum=").append(wxUser.getMember().getCardNo());

		// 组装请求参数副本
		paramsStr.append(HDMember.MEMBER_QUERY_BYCARD).append("cardnum=").append(wxUser.getMember().getCardNo());

		Date now1 = new Date();
		logger.info("开始请求服务器  【" + sdf.format(now1) + "】");
		
		String jsonMemStr = recheckRequest(passIndex, serverUrl.toString(), "GET", null,paramsStr.toString());
		Date end1 = new Date();
		logger.info("结束请求服务器  【" + sdf.format(end1) + "】");
		long costTime1 = end1.getTime() - now1.getTime();
		logger.info("总请求耗时 【" + costTime1 + "】毫秒");
		logger.info("请求结果 >>> " + jsonMemStr);
		
		Map<Object, Object> creditMap = null;
		
		if (jsonMemStr != null && !"".equals(jsonMemStr)) {
			try {
				//转json对象
				JSONObject jsonObj = JSONObject.parseObject(jsonMemStr);
				
				if(jsonObj.containsKey("cardinfo")){
				
					String stateStr = jsonObj.getJSONObject("cardinfo").getString("state");
					
					boolean isFound = false;
					
					if("已核对".equals(stateStr)){
					
							while("已核对".equals(stateStr) && fromIndex >0){
									//if("已核对".equals(stateStr)){
										
										logger.info("该卡号为已核对，重新查找！");
										
										String tmpUrl = Constant.SERVER_MAP.get(Constant.SERVER_KEY_ARRAY[fromIndex-1]).getServerUrl();
										serverUrl.append(tmpUrl).append(HDMember.MEMBER_QUERY_BYCARD).append("cardnum=").append(wxUser.getMember().getCardNo());
										jsonMemStr = recheckFromBackRequest(fromIndex, serverUrl.toString(), "GET", null,paramsStr.toString());
										fromIndex -= 1;
										if(jsonMemStr != null && !"".equals(jsonMemStr)){
											jsonObj = JSONObject.parseObject(jsonMemStr);
											
											stateStr = jsonObj.getJSONObject("cardinfo").getString("state");
											
											logger.info("重新查找后的卡状态====>"+stateStr);
											
											member = getCardInfoMember(jsonObj);
											
											if(member != null){
												//计算会员总积分
												double score = totalGialenCredit(jsonObj);
												creditMap = new HashMap<Object, Object>();
												creditMap.put("CREDIT_TOTAL", score);
												isFound = true;
												break;
											}else{
												logger.info("重新获取也没有会员信息？");
												stateStr = "已核对";
												
											}
										}else{
											stateStr = "已核对";
										}
										
									//}else{
										/* member = getCardInfoMember(jsonObj);
										// 会员总积分
										if (member != null) {
											//计算会员总积分
											double score = totalGialenCredit(jsonObj);
											creditMap = new HashMap<Object, Object>();
											creditMap.put("CREDIT_TOTAL", score);
										} else {
											logger.info("无会员信息？");
										}*/
									//}
							}
					
					}else{
								member = getCardInfoMember(jsonObj);
								
								if("使用中".equals(stateStr) && member == null){
								
												while("使用中".equals(stateStr) && member == null && fromIndex > 0){
														
														logger.info("该卡号为使用中，但会员信息为null！");
														
														String tmpUrl = Constant.SERVER_MAP.get(Constant.SERVER_KEY_ARRAY[fromIndex-1]).getServerUrl();
														serverUrl.append(tmpUrl).append(HDMember.MEMBER_QUERY_BYCARD).append("cardnum=").append(wxUser.getMember().getCardNo());
														jsonMemStr = recheckFromBackRequest(fromIndex, serverUrl.toString(), "GET", null,paramsStr.toString());
														fromIndex -= 1;
														if(jsonMemStr != null && !"".equals(jsonMemStr)){
															jsonObj = JSONObject.parseObject(jsonMemStr);
															
															stateStr = jsonObj.getJSONObject("cardinfo").getString("state");
															
															logger.info("重新查找后的卡状态====>"+stateStr);
															
															member = getCardInfoMember(jsonObj);
															
															if(member != null){
																//计算会员总积分
																double score = totalGialenCredit(jsonObj);
																creditMap = new HashMap<Object, Object>();
																creditMap.put("CREDIT_TOTAL", score);
																isFound = true;
																break;
															}else{
																logger.info("重新获取也没有会员信息？");
																
															}
														}
											}
								}else{
									//正常计算会员积分
									//计算会员总积分
									double score = totalGialenCredit(jsonObj);
									creditMap = new HashMap<Object, Object>();
									creditMap.put("CREDIT_TOTAL", score);
								}
					}
					
					
					
					/*if(isFound){
					 	member = getCardInfoMember(jsonObj);
						// 会员总积分
						if (member != null) {
							//计算会员总积分
							double score = totalGialenCredit(jsonObj);
							creditMap = new HashMap<Object, Object>();
							creditMap.put("CREDIT_TOTAL", score);
						} else {
							logger.info("无会员信息？");
						}
					}*/
				}
			} catch (Exception e) {
				logger.info("会员JSON转换对象异常  >>>" + jsonMemStr);
				e.printStackTrace();
			}
		}
		
		
		///////////////////////////////////////////////////////////////////////////////////////////////积分查询
		/*Map<Object, Object> creditMap = null;
		String curCardServer = loadServerUrl(wxUser);
		StringBuilder paramsCardStr = new StringBuilder();
		// 初始化参数
		HDCredit param = new HDCredit(HDCredit.CREDIT_TYPE_QUERY);
		param.setAccount_accesscode(wxUser.getMember().getCardNo());// 会员号

		StringBuilder url = new StringBuilder();
		url.append(curCardServer).append(param.toString());
		paramsCardStr.append(param.toString());

		Date now = new Date();
		logger.info("开始请求服务器  【" + sdf.format(now) + "】");
		String jsonStr = recheckRequest(-1, url.toString(),param.getRequestMethod(), null, paramsCardStr.toString());
		Date end = new Date();
		logger.info("结束请求服务器  【" + sdf.format(end) + "】");
		long costTime = end.getTime() - now.getTime();
		logger.info("总请求耗时 【" + costTime + "】毫秒");
		logger.info(jsonStr);
		
		//TODO 检查会员卡的状态,如果是
		
		if (jsonStr != null && !"".equals(jsonStr)) {
			// 获取积分列表
			SubjectAccounts subjectAccList = JSONObject.parseObject(jsonStr,SubjectAccounts.class);
			// 返回积分
			creditMap = new HashMap<Object, Object>();
			creditMap.put("CREDIT_TOTAL", totalGialenCredit(subjectAccList));
		}*/

		return creditMap;
	}

	/**
	 * @category 计算积分
	 * @param subjectAccList
	 * @return
	 */
	private static double totalGialenCredit(JSONObject jsonObj) {
		String creditJson = jsonObj.getJSONObject("cardinfo")
				.getJSONObject("desAccount")
				.getJSONObject("scoreAccount").toJSONString();
		SubjectAccounts subjectAccList = JSONObject.parseObject(
				creditJson, SubjectAccounts.class);

		if (subjectAccList != null
				&& subjectAccList.getSubjectAccounts() != null
				&& subjectAccList.getSubjectAccounts().size() > 0) {
			logger.info("计算积分");
			return totalGialenCredit(subjectAccList);
		} else {
			logger.info("积分对象为空，无法计算积分");
		}

		return 0;
	}
	
	
	private static double totalGialenCredit(SubjectAccounts subjectAccList) {

		if (subjectAccList != null
				&& subjectAccList.getSubjectAccounts() != null
				&& subjectAccList.getSubjectAccounts().size() > 0) {
			logger.info("计算积分");
			double totalCredit = 0;

			for (SubjectAccount acc : subjectAccList.getSubjectAccounts()) {

				logger.info(acc.getScore() + "  >>>  "+ acc.getScoreType().getName() + " >>> "+ acc.getScoreType().getCode());
				// 只取积分
				if (acc.getScoreType().getCode().equals("0001")) {
					BigDecimal b1 = new BigDecimal(totalCredit);
					BigDecimal b2 = new BigDecimal(acc.getScore());

					BigDecimal result = b1.add(b2, MathContext.DECIMAL32);
					totalCredit = result.doubleValue();
				}
			}
			BigDecimal bd = new BigDecimal(totalCredit);
			logger.info("积分 >>> " + totalCredit);

			return bd.doubleValue();
		} else {
			logger.info("积分对象为空，无法计算积分");
		}

		return 0;
	}

	private static HDMember getCardInfoMember(JSONObject jsonObj) {
		HDMember member = null;
		try{
			// 获取会员的json
			if (jsonObj.containsKey("member")) {
				JSONObject memJson = jsonObj.getJSONObject("member");
				if (memJson != null) {
					member = JSONObject.parseObject(memJson.toJSONString(),HDMember.class);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return member;
	}

	/***
	 * @category 验证会员是否有效
	 * @param memberId
	 * @param mobileNumber
	 * @return
	 */
	public static  boolean  requestValidMember(String serverUrl, String cardNo,
			String cellPhone, Map<Object, Object> callBackMap) {
		author();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String cardMemCode = "";
		String phoneMemCode = "";
		StringBuilder cardUrl = new StringBuilder();
		StringBuilder paramsStr1 = new StringBuilder();
		//组装第一次请求参数
		cardUrl.append(serverUrl).append(HDMember.MEMBER_QUERY_BYCARD).append("cardnum=").append(cardNo);
		//传参，在递归时使用
		paramsStr1.append(HDMember.MEMBER_QUERY_BYCARD).append("cardnum=").append(cardNo);

		//默认积分
		double score = 0;
		String cardMemJson = null;
		
		try {
			//使用会员卡号查询会员信息,遍历所有服务器
			cardMemJson = recheckRequest(-1, cardUrl.toString(), "GET", null,paramsStr1.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("根据会员卡号请求的会员全部信息  >>> " + cardMemJson);
		if (cardMemJson != null && !"".equals(cardMemJson)) {
			JSONObject jsonObject = JSONObject.parseObject(cardMemJson);
			if (jsonObject != null) {
				JSONObject memJsonObj = null;
				// 会员总积分,这里需要做处理，把婷美积分过滤
				if (jsonObject.containsKey("cardinfo")) {
					
					String stateStr = jsonObject.getJSONObject("cardinfo").getString("state");
					String cardType = jsonObject.getJSONObject("cardinfo").getJSONObject("cardType").getString("code");
					// 只处理使用中的与0001类型的卡
					if ("使用中".equals(stateStr) && "0001".equals(cardType)) {
						// 转换会员对象信息
						memJsonObj = jsonObject.getJSONObject("member");
						logger.info(stateStr + "  >>> 使用中，并且是会员卡");
						
						// 获取会员的唯一标识
						if (memJsonObj != null) {
							cardMemCode = memJsonObj.containsKey("code") ? memJsonObj.getString("code") : "";
						}else{
							logger.info("卡没有会员消息！");
							callBackMap.put("resultMsg", "3");
						}
						
						// 获取积分帐户的积分
						if (jsonObject.getJSONObject("cardinfo").containsKey("desAccount")) {
							score = totalGialenCredit(jsonObject);
						}
					} else if ("使用中".equals(stateStr)&& !"0001".equals(cardType)) {
						logger.info(stateStr + "  >>>> 使用中，但非会员卡");
						callBackMap.put("resultMsg", "1");
					} else {
						logger.info(stateStr + "  >>> 此卡状态非使用中");
						if("已核对".equals(stateStr) && "0001".equals(cardType)){
							
							logger.info("该卡为已核实，重新获取");
							
							cardUrl.setLength(0);
							
							int backIndex = Constant.SERVER_MAP.size();
							String backKey = (String)Constant.SERVER_KEY_ARRAY[backIndex-1];
							String tmpServerUrl = Constant.SERVER_MAP.get(backKey).getServerUrl();
							System.out.println(Constant.SERVER_MAP.get(backKey).getServerUrl());
							cardUrl.append(tmpServerUrl).append(HDMember.MEMBER_QUERY_BYCARD).append("cardnum=").append(cardNo);
							
							cardMemJson = recheckFromBackRequest(backIndex,cardUrl.toString(), "GET", null,paramsStr1.toString());
							logger.info("重新获取结果  ===>" + cardMemJson);
							if (cardMemJson != null && !"".equals(cardMemJson)) {
								jsonObject = JSONObject.parseObject(cardMemJson);
								
								if (jsonObject.containsKey("cardinfo")) {
									stateStr = jsonObject.getJSONObject("cardinfo").getString("state");
									cardType = jsonObject.getJSONObject("cardinfo").getJSONObject("cardType").getString("code");
									// 只处理使用中的与0001类型的卡
									if ("使用中".equals(stateStr) && "0001".equals(cardType)) {
										// 转换会员对象信息
										memJsonObj = jsonObject.getJSONObject("member");
										logger.info(stateStr + "  >>> 使用中，并且是会员卡");
										
										// 获取会员的唯一标识
										if (memJsonObj != null) {
											cardMemCode = memJsonObj.containsKey("code") ? memJsonObj.getString("code") : "";
										}else{
											logger.info("卡没有会员消息！");
											callBackMap.put("resultMsg", "3");
										}
										
										// 获取积分帐户的积分
										if (jsonObject.getJSONObject("cardinfo").containsKey("desAccount")) {
											score = totalGialenCredit(jsonObject);
										}
									} else if ("使用中".equals(stateStr)&& !"0001".equals(cardType)) {
										logger.info(stateStr + "  >>>> 使用中，但非会员卡");
										callBackMap.put("resultMsg", "1");
									} else {
										callBackMap.put("resultMsg", "2");
									}
								}
							}
							
						}else{
							callBackMap.put("resultMsg", "2");
						}
					}
				}
				
			} else {
				logger.info("无法获取此卡信息！");
				callBackMap.put("resultMsg", "4");
			}
		}


		// 必须会员卡号能获取到会员信息，才会验证手机是否能获取到
		if (cardMemCode != null && !"".equals(cardMemCode)) {
			
			StringBuilder paramsStr2 = new StringBuilder();
			cardUrl.setLength(0);
			cardUrl.append(serverUrl).append(HDMember.MEMBER_QUERY_BYMOBILE).append("mobilenum=").append(cellPhone);
			paramsStr2.append(HDMember.MEMBER_QUERY_BYMOBILE).append("mobilenum=").append(cellPhone);
			
			// 使用会员手机查询会员信息
			String phoneMemJson = null;

			try {
				Date now = new Date();
				logger.info("开始请求服务器  【" + sdf.format(now) + "】");
				phoneMemJson = recheckRequest(-1, cardUrl.toString(), "GET",null, paramsStr2.toString());
				Date end = new Date();
				logger.info("结束请求服务器  【" + sdf.format(end) + "】");
				long costTime = end.getTime() - now.getTime();
				logger.info("总请求耗时 【" + costTime + "】毫秒");
			} catch (Exception e) {
				e.printStackTrace();
			}

			logger.info("根据会员手机号请求的会员全部信息  >>> " + phoneMemJson);

			if (phoneMemJson != null && !"".equals(phoneMemJson)) {
				JSONObject jsonObject2 = JSONObject.parseObject(phoneMemJson);
				// 获取会员的唯一标识
				if (jsonObject2 != null) {
					phoneMemCode = jsonObject2.containsKey("code") ? jsonObject2.getString("code") : "";
					HDMember member = JSONObject.parseObject(phoneMemJson,HDMember.class);
					member.setCardNo(cardNo);
					callBackMap.put("member", member);
				}
			} else {
				logger.info("手机验证会员信息失败！");
				callBackMap.put("resultMsg", "5");
			}

			if (!"".equals(cardMemCode) && !"".equals(phoneMemCode)) {
				if (cardMemCode.equalsIgnoreCase(phoneMemCode)) {
					logger.info("匹配绑定成功！");
					callBackMap.put("score", score);
					callBackMap.put("resultMsg", "0");
					return true;
				} else {
					logger.info("你输入卡号或手机号不一致！");
					callBackMap.put("resultMsg", "6");
				}
			}
		}

		return false;
	}


	/***
	 * @category 会员签到
	 * @param memberId
	 * @return
	 */
	public static boolean requestDaySign(TMbWeixinuser wxUser) {
		author();
		String serverUrl = loadServerUrl(wxUser);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuilder paramsStr = new StringBuilder();

		HDCredit credit = new HDCredit(HDCredit.CREDIT_TYPE_ADD);
		credit.setXid("123");
		credit.setAccount_accesscode(wxUser.getMember().getCardNo());
		StringBuilder builder = new StringBuilder();
		builder.append(serverUrl).append(credit.toString());

		paramsStr.append(credit.toString());

		List<SubjectAccount> jsonList = new ArrayList<SubjectAccount>();

		ScoreType scoreType = new ScoreType();
		scoreType.setCode("0001");
		scoreType.setName("积分");
		scoreType.setUuid("2c9e86b33e3f94b7013e462e30621378");

		ScoreSubject scoreSubject = new ScoreSubject();
		// scoreSubject.setUuid("");
		scoreSubject.setName("调整");
		scoreSubject.setCode("104");

		SubjectAccount subjectAccount = new SubjectAccount();
		subjectAccount.setRemark("签到积分");
		subjectAccount.setScoreType(scoreType);
		subjectAccount.setScore(10);
		subjectAccount.setScoreSubject(scoreSubject);

		Scoreaccount scoreaccount = new Scoreaccount();
		jsonList.add(subjectAccount);
		scoreaccount.setSubjectAccounts(jsonList);

		String postJson = JSONObject.toJSONString(scoreaccount);

		Date now = new Date();
		logger.info("开始请求服务器  【" + sdf.format(now) + "】");
		String creditJson = recheckRequest(-1, builder.toString(),credit.getRequestMethod(), postJson, paramsStr.toString());
		Date end = new Date();
		logger.info("结束请求服务器  【" + sdf.format(end) + "】");
		long costTime = end.getTime() - now.getTime();
		logger.info("总请求耗时 【" + costTime + "】毫秒");

		System.out.println(creditJson);
		if (creditJson != null && !"".equals(creditJson)) {
			// 检查返回的内容是否正确
			return true;
		}

		return false;
	}

	private static String loadServerUrl(TMbWeixinuser wxUser) {
		String url = null;
		TMbGroup curGroup = wxUser.getGroup();
		if (curGroup != null) {// 如果有上级
			if (curGroup.getParent() == null) {// 没有上一级，这个就是分公司？
				url = curGroup.getServerUrl();
				logger.info("请求服务器  >>>" + curGroup.getGroupname());
			} else {// 获取分公司的服务器地址
				url = curGroup.getParent().getServerUrl();
				logger.info("请求服务器  >>>" + curGroup.getParent().getGroupname());
			}
		}

		if (url == null || "".equals(url)) {// 无法从用户的信息中获取到请求服务器

			TMbGroup firstGroup = (TMbGroup) Constant.SERVER_MAP.get(Constant.SERVER_KEY_ARRAY[0]);

			logger.info("返回第一个服务器  >>> " + firstGroup.getGroupname());

			url = firstGroup.getServerUrl();
		}

		return url;
	}

	private static InputStream inputStream = null;
	private static InputStreamReader inputStreamReader = null;
	private static HttpURLConnection httpUrlConn = null;
	private static BufferedReader bufferedReader = null;

	private static String recheckRequest(Integer index, String url,String RequestMethod, String postData, String paramsStr) {

		String result = null;
		try {
			result = HDhttpRequest(url, RequestMethod, postData);
			logger.info("请求url  >>> " + url);
			logger.info("请求结果        >>>  " + result);

			if ("".equals(result)) {
				logger.info("返回空？");
				try {
					if (inputStream != null) {
						inputStream.close();
					}
					if (inputStreamReader != null) {
						inputStreamReader.close();
					}
					if (httpUrlConn != null) {
						httpUrlConn.disconnect();
					}
					if (bufferedReader != null) {
						bufferedReader.close();
					}
				} catch (Exception ee) {
					ee.printStackTrace();
				}

				index += 1;
				// 取下一个服务器地址
				if (index < Constant.SERVER_KEY_ARRAY.length) {
					// 获取下条记录的key值
					String nextKey = Constant.SERVER_KEY_ARRAY[index].toString();
					// 获取记录中的分公司
					TMbGroup firstGroup = (TMbGroup) Constant.SERVER_MAP.get(nextKey);

					// 取分公司地址
					String serverUrl = firstGroup.getServerUrl();
					StringBuilder requestUrl = new StringBuilder();

					if (paramsStr != null && !"".equals(paramsStr)) {
						requestUrl.append(serverUrl).append(paramsStr);
					}
					logger.info("请求分公司 >>> " + firstGroup.getGroupname()+ "  >>>> " + requestUrl);
					// 递归
					result = recheckRequest(index, requestUrl.toString(),RequestMethod, postData, paramsStr);
				}

			}

		} catch (Exception e) {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
				if (inputStreamReader != null) {
					inputStreamReader.close();
				}
				if (httpUrlConn != null) {
					httpUrlConn.disconnect();
				}
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (Exception ee) {
				ee.printStackTrace();
			}

			index += 1;
			// 取下一个服务器地址
			if (index < Constant.SERVER_KEY_ARRAY.length) {
				// 获取下条记录的key值
				String nextKey = Constant.SERVER_KEY_ARRAY[index].toString();
				// 获取记录中的分公司
				TMbGroup firstGroup = (TMbGroup) Constant.SERVER_MAP.get(nextKey);

				// 取分公司地址
				String serverUrl = firstGroup.getServerUrl();
				StringBuilder requestUrl = new StringBuilder();

				if (paramsStr != null && !"".equals(paramsStr)) {
					requestUrl.append(serverUrl).append(paramsStr);
				}
				logger.info("请求分公司   >>> " + firstGroup.getGroupname()+ "  >>>> " + requestUrl);
				// 递归
				result = recheckRequest(index, requestUrl.toString(),RequestMethod, postData, paramsStr);
			}
		}finally{
			try {
				if (inputStream != null) {
					inputStream.close();
				}
				if (inputStreamReader != null) {
					inputStreamReader.close();
				}
				if (httpUrlConn != null) {
					httpUrlConn.disconnect();
				}
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (Exception ee) {
				ee.printStackTrace();
			}finally{
				try {
					if (inputStream != null) {
						inputStream.close();
					}
					if (inputStreamReader != null) {
						inputStreamReader.close();
					}
					if (httpUrlConn != null) {
						httpUrlConn.disconnect();
					}
					if (bufferedReader != null) {
						bufferedReader.close();
					}
				} catch (Exception ee) {
					ee.printStackTrace();
				}
			}
		}
		return result;
	}
	
	
	
	/**
	 * @category 从后获取
	 * @param index
	 * @param url
	 * @param RequestMethod
	 * @param postData
	 * @param paramsStr
	 * @return
	 */
	private static String recheckFromBackRequest(Integer index, String url,String RequestMethod, String postData, String paramsStr) {

		String result = null;
		try {
			result = HDhttpRequest(url, RequestMethod, postData);
			logger.info("请求url  >>> " + url);
			logger.info("请求结果        >>>  " + result);

			if ("".equals(result)) {
				logger.info("返回空？");
				try {
					if (inputStream != null) {
						inputStream.close();
					}
					if (inputStreamReader != null) {
						inputStreamReader.close();
					}
					if (httpUrlConn != null) {
						httpUrlConn.disconnect();
					}
					if (bufferedReader != null) {
						bufferedReader.close();
					}
				} catch (Exception ee) {
					ee.printStackTrace();
				}

				index -= 1;
				// 取下一个服务器地址
				if (Constant.SERVER_KEY_ARRAY.length >= 0) {
					// 获取下条记录的key值
					String nextKey = Constant.SERVER_KEY_ARRAY[index].toString();
					// 获取记录中的分公司
					TMbGroup firstGroup = (TMbGroup) Constant.SERVER_MAP
							.get(nextKey);

					// 取分公司地址
					String serverUrl = firstGroup.getServerUrl();
					StringBuilder requestUrl = new StringBuilder();

					if (paramsStr != null && !"".equals(paramsStr)) {
						requestUrl.append(serverUrl).append(paramsStr);
					}
					logger.info("请求分公司 >>> " + firstGroup.getGroupname()+ "  >>>> " + requestUrl);
					// 递归
					result = recheckFromBackRequest(index, requestUrl.toString(),RequestMethod, postData, paramsStr);
				}

			}

		} catch (Exception e) {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
				if (inputStreamReader != null) {
					inputStreamReader.close();
				}
				if (httpUrlConn != null) {
					httpUrlConn.disconnect();
				}
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (Exception ee) {
				ee.printStackTrace();
			}

			index -= 1;
			// 取下一个服务器地址
			if (Constant.SERVER_KEY_ARRAY.length >= 0) {
				// 获取下条记录的key值
				String nextKey = Constant.SERVER_KEY_ARRAY[index].toString();
				// 获取记录中的分公司
				TMbGroup firstGroup = (TMbGroup) Constant.SERVER_MAP.get(nextKey);

				// 取分公司地址
				String serverUrl = firstGroup.getServerUrl();
				StringBuilder requestUrl = new StringBuilder();

				if (paramsStr != null && !"".equals(paramsStr)) {
					requestUrl.append(serverUrl).append(paramsStr);
				}
				logger.info("请求分公司   >>> " + firstGroup.getGroupname()+ "  >>>> " + requestUrl);
				// 递归
				result = recheckFromBackRequest(index, requestUrl.toString(),RequestMethod, postData, paramsStr);
			}
		}finally{
			try {
				if (inputStream != null) {
					inputStream.close();
				}
				if (inputStreamReader != null) {
					inputStreamReader.close();
				}
				if (httpUrlConn != null) {
					httpUrlConn.disconnect();
				}
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (Exception ee) {
				ee.printStackTrace();
			}
		}
		return result;
	}

	public static  String HDhttpRequest(String requestUrl, String requestMethod,
			String outputStr) throws Exception {

		StringBuffer buffer = new StringBuffer();

		URL url = new URL(requestUrl);
		httpUrlConn = (HttpURLConnection) url.openConnection();
		httpUrlConn.setDoOutput(true);
		httpUrlConn.setDoInput(true);
		httpUrlConn.setUseCaches(false);
		// 设置请求方式（GET/POST）
		httpUrlConn.setRequestMethod(requestMethod);
		httpUrlConn.setRequestProperty("Content-Type", "application/json");
		httpUrlConn.setRequestProperty("Cache-Control", "no-cache");
		httpUrlConn.setConnectTimeout(30000);
		httpUrlConn.setReadTimeout(30000);
		httpUrlConn.connect();

		// 当有数据需要提交时
		if (null != outputStr) {
			OutputStream outputStream = httpUrlConn.getOutputStream();
			// 注意编码格式，防止中文乱码
			outputStream.write(outputStr.getBytes("UTF-8"));
			outputStream.close();
		}

		// 将返回的输入流转换成字符串
		inputStream = httpUrlConn.getInputStream();
		inputStreamReader = new InputStreamReader(inputStream, "utf-8");
		bufferedReader = new BufferedReader(inputStreamReader);

		String str = null;
		while ((str = bufferedReader.readLine()) != null) {
			buffer.append(str);
		}
		bufferedReader.close();
		inputStreamReader.close();
		// 释放资源
		inputStream.close();
		inputStream = null;
		httpUrlConn.disconnect();

		return buffer.toString();
	}
	
	
	public static String requestWxBind(BindWxObject wxObj){
		
		author();
		
		TMbGroup firstGroup = (TMbGroup) Constant.SERVER_MAP.get(Constant.SERVER_KEY_ARRAY[0]);
		
		StringBuilder paramsStr = new StringBuilder();
		
		paramsStr.append(wxObj.toString());
		
		StringBuilder url = new StringBuilder();
		
		url.append(firstGroup.getServerUrl()).append(wxObj.toString());
		
		String result = recheckRequest(-1, url.toString(),"POST", null, paramsStr.toString());
		
		return result;
		
	}

}
