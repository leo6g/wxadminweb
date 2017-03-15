package com.lfc.wxadminweb.modules.biz.form;

import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

import com.lfc.wxadminweb.common.form.BaseForm;
public class AdvertisementForm extends BaseForm{

	/*
	字段注释：广告链接
	列名称:URL
	字段类型:VARCHAR2*/
	private String url;
	/*
	字段注释：广告标题
	列名称:NAME
	字段类型:VARCHAR2*/
	private String name;
	/*
	字段注释：删除标识
	列名称:DELETE_FLAG
	字段类型:NUMBER*/
	private Integer deleteFlag;
	/*
	字段注释：ADVER_ID
	列名称:ADVER_ID
	字段类型:VARCHAR2*/
	private String adverId;
	/*
	字段注释：投放结束日期
	列名称:END_DATE
	字段类型:DATE*/
	private Date endDate;
	/*
	字段注释：投放开始日期
	列名称:START_DATE
	字段类型:DATE*/
	private Date startDate;
	/*
	字段注释：广告厂商电话
	列名称:FIRM_PHONE
	字段类型:VARCHAR2*/
	private String firmPhone;
	/*
	字段注释：广告厂商名称
	列名称:FIRM_NAME
	字段类型:VARCHAR2*/
	private String firmName;
	/*
	字段注释：创建人
	列名称:CREATE_USER
	字段类型:VARCHAR2*/
	private String createUser;
	/*
	字段注释：创建时间
	列名称:CREATE_TIME
	字段类型:DATE*/
	private Date createTime;
	//广告图片
	private String imgPath;
	
	
	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}



	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public void setAdverId(String adverId){
		this.adverId = adverId;
	}

	public String getAdverId(){
		return adverId;
	}

	public void setEndDate(Date endDate){
		this.endDate = endDate;
	}

	public Date getEndDate(){
		return endDate;
	}

	public void setStartDate(Date startDate){
		this.startDate = startDate;
	}

	public Date getStartDate(){
		return startDate;
	}

	public void setFirmPhone(String firmPhone){
		this.firmPhone = firmPhone;
	}

	public String getFirmPhone(){
		return firmPhone;
	}

	public void setFirmName(String firmName){
		this.firmName = firmName;
	}

	public String getFirmName(){
		return firmName;
	}

	public void setCreateUser(String createUser){
		this.createUser = createUser;
	}

	public String getCreateUser(){
		return createUser;
	}

	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

	public Date getCreateTime(){
		return createTime;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	
}