package com.focustar.entity.hd;

public class HDCredit extends HDError {
	
	
	public final static int   CREDIT_TYPE_QUERY = 1;
	public final static int   CREDIT_TYPE_ADD = 2;
	
	public final static String CREDIT_QUERY_URL = "/scoreaccount/query?";
	public final static String  CREDIT_ADD_URL = "/scoreaccount/inc?";
	
	public final static String  ACCOUNT_TYPE_CARD = "0";//卡号
	public final static String  ACCOUNT_TYPE_MEMBERCODE = "2";//会员号

	private String xid;	//业务流水
	private String orgcode = "3";	//相当于积分来源，默认为3,表示 来自jvsiny
	private String account_accesscode;//帐户识别码
	
	/***
	 * 没有启用会员号查询积分，所以只能用会员卡号查询
	 * @category 默认为0 会员卡号  1会员号
	 */
	private String account_accesstype = "0";
	
	//根据不同的类型返回不同的请求url
	private transient int creditType = 1;
	
	public HDCredit(int creditType){
		this.creditType = creditType;
	}
	
	public String getXid() {
		return xid;
	}
	public void setXid(String xid) {
		this.xid = xid;
	}
	public String getOrgcode() {
		return orgcode;
	}
	public void setOrgcode(String orgcode) {
		this.orgcode = orgcode;
	}
	public String getAccount_accesscode() {
		return account_accesscode;
	}
	public void setAccount_accesscode(String account_accesscode) {
		this.account_accesscode = account_accesscode;
	}
	public String getAccount_accesstype() {
		return account_accesstype;
	}
	public void setAccount_accesstype(String account_accesstype) {
		this.account_accesstype = account_accesstype;
	}
	
	
	public String toString(){
		
		StringBuilder queryStr = new StringBuilder();
		switch(this.creditType){
		case CREDIT_TYPE_QUERY:
			requestMethod = "GET";
			queryStr.append(CREDIT_QUERY_URL)
					.append("account_accesscode=").append(this.account_accesscode)
					.append("&account_accesstype=").append(this.account_accesstype);
			break;
		case CREDIT_TYPE_ADD:
			requestMethod = "POST";
			queryStr.append(CREDIT_ADD_URL)
					.append("xid=").append(this.xid)
					.append("&orgcode=").append(this.orgcode)
					.append("&account_accesscode=").append(this.account_accesscode)
					.append("&account_accesstype=").append(this.account_accesstype);
			break;
		}
		
		return queryStr.toString();
	}
	
}
