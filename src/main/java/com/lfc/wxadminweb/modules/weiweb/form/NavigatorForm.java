package com.lfc.wxadminweb.modules.weiweb.form;

import java.util.Date;
import org.hibernate.validator.constraints.NotEmpty;
import com.lfc.wxadminweb.common.form.BaseForm;
public class NavigatorForm extends BaseForm{

	/*
	字段注释：主键ID
	列名称:NAV_ID
	字段类型:VARCHAR2*/
	private String navId;
	/*
	字段注释：名称
	列名称:NAME
	字段类型:VARCHAR2*/
	@NotEmpty(message = "名称不能为空")
	private String name;
	/*
	字段注释：图标路径
	列名称:ICON_PATH
	字段类型:VARCHAR2*/
	private String iconPath;
	/*
	字段注释：排序
	列名称:SORT
	字段类型:NUMBER*/
	private Integer sort;
	/*
	字段注释：链接URL
	列名称:URL
	字段类型:VARCHAR2*/
	private String url;
	/*
	字段注释：创建人
	列名称:CREATE_USER
	字段类型:VARCHAR2*/
	private String createUser;
	/*
	字段注释：创建时间
	列名称:CREATE_TIME
	字段类型:DATE*/
	private Date createTime;
	/*
	字段注释：删除标识（0：正常，-1：删除）
	列名称:DELETE_FLAG
	字段类型:NUMBER*/
	private Integer deleteFlag;
	
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

	public void setNavId(String navId){
		this.navId = navId;
	}

	public String getNavId(){
		return navId;
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

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}

	public void setSort(Integer sort){
		this.sort = sort;
	}

	public Integer getSort(){
		return sort;
	}

	public void setDeleteFlag(Integer deleteFlag){
		this.deleteFlag = deleteFlag;
	}

	public Integer getDeleteFlag(){
		return deleteFlag;
	}

	
}