package com.lfc.wxadminweb.modules.weixin.form;

import java.util.Date;

import com.lfc.wxadminweb.common.form.BaseForm;
public class ArticlePraiseForm extends BaseForm implements java.io.Serializable{

	/*
	字段注释：微信用户ID
	列名称:OPEN_ID
	字段类型:VARCHAR2*/
	private String openId;
	/*
	字段注释：文章ID
	列名称:ITEM_ID
	字段类型:VARCHAR2*/
	private String itemId;
	/*
	字段注释：null
	列名称:ID
	字段类型:VARCHAR2*/
	private String id;
	/*
	字段注释：null
	列名称:CREATE_TIME
	字段类型:DATE*/
	private Date createTime;
	/*
	字段注释：点赞状态(T:已经点赞|F:取消点赞)
	列名称:STATE
	字段类型:VARCHAR2*/
	private String state;
	
	public void setOpenId(String openId){
		this.openId = openId;
	}

	public String getOpenId(){
		return openId;
	}

	public void setItemId(String itemId){
		this.itemId = itemId;
	}

	public String getItemId(){
		return itemId;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

	public Date getCreateTime(){
		return createTime;
	}

	public void setState(String state){
		this.state = state;
	}

	public String getState(){
		return state;
	}

	
}