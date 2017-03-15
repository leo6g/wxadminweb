package com.lfc.wxadminweb.modules.biz.form.study;

import java.util.Date;
import org.hibernate.validator.constraints.NotEmpty;
import com.lfc.wxadminweb.common.form.BaseForm;
public class StudyCategoryForm extends BaseForm{

	/*
	字段注释：图标路径
	列名称:ICON_PATH
	字段类型:VARCHAR2*/
	private String iconPath;
	/*
	字段注释：板块名称
	列名称:NAME
	字段类型:VARCHAR2*/
	private String name;
	/*
	字段注释：ID
	列名称:ID
	字段类型:VARCHAR2*/
	private String id;
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
	字段注释：背景色
	列名称:BG_COLOR
	字段类型:VARCHAR2*/
	private String bgColor;
	/*
	字段注释：板块排序
	列名称:SORT
	字段类型:NUMBER*/
	private Integer sort;
	/*
	字段注释：删除标识
	列名称:DELETE_FLAG
	字段类型:VARCHAR2*/
	private String deleteFlag;
	
	
	public void setIconPath(String iconPath){
		this.iconPath = iconPath;
	}

	public String getIconPath(){
		return iconPath;
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

	public void setBgColor(String bgColor){
		this.bgColor = bgColor;
	}

	public String getBgColor(){
		return bgColor;
	}

	public void setSort(Integer sort){
		this.sort = sort;
	}

	public Integer getSort(){
		return sort;
	}

	public void setDeleteFlag(String deleteFlag){
		this.deleteFlag = deleteFlag;
	}

	public String getDeleteFlag(){
		return deleteFlag;
	}

	
}