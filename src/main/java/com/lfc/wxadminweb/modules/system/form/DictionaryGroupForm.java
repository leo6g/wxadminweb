package com.lfc.wxadminweb.modules.system.form;

import org.hibernate.validator.constraints.NotBlank;

public class DictionaryGroupForm {
	
	//主键ID
    private String id;

    //字典组编码
    @NotBlank(message = "编码不能为空")
    private String dicGroupCode;
    
    //字典组名称
    @NotBlank(message = "名称不能为空")
    private String dicGroupName;
    
    //逻辑删除标志
    private Integer deleteFlag;

    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDicGroupCode() {
		return dicGroupCode;
	}

	public void setDicGroupCode(String dicGroupCode) {
		this.dicGroupCode = dicGroupCode;
	}

	public String getDicGroupName() {
		return dicGroupName;
	}

	public void setDicGroupName(String dicGroupName) {
		this.dicGroupName = dicGroupName;
	}

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

}