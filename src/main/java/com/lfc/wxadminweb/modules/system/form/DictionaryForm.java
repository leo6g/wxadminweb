package com.lfc.wxadminweb.modules.system.form;

import org.hibernate.validator.constraints.NotBlank;

public class DictionaryForm {
	
	//主键ID
    private String id;

    //字典编码
    @NotBlank(message = "编码不能为空")
    private String dicCode;

    //字典名称
    @NotBlank(message = "名称不能为空")
    private String dicName;

    //字典组ID
    private String dicGroupId;

    //父ID
    private String parentId;

    //逻辑删除标志
    private Integer deleteFlag;

    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDicCode() {
		return dicCode;
	}

	public void setDicCode(String dicCode) {
		this.dicCode = dicCode;
	}

	public String getDicName() {
		return dicName;
	}

	public void setDicName(String dicName) {
		this.dicName = dicName;
	}

	public String getDicGroupId() {
		return dicGroupId;
	}

	public void setDicGroupId(String dicGroupId) {
		this.dicGroupId = dicGroupId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

}