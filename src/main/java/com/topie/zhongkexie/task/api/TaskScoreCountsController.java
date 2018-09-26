package com.topie.zhongkexie.task.api;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.utils.ExcelExportUtils;
import com.topie.zhongkexie.common.utils.PageConvertUtil;
import com.topie.zhongkexie.common.utils.ResponseUtil;
import com.topie.zhongkexie.common.utils.Result;
import com.topie.zhongkexie.common.utils.date.DateUtil;
import com.topie.zhongkexie.database.task.model.Task;
import com.topie.zhongkexie.database.task.model.TaskScore;
import com.topie.zhongkexie.task.service.ITaskService;

@Controller
@RequestMapping("/api/task/scoreCounts")
public class TaskScoreCountsController {

    @Autowired
    private ITaskService iTaskService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(Task task,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        PageInfo<Map> pageInfo = iTaskService.selectScoreCountsByFilterAndPage(task, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }
    @RequestMapping(value = "/taskScoreListExport", method = RequestMethod.GET)
    @ResponseBody
    public void list(Task task,HttpServletRequest request, HttpServletResponse response) {
        PageInfo<Map> pageInfo = iTaskService.selectScoreCountsByFilterAndPage(task, 1, 1000);
        List list = pageInfo.getList();
        String[] fields = "displayName,score".split(",");
        String[] names = "学会,分数".split(",");
        try {
        	String name = "部门考核分数_"+DateUtil.getDate(new Date());
        	HSSFWorkbook wb = ExcelExportUtils.getInstance().inExcelMoreSheet(list, fields, names);
        	outWrite(request, response, wb, name);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private static void outWrite(HttpServletRequest request,
			HttpServletResponse response, HSSFWorkbook wb, String fileName)
			throws IOException {
		OutputStream output = null;
		try {
			output = response.getOutputStream();
			response.reset();
			// fileName=new String((fileName+"导出数据.xls").getBytes(),
			// "ISO_8859_1");
			fileName = URLEncoder.encode(fileName + "_导出数据.xls", "UTF-8");
			response.setHeader("Content-disposition", "attachment; filename="
					+ fileName);
			// response.setHeader("Content-disposition",
			// "attachment;filename*=UTF-8 "
			// + URLEncoder.encode(fileName,"UTF-8"));
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setCharacterEncoding("utf-8");
			wb.write(output);
			output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (output != null) {
				output.close();
			}
		}
	}
    
    @RequestMapping(value = "/taskScoreList", method = RequestMethod.GET)
    @ResponseBody
    public Result list(TaskScore taskScore) {
        List<TaskScore> list = iTaskService.selectByFilter(taskScore);
        return ResponseUtil.success(list);
    }
    
    


}
