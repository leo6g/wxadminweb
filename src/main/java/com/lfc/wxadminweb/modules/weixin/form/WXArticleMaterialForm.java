package com.lfc.wxadminweb.modules.weixin.form;

import java.util.List;
import java.util.Map;

import com.lfc.wxadminweb.common.form.BaseForm;
/**
 * 微信图文素材实体
 * @author helei
 */
public class WXArticleMaterialForm extends BaseForm{

	/*
	字段注释：文章ID
	列名称:ARTICLE_ID
	字段类型:VARCHAR2*/
	private String articleId;
	
	/*
	字段注释：素材ID
	列名称:MATERIAL_ID
	字段类型:VARCHAR2*/
	private String materialId;
	
	/*
	字段注释：文章标题
	列名称:TITLE
	字段类型:VARCHAR2*/
	private String title;
	
	
	/*
	字段注释：作者
	列名称:AUTHOR
	字段类型:VARCHAR2*/
	private String author;
	
	
	/*
	字段注释：缩略图ID
	列名称:THUMB_MEDIA_ID
	字段类型:VARCHAR2*/
	private String thumbMediaId;
	
	
	/*
	字段注释：文章摘要
	列名称:DIGEST
	字段类型:VARCHAR2*/
	private String digest;
	
	
	/*
	字段注释：文章内容
	列名称:CONTENT
	字段类型:VARCHAR2*/
	private String content;
	
	
	/*
	字段注释：原文URL
	列名称:CONTENT_SOURCE_URL
	字段类型:VARCHAR2*/
	private String sourceUrl;
	
	
	/*
	字段注释：是否显示缩略图
	列名称:SHOW_COVER_PIC
	字段类型:Integer*/
	private Integer showCoverPic;


	/*
	字段注释：文章顺序
	列名称:INDEX_NUM
	字段类型:Integer*/
	private Integer indexNum;
	
	
	/*
	字段注释：服务器端URL地址(即微信服务器该素材的URL)
	列名称:REMOTE_URL
	字段类型:VARCHAR2*/
	private String remoteUrl;

	
	/**
	 * 外链图片URL集合，用来前端UE上传外链图片到微信服务器
	 */
	private List<Map<String, Object>> imgs;
	
	
	public String getArticleId() {
		return articleId;
	}


	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	
	public String getMaterialId() {
		return materialId;
	}


	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getAuthor() {
		return author;
	}


	public void setAuthor(String author) {
		this.author = author;
	}


	public String getThumbMediaId() {
		return thumbMediaId;
	}


	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}


	public String getDigest() {
		return digest;
	}


	public void setDigest(String digest) {
		this.digest = digest;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getSourceUrl() {
		return sourceUrl;
	}


	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}


	public Integer getShowCoverPic() {
		return showCoverPic;
	}


	public void setShowCoverPic(Integer showCoverPic) {
		this.showCoverPic = showCoverPic;
	}


	public List<Map<String, Object>> getImgs() {
		return imgs;
	}


	public void setImgs(List<Map<String, Object>> imgs) {
		this.imgs = imgs;
	}


	public Integer getIndexNum() {
		return indexNum;
	}


	public void setIndexNum(Integer indexNum) {
		this.indexNum = indexNum;
	}

	public String getRemoteUrl() {
		return remoteUrl;
	}


	public void setRemoteUrl(String remoteUrl) {
		this.remoteUrl = remoteUrl;
	}
	
	
}