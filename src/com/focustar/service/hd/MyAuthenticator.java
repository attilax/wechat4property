package com.focustar.service.hd;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

import org.apache.log4j.Logger;

import com.focustar.util.ConfigService;
import com.focustar.util.Constant;

public class MyAuthenticator extends Authenticator {
	private static Logger logger = Logger.getLogger(MyAuthenticator.class);
	public MyAuthenticator(){
	}
	
	protected PasswordAuthentication getPasswordAuthentication(){
		logger.info("hd 用户 ::"+Constant.HD_API_USER+"   密码：："+Constant.HD_API_PWD);
		return new PasswordAuthentication(Constant.HD_API_USER,Constant.HD_API_PWD.toCharArray());
	}

}
