package com.focustar.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.focustar.entity.hd.HDNote;
import com.focustar.util.Constant;
import com.focustar.util.MsgJober;

/***
 * @category 提供消息提醒
 * @author Administrator
 *
 */

public class MsgServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3689652451706117416L;
	private static Logger logger = Logger.getLogger(MsgServlet.class);
	
	public void doPost(HttpServletRequest request,HttpServletResponse response){
		
		String ip = request.getRemoteAddr();
		if(checkIP(ip)){
			logger.info("访问ip  >>>" + ip +" query string >>> " +request.getQueryString());
			InputStream is = null;
			ByteArrayOutputStream bos = null;
			String jsonData = "";
				try {
					is = request.getInputStream();
					bos = new ByteArrayOutputStream();
					
					byte[] bs = new byte[1024 * 2];
					int len = -1;
					while((len = is.read(bs)) != -1){
						bos.write(bs,0,len);
					}
					jsonData = new String(bos.toByteArray(),"UTF-8");
					
					logger.info("通知数据===>"+jsonData);
					
				} catch (IOException e1) {
					logger.info("读取提交数据异常  >>> "+e1.getMessage());
					e1.printStackTrace();
				}finally{
					try{
						if(bos != null){
							bos.close();
						}
						if(is != null){
							is.close();
						}}
					catch(Exception e){
						logger.info("关闭流异常 ");
						e.printStackTrace();
					}
				}
				String result = "0";//失败
				if(jsonData != null && !"".equals(jsonData)){
					
					try{
						jsonData = URLDecoder.decode(jsonData, "UTF-8");
						List<HDNote>  noteList = JSONObject.parseArray(jsonData,HDNote.class);
						if(noteList != null && noteList.size() > 0){
							MsgJober jober = new MsgJober(noteList);
							//提交线程池 2000
							Constant.AsyncMsgJober.submit(jober);
							
							result = "1";//成功
						}else{
							logger.info(jsonData +" 转换列表为空  >>> " + noteList);
						}
					}catch(Exception e){
						logger.info("转换格式异常  >>> " + jsonData);
						e.printStackTrace();
					}
				}
				try {
					response.setHeader("contentType", "text/html");
					response.getWriter().print(result);
					response.getWriter().flush();
					response.getWriter().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}else{
			
			logger.info("该ip 不允许 访问  >>> " + ip);
			
		}
	}
	
	
	private boolean checkIP(String ip){
		
		boolean flag = false;
		if(Constant.NOTE_SERVER != null && Constant.NOTE_SERVER.size() > 0){
			
			for(String chkIP:Constant.NOTE_SERVER){
				
				if(chkIP.equals(ip)){
					flag = true;
					break;
				}
			}
			
		}
		
		//return flag;
		return true;
	}

}
