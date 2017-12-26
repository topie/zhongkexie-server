package com.topie.zhongkexie.core.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import tk.mybatis.mapper.entity.Example;

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

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(ScorePaper scorePaper,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        PageInfo<ScorePaper> pageInfo = iScorePaperService.selectByFilterAndPage(scorePaper, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @RequestMapping(value = "/reportCheck", method = RequestMethod.GET)
    @ResponseBody
    public Result reportCheck(ScorePaper scorePaper,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        PageInfo<ScorePaper> pageInfo = iScorePaperService.selectByFilterAndPage(scorePaper, pageNum, pageSize);
        List<PagerUserDto> listDto = new ArrayList<PagerUserDto>();
        for (ScorePaper sp : pageInfo.getList()) {
            PagerUserDto dto = new PagerUserDto();
            BeanUtils.copyProperties(sp, dto);
            ScorePaperUser dScorePaperUser = iScorePagerUserService.selectByKey(sp.getId());
            if (dScorePaperUser != null) {
                dto.setCheckStatus(dScorePaperUser.getStatus());
            }
            listDto.add(dto);
        }
        return ResponseUtil.success(PageConvertUtil.grid(new PageInfo<>(listDto)));
    }

    @RequestMapping(value = "/check", method = RequestMethod.GET)
    @ResponseBody
    public Result checkList(int id, short result) {
        iScorePaperService.check(id, result);
        return ResponseUtil.success("操作完成！");
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public Result insert(ScorePaper scorePaper) {
        //String contentJson = iScorePaperService.getContentJson(scorePaper.getTitle());
        //scorePaper.setContentJson(contentJson);
        int result = iScorePaperService.saveNotNull(scorePaper);
        return result > 0 ? ResponseUtil.success() : ResponseUtil.error();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(ScorePaper scorePaper) {
        String contentJson = iScorePaperService.getContentJson(scorePaper.getId(),scorePaper.getTitle());
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

    @RequestMapping(value = "/reportContentCheck", method = RequestMethod.GET)
    @ResponseBody
    public Result reportContentCheck(int id, short result) {
        iScorePagerUserService.check(id, result);
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
                iScoreAnswerService.saveNotNull(sa);
                //todo 计算分数
            }
        }
        return ResponseUtil.success();
    }

    @RequestMapping(value = "/getAnswer", method = RequestMethod.POST)
    @ResponseBody
    public Result getAnswer(@RequestParam(value = "paperId") Integer paperId) {
        Integer userId = SecurityUtil.getCurrentUserId();
        ScoreAnswer scoreAnswer = new ScoreAnswer();
        scoreAnswer.setUserId(userId);
        scoreAnswer.setPaperId(paperId);
        scoreAnswer.setSort_("item_id_asc");
        List<ScoreAnswer> ans = iScoreAnswerService.selectByFilter(scoreAnswer);
        return ResponseUtil.success(ans);
    }
    
    @RequestMapping(value="/getPaperOptions",method=RequestMethod.GET)
    @ResponseBody
    public Result getPaperOptions(){
    	Example example = new Example(ScorePaper.class);
    	example.setOrderByClause("create_time desc");
    	List<ScorePaper> list = iScorePaperService.selectByExample(example);
    	List<Map> maps = new ArrayList<Map>();
    	for(ScorePaper s:list){
    		Map<String,Object> map = new HashMap<String,Object>();
    		map.put("name", s.getTitle());
    		map.put("id", s.getId());
    		maps.add(map);
    	}
    	return ResponseUtil.success(maps);
    }

}
