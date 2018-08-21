package com.topie.zhongkexie.core.api;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
import com.topie.zhongkexie.core.service.IDeptService;
import com.topie.zhongkexie.core.service.IScoreAnswerService;
import com.topie.zhongkexie.core.service.IScoreItemOptionService;
import com.topie.zhongkexie.core.service.IScoreItemService;
import com.topie.zhongkexie.core.service.IScorePagerUserService;
import com.topie.zhongkexie.core.service.IScorePaperService;
import com.topie.zhongkexie.database.core.model.ScoreAnswer;
import com.topie.zhongkexie.expert.service.IExpertItemScoreService;
import com.topie.zhongkexie.security.service.UserService;

/**
 * 统计数据
 */
@Controller
@RequestMapping("/api/core/scoreCount")
public class ScoreCountController {

	@Autowired
	private IScorePaperService iScorePaperService;

	@Autowired
	private IScoreItemService iScoreItemService;

	@Autowired
	private IScoreAnswerService iScoreAnswerService;

	@Autowired
	private IScoreItemOptionService iScoreItemOptionService;

	@Autowired
	private IScorePagerUserService iScorePagerUserService;
	@Autowired
	private IDeptService iDeptService;
	@Autowired
	private IExpertItemScoreService iExpertItemScoreService;
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(ScoreAnswer scoreAnswer,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
		Map map = new HashMap();
		map.put("paperId", scoreAnswer.getPaperId());
        PageInfo<Map> pageInfo = iScoreAnswerService.selectResultTime(map, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }
	
	@RequestMapping(value = "/partIndexScore", method = RequestMethod.GET)
    @ResponseBody
    public Result selectPartIndexScore(String itemIds,String indexIds,Integer paperId,String deptType,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
		Map map = new HashMap();
		List itemIdList = null;
		if(StringUtils.isNotEmpty(itemIds)){
			itemIdList =  Arrays.asList(itemIds.split(","));
		}
		List indexIdList = null;
		if(StringUtils.isNotEmpty(indexIds)){
			indexIdList =  Arrays.asList(indexIds.split(","));
		}
		map.put("paperId", paperId);
		map.put("itemIds", itemIdList);
		map.put("indexIds", indexIdList);
		map.put("deptType", deptType);
        PageInfo<Map> pageInfo = iScoreAnswerService.selectPartIndexScore(map, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }
	
	@RequestMapping(value = "/partIndexScoreExport", method = RequestMethod.GET)
    public void partIndexScoreExport(String itemIds,String indexIds,Integer paperId,String deptType,
			HttpServletRequest request, HttpServletResponse response) {
		Map map = new HashMap();
		List itemIdList = null;
		if(StringUtils.isNotEmpty(itemIds)){
			itemIdList =  Arrays.asList(itemIds.split(","));
		}
		List indexIdList = null;
		if(StringUtils.isNotEmpty(indexIds)){
			indexIdList =  Arrays.asList(indexIds.split(","));
		}
		map.put("paperId", paperId);
		map.put("itemIds", itemIdList);
		map.put("indexIds", indexIdList);
		map.put("deptType", deptType);
        List<Map> list = iScoreAnswerService.selectPartIndexScore(map);

		 String[] fields = "code,displayName,score".split(",");
	        String[] names = "学会编码,学会,分数".split(",");
	        try {
	        	String name = "分数导出";
	        	name = deptType==null?"":deptType+name;
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
	@RequestMapping(value = "/userUploadFileCounts", method = RequestMethod.GET)
    @ResponseBody
    public Result userUploadFileCounts(Integer paperId) {
		Map map = new HashMap();
		map.put("paperId", paperId);
        List<Map> pageInfo = iScoreAnswerService.selectUserUploadFileCounts(map);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
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
}
