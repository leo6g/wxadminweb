package com.lfc.wxadminweb.modules.system.form;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

import com.lfc.wxadminweb.common.form.BaseForm;

public class RoleForm extends BaseForm implements java.io.Serializable{

	/*
	字段注释：角色编码
	列名称:ROLE_CODE
	字段类型:VARCHAR2*/
	@NotBlank(message = "角色编码不能为空")
	private String roleCode;
	/*
	字段注释：角色名称
	列名称:ROLE_NAME
	字段类型:VARCHAR2*/
	@NotBlank(message = "角色名称不能为空")
	private String roleName;
	/*
	字段注释：ID
	列名称:ID
	字段类型:VARCHAR2*/
	private String id;
	/*
	字段注释：最后编辑时间
	列名称:UPDATE_TIME
	字段类型:DATE*/
	private Date updateTime;
	/*
	字段注释：创建时间
	列名称:CREATE_TIME
	字段类型:DATE*/
	private Date createTime;
	/*
	字段注释：逻辑删除标志；0-正常；-1：删除；
	列名称:DELETE_FLAG
	字段类型:NUMBER*/
	private Integer deleteFlag;
	//开始时间
	private String beginTime;
	//结束时间
    private String endTime;
	//部门ID
    private String departId;
	//角色用户实体
    private List<ManagerForm> userId;
	public void setRoleCode(String roleCode){
		this.roleCode = roleCode;
	}

	public String getRoleCode(){
		return roleCode;
	}

	public void setRoleName(String roleName){
		this.roleName = roleName;
	}

	public String getRoleName(){
		return roleName;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setUpdateTime(Date updateTime){
		this.updateTime = updateTime;
	}

	public Date getUpdateTime(){
		return updateTime;
	}

	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

	public Date getCreateTime(){
		return createTime;
	}

	public void setDeleteFlag(Integer deleteFlag){
		this.deleteFlag = deleteFlag;
	}

	public Integer getDeleteFlag(){
		return deleteFlag;
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

	public String getDepartId() {
		return departId;
	}

	public void setDepartId(String departId) {
		this.departId = departId;
	}

	public List<ManagerForm> getUserId() {
		return userId;
	}

	public void setUserId(List<ManagerForm> userId) {
		this.userId = userId;
	}
}