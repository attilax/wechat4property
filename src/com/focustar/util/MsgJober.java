package com.focustar.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.focustar.dao.impl.MemberImpl;
import com.focustar.dao.impl.UserImpl;
import com.focustar.entity.THDMemberCostHistory;
import com.focustar.entity.TMbWeixinuser;
import com.focustar.entity.hd.HDNote;
import com.focustar.entity.weixin.sendMessage.TextMessage;

public class MsgJober implements Runnable {
	private static Logger loger = Logger.getLogger(MsgJober.class);
	private List<HDNote> noteList;
	private SimpleDateFormat sdf = null;
	
	
	
	public MsgJober(List<HDNote> noteList){
		this.noteList = noteList;
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}

	public void run() {
		
		String beginTime = sdf.format(new Date());
		loger.info("线程   开始发送 时间[" + beginTime + "]");
		
		try{
		if(noteList != null && noteList.size() > 0){
			
			//收集memberId
			List<String>  memberIds = new ArrayList<String>();
			List<HDNote> sendList = new ArrayList<HDNote>();
			for(HDNote oneNote:noteList){
				if(oneNote.getShopName() != null && oneNote.getShopName().indexOf("jvsiny科技") == -1){
					loger.info("没有jvsiny推送...");
					if(oneNote != null && oneNote.getMemberId() != null && !"".equals(oneNote.getMemberId())){
							memberIds.add(oneNote.getMemberId());
							sendList.add(oneNote);
					}
				}else{
					loger.info("有jvsiny推送...");
				}
			}
			UserImpl userDao = new UserImpl();
			
			//查询出所有绑定的会员
			List<TMbWeixinuser> userList = userDao.getUserListBymemberIds(memberIds.toArray()); 
			
			if(userList != null && userList.size() > 0){
				
				StringBuilder  msgNote = new StringBuilder();//(String)Constant.MSG_MAP.get(Constant.INFO_CREDIT_NOTE);
				StringBuilder msgNoteReturn = new StringBuilder();//(String)Constant.MSG_MAP.get(Constant.INFO_CREDIT_NOTE_RETURN);
				
				String hdNote = "";
				
				List<THDMemberCostHistory>  costList = new ArrayList<THDMemberCostHistory>();
				
				for(TMbWeixinuser oneUser:userList){
					for(HDNote note: sendList){
						if(oneUser.getMember() != null && oneUser.getMember().getMemberId().equals(note.getMemberId())){
								if(note.getOperType() == 0){ //消费
									
									//String otherCredit = note.getOtherCredit() != 0 ? note.getOtherCredit()+"":"";
									msgNote.append("亲，卡号为").append(note.getCardNo()).append("的会员，");
									msgNote.append("你本次在").append(note.getShopName()).append(" ");
									if(note.getAddCredit() != 0){
										if(note.getUseCredit() != 0){
											msgNote.append("消费得到积分").append(note.getAddCredit()).append(",");
										}else{
											msgNote.append("消费得到积分").append(note.getAddCredit()).append("。");
										}
									}
									if(note.getUseCredit() != 0){
										msgNote.append("使用积分").append(note.getUseCredit()).append("。");
									}
									/*hdNote = hdNote.replaceAll(Constant.RP_CARD_NO, note.getCardNo())
												.replaceAll(Constant.RP_SHOP_NAME, note.getShopName())
										       .replaceAll(Constant.RP_ADD_CREDIT, note.getAddCredit()+"")
										       .replaceAll(Constant.RP_USE_CREDIT, note.getUseCredit()+"")
										       .replaceAll(Constant.RP_OTHER_CREDIT, otherCredit)
										       .replaceAll(Constant.RP_CREDIT_TOTAL,note.getCreditTotal()+"");*/
									
									msgNote.append("可用积分为").append(note.getCreditTotal());
									
									hdNote = msgNote.toString();
									
								}else{ //退货
									
									msgNoteReturn.append("亲，卡号为").append(note.getCardNo()).append("的会员，");
									msgNoteReturn.append("你本次在").append(note.getShopName()).append(" ");
									
									if(note.getUseCredit() != 0){
										if(note.getAddCredit() != 0){
											msgNoteReturn.append("消费退回使用积分").append(note.getUseCredit()).append(",");
										}else{
											msgNoteReturn.append("消费退回使用积分").append(note.getUseCredit()).append("。");
										}
									}
									if(note.getAddCredit() != 0){
										msgNoteReturn.append("取消赠送积分").append(note.getAddCredit()).append("。");
									}
									
									msgNoteReturn.append("可用积分为").append(note.getCreditTotal());
									
									hdNote = msgNoteReturn.toString();
									/*hdNote = msgNoteReturn;
									hdNote = hdNote.replaceAll(Constant.RP_CARD_NO, note.getCardNo())
												.replaceAll(Constant.RP_SHOP_NAME, note.getShopName())
										       .replaceAll(Constant.RP_ADD_CREDIT, note.getAddCredit()+"")
										       .replaceAll(Constant.RP_USE_CREDIT, note.getUseCredit()+"")
										       .replaceAll(Constant.RP_CREDIT_TOTAL,note.getCreditTotal()+"");*/
									
								}
								
								THDMemberCostHistory oneHis = new THDMemberCostHistory();
								oneHis.setCostTime(new Date());
								oneHis.setCredit(note.getAddCredit());
								oneHis.setMemberId(note.getMemberId());
								oneHis.setOperType(note.getOperType());
								oneHis.setShopName(note.getShopName());
								
								costList.add(oneHis);
								
								TextMessage msgObj = WeixinUtil.buildTextMessage(oneUser.getOpenid(), hdNote);
								String jsonData = JSONObject.toJSONString(msgObj);
								int retCode = WeixinUtil.sendMessage(jsonData,Constant.token.getToken());
								
								oneHis.setStateCode(retCode);
								
								loger.info("正在向微信用户["+oneUser.getNickname()+"]发送通知 ["+hdNote+"]");
								loger.info("消息发送结果 ["+(retCode==0?"成功":"失败")+"]["+retCode+"]");
						}
					}
				}
				
				if(costList.size() > 0){
					
					if(costList != null && costList.size() > 0){
					
						MemberImpl memberDao = new MemberImpl();
					
						memberDao.addCostRecordList(costList);
					
					}else{
						loger.info("无需要添加的消息列表");
					}
					
				}
				
			}else{
				loger.info("会员无绑定微信");
			}
		
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		String endTime = sdf.format(new Date());
		loger.info("线程   结束发送时间 [" + endTime + "]");
		
	}

}
