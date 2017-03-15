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
import org.apache.poi.util.StringUtil;

import com.lfc.wxadminweb.common.utils.DateUtil;
import com.lfc.wxadminweb.modules.biz.form.CardApplierForm;

public class ExportMerchantAppliData {
	public static void exportData(List<Map<String,Object>> data,
			HttpServletRequest request, HttpServletResponse response) {
		HSSFWorkbook wb = new HSSFWorkbook();    
        HSSFSheet sheet = wb.createSheet("申请信息");    
        HSSFRow row = sheet.createRow((int) 0);    
        HSSFCellStyle style = wb.createCellStyle();    
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); 
    
        String[] excelHeader = {"申请人名称","申请网点","商户名称","处理状态","申请商户类型","申请人手机号码","创建时间","接收人","处理结果"};
        
        for (int i = 0; i < excelHeader.length; i++) {    
            HSSFCell cell = row.createCell(i);    
            cell.setCellValue(excelHeader[i]);    
            cell.setCellStyle(style);    
            sheet.autoSizeColumn(450);    
        }    
    
        //设置Excel没一行数据展示
        if(CollectionUtils.isNotEmpty(data)){
        	int index = 1;
			for (Map<String,Object> appliOutEntity : data) {
			    row = sheet.createRow(index); 
				row.createCell(0).setCellValue(appliOutEntity.get("applierName")==null?"":appliOutEntity.get("applierName").toString());  
				row.createCell(1).setCellValue(appliOutEntity.get("departName")==null?"":appliOutEntity.get("departName").toString());
	            row.createCell(2).setCellValue(appliOutEntity.get("shopName")==null?"":appliOutEntity.get("shopName").toString());
	            String processState = "";
	            if("applied".equals(appliOutEntity.get("processState"))){
	            	processState = "申请";
	            }else if("processing".equals(appliOutEntity.get("processState"))){
	            	processState = "处理中";
	            }else if("completed".equals(appliOutEntity.get("processState"))){
	            	processState = "处理完成";
	            }
	            row.createCell(3).setCellValue(processState); 
	            row.createCell(4).setCellValue(appliOutEntity.get("type")==null?"":appliOutEntity.get("type").toString()); 
	            row.createCell(5).setCellValue(appliOutEntity.get("applierPhone")==null?"":appliOutEntity.get("applierPhone").toString());
	            row.createCell(6).setCellValue(appliOutEntity.get("createTime")==null?"":appliOutEntity.get("createTime").toString());    
	            row.createCell(7).setCellValue(appliOutEntity.get("realName")==null?"":appliOutEntity.get("realName").toString());          
	            String processResult = "";
	            if("success".equals(appliOutEntity.get("processResult"))){
	            	processResult= "申办成功";
	      	     }else if("fail".equals(appliOutEntity.get("processResult"))){
	      	    	processResult= "申办失败";
	              }
	            row.createCell(8).setCellValue(processResult);  
	            index ++ ;
			}
			row = sheet.createRow(index); 
		}
        
        response.setCharacterEncoding("utf-8");  
        response.setContentType("multipart/form-data");    
		response.setHeader("Content-Disposition", "attachment;fileName="+getFileName("特惠商户申请信息-"+DateUtil.getCurDate()));

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
