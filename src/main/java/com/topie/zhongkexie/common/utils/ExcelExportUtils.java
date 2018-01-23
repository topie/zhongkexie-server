package com.topie.zhongkexie.common.utils;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelExportUtils {

	private static ExcelExportUtils excelExport = new ExcelExportUtils();

	private ExcelExportUtils() {

	}

	public static ExcelExportUtils getInstance() {
		return excelExport;
	}
	public HSSFSheet createSheet(HSSFWorkbook wb,String sheetName){
		HSSFSheet sheet = wb.createSheet(sheetName);
		sheet.autoSizeColumn(1);// 设置每个单元格宽度根据字多少自适应
		HSSFCellStyle style = wb.createCellStyle(); // style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
													// // 创建一个居中格式
		HSSFFont fontSearch = wb.createFont();// 设置字体大小fontSearch.setFontHeightInPoints((short)
												// 13);
		style.setFont(fontSearch);// 将该字体样式放入style这个样式中，其他单元格样式也是这么加的，这里只是给一个例子
		style.setBorderBottom(HSSFCellStyle.BORDER_DOUBLE);// 设置单元格下边框
		style.setBorderLeft(HSSFCellStyle.BORDER_DOUBLE);
		return sheet;
	}

}
