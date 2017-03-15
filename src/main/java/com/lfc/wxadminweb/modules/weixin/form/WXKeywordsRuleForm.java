package com.lfc.wxadminweb.modules.weixin.form;

import org.hibernate.validator.constraints.NotBlank;

import com.lfc.wxadminweb.common.form.BaseForm;
public class WXKeywordsRuleForm  extends BaseForm{

	/*
	字段注释：主键RULE_ID
	列名称:RULE_ID
	字段类型:VARCHAR2*/
	private String ruleId;
	
	/*
	字段注释：规则名称
	列名称:RULE_NAME
	字段类型:VARCHAR2*/
	@NotBlank(message = "规则名不能为空")
	private String ruleName;
	/*
	字段注释：text：文本，image：图片，voice：语音，video：视频，article：图文
	列名称:RESPONSE_TYPE
	字段类型:VARCHAR2*/
	@NotBlank
	private String responseType;
	
	/*
	字段注释：媒体ID
	列名称:MATERIAL_ID
	字段类型:VARCHAR2*/
	private String materialId;
	/*
	字段注释：文本内容
	列名称:TXT_CONTENT
	字段类型:VARCHAR2*/
	private String txtContent;
	/*
	字段注释：创建人
	列名称:CREATE_USER
	字段类型:VARCHAR2*/
	private String createUser;
	@NotBlank(message = "关键词不能为空")
	private String words;//关键字信息"app_0"
	
	private String queryKey;//查询参数，规则/关键字
	
	public String getQueryKey() {
		return queryKey;
	}
	public void setQueryKey(String queryKey) {
		this.queryKey = queryKey;
	}

	public String getRuleId() {
		return ruleId;
	}
	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getResponseType() {
		return responseType;
	}
	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}
	public String getMaterialId() {
		return materialId;
	}
	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}
	public String getTxtContent() {
		return txtContent;
	}
	public void setTxtContent(String txtContent) {
		this.txtContent = txtContent;
	}
	public String getWords() {
		return words;
	}
	public void setWords(String words) {
		this.words = words;
	}

}