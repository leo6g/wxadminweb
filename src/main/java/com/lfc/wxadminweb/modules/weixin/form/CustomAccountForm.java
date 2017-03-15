package com.lfc.wxadminweb.modules.weixin.form;

import java.util.Date;
import org.hibernate.validator.constraints.NotEmpty;
import com.lfc.wxadminweb.common.form.BaseForm;
public class CustomAccountForm extends BaseForm{

	/*
	字段注释：客服昵称
	列名称:NICK_NAME
	字段类型:VARCHAR2*/
	private String nickName;
	/*
	字段注释：客服账号
	列名称:ACCOUNT
	字段类型:VARCHAR2*/
	private String account;
	/*
	字段注释：ID
	列名称:ID
	字段类型:VARCHAR2*/
	private String id;
	/*
	字段注释：客服头像
	列名称:HEAD_IMG
	字段类型:VARCHAR2*/
	private String headImg;
	/*
	字段注释：密码
	列名称:PWD
	字段类型:VARCHAR2*/
	private String pwd;
	
	
	public void setNickName(String nickName){
		this.nickName = nickName;
	}

	public String getNickName(){
		return nickName;
	}

	public void setAccount(String account){
		this.account = account;
	}

	public String getAccount(){
		return account;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setHeadImg(String headImg){
		this.headImg = headImg;
	}

	public String getHeadImg(){
		return headImg;
	}

	public void setPwd(String pwd){
		this.pwd = pwd;
	}

	public String getPwd(){
		return pwd;
	}

	
}