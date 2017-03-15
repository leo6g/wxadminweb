package com.lfc.wxadminweb.modules.biz.form.paymentInfo;

import java.util.Date;
import org.hibernate.validator.constraints.NotEmpty;
import com.lfc.wxadminweb.common.form.BaseForm;
public class PaymentInfoForm extends BaseForm{

	/*
	字段注释：缴费项目编码
	列名称:CODE
	字段类型:VARCHAR2*/
	private String code;
	/*
	字段注释：缴费项目名称
	列名称:NAME
	字段类型:VARCHAR2*/
	private String name;
	/*
	字段注释：null
	列名称:ID
	字段类型:VARCHAR2*/
	private String id;
	/*
	字段注释：链接地址
	列名称:LINK_URL
	字段类型:VARCHAR2*/
	private String linkUrl;
	/*
	字段注释：图片路径
	列名称:IMG_PATH
	字段类型:VARCHAR2*/
	private String imgPath;
	
	
	public void setCode(String code){
		this.code = code;
	}

	public String getCode(){
		return code;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setLinkUrl(String linkUrl){
		this.linkUrl = linkUrl;
	}

	public String getLinkUrl(){
		return linkUrl;
	}

	public void setImgPath(String imgPath){
		this.imgPath = imgPath;
	}

	public String getImgPath(){
		return imgPath;
	}

	
}