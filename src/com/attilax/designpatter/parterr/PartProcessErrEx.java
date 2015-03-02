package com.attilax.designpatter.parterr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.attilax.json.JsonUtil4jackjson;

public class PartProcessErrEx extends DwrSupportEx   {

	public String typex;
	public String getTypex() {
		return typex;
	}

	public void setTypex(String typex) {
		this.typex = typex;
	}

	public String message;
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public PartProcessErrEx(List li) {
	 
		 super(JsonUtil4jackjson.buildNormalBinder().toJson(li) );
//	this.message=	JsonUtil4jackjson.buildNormalBinder().toJson(li);
	
	}

	public PartProcessErrEx(List li, boolean is4jsUse) {
		if(is4jsUse){
			StackTraceElement[] ste_a=this.getStackTrace();
			
		StackTraceElement ste=new StackTraceElement("PartProcessErrEx", "PartProcessErrEx", "", 0);
		List lix=new ArrayList();
		lix.add(ste);
		for (StackTraceElement ste_tmp : ste_a) {
			lix.add(ste_tmp);
		}
		StackTraceElement[] newStr = (StackTraceElement[]) lix.toArray(new StackTraceElement[lix.size()]);
		//lix.addAll(ste_a);
		 this.setStackTrace(newStr);
		
//		 ErrorItem ei=new ErrorItem();
//			ei.id="PartProcessErrEx";
//			ei.msg="PartProcessErrEx";
//			li.add(0, ei);
		}
			this.message=	JsonUtil4jackjson.buildNormalBinder().toJson(li);
	}

	public PartProcessErrEx() {
		System.out.println("");
	}

	public PartProcessErrEx(List li, String typex) {
		this.typex=typex;
		this.message=	JsonUtil4jackjson.buildNormalBinder().toJson(li);
	}
	public PartProcessErrEx(String message2) {
		this.message=message2;
	}
	public PartProcessErrEx(String message2, Throwable e) {
		super(message2,e);
	}

	public PartProcessErrEx(String message2, String typex) {
		this.message=message2;	this.typex=typex;
	}
 

}