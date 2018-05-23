package com.topie.zhongkexie.common.utils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class ExcelExportUtils {

	private static ExcelExportUtils excelExport = new ExcelExportUtils();

	private ExcelExportUtils() {

	}

	public static ExcelExportUtils getInstance() {
		return excelExport;
	}
	public HSSFWorkbook inExcelMoreSheet(List list, String[] fields,
			String[] names)
			throws IOException, IllegalArgumentException,
			IllegalAccessException {
		HSSFWorkbook wb = new HSSFWorkbook();
		return inExcelMoreSheet(list,fields,names,wb,"sheet1");
	}
	public HSSFWorkbook inExcelMoreSheet(List list, String[] fields,
			String[] names, HSSFWorkbook wb, String sheetName)
			throws IOException, IllegalArgumentException,
			IllegalAccessException {
		HSSFSheet sheet = wb.createSheet(sheetName); // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		// ，这里可建多个sheet页
		HSSFCellStyle style = wb.createCellStyle(); // style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);// 设置背景色
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);// 设置单元格下边框
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);//
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上
		// // 创建一个居中格式

		HSSFCellStyle style2 = wb.createCellStyle(); // style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);// 设置单元格下边框
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);//
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上
		HSSFRow rowHeard = sheet.createRow(0);// 创建第一行
		for (int i = 0; i < names.length; i++) {
			HSSFCell head = rowHeard.createCell(i);
			head.setCellStyle(style);
			head.setCellValue(names[i]);
		}
		/*
		 * HSSFCell cellHeard = rowHeard.createCell(0);// 创建第一行第一个单元格
		 * 
		 * cellHeard.setCellValue("这是一个表头");// 在该单元格内输入内容
		 */HSSFRow row = null;
		for (int i = 0; i < list.size(); i++) {
			row = sheet.createRow((int) i + 1);
			// 第四步，创建单元格，并设置值
			Map<String, String> value = getValue(list.get(i), fields);
			for (int j = 0; j < fields.length; j++) {
				HSSFCell head = row.createCell(j);
				head.setCellStyle(style2);
				head.setCellValue(value.get(fields[j]));
			}

		}
		return wb;
	}


	public HSSFWorkbook insheet(HSSFWorkbook wb,String sheetname,List list, String[] fields, String[] names)
			throws IOException, IllegalArgumentException,
			IllegalAccessException {
		HSSFSheet sheet = wb.createSheet(sheetname); // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
												// ，这里可建多个sheet页
		sheet.autoSizeColumn(1);// 设置每个单元格宽度根据字多少自适应
		HSSFCellStyle style = wb.createCellStyle(); // style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
													// // 创建一个居中格式
		HSSFFont fontSearch = wb.createFont();// 设置字体大小fontSearch.setFontHeightInPoints((short)
												// 13);
		style.setFont(fontSearch);// 将该字体样式放入style这个样式中，其他单元格样式也是这么加的，这里只是给一个例子
		style.setBorderBottom(HSSFCellStyle.BORDER_DOUBLE);// 设置单元格下边框
		style.setBorderLeft(HSSFCellStyle.BORDER_DOUBLE);// 左style.setBorderRight(HSSFCellStyle.BORDER_DOUBLE);//youstyle.setBorderTop(HSSFCellStyle.BORDER_DOUBLE);//上
		HSSFRow rowHeard = sheet.createRow(0);// 创建第一行
		for (int i = 0; i < names.length; i++) {
			rowHeard.createCell(i).setCellValue(names[i]);
		}
		/*
		 * HSSFCell cellHeard = rowHeard.createCell(0);// 创建第一行第一个单元格
		 * 
		 * cellHeard.setCellValue("这是一个表头");// 在该单元格内输入内容
		 */HSSFRow row = null;
		for (int i = 0; i < list.size(); i++) {
			row = sheet.createRow((int) i + 1);
			// 第四步，创建单元格，并设置值
			Map<String, String> value = getValue(list.get(i), fields);
			for (int j = 0; j < fields.length; j++) {
				row.createCell(j).setCellValue(value.get(fields[j]));
			}

		}
		sheet.autoSizeColumn(1);// 设置每个单元格宽度根据字多少自适应
		return wb;
	}

	public Map<String, String> getValue(Object t, String[] files)
			throws IllegalArgumentException, IllegalAccessException {
		Field fields[] = t.getClass().getDeclaredFields();// 获得对象所有属性
		Map<String, String> map = new HashMap<String, String>();
		if (t.getClass().getName().equals("java.util.HashMap")) {
			String[] attr = files;
			Map m = (Map )t;
			for (int j = 0; j < attr.length; j++) {
					String value = (m.get(attr[j]) == null || m.get(attr[j])
							.toString().equals("null")) ? "" : m.get(attr[j])
							.toString();
					map.put(attr[j], value);
			}
		} else {
			Field field = null;
			String[] attr = files;
			for (int i = 0; i < fields.length; i++) {
				field = fields[i];
				field.setAccessible(true);// 修改访问权限

				for (int j = 0; j < attr.length; j++) {
					if (attr[j].equals(field.getName())) {
						String value = (field.get(t) == null || field.get(t)
								.toString().equals("null")) ? "" : field.get(t)
								.toString();
						map.put(attr[j], value);
						// System.out.println(value);
						// System.out.println(field.getName() + ":" +
						// field.get(t));// 读取属性值
					}
				}
			}
		}
		return map;
	}
}
