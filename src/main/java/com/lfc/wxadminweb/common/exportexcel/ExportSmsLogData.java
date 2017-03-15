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

public class ExportSmsLogData {
	public static void exportData(List<Map<String,Object>> data,
			HttpServletRequest request, HttpServletResponse response) {
		HSSFWorkbook wb = new HSSFWorkbook();    
        HSSFSheet sheet = wb.createSheet("短信发送日志");    
        HSSFRow row = sheet.createRow((int) 0);    
        HSSFCellStyle style = wb.createCellStyle();  
        sheet.setColumnWidth(0, 30 * 100); 
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); 
    
        String[] excelHeader = { "短信内容","手机号码","短信条数","发送时间"};
        
        for (int i = 0; i < excelHeader.length; i++) {    
            HSSFCell cell = row.createCell(i);    
            cell.setCellValue(excelHeader[i]);    
            cell.setCellStyle(style);    
            sheet.autoSizeColumn(450);    
        }    
    
        //设置Excel没一行数据展示
        if(CollectionUtils.isNotEmpty(data)){
        	int index = 1;
			for (Map<String,Object> staffR2 : data) {
			    row = sheet.createRow(index); 
			    row.setHeightInPoints(30);//这是倍数，默认高度的10倍
				row.createCell(0).setCellValue(staffR2.get("smsContent")==null?"":staffR2.get("smsContent").toString());    
	            row.createCell(1).setCellValue(staffR2.get("phoneNumber")==null?"":staffR2.get("phoneNumber").toString());
	            row.createCell(2).setCellValue(staffR2.get("smsCnt")==null?"":staffR2.get("smsCnt").toString());
	            row.createCell(3).setCellValue(staffR2.get("sendTime")==null?"":staffR2.get("sendTime").toString()); 
//	            row.createCell(4).setCellValue(staffR2.get("createTime")==null?"":staffR2.get("createTime").toString()); 
//	            row.createCell(5).setCellValue(staffR2.get("statisDate")==null?"":staffR2.get("statisDate").toString()); 
	            index++;
			}
			row = sheet.createRow(index); 
		}
        
		response.setCharacterEncoding("utf-8");  
        response.setContentType("multipart/form-data");    
		response.setHeader("Content-Disposition", "attachment;fileName="+getFileName("短信发送日志-"+DateUtil.getCurDate()));

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
