package com.topie.zhongkexie.core.api;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.utils.ExcelExportUtils;
import com.topie.zhongkexie.common.utils.PageConvertUtil;
import com.topie.zhongkexie.common.utils.ResponseUtil;
import com.topie.zhongkexie.common.utils.Result;
import com.topie.zhongkexie.core.dto.AnswerDto;
import com.topie.zhongkexie.core.dto.PagerUserDto;
import com.topie.zhongkexie.core.dto.PaperAnswerDto;
import com.topie.zhongkexie.core.service.IDeptService;
import com.topie.zhongkexie.core.service.IScoreAnswerService;
import com.topie.zhongkexie.core.service.IScoreItemOptionService;
import com.topie.zhongkexie.core.service.IScoreItemService;
import com.topie.zhongkexie.core.service.IScorePagerUserService;
import com.topie.zhongkexie.core.service.IScorePaperService;
import com.topie.zhongkexie.database.core.model.Dept;
import com.topie.zhongkexie.database.core.model.ScoreAnswer;
import com.topie.zhongkexie.database.core.model.ScoreItem;
import com.topie.zhongkexie.database.core.model.ScorePaper;
import com.topie.zhongkexie.database.core.model.ScorePaperUser;
import com.topie.zhongkexie.database.expert.model.ExpertItemScore;
import com.topie.zhongkexie.expert.service.IExpertItemScoreService;
import com.topie.zhongkexie.security.security.SecurityUser;
import com.topie.zhongkexie.security.service.UserService;
import com.topie.zhongkexie.security.utils.SecurityUtil;

/**
 * Created by chenguojun on 2017/4/19.
 */
@Controller
@RequestMapping("/api/core/scorePaper")
public class ScorePaperController {

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

