package com.focustar.thread;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import com.focustar.dao.impl.GroupImpl;
import com.focustar.entity.TMbGroup;
import com.focustar.entity.TMbNews;
import com.focustar.util.Constant;
import com.focustar.util.WeixinUtil;

public class LocationSignJober extends Jober {
	private double latitude;
	private double longitude;
	public LocationSignJober(String openId,double latitude,double longitude){
		this.openId = openId;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	protected void   startWork(){
		
		//返回五公里内，最近的三家门店
		double slat = latitude - 0.025;
		double elat = latitude + 0.025;
		

		//经度
		double slong = longitude - 0.025;
		double elong = longitude + 0.025;
		
		GroupImpl groupImpl = new GroupImpl();

		List<TMbGroup> groupList = groupImpl.selectGroupNearByDistance(0, latitude, longitude, slat, elat, slong, elong);
		List<TMbNews> newsList = new ArrayList<TMbNews>();
		
		if(groupList != null && groupList.size() > 0){
				//请求的servlet
				StringBuilder rqUrl = new StringBuilder();
				for(int i = 0; i < groupList.size(); i ++){
					TMbGroup oneGroup = groupList.get(i);
					if(oneGroup != null){
						rqUrl.setLength(0);
						
						int disc = new Double(WeixinUtil.getDistance(longitude, latitude, oneGroup.getLongitude(), oneGroup.getLatitude())).intValue();
						
						TMbNews oneNews = new TMbNews();
						oneNews.setAuthor("小娇");
						oneNews.setDescription(oneGroup.getRemark()
								+ " 距离大约：" + disc + "米");
						rqUrl.append("/signServlet?openid="+openId)
						     .append("&groupid=").append(oneGroup.getGroupid())
							 .append("&lat=").append(latitude)
							 .append("&lng=").append(longitude)
							 .append("&clat=").append(oneGroup.getLatitude())
							 .append("&clng=").append(oneGroup.getLongitude());
						
						oneNews.setHtmlName(rqUrl.toString());
						newsList.add(oneNews);
					}
					if(i == 3){
						break;
					}
				}
		
				msgObj = WeixinUtil.builderSignMessage(openId, newsList);
		
		}else{
			
			String noSignLoc = (String)Constant.MSG_MAP.get(Constant.INFO_NO_SIGN_LOCATION);
			if(noSignLoc != null && !"".equals(noSignLoc)){
				msgObj = WeixinUtil.buildTextMessage(openId, noSignLoc);
			}
			
		}
		
		
		
	}

}
