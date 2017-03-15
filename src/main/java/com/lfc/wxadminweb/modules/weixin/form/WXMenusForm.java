package com.lfc.wxadminweb.modules.weixin.form;

import java.util.Date;
import org.hibernate.validator.constraints.NotEmpty;
import com.lfc.wxadminweb.common.form.BaseForm;
public class WXMenusForm extends BaseForm{

	/*
	字段注释：菜单名称
	列名称:NAME
	字段类型:VARCHAR2*/
	private String name;
	/*
	字段注释：菜单编码
	列名称:MENU_KEY
	字段类型:VARCHAR2*/
	private String menuKey;
	/*
	字段注释：创建时间
	列名称:CREATE_TIME
	字段类型:DATE*/
	private Date createTime;
	/*
	字段注释：主键ID
	列名称:MENU_ID
	字段类型:VARCHAR2*/
	private String menuId;
	/*
	字段注释：菜单链接
	列名称:URL
	字段类型:VARCHAR2*/
	private String url;
	/*
	字段注释：菜单类别
	列名称:TYPE
	字段类型:VARCHAR2*/
	private String type;
	/*
	字段注释：菜单顺序
	列名称:SORT
	字段类型:NUMBER*/
	private Integer sort;
	/*
	字段注释：菜单级别
	列名称:LEVELS
	字段类型:VARCHAR2*/
	private String levels;
	/*
	字段注释：创建人
	列名称:CREATE_USER
	字段类型:VARCHAR2*/
	private String createUser;
	/*
	字段注释：父菜单ID
	列名称:PARENT_ID
	字段类型:VARCHAR2*/
	private String parentId;
	/*
	字段注释：null
	列名称:DELETE_FLAG
	字段类型:NUMBER*/
	private Integer deleteFlag;
	/*
	字段注释：模版Id
	列名称:TEMPLATE_ID
	字段类型:VARCHAR2*/
	private String templateId;
	/*
	字段注释：模版类型
	列名称:TEMPLATE_ID
	字段类型:VARCHAR2*/
	private String templateType;
	
	
	/*
	字段注释：消息素材mediaId
	列名称:MEDIA_ID
	字段类型:VARCHAR2*/
	private String mediaId;
	
	
	/*
	字段注释：消息素材类型mediaType
	列名称:MEDIA_TYPE
	字段类型:VARCHAR2*/
	private String mediaType;
	
	
	public String getTemplateType() {
		return templateType;
	}

	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setMenuKey(String menuKey){
		this.menuKey = menuKey;
	}

	public String getMenuKey(){
		return menuKey;
	}

	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

	public Date getCreateTime(){
		return createTime;
	}

	public void setMenuId(String menuId){
		this.menuId = menuId;
	}

	public String getMenuId(){
		return menuId;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setSort(Integer sort){
		this.sort = sort;
	}

	public Integer getSort(){
		return sort;
	}

	public void setLevels(String levels){
		this.levels = levels;
	}

	public String getLevels(){
		return levels;
	}

	public void setCreateUser(String createUser){
		this.createUser = createUser;
	}

	public String getCreateUser(){
		return createUser;
	}

	public void setParentId(String parentId){
		this.parentId = parentId;
	}

	public String getParentId(){
		return parentId;
	}

	public void setDeleteFlag(Integer deleteFlag){
		this.deleteFlag = deleteFlag;
	}

	public Integer getDeleteFlag(){
		return deleteFlag;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}
	
}