	/**
	 * 编辑评价表列表
	 * 
	 * @param scorePaper
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Result list(
			ScorePaper scorePaper,
			@RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
		scorePaper.setSort_("createTime_desc");
		PageInfo<ScorePaper> pageInfo = iScorePaperService
				.selectByFilterAndPage(scorePaper, pageNum, pageSize);
		return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
	}

	/**
	 * 中科协审核发布列表
	 * 
	 * @param scorePaper
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/checkList", method = RequestMethod.GET)
	@ResponseBody
	public Result checkList(
			ScorePaper scorePaper,
			@RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
		scorePaper.setSort_("createTime_desc");
		PageInfo<ScorePaper> pageInfo = iScorePaperService
				.selectByFilterAndPageForCheck(scorePaper, pageNum, pageSize);
		return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
	}

	/**
	 * 中科协 查看各个学会提交信息
	 * 
	 * @param scorePaper
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/zkxcheckList", method = RequestMethod.GET)
	@ResponseBody
	public Result zkxcheckList(
			PagerUserDto pagerUserDto,
			@RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
		if(pagerUserDto.getPaperId()==null)return ResponseUtil.success(PageConvertUtil.emptyPage());
		PageInfo<PagerUserDto> pageInfo = iScorePagerUserService
				.selectAllUserCommit(pagerUserDto, pageNum, pageSize);
		return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
	}
	
	/**
	 * 公示 查看各个学会提交信息
	 * 
	 * @param scorePaper
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/publicityList", method = RequestMethod.GET)
	@ResponseBody
	public Result publicityList(
			PagerUserDto pagerUserDto,
			@RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
		if(pagerUserDto.getPaperId()==null)return ResponseUtil.success(PageConvertUtil.emptyPage());
		pagerUserDto.setSort_("code");
		PageInfo<PagerUserDto> pageInfo = iScorePagerUserService
				.selectAllUserCommit(pagerUserDto, pageNum, pageSize);
		return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
	}

	/**
	 * 专家 查看各个学会提交信息
	 * 
	 * @param scorePaper
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/zjcheckList", method = RequestMethod.GET)
	@ResponseBody
	public Result zjcheckList(
			PagerUserDto pagerUserDto,
			@RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
		PageInfo<PagerUserDto> pageInfo = iScorePagerUserService
				.selectZJUserCommit(pagerUserDto, pageNum, pageSize);
		return ResponseUtil.success(PageConvertUtil.grid(pageInfo));

	}
	
	/**
	 * 专家 获取未完成评分的学会
	 * 
	 * @param scorePaper
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/zjcheckListNotFinished", method = RequestMethod.GET)
	@ResponseBody
	public Result zjcheckListNotFinished(
			PagerUserDto pagerUserDto) {
		List<Map> m = iScorePaperService
				.getiNotFinishedDept(pagerUserDto);
		return ResponseUtil.success(m);

	}
	/**
	 * 专家 获取未完成评分的学会整合
	 * 
	 * @param scorePaper
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/zjcheckListNotFinishedColl", method = RequestMethod.GET)
	@ResponseBody
	public Result zjcheckListNotFinishedColl(
			PagerUserDto pagerUserDto) {
		List<Map> list = iScorePaperService
				.getiNotFinishedDeptColl(pagerUserDto);
		Map m = new HashMap();
		m.put("total", list.size());
		m.put("data", list);
		return ResponseUtil.success(m);

	}
	/**
	 * 专家 获取各个学会提交信息
	 * 
	 * @param scorePaper
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/zjcheckListName", method = RequestMethod.GET)
	@ResponseBody
	public Result zjcheckListName(
			PagerUserDto pagerUserDto) {
		PageInfo<PagerUserDto> pageInfo = iScorePagerUserService
				.selectZJUserCommit(pagerUserDto, 0, 1000);
		return ResponseUtil.success(PageConvertUtil.grid(pageInfo));

	}

	/**
	 * 学会评价表审核列表
	 * 
	 * @param scorePaper
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/reportCheck", method = RequestMethod.GET)
	@ResponseBody
	public Result reportCheck(
			ScorePaper scorePaper,
			@RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
		scorePaper.setStatus(PagerUserDto.PAPERSTATUS_SUBMMIT);
		scorePaper.setSort_("createTime_desc");
		PageInfo<ScorePaper> pageInfo = iScorePagerUserService
				.selectByFilterAndPage(scorePaper, pageNum, pageSize);
		return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
	}

	/**
	 * 获取退回意见
	 * 
	 * @param paperId
	 * @return
	 */
	@RequestMapping(value = "/getFeedback", method = RequestMethod.GET)
	@ResponseBody
	public Result getFeedback(Integer paperId) {
		ScorePaperUser user = this.iScorePagerUserService
				.getCurrentUserScorePaperUser(paperId);
		return ResponseUtil.success(user);
	}

	/**
	 * 开启、关闭 填报
	 */
	@RequestMapping(value = "/paperStatus", method = RequestMethod.POST)
	@ResponseBody
	public Result paperStatus(ScorePaper scorePaper) {
		iScorePaperService.updateNotNull(scorePaper);
		return ResponseUtil.success("操作完成！");
	}

