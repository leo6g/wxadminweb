package com.lfc.wxadminweb.modules.biz.form;

import java.util.Date;
import org.hibernate.validator.constraints.NotEmpty;
import com.lfc.wxadminweb.common.form.BaseForm;
public class SmsLogForm extends BaseForm{

	/*
	字段注释：短信内容
	列名称:SMS_CONTENT
	字段类型:VARCHAR2*/
	private String smsContent;
	/*
	字段注释：手机号码
	列名称:PHONE_NUMBER
	字段类型:VARCHAR2*/
	private String phoneNumber;
	/*
	字段注释：id
	列名称:ID
	字段类型:VARCHAR2*/
	private String id;
	/*
	字段注释：发送时间
	列名称:SEND_TIME
	字段类型:DATE*/
	private Date sendTime;
	/*
	字段注释：短信条数
	列名称:SMS_CNT
	字段类型:NUMBER*/
	private Integer smsCnt;
	/*
	字段注释：短信条数
	列名称:SMS_CNT
	字段类型:NUMBER*/
	private Date beginTime;
	/*
	字段注释：短信条数
	列名称:SMS_CNT
	字段类型:NUMBER*/
	private Date endTime;
	
	public void setSmsContent(String smsContent){
		this.smsContent = smsContent;
	}

	public String getSmsContent(){
		return smsContent;
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

	public void setSendTime(Date sendTime){
		this.sendTime = sendTime;
	}

	public Date getSendTime(){
		return sendTime;
	}

	public void setSmsCnt(Integer smsCnt){
		this.smsCnt = smsCnt;
	}

	public Integer getSmsCnt(){
		return smsCnt;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
}