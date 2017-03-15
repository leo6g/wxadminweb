package com.lfc.wxadminweb.modules.biz.form;

import java.util.Date;
import org.hibernate.validator.constraints.NotEmpty;
import com.lfc.wxadminweb.common.form.BaseForm;
public class WXCardInfoForm extends BaseForm{

	/*
	字段注释：卡名称
	列名称:NAME
	字段类型:VARCHAR2*/
	private String name;
	/*
	字段注释：信用卡类型
	列名称:TYPE
	字段类型:VARCHAR2*/
	private String type;
	/*
	字段注释：删除标识
	列名称:DELETE_FLAG
	字段类型:NUMBER*/
	private Integer deleteFlag;
	/*
	字段注释：主键ID
	列名称:CARD_ID
	字段类型:VARCHAR2*/
	private String cardId;
	/*
	字段注释：审批意见
	列名称:COMMENTS
	字段类型:VARCHAR2*/
	private String comments;
	/*
	字段注释：产品状态(onSale:上架|offSale:下架)
	列名称:STATE
	字段类型:VARCHAR2*/
	private String state;
	/*
	字段注释：审核状态
	（DRAFT：草稿，1-WAITING：一级通过，
	1-REJECTED：二级驳回，2-WAITING：二级通过，
	2-REJECTED：三级驳回，COMPLETED：三级通过）
	列名称:AUTH_STATE
	字段类型:VARCHAR2*/
	private String authState;
	/*
	字段注释：图片路径
	列名称:IMAGE_PATH
	字段类型:VARCHAR2*/
	private String imagePath;
	/*
	字段注释：创建时间
	列名称:CREATE_TIME
	字段类型:DATE*/
	private Date createTime;
	/*
	字段注释：创建人
	列名称:CREATE_USER
	字段类型:VARCHAR2*/
	private String createUser;
	/*
	字段注释：是否热门推荐
	列名称:HOT_FLAG
	字段类型:VARCHAR2*/
	private String hotFlag;
	
	//开始时间
	private String beginTime;
	//结束时间
    private String endTime;
	//角色
    private String role;
    
    /**
     * 信用卡权益信息
     */
    private String rights;
	
	public String getRights() {
		return rights;
	}

	public void setRights(String rights) {
		this.rights = rights;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setDeleteFlag(Integer deleteFlag){
		this.deleteFlag = deleteFlag;
	}

	public Integer getDeleteFlag(){
		return deleteFlag;
	}

	public void setCardId(String cardId){
		this.cardId = cardId;
	}

	public String getCardId(){
		return cardId;
	}

	public void setComments(String comments){
		this.comments = comments;
	}

	public String getComments(){
		return comments;
	}

	public void setState(String state){
		this.state = state;
	}

	public String getState(){
		return state;
	}

	public void setAuthState(String authState){
		this.authState = authState;
	}

	public String getAuthState(){
		return authState;
	}

	public void setImagePath(String imagePath){
		this.imagePath = imagePath;
	}

	public String getImagePath(){
		return imagePath;
	}

	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

	public Date getCreateTime(){
		return createTime;
	}

	public void setCreateUser(String createUser){
		this.createUser = createUser;
	}

	public String getCreateUser(){
		return createUser;
	}

	public String getHotFlag() {
		return hotFlag;
	}

	public void setHotFlag(String hotFlag) {
		this.hotFlag = hotFlag;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	
}