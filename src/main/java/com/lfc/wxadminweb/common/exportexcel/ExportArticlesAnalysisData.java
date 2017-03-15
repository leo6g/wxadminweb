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

public class ExportArticlesAnalysisData {
	public static void exportData(List<Map<String,Object>> data, HttpServletRequest request, HttpServletResponse response) {
		HSSFWorkbook wb = new HSSFWorkbook();    
        HSSFSheet sheet = wb.createSheet("图文分析");    
        HSSFRow row = sheet.createRow((int) 0);    
        HSSFCellStyle style = wb.createCellStyle();  
        sheet.setColumnWidth(0, 30 * 250); 
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); 
        String[] excelHeader = null;
        String type = request.getParameter("type");
        if (type == "day" || type.equals("day")) {
        excelHeader = new String[]{ "时间","图文页阅读人数","图文页阅读次数","原文页阅读人数","原文页阅读次数","分享的人数","分享的次数","收藏的人数","收藏的次数"};
        }
        if (type == "hour" || type.equals("hour")) {
        	excelHeader = new String[]{ "时间","小时","图文页阅读人数","图文页阅读次数","原文页阅读人数","原文页阅读次数","分享的人数","分享的次数","收藏的人数","收藏的次数"};
        }
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
			    row.createCell(0).setCellValue(num.get("statDate")==null?"":num.get("statDate").toString()); 
			    if (type == "day" || type.equals("day")) {
			    	row.createCell(1).setCellValue(num.get("intPageReadUser")==null?"":num.get("intPageReadUser").toString());  
		            row.createCell(2).setCellValue(num.get("intPageReadCount")==null?"":num.get("intPageReadCount").toString());
		            row.createCell(3).setCellValue(num.get("oriPageReadUser")==null?"":num.get("oriPageReadUser").toString());
		            row.createCell(4).setCellValue(num.get("oriPageReadCount")==null?"":num.get("oriPageReadCount").toString());
		            row.createCell(5).setCellValue(num.get("shareUser")==null?"":num.get("shareUser").toString());
		            row.createCell(6).setCellValue(num.get("shareCount")==null?"":num.get("shareCount").toString());
		            row.createCell(7).setCellValue(num.get("addToFavUser")==null?"":num.get("addToFavUser").toString());
		            row.createCell(8).setCellValue(num.get("addToFavCount")==null?"":num.get("addToFavCount").toString());
			    }
			    if (type == "hour" || type.equals("hour")) {
			    	row.createCell(1).setCellValue(num.get("refHour")==null?"":num.get("refHour").toString()); 
			    	row.createCell(2).setCellValue(num.get("intPageReadUser")==null?"":num.get("intPageReadUser").toString());  
		            row.createCell(3).setCellValue(num.get("intPageReadCount")==null?"":num.get("intPageReadCount").toString());
		            row.createCell(4).setCellValue(num.get("oriPageReadUser")==null?"":num.get("oriPageReadUser").toString());
		            row.createCell(5).setCellValue(num.get("oriPageReadCount")==null?"":num.get("oriPageReadCount").toString());
		            row.createCell(6).setCellValue(num.get("shareUser")==null?"":num.get("shareUser").toString());
		            row.createCell(7).setCellValue(num.get("shareCount")==null?"":num.get("shareCount").toString());
		            row.createCell(8).setCellValue(num.get("addToFavUser")==null?"":num.get("addToFavUser").toString());
		            row.createCell(9).setCellValue(num.get("addToFavCount")==null?"":num.get("addToFavCount").toString());
			    }
			    
	            index++;
			}
			row = sheet.createRow(index); 
		}
        
		 response.setCharacterEncoding("utf-8");  
         response.setContentType("multipart/form-data");    
		 response.setHeader("Content-Disposition", "attachment;fileName="+RootUtils.getFileName("图文分析-"+DateUtil.getCurDate()));

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
