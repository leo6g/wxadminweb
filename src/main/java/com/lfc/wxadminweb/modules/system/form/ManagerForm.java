package com.lfc.wxadminweb.modules.system.form;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import com.lfc.wxadminweb.common.beanvalidator.ILoginCheck;
import com.lfc.wxadminweb.common.beanvalidator.IUserInfoCheck;
import com.lfc.wxadminweb.common.form.BaseForm;

public class ManagerForm extends BaseForm implements Serializable {
	
	private static final long serialVersionUID = 561239441333041132L;

	//主键ID
	private String id;

	//用户名
	@NotBlank(message = "用户名不能为空",groups={ILoginCheck.class})
	private String userName;
	
	//密码
	@NotBlank(message = "密码不能为空",groups={ILoginCheck.class})
	@Pattern(regexp="(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{9,20}",message="密码必须由9~20位数字和大、小写字母的组成",groups={IUserInfoCheck.class})
	private String passWord;
	
	//状态：1：正常；-1：冻结；
	private Integer status;
	
	//有效开始时间
	//@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private String validBeginTime;

	//有效截止时间
	//@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private String validEndTime;

	//逻辑删除标志；0-正常；-1-删除；
    private BigDecimal deleteFlag;

    //创建时间
    private Date createTime;

    //最后更新时间
    private Date updateTime;
	
    //真实姓名
    @NotBlank(message = "真实姓名不能为空",groups={IUserInfoCheck.class})
	private String realName;
	
	//性别:  0:男；1：女
    @NotNull(message="性别不能为空",groups={IUserInfoCheck.class})
	private Integer sex;
	
	//出生日期
	//@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private String birthday;
	
	//邮箱地址
	private String email;
	
	//联系方式
	private String telPhone;
	
	//用户ID
	private String userId;
	
	//验证码（登录时验证参数）
	private String validateCode;

	//职位ID（新增和编辑页面参数）
	//@NotBlank(message = "职位不能为空",groups={IUserInfoCheck.class})
	private String positionId;
	
	//部门ID（新增和编辑页面参数）
	@NotBlank(message = "部门不能为空",groups={IUserInfoCheck.class})
	private String departId;
	
	//创建开始时间（查询参数）
	private String createTimeBegin;
	
	//创建截止时间（查询参数）
	private String createTimeEnd;
	
	//用户手机号码
	private String phoneNumber;
	
	//微信openId
	private String openId;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}


	public BigDecimal getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(BigDecimal deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	public String getDepartId() {
		return departId;
	}

	public void setDepartId(String departId) {
		this.departId = departId;
	}

	public String getCreateTimeBegin() {
		return createTimeBegin;
	}

	public void setCreateTimeBegin(String createTimeBegin) {
		this.createTimeBegin = createTimeBegin;
	}

	public String getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public String getValidBeginTime() {
		return validBeginTime;
	}

	public void setValidBeginTime(String validBeginTime) {
		this.validBeginTime = validBeginTime;
	}

	public String getValidEndTime() {
		return validEndTime;
	}

	public void setValidEndTime(String validEndTime) {
		this.validEndTime = validEndTime;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
}
