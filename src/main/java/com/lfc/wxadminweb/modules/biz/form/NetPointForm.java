package com.lfc.wxadminweb.modules.biz.form;

import java.util.Date;
import org.hibernate.validator.constraints.NotEmpty;
import com.lfc.wxadminweb.common.form.BaseForm;
public class NetPointForm extends BaseForm{

	/*
	字段注释：纬度
	列名称:LATITUDE
	字段类型:NUMBER*/
	private String latitude;
	/*
	字段注释：null
	列名称:ORG_ABLIB
	字段类型:VARCHAR2*/
	private String orgAblib;
	/*
	字段注释：经度
	列名称:LONGITUDE
	字段类型:NUMBER*/
	private String longitude;
	/*
	字段注释：null
	列名称:AGENCY_SORT
	字段类型:NUMBER*/
	private Integer agencySort;
	/*
	字段注释：null
	列名称:ORG_ABLIA
	字段类型:VARCHAR2*/
	private String orgAblia;
	/*
	字段注释：城市
	列名称:CITY
	字段类型:VARCHAR2*/
	private String city;
	/*
	字段注释：县区
	列名称:TOWN
	字段类型:VARCHAR2*/
	private String town;
	/*
	字段注释：行政区划
	列名称:RGLM_CODE
	字段类型:VARCHAR2*/
	private String rglmCode;
	/*
	字段注释：地域属性(1-城市；2-县城；3-县以下)
	列名称:AREA_ATTR
	字段类型:VARCHAR2*/
	private String areaAttr;
	/*
	字段注释：机构简称
	列名称:ORG_SHT_NAME
	字段类型:VARCHAR2*/
	private String orgShtName;
	/*
	字段注释：图片路径
	列名称:IMAGE_PATH
	字段类型:VARCHAR2*/
	private String imagePath;
	/*
	字段注释：机构名称
	列名称:ORG_NAME
	字段类型:VARCHAR2*/
	private String orgName;
	/*
	字段注释：新机构编码
	列名称:NEW_ORG_ID
	字段类型:VARCHAR2*/
	private String newOrgId;
	/*
	字段注释：直属标识(1-否；2-总行直属支行；3-一级分行直属支行；4-二级分行直属支行)
	列名称:ORG_FLAG
	字段类型:VARCHAR2*/
	private String orgFlag;
	/*
	字段注释：机构职能(1-全功能 ; 2-交易型 ; 3-多功能)
	列名称:ORG_ABLI
	字段类型:VARCHAR2*/
	private String orgAbli;
	/*
	字段注释：机构层级(1-总行；2-一级分行；3-二级分行；4-一级支行；5-网点)
	列名称:ORG_LVL
	字段类型:VARCHAR2*/
	private String orgLvl;
	/*
	字段注释：老机构
	列名称:OLD_ORG_ID
	字段类型:VARCHAR2*/
	private String oldOrgId;
	/*
	字段注释：上级机构
	列名称:PAGENCY_ID
	字段类型:VARCHAR2*/
	private String pagencyId;
	/*
	字段注释：机构状态(1-待开业；2-已开业；3-已营业；4-临时停业;5-停业；6-待撤销;7-已撤销)
	列名称:ORG_STAT
	字段类型:VARCHAR2*/
	private String orgStat;
	/*
	字段注释：机构属性(1-一类；2-二类；3-代理。
)
	列名称:ORG_ATTR
	字段类型:VARCHAR2*/
	private String orgAttr;
	
	/*
	字段注释：地址
	列名称:ADDR
	字段类型:VARCHAR2*/
	private String addr;
	/*
	字段注释：办理业务种类
	列名称:BIZ_TYPES
	字段类型:VARCHAR2*/
	private String bizTypes;
	
	/*
	 * 电话
	 */
	private String phone;
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getBizTypes() {
		return bizTypes;
	}

	public void setBizTypes(String bizTypes) {
		this.bizTypes = bizTypes;
	}

	public void setOrgAblib(String orgAblib){
		this.orgAblib = orgAblib;
	}

	public String getOrgAblib(){
		return orgAblib;
	}
	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public void setAgencySort(Integer agencySort){
		this.agencySort = agencySort;
	}

	public Integer getAgencySort(){
		return agencySort;
	}

	public void setOrgAblia(String orgAblia){
		this.orgAblia = orgAblia;
	}

	public String getOrgAblia(){
		return orgAblia;
	}

	public void setCity(String city){
		this.city = city;
	}

	public String getCity(){
		return city;
	}

	public void setTown(String town){
		this.town = town;
	}

	public String getTown(){
		return town;
	}

	public void setRglmCode(String rglmCode){
		this.rglmCode = rglmCode;
	}

	public String getRglmCode(){
		return rglmCode;
	}

	public void setAreaAttr(String areaAttr){
		this.areaAttr = areaAttr;
	}

	public String getAreaAttr(){
		return areaAttr;
	}

	public void setOrgShtName(String orgShtName){
		this.orgShtName = orgShtName;
	}

	public String getOrgShtName(){
		return orgShtName;
	}

	public void setImagePath(String imagePath){
		this.imagePath = imagePath;
	}

	public String getImagePath(){
		return imagePath;
	}

	public void setOrgName(String orgName){
		this.orgName = orgName;
	}

	public String getOrgName(){
		return orgName;
	}

	public void setNewOrgId(String newOrgId){
		this.newOrgId = newOrgId;
	}

	public String getNewOrgId(){
		return newOrgId;
	}

	public void setOrgFlag(String orgFlag){
		this.orgFlag = orgFlag;
	}

	public String getOrgFlag(){
		return orgFlag;
	}

	public void setOrgAbli(String orgAbli){
		this.orgAbli = orgAbli;
	}

	public String getOrgAbli(){
		return orgAbli;
	}

	public void setOrgLvl(String orgLvl){
		this.orgLvl = orgLvl;
	}

	public String getOrgLvl(){
		return orgLvl;
	}

	public void setOldOrgId(String oldOrgId){
		this.oldOrgId = oldOrgId;
	}

	public String getOldOrgId(){
		return oldOrgId;
	}

	public void setPagencyId(String pagencyId){
		this.pagencyId = pagencyId;
	}

	public String getPagencyId(){
		return pagencyId;
	}

	public void setOrgStat(String orgStat){
		this.orgStat = orgStat;
	}

	public String getOrgStat(){
		return orgStat;
	}

	public void setOrgAttr(String orgAttr){
		this.orgAttr = orgAttr;
	}

	public String getOrgAttr(){
		return orgAttr;
	}

	
}