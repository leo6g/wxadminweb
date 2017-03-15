package com.lfc.wxadminweb.modules.biz.form.personalcenter;

import java.util.Date;
import org.hibernate.validator.constraints.NotEmpty;
import com.lfc.wxadminweb.common.form.BaseForm;
public class FicMoneyForm extends BaseForm{

	/*
	字段注释：积分来源方式
	列名称:TYPE
	字段类型:VARCHAR2*/
	private String type;
	/*
	字段注释：微信用户ID
	列名称:OPEN_ID
	字段类型:VARCHAR2*/
	private String openId;
	/*
	字段注释：null
	列名称:FIC_ID
	字段类型:VARCHAR2*/
	private String ficId;
	/*
	字段注释：创建时间
	列名称:CREATE_TIME
	字段类型:DATE*/
	private Date createTime;
	/*
	字段注释：数量
	列名称:AMOUNT
	字段类型:NUMBER*/
	private Integer amount;
	
	
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

	public void setFicId(String ficId){
		this.ficId = ficId;
	}

	public String getFicId(){
		return ficId;
	}

	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

	public Date getCreateTime(){
		return createTime;
	}

	public void setAmount(Integer amount){
		this.amount = amount;
	}

	public Integer getAmount(){
		return amount;
	}

	
}