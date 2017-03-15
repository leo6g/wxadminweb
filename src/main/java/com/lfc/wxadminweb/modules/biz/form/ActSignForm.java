package com.lfc.wxadminweb.modules.biz.form;

import java.util.Date;
import org.hibernate.validator.constraints.NotEmpty;
import com.lfc.wxadminweb.common.form.BaseForm;
public class ActSignForm extends BaseForm{

	/*
	字段注释：姓名
	列名称:NAME
	字段类型:VARCHAR2*/
	private String name;
	/*
	字段注释：手机号码
	列名称:PHONE_NUMBER
	字段类型:VARCHAR2*/
	private String phoneNumber;
	/*
	字段注释：扩展数字字段1
	列名称:EXT_NUM1
	字段类型:NUMBER*/
	private Integer extNum1;
	/*
	字段注释：ID
	列名称:ID
	字段类型:VARCHAR2*/
	private String id;
	/*
	字段注释：扩展字符字段1
	列名称:EXT_STR1
	字段类型:VARCHAR2*/
	private String extStr1;
	/*
	字段注释：报名隶属地市
	列名称:BELONG_CITY
	字段类型:VARCHAR2*/
	private String belongCity;
	/*
	字段注释：创建时间
	列名称:CREATE_TIME
	字段类型:DATE*/
	private Date createTime;
	/*
	字段注释：报名人OPEN_ID
	列名称:OPEN_ID
	字段类型:VARCHAR2*/
	private String openId;
	/*
	字段注释：扩展字符字段3
	列名称:EXT_STR3
	字段类型:VARCHAR2*/
	private String extStr3;
	/*
	字段注释：扩展字符字段2
	列名称:EXT_STR2
	字段类型:VARCHAR2*/
	private String extStr2;
	/*
	字段注释：扩展数字字段2
	列名称:EXT_NUM2
	字段类型:NUMBER*/
	private Integer extNum2;
	/*
	字段注释：扩展数字字段3
	列名称:EXT_NUM3
	字段类型:NUMBER*/
	private Integer extNum3;
	/*
	字段注释：活动类型ACT_TYPE
	列名称:ACT_TYPE
	字段类型:VARCHAR2*/
	private String actType;
	
	
	
	public String getActType() {
		return actType;
	}

	public void setActType(String actType) {
		this.actType = actType;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setPhoneNumber(String phoneNumber){
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneNumber(){
		return phoneNumber;
	}

	public void setExtNum1(Integer extNum1){
		this.extNum1 = extNum1;
	}

	public Integer getExtNum1(){
		return extNum1;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setExtStr1(String extStr1){
		this.extStr1 = extStr1;
	}

	public String getExtStr1(){
		return extStr1;
	}

	public void setBelongCity(String belongCity){
		this.belongCity = belongCity;
	}

	public String getBelongCity(){
		return belongCity;
	}

	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

	public Date getCreateTime(){
		return createTime;
	}

	public void setOpenId(String openId){
		this.openId = openId;
	}

	public String getOpenId(){
		return openId;
	}

	public void setExtStr3(String extStr3){
		this.extStr3 = extStr3;
	}

	public String getExtStr3(){
		return extStr3;
	}

	public void setExtStr2(String extStr2){
		this.extStr2 = extStr2;
	}

	public String getExtStr2(){
		return extStr2;
	}

	public void setExtNum2(Integer extNum2){
		this.extNum2 = extNum2;
	}

	public Integer getExtNum2(){
		return extNum2;
	}

	public void setExtNum3(Integer extNum3){
		this.extNum3 = extNum3;
	}

	public Integer getExtNum3(){
		return extNum3;
	}

	
}