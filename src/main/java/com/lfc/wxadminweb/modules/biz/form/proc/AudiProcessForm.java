package com.lfc.wxadminweb.modules.biz.form.proc;

import java.util.Date;
import org.hibernate.validator.constraints.NotEmpty;
import com.lfc.wxadminweb.common.form.BaseForm;
public class AudiProcessForm extends BaseForm{

	/*
	字段注释：流程标题
	列名称:TITLE
	字段类型:VARCHAR2*/
	private String title;
	/*
	字段注释：图文素材ID
	列名称:MATERIAL_ID
	字段类型:VARCHAR2*/
	private String materialId;
	/*
	字段注释：流程等级(city:地市发起|province:省分行发起)
	列名称:DEPART_LEVEL
	字段类型:VARCHAR2*/
	private String departLevel;
	/*
	字段注释：主键ID
	列名称:PROCESS_ID
	字段类型:VARCHAR2*/
	private String processId;
	/*
	字段注释：当前节点状态
	列名称:CURRENT_NODE
	字段类型:VARCHAR2*/
	private String currentNode;
	/*
	字段注释：流程状态(running:在程|closed:已关闭)
	列名称:STATE
	字段类型:VARCHAR2*/
	private String state;
	/*
	字段注释：流程发起时间
	列名称:CREATE_TIME
	字段类型:DATE*/
	private Date createTime;
	/*
	字段注释：流程发起人(关联T_S_USER 表ID)
	列名称:INITIATOR_ID
	字段类型:VARCHAR2*/
	private String initiatorId;
	/*
	字段注释：部门业务类型(credit:信用卡部业务|loan:信贷部业务|ebank:电子银行部业务)
	列名称:DEPART_TYPE
	字段类型:VARCHAR2*/
	private String departType;
	/*
	字段注释：发起部门编码
	列名称:INIT_DEPART_CODE
	字段类型:VARCHAR2*/
	private String initDepartCode;
	/*
	字段注释：推荐类型：当流程业务类型是hotpoint时， 该字段有值 (loan:贷款|metal:贵金属|finance:理财)
	列名称:RECOMMEND_TYPE
	字段类型:VARCHAR2*/
	private String recommendType;
	/*
	字段注释：推文发布日期
	列名称:PUBLISH_DATE
	字段类型:DATE*/
	private String publishDate;
	/*
	字段注释：流程启动部门编码
	列名称:DEPART_CODE
	字段类型:VARCHAR2*/
	private String departCode;
	/*
	字段注释：流程业务类型(activity:热点导航活动|article:每周推文|hotpoint:热门推荐)
	列名称:BIZ_TYPE
	字段类型:VARCHAR2*/
	private String bizType;
	
	//开始时间
	private String beginTime;
	//结束时间
	private String endTime;
	
	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setMaterialId(String materialId){
		this.materialId = materialId;
	}

	public String getMaterialId(){
		return materialId;
	}

	public void setDepartLevel(String departLevel){
		this.departLevel = departLevel;
	}

	public String getDepartLevel(){
		return departLevel;
	}

	public void setProcessId(String processId){
		this.processId = processId;
	}

	public String getProcessId(){
		return processId;
	}

	public void setCurrentNode(String currentNode){
		this.currentNode = currentNode;
	}

	public String getCurrentNode(){
		return currentNode;
	}

	public void setState(String state){
		this.state = state;
	}

	public String getState(){
		return state;
	}

	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

	public Date getCreateTime(){
		return createTime;
	}

	public void setInitiatorId(String initiatorId){
		this.initiatorId = initiatorId;
	}

	public String getInitiatorId(){
		return initiatorId;
	}

	public void setDepartType(String departType){
		this.departType = departType;
	}

	public String getDepartType(){
		return departType;
	}

	public void setInitDepartCode(String initDepartCode){
		this.initDepartCode = initDepartCode;
	}

	public String getInitDepartCode(){
		return initDepartCode;
	}

	public void setRecommendType(String recommendType){
		this.recommendType = recommendType;
	}

	public String getRecommendType(){
		return recommendType;
	}

	public void setPublishDate(String publishDate){
		this.publishDate = publishDate;
	}

	public String getPublishDate(){
		return publishDate;
	}

	public void setDepartCode(String departCode){
		this.departCode = departCode;
	}

	public String getDepartCode(){
		return departCode;
	}

	public void setBizType(String bizType){
		this.bizType = bizType;
	}

	public String getBizType(){
		return bizType;
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