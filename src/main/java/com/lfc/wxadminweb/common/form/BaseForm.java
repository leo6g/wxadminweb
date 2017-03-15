package com.lfc.wxadminweb.common.form;

public class BaseForm {

	private Integer pageNumber = null; // 当前页数
	
	private Integer limit = null; // 每页显示记录数
	

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

}
