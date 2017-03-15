package com.lfc.wxadminweb.modules.biz.form;

import java.util.Date;
import org.hibernate.validator.constraints.NotEmpty;
import com.lfc.wxadminweb.common.form.BaseForm;
public class LuckyDogsForm extends BaseForm{

	/*
	字段注释：中奖奖品ID
	列名称:AWARDS_ID
	字段类型:VARCHAR2*/
	@NotEmpty(message = "中奖奖品ID不能为空")
	private String awardsId;
	/*
	字段注释：活动ID
	列名称:ACTIVITY_ID
	字段类型:VARCHAR2*/
	@NotEmpty(message = "活动ID不能为空")
	private String activityId;
	/*
	字段注释：null
	列名称:ID
	字段类型:VARCHAR2*/
	private String id;
	/*
	字段注释：中奖者手机号码
	列名称:PHONE_NUMBER
	字段类型:VARCHAR2*/
	private String phoneNumber;
	/*
	字段注释：中奖时间
	列名称:CREATE_TIME
	字段类型:DATE*/
	private Date createTime;
	/*
	字段注释：中奖者OPEN_ID, 扩张字段，可以不填
	列名称:OPEN_ID
	字段类型:VARCHAR2*/
	private String openId;
	
	//活动名称
	private String activityName;
	//中奖者名字
	private String name;
	
	
	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAwardsId(String awardsId){
		this.awardsId = awardsId;
	}

	public String getAwardsId(){
		return awardsId;
	}

	public void setActivityId(String activityId){
		this.activityId = activityId;
	}

	public String getActivityId(){
		return activityId;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setPhoneNumber(String phoneNumber){
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneNumber(){
		return phoneNumber;
	}

	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

	public Date getCreateTime(){
		return createTime;
	}

	public void setOpenId(String openId){
		this.openId = openId;
	}

	public String getOpenId(){
		return openId;
	}

	
}