package com.lfc.wxadminweb.modules.biz.form.award;

import java.util.Date;
import org.hibernate.validator.constraints.NotEmpty;
import com.lfc.wxadminweb.common.form.BaseForm;
public class TBizAwardPlayerForm extends BaseForm{

	/*
	字段注释：姓名
	列名称:NAME
	字段类型:VARCHAR2*/
	private String name;
	/*
	字段注释：手机号码
	列名称:PHONE_NUMBER
	字段类型:VARCHAR2*/
	private String phoneNumber;
	/*
	字段注释：null
	列名称:ID
	字段类型:VARCHAR2*/
	private String id;
	/*
	字段注释：抽奖期数
	列名称:SERIES_NUM
	字段类型:VARCHAR2*/
	private String seriesNum;
	/*
	字段注释：(T:已经参与|F:为参与)
	列名称:PLAY_FLAG
	字段类型:VARCHAR2*/
	private String playFlag;
	
	
	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setPhoneNumber(String phoneNumber){
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneNumber(){
		return phoneNumber;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setSeriesNum(String seriesNum){
		this.seriesNum = seriesNum;
	}

	public String getSeriesNum(){
		return seriesNum;
	}

	public void setPlayFlag(String playFlag){
		this.playFlag = playFlag;
	}

	public String getPlayFlag(){
		return playFlag;
	}

	
}