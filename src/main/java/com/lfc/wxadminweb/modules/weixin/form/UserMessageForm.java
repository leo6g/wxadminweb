package com.lfc.wxadminweb.modules.weixin.form;

import java.util.Date;
import org.hibernate.validator.constraints.NotEmpty;
import com.lfc.wxadminweb.common.form.BaseForm;
public class UserMessageForm extends BaseForm{

	/*
	字段注释：消息内容
	列名称:MSG
	字段类型:VARCHAR2*/
	private String msg;
	/*
	字段注释：主键ID
	列名称:WX_USER_ID
	字段类型:VARCHAR2*/
	private String wxUserId;
	/*
	字段注释：主键ID
	列名称:MSG_ID
	字段类型:VARCHAR2*/
	private String msgId;
	/*
	字段注释：创建时间
	列名称:CREATE_TIME
	字段类型:DATE*/
	private Date createTime;
	
	
	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public void setWxUserId(String wxUserId){
		this.wxUserId = wxUserId;
	}

	public String getWxUserId(){
		return wxUserId;
	}

	public void setMsgId(String msgId){
		this.msgId = msgId;
	}

	public String getMsgId(){
		return msgId;
	}

	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

	public Date getCreateTime(){
		return createTime;
	}

	
}