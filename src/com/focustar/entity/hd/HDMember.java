package com.focustar.entity.hd;

import java.util.Date;
import java.util.List;

public class HDMember extends HDError{
	
	
	public final static String MEMBER_QUERY_BYCARD = "/cardinfo/query_cardinfo_member_by_cardnum?";
	
	//根据手机号查询会员信息
	public final static String MEMBER_QUERY_BYMOBILE = "/member/query_by_mobilenum?";
	//修改会员信息
	public final static String MEMBER_MODIFY_URL = "/member/modify?";
	private String code = "";
	private String name = "";
	private String email = "";
	private Date birthday = null;
	private String nameSpell = "";
	private Date memorialDay = null;
	private String paperType = "";
	private String paperCode = "";
	private String cellPhone = "";
	private String occupation = "";
	private String specialty = "";
	private String nationality = "";
	private Presenter presenter = null;
	private MemberGrade memberGrade = null;
	private String fixedPhone = "";
	private String postCode = "";
	private String address = "";
	private String remark = "";
	private String sex = "";
	private String wedding = "";
	private Org org = null;
	private String originCode = "";
	private String appName = "";
	private String login = "";
	private int activityHeat;
	private MemberSource memberSource;
	private Date registerDate;
	private List<SMemberExtAttribute> extAttributes;
	private String WX;
	private String QQ;
	
	//extends
	private transient Double score; //会员积分
	
	private transient String cardNo;//卡号
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCellPhone() {
		return cellPhone;
	}
	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getNameSpell() {
		return nameSpell;
	}
	public void setNameSpell(String nameSpell) {
		this.nameSpell = nameSpell;
	}

	public Date getMemorialDay() {
		return memorialDay;
	}
	public void setMemorialDay(Date memorialDay) {
		this.memorialDay = memorialDay;
	}
	public String getPaperType() {
		return paperType;
	}
	public void setPaperType(String paperType) {
		this.paperType = paperType;
	}
	public String getPaperCode() {
		return paperCode;
	}
	public void setPaperCode(String paperCode) {
		this.paperCode = paperCode;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public String getSpecialty() {
		return specialty;
	}
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	
	public Presenter getPresenter() {
		return presenter;
	}
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}
	
	
	
	public MemberGrade getMemberGrade() {
		return memberGrade;
	}
	public void setMemberGrade(MemberGrade memberGrade) {
		this.memberGrade = memberGrade;
	}
	public String getFixedPhone() {
		return fixedPhone;
	}
	public void setFixedPhone(String fixedPhone) {
		this.fixedPhone = fixedPhone;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getWedding() {
		return wedding;
	}
	public void setWedding(String wedding) {
		this.wedding = wedding;
	}

	public String getOriginCode() {
		return originCode;
	}
	public void setOriginCode(String originCode) {
		this.originCode = originCode;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	
	public Date getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	
	public Org getOrg() {
		return org;
	}
	public void setOrg(Org org) {
		this.org = org;
	}
	public String getWX() {
		return WX;
	}
	public void setWX(String wX) {
		WX = wX;
	}
	public String getQQ() {
		return QQ;
	}
	public void setQQ(String qQ) {
		QQ = qQ;
	}
	public int getActivityHeat() {
		return activityHeat;
	}
	public void setActivityHeat(int activityHeat) {
		this.activityHeat = activityHeat;
	}
	public MemberSource getMemberSource() {
		return memberSource;
	}
	public void setMemberSource(MemberSource memberSource) {
		this.memberSource = memberSource;
	}
	public List<SMemberExtAttribute> getExtAttributes() {
		return extAttributes;
	}
	public void setExtAttributes(List<SMemberExtAttribute> extAttributes) {
		this.extAttributes = extAttributes;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	
	
	
}
