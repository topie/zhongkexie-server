package com.topie.zhongkexie.core.api;

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
import com.topie.zhongkexie.common.utils.PageConvertUtil;
import com.topie.zhongkexie.common.utils.ResponseUtil;
import com.topie.zhongkexie.common.utils.Result;
import com.topie.zhongkexie.core.dto.AnswerDto;
import com.topie.zhongkexie.core.dto.PagerUserDto;
import com.topie.zhongkexie.core.dto.PaperAnswerDto;
import com.topie.zhongkexie.core.service.IScoreAnswerService;
import com.topie.zhongkexie.core.service.IScoreItemOptionService;
import com.topie.zhongkexie.core.service.IScoreItemService;
import com.topie.zhongkexie.core.service.IScorePagerUserService;
import com.topie.zhongkexie.core.service.IScorePaperService;
import com.topie.zhongkexie.database.core.model.ScoreAnswer;
import com.topie.zhongkexie.database.core.model.ScoreItem;
import com.topie.zhongkexie.database.core.model.ScoreItemOption;
import com.topie.zhongkexie.database.core.model.ScorePaper;
import com.topie.zhongkexie.database.core.model.ScorePaperUser;
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
		PageInfo<PagerUserDto> pageInfo = iScorePagerUserService
				.selectAllUserCommit(pagerUserDto, pageNum, pageSize);
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
		PageInfo<ScorePaper> pageInfo = iScorePagerUserService
				.selectByFilterAndPage(scorePaper, pageNum, pageSize);
		return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
	}
	
	/**
	 * 获取退回意见
	 * @param paperId
	 * @return
	 */
	@RequestMapping(value = "/getFeedback", method = RequestMethod.GET)
	@ResponseBody
	public Result getFeedback(
			Integer paperId) {
		ScorePaperUser user = this.iScorePagerUserService.getCurrentUserScorePaperUser(paperId);
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
	public void exportPaper(Integer paperId, String indexIds, String orgIds,
			HttpServletRequest request, HttpServletResponse response) {
		HSSFWorkbook wb = iScorePaperService.exportPaper(paperId, indexIds,
				orgIds);
		try {
			String p = this.iScorePaperService.selectByKey(paperId).getTitle();
			outWrite(request, response, wb, p);
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
			//fileName=new String((fileName+"导出数据.xls").getBytes(), "ISO_8859_1");
			fileName=URLEncoder.encode(fileName+"导出数据.xls","UTF-8");
			response.setHeader("Content-disposition", "attachment; filename="
					+ fileName);
//			response.setHeader("Content-disposition", "attachment;filename*=UTF-8 "
//					+ URLEncoder.encode(fileName,"UTF-8"));
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
		iScorePagerUserService.check(id, PagerUserDto.PAPERSTATUS_SUBMMIT,null);
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
			@RequestParam(value="feedback",required=false)String feedback) {
		iScorePagerUserService.check(id, result,feedback);
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
			@RequestParam(value="feedback",required=false)String feedback) {
		iScorePagerUserService.check(id,new Short("0") ,userId,feedback);
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
		Integer userId = SecurityUtil.getCurrentUserId();
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
				if (scoreItem.getType() == 0) {
					// 填空
					String logic = scoreItem.getOptionLogic();
				} else if (scoreItem.getType() == 1) {
					Integer optionId = Integer.parseInt((String) itemValue);
					ScoreItemOption option = iScoreItemOptionService
							.selectByKey(optionId);
					sa.setAnswerScore(scoreItem.getScore().multiply(
							option.getOptionRate()));
					// 单选
				} else if (scoreItem.getType() == 2) {
					// 多选
					String logic = scoreItem.getOptionLogic();
				}
				iScoreAnswerService.saveNotNull(sa);
				// TODO 计算分数
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
		List<ScorePaper> list = iScorePaperService.selectByExample(example);
		List<Map> maps = new ArrayList<Map>();
		for (ScorePaper s : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", s.getTitle());
			map.put("id", s.getId());
			maps.add(map);
		}
		return ResponseUtil.success(maps);
	}
	
	/**
	 * 获取所有答题不真实的item
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getPaperAnswerReal", method = RequestMethod.GET)
	@ResponseBody
	public Result getPaperAnswerReal(Integer paperId,Integer userId) {
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
	public Result updateAnswerReal(Integer paperId,Integer userId,Integer itemId,Boolean answerReal) {
		Example example = new Example(ScoreAnswer.class);
		Criteria c = example.createCriteria();
		c.andEqualTo("paperId", paperId);
		c.andEqualTo("userId", userId);
		c.andEqualTo("itemId", itemId);
		List<ScoreAnswer> list = iScoreAnswerService.selectByExample(example);
		if(list.size()>0){
			ScoreAnswer one = list.get(0);
			one.setAnswerReal(answerReal);
			iScoreAnswerService.updateAll(one);
			return ResponseUtil.success();
		}
		return ResponseUtil.error();
	}
	
	/**
	 * 更新答题的分数
	 * 
	 * @return
	 */
	@RequestMapping(value = "/updateAnswerScore", method = RequestMethod.POST)
	@ResponseBody
	public Result updateAnswerScore(Integer paperId,Integer userId,Integer itemId,BigDecimal answerScore) {
		Example example = new Example(ScoreAnswer.class);
		Criteria c = example.createCriteria();
		c.andEqualTo("paperId", paperId);
		c.andEqualTo("userId", userId);
		c.andEqualTo("itemId", itemId);
		List<ScoreAnswer> list = iScoreAnswerService.selectByExample(example);
		if(list.size()>0){
			ScoreAnswer one = list.get(0);
			one.setAnswerScore(answerScore);
			iScoreAnswerService.updateAll(one);
			return ResponseUtil.success();
		}
		return ResponseUtil.error();
	}
	

}
