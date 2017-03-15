package com.lfc.wxadminweb.modules.biz.form;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import org.hibernate.validator.constraints.NotEmpty;
import com.lfc.wxadminweb.common.form.BaseForm;
public class CardApplierForm extends BaseForm{

	/*
	字段注释：身份证号码
	列名称:ID_NUMBER
	字段类型:VARCHAR2*/
	private String idNumber;
	/*
	字段注释：姓名
	列名称:NAME
	字段类型:VARCHAR2*/
	private String name;
	/*
	字段注释：APPLIER_ID
	列名称:APPLIER_ID
	字段类型:VARCHAR2*/
	private String applierId;
	/*
	字段注释：删除标识
	列名称:DELETE_FLAG
	字段类型:NUMBER*/
	private Integer deleteFlag;
	/*
	字段注释：申请时间
	列名称:APPLY_TIME
	字段类型:DATE*/
	private Date applyTime;
	/*
	字段注释：null
	列名称:NET_POINT_ID
	字段类型:VARCHAR2*/
	private String netPointId;
	/*
	字段注释：手机号码
	列名称:CELL_NUMBER
	字段类型:VARCHAR2*/
	private String cellNumber;
	//开始时间
	private String beginTime;
	//结束时间
	private String endTime;
	//二分ID
	private String cityId;
	//一支ID
	private String townId;
	//二支ID
	private String countryId;
	
	/*
	字段注释：申请人微信ID
	列名称:OPEN_ID
	字段类型:VARCHAR2*/
	private String openId;
	/*
	字段注释：申请类型
	列名称:APPLY_TYPE
	字段类型:VARCHAR2*/
	private String applyType;
	/*
	字段注释：贷款产品ID
	列名称:FINC_ID
	字段类型:VARCHAR2*/
	private String fincId;
	/*
	字段注释：贷款用途
	列名称:USEAGE
	字段类型:VARCHAR2*/
	private String useage;
	/*
	字段注释：贷款金额
	列名称:LOAN_AMOUNT
	字段类型:VARCHAR2*/
	private String loanAmount;
	/*
	字段注释：处理状态
	列名称:PROCESS_STATE
	字段类型:VARCHAR2*/
	private String processState;
	/*
	字段注释：处理结果
	列名称:PROCESS_RESULT
	字段类型:VARCHAR2*/
	private String processResult;
	/*
	字段注释：处理结果
	列名称:PROCESS_RESULT
	字段类型:VARCHAR2*/
	private String processRemark;
	/*
	字段注释：任务接收人
	列名称:TASKER_ID
	字段类型:VARCHAR2*/
	private String taskerId;
	
	private String departCode;
	private String roleCode;
	//信用卡名称
	private String cardName;
	//信用卡类型
	private String cardType;
	//接收人
	private String realName;
	//贷款产品
	private String loanName;
	
	public String getProcessRemark() {
		return processRemark;
	}

	public void setProcessRemark(String processRemark) {
		this.processRemark = processRemark;
	}

	public void setIdNumber(String idNumber){
		this.idNumber = idNumber;
	}

	public String getIdNumber(){
		return idNumber;
	}

	public void setName(String name) throws UnsupportedEncodingException{
		this.name = URLDecoder.decode(name,"utf-8");
	}

	public String getName(){
		return name;
	}

	public void setApplierId(String applierId){
		this.applierId = applierId;
	}

	public String getApplierId(){
		return applierId;
	}

	public void setDeleteFlag(Integer deleteFlag){
		this.deleteFlag = deleteFlag;
	}

	public Integer getDeleteFlag(){
		return deleteFlag;
	}

	public void setApplyTime(Date applyTime){
		this.applyTime = applyTime;
	}

	public Date getApplyTime(){
		return applyTime;
	}

	public void setNetPointId(String netPointId){
		this.netPointId = netPointId;
	}

	public String getNetPointId(){
		return netPointId;
	}

	public void setCellNumber(String cellNumber){
		this.cellNumber = cellNumber;
	}

	public String getCellNumber(){
		return cellNumber;
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

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getTownId() {
		return townId;
	}

	public void setTownId(String townId) {
		this.townId = townId;
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getApplyType() {
		return applyType;
	}

	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}

	public String getFincId() {
		return fincId;
	}

	public void setFincId(String fincId) {
		this.fincId = fincId;
	}

	public String getUseage() {
		return useage;
	}

	public void setUseage(String useage) {
		this.useage = useage;
	}

	public String getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}

	public String getProcessState() {
		return processState;
	}

	public void setProcessState(String processState) {
		this.processState = processState;
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

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getLoanName() {
		return loanName;
	}

	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}

	
}