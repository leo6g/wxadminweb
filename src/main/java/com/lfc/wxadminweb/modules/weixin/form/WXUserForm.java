package com.lfc.wxadminweb.modules.weixin.form;

import java.util.Date;
import com.lfc.wxadminweb.common.form.BaseForm;

public class WXUserForm extends BaseForm {
	/*
	字段注释：名称
	列名称:NICK_NAME
	字段类型:VARCHAR2*/
	private String nickName;
	/*
	字段注释：备注名称
	列名称:REMARK
	字段类型:VARCHAR2*/
	private String remark;
	/*
	字段注释：微信用户ID
	列名称:OPEN_ID
	字段类型:VARCHAR2*/
	private String openId;
	/*
	 * 字段注释：微信用户性别 列名称: GENDER 字段类型:VARCHAR2
	 */
	private String gender;
	/*
	 * 字段注释：删除标识 列名称:DELETE_FLAG 字段类型:NUMBER
	 */
	private Integer deleteFlag;
	/*
	 * 字段注释：主键ID 列名称:WX_USER_ID 字段类型:VARCHAR2
	 */
	private String wxUserId;
	/*
	 * 字段注释：null 列名称:HEADER_IMAGE 字段类型:VARCHAR2
	 */
	private String headerImage;
	/*
	 * 字段注释：微信用户分组ID 列名称:GROUP_ID 字段类型:VARCHAR2
	 */
	private String groupId;
	/*
	 * 字段注释：取消关注时间 列名称:UNSUBSCRIBE_TIME 字段类型:DATE
	 */
	private Date unsubscribeTime;
	/*
	 * 字段注释：关注时间 列名称:SUBSCRIBE_TIME 字段类型:DATE
	 */
	private Date subscribeTime;
	/*
	 * 字段注释：创建时间 列名称:CREATE_TIME 字段类型:DATE
	 */
	private Date createTime;
	/*
	 * 字段注释：创建人 列名称:CREATE_USER 字段类型:VARCHAR2
	 */
	private String createUser;

	/*
	 * 字段注释：交易密码 列名称:DEAL_PWD 字段类型:VARCHAR2
	 */
	private String dealPwd;
	/*
	 * 字段注释：手机号码 列名称:PHONE_NUMBER 字段类型:VARCHAR2
	 */
	private String phoneNumber;
	/*
	 * 字段注释：住址 列名称:ADDRESS 字段类型:VARCHAR2
	 */
	private String address;
	/*
	 * 字段注释：姓名 列名称:NAME 字段类型:VARCHAR2
	 */
	private String name;
	/*
	 * 字段注释：身份证号 列名称:ID_NUMBER 字段类型:VARCHAR2
	 */
	private String idNumber;
	/*
	 * 字段注释：自己定义的头像的图片路径 列名称:SELF_IMG 字段类型:VARCHAR2
	 */
	private String selfImg;

	/*
	 * 是否关注
	 */
	private Integer subscribe;

	public Integer getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(Integer subscribe) {
		this.subscribe = subscribe;
	}

	public String getDealPwd() {
		return dealPwd;
	}

	public void setDealPwd(String dealPwd) {
		this.dealPwd = dealPwd;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getSelfImg() {
		return selfImg;
	}

	public void setSelfImg(String selfImg) {
		this.selfImg = selfImg;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setWxUserId(String wxUserId) {
		this.wxUserId = wxUserId;
	}

	public String getWxUserId() {
		return wxUserId;
	}

	public void setHeaderImage(String headerImage) {
		this.headerImage = headerImage;
	}

	public String getHeaderImage() {
		return headerImage;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setUnsubscribeTime(Date unsubscribeTime) {
		this.unsubscribeTime = unsubscribeTime;
	}

	public Date getUnsubscribeTime() {
		return unsubscribeTime;
	}

	public void setSubscribeTime(Date subscribeTime) {
		this.subscribeTime = subscribeTime;
	}

	public Date getSubscribeTime() {
		return subscribeTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCreateUser() {
		return createUser;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}