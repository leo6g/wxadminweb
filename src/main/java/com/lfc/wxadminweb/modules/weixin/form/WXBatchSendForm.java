package com.lfc.wxadminweb.modules.weixin.form;

public class WXBatchSendForm {

	private String materialId;
	private String type;
	private String content;
	private Integer isToAll;
	private Integer tagId;
	public String getMaterialId() {
		return materialId;
	}
	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getIsToAll() {
		return isToAll;
	}
	public void setIsToAll(Integer isToAll) {
		this.isToAll = isToAll;
	}
	public Integer getTagId() {
		return tagId;
	}
	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}
}
