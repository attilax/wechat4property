package com.focustar.thread;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.focustar.dao.impl.MemberImpl;
import com.focustar.entity.HdBindHistory;
import com.focustar.entity.THDMember;

public class RecordBindFailJober implements Runnable{
	
	private final static Logger log = Logger.getLogger(RecordBindFailJober.class);
	private List<THDMember>  failList;
	
	
	public RecordBindFailJober(List<THDMember>  failList){
		this.failList = failList;
	}

	@Override
	public void run() {
		log.info("启动同步绑定信息失败记录！");
		if(failList != null && failList.size() > 0){
			
			MemberImpl memDao = new MemberImpl(); 
			
			for(THDMember failMem:failList){
				HdBindHistory his = new HdBindHistory();
				his.setMemberId(failMem.getMemberId());
				his.setOpenId(failMem.getOpenId());
				his.setBindStatus(0);
				his.setRemark(failMem.getRemark());
				his.setAsyncTime(new Date());
				
				if(memDao.addBindHistory(his)){
					log.info("添加同步失败历史到数据库成功 ===>memberId"+his.getMemberId()+"==="+his.getOpenId());
				}else{
					log.info("添加同步失败历史到数据库失败 ===>memberId"+his.getMemberId()+"==="+his.getOpenId());
				}
			}
		}
	}
	
	
	

}
