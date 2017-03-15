package com.lfc.wxadminweb.modules.biz.form.award;

import java.util.Date;
import java.util.List;

import com.lfc.wxadminweb.common.form.BaseForm;
public class AwardActivityForm extends BaseForm{

	/*
	字段注释：开始日期
	列名称:START_DATE
	字段类型:DATE*/
	private Date startDate;
	/*
	字段注释：活动名称
	列名称:ACTIVITY_NAME
	字段类型:VARCHAR2*/
	private String activityName;
	/*
	字段注释：抽奖活动ID
	列名称:ACTIVITY_ID
	字段类型:VARCHAR2*/
	private String activityId;
	/*
	字段注释：活动描述
	列名称:COMMENTS
	字段类型:VARCHAR2*/
	private String comments;
	/*
	字段注释：结束日期
	列名称:END_DATE
	字段类型:DATE*/
	private Date endDate;
	
	List<AwardsInfoForm> awardsInfoFormList;
	
	private String activityCode;
	
	
	public void setStartDate(Date startDate){
		this.startDate = startDate;
	}

	public Date getStartDate(){
		return startDate;
	}

	public void setActivityName(String activityName){
		this.activityName = activityName;
	}

	public String getActivityName(){
		return activityName;
	}

	public void setActivityId(String activityId){
		this.activityId = activityId;
	}

	public String getActivityId(){
		return activityId;
	}

	public void setComments(String comments){
		this.comments = comments;
	}

	public String getComments(){
		return comments;
	}

	public void setEndDate(Date endDate){
		this.endDate = endDate;
	}

	public Date getEndDate(){
		return endDate;
	}

	public List<AwardsInfoForm> getAwardsInfoFormList() {
		return awardsInfoFormList;
	}

	public void setAwardsInfoFormList(List<AwardsInfoForm> awardsInfoFormList) {
		this.awardsInfoFormList = awardsInfoFormList;
	}

	public String getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}

	
}