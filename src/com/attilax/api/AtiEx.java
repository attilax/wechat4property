package com.attilax.api;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.attilax.lang.Idata;
import com.attilax.lang.core;
//import com.attilax.user.UserAlreadyExistEx;

//import com.attilax.json.JsonUtil4jackjson;

public class AtiEx extends  Exception   {
	 

	public String getCauseExParam() {
		return causeExParam;
	}

	public void setCauseExParam(String causeExParam) {
		this.causeExParam = causeExParam;
	}

	public String causeExParam;
	public AtiEx(Exception e) {
		super(e.getMessage(),e);
	    try {
			this.setTypex(e.getClass().getName());
		} catch (Exception e2) {
			// TODO: handle exception
		}
	    try {///set data
			Idata id=(Idata) e;
			this.causeExParam=id.getCauseExParam();
		} catch (Exception e2) {
			// TODO: handle exception
		}
	}

//	public AtiEx(String message) {
//		super(message);
//	}

	public static void main(String[] args) {
	//	FileNotFoundException
//		try {
//			throw new UserAlreadyExistEx("exmsg");
//		} catch (Exception e) {
//			AtiEx ae=new AtiEx(e);
//			ae.setStackTrace(e.getStackTrace());
//			System.out.println(e.getClass().getName());
//			//com.attilax.user.UserAlreadyExistEx
//			System.out.println(core.toJsonStrO88(ae));
//		}
	}

	public String typex;
	public String getTypex() {
		return typex;
	}

	public void setTypex(String typex) {
		this.typex = typex;
	}
	
}