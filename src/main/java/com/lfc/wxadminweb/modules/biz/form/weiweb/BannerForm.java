package com.lfc.wxadminweb.modules.biz.form.weiweb;

import java.util.Date;
import org.hibernate.validator.constraints.NotEmpty;
import com.lfc.wxadminweb.common.form.BaseForm;
public class BannerForm extends BaseForm{

	/*
	字段注释：图片名称
	列名称:NAME
	字段类型:VARCHAR2*/
	private String name;
	/*
	字段注释：模块ID
	列名称:MODULE_ID
	字段类型:VARCHAR2*/
	private String moduleId;
	/*
	字段注释：null
	列名称:DELETE_FLAG
	字段类型:NUMBER*/
	private Integer deleteFlag;
	/*
	字段注释：主键ID
	列名称:BANNER_ID
	字段类型:VARCHAR2*/
	private String bannerId;
	/*
	字段注释：轮播顺序
	列名称:SORT
	字段类型:NUMBER*/
	private Integer sort;
	/*
	字段注释：类型(banner:轮播广告|background:背景图片)
	列名称:TYPE
	字段类型:VARCHAR2*/
	private String type;
	/*
	字段注释：图片链接URL
	列名称:URL
	字段类型:VARCHAR2*/
	private String url;
	/*
	字段注释：图片路径
	列名称:PATH
	字段类型:VARCHAR2*/
	private String path;
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
	
	
	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setModuleId(String moduleId){
		this.moduleId = moduleId;
	}

	public String getModuleId(){
		return moduleId;
	}

	public void setDeleteFlag(Integer deleteFlag){
		this.deleteFlag = deleteFlag;
	}

	public Integer getDeleteFlag(){
		return deleteFlag;
	}

	public void setBannerId(String bannerId){
		this.bannerId = bannerId;
	}

	public String getBannerId(){
		return bannerId;
	}

	public void setSort(Integer sort){
		this.sort = sort;
	}

	public Integer getSort(){
		return sort;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}

	public void setPath(String path){
		this.path = path;
	}

	public String getPath(){
		return path;
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