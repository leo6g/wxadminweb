package com.lfc.wxadminweb.modules.weixin.form;

import java.util.Date;
import org.hibernate.validator.constraints.NotEmpty;
import com.lfc.wxadminweb.common.form.BaseForm;
public class WXTextTemplatesForm extends BaseForm{

	/*
	字段注释：模板内容
	列名称:CONTENT
	字段类型:VARCHAR2*/
	private String content;
	/*
	字段注释：模版名称
	列名称:NAME
	字段类型:VARCHAR2*/
	private String name;
	/*
	字段注释：主键ID
	列名称:TEXT_TEMP_ID
	字段类型:VARCHAR2*/
	private String textTempId;
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
	//开始时间
	private String beginTime;
	//结束时间
    private String endTime;
	
	
	public void setContent(String content){
		this.content = content;
	}

	public String getContent(){
		return content;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setTextTempId(String textTempId){
		this.textTempId = textTempId;
	}

	public String getTextTempId(){
		return textTempId;
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

	
}