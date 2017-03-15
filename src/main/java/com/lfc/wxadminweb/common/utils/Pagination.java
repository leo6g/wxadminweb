package com.lfc.wxadminweb.common.utils;

import java.io.Serializable;
import java.util.List;

public class Pagination<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2982130739307705642L;


	//查询结果总数量
	private int itemCount;
	//查询结果
	private transient List<T> pageData;

	
	public int getItemCount() {
		return itemCount;
	}
	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}
	public List<T> getPageData() {
		return pageData;
	}
	public void setPageData(List<T> pageData) {
		this.pageData = pageData;
	}


	
}