	/**
	 * 评价表审核
	 * 
	 * @param id
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	@ResponseBody
	public Result checkList(int id, short result) {
		iScorePaperService.check(id, result);
		return ResponseUtil.success("操作完成！");
	}

	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public Result insert(
			ScorePaper scorePaper,
			@RequestParam(value = "copyPaperId", required = false) Integer copyPaperId) {
		// String contentJson =
		// iScorePaperService.getContentJson(scorePaper.getTitle());
		// scorePaper.setContentJson(contentJson);
		scorePaper.setApproveStatus(PagerUserDto.PAPERSTATUS_NEW);// 开启填报
		scorePaper.setStatus(PagerUserDto.PAPERSTATUS_END);// 未开启填报
		int result = 0;
		if (copyPaperId == null) {
			result = iScorePaperService.saveNotNull(scorePaper);
		} else {
			result = iScorePaperService.saveNotNull(scorePaper, copyPaperId);
		}
		return result > 0 ? ResponseUtil.success() : ResponseUtil.error();
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Result update(ScorePaper scorePaper) {
		String contentJson = iScorePaperService.getContentJson(
				scorePaper.getId(), scorePaper.getTitle());
		scorePaper.setContentJson(contentJson);
		iScorePaperService.updateNotNull(scorePaper);
		return ResponseUtil.success();
	}

	@RequestMapping(value = "/getPaperJson", method = RequestMethod.POST)
	@ResponseBody
	public Result getPaperJson(Integer paperId, String indexIds, String orgIds) {
		JSONObject contentJson = iScorePaperService.getContentJson(paperId,
				indexIds, orgIds);
		return ResponseUtil.success(contentJson);
	}

	@RequestMapping(value = "/exportPaper", method = RequestMethod.GET)
	public void exportPaper(Integer paperId, String indexIds, String orgIds,@RequestParam(required=false)String type,
			HttpServletRequest request, HttpServletResponse response) {
		HSSFWorkbook wb = iScorePaperService.exportPaper(paperId, indexIds,
				orgIds,type);
		try {
			String p = this.iScorePaperService.selectByKey(paperId).getTitle()+"详细得分情况";
			outWrite(request, response, wb, p);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 导出专家详细打分情况
	 * @param paperId
	 * @param orgIds
	 * @param scoreType
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/exportEPScore", method = RequestMethod.GET)
	public void exportPaper(Integer paperId,String orgIds,String scoreType,
			HttpServletRequest request, HttpServletResponse response) {
		HSSFWorkbook wb = iScorePaperService.exportEPScore(paperId, 
				orgIds,scoreType);
		try {
			String p = this.iScorePaperService.selectByKey(paperId).getTitle()+"专家详细得分情况";
			outWrite(request, response, wb, p);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 导出专家未完成评分的学会
	 * @param paperId
	 * @param orgIds
	 * @param scoreType
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/exportEPNotFinished", method = RequestMethod.GET)
	public void exportEPNotFinished(Integer paperId,
			HttpServletRequest request, HttpServletResponse response) {
		HSSFWorkbook wb = iScorePaperService.exportEPNotFinished(paperId);
		try {
			String p = this.iScorePaperService.selectByKey(paperId).getTitle()+"专家未完成情况";
			outWrite(request, response, wb, p);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/exportPaperScore", method = RequestMethod.GET)
	public void exportPaperScore(PagerUserDto pagerUserDto,
			HttpServletRequest request, HttpServletResponse response) {
		List<PagerUserDto> list = iScorePagerUserService
				.selectAllUserCommit(pagerUserDto);
		 String[] fields = "code,userName,score".split(",");
	        String[] names = "学会编码,学会,分数".split(",");
	        try {
	        	String name = "分数导出";
	        	name = list.get(0).getTitle()+name;
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
			//保存在本地
			String filename = fileName+".xls";
	        String dicPath = new File(".").getCanonicalPath();
	        String srcPath = dicPath +"/Excel/"+ filename;
	        File newPath = new File(dicPath+"/Excel/");
	        if(!newPath.exists())
	        	newPath.mkdirs();
	        File file = new File(srcPath);
	        System.out.println(srcPath);
	        FileOutputStream fos = new FileOutputStream(file);
	        wb.write(fos);// 写文件
	        //输出到网络
	        //if(1==1)return;
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

	@RequestMapping(value = "/load/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Result load(@PathVariable(value = "id") Integer id) {
		ScorePaper scorePaper = iScorePaperService.selectByKey(id);
		return ResponseUtil.success(scorePaper);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ResponseBody
	public Result delete(@RequestParam(value = "id") Integer id) {
		iScorePaperService.delete(id);
		return ResponseUtil.success();
	}

	/**
	 * 提交到 地方学会审核答案
	 * 
	 * @param id
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/reportContentSubmit", method = RequestMethod.GET)
	@ResponseBody
	public Result reportContentSubmit(int id) {
		iScorePagerUserService
				.check(id, PagerUserDto.PAPERSTATUS_SUBMMIT, null);
		return ResponseUtil.success("操作完成！");
	}

	/**
	 * 地方学会审核
	 * 
	 * @param id
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/reportContentCheck", method = RequestMethod.GET)
	@ResponseBody
	public Result reportContentCheck(int id, short result,
			@RequestParam(value = "feedback", required = false) String feedback) {
		iScorePagerUserService.check(id, result, feedback);
		return ResponseUtil.success("操作完成！");
	}

	/**
	 * 中科协特殊渠道退回到填报员
	 * 
	 * @param id
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/reportBack", method = RequestMethod.GET)
	@ResponseBody
	public Result reportContentCheck(int id, int userId,
			@RequestParam(value = "feedback", required = false) String feedback) {
		iScorePagerUserService.check(id, new Short("0"), userId, feedback);
		return ResponseUtil.success("操作完成！");
	}

	/**
	 * 填報用户评价表 列表
	 * 
	 * @param scorePaper
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/reportList", method = RequestMethod.GET)
	@ResponseBody
	public Result reportList(
			ScorePaper scorePaper,
			@RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
		scorePaper.setApproveStatus(PagerUserDto.PAPERSTATUS_EGIS);// 审核通过的
		scorePaper.setSort_("createTime_desc");
		PageInfo<ScorePaper> pageInfo = iScorePagerUserService
				.selectByFilterAndPage(scorePaper, pageNum, pageSize);
		return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
	}

	/**
	 * 提交答案
	 * 
	 * @param paperAnswerDto
	 * @return
	 */
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	@ResponseBody
	public Result submit(@RequestBody PaperAnswerDto paperAnswerDto) {
		SecurityUser user = SecurityUtil.getCurrentSecurityUser();
		String CUname = user.getLoginName();
		String Mname = CUname;
		Integer userId = user.getId();
		// TODO 审核员 查看 填报员填报得信息 Mname =
		// CUname.substring(0,CUname.lastIndexOf("-001"))+"-002";
		if (CUname.endsWith("001")) {// 如果学会审核员
			Mname = CUname.substring(0, CUname.lastIndexOf("001")) + "002";
			userId = userService.findUserByLoginName(Mname).getId();
		} else if (CUname.endsWith("002")) {
			// Mname = CUname ;
		} else {// 如果不是学会审核员 返回空

		}
		if (paperAnswerDto.getPaperId() != null
				&& CollectionUtils.isNotEmpty(paperAnswerDto.getAnswers())) {
			for (AnswerDto a : paperAnswerDto.getAnswers()) {
				ScoreAnswer sa = new ScoreAnswer();
				sa.setUserId(userId);
				sa.setPaperId(paperAnswerDto.getPaperId());
				Integer itemId = a.getItemId();
				sa.setItemId(itemId);
				List<ScoreAnswer> list = iScoreAnswerService.selectByFilter(sa);
				if (list.size() > 0) {
					for (ScoreAnswer scoreAnswer : list) {
						iScoreAnswerService.delete(scoreAnswer.getId());
					}
				}
				ScoreItem scoreItem = iScoreItemService.selectByKey(itemId);
				sa.setItemScore(scoreItem.getScore());
				Object itemValue = a.getItemValue();
				sa.setAnswerValue((String) itemValue);
				sa.setAnswerFile(a.getItemFile());
				iScoreAnswerService.divScore(scoreItem,sa);
				iScoreAnswerService.saveNotNull(sa);
			}
		}
		return ResponseUtil.success();
	}


