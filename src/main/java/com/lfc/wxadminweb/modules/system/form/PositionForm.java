package com.lfc.wxadminweb.modules.system.form;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

import com.lfc.wxadminweb.common.form.BaseForm;

public class PositionForm extends BaseForm {
	
	//主键ID
    private String id;

    //职位名称
    @NotBlank(message = "职位名称不能为空")
    private String pozName;

    //职位编码
    @NotBlank(message = "职位编码不能为空")
    private String pozCode;

    //描述或备注
    private String descn;

    //顺序
    private BigDecimal pozOrder;

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

    public String getPozName() {
        return pozName;
    }

    public void setPozName(String pozName) {
        this.pozName = pozName;
    }

    public String getPozCode() {
        return pozCode;
    }

    public void setPozCode(String pozCode) {
        this.pozCode = pozCode;
    }

    public String getDescn() {
        return descn;
    }

    public void setDescn(String descn) {
        this.descn = descn;
    }

    public BigDecimal getPozOrder() {
        return pozOrder;
    }

    public void setPozOrder(BigDecimal pozOrder) {
        this.pozOrder = pozOrder;
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