package com.lfc.wxadminweb.modules.biz.form;

import java.util.Date;
import org.hibernate.validator.constraints.NotEmpty;
import com.lfc.wxadminweb.common.form.BaseForm;
public class RecommendFinanceForm extends BaseForm{

	/*
	字段注释：推荐等级(信用卡和借记卡的用户等级)
	列名称:LEVELS
	字段类型:VARCHAR2*/
	private String levels;
	/*
	字段注释：文章ID
	列名称:ARTICLE_ID
	字段类型:VARCHAR2*/
	private String articleId;
	/*
	字段注释：主键ID
	列名称:ID
	字段类型:VARCHAR2*/
	private String id;
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
	字段注释：热门图标路径
	列名称:HOT_IMG_PATH
	字段类型:VARCHAR2*/
	private String hotImgPath;
	/*
	字段注释：是否热门
	列名称:IS_HOT
	字段类型:VARCHAR2*/
	private String isHot;
	
	private String title;
	
	
	public void setLevels(String levels){
		this.levels = levels;
	}

	public String getLevels(){
		return levels;
	}

	public void setArticleId(String articleId){
		this.articleId = articleId;
	}

	public String getArticleId(){
		return articleId;
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

	public void setCreateUser(String createUser){
		this.createUser = createUser;
	}

	public String getCreateUser(){
		return createUser;
	}

	public void setHotImgPath(String hotImgPath){
		this.hotImgPath = hotImgPath;
	}

	public String getHotImgPath(){
		return hotImgPath;
	}

	public void setIsHot(String isHot){
		this.isHot = isHot;
	}

	public String getIsHot(){
		return isHot;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	
}