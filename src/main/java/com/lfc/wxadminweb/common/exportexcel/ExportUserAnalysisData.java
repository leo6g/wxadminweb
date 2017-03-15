package com.lfc.wxadminweb.common.exportexcel;

import java.io.IOException;
import java.io.OutputStream;
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
import com.lfc.wxadminweb.common.utils.RootUtils;

public class ExportUserAnalysisData {
	public static void exportData(List<Map<String,Object>> data, HttpServletRequest request, HttpServletResponse response) {
		HSSFWorkbook wb = new HSSFWorkbook();    
        HSSFSheet sheet = wb.createSheet("用户分析");    
        HSSFRow row = sheet.createRow((int) 0);    
        HSSFCellStyle style = wb.createCellStyle();  
        sheet.setColumnWidth(0, 30 * 250); 
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); 
    
        String[] excelHeader = { "新增关注人数", "取消关注人数", "净增人数", "积累人数", "时间"};
        
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
				row.createCell(0).setCellValue(num.get("sm")==null?"":num.get("sm").toString());    
	            row.createCell(1).setCellValue(num.get("um")==null?"":num.get("um").toString());
	            row.createCell(2).setCellValue(num.get("jzm")==null?"":num.get("jzm").toString());
	            row.createCell(3).setCellValue(num.get("sumnum")==null?"":num.get("sumnum").toString()); 
	            row.createCell(4).setCellValue(num.get("time")==null?"":num.get("time").toString()); 
	            index++;
			}
			row = sheet.createRow(index); 
		}
        
		 response.setCharacterEncoding("utf-8");  
         response.setContentType("multipart/form-data");    
		 response.setHeader("Content-Disposition", "attachment;fileName="+RootUtils.getFileName("用户分析-"+DateUtil.getCurDate()));

        try {
            OutputStream ouputStream = response.getOutputStream();    
			wb.write(ouputStream);
	       // ouputStream.flush();    
	        ouputStream.close(); 
		} catch (IOException e) {
			e.printStackTrace();
		}  
		
	}
	
	
}
