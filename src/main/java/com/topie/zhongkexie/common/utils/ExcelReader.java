package com.topie.zhongkexie.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
/**
 * 
 * @author root
 *
 */
public class ExcelReader {
	 /**  
     * 对外提供读取excel 的方法  
     */  
    public static List<List<Object>> readExcel(File file) throws IOException {  
        String fileName = file.getName();  
        String extension = fileName.lastIndexOf(".") == -1 ? "" : fileName  
                .substring(fileName.lastIndexOf(".") + 1);  
        if ("xls".equals(extension)) {  
            return read2003Excel(file);  
        } else if ("xlsx".equals(extension)) {  
            return read2007Excel(file);  
        } else {  
            throw new IOException("不支持的文件类型");  
        }  
    }  
  
    /**  
     * 读取 office 2003 excel  
     *   
     * @throws IOException  
     * @throws FileNotFoundException  
     */  
    private static List<List<Object>> read2003Excel(File file)  
            throws IOException {  
        List<List<Object>> list = new LinkedList<List<Object>>();  
        HSSFWorkbook hwb = new HSSFWorkbook(new FileInputStream(file));  
        HSSFSheet sheet = hwb.getSheetAt(0);  
        Object value = null;  
        HSSFRow row = null;  
        HSSFCell cell = null;  
        //System.out.println("读取office 2003 excel内容如下：");  
        for (int i = sheet.getFirstRowNum(); i <= sheet  
                .getPhysicalNumberOfRows(); i++) {  
            row = sheet.getRow(i);  
            if (row == null) {  
                continue;  
            }  
            List<Object> linked = new LinkedList<Object>();  
            for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {  
                cell = row.getCell(j);  
                if (cell == null) {  
                    continue;  
                }  
                DecimalFormat df = new DecimalFormat("0");// 格式化 number String  
                // 字符  
                SimpleDateFormat sdf = new SimpleDateFormat(  
                        "yyyy-MM-dd HH:mm:ss");// 格式化日期字符串  
                DecimalFormat nf = new DecimalFormat("0.00");// 格式化数字  
                switch (cell.getCellType()) {  
                case XSSFCell.CELL_TYPE_STRING:  
                    // System.out.println(i + "行" + j + " 列 is String type");  
                    value = cell.getStringCellValue();  
                    //System.out.print("  " + value + "  ");  
                    break;  
                case XSSFCell.CELL_TYPE_NUMERIC:  
                    // System.out.println(i + "行" + j  
                    // + " 列 is Number type ; DateFormt:"  
                    // + cell.getCellStyle().getDataFormatString());  
                    if ("@".equals(cell.getCellStyle().getDataFormatString())) {  
                        value = df.format(cell.getNumericCellValue());  
  
                    } else if ("General".equals(cell.getCellStyle()  
                            .getDataFormatString())) {  
                        value = nf.format(cell.getNumericCellValue());  
                    } else {  
                        value = sdf.format(HSSFDateUtil.getJavaDate(cell  
                                .getNumericCellValue()));  
                    }  
                    //System.out.print("  " + value + "  ");  
                    break;  
                case XSSFCell.CELL_TYPE_BOOLEAN:  
                    // System.out.println(i + "行" + j + " 列 is Boolean type");  
                    value = cell.getBooleanCellValue();  
                    //System.out.print("  " + value + "  ");  
                    break;  
                case XSSFCell.CELL_TYPE_BLANK:  
                    // System.out.println(i + "行" + j + " 列 is Blank type");  
                    value = "";  
                    //System.out.print("  " + value + "  ");  
                    break;  
                default:  
                    // System.out.println(i + "行" + j + " 列 is default type");  
                    value = cell.toString();  
                   // System.out.print("  " + value + "  ");  
                }  
                if (value == null || "".equals(value)) {  
                    continue;  
                }  
                linked.add(value);  
  
            }  
            //System.out.println("");  
            list.add(linked);  
        }  
  
        return list;  
    }  
  
    /**  
     * 读取Office 2007 excel  
     */  
  