	/**
	 * 获取当前用户的答案
	 * 
	 * @param paperId
	 * @return
	 */
	@RequestMapping(value = "/getAnswer", method = RequestMethod.POST)
	@ResponseBody
	public Result getAnswer(@RequestParam(value = "paperId") Integer paperId,
			@RequestParam(value = "deptId", required = false) Integer deptId,
			@RequestParam(value = "userId", required = false) Integer userId) {
		if (userId == null) {
			SecurityUser user = SecurityUtil.getCurrentSecurityUser();
			String CUname = user.getLoginName();
			String Mname = CUname;
			userId = user.getId();
			// TODO 审核员 查看 填报员填报得信息 Mname =
			// CUname.substring(0,CUname.lastIndexOf("-001"))+"-002";
			if (CUname.endsWith("001")) {// 如果学会审核员
				Mname = CUname.substring(0, CUname.lastIndexOf("001")) + "002";
				userId = userService.findUserByLoginName(Mname).getId();
			} else if (CUname.endsWith("002")) {
				// Mname = CUname ;
			} else {// 如果不是学会审核员 返回空

			}
			if(deptId!=null){
				Dept dept = iDeptService.selectByKey(deptId);
				if(dept!=null){
					Mname = dept.getCode() + "002";
					userId = userService.findUserByLoginName(Mname).getId();
				}
			}

		}
		ScoreAnswer scoreAnswer = new ScoreAnswer();
		scoreAnswer.setUserId(userId);
		scoreAnswer.setPaperId(paperId);
		scoreAnswer.setSort_("item_id_asc");
		List<ScoreAnswer> ans = iScoreAnswerService.selectByFilter(scoreAnswer);
		return ResponseUtil.success(ans);
	}

