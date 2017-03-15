package com.lfc.wxadminweb.common.utils;

import java.io.UnsupportedEncodingException;

public class RootUtils {
	/***
	 * 得到下载文件中文名称
	 * @return downFileName
	 */
	public static String getFileName(String downfileName){
		String downFileName = "";
		try {     
			downFileName = new String(downfileName.getBytes("gbk"), "ISO8859-1")+".xls";    
			  
			} catch (UnsupportedEncodingException e) {    
				e.printStackTrace();
				throw new RuntimeException("字符转化失败!");
			}    
			  
			return downFileName;   
	}
}
