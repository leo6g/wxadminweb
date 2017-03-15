package com.lfc.wxadminweb.modules.biz.form.recommend.form;

import java.util.Date;
import org.hibernate.validator.constraints.NotEmpty;
import com.lfc.wxadminweb.common.form.BaseForm;
public class StaffRecomendForm extends BaseForm{

	/*
	字段注释：关注用户ID
	列名称:OPEN_ID
	字段类型:VARCHAR2*/
	private String openId;
	/*
	字段注释：员工柜员号码
	列名称:STAFF_CODE
	字段类型:VARCHAR2*/
	private String staffCode;
	/*
	字段注释：主键
	列名称:ID
	字段类型:VARCHAR2*/
	private String id;
	/*
	字段注释：取消关注时间
	列名称:CANCEL_TIME
	字段类型:DATE*/
	private Date cancelTime;
	/*
	字段注释：关注时间
	列名称:SUBSCRIBE_TIME
	字段类型:DATE*/
	private Date subscribeTime;
	/*
	字段注释：关注用户头像
	列名称:HEAD_IMG
	字段类型:VARCHAR2*/
	private String headImg;
	/*
	字段注释：关注用户昵称
	列名称:NICK_NAME
	字段类型:CHAR*/
	private String nickName;
	/*
	字段注释：当前状态(T:关注|F:取消关注)
	列名称:CUR_STATE
	字段类型:VARCHAR2*/
	private String curState;
	/*
	字段注释：重复关注时间
	列名称:RESUB_TIME
	字段类型:DATE*/
	private Date resubTime;
	
	
	public void setOpenId(String openId){
		this.openId = openId;
	}

	public String getOpenId(){
		return openId;
	}

	public void setStaffCode(String staffCode){
		this.staffCode = staffCode;
	}

	public String getStaffCode(){
		return staffCode;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setCancelTime(Date cancelTime){
		this.cancelTime = cancelTime;
	}

	public Date getCancelTime(){
		return cancelTime;
	}

	public void setSubscribeTime(Date subscribeTime){
		this.subscribeTime = subscribeTime;
	}

	public Date getSubscribeTime(){
		return subscribeTime;
	}

	public void setHeadImg(String headImg){
		this.headImg = headImg;
	}

	public String getHeadImg(){
		return headImg;
	}

	public void setNickName(String nickName){
		this.nickName = nickName;
	}

	public String getNickName(){
		return nickName;
	}

	public void setCurState(String curState){
		this.curState = curState;
	}

	public String getCurState(){
		return curState;
	}

	public void setResubTime(Date resubTime){
		this.resubTime = resubTime;
	}

	public Date getResubTime(){
		return resubTime;
	}

	
}