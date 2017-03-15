package com.lfc.wxadminweb.modules.biz.form;

import java.util.Date;
import org.hibernate.validator.constraints.NotEmpty;
import com.lfc.wxadminweb.common.form.BaseForm;
public class UserRightsInfoForm extends BaseForm{

	/*
	字段注释：客户等级
	列名称:CUSTOMER_LEVEL
	字段类型:VARCHAR2*/
	private String customerLevel;
	/*
	字段注释：微信用户ID
	列名称:WX_USER_ID
	字段类型:VARCHAR2*/
	private String wxUserId;
	/*
	字段注释：主键
	列名称:ID
	字段类型:VARCHAR2*/
	private String id;
	/*
	字段注释：剩余次数
	列名称:REMAIN_CNT
	字段类型:NUMBER*/
	private Integer remainCnt;
	/*
	字段注释：使用次数
	列名称:USED_CNT
	字段类型:NUMBER*/
	private Integer usedCnt;
	/*
	字段注释：初始次数
	列名称:INIT_CNT
	字段类型:NUMBER*/
	private Integer initCnt;
	/*
	字段注释：服务代码
	列名称:SERVICE_CODE
	字段类型:VARCHAR2*/
	private String serviceCode;
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
	
	
	public void setCustomerLevel(String customerLevel){
		this.customerLevel = customerLevel;
	}

	public String getCustomerLevel(){
		return customerLevel;
	}

	public void setWxUserId(String wxUserId){
		this.wxUserId = wxUserId;
	}

	public String getWxUserId(){
		return wxUserId;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setRemainCnt(Integer remainCnt){
		this.remainCnt = remainCnt;
	}

	public Integer getRemainCnt(){
		return remainCnt;
	}

	public void setUsedCnt(Integer usedCnt){
		this.usedCnt = usedCnt;
	}

	public Integer getUsedCnt(){
		return usedCnt;
	}

	public void setInitCnt(Integer initCnt){
		this.initCnt = initCnt;
	}

	public Integer getInitCnt(){
		return initCnt;
	}

	public void setServiceCode(String serviceCode){
		this.serviceCode = serviceCode;
	}

	public String getServiceCode(){
		return serviceCode;
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