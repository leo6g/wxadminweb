package com.lfc.wxadminweb.modules.weixin.form;

import java.util.Date;
import org.hibernate.validator.constraints.NotEmpty;
import com.lfc.wxadminweb.common.form.BaseForm;
public class WXMtrlImgGroupForm extends BaseForm{

	/*
	字段注释：分组名称
	列名称:GROUP_NAME
	字段类型:VARCHAR2*/
	private String groupName;
	/*
	字段注释：ID
	列名称:GROUP_ID
	字段类型:VARCHAR2*/
	private String groupId;
	
	
	public void setGroupName(String groupName){
		this.groupName = groupName;
	}

	public String getGroupName(){
		return groupName;
	}

	public void setGroupId(String groupId){
		this.groupId = groupId;
	}

	public String getGroupId(){
		return groupId;
	}

	
}