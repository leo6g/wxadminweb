package com.lfc.wxadminweb.common.exportexcel;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.lfc.wxadminweb.common.utils.DateUtil;

public class LuckyDogsData {
	
	
	
	
	public static void exportData(List<Map<String,Object>> data, HttpServletRequest request, HttpServletResponse response) {
		HSSFWorkbook wb = new HSSFWorkbook();    
        HSSFSheet sheet = wb.createSheet("中奖纪录明细");    
        HSSFRow row = sheet.createRow((int) 0);    
        HSSFCellStyle style = wb.createCellStyle();  
        sheet.setColumnWidth(0, 30 * 250); 
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); 
    
        String[] excelHeader = { "活动名称", "奖品名称", "奖品等级", "中奖者手机号码", "中奖时间","中奖人"};
        
        for (int i = 0; i < excelHeader.length; i++) {    
            HSSFCell cell = row.createCell(i);    
            cell.setCellValue(excelHeader[i]);    
            cell.setCellStyle(style);    
            sheet.autoSizeColumn(450);    
        } 
		
      //设置Excel没一行数据展示
        if(CollectionUtils.isNotEmpty(data)){
        	int index = 1;
			for (Map<String,Object> num : data) {
			    row = sheet.createRow(index); 
			    row.setHeightInPoints(30);//这是倍数，默认高度的10倍
				row.createCell(0).setCellValue(num.get("activityName")==null?"":num.get("activityName").toString());    
	            row.createCell(1).setCellValue(num.get("prizeName")==null?"":num.get("prizeName").toString());
	            row.createCell(2).setCellValue(num.get("rank")==null?"":num.get("rank").toString());
	            row.createCell(3).setCellValue(num.get("phoneNumber")==null?"":num.get("phoneNumber").toString()); 
	            row.createCell(4).setCellValue(num.get("createTime")==null?"":num.get("createTime").toString()); 
	            row.createCell(5).setCellValue(num.get("name")==null?"":num.get("name").toString()); 
	            index++;
			}
			row = sheet.createRow(index); 
		}
        
		 response.setCharacterEncoding("utf-8");  
         response.setContentType("multipart/form-data");    
		 response.setHeader("Content-Disposition", "attachment;fileName="+getFileName("中奖纪录明细-"+DateUtil.getCurDate()));

        try {
            OutputStream ouputStream = response.getOutputStream();    
			wb.write(ouputStream);
	       // ouputStream.flush();    
	        ouputStream.close(); 
		} catch (IOException e) {
			e.printStackTrace();
		}  
		
	}
	
	
	
	
	/***
	 * 得到下载文件中文名称
	 * @return downFileName
	 */
	private static String getFileName(String downfileName){
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
