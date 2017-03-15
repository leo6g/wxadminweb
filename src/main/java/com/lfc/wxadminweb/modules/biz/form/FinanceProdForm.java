package com.lfc.wxadminweb.modules.biz.form;

import java.util.Date;

import com.lfc.wxadminweb.common.form.BaseForm;
import com.lfc.wxadminweb.modules.weixin.form.WXNewsItemsForm;
public class FinanceProdForm extends BaseForm{

	/*
	字段注释：创建时间
	列名称:CREATE_TIME
	字段类型:DATE*/
	private Date createTime;
	/*
	字段注释：删除标识（0：正常，-1：删除）
	列名称:DELETE_FLAG
	字段类型:NUMBER*/
	private Integer deleteFlag;
	/*
	字段注释：备注
	列名称:COMMENTS
	字段类型:VARCHAR2*/
	private String comments;
	/*
	字段注释：创建人
	列名称:CREATE_USER
	字段类型:VARCHAR2*/
	private String createUser;
	/*
	字段注释：审核状态
	（DRAFT：草稿，1-WAITING：一级通过，
	1-REJECTED：二级驳回，2-WAITING：二级通过，
	2-REJECTED：三级驳回，COMPLETED：三级通过）
	列名称:AUTH_STATE
	字段类型:VARCHAR2*/
	private String authState;
	/*
	字段注释：产品状态(onSale:上架|offSale:下架)
	列名称:STATE
	字段类型:VARCHAR2*/
	private String state;
	/*
	字段注释：名称
	列名称:NAME
	字段类型:VARCHAR2*/
	private String name;
	/*
	字段注释：产品类型-字典ID，数据字典组编码：ficProdType
	列名称:TYPE
	字段类型:VARCHAR2*/
	private String type;
	
	/*
	*字段注释：产品类型名称
	*/
	private String typeName;
	
	/*
	字段注释：主键ID
	列名称:FINC_ID
	字段类型:VARCHAR2*/
	private String fincId;
	/*
	字段注释：理财期限-字典ID，数据字典组编码：ficPeriodType
	列名称:PERIOD_TYPE
	字段类型:VARCHAR2*/
	private String periodType;
	
	/**
	 * 理财期限名称
	 */
	private String periodTypeName;
	/*
	字段注释：产品代码
	列名称:PROD_CODE
	字段类型:VARCHAR2*/
	private String prodCode;
	/*
	字段注释：图片路径
	列名称:IMAGE_PATH
	字段类型:VARCHAR2*/
	private String imagePath;
	/*
	字段注释：销售渠道-字典ID，数据字典组编码：ficSaleChannel
	列名称:SALE_CHANNEL
	字段类型:VARCHAR2*/
	private String saleChannel;
	
	/**
	 * 操作者角色
	 */
	private String role;
	/*
	字段注释：扩展字段1
	列名称:EXT_STR1
	字段类型:VARCHAR2*/
	private String extStr1;
	/*
	字段注释：扩展字段2
	列名称:EXT_STR2
	字段类型:VARCHAR2*/
	private String extStr2;
	/*
	字段注释： 是否热门理财产品      T:是    F:否     zhaoyan 添加      
	列名称:EXT_STR3
	字段类型:VARCHAR2*/
	private String extStr3;
	/*
	字段注释：产品子类型
	列名称:SUB_TYPE
	字段类型:VARCHAR2*/
	private String subType;
	/*
	字段注释：null
	列名称:ARTICLE_ID
	字段类型:VARCHAR2*/
	private String articleId;
	//开始时间
	private String beginTime;
	//结束时间
    private String endTime;
    //关联文章(实体)
    private WXNewsItemsForm itemForm;
    /*
	字段注释：分类类型
	列名称:CATEGORY
	字段类型:VARCHAR2*/
	private String category;
	
	private String loanType;
	
	private Integer sort;
    
	public String getLoanType() {
		return loanType;
	}
	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}
	
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public WXNewsItemsForm getItemForm() {
		return itemForm;
	}
	public void setItemForm(WXNewsItemsForm itemForm) {
		this.itemForm = itemForm;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getAuthState() {
		return authState;
	}
	public void setAuthState(String authState) {
		this.authState = authState;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getFincId() {
		return fincId;
	}
	public void setFincId(String fincId) {
		this.fincId = fincId;
	}
	public String getPeriodType() {
		return periodType;
	}
	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}
	public String getPeriodTypeName() {
		return periodTypeName;
	}
	public void setPeriodTypeName(String periodTypeName) {
		this.periodTypeName = periodTypeName;
	}
	public String getProdCode() {
		return prodCode;
	}
	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getSaleChannel() {
		return saleChannel;
	}
	public void setSaleChannel(String saleChannel) {
		this.saleChannel = saleChannel;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getExtStr1() {
		return extStr1;
	}
	public void setExtStr1(String extStr1) {
		this.extStr1 = extStr1;
	}
	public String getExtStr2() {
		return extStr2;
	}
	public void setExtStr2(String extStr2) {
		this.extStr2 = extStr2;
	}
	public String getExtStr3() {
		return extStr3;
	}
	public void setExtStr3(String extStr3) {
		this.extStr3 = extStr3;
	}
	public String getSubType() {
		return subType;
	}
	public void setSubType(String subType) {
		this.subType = subType;
	}
	public String getArticleId() {
		return articleId;
	}
	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}