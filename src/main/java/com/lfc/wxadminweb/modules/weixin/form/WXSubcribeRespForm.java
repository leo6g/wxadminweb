package com.lfc.wxadminweb.modules.weixin.form;

import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

import com.lfc.wxadminweb.common.form.BaseForm;
public class WXSubcribeRespForm extends BaseForm{

	/*
	字段注释：主键ID
	列名称:ID
	字段类型:VARCHAR2*/
	private String id;
	/*
	字段注释：消息类型(text:文本消息|news:图文消息|voice:语音|video:视频)
	列名称:MSG_TYPE
	字段类型:VARCHAR2*/
	@NotBlank(message="消息类型不能为空")
	private String msgType;
	/*
	字段注释：消息类型(text:文本消息内容,当回复类型是text，该字段有效)
	列名称:TXT_MSG
	字段类型:VARCHAR2*/
	private String txtMsg;
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
	字段注释：媒体ID
	列名称:MEDIA_ID
	字段类型:VARCHAR2*/
	private String mediaId;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTxtMsg() {
		return txtMsg;
	}

	public void setTxtMsg(String txtMsg) {
		this.txtMsg = txtMsg;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public void setMsgType(String msgType){
		this.msgType = msgType;
	}

	public String getMsgType(){
		return msgType;
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


	
}