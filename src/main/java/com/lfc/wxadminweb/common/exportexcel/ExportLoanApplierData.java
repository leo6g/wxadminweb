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

public class ExportLoanApplierData {
	public static void exportData(List<Map<String,Object>> data,
			HttpServletRequest request, HttpServletResponse response) {
		HSSFWorkbook wb = new HSSFWorkbook();    
        HSSFSheet sheet = wb.createSheet("贷款");    
        HSSFRow row = sheet.createRow((int) 0);    
        HSSFCellStyle style = wb.createCellStyle();    
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); 
        String[] excelHeader = { "姓名", "申请网点","申请时间", "手机号码","贷款产品", "贷款用途", "贷款金额",
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
			for (Map<String,Object> loanOutEntity : data) {
				
			    row = sheet.createRow(index); 
				row.createCell(0).setCellValue(loanOutEntity.get("name")==null?"":loanOutEntity.get("name").toString());  
				row.createCell(1).setCellValue(loanOutEntity.get("orgShtName")==null?"":loanOutEntity.get("orgShtName").toString()); 
	            row.createCell(2).setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(loanOutEntity.get("applyTime")));    
	            row.createCell(3).setCellValue(loanOutEntity.get("cellNumber")==null?"":loanOutEntity.get("cellNumber").toString());
	            row.createCell(4).setCellValue(loanOutEntity.get("loanName")==null?"":loanOutEntity.get("loanName").toString());
	            row.createCell(5).setCellValue(loanOutEntity.get("useage")==null?"":loanOutEntity.get("useage").toString()); 
	            row.createCell(6).setCellValue(loanOutEntity.get("loanAmount")==null?"":loanOutEntity.get("loanAmount").toString());
	            row.createCell(7).setCellValue(loanOutEntity.get("realName")==null?"":loanOutEntity.get("realName").toString()); 
	            String processState = "";
	            
	            if("applied".equals(loanOutEntity.get("processState"))){
	            	processState = "申请";
	            }else if("processing".equals(loanOutEntity.get("processState"))){
	            	processState = "处理中";
	            }else if("completed".equals(loanOutEntity.get("processState"))){
	            	processState = "处理完成";
	            }
	            row.createCell(8).setCellValue(processState); 
	            String processResult = "";
	            if("success".equals(loanOutEntity.get("processResult"))){
	            	processResult= "申办成功";
	      	     }else if("fail".equals(loanOutEntity.get("processResult"))){
	      	    	processResult= "申办失败";
	              }
	            row.createCell(9).setCellValue(processResult);  
	            index ++ ;
			}
			row = sheet.createRow(index); 
		}
        
        response.setCharacterEncoding("utf-8");  
        response.setContentType("multipart/form-data");    
		response.setHeader("Content-Disposition", "attachment;fileName="+getFileName("贷款申办信息-"+DateUtil.getCurDate()));

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
