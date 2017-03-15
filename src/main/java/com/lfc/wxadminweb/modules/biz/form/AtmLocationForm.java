package com.lfc.wxadminweb.modules.biz.form;

import java.math.BigDecimal;
import java.util.Date;

import com.lfc.wxadminweb.common.form.BaseForm;

public class AtmLocationForm extends BaseForm {
	
	//主键ID
    private String atmId;

    //位置信息
    private String address;

    //地址类型-字典ID，数据字典组编码：atmAddrType
    private String addrType;
    
    //地址类型名称
    private String addrTypeName;

    //设备种类-字典ID，数据字典组编码：atmType
    private String type;

    //设备种类名称
    private String typeName;
    
    //设备状态（0：正常，1：维修，-1：故障）
    private Integer state;

    //管辖网点ID
    private String netPointId;

    //经度
    private BigDecimal longitude;

    //纬度
    private BigDecimal latitude;

    //图片路径
    private String imagePath;

    //联系电话
    private String tactorPhone;

    //备注
    private String comments;

    //创建人
    private String createUser;

    //创建时间
    private Date createTime;

    //逻辑删除标志；0-正常；-1-删除
    private Integer deleteFlag;
    
    //所在市ID
    private String city;
    
    //所在市名称
    private String cityName;
    
    //一级支行ID
    private String town;
    
    //一级支行名称
    private String townName;
    
    //管辖网点ID
    private String orgShtId;
    
    //管辖网点名称
    private String orgShtName;
    
	public String getAtmId() {
		return atmId;
	}

	public void setAtmId(String atmId) {
		this.atmId = atmId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddrType() {
		return addrType;
	}

	public void setAddrType(String addrType) {
		this.addrType = addrType;
	}

	public String getAddrTypeName() {
		return addrTypeName;
	}

	public void setAddrTypeName(String addrTypeName) {
		this.addrTypeName = addrTypeName;
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

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getNetPointId() {
		return netPointId;
	}

	public void setNetPointId(String netPointId) {
		this.netPointId = netPointId;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getTactorPhone() {
		return tactorPhone;
	}

	public void setTactorPhone(String tactorPhone) {
		this.tactorPhone = tactorPhone;
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getTownName() {
		return townName;
	}

	public void setTownName(String townName) {
		this.townName = townName;
	}

	public String getOrgShtId() {
		return orgShtId;
	}

	public void setOrgShtId(String orgShtId) {
		this.orgShtId = orgShtId;
	}

	public String getOrgShtName() {
		return orgShtName;
	}

	public void setOrgShtName(String orgShtName) {
		this.orgShtName = orgShtName;
	}

}