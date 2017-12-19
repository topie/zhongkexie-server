package com.topie.zhongkexie.core.api;

import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.utils.PageConvertUtil;
import com.topie.zhongkexie.common.utils.ResponseUtil;
import com.topie.zhongkexie.common.utils.Result;
import com.topie.zhongkexie.core.service.IScorePaperService;
import com.topie.zhongkexie.database.core.model.ScorePaper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by chenguojun on 2017/4/19.
 */
@Controller
@RequestMapping("/api/core/scorePaper")
public class ScorePaperController {

    @Autowired
    private IScorePaperService iScorePaperService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(ScorePaper scorePaper,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        PageInfo<ScorePaper> pageInfo = iScorePaperService.selectByFilterAndPage(scorePaper, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public Result insert(ScorePaper scorePaper) {
        String contentJson = iScorePaperService.getContentJson(scorePaper.getTitle());
        scorePaper.setContentJson(contentJson);
        int result = iScorePaperService.saveNotNull(scorePaper);
        return result > 0 ? ResponseUtil.success() : ResponseUtil.error();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(ScorePaper scorePaper) {
        String contentJson = iScorePaperService.getContentJson(scorePaper.getTitle());
        scorePaper.setContentJson(contentJson);
        iScorePaperService.updateNotNull(scorePaper);
        return ResponseUtil.success();
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
    
    @RequestMapping(value = "/checkList", method = RequestMethod.GET)
    @ResponseBody
    public Result checkList(ScorePaper scorePaper,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        PageInfo<ScorePaper> pageInfo = iScorePaperService.selectByFilterAndPage(scorePaper, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

}