	/**
	 * 获取所有评价表 下拉框
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getPaperOptions", method = RequestMethod.GET)
	@ResponseBody
	public Result getPaperOptions() {
		Example example = new Example(ScorePaper.class);
		example.setOrderByClause("create_time desc");
		// TODO 查询编辑状态的
		List<ScorePaper> list = iScorePaperService.selectByExample(example);
		List<Map> maps = new ArrayList<Map>();
		for (ScorePaper s : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", s.getTitle());
			map.put("id", s.getId());
			map.put("score", s.getScore());
			maps.add(map);
		}
		return ResponseUtil.success(maps);
	}

	/**
	 * 获取所有评价表 下拉框
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getPaperSelect")
	@ResponseBody
	public Object getPaperSelect(String status) {
		Example example = new Example(ScorePaper.class);
		example.setOrderByClause("create_time desc");
		// TODO 查询编辑状态的
		List<ScorePaper> list = iScorePaperService.selectByExample(example);
		List<Map> maps = new ArrayList<Map>();
		for (ScorePaper s : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("text", s.getTitle());
			map.put("value", s.getId());
			map.put("score", s.getScore());
			map.put("status", s.getStatus());
			maps.add(map);
		}
		return maps;
	}

	/**
	 * 获取所有答题不真实的item
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getPaperAnswerReal", method = RequestMethod.GET)
	@ResponseBody
	public Result getPaperAnswerReal(Integer paperId, Integer userId) {
		Example example = new Example(ScoreAnswer.class);
		Criteria c = example.createCriteria();
		c.andEqualTo("paperId", paperId);
		c.andEqualTo("userId", userId);
		c.andEqualTo("answerReal", false);
		List<ScoreAnswer> list = iScoreAnswerService.selectByExample(example);
		return ResponseUtil.success(list);
	}

	/**
	 * 更新答题的真实情况
	 * 
	 * @return
	 */
	@RequestMapping(value = "/updateAnswerReal", method = RequestMethod.POST)
	@ResponseBody
	public Result updateAnswerReal(Integer paperId, Integer userId,
			Integer itemId, Boolean answerReal) {
		Example example = new Example(ScoreAnswer.class);
		Criteria c = example.createCriteria();
		c.andEqualTo("paperId", paperId);
		c.andEqualTo("userId", userId);
		c.andEqualTo("itemId", itemId);
		List<ScoreAnswer> list = iScoreAnswerService.selectByExample(example);
		if (list.size() > 0) {
			ScoreAnswer one = list.get(0);
			one.setAnswerReal(answerReal);
			if (!answerReal) {
				one.setAnswerScore(new BigDecimal(0));
			}
			iScoreAnswerService.updateAll(one);
			return ResponseUtil.success();
		}
		return ResponseUtil.error();
	}

