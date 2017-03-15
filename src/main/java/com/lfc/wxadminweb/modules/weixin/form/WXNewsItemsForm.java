package com.lfc.wxadminweb.modules.weixin.form;

import java.util.Date;
import com.lfc.wxadminweb.common.form.BaseForm;
public class WXNewsItemsForm extends BaseForm implements java.io.Serializable{

	/*
	字段注释：内容
	列名称:CONTENT
	字段类型:VARCHAR2*/
	private String content;
	/*
	字段注释：类型
	列名称:TYPE
	字段类型:VARCHAR2*/
	private String type;
	/*
	字段注释：创建时间
	列名称:CREATE_TIME
	字段类型:DATE*/
	private Date createTime;
	/*
	字段注释：主键ID
	列名称:ITEM_ID
	字段类型:VARCHAR2*/
	private String itemId;
	/*
	字段注释：链接URL
	列名称:URL
	字段类型:VARCHAR2*/
	private String url;
	/*
	字段注释：标题
	列名称:TITLE
	字段类型:VARCHAR2*/
	private String title;
	/*
	字段注释：排序
	列名称:SORT
	字段类型:NUMBER*/
	private Integer sort;
	/*
	字段注释：图片路径
	列名称:IMAGE_PATH
	字段类型:VARCHAR2*/
	private String imagePath;
	/*
	字段注释：创建人
	列名称:CREATE_USER
	字段类型:VARCHAR2*/
	private String createUser;
	/*
	字段注释：模板ID
	列名称:NEWS_TEMP_ID
	字段类型:VARCHAR2*/
	private String newsTempId;
	/*
	字段注释：文章作者
	列名称:AUTHOR
	字段类型:VARCHAR2*/
	private String author;
	/*
	字段注释：阅读次数
	列名称:VIEW_TIMES
	字段类型:NUMBER*/
	private Integer viewTimes;
	/*
	字段注释：点赞次数
	列名称:PRAISE_TIMES
	字段类型:NUMBER*/
	private Integer praiseTimes;
	//显示广告
    private String showAdver;
    //显示点赞
    private String showPraise;
    //显示评论
    private String showRemark;
    
	public String getShowRemark() {
		return showRemark;
	}

	public void setShowRemark(String showRemark) {
		this.showRemark = showRemark;
	}

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

	public void setContent(String content){
		this.content = content;
	}

	public String getContent(){
		return content;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

	public Date getCreateTime(){
		return createTime;
	}

	public void setItemId(String itemId){
		this.itemId = itemId;
	}

	public String getItemId(){
		return itemId;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setSort(Integer sort){
		this.sort = sort;
	}

	public Integer getSort(){
		return sort;
	}

	public void setImagePath(String imagePath){
		this.imagePath = imagePath;
	}

	public String getImagePath(){
		return imagePath;
	}

	public void setCreateUser(String createUser){
		this.createUser = createUser;
	}

	public String getCreateUser(){
		return createUser;
	}

	public void setNewsTempId(String newsTempId){
		this.newsTempId = newsTempId;
	}

	public String getNewsTempId(){
		return newsTempId;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Integer getViewTimes() {
		return viewTimes;
	}

	public void setViewTimes(Integer viewTimes) {
		this.viewTimes = viewTimes;
	}

	public Integer getPraiseTimes() {
		return praiseTimes;
	}

	public void setPraiseTimes(Integer praiseTimes) {
		this.praiseTimes = praiseTimes;
	}

	
}