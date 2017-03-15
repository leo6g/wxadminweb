package com.lfc.wxadminweb.common.exportexcel;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
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

public class ExportCardAppliData {
	public static void exportData(List<Map<String,Object>> data,
			HttpServletRequest request, HttpServletResponse response) {
		HSSFWorkbook wb = new HSSFWorkbook();    
        HSSFSheet sheet = wb.createSheet("申办信息");    
        HSSFRow row = sheet.createRow((int) 0);    
        HSSFCellStyle style = wb.createCellStyle();    
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); 
    
        String[] excelHeader = { "姓名", "申请网点", "申请时间", "手机号码", "信用卡名称", "信用卡类型",
        		"接收人", "处理状态", "处理结果"};
        
        for (int i = 0; i < excelHeader.length; i++) {    
            HSSFCell cell = row.createCell(i);    
            cell.setCellValue(excelHeader[i]);    
            cell.setCellStyle(style);    
            sheet.autoSizeColumn(450);    
        }    
    
        //设置Excel没一行数据展示
        if(CollectionUtils.isNotEmpty(data)){
        	int index = 1;
        	Double totalInvoiceAmount = 0.0;
			for (Map<String,Object> cardOutEntity : data) {
			    row = sheet.createRow(index); 
				row.createCell(0).setCellValue(cardOutEntity.get("name")==null?"":cardOutEntity.get("name").toString());
				row.createCell(1).setCellValue(cardOutEntity.get("orgShtName")==null?"":cardOutEntity.get("orgShtName").toString()); 
	            row.createCell(2).setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(cardOutEntity.get("applyTime")));    
	            row.createCell(3).setCellValue(cardOutEntity.get("cellNumber")==null?"":cardOutEntity.get("cellNumber").toString());
	            row.createCell(4).setCellValue(cardOutEntity.get("cardName")==null?"":cardOutEntity.get("cardName").toString()); 
	            row.createCell(5).setCellValue(cardOutEntity.get("cardType")==null?"":cardOutEntity.get("cardType").toString());
	            row.createCell(6).setCellValue(cardOutEntity.get("realName")==null?"":cardOutEntity.get("realName").toString()); 
	            String processState = "";
	            if("applied".equals(cardOutEntity.get("processState"))){
	            	processState = "申请";
	            }else if("processing".equals(cardOutEntity.get("processState"))){
	            	processState = "处理中";
	            }else if("completed".equals(cardOutEntity.get("processState"))){
	            	processState = "处理完成";
	            }
	            row.createCell(7).setCellValue(processState); 
	            String processResult = "";
	            if("success".equals(cardOutEntity.get("processResult"))){
	            	processResult= "申办成功";
	      	     }else if("fail".equals(cardOutEntity.get("processResult"))){
	      	    	processResult= "申办失败";
	              }
	            row.createCell(8).setCellValue(processResult);  
	            index ++ ;
			}
			row = sheet.createRow(index); 
		}
        response.setCharacterEncoding("utf-8");  
        response.setContentType("multipart/form-data");    
		response.setHeader("Content-Disposition", "attachment;fileName="+getFileName("信用卡申办信息"+DateUtil.getCurDate()));
        try {
            OutputStream ouputStream = response.getOutputStream();    
			wb.write(ouputStream);
	        ouputStream.flush();    
	        ouputStream.close(); 
		} catch (IOException e) {
			e.printStackTrace();
		}  
	}
	/**
	 * 信用卡申请查询--导出
	 * @param data
	 * @param request
	 * @param response
	 */
	public static void exportCardAppInfo(List<Map<String,Object>> data,
			HttpServletRequest request, HttpServletResponse response) {
		HSSFWorkbook wb = new HSSFWorkbook();    
        HSSFSheet sheet = wb.createSheet("申办信息");    
        HSSFRow row = sheet.createRow((int) 0);    
        HSSFCellStyle style = wb.createCellStyle();    
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); 
    
        String[] excelHeader = { "姓名", "申请时间", "二分", "申办网点",  "手机号码", "信用卡名称", "信用卡类型"};
        
        for (int i = 0; i < excelHeader.length; i++) {    
            HSSFCell cell = row.createCell(i);    
            cell.setCellValue(excelHeader[i]);    
            cell.setCellStyle(style);    
            sheet.autoSizeColumn(450);    
        }    
        //设置Excel数据展示
        if(CollectionUtils.isNotEmpty(data)){
        	int index = 1;
        	for (Map<String,Object> cardOutEntity : data) {
			    row = sheet.createRow(index); 
				row.createCell(0).setCellValue(cardOutEntity.get("name")==null?"":cardOutEntity.get("name").toString()); 
	            row.createCell(1).setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(cardOutEntity.get("applyTime")));
				row.createCell(2).setCellValue(cardOutEntity.get("city")==null?"":cardOutEntity.get("city").toString());
				row.createCell(3).setCellValue(cardOutEntity.get("orgShtName")==null?"":cardOutEntity.get("orgShtName").toString());
	            row.createCell(4).setCellValue(cardOutEntity.get("cellNumber")==null?"":cardOutEntity.get("cellNumber").toString());
	            row.createCell(5).setCellValue(cardOutEntity.get("cardName")==null?"":cardOutEntity.get("cardName").toString()); 
	            row.createCell(6).setCellValue(cardOutEntity.get("cardType")==null?"":cardOutEntity.get("cardType").toString());
	            
	            index ++ ;
			}
			row = sheet.createRow(index); 
		}
        response.setCharacterEncoding("utf-8");  
        response.setContentType("multipart/form-data");    
		response.setHeader("Content-Disposition", "attachment;fileName="+getFileName("信用卡申办完整信息"+DateUtil.getCurDate()));
        try {
            OutputStream ouputStream = response.getOutputStream();    
			wb.write(ouputStream);
	        ouputStream.flush();    
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
