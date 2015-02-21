package com.focustar.thread;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.focustar.dao.impl.MemberImpl;
import com.focustar.entity.THDMember;
import com.focustar.entity.hd.BindWxObject;
import com.focustar.service.hd.HDService;
import com.focustar.util.Constant;


public class Bind2HdJober implements Runnable{
	
	
	private final static Logger log = Logger.getLogger(Bind2HdJober.class);
	
	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	//绑定的会员列表
	private List<THDMember>  bindMemberList;
	
	public Bind2HdJober(){
		
	}
	

	@Override
	public void run() {
		
		
		try {
			getBindMemberList();
		} catch (IOException e) {
			e.printStackTrace();
		}
		beginBindMember2Hd();
		
	}
	
	
	private void getBindMemberList() throws IOException{
		log.info("开始获取已绑定会员列表...");
		
		//读取配置文件的时间
		
		File hdFile = new File(Constant.path+"bind.properties");
		
		//如果不存在，创建一个文件
		if(!hdFile.exists()){
			hdFile.createNewFile();
		}
		Properties prop = new Properties();
		InputStream fis = new FileInputStream(hdFile);
		try{
			prop.load(fis);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(fis != null){
				fis.close();
			}
		}
		String lastTime = prop.getProperty("LASTTIME");
		
		if(lastTime == null || "".equals(lastTime)){
			GregorianCalendar targetTime = new GregorianCalendar();
			targetTime.setTime(new Date(0));
			lastTime = sdf.format(targetTime.getTime());
		}
		
		
		//当前时间
		String currentTime = sdf.format(new Date());
		
		MemberImpl memDao = new MemberImpl();
		
		log.info("获取开始时间==>" + lastTime+" 与  结束时间 ==> " + currentTime+"的绑定数据");
		
		bindMemberList = memDao.getBindMemberList(lastTime,currentTime);
		
		if(bindMemberList != null){
			OutputStream fos = new FileOutputStream(hdFile);
			try{
			prop = new Properties();
			prop.setProperty("LASTTIME", currentTime);
			prop.store(fos, "");
			fos.flush();
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				if(fos != null){
					fos.close();
				}
			}
		}
		
	}
	
	private void beginBindMember2Hd(){
		if(bindMemberList != null && bindMemberList.size() > 0){
			//循环每个绑定的粉丝
			//同步失败记录下来
			//List<THDMember>  failMemList = new ArrayList<THDMember>();
			for(THDMember oneMem:bindMemberList){
				try {
					
					//设置参数
					BindWxObject wxObj = new BindWxObject();
					wxObj.setMobilenum(oneMem.getCellPhone());
					wxObj.setWx(oneMem.getOpenId());
					
					//调用请求接口
					String result = HDService.requestWxBind(wxObj);
					log.info("绑定结果===>"+result);
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			
		}else{
			log.info("无需要同步的绑定会员到hd...");
		}
	}
	
	
	

	
	




	
	
	
	
}


