package com.lfc.wxadminweb.modules.biz.form;

import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

import com.lfc.wxadminweb.common.form.BaseForm;
public class MerchantForm extends BaseForm implements java.io.Serializable{

	/*
	字段注释：删除标识
	列名称:DELETE_FLAG
	字段类型:NUMBER*/
	private Integer deleteFlag;
	/*
	字段注释：创建人
	列名称:CREATE_USER
	字段类型:VARCHAR2*/
	private String createUser;
	/*
	字段注释：创建时间
	列名称:CREATE_TIME
	字段类型:DATE*/
	private Date createTime;
	/*
	字段注释：商户地址
	列名称:ADDRESS
	字段类型:VARCHAR2*/
	private String address;
	/*
	字段注释：商户介绍
	列名称:INTRODUCTION
	字段类型:VARCHAR2*/
	private String introduction;
	/*
	字段注释：联系电话
	列名称:TACTOR_PHONE
	字段类型:VARCHAR2*/
	private String tactorPhone;
	/*
	字段注释：备注
	列名称:COMMENTS
	字段类型:VARCHAR2*/
	private String comments;
	/*
	字段注释：简称
	列名称:SHORT_NAME
	字段类型:VARCHAR2*/
	private String shortName;
	/*
	字段注释：名称
	列名称:NAME
	字段类型:VARCHAR2*/
	private String name;
	/*
	字段注释：null
	列名称:MERCHANT_ID
	字段类型:VARCHAR2*/
	private String merchantId;
	/*
	字段注释：图片路径
	列名称:IMAGE_PATH
	字段类型:VARCHAR2*/
	private String imagePath;
	/*
	字段注释：状态
	列名称:STATE
	字段类型:VARCHAR2*/
	private String state;
	/*
	字段注释：支持卡种
	列名称:SUPPORTED_CARDS
	字段类型:VARCHAR2*/
	private String supportedCards;
	/*
	字段注释：商户类型
	列名称:TYPE
	字段类型:VARCHAR2*/
	private String type;
	/*
	字段注释：商户编码
	列名称:CODE
	字段类型:VARCHAR2*/
	private String code;
	/*
	字段注释：纬度
	列名称:LATITUDE
	字段类型:NUMBER*/
	private Double latitude;
	/*
	字段注释：经度
	列名称:LONGITUDE
	字段类型:NUMBER*/
	private Double longitude;
	/*
	字段注释：活动名称
	列名称:ACTIVITY_NAME
	字段类型:VARCHAR*/
	private String activityName;
	/*
	字段注释：活动开始日期
	列名称:START_DATE
	字段类型:DATE*/
	private Date startDate;
	/*
	字段注释：活动结束日期
	列名称:END_DATE
	字段类型:DATE*/
	private Date endDate;
	
	/*
	字段注释：广告图片
	列名称:BANNER_IMG
	字段类型:VARCHAR*/
	private String bannerImg;
	
	/*
	字段注释：是否热门
	列名称:IS_HOT
	字段类型:VARCHAR*/
	private String isHot;
	
	/*
	字段注释：活动内容
	列名称:ACTIVITY_CONTENT
	字段类型:VARCHAR*/
	private String activityContent;
	/*
	字段注释：一分支行
	列名称:TOWN_CODE
	字段类型:VARCHAR*/
	private String twonCode;
	/*
	字段注释：二级分行机构号
	列名称:CITY_CODE
	字段类型:VARCHAR*/
	private String cityCode;
	/*
	字段注释：折扣详情
	列名称:DISACCOUNT
	字段类型:VARCHAR*/
	private String disaccount;
	
	private String id;

	private FileInputStream file;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTwonCode() {
		return twonCode;
	}

	public void setTwonCode(String twonCode) {
		this.twonCode = twonCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getDisaccount() {
		return disaccount;
	}

	public void setDisaccount(String disaccount) {
		this.disaccount = disaccount;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public void setDeleteFlag(Integer deleteFlag){
		this.deleteFlag = deleteFlag;
	}

	public Integer getDeleteFlag(){
		return deleteFlag;
	}

	public void setCreateUser(String createUser){
		this.createUser = createUser;
	}

	public String getCreateUser(){
		return createUser;
	}

	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

	public Date getCreateTime(){
		return createTime;
	}

	public void setTactorPhone(String tactorPhone){
		this.tactorPhone = tactorPhone;
	}

	public String getTactorPhone(){
		return tactorPhone;
	}

	public void setComments(String comments){
		this.comments = comments;
	}

	public String getComments(){
		return comments;
	}

	public void setShortName(String shortName){
		this.shortName = shortName;
	}

	public String getShortName(){
		return shortName;
	}

	public void setName(String name) throws UnsupportedEncodingException{
		this.name = URLDecoder.decode(name,"utf-8");
	}

	public String getName(){
		return name;
	}

	public void setMerchantId(String merchantId){
		this.merchantId = merchantId;
	}

	public String getMerchantId(){
		return merchantId;
	}

	public void setImagePath(String imagePath){
		this.imagePath = imagePath;
	}

	public String getImagePath(){
		return imagePath;
	}

	public void setState(String state){
		this.state = state;
	}

	public String getState(){
		return state;
	}

	public void setSupportedCards(String supportedCards){
		this.supportedCards = supportedCards;
	}

	public String getSupportedCards(){
		return supportedCards;
	}

	public void setType(String type) throws UnsupportedEncodingException{
		this.type = URLDecoder.decode(type,"utf-8");
	}

	public String getType(){
		return type;
	}

	public void setCode(String code){
		this.code = code;
	}

	public String getCode(){
		return code;
	}

	public void setLatitude(Double latitude){
		this.latitude = latitude;
	}

	public Double getLatitude(){
		return latitude;
	}

	public void setLongitude(Double longitude){
		this.longitude = longitude;
	}

	public Double getLongitude(){
		return longitude;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getActivityContent() {
		return activityContent;
	}

	public void setActivityContent(String activityContent) {
		this.activityContent = activityContent;
	}

	public FileInputStream getFile() {
		return file;
	}

	public void setFile(FileInputStream file) {
		this.file = file;
	}

	public String getBannerImg() {
		return bannerImg;
	}

	public void setBannerImg(String bannerImg) {
		this.bannerImg = bannerImg;
	}

	public String getIsHot() {
		return isHot;
	}

	public void setIsHot(String isHot) {
		this.isHot = isHot;
	}

	
}