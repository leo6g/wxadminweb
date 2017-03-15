package com.lfc.wxadminweb.modules.weixin.form;

import java.util.Date;
import org.hibernate.validator.constraints.NotEmpty;
import com.lfc.wxadminweb.common.form.BaseForm;
public class WXArticleForm extends BaseForm implements java.io.Serializable{

	/*
	字段注释：模板类型(menu:菜单|news:新闻|push:推送)
	列名称:TYPE
	字段类型:VARCHAR2*/
	private String type;
	/*
	字段注释：模板名称
	列名称:NAME
	字段类型:VARCHAR2*/
	private String name;
	/*
	字段注释：主键ID
	列名称:NEWS_TEMP_ID
	字段类型:VARCHAR2*/
	private String newsTempId;
	/*
	字段注释：发布日期
	列名称:PUBLISH_DATE
	字段类型:DATE*/
	private String publishDate;
	/*
	字段注释：期数
	列名称:WEEKLY_NUMBER
	字段类型:VARCHAR2*/
	private String weeklyNumber;
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
	/*
	字段注释：删除标识
	列名称:DELETE_FLAG
	字段类型:NUMBER*/
	private Integer deleteFlag;
	/*
	字段注释：发送状态(T:已发送|F:未发送)
	列名称:SEND_STATE
	字段类型:VARCHAR2*/
	private String sendState;
	/*
	字段注释：审核状态
	（DRAFT：草稿，1-WAITING：一级通过，
	1-REJECTED：二级驳回，2-WAITING：二级通过，
	2-REJECTED：三级驳回，COMPLETED：三级通过）
	列名称:AUTH_STATE
	字段类型:VARCHAR2*/
	private String authState;
	
	//开始时间
	private String beginTime;
	//结束时间
	private String endTime;
	//角色
    private String role;
    
  //显示广告
    private String showAdver;
  //显示点赞
    private String showPraise;
	public String getShowAdver() {
		return showAdver;
	}

	public void setShowAdver(String showAdver) {
		this.showAdver = showAdver;
	}

	public String getShowPraise() {
		return showPraise;
	}

	public void setShowPraise(String showPraise) {
		this.showPraise = showPraise;
	}

	public String getAuthState() {
		return authState;
	}

	public void setAuthState(String authState) {
		this.authState = authState;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setNewsTempId(String newsTempId){
		this.newsTempId = newsTempId;
	}

	public String getNewsTempId(){
		return newsTempId;
	}
	
	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	public void setWeeklyNumber(String weeklyNumber){
		this.weeklyNumber = weeklyNumber;
	}

	public String getWeeklyNumber(){
		return weeklyNumber;
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

	public void setDeleteFlag(Integer deleteFlag){
		this.deleteFlag = deleteFlag;
	}

	public Integer getDeleteFlag(){
		return deleteFlag;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getSendState() {
		return sendState;
	}

	public void setSendState(String sendState) {
		this.sendState = sendState;
	}

	
}