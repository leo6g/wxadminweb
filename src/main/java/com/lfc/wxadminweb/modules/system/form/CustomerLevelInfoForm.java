package com.lfc.wxadminweb.modules.system.form;

import org.hibernate.validator.constraints.NotBlank;

import com.lfc.wxadminweb.common.form.BaseForm;
public class CustomerLevelInfoForm extends BaseForm{

	/*
	字段注释：卡种级别(00为普通级，01为金卡级，02为白金级，03为钻石级。)
	列名称:LEVEL_NAME
	字段类型:VARCHAR2*/
	@NotBlank(message="卡种级别不能为空")
	private String levelName;
	/*
	字段注释：电话
	列名称:PHONE_NUMBER
	字段类型:VARCHAR2*/
	@NotBlank(message="电话不能为空")
	private String phoneNumber;
	/*
	字段注释：null
	列名称:ID
	字段类型:VARCHAR2*/
	private String id;
	/*
	字段注释：客户级别
	列名称:CUSTOMER_LEVEL
	字段类型:VARCHAR2*/
	@NotBlank(message="客户级别不能为空")
	private String customerLevel;
	/*
	字段注释：客户名称
	列名称:CUSTOMER_NAME
	字段类型:VARCHAR2*/
	@NotBlank(message="客户姓名不能为空")
	private String customerName;
	
	/*
	字段注释：持卡类型
	列名称:CARD_TYPE
	字段类型:VARCHAR2*/
	private String cardType;
	
	private String managerName;
	private String managerPhone;
	
	
	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public void setLevelName(String levelName){
		this.levelName = levelName;
	}

	public String getLevelName(){
		return levelName;
	}

	public void setPhoneNumber(String phoneNumber){
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneNumber(){
		return phoneNumber;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setCustomerLevel(String customerLevel){
		this.customerLevel = customerLevel;
	}

	public String getCustomerLevel(){
		return customerLevel;
	}

	public void setCustomerName(String customerName){
		this.customerName = customerName.trim();
	}

	public String getCustomerName(){
		return customerName;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getManagerPhone() {
		return managerPhone;
	}

	public void setManagerPhone(String managerPhone) {
		this.managerPhone = managerPhone;
	}

	
}