package com.lfc.wxadminweb.modules.weixin.form;

import java.util.Date;
import com.lfc.wxadminweb.common.form.BaseForm;
public class ArticleRemarkForm extends BaseForm implements java.io.Serializable{

	/*
	字段注释：微信用户ID
	列名称:OPEN_ID
	字段类型:VARCHAR2*/
	private String openId;
	/*
	字段注释：主键ID
	列名称:ITEM_ID
	字段类型:VARCHAR2*/
	private String itemId;
	/*
	字段注释：主键
	列名称:REMARK_ID
	字段类型:VARCHAR2*/
	private String remarkId;
	/*
	字段注释：审核人
	列名称:AUTH_USER
	字段类型:VARCHAR2*/
	private String authUser;
	/*
	字段注释：审核标识(T:审核通过|F:审核未通过)
	列名称:AUTH_FLAG
	字段类型:VARCHAR2*/
	private String authFlag;
	/*
	字段注释：评论时间
	列名称:CREATE_TIME
	字段类型:DATE*/
	private Date createTime;
	/*
	字段注释：用户评论内容
	列名称:REMARK
	字段类型:VARCHAR2*/
	private String remark;
	/*
	字段注释：审核时间
	列名称:AUTH_TIME
	字段类型:DATE*/
	private Date authTime;
	/*
	字段注释：用户昵称
	列名称:nick_name
	字段类型:VARCHAR2*/
	private String nickName;
	
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

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

	public void setRemarkId(String remarkId){
		this.remarkId = remarkId;
	}

	public String getRemarkId(){
		return remarkId;
	}

	public void setAuthUser(String authUser){
		this.authUser = authUser;
	}

	public String getAuthUser(){
		return authUser;
	}

	public void setAuthFlag(String authFlag){
		this.authFlag = authFlag;
	}

	public String getAuthFlag(){
		return authFlag;
	}

	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

	public Date getCreateTime(){
		return createTime;
	}

	public void setRemark(String remark){
		this.remark = remark;
	}

	public String getRemark(){
		return remark;
	}

	public void setAuthTime(Date authTime){
		this.authTime = authTime;
	}

	public Date getAuthTime(){
		return authTime;
	}

	
}