	/**
	 * 专家评分
	 * 更新答题的分数
	 * V2 多个专家给一个题打分 取平均分
	 * @return
	 */
	@RequestMapping(value = "/updateAnswerScore", method = RequestMethod.POST)
	@ResponseBody
	public Result updateAnswerScore(Integer paperId, Integer userId,
			Integer itemId, BigDecimal answerScore) {
		Example example = new Example(ScoreAnswer.class);
		Criteria c = example.createCriteria();
		c.andEqualTo("paperId", paperId);
		c.andEqualTo("userId", userId);
		c.andEqualTo("itemId", itemId);
		SecurityUser user = SecurityUtil.getCurrentSecurityUser();
		Integer cuserId = user.getId();
		if(user.getUserType()!=4){
			return ResponseUtil.error("专家才能评分");
		}
		List<ScoreAnswer> list = iScoreAnswerService.selectByExample(example);
		if (list.size() == 0) {
			ScoreAnswer one = new ScoreAnswer();
			one.setPaperId(paperId);
			one.setUserId(userId);
			one.setItemId(itemId);
			one.setAnswerReal(true);
			one.setAnswerValue("");
			one.setAnswerScore(new BigDecimal(0));
			one.setAnswerReason("");
			try{
				ScoreItem n= iScoreItemService.selectByKey(itemId);
				one.setItemScore(n.getScore());
				one.setIndexId(n.getIndexId());
			}catch(Exception e){
				one.setItemScore(new BigDecimal(0));
				
			}
			iScoreAnswerService.saveNotNull(one);
			list.add(one);
			//return ResponseUtil.error("用户未答题不能评分");
		}
		ScoreAnswer one = list.get(0);
		if (!one.getAnswerReal()) {
			one.setAnswerScore(new BigDecimal(0));
			iScoreAnswerService.updateAll(one);
			return ResponseUtil.error("信息虚假，不能修改分数");
		}
		ExpertItemScore  entity = new ExpertItemScore();
		entity.setExpertId(null);
		entity.setItemId(itemId);
		entity.setExpertUserId(cuserId);
		entity.setOrgUserId(userId);
		entity.setPaperId(paperId);
		List<ExpertItemScore> ls = iExpertItemScoreService.selectByFilter(entity);
		if(ls.size()==0){
			entity.setItemScore(answerScore);
			iExpertItemScoreService.save(entity);
		}else if(ls.size()==1){
			entity.setItemScore(answerScore);
			Example ex = new Example(ExpertItemScore.class);
			Criteria c1 = ex.createCriteria();
			if(entity.getPaperId()!=null)
			c1.andEqualTo("paperId", entity.getPaperId());
			if(entity.getOrgUserId()!=null)
			c1.andEqualTo("orgUserId", entity.getOrgUserId());
			if(entity.getItemId()!=null)
			c1.andEqualTo("itemId", entity.getItemId());
			if(entity.getExpertUserId()!=null)
			c1.andEqualTo("expertUserId", entity.getExpertUserId());
			if(entity.getExpertId()!=null)
			c1.andEqualTo("expertId", entity.getExpertId());
			iExpertItemScoreService.updateByExample(entity,ex);
		}else{//删除后添加
			//iExpertItemScoreService.deleteByExample(ex)
		}
		BigDecimal score = iExpertItemScoreService.divScore(one);
		one.setAnswerScore(score);
		iScoreAnswerService.updateAll(one);
		return ResponseUtil.success();
	}
	/**
	 * 获取当前专家打的分数
	 * @param paperId
	 * @param userId
	 * @return
	 */
	@RequestMapping("getExperScore")
	@ResponseBody
	public Object getExperScore(Integer paperId,Integer userId){
		ExpertItemScore  entity = new ExpertItemScore();
		entity.setPaperId(paperId);
		entity.setOrgUserId(userId);
		entity.setExpertUserId(SecurityUtil.getCurrentUserId());
		List<ExpertItemScore> list = iExpertItemScoreService.selectByFilter(entity);
		
		return ResponseUtil.success(list);
	}

