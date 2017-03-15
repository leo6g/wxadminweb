package com.lfc.wxadminweb.modules.weiweb.form;

import java.util.Date;
import org.hibernate.validator.constraints.NotEmpty;
import com.lfc.wxadminweb.common.form.BaseForm;
public class ArticleForm extends BaseForm{

	/*
	字段注释：内容
	列名称:CONTENT
	字段类型:CLOB*/
	private String content;
	/*
	字段注释：标题
	列名称:TITLE
	字段类型:VARCHAR2*/
	private String title;
	/*
	字段注释：主键ID
	列名称:ARTICLE_ID
	字段类型:VARCHAR2*/
	private String articleId;
	/*
	字段注释：null
	列名称:DELETE_FLAG
	字段类型:NUMBER*/
	private Integer deleteFlag;
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
	字段注释：阅读次数
	列名称:VIEW_TIMES
	字段类型:NUMBER*/
	private Integer viewTimes;
	
	
	public void setContent(String content){
		this.content = content;
	}

	public String getContent(){
		return content;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setArticleId(String articleId){
		this.articleId = articleId;
	}

	public String getArticleId(){
		return articleId;
	}

	public void setDeleteFlag(Integer deleteFlag){
		this.deleteFlag = deleteFlag;
	}

	public Integer getDeleteFlag(){
		return deleteFlag;
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

	public void setViewTimes(Integer viewTimes){
		this.viewTimes = viewTimes;
	}

	public Integer getViewTimes(){
		return viewTimes;
	}

	
}