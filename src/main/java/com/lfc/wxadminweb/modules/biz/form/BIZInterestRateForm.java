package com.lfc.wxadminweb.modules.biz.form;

import java.util.Date;
import org.hibernate.validator.constraints.NotEmpty;
import com.lfc.wxadminweb.common.form.BaseForm;
public class BIZInterestRateForm extends BaseForm implements java.io.Serializable{

	/*
	字段注释：null
	列名称:SUB_TYPE
	字段类型:VARCHAR2*/
	@NotEmpty(message = "子类型不能为空")
	private String subType;
	
	private String subTypeName;
	/*
	字段注释：类型(loan:贷款|deposit:存款)
	列名称:TYPE
	字段类型:VARCHAR2*/
	@NotEmpty(message = "类型不能为空")
	private String type;
	/*
	字段注释：null
	列名称:RATE_ID
	字段类型:VARCHAR2*/
	private String rateId;
	/*
	字段注释：创建人
	列名称:CREATE_USER
	字段类型:VARCHAR2*/
	private String createUser;
	/*
	字段注释：存贷款期限中文描述
	列名称:PERIOD_CN
	字段类型:VARCHAR2*/
	private String periodCn;
	/*
	字段注释：利率
	列名称:RATE
	字段类型:NUMBER*/
	@NotEmpty(message = "年利率不能为空")
	private String rate;
	/*
	字段注释：存贷款期限
	列名称:PERIOD
	字段类型:VARCHAR2*/
	@NotEmpty(message = "存贷款期限不能为空")
	private String period;
	/*
	字段注释：删除标识
	列名称:DELETE_FLAG
	字段类型:NUMBER*/
	private Integer deleteFlag;
	/*
	字段注释：创建时间
	列名称:CREATE_TIME
	字段类型:DATE*/
	private Date createTime;
	
	
	public void setSubType(String subType){
		this.subType = subType;
	}

	public String getSubType(){
		return subType;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setRateId(String rateId){
		this.rateId = rateId;
	}

	public String getRateId(){
		return rateId;
	}

	public void setCreateUser(String createUser){
		this.createUser = createUser;
	}

	public String getCreateUser(){
		return createUser;
	}

	public void setPeriodCn(String periodCn){
		this.periodCn = periodCn;
	}

	public String getPeriodCn(){
		return periodCn;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public void setPeriod(String period){
		this.period = period;
	}

	public String getPeriod(){
		return period;
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

	public String getSubTypeName() {
		return subTypeName;
	}

	public void setSubTypeName(String subTypeName) {
		this.subTypeName = subTypeName;
	}

	
}