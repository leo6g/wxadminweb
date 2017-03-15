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

public class ExportTBStaffRecomendR1Data {
	public static void exportData(List<Map<String,Object>> data,
			HttpServletRequest request, HttpServletResponse response) {
		HSSFWorkbook wb = new HSSFWorkbook();    
        HSSFSheet sheet = wb.createSheet("个人排名");    
        HSSFRow row = sheet.createRow((int) 0);    
        HSSFCellStyle style = wb.createCellStyle();  
        sheet.setColumnWidth(0, 30 * 100); 
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); 
    
        String[] excelHeader = { "名次", "柜员号", "姓名", "昨日推荐量","地区","累计推荐量","统计日期"};
        
        for (int i = 0; i < excelHeader.length; i++) {    
            HSSFCell cell = row.createCell(i);    
            cell.setCellValue(excelHeader[i]);    
            cell.setCellStyle(style);    
            sheet.autoSizeColumn(450);    
        }    
    
        //设置Excel没一行数据展示
        if(CollectionUtils.isNotEmpty(data)){
        	int index = 1;
			for (Map<String,Object> staffR1 : data) {
			    row = sheet.createRow(index); 
			    row.setHeightInPoints(30);//这是倍数，默认高度的10倍
				row.createCell(0).setCellValue(staffR1.get("openCntRank")==null?"":staffR1.get("openCntRank").toString());    
	            row.createCell(1).setCellValue(staffR1.get("staffCode")==null?"":staffR1.get("staffCode").toString());
	            row.createCell(2).setCellValue(staffR1.get("realName")==null?"":staffR1.get("realName").toString());
	            row.createCell(3).setCellValue(staffR1.get("openDayCnt")==null?"":staffR1.get("openDayCnt").toString()); 
	            row.createCell(4).setCellValue(staffR1.get("departName")==null?"":staffR1.get("departName").toString()); 
	            row.createCell(5).setCellValue(staffR1.get("openCnt")==null?"":staffR1.get("openCnt").toString()); 
	            row.createCell(6).setCellValue(staffR1.get("statisDate")==null?"":staffR1.get("statisDate").toString()); 
	            index++;
			}
			row = sheet.createRow(index); 
		}
		
		response.setCharacterEncoding("utf-8");  
        response.setContentType("multipart/form-data");    
		response.setHeader("Content-Disposition", "attachment;fileName="+getFileName("劳动竞赛个人排名-"+DateUtil.getCurDate()));

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
