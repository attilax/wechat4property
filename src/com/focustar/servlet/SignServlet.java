package com.focustar.servlet;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.focustar.dao.impl.UserImpl;
import com.focustar.entity.TMbWeixinuser;
import com.focustar.service.hd.HDService;
import com.focustar.util.Constant;
import com.focustar.util.MD5;
import com.focustar.util.MyCacher;
import com.focustar.util.WeixinUtil;

public class SignServlet  extends HttpServlet{
	
	private static Logger logger = Logger.getLogger(SignServlet.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = -1383444358064470602L;

	public void doGet(HttpServletRequest request, HttpServletResponse response){
		
		String openid = request.getParameter("openid");
		
		//TMbWeixinuser user = (TMbWeixinuser)MyCacher.getInstance().getCache(MD5.getMD5(openid));
		
		String strGroupId = request.getParameter("groupid");
	
		logger.info("当前微信用户["+openid+"],签到的分店["+strGroupId+"]");
		
		UserImpl userDao = new UserImpl();
		
		TMbWeixinuser user = userDao.getUserByopenid(openid);
		
		if(user != null && user.getIsSign() != null && user.getIsSign() == TMbWeixinuser.SIGNED){
			//已签到
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String signDateStr = sdf.format(user.getSignDate());
			
			String signTips = (String) Constant.MSG_MAP.get(Constant.INFO_SIGN_TIPS);
			if(signTips != null){
				signTips = signTips.replaceAll(Constant.RP_SIGN_DATE,signDateStr);
			}
			request.setAttribute("message",signTips);
			toSigned(request,response);
		}else{
				//当前微信用户的location
				String lat = request.getParameter("lat");
				String lng = request.getParameter("lng");
				
				logger.info("当前微信用户的坐标  lat ==>" + lat+"    lng ==>" + lng);
				
				//当前点击门店的location
				String clat = request.getParameter("clat");
				String clng = request.getParameter("clng");
				
				logger.info("当前门店的坐标  clat ==>" + clat+"    clng ==>" + clng);
				
				double dlat = Double.parseDouble(lat);
				double dlng = Double.parseDouble(lng);
				
				double dclat = Double.parseDouble(clat);
				double dclng = Double.parseDouble(clng);
				
				//计算两个坐标之前的距离
				int disc = new Double(WeixinUtil.getDistance(dlng, dlat, dclng, dclat)).intValue();
				
				logger.info("计算出两坐标之间的距离   >>>> " + disc);
				
				if(openid != null && !"".equals(openid)){
					
					if(disc <= 50){
							//更新数据库
							user.setIsSign(TMbWeixinuser.SIGNED);
							user.setSignDate(new Date());
							Integer iGroupId = Integer.parseInt(strGroupId);
							//在事务中进行签到功能
							if(userDao.updateUser(user,iGroupId)){
								logger.info("签到距离小于等于50米，所以签到成功！");
								//签到成功，更新缓存
								MyCacher.getInstance().putCache(MD5.getMD5(openid), user,Constant.TEN_MINUTES);
								String okMsg = (String)Constant.MSG_MAP.get(Constant.SIGN_OK);
								//替换赠送多少积分
								okMsg = okMsg.replaceAll(Constant.RP_ADD_CREDIT,Constant.SIGN_CREDIT+"");
								request.setAttribute("message",okMsg);
								toSigned(request, response);
							}else{
								logger.info("更新数据库失败，所以签到失败！");
								String signFail = (String)Constant.MSG_MAP.get(Constant.SIGN_FAIL);
								signFail = signFail.replaceAll(Constant.RP_ADD_CREDIT,Constant.SIGN_CREDIT+"");
								request.setAttribute("message",signFail);
								
							}
					}else{
						//签到失败
						logger.info("签到距离大于50米，所以签到失败！");
						String signFail = (String)Constant.MSG_MAP.get(Constant.SIGN_FAIL);
						signFail = signFail.replaceAll(Constant.RP_ADD_CREDIT,Constant.SIGN_CREDIT+"");
						request.setAttribute("message",signFail);
						toTips(request, response);
					}
				}
		
		}
		
		
	}
	
	private void toSigned(HttpServletRequest request,HttpServletResponse response){
		try {
			request.getRequestDispatcher("mobile/signtips.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void toTips(HttpServletRequest request,HttpServletResponse response){
		try{
			request.getRequestDispatcher("mobile/signtips.jsp").forward(request, response);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

}
