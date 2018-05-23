package com.topie.zhongkexie.expert.api;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.utils.ExcelExportUtils;
import com.topie.zhongkexie.common.utils.PageConvertUtil;
import com.topie.zhongkexie.common.utils.ResponseUtil;
import com.topie.zhongkexie.common.utils.Result;
import com.topie.zhongkexie.database.expert.model.ExpertInfo;
import com.topie.zhongkexie.expert.service.IExpertInfoService;

@Controller
@RequestMapping("/api/expert/info")
public class ExpertInfoController {
	
	 @Autowired
	    private IExpertInfoService iExpertService;

	    @RequestMapping(value = "/list", method = RequestMethod.GET)
	    @ResponseBody
	    public Result list(ExpertInfo expertInfo,
	            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
	            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
	        PageInfo<ExpertInfo> pageInfo = iExpertService.selectByFilterAndPage(expertInfo, pageNum, pageSize);
	        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
	    }

	    @RequestMapping(value = "/insert", method = RequestMethod.POST)
	    @ResponseBody
	    public Result insert(ExpertInfo expertInfo,String password) {
	        int result = iExpertService.saveNotNull(expertInfo,password);
	        return result > 0 ? ResponseUtil.success() : ResponseUtil.error();
	    }

	    @RequestMapping(value = "/update", method = RequestMethod.POST)
	    @ResponseBody
	    public Result update(ExpertInfo expertInfo) {
	        iExpertService.updateNotNull(expertInfo);
	        return ResponseUtil.success();
	    }

	    @RequestMapping(value = "/load/{id}", method = RequestMethod.GET)
	    @ResponseBody
	    public Result load(@PathVariable(value = "id") Integer id) {
	        ExpertInfo expertInfo = iExpertService.selectByKey(id);
	        return ResponseUtil.success(expertInfo);
	    }

	    @RequestMapping(value = "/delete", method = RequestMethod.GET)
	    @ResponseBody
	    public Result delete(@RequestParam(value = "id") Integer id) {
	        iExpertService.delete(id);
	        return ResponseUtil.success();
	    }
	    @RequestMapping(value = "/export", method = RequestMethod.GET)
	    public void export(ExpertInfo expertInfo,HttpServletRequest request,HttpServletResponse response) {
	        List<ExpertInfo> list = this.iExpertService.selectByFilter(expertInfo);
	        String[] fields = "realName,workUnits,title,relatedField,loginName".split(",");
	        String[] names = "姓名,工作单位,职务职称,专业特长,登录名".split(",");
	        try {
	        	HSSFWorkbook wb = ExcelExportUtils.getInstance().inExcelMoreSheet(list, fields, names);
	        	String name = "专家信息";
	        	if(StringUtils.isNotEmpty(expertInfo.getRelatedField())){
	        		name = expertInfo.getRelatedField()+"_"+name;
	        	}
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
			fileName = URLEncoder.encode(fileName + "导出数据.xls", "UTF-8");
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
}
