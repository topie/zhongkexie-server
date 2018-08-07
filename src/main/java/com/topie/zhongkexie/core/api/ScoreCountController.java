package com.topie.zhongkexie.core.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
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
}
