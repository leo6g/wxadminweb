package com.lfc.wxadminweb.modules.biz.form.study;

import java.util.Date;
import org.hibernate.validator.constraints.NotEmpty;
import com.lfc.wxadminweb.common.form.BaseForm;
public class StudyArticleForm extends BaseForm{

	/*
	字段注释：隶属板块ID
	列名称:CATEGORY_ID
	字段类型:VARCHAR2*/
	private String categoryId;
	/*
	字段注释：ID
	列名称:ID
	字段类型:VARCHAR2*/
	private String id;
	/*
	字段注释：文章ID
	列名称:ARTICLE_ID
	字段类型:VARCHAR2*/
	private String articleId;
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
	字段注释：文章内容
	列名称:CONTENT
	字段类型:CLOB*/
	private String content;
	/*
	字段注释：文章标题
	列名称:TITLE
	字段类型:VARCHAR2*/
	private String title;
	/*
	字段注释：文章图片
	列名称:IMG_PATH
	字段类型:VARCHAR2*/
	private String imgPath;
	//排序字段
	private Integer sortNum;
	//子类型
	private String subCategory;
	
	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public void setCategoryId(String categoryId){
		this.categoryId = categoryId;
	}

	public String getCategoryId(){
		return categoryId;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setArticleId(String articleId){
		this.articleId = articleId;
	}

	public String getArticleId(){
		return articleId;
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

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}

	
}