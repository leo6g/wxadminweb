package com.lfc.wxadminweb.modules.system.form;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

public class ButtonForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 561239441333041132L;
    //主键
	private String id;
	//按钮名称
	@NotBlank(message = "按钮名称不能为空")
	private String butName;
	//按钮标识-关键字
	@NotBlank(message = "按钮编码不能为空")
	private String butCode;
	//按钮图标
    private String butIcon;
    //按钮URl
    @NotBlank(message = "按钮路径不能为空")
	private String url;
	//菜单ID-外键
	private String menuId;
	//按钮的创建时间
	private Date createTime;
	//按钮最后一次更新时间
	private Date updateTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getButName() {
		return butName;
	}
	public void setButName(String butName) {
		this.butName = butName;
	}
	public String getButCode() {
		return butCode;
	}
	public void setButCode(String butCode) {
		this.butCode = butCode;
	}
	public String getButIcon() {
		return butIcon;
	}
	public void setButIcon(String butIcon) {
		this.butIcon = butIcon;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
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



	
}
