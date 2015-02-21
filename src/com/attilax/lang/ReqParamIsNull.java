package com.attilax.lang;

public class ReqParamIsNull extends RuntimeException implements Idata {

	public String getCauseExParam() {
		return causeExParam;
	}

	public void setCauseExParam(String causeExParam) {
		this.causeExParam = causeExParam;
	}

	public String causeExParam;

	public ReqParamIsNull(String msgPre, String data) {
		super(msgPre+":"+data);
		this.causeExParam=data;
		
	}

}
