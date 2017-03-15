package com.lfc.wxadminweb.modules.biz.form.weiweb;

import java.util.Date;
import org.hibernate.validator.constraints.NotEmpty;
import com.lfc.wxadminweb.common.form.BaseForm;
public class ModulesForm extends BaseForm{

	/*
	字段注释：模块编码
	列名称:MODULE_CODE
	字段类型:VARCHAR2*/
	private String moduleCode;
	/*
	字段注释：名称
	列名称:NAME
	字段类型:VARCHAR2*/
	private String name;
	/*
	字段注释：主键
	列名称:MODULE_ID
	字段类型:VARCHAR2*/
	private String moduleId;
	/*
	字段注释：创建人
	列名称:CREATE_USER
	字段类型:VARCHAR2*/
	private String createUser;
	/*
	字段注释：是否叶子节点(T:是|F:否)
	列名称:LEAF
	字段类型:VARCHAR2*/
	private String leaf;
	/*
	字段注释：父ID
	列名称:PARENT_MODULE_ID
	字段类型:VARCHAR2*/
	private String parentModuleId;
	/*
	字段注释：级别
	列名称:LEVELS
	字段类型:NUMBER*/
	private Integer levels;
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
	
	
	public void setModuleCode(String moduleCode){
		this.moduleCode = moduleCode;
	}

	public String getModuleCode(){
		return moduleCode;
	}

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

	public void setCreateUser(String createUser){
		this.createUser = createUser;
	}

	public String getCreateUser(){
		return createUser;
	}

	public void setLeaf(String leaf){
		this.leaf = leaf;
	}

	public String getLeaf(){
		return leaf;
	}

	public void setParentModuleId(String parentModuleId){
		this.parentModuleId = parentModuleId;
	}

	public String getParentModuleId(){
		return parentModuleId;
	}

	public void setLevels(Integer levels){
		this.levels = levels;
	}

	public Integer getLevels(){
		return levels;
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