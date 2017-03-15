package com.lfc.wxadminweb.modules.weixin.form;

import java.util.Date;
import org.hibernate.validator.constraints.NotEmpty;
import com.lfc.wxadminweb.common.form.BaseForm;
public class AccessTokenForm extends BaseForm{

	/*
	字段注释：获取日期
	列名称:ADD_TIME
	字段类型:DATE*/
	private Date addTime;
	/*
	字段注释：TOKEN值
	列名称:ACCESS_TOKEN
	字段类型:VARCHAR2*/
	private String accessToken;
	/*
	字段注释：主键ID
	列名称:ID
	字段类型:VARCHAR2*/
	private String id;
	/*
	字段注释：过期
	列名称:EXPIRES_IB
	字段类型:NUMBER*/
	private Integer expiresIb;
	
	
	public void setAddTime(Date addTime){
		this.addTime = addTime;
	}

	public Date getAddTime(){
		return addTime;
	}

	public void setAccessToken(String accessToken){
		this.accessToken = accessToken;
	}

	public String getAccessToken(){
		return accessToken;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setExpiresIb(Integer expiresIb){
		this.expiresIb = expiresIb;
	}

	public Integer getExpiresIb(){
		return expiresIb;
	}

	
}