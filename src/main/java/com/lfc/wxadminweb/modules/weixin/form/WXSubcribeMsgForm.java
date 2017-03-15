package com.lfc.wxadminweb.modules.weixin.form;

import java.util.Date;
import org.hibernate.validator.constraints.NotEmpty;
import com.lfc.wxadminweb.common.form.BaseForm;
public class WXSubcribeMsgForm extends BaseForm{

	/*
	字段注释：模板ID
	列名称:TEMPLATE_ID
	字段类型:VARCHAR2*/
	private String templateId;
	/*
	字段注释：消息类型(text:文本消息|news:图文消息)
	列名称:MSG_TYPE
	字段类型:VARCHAR2*/
	private String msgType;
	/*
	字段注释：主键ID
	列名称:SUB_ID
	字段类型:VARCHAR2*/
	private String subId;
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
	
	public void setTemplateId(String templateId){
		this.templateId = templateId;
	}

	public String getTemplateId(){
		return templateId;
	}

	public void setMsgType(String msgType){
		this.msgType = msgType;
	}

	public String getMsgType(){
		return msgType;
	}

	public void setSubId(String subId){
		this.subId = subId;
	}

	public String getSubId(){
		return subId;
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