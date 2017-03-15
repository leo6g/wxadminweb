package com.lfc.wxadminweb.modules.weixin.form;

import java.util.Date;

import com.lfc.wxadminweb.common.form.BaseForm;
public class WXNewsTemplatesForm extends BaseForm implements java.io.Serializable{

	/*
	字段注释：模板类型
	列名称:TYPE
	字段类型:VARCHAR2*/
	private String type;
	/*
	字段注释：模板名称
	列名称:NAME
	字段类型:VARCHAR2*/
	private String name;
	/*
	字段注释：主键ID
	列名称:NEWS_TEMP_ID
	字段类型:VARCHAR2*/
	private String newsTempId;
	/*
	字段注释：创建时间
	列名称:CREATE_TIME
	字段类型:DATE*/
	private Date createTime;
	/*
	字段注释：创建人
	列名称:CREATE_USER
	字段类型:VARCHAR2*/
	private String createUser;
	/*
	字段注释：审核状态
	（DRAFT：草稿，1-WAITING：一级通过，
	1-REJECTED：二级驳回，2-WAITING：二级通过，
	2-REJECTED：三级驳回，COMPLETED：三级通过）
	列名称:AUTH_STATE
	字段类型:VARCHAR2*/
	private String authState;
    
	//开始时间
	private String beginTime;
	//结束时间
    private String endTime;
	
	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setNewsTempId(String newsTempId){
		this.newsTempId = newsTempId;
	}

	public String getNewsTempId(){
		return newsTempId;
	}

	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

	public Date getCreateTime(){
		return createTime;
	}

	public void setCreateUser(String createUser){
		this.createUser = createUser;
	}

	public String getCreateUser(){
		return createUser;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getAuthState() {
		return authState;
	}

	public void setAuthState(String authState) {
		this.authState = authState;
	}

	
}