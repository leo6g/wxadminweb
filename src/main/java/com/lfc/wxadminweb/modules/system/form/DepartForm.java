package com.lfc.wxadminweb.modules.system.form;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

public class DepartForm {
	
	//ID
    private String id = null;

    //组织机构名称
    @NotBlank(message = "机构名称不能为空")
    private String departName = null;

    //组织机构编码
    @NotBlank(message = "机构编码不能为空")
    private String departCode = null;

    //父ID
    @NotEmpty(message = "父级ID不能为空")
    private String parentId = null;

    //描述或备注
    private String descn = null;

    //顺序
    private BigDecimal departOrder = null;

    //逻辑删除标志；0-正常；-1-删除；
    private BigDecimal deleteFlag = null;

    //创建时间
    private Date createTime = null;

    //最后更新时间
    private Date updateTime = null;

    //机构级别ID
//    @NotEmpty(message = "机构级别不能为空")
//    private String levelId = null;
//    
    //机构级别(2: 一级分行|3:二级分行|4:一级支行|5:二级支行)
    @NotEmpty(message = "机构级别不能为空")
    private String levelRank;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public String getDepartCode() {
        return departCode;
    }

    public void setDepartCode(String departCode) {
        this.departCode = departCode;
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

    public BigDecimal getDepartOrder() {
        return departOrder;
    }

    public void setDepartOrder(BigDecimal departOrder) {
        this.departOrder = departOrder;
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

	public String getLevelRank() {
		return levelRank;
	}

	public void setLevelRank(String levelRank) {
		this.levelRank = levelRank;
	}
    
}