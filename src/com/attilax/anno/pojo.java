package com.attilax.anno;

import org.apache.tapestry5.beaneditor.DataType;
import org.apache.tapestry5.beaneditor.NonVisual;
import org.apache.tapestry5.corelib.ClientValidation;

import com.attilax.anno.DataTypeConstants;

@Property(label="用户表单", reorder = "honorific,firstName,lastName,street1,street2,city,state,zip,email,phone")
public class pojo {
	// @width
	@DataType(DataTypeConstants.TEXT)
	@Validate(value = "required",minlength=1,maxlength=15,msg="名称不能使用")
	@Property(label = "名称")
	String name;
	@DataType(DataTypeConstants.NUMBER)
	@Validate (max=99999999,min=1)@NonVisual @NonVisual_list
	int id;
	@Validate(type=DataTypeConstants.email,value = "required", regexp = "^\\d{5}(-\\d{4})?$", regexp_message = "Zip Codes are five or nine digits")
	@Property(label="邮件")
	String email;
	@DataType(DataTypeConstants.PASSWORD)
	 @Property(label = "密码")
	String pwd;

	public String getName() {
		return name;
	}

	public void setName(String name) {
	//	org.apache.tapestry5.corelib.ClientValidation.BLUR/none/submit
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
