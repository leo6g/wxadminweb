package com.lfc.wxadminweb.modules.biz.form.weiweb;

import java.util.Date;
import org.hibernate.validator.constraints.NotEmpty;
import com.lfc.wxadminweb.common.form.BaseForm;
public class ModulesArticleForm extends BaseForm{

	/*
	字段注释：标题
	列名称:TITLE
	字段类型:VARCHAR2*/
	private String title;
	/*
	字段注释：模块ID
	列名称:MODULE_ID
	字段类型:VARCHAR2*/
	private String moduleId;
	/*
	字段注释：主键ID
	列名称:ID
	字段类型:VARCHAR2*/
	private String id;
	/*
	字段注释：创建人
	列名称:CREATE_USER
	字段类型:VARCHAR2*/
	private String createUser;
	/*
	字段注释：标题尾部图片路径
	列名称:TAILE_IMG_PATH
	字段类型:VARCHAR2*/
	private String taileImgPath;
	/*
	字段注释：文章URL
	列名称:URL
	字段类型:VARCHAR2*/
	private String url;
	/*
	字段注释：图片路径
	列名称:IMG_PATH
	字段类型:VARCHAR2*/
	private String imgPath;
	/*
	字段注释：null
	列名称:DELETE_FLAG
	字段类型:NUMBER*/
	private Integer deleteFlag;
	/*
	字段注释：创建时间
	列名称:CREATE_TIME
	字段类型:DATE*/
	private Date createTime;
	
	/*
	字段注释：顺序
	列名称:sortNum
	字段类型:NUMBER*/
	private Integer sortNum;
	public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setModuleId(String moduleId){
		this.moduleId = moduleId;
	}

	public String getModuleId(){
		return moduleId;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setCreateUser(String createUser){
		this.createUser = createUser;
	}

	public String getCreateUser(){
		return createUser;
	}

	public void setTaileImgPath(String taileImgPath){
		this.taileImgPath = taileImgPath;
	}

	public String getTaileImgPath(){
		return taileImgPath;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}

	public void setImgPath(String imgPath){
		this.imgPath = imgPath;
	}

	public String getImgPath(){
		return imgPath;
	}

	public void setDeleteFlag(Integer deleteFlag){
		this.deleteFlag = deleteFlag;
	}

	public Integer getDeleteFlag(){
		return deleteFlag;
	}

	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

	public Date getCreateTime(){
		return createTime;
	}

	
}