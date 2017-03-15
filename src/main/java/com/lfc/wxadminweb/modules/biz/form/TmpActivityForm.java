package com.lfc.wxadminweb.modules.biz.form;

import java.util.Date;
import org.hibernate.validator.constraints.NotEmpty;
import com.lfc.wxadminweb.common.form.BaseForm;
public class TmpActivityForm extends BaseForm{

	/*
	字段注释：活动类型
	列名称:TYPE
	字段类型:VARCHAR2*/
	private String type;
	/*
	字段注释：微信用户OPEN_ID
	列名称:OPEN_ID
	字段类型:VARCHAR2*/
	private String openId;
	/*
	字段注释：null
	列名称:ID
	字段类型:VARCHAR2*/
	private String id;
	/*
	字段注释：扩展字段4
	列名称:EXT_STR4
	字段类型:VARCHAR2*/
	private String extStr4;
	/*
	字段注释：扩展字段3
	列名称:EXT_STR3
	字段类型:VARCHAR2*/
	private String extStr3;
	/*
	字段注释：扩展字段2
	列名称:EXT_STR2
	字段类型:VARCHAR2*/
	private String extStr2;
	/*
	字段注释：扩展字段1
	列名称:EXT_STR1
	字段类型:VARCHAR2*/
	private String extStr1;
	/*
	字段注释：删除标识
	列名称:DELETE_FLAG
	字段类型:VARCHAR2*/
	private String deleteFlag;
	/*
	字段注释：创建日期
	列名称:CREATE_TIME
	字段类型:DATE*/
	private Date createTime;
	
	
	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setOpenId(String openId){
		this.openId = openId;
	}

	public String getOpenId(){
		return openId;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setExtStr4(String extStr4){
		this.extStr4 = extStr4;
	}

	public String getExtStr4(){
		return extStr4;
	}

	public void setExtStr3(String extStr3){
		this.extStr3 = extStr3;
	}

	public String getExtStr3(){
		return extStr3;
	}

	public void setExtStr2(String extStr2){
		this.extStr2 = extStr2;
	}

	public String getExtStr2(){
		return extStr2;
	}

	public void setExtStr1(String extStr1){
		this.extStr1 = extStr1;
	}

	public String getExtStr1(){
		return extStr1;
	}

	public void setDeleteFlag(String deleteFlag){
		this.deleteFlag = deleteFlag;
	}

	public String getDeleteFlag(){
		return deleteFlag;
	}

	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

	public Date getCreateTime(){
		return createTime;
	}

	
}