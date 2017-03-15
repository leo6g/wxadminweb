package com.lfc.wxadminweb.modules.weixin.form;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lfc.wxadminweb.common.form.BaseForm;
public class WXMaterialForm extends BaseForm{

	/*
	字段注释：媒体ID
	列名称:MEDIA_ID
	字段类型:VARCHAR2*/
	private String mediaId;
	/*
	字段注释：链接名称
	列名称:NAME
	字段类型:VARCHAR2*/
	private String name;
	/*
	字段注释：创建人
	列名称:CREATE_USER
	字段类型:VARCHAR2*/
	private String createUser;
	/*
	字段注释：主键ID
	列名称:MATERIAL_ID
	字段类型:VARCHAR2*/
	private String materialId;
	/*
	字段注释：分类，该字段只对类型是 视频、音频的数据有效。
属于音频素材、视频素材专有属性。
	列名称:SUB_TYPE
	字段类型:VARCHAR2*/
	private String subType;
	/*
	字段注释：图片分组ID，该字段只对图片素材类型的素材有效。
	列名称:GROUP_ID
	字段类型:VARCHAR2*/
	private String groupId;
	/*
	字段注释：本地资源URL， 该字段只对 图片素材、语音素材、视频素材有效
	列名称:LOCAL_URL
	字段类型:VARCHAR2*/
	private String localUrl;
	/*
	字段注释：素材分类(text:文本|news:图文|voice:语音|video:视频)
	列名称:CATEGORY
	字段类型:VARCHAR2*/
	private String category;
	/*
	字段注释：标签： 只针对视频素材，该列有效
	列名称:VIDEO_TAGS
	字段类型:VARCHAR2*/
	private String videoTags;
	/*
	字段注释：null
	列名称:VIDEO_INSTRU
	字段类型:VARCHAR2*/
	private String videoInstru;
	/*
	字段注释：创建时间
	列名称:CREATE_TIME
	字段类型:DATE*/
	private Date createTime;
	/*
	字段注释：远程资源URL， 该字段只对 图片素材、语音素材、视频素材有效
	列名称:REMOTE_URL
	字段类型:VARCHAR2*/
	private String remoteUrl;
	
	
	//微信多图文内容列表,用来新增微信多图文的入参，请勿删除
	private List<Map<String, Object>> articles;
	
	
	//微信多图文内容列表,用来修改微信多图文的入参，修改文章的索引值，请勿删除
	private String index;
	
	
	//微信多图文内容列表,用来修改微信多图文的入参，请勿删除
	private Map<String, Object> article;
	
	
	//图文素材搜索关键词，包含标题、作者、摘要
	private String keyword;
	
	
	public void setMediaId(String mediaId){
		this.mediaId = mediaId;
	}

	public String getMediaId(){
		return mediaId;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setCreateUser(String createUser){
		this.createUser = createUser;
	}

	public String getCreateUser(){
		return createUser;
	}

	public void setMaterialId(String materialId){
		this.materialId = materialId;
	}

	public String getMaterialId(){
		return materialId;
	}

	public void setSubType(String subType){
		this.subType = subType;
	}

	public String getSubType(){
		return subType;
	}

	public void setGroupId(String groupId){
		this.groupId = groupId;
	}

	public String getGroupId(){
		return groupId;
	}

	public void setLocalUrl(String localUrl){
		this.localUrl = localUrl;
	}

	public String getLocalUrl(){
		return localUrl;
	}

	public void setCategory(String category){
		this.category = category;
	}

	public String getCategory(){
		return category;
	}

	public void setVideoTags(String videoTags){
		this.videoTags = videoTags;
	}

	public String getVideoTags(){
		return videoTags;
	}

	public void setVideoInstru(String videoInstru){
		this.videoInstru = videoInstru;
	}

	public String getVideoInstru(){
		return videoInstru;
	}

	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

	public Date getCreateTime(){
		return createTime;
	}

	public String getRemoteUrl() {
		return remoteUrl;
	}

	public void setRemoteUrl(String remoteUrl) {
		this.remoteUrl = remoteUrl;
	}

	public List<Map<String, Object>> getArticles() {
		return articles;
	}

	public void setArticles(List<Map<String, Object>> articles) {
		this.articles = articles;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public Map<String, Object> getArticle() {
		return article;
	}

	public void setArticle(Map<String, Object> article) {
		this.article = article;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
}