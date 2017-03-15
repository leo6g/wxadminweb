package com.lfc.wxadminweb.modules.weixin.form;

import java.util.Date;
import org.hibernate.validator.constraints.NotEmpty;
import com.lfc.wxadminweb.common.form.BaseForm;
public class WXAutoResponseForm extends BaseForm{

	/*
	字段注释：信息类型
	列名称:MSG_TYPE
	字段类型:VARCHAR2*/
	private String msgType;
	/*
	字段注释：关键字
	列名称:KEY_WORD
	字段类型:VARCHAR2*/
	private String keyWord;
	/*
	字段注释：主键ID
	列名称:RESPONSE_ID
	字段类型:VARCHAR2*/
	private String responseId;
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
	字段注释：文本模板ID
	列名称:TEXT_TEMP_ID
	字段类型:VARCHAR2*/
	private String textTempId;
	//开始时间
	private String beginTime;
	//结束时间
    private String endTime;
	
	public void setMsgType(String msgType){
		this.msgType = msgType;
	}

	public String getMsgType(){
		return msgType;
	}

	public void setKeyWord(String keyWord){
		this.keyWord = keyWord;
	}

	public String getKeyWord(){
		return keyWord;
	}

	public void setResponseId(String responseId){
		this.responseId = responseId;
	}

	public String getResponseId(){
		return responseId;
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

	public void setTextTempId(String textTempId){
		this.textTempId = textTempId;
	}

	public String getTextTempId(){
		return textTempId;
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