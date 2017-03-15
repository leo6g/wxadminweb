package com.lfc.wxadminweb.modules.weiweb.form;

import java.util.Date;
import org.hibernate.validator.constraints.NotEmpty;
import com.lfc.wxadminweb.common.form.BaseForm;
public class ModulesForm extends BaseForm{

	/*
	字段注释：级别
	列名称:LEVELS
	字段类型:NUMBER*/
	private Integer levels;
	/*
	字段注释：名称
	列名称:NAME
	字段类型:VARCHAR2*/
	private String name;
	/*
	字段注释：null
	列名称:DELETE_FLAG
	字段类型:NUMBER*/
	private Integer deleteFlag;
	/*
	字段注释：null
	列名称:MODULE_ID
	字段类型:VARCHAR2*/
	private String moduleId;
	/*
	字段注释：文章ID
	列名称:ARTICLE_ID
	字段类型:VARCHAR2*/
	private String articleId;
	/*文章名称*/
	private String title;
	/*
	字段注释：是否叶子节点(T:是|F:否)
	列名称:LEAF
	字段类型:VARCHAR2*/
	private String leaf;
	/*
	字段注释：父ID
	列名称:PARENT_ID
	字段类型:VARCHAR2*/
	private String parentId;
	private String parentName;
	/*
	字段注释：模块图标路径
	列名称:ICON_PATH
	字段类型:VARCHAR2*/
	private String iconPath;
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
	
	
	public void setLevels(Integer levels){
		this.levels = levels;
	}

	public Integer getLevels(){
		return levels;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setDeleteFlag(Integer deleteFlag){
		this.deleteFlag = deleteFlag;
	}

	public Integer getDeleteFlag(){
		return deleteFlag;
	}

	public void setModuleId(String moduleId){
		this.moduleId = moduleId;
	}

	public String getModuleId(){
		return moduleId;
	}

	public void setArticleId(String articleId){
		this.articleId = articleId;
	}

	public String getArticleId(){
		return articleId;
	}

	public void setLeaf(String leaf){
		this.leaf = leaf;
	}

	public String getLeaf(){
		return leaf;
	}

	public void setParentId(String parentId){
		this.parentId = parentId;
	}

	public String getParentId(){
		return parentId;
	}

	public void setIconPath(String iconPath){
		this.iconPath = iconPath;
	}

	public String getIconPath(){
		return iconPath;
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

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	
}