    private static List<List<Object>> read2007Excel(File file)  
            throws IOException {  
  
        List<List<Object>> list = new LinkedList<List<Object>>();  
        // String path = System.getProperty("user.dir") +  
        // System.getProperty("file.separator")+"dd.xlsx";  
        // System.out.println("路径："+path);  
        // 构造 XSSFWorkbook 对象，strPath 传入文件路径  
        XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(file));  
        FormulaEvaluator evaluator = xwb.getCreationHelper().createFormulaEvaluator(); 
        // 读取第一章表格内容  
        XSSFSheet sheet = xwb.getSheetAt(0);  
        Object value = null;  
        XSSFRow row = null;  
        XSSFCell cellvalue = null;  
        //System.out.println("读取office 2007 excel内容如下：");  
        for (int i = sheet.getFirstRowNum(); i <= sheet  
                .getPhysicalNumberOfRows(); i++) {  
            row = sheet.getRow(i);  
            if (row == null) {  
                continue;  
            }  
            List<Object> linked = new LinkedList<Object>();  
            for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {  
            	cellvalue = row.getCell(j);  
                CellValue cell = evaluator.evaluate(cellvalue); 
                if (cell == null) {  
                	value=null;
                	linked.add(value);
                    continue;  
                }  
                DecimalFormat df = new DecimalFormat("0");// 格式化 number String  
                // 字符  
                SimpleDateFormat sdf = new SimpleDateFormat(  
                        "yyyy-MM-dd HH:mm:ss");// 格式化日期字符串  
                DecimalFormat nf = new DecimalFormat("0.00");// 格式化数字  
  
                switch (cell.getCellType()) {  
                case XSSFCell.CELL_TYPE_STRING:  
                    // System.out.println(i + "行" + j + " 列 is String type");  
                    value = cell.getStringValue();
//                    System.out.print("  " + value + "  ");  
                    break;  
                case XSSFCell.CELL_TYPE_NUMERIC:  
                    // System.out.println(i + "行" + j  
                    // + " 列 is Number type ; DateFormt:"  
                    // + cell.getCellStyle().getDataFormatString());  
                        value = df.format(cell.getNumberValue());  
//                    System.out.print("  " + value + "  ");  
                    break;  
                case XSSFCell.CELL_TYPE_BOOLEAN:  
                    // System.out.println(i + "行" + j + " 列 is Boolean type");  
                    value = cell.getBooleanValue();  
//                    System.out.print("  " + value + "  ");  
                    break;  
                case XSSFCell.CELL_TYPE_BLANK:  
                    // System.out.println(i + "行" + j + " 列 is Blank type");  
                    value = "";  
                    // System.out.println(value);  
                    break;  
                default:  
                    // System.out.println(i + "行" + j + " 列 is default type");  
                    value = cell.toString();  
//                    System.out.print("  " + value + "  ");  
                }  
                if (value == null || "".equals(value)) {   
                	value=null;
                	linked.add(value);
                    continue;  
                }  
                linked.add(value);  
            }  
//            System.out.println("");  
            list.add(linked);  
        }  
        return list;  
    }  
    
    public static void main(String[] args) throws IOException {
    	File file = new File("D:\\app_note\\qita\\中科协\\指标.xlsx");
    	List<List<Object>> list = ExcelReader.readExcel(file);
    	int i=0;
    	int start=3;
    	int end=232;
    	//指标的列
    	String jsonindex= "[{index:0,socre:1},{index:2,socre:3},{index:4,socre:5},{index:6,socre:7}]";
    	//题目的列
    	String jsonitem ="{title:8,socre:9,desc:10,org:12}";
    	//department mapping
    	String jsonMap ="";
    	//department mapping
    	String deptMap ="";
    	Map<Integer,Integer> map = new HashMap<Integer,Integer>();//记录当前列数 最后一次更新的 指标ID 供下级取Pid
    	String orgName=null;
    	JSONArray jsonIndex = JSONArray.parseArray(jsonindex);
    	JSONObject jsonItem = JSONObject.parseObject(jsonitem);
    	for(List<Object> ls: list){
    		i++;
    		if(i<start) 
    			continue;
    		if(i>end) 
    			break;
    		System.out.println("第"+ i +"行");
    		for(int j=0;j<jsonIndex.size();j++){
    			JSONObject jo = (JSONObject)jsonIndex.get(j);
    			int col = jo.getIntValue("index");//获取指标列数
    			String s =ls.get(col)==null?null:(String)ls.get(col);//获取指标名称
    			if(s==null){//没有指标 则不添加
    				//s = map.get(col);
    			}else{//添加指标
    				int ccl = jo.getIntValue("socre");//获取指标 分值列数
    				String sc = ls.get(ccl)==null?null:(String)ls.get(ccl);//获取指标 分值
    				//插入操作
    				String sql="INSERT INTO `d_score_index_2` (`id`, `parent_id`, `name`, `score`, `sort`, `paper_id`)"
    						+ " VALUES (null, ?, ?, ?, '0', '4')";
    				if(j==0){//若果是第一列  则pid=0 为顶级指标
    					int a =0;//MysqlUtil2.excuteUpdateKey(sql,new Object[]{0,s,sc});//插入指标 并返回当前指标ID
    							System.out.println(0+"    s:"+s+"   sc:"+sc);
    					map.put(j, a);//记录当前列数 最后一次更新的 指标ID 供下级取Pid
    				}else{
    					int pid = map.get(j-1);
    					System.out.println(pid+"   s:"+s+"   sc:"+sc);
    					int a =0;//MysqlUtil2.excuteUpdateKey(sql,new Object[]{pid,s,sc});
    					map.put(j, a);
    				}
    			}
    		}
    		int pid = map.get(jsonIndex.size()-1);
    		String sql = "INSERT INTO`d_score_item_2`"
    				+ " (`id`, `index_id`, `title`, `type`, `option_logic`, `score`, `sort`, `responsible_department`, `related_field`)"
    				+ " VALUES (null, ?, ?, '0', ?, ?, '1', ?, NULL)";
    		String title =ls.get(jsonItem.getIntValue("title"))==null?null:(String)ls.get(jsonItem.getIntValue("title"));
    		String socre =ls.get(jsonItem.getIntValue("socre"))==null?null:(String)ls.get(jsonItem.getIntValue("socre"));
    		String desc =ls.get(jsonItem.getIntValue("desc"))==null?null:(String)ls.get(jsonItem.getIntValue("desc"));
    		String org =ls.get(jsonItem.getIntValue("org"))==null?null:(String)ls.get(jsonItem.getIntValue("org"));
    		if(org==null){
    			org = orgName;
    		}else{
    			orgName = org;
    		}
    		int a =0;// MysqlUtil2.excuteUpdateKey(sql,new Object[]{pid,title,desc,socre,org});
    		System.out.println(pid+" : "+title+" : "+desc+" : "+socre+" : "+org);
    		System.out.println("=====================");
    	}
    }
}  

