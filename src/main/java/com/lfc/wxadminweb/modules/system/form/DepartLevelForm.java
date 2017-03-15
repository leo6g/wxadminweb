package com.lfc.wxadminweb.modules.system.form;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

import com.lfc.wxadminweb.common.form.BaseForm;

public class DepartLevelForm extends BaseForm{
	
	//主键ID
    private String id;

    //级别名称
    @NotBlank(message = "级别名称不能为空")
    private String levName;

    //级别编码
    @NotBlank(message = "级别编码不能为空")
    private String levCode;

    //描述或备注
    private String descn;

    //顺序
    private BigDecimal levOrder;

    //逻辑删除标志；0-正常；-1-删除；
    private BigDecimal deleteFlag;

    //创建时间
    private Date createTime;

    //最后更新时间
    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLevName() {
        return levName;
    }

    public void setLevName(String levName) {
        this.levName = levName;
    }

    public String getLevCode() {
        return levCode;
    }

    public void setLevCode(String levCode) {
        this.levCode = levCode;
    }

    public String getDescn() {
        return descn;
    }

    public void setDescn(String descn) {
        this.descn = descn;
    }

    public BigDecimal getLevOrder() {
        return levOrder;
    }

    public void setLevOrder(BigDecimal levOrder) {
        this.levOrder = levOrder;
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
}