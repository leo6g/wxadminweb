package com.lfc.wxadminweb.common.utils;

import java.io.Serializable;
import java.util.Map;

public class PaginationUtil implements Serializable{

	private static final long serialVersionUID = 2982130739307705789L;

	
	/**
	 * 根据页码和每页数量，计算分页开始行和结束行
	 * @param params
	 */
	public static void initPagination(Map<String, Object> params) {
		Object pageNumberObj = params.get("pageNumber");
		Object limitObj = params.get("limit");
		Integer pageNumber = null;
		Integer limit = null;
		try{
			pageNumber = pageNumberObj == null ? 1 : (Integer)pageNumberObj;
			pageNumber = pageNumber<1 ? 1 : pageNumber;
			limit = limitObj == null ? 10 : (Integer)limitObj;
			limit = limit<1 ? 10 : limit;
		}catch(Exception e){
			pageNumber = 1;
			limit = 10;
		}
		Integer oracleStart = (pageNumber - 1) * limit;
		Integer oracleEnd = pageNumber * limit;
		params.put("oracleStart", oracleStart);
		params.put("oracleEnd", oracleEnd);
	}
	
}
