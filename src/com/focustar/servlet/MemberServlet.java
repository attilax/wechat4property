package com.focustar.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.focustar.dao.impl.GroupImpl;
import com.focustar.dao.impl.UserImpl;
import com.focustar.entity.THDMember;
import com.focustar.entity.TMbGroup;
import com.focustar.entity.TMbWeixinuser;
import com.focustar.entity.hd.HDMember;
import com.focustar.service.hd.HDService2;
import com.focustar.thread.CheckActivityJober;
import com.focustar.util.Constant;
import com.focustar.util.MD5;
import com.focustar.util.MyCacher;

public class MemberServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6805154721578688781L;

	private static final Logger logger = Logger.getLogger(MemberServlet.class);

	/**
	 * Constructor of the object.
	 */
	public MemberServlet() {
		super();
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	@SuppressWarnings("unused")
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String view = request.getParameter("view");
		
		if(view != null && !"".equals(view)){
			
			String openid = request.getParameter("openid");
			
			if(openid != null && !"".equals(openid)){
				
				//查看会员信息
				if("view".equals(view)){
					
					UserImpl userDao = new UserImpl();
					TMbWeixinuser user = userDao.getUserByopenid(openid);
					HDMember hdMember = HDService2.requestMemberByCardNo(user);
					if(hdMember != null){
						
						THDMember member = Convert2THDMember(hdMember);
						//是否需要更新？？
						try{
							member.setUpdateTime(new Date());
							userDao.updateMember(member);
						}catch(Exception e){
							e.printStackTrace();
						}
						request.setAttribute("openid",openid);
						request.setAttribute("member", member);
						toViewMember(request, response);
					}else{
						request.setAttribute("message", "亲，暂时无法查询，请稍候再试！");
						toTips(request, response);
					}
					//会员绑定页面
				}else if("bind".equals(view)){
					
					UserImpl userDao = new UserImpl();
					
					TMbWeixinuser user = userDao.getUserByopenid(openid);
					
					if(user != null && user.getMember() != null){//已绑定
						
						//是否启用线程更新会员数据？
						request.setAttribute("openid",openid);
						request.setAttribute("isBind","1");//绑定成功
						toBindMember(request, response);
						
					}else if(user != null && user.getMember() == null){
						
						request.setAttribute("openid",openid);
						request.setAttribute("isBind","0");//未绑定
						toBindMember(request, response);
						
					}
					
					
				}
				
			}
			
		}else{
			request.setAttribute("message", "请求失败，缺少参数！");
			toTips(request, response);
		}
		
	}
	
	
	public void toViewMember(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		request.getRequestDispatcher("mobile/viewMember.jsp").forward(request, response);
	}

	/***
	 * @category 初始绑定页面
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void toBindMember(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("mobile/bindMember.jsp").forward(request,
				response);
	}
	
	public void redirectBindMember(HttpServletRequest request,HttpServletResponse response,String openid,Map<Object,Object> paramsMap){
		
		StringBuilder rdUrl = new StringBuilder();
		rdUrl.append("mobile/bindMember.jsp?openid="+openid);
		
		for(Map.Entry<Object,Object> entry:paramsMap.entrySet()){
			
			if(entry != null && entry.getKey() != null && entry.getValue() != null){
				String encodeValue = "";
				try {
					encodeValue = URLEncoder.encode(entry.getValue().toString(),"UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				rdUrl.append("&").append(entry.getKey()).append("=").append(encodeValue);
			}
		}
		
		try {
			response.sendRedirect(rdUrl.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	


	/***
	 * @category 提示信息
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void toTips(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("mobile/tips.jsp").forward(request,
				response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String doWhat = request.getParameter("do");

		if (doWhat != null) {

			if ("bind".equals(doWhat)) {// 提交绑定
				
				String cardNo = request.getParameter("cardNo");
				String cellPhone = request.getParameter("cellPhone");
				String openid = request.getParameter("openid");
				
				//根据地址位置，获取最近的一家门店，获取门店的分公司地址
				double lat = 0;
				double lng = 0;
				try{
					lat = Double.parseDouble(request.getParameter("latitude"));
					lng = Double.parseDouble(request.getParameter("longitude"));
				}catch(Exception e){
					e.printStackTrace();
				}
				TMbGroup neastGroup = null;
				if(lat != 0 && lng != 0){
					GroupImpl groupDao = new GroupImpl();
					
					double slat = lat - 0.05;
					double elat = lat + 0.05;
					
					double slong = lng - 0.05;
					double elong = lng + 0.05;
					
					List<TMbGroup> groupList = groupDao.selectGroupNearByDistance(0, lat, lng, slat, elat, slong, elong);;
					if(groupList != null && groupList.size() > 0){
						//只取最近一家
						neastGroup = groupList.get(0);
						logger.info("最近的门店   >>>  " + neastGroup.getGroupname());
					}
				}
				
				//根据经纬度获取
				String serverUrl = null;
				if(neastGroup != null){
					if(neastGroup.getParent() != null){
						logger.info("最近的分公司   >>>  " + neastGroup.getParent().getGroupname());
						serverUrl = neastGroup.getParent().getServerUrl();
					}else{
						logger.info("最近的分公司   >>>  " + neastGroup.getGroupname());
						serverUrl = neastGroup.getServerUrl();
					}
				}else{
					// 获取下条记录的key值
					String nextKey = Constant.SERVER_KEY_ARRAY[0].toString();
					// 获取记录中的分公司
					TMbGroup firstGroup = (TMbGroup) Constant.SERVER_MAP.get(nextKey);
					serverUrl = firstGroup.getServerUrl();
				}
				
				
				Map<Object,Object> callBackMap = new HashMap<Object,Object>();
				
				//serverUrl = "http://211.147.235.198:12501/hdcard-services/api";
				//请求验证
				if(HDService2.requestValidMember(serverUrl,cardNo, cellPhone,callBackMap)){
					
					//验证通过
					UserImpl userDao = new UserImpl();
					//获取会员信息
					HDMember hdMember = (HDMember) callBackMap.get("member"); 
					//积分
					double score = Double.parseDouble(callBackMap.get("score").toString());
					//转数据库存储对你
					THDMember member = Convert2THDMember(hdMember);
					member.setCredit(score);
					//绑定会员信息
					Map<Object,Object> feedBackMap = new HashMap<Object,Object>();
					
					if(userDao.bindMember(openid, member,feedBackMap)){
						
						//更新缓存
						TMbWeixinuser user = (TMbWeixinuser)MyCacher.getInstance().getCache(MD5.getMD5(openid));
						if(user != null){
							user.setMember(member);
							MyCacher.getInstance().putCache(MD5.getMD5(openid), user,Constant.TEN_MINUTES);
						}
						feedBackMap.put("isBind","1");
						
						if(feedBackMap.containsKey("isFirst")){
							//绑定成功，检查是否举行相关的活动
							logger.info("绑定成功，并且是首次绑定，开始检查是否举行抽奖活动");;
							CheckActivityJober chkJober = new CheckActivityJober(openid,CheckActivityJober.ACTIVITY_TYPE_CREDIT,true,serverUrl);
							Constant.AsyncJober.execute(chkJober);
						}
						redirectBindMember(request,response,openid,feedBackMap);
						
					}else{
						
						
						//绑定失败
						Map<Object,Object> paramsMap = new HashMap<Object,Object>();
						paramsMap.put("isBind","2");//绑定失败
						
						//检查数据库返回
						String errMsg = (String) feedBackMap.get("dbErrMsg");
						if(errMsg != null && !"".equals(errMsg)){
							paramsMap.put("errMsg","8");
						}else{
							paramsMap.put("errMsg","7");
						}
						
						redirectBindMember(request,response,openid,paramsMap);
					}
				}else{
					//绑定失败
					Map<Object,Object> paramsMap = new HashMap<Object,Object>();
					paramsMap.put("isBind","2");//绑定失败
					logger.info("绑定失败  >>> " + callBackMap.get("resultMsg"));
					paramsMap.put("errMsg",callBackMap.get("resultMsg"));
					redirectBindMember(request,response,openid,paramsMap);
				}
				
				
			}else{
				request.setAttribute("message","无效请求！");
				toTips(request, response);
			}

		} else {
			request.setAttribute("message","无效请求！");
			toTips(request, response);
		}
	}
	
	
	private static THDMember Convert2THDMember(HDMember hdMember){
		if(hdMember != null){
				THDMember member = new THDMember();
				member.setMemberId(hdMember.getCode());
				member.setAddress(hdMember.getAddress());
				member.setBirthday(hdMember.getBirthday());
				member.setEmail(hdMember.getEmail());
				member.setName(hdMember.getName());
				member.setCellPhone(hdMember.getCellPhone());
				member.setUpdateTime(new Date());
				
				if(hdMember.getScore() != null && hdMember.getScore() > 0){
					member.setCredit(hdMember.getScore());
				}
					member.setCardNo(hdMember.getCardNo());
				
			return member;
		}
		
		return null;
	}

}
