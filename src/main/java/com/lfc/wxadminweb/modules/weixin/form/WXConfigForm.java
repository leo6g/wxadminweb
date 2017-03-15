package com.lfc.wxadminweb.modules.weixin.form;

import java.util.Date;
import org.hibernate.validator.constraints.NotEmpty;
import com.lfc.wxadminweb.common.form.BaseForm;
public class WXConfigForm extends BaseForm{

	/*
	字段注释：token信息
	列名称:TOKEN
	字段类型:VARCHAR2*/
	private String token;
	/*
	字段注释：账号描述
	列名称:DESCRIPTION
	字段类型:VARCHAR2*/
	private String description;
	/*
	字段注释：公众号类型
	列名称:TYPE
	字段类型:VARCHAR2*/
	private String type;
	/*
	字段注释：主键ID
	列名称:CFG_ID
	字段类型:VARCHAR2*/
	private String cfgId;
	/*
	字段注释：公众号APPID
	列名称:APP_ID
	字段类型:VARCHAR2*/
	private String appId;
	/*
	字段注释：访问token
	列名称:ACCESS_TOKE
	字段类型:VARCHAR2*/
	private String accessToke;
	/*
	字段注释：公众号名称
	列名称:ACCOUNT
	字段类型:VARCHAR2*/
	private String account;
	/*
	字段注释：原始ID
	列名称:ACCOUNT_ID
	字段类型:VARCHAR2*/
	private String accountId;
	/*
	字段注释：邮箱地址
	列名称:EMAIL
	字段类型:VARCHAR2*/
	private String email;
	/*
	字段注释：公众号密码
	列名称:APP_SECRET
	字段类型:VARCHAR2*/
	private String appSecret;
	/*
	字段注释：null
	列名称:CREATE_USER
	字段类型:VARCHAR2*/
	private String createUser;
	/*
	字段注释：null
	列名称:CREATE_TIME
	字段类型:DATE*/
	private Date createTime;
	
	
	public void setToken(String token){
		this.token = token;
	}

	public String getToken(){
		return token;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setCfgId(String cfgId){
		this.cfgId = cfgId;
	}

	public String getCfgId(){
		return cfgId;
	}

	public void setAppId(String appId){
		this.appId = appId;
	}

	public String getAppId(){
		return appId;
	}

	public void setAccessToke(String accessToke){
		this.accessToke = accessToke;
	}

	public String getAccessToke(){
		return accessToke;
	}

	public void setAccount(String account){
		this.account = account;
	}

	public String getAccount(){
		return account;
	}

	public void setAccountId(String accountId){
		this.accountId = accountId;
	}

	public String getAccountId(){
		return accountId;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setAppSecret(String appSecret){
		this.appSecret = appSecret;
	}

	public String getAppSecret(){
		return appSecret;
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

	
}