package com.topie.zhongkexie.core.api;

import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.utils.PageConvertUtil;
import com.topie.zhongkexie.common.utils.ResponseUtil;
import com.topie.zhongkexie.common.utils.Result;
import com.topie.zhongkexie.core.service.IScoreItemOptionService;
import com.topie.zhongkexie.database.core.model.ScoreItemOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by chenguojun on 2017/4/19.
 */
@Controller
@RequestMapping("/api/core/scoreItemOption")
public class ScoreItemOptionController {

    @Autowired
    private IScoreItemOptionService iScoreItemOptionService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(ScoreItemOption scoreItemOption,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        PageInfo<ScoreItemOption> pageInfo = iScoreItemOptionService.selectByFilterAndPage(scoreItemOption, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public Result insert(ScoreItemOption scoreItemOption) {
        int result = iScoreItemOptionService.saveNotNull(scoreItemOption);
        return result > 0 ? ResponseUtil.success() : ResponseUtil.error();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(ScoreItemOption scoreItemOption) {
        iScoreItemOptionService.updateNotNull(scoreItemOption);
        return ResponseUtil.success();
    }

    @RequestMapping(value = "/load/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Result load(@PathVariable(value = "id") Integer id) {
        ScoreItemOption scoreItemOption = iScoreItemOptionService.selectByKey(id);
        return ResponseUtil.success(scoreItemOption);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public Result delete(@RequestParam(value = "id") Integer id) {
        iScoreItemOptionService.delete(id);
        return ResponseUtil.success();
    }

}
