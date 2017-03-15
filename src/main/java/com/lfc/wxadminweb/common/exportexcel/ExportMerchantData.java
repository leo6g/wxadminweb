package com.lfc.wxadminweb.common.exportexcel;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.util.StringUtil;

import com.lfc.wxadminweb.common.utils.DateUtil;
import com.lfc.wxadminweb.modules.biz.form.CardApplierForm;

public class ExportMerchantData {
	public static void exportData(List<Map<String,Object>> data,
			HttpServletRequest request, HttpServletResponse response) {
		HSSFWorkbook wb = new HSSFWorkbook();    
        HSSFSheet sheet = wb.createSheet("特惠商户");    
        HSSFRow row = sheet.createRow((int) 0);    
        HSSFCellStyle style = wb.createCellStyle();  
        sheet.setColumnWidth(0, 30 * 250); 
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); 
    
        String[] excelHeader = { "商户名称", "商户简称", "商户类型", "联系电话", "经度",
        		"纬度", "商户图片", "状态","活动名称","活动开始日期","活动结束日期","活动内容"};
        
        for (int i = 0; i < excelHeader.length; i++) {    
            HSSFCell cell = row.createCell(i);    
            cell.setCellValue(excelHeader[i]);    
            cell.setCellStyle(style);    
            sheet.autoSizeColumn(450);    
        }    
    
        //设置Excel没一行数据展示
        if(CollectionUtils.isNotEmpty(data)){
        	int index = 1;
			for (Map<String,Object> merchant : data) {
			    row = sheet.createRow(index); 
			    row.setHeightInPoints(30);//这是倍数，默认高度的10倍
				row.createCell(0).setCellValue(merchant.get("name")==null?"":merchant.get("name").toString());    
	            row.createCell(1).setCellValue(merchant.get("shortName")==null?"":merchant.get("shortName").toString());
	            row.createCell(2).setCellValue(merchant.get("type")==null?"":merchant.get("type").toString());
	            row.createCell(3).setCellValue(merchant.get("tactorPhone")==null?"":merchant.get("tactorPhone").toString()); 
	            row.createCell(4).setCellValue(merchant.get("longitude")==null?"":merchant.get("longitude").toString()); 
	            row.createCell(5).setCellValue(merchant.get("latitude")==null?"":merchant.get("latitude").toString()); 
	            BufferedImage bufferImg = null;     
	            //先把读进来的图片放到一个ByteArrayOutputStream中，以便产生ByteArray    
	            try {  
	                ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();     
	                bufferImg = ImageIO.read(new File(merchant.get("imagePath")==null?"":merchant.get("imagePath").toString()));     
	                ImageIO.write(bufferImg, "jpg", byteArrayOut);    
	                //画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）  
	                HSSFPatriarch patriarch = sheet.createDrawingPatriarch();     
	                //anchor主要用于设置图片的属性  
	                HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 20, 20,(short) 6, index, (short) 7, index+1);     
	                anchor.setAnchorType(3);     
	                //插入图片    
	            patriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));   
	            }catch (Exception e) {  
	                e.printStackTrace();  
	            }
	            //row.createCell(6).setCellValue(merchant.get("imagePath")==null?"":merchant.get("imagePath").toString()); 
	            row.createCell(7).setCellValue(merchant.get("state")==null?"":merchant.get("state").toString()); 
	            row.createCell(8).setCellValue(merchant.get("activityName")==null?"":merchant.get("activityName").toString()); 
	            row.createCell(9).setCellValue(merchant.get("startDate")!=null?new SimpleDateFormat("yyyy-MM-dd").format(merchant.get("startDate")):"");
	            row.createCell(10).setCellValue(merchant.get("endDate")!=null?new SimpleDateFormat("yyyy-MM-dd").format(merchant.get("endDate")):"");
	            row.createCell(11).setCellValue(merchant.get("activityContent")==null?"":merchant.get("activityContent").toString()); 
	            index++;
			}
			row = sheet.createRow(index); 
		}
        
		 response.setCharacterEncoding("utf-8");  
         response.setContentType("multipart/form-data");    
		 response.setHeader("Content-Disposition", "attachment;fileName="+getFileName("特惠商户-"+DateUtil.getCurDate()));

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
