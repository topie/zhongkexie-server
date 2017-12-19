package com.topie.zhongkexie.core.api;

import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.utils.PageConvertUtil;
import com.topie.zhongkexie.common.utils.ResponseUtil;
import com.topie.zhongkexie.common.utils.Result;
import com.topie.zhongkexie.core.dto.AnswerDto;
import com.topie.zhongkexie.core.dto.PaperAnswerDto;
import com.topie.zhongkexie.core.service.IScoreAnswerService;
import com.topie.zhongkexie.core.service.IScoreItemOptionService;
import com.topie.zhongkexie.core.service.IScoreItemService;
import com.topie.zhongkexie.core.service.IScorePaperService;
import com.topie.zhongkexie.database.core.model.ScoreAnswer;
import com.topie.zhongkexie.database.core.model.ScoreItem;
import com.topie.zhongkexie.database.core.model.ScoreItemOption;
import com.topie.zhongkexie.database.core.model.ScorePaper;
import com.topie.zhongkexie.security.utils.SecurityUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    
    @RequestMapping(value = "/check", method = RequestMethod.GET)
    @ResponseBody
    public Result checkList(int id,short result) {
         iScorePaperService.check(id,result);
        return ResponseUtil.success("操作完成！");
    }
    
    @RequestMapping(value = "/reportList", method = RequestMethod.GET)
    @ResponseBody
    public Result reportList(ScorePaper scorePaper,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        PageInfo<ScorePaper> pageInfo = iScorePaperService.selectByFilterAndPage(scorePaper, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    @ResponseBody
    public Result submit(@RequestBody PaperAnswerDto paperAnswerDto) {
        Integer userId = SecurityUtil.getCurrentUserId();
        if (paperAnswerDto.getPaperId() != null && CollectionUtils.isNotEmpty(paperAnswerDto.getAnswers())) {
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
                    //填空
                    String logic = scoreItem.getOptionLogic();
                } else if (scoreItem.getType() == 1) {
                    Integer optionId = Integer.parseInt((String) itemValue);
                    ScoreItemOption option = iScoreItemOptionService.selectByKey(optionId);
                    sa.setAnswerScore(scoreItem.getScore().multiply(option.getOptionRate()));
                    //单选
                } else if (scoreItem.getType() == 2) {
                    //多选
                    String logic = scoreItem.getOptionLogic();
                }

                //todo 计算分数
                iScoreAnswerService.saveNotNull(sa);
            }
        }
        return ResponseUtil.success();
    }

}
