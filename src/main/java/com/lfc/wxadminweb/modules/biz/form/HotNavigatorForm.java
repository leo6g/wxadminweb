package com.lfc.wxadminweb.modules.biz.form;

import java.util.Date;
import org.hibernate.validator.constraints.NotEmpty;
import com.lfc.wxadminweb.common.form.BaseForm;
public class HotNavigatorForm extends BaseForm{

	/*
	字段注释：类型(finance:理财|metal:贵金属|loan:贷款)
	列名称:TYPE
	字段类型:VARCHAR2*/
	private String type;
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
	字段注释：自定义图标
	列名称:SELF_IMG_PATH
	字段类型:VARCHAR2*/
	private String selfImgPath;
	/*
	字段注释：自定义标题
	列名称:TITLE
	字段类型:VARCHAR2*/
	private String title;
	
	
	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
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

	public void setSelfImgPath(String selfImgPath){
		this.selfImgPath = selfImgPath;
	}

	public String getSelfImgPath(){
		return selfImgPath;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	
}