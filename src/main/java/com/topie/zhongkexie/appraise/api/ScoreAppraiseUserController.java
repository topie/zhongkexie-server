package com.topie.zhongkexie.appraise.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.appraise.service.IScoreAppraiseUserService;
import com.topie.zhongkexie.common.utils.PageConvertUtil;
import com.topie.zhongkexie.common.utils.ResponseUtil;
import com.topie.zhongkexie.common.utils.Result;
import com.topie.zhongkexie.core.dto.AnswerDto;
import com.topie.zhongkexie.core.dto.PaperAnswerDto;
import com.topie.zhongkexie.core.service.IScoreItemService;
import com.topie.zhongkexie.database.appriase.model.ScoreAppraiseUser;
import com.topie.zhongkexie.database.core.model.ScoreItem;
import com.topie.zhongkexie.security.utils.SecurityUtil;

@Controller
@RequestMapping("/api/score/appraise")
public class ScoreAppraiseUserController {
	
	@Autowired
    private IScoreAppraiseUserService iScoreAppraiseUserService;
	@Autowired
	private IScoreItemService iScoreItemService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(ScoreAppraiseUser scoreAppraiseUser,
    		@RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        PageInfo<ScoreAppraiseUser> pageInfo = iScoreAppraiseUserService.selectByFilterAndPage(scoreAppraiseUser, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }
    
    @RequestMapping(value = "/userItems", method = RequestMethod.GET)
    @ResponseBody
    public Result userItems(ScoreAppraiseUser scoreAppraiseUser){
    	if(scoreAppraiseUser.getUserId()==null){
	    	int userId = SecurityUtil.getCurrentUserId();
	    	scoreAppraiseUser.setUserId(userId);
    	}
        List<ScoreAppraiseUser> list = iScoreAppraiseUserService.selectByFilter(scoreAppraiseUser);
        List<ScoreItem> result = new ArrayList<ScoreItem>();
        for(ScoreAppraiseUser au:list){
        	ScoreItem i = iScoreItemService.selectByKey(au.getItemId());
        	if(i!=null){
        		result.add(i);
        	}
        }
        Map map = new HashMap();
        map.put("values", list);
        map.put("items", result);
        return ResponseUtil.success(map);
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public Result insert(ScoreAppraiseUser scoreAppraiseUser) {
        int result = iScoreAppraiseUserService.saveNotNull(scoreAppraiseUser);
        return result > 0 ? ResponseUtil.success() : ResponseUtil.error();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(ScoreAppraiseUser scoreAppraiseUser) {
        iScoreAppraiseUserService.updateNotNull(scoreAppraiseUser);
        return ResponseUtil.success();
    }
    
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    @ResponseBody
    public Result submit(@RequestBody PaperAnswerDto paperAnswerDto) {
    	Integer userId = SecurityUtil.getCurrentUserId();
		if (CollectionUtils.isNotEmpty(paperAnswerDto.getAnswers())) {
			for (AnswerDto a : paperAnswerDto.getAnswers()) {
				ScoreAppraiseUser scoreAppraiseUser = new ScoreAppraiseUser();
				scoreAppraiseUser.setUserId(userId);
				scoreAppraiseUser.setItemId(a.getItemId());
				List<ScoreAppraiseUser> list = this.iScoreAppraiseUserService.selectByFilter(scoreAppraiseUser);
				if(list.size()>0){
					ScoreAppraiseUser an = list.get(0);
					an.setItemValue((String)a.getItemValue());
					iScoreAppraiseUserService.updateNotNull(an,true);
				}else{
					
				}
			}
		}
        return ResponseUtil.success();
    }

    @RequestMapping(value = "/load/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Result load(@PathVariable(value = "id") Integer id) {
        ScoreAppraiseUser scoreAppraiseUser = iScoreAppraiseUserService.selectByKey(id);
        return ResponseUtil.success(scoreAppraiseUser);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public Result delete(@RequestParam(value = "id") Integer id) {
        iScoreAppraiseUserService.delete(id);
        return ResponseUtil.success();
    }
}
