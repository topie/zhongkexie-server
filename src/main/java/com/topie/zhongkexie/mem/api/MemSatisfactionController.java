package com.topie.zhongkexie.mem.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.utils.PageConvertUtil;
import com.topie.zhongkexie.common.utils.ResponseUtil;
import com.topie.zhongkexie.common.utils.Result;
import com.topie.zhongkexie.core.dto.PagerUserDto;
import com.topie.zhongkexie.database.mem.model.MemPaperItem;
import com.topie.zhongkexie.database.mem.model.MemUserScore;
import com.topie.zhongkexie.mem.service.IMemPaperItemService;
import com.topie.zhongkexie.mem.service.IMemUserScoreService;

@Controller
@RequestMapping("/api/mem/score")
public class MemSatisfactionController {
	@Autowired
	private IMemPaperItemService iMemPaperItemService;
	@Autowired
	private IMemUserScoreService iMemUserScoreService;

	@RequestMapping("list")
	@ResponseBody
	public Result list(
			PagerUserDto pagerUserDto,
			@RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
		PageInfo<PagerUserDto> pageInfo = iMemUserScoreService
				.selectByFilterAndPage(pagerUserDto, pageNum, pageSize);
		return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
	}

	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public Result insert(MemPaperItem dept) {
		int result = iMemPaperItemService.saveNotNull(dept);
		return result > 0 ? ResponseUtil.success() : ResponseUtil.error();
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Result update(MemPaperItem dept) {
		int result = iMemPaperItemService.updateNotNull(dept);
		return result > 0 ? ResponseUtil.success() : ResponseUtil.error();
	}

	@RequestMapping(value = "/load/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Result load(@PathVariable(value = "id") Integer id) {
		MemPaperItem dept = iMemPaperItemService.selectByKey(id);
		return ResponseUtil.success(dept);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ResponseBody
	public Result delete(@RequestParam(value = "id") Integer id) {
		iMemPaperItemService.delete(id);
		return ResponseUtil.success();
	}

	@RequestMapping("itemList")
	@ResponseBody
	public Result itemList(
			MemPaperItem memPaperItem,
			@RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
		PageInfo<MemPaperItem> pageInfo = iMemPaperItemService
				.selectByFilterAndPage(memPaperItem, pageNum, pageSize);
		return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
	}

	@RequestMapping(value = "/scoreUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Result scoreUpdate(MemUserScore memUserScore) {
		MemPaperItem memPaperItem = new MemPaperItem();
		memPaperItem.setPaperId(memUserScore.getPaperId());
		List<MemPaperItem> list = iMemPaperItemService
				.selectByFilter(memPaperItem);
		if (list.size() != 1) {
			return ResponseUtil.error("后台配置错误，请指定一个试题");
		}
		Integer itemId = list.get(0).getItemId();
		memUserScore.setItemId(itemId);
		int result = 0;
		if (memUserScore.getId() == null) {
			List<MemUserScore> ls = iMemUserScoreService.selectByFilter(memUserScore);
			if(ls.size()>0){
				MemUserScore ety = ls.get(0);
				ety.setScore(memUserScore.getScore());
				result = iMemUserScoreService.updateNotNull(ety);
			}else{
				result = iMemUserScoreService.saveNotNull(memUserScore);
			}
		} else {
			result = iMemUserScoreService.updateNotNull(memUserScore);
		}
		iMemUserScoreService.divScore(memUserScore);
		
		return result > 0 ? ResponseUtil.success() : ResponseUtil.error();
	}

}
