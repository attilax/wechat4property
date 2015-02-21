package com.focustar.thread;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.focustar.dao.impl.TaskImpl;
import com.focustar.entity.TMbMessage;
import com.focustar.entity.TMbTask;
import com.focustar.util.Constant;
import com.focustar.util.MyHttpUtils;

public class SaveMsgJober implements Runnable{
	
	
	private final static String  WX_DL_URL = "http://file.api.weixin.qq.com/cgi-bin/media/get?";
	
	private static Logger logger = Logger.getLogger(SaveMsgJober.class);
	
	private int msgType;
	private Map<String, String> paramsMap;
	
	private String openid;
	private long  publishTime;
	private String msgContent;
	private String mediaId;
	private InputStream is = null;
	private FileOutputStream fos = null;
	private ByteArrayOutputStream baos = null;
	
	public SaveMsgJober(int msgType,Map<String, String> paramsMap){
		
		this.msgType = msgType;
		this.paramsMap = paramsMap;
		
	}

	public void run() {
		try{
			logger.info("开始保存消息 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			
				this.openid = paramsMap.get("FromUserName");
				this.publishTime = Long.valueOf(paramsMap.get("CreateTime"));
				TMbTask task = new TMbTask();
				switch(this.msgType){
				case TMbTask.MSGTYPE_IMG:
				case TMbTask.MSGTYPE_VIDEO:
				case TMbTask.MSGTYPE_VOICE:
				
					this.mediaId = paramsMap.get("MediaId");
					
					StringBuilder  builder = new StringBuilder();
					
					builder.append(WX_DL_URL);
					builder.append("access_token=").append(Constant.token.getToken())
						   .append("&media_id=").append(this.mediaId);
					//下载图片
					Map<Object,Object> resultMap = MyHttpUtils.httpStream(builder.toString(), "GET",null);
					
					if(resultMap != null){
						
						String contentType = (String) resultMap.get("contentType");
						is = (InputStream) resultMap.get("inputStream");
						if("image/jpeg".equalsIgnoreCase(contentType) 
								|| "audio/amr".equalsIgnoreCase(contentType)
								|| "video/mpeg4".equalsIgnoreCase(contentType)
								|| "audio/mpeg".equalsIgnoreCase(contentType)
								|| "audio/mp3".equalsIgnoreCase(contentType)
								|| "video/mp4".equalsIgnoreCase(contentType)){
								//图片网络路径
								StringBuilder dbImagePath = new StringBuilder();
						
								if(is != null){
							
									//文件路径
									StringBuilder dirPath = new StringBuilder();
									
									dirPath.append(Constant.MEDIA_FILE_PATH);
									dirPath.append(this.openid).append(File.separator);
									
									
									
									File dirCheck = new File(dirPath.toString());
									
									dirCheck.mkdirs();
									
									//保存到数据库的网络路径
									
									
									if(msgType == TMbMessage.MSGTYPE_IMG){
										dbImagePath.append(this.openid).append("/").append(this.mediaId).append(".jpg");
										dirPath.append(this.mediaId).append(".jpg");
									}else if(msgType == TMbMessage.MSGTYPE_VOICE){
										dbImagePath.append(this.openid).append("/").append(this.mediaId).append(".amr");
										dirPath.append(this.mediaId).append(".amr");
									}else if(msgType == TMbMessage.MSGTYPE_VEDIO){
										dbImagePath.append(this.openid).append("/").append(this.mediaId).append(".mp4");
										dirPath.append(this.mediaId).append(".mp4");
									}
									
									//创建文件
									File mediaFile = new File(dirPath.toString());
									mediaFile.createNewFile();
									
									logger.info("媒体文件  >>> " + dirPath.toString());
									
									//写入文件
									fos = new FileOutputStream(mediaFile);
									
									 byte[] bs = new byte[1024 * 2];
									 int len = -1;
									 	while((len = is.read(bs)) != -1){
											fos.write(bs, 0, len);
										}
									 fos.flush();
								}
								task.setMediaUrl(dbImagePath.toString());
						}else{
							baos = new ByteArrayOutputStream();
							int b = -1;
							while((b = is.read()) != -1){
								baos.write(b);
							}
							String jsonStr = new String(baos.toByteArray(),"UTF-8");
							//JSONObject jsonObj = JSONObject.fromObject(jsonStr);
							logger.info("请求地址  >>> " + builder.toString());
							logger.info("请求结果  >>> " + jsonStr);
							
						}
					}
					
					break;
				case TMbTask.MSGTYPE_TEXT:
				case TMbTask.MSGTYPE_LINK:
				case TMbTask.MSGTYPE_LOC:
					this.msgContent = paramsMap.get("Content");
					task.setMsgContent(this.msgContent);
					break;
				}
				
				task.setOpenid(openid);
				task.setPublishTime(new Date(this.publishTime * 1000));
				task.setState(TMbTask.STATE_UNREAD);
				task.setMsgType(this.msgType);
				
				TaskImpl taskDao = new TaskImpl();
				if(taskDao.addTask(task)){
					logger.info("保存消息成功！");
					if(task.getMsgType() != null && task.getMsgType().intValue() == TMbTask.MSGTYPE_TEXT){
						logger.info("文字消息，检查是否关键字["+this.msgContent+"]");
						//启动线程检查是否关键字
						if(this.msgContent != null && !"".equals(this.msgContent)){
							KeywordJober kwJober = new KeywordJober(this.openid,this.msgContent);
							Constant.AsyncJober.execute(kwJober);
						}
					}
				}else{
					logger.info("保存消息失败！");
				}
				
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
				try {
					
					if(baos != null){
						baos.close();
					}
					if(fos != null){
						fos.close();
					}
					if(is != null){
						is.close();
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			
		}
		
		logger.info("结束保存消息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		
	}

	
	
}
