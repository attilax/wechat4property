package com.focustar.thread;

import java.util.ArrayList;
import java.util.List;

import com.focustar.dao.impl.GroupImpl;
import com.focustar.dao.impl.NewsImpl;
import com.focustar.entity.TMbGroup;
import com.focustar.entity.TMbNews;
import com.focustar.util.Constant;
import com.focustar.util.MD5;
import com.focustar.util.MyCacher;
import com.focustar.util.WeixinUtil;

public class LocationMsgJober extends Jober {
	
	private double latitude;
	private double longitude;
	
	public LocationMsgJober(String openId, double latitude,
			double longitude){
		this.openId = openId;
		this.latitude = latitude;
		this.longitude =longitude;
	}

	
	
	public void startWork(){
				List<TMbNews> newsList = new ArrayList<TMbNews>();
					logger.info("从数据库查询返回...");
					// 查找1公里范围内的门店
					//纬度
					double slat = latitude - 0.025;
					double elat = latitude + 0.025;

					//经度
					double slong = longitude - 0.025;
					double elong = longitude + 0.025;

					GroupImpl groupImpl = new GroupImpl();

					List<TMbGroup> groupList = groupImpl.selectGroupNearByDistance(0, latitude, longitude, slat, elat, slong, elong);
					logger.info("查询五公里范围内..."+groupList!=null?groupList.size():0);
					if (groupList != null && groupList.size() > 0) {
						
						TMbGroup tmpGroup = groupList.get(0);
						NewsImpl newsDao = new NewsImpl();
						
						TMbNews bestNews = null;
						//获取门店精选的图文
						if(tmpGroup.getParent() != null){
							List<TMbNews>  tmpList = newsDao.getNewsList(com.focustar.entity.weixin.sendMessage.NewsMessage.NEWS_SHOP_BEST, tmpGroup.getParent().getGroupid());
							if(tmpList != null && tmpList.size() > 0){
								bestNews = tmpList.get(0);
							}
						}else{
							List<TMbNews>  tmpList = newsDao.getNewsList(com.focustar.entity.weixin.sendMessage.NewsMessage.NEWS_SHOP_BEST, tmpGroup.getGroupid());
							if(tmpList != null && tmpList.size() > 0){
								bestNews = tmpList.get(0);
							}
						}
						
						TMbGroup oneGroup = null;
						for(int i = 0 ; i < groupList.size(); i ++){
							
							oneGroup = groupList.get(i);
							if(oneGroup != null){
								
								TMbNews oneNews = new TMbNews();
								oneNews.setAuthor("小娇");
								
								//计算距离
								int disc = new Double(WeixinUtil.getDistance(longitude, latitude, oneGroup.getLongitude(), oneGroup.getLatitude())).intValue();
								
								oneNews.setDescription(oneGroup.getRemark()+ " 距离大约：" + disc + "米");
								
								newsList.add(oneNews);
								
							}
							
							if(i==8){//公众平台。最多8条
								break;
							}
						}
						msgObj = WeixinUtil.buildLocationMessage(openId, newsList,bestNews);
						
					} else {
						//返回没有附近门店的提示
						logger.info("没有找到附近的门店！");
						String cacheResult = (String)Constant.MSG_MAP.get(Constant.INFO_NO_NEARBY);
						if(cacheResult != null){
							msgObj = WeixinUtil.buildTextMessage(openId, cacheResult);
						}
					}
	}



}
