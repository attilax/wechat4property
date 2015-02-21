package com.focustar.entity.hd;

public class HDNote {
	
	private String 		memberId;
	private String 		cardNo;
	private String 		shopName;
	private int         operType;//操作类型 0消费  1退货
	private double	   	addCredit;//增加积分
	private double    	useCredit;//使用积分
	private double    	otherCredit;//其它积分
	private double    	creditTotal;//总积
	private String      remark;
	
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public double getAddCredit() {
		return addCredit;
	}
	public void setAddCredit(double addCredit) {
		this.addCredit = addCredit;
	}
	public double getUseCredit() {
		return useCredit;
	}
	public void setUseCredit(double useCredit) {
		this.useCredit = useCredit;
	}
	public double getReduceCredit() {
		return otherCredit;
	}
	public void setReduceCredit(double reduceCredit) {
		this.otherCredit = reduceCredit;
	}
	
	public int getOperType() {
		return operType;
	}
	public void setOperType(int operType) {
		this.operType = operType;
	}
	public double getOtherCredit() {
		return otherCredit;
	}
	public void setOtherCredit(double otherCredit) {
		this.otherCredit = otherCredit;
	}
	public double getCreditTotal() {
		return creditTotal;
	}
	public void setCreditTotal(double creditTotal) {
		this.creditTotal = creditTotal;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
	

}
