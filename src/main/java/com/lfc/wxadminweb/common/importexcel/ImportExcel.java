package com.lfc.wxadminweb.common.importexcel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class ImportExcel {
	
	/**
	 * 
	 * */
	   public static String getStringCellValue(Cell cell) {
	        String strCell = "";
	        switch (cell.getCellType()) {
	        case Cell.CELL_TYPE_STRING:
	            strCell = cell.getStringCellValue();
	            break;
	        case Cell.CELL_TYPE_NUMERIC:
	            strCell = String.valueOf(cell.getNumericCellValue());
	            break;
	        case Cell.CELL_TYPE_BOOLEAN:
	            strCell = String.valueOf(cell.getBooleanCellValue());
	            break;
	        case Cell.CELL_TYPE_BLANK:
	            strCell = "";
	            break;
	        default:
	            strCell = "";
	            break;
	        }
	        if (strCell.equals("") || strCell == null) {
	            return "";
	        }
	        if (cell == null) {
	            return "";
	        }
	        return strCell;
	    }
	
	   public static boolean isBlankRow(Row row){
        if(row == null) return true;
        boolean result = true;
        for(int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++){
           Cell cell = row.getCell(i, Row.RETURN_BLANK_AS_NULL);
            String value = "";
            if(cell != null){
                switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    value = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    value = String.valueOf((int) cell.getNumericCellValue());
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    value = String.valueOf(cell.getBooleanCellValue());
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    value = String.valueOf(cell.getCellFormula());
                    break;
                default:
                    break;
                }
                
                if(!value.trim().equals("")){
                    result = false;
                    break;
                }
            }
        }
        
        return result;
    }
}
