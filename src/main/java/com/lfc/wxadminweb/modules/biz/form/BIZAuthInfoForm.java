package com.lfc.wxadminweb.modules.biz.form;

import java.util.Date;
import org.hibernate.validator.constraints.NotEmpty;
import com.lfc.wxadminweb.common.form.BaseForm;
public class BIZAuthInfoForm extends BaseForm implements java.io.Serializable{

	/*
	字段注释：审核类型(CARD:信用卡|LOAN:贷款产品|FINANCE:理财产品)
	列名称:TYPE
	字段类型:VARCHAR2*/
	private String type;
	/*
	字段注释：审核的产品ID(信用卡、理财产品、贷款产品ID)
	列名称:PROD_ID
	字段类型:VARCHAR2*/
	private String prodId;
	/*
	字段注释：null
	列名称:AUTH_ID
	字段类型:VARCHAR2*/
	private String authId;
	/*
	字段注释：审核操作结果(APPROVED:通过|REJECTED:驳回|WAITING:待审批)
	列名称:OPER_FLAG
	字段类型:VARCHAR2*/
	private String operFlag;
	/*
	字段注释：审核意见
	列名称:COMMENTS
	字段类型:VARCHAR2*/
	private String comments;
	/*
	字段注释：审核时间
	列名称:CREATE_TIME
	字段类型:DATE*/
	private Date createTime;
	/*
	字段注释：审核人(操作人ID)
	列名称:EXAMINER
	字段类型:VARCHAR2*/
	private String examiner;
	//审核人姓名
	private String checkName;
	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setProdId(String prodId){
		this.prodId = prodId;
	}

	public String getProdId(){
		return prodId;
	}

	public void setAuthId(String authId){
		this.authId = authId;
	}

	public String getAuthId(){
		return authId;
	}

	public void setOperFlag(String operFlag){
		this.operFlag = operFlag;
	}

	public String getOperFlag(){
		return operFlag;
	}

	public void setComments(String comments){
		this.comments = comments;
	}

	public String getComments(){
		return comments;
	}

	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

	public Date getCreateTime(){
		return createTime;
	}

	public void setExaminer(String examiner){
		this.examiner = examiner;
	}

	public String getExaminer(){
		return examiner;
	}

	public String getCheckName() {
		return checkName;
	}

	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}

	
}