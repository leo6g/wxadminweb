package com.lfc.wxadminweb.modules.biz.form;

import java.util.Date;
import org.hibernate.validator.constraints.NotEmpty;
import com.lfc.wxadminweb.common.form.BaseForm;
public class MerchApplierForm extends BaseForm{

	/*
	字段注释：申请人名称
	列名称:APPLIER_NAME
	字段类型:VARCHAR2*/
	private String applierName;
	/*
	字段注释：商户名称
	列名称:SHOP_NAME
	字段类型:VARCHAR2*/
	private String shopName;
	/*
	字段注释：删除标识(T:删除|F:未删除)
	列名称:DELETE_FLAG
	字段类型:VARCHAR2*/
	private String deleteFlag;
	/*
	字段注释：null
	列名称:ID
	字段类型:VARCHAR2*/
	private String id;
	/*
	字段注释：处理状态
	列名称:PROCESS_STATE
	字段类型:VARCHAR2*/
	private String processState;
	/*
	字段注释：商户所属地区
	列名称:BELONG_CITY
	字段类型:VARCHAR2*/
	private String belongCity;
	/*
	字段注释：申请商户类型
	列名称:MERCH_TYPE
	字段类型:VARCHAR2*/
	private String merchType;
	/*
	字段注释：申请人手机号码
	列名称:APPLIER_PHONE
	字段类型:VARCHAR2*/
	private String applierPhone;
	/*
	字段注释：创建时间
	列名称:CREATE_TIME
	字段类型:DATE*/
	private Date createTime;
	/*
	字段注释：处理备注
	列名称:PROCESS_REMARK
	字段类型:VARCHAR2*/
	private String processRemark;
	/*
	字段注释：处理结果
	列名称:PROCESS_RESULT
	字段类型:VARCHAR2*/
	private String processResult;
//	private String dicName;
	/*
	字段注释：申请人OPEN_ID
	列名称:OPEN_ID
	字段类型:VARCHAR2*/
	private String openId;
	/*
	字段注释：任务接收人
	列名称:TASKER_ID
	字段类型:VARCHAR2*/
	private String taskerId;
	
	//用户--机构编码
	private String departCode;
	//用户--角色编码
	private String roleCode;
	
	private String type;
	
	private String cityName;
	
	
	
	public String getProcessRemark() {
		return processRemark;
	}

	public void setProcessRemark(String processRemark) {
		this.processRemark = processRemark;
	}

	public String getProcessResult() {
		return processResult;
	}

	public void setProcessResult(String processResult) {
		this.processResult = processResult;
	}

	public String getTaskerId() {
		return taskerId;
	}

	public void setTaskerId(String taskerId) {
		this.taskerId = taskerId;
	}

	public String getDepartCode() {
		return departCode;
	}

	public void setDepartCode(String departCode) {
		this.departCode = departCode;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public void setApplierName(String applierName){
		this.applierName = applierName;
	}

	public String getApplierName(){
		return applierName;
	}

	public void setShopName(String shopName){
		this.shopName = shopName;
	}

	public String getShopName(){
		return shopName;
	}

	public void setDeleteFlag(String deleteFlag){
		this.deleteFlag = deleteFlag;
	}

	public String getDeleteFlag(){
		return deleteFlag;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setProcessState(String processState){
		this.processState = processState;
	}

	public String getProcessState(){
		return processState;
	}

	public void setBelongCity(String belongCity){
		this.belongCity = belongCity;
	}

	public String getBelongCity(){
		return belongCity;
	}

	public void setMerchType(String merchType){
		this.merchType = merchType;
	}

	public String getMerchType(){
		return merchType;
	}

	public void setApplierPhone(String applierPhone){
		this.applierPhone = applierPhone;
	}

	public String getApplierPhone(){
		return applierPhone;
	}

	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

	public Date getCreateTime(){
		return createTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	
}