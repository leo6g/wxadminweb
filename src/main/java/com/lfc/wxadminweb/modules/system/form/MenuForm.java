package com.lfc.wxadminweb.modules.system.form;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Min;
import org.hibernate.validator.constraints.NotBlank;

import com.lfc.wxadminweb.common.form.BaseForm;

public class MenuForm extends BaseForm implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 561239441333041132L;
    //主键
	private String id;
	//菜单名称
	@NotBlank(message = "菜单名称不能为空")
	private String menuName;
	//菜单URl
	private String menuUrl;
	//菜单图标
    private String menuIcon;
	//菜单类型
	private BigDecimal menuType;
	//菜单顺序
	private BigDecimal menuOrder;
	//菜单级别
	@Min(1)
	private BigDecimal menuLevel;
	//父菜单ID
	private String parentId;
	//备注
	private String descn;
	//菜单创建时间
	private Date createTime;
	//菜单最后编辑日期
	private Date updateTime;
	//开始时间
	private String beginTime;
	//结束时间
    private String endTime;
    //角色ID 
    private String roleId;

	

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public String getMenuIcon() {
		return menuIcon;
	}

	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}

	public BigDecimal getMenuType() {
		return menuType;
	}

	public void setMenuType(BigDecimal menuType) {
		this.menuType = menuType;
	}

	public BigDecimal getMenuOrder() {
		return menuOrder;
	}

	public void setMenuOrder(BigDecimal menuOrder) {
		this.menuOrder = menuOrder;
	}

	public BigDecimal getMenuLevel() {
		return menuLevel;
	}

	public void setMenuLevel(BigDecimal menuLevel) {
		this.menuLevel = menuLevel;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getDescn() {
		return descn;
	}

	public void setDescn(String descn) {
		this.descn = descn;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
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

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}



	
}