	/**
	 * 计算得分
	 * 未用到
	 * @return
	 */
	@RequestMapping(value = "/getUserScore", method = RequestMethod.GET)
	@ResponseBody
	public Result getUserScore(Integer paperId, Integer userId) {
		BigDecimal score = this.iScoreAnswerService.getUserScore(paperId,
				userId);
		Example example = new Example(ScorePaperUser.class);
		example.createCriteria().andEqualTo("paperId", paperId)
				.andEqualTo("userId", userId);
		List<ScorePaperUser> list = this.iScorePagerUserService
				.selectByExample(example);
		if (list.size() > 0) {
			ScorePaperUser user = list.get(0);
			user.setScore(score);
			iScorePagerUserService.updateNotNull(user);
		}
		return ResponseUtil.success(score);
	}

	/**
	 * 计算所有得分
	 * 
	 * @return
	 */
	@RequestMapping(value = "/updatePaperScore", method = RequestMethod.GET)
	@ResponseBody
	public Result updateUserScore(Integer paperId) {
		this.iScorePagerUserService.updatePaperScore(paperId);
		return ResponseUtil.success();
	}

	/**
	 * 更新附加分数 主观分
	 */
	@RequestMapping(value = "/updateSubjectiveScore", method = RequestMethod.GET)
	@ResponseBody
	public Result updateSubjectiveScore(Integer paperId, Integer userId,
			BigDecimal subjectiveScore) {
		Example example = new Example(ScorePaperUser.class);
		example.createCriteria().andEqualTo("paperId", paperId)
				.andEqualTo("userId", userId);
		List<ScorePaperUser> list = this.iScorePagerUserService
				.selectByExample(example);
		if (list.size() > 0) {
			ScorePaperUser user = list.get(0);
			user.setSubjectiveScore(subjectiveScore);
			iScorePagerUserService.updateNotNull(user);
		}
		return ResponseUtil.success();
	}

	@RequestMapping("getAnswerOfRanking")
	@ResponseBody
	public Result getAnswerOfRanking(Integer itemId, String answer) {
		String rank = iScoreAnswerService.getAnswerOfRanking(itemId, answer);
		return ResponseUtil.success(rank);
	}

	/**
	 * 专家获取试卷内容
	 * 专家和科协用户 才能获得
	 * @param itemId
	 * @param answer
	 * @return
	 */
	@RequestMapping("getPaper")
	@ResponseBody
	public Object getPaper(Integer paperId) {
		String contentJson = this.iScorePaperService
				.getCurrentUserPaper(paperId);
		if (contentJson == null)
			return ResponseUtil.error("没有权限获取数据");
		return ResponseUtil.success(contentJson);
	}
	
	/**
	 * 根据权重重新计算题目分数
	 * @param paperId
	 * @return
	 */
	@RequestMapping(value = "/updateWightScore", method = RequestMethod.POST)
	@ResponseBody
	public Result updateWightScore(Integer paperId) {
		iScorePaperService.updateWightScore(paperId);
		return ResponseUtil.success();
	}
	
	/**
	 * 重新计算用户填报数据的分数
	 * @param paperId
	 * @return
	 */
	@RequestMapping(value = "/updateWightUserScore", method = RequestMethod.POST)
	@ResponseBody
	public Result updateWightUserScore(Integer paperId) {
		iScorePaperService.updateWightUserScore(paperId);
		
		return ResponseUtil.success();
	}
}
