package com.topie.zhongkexie.core.api;

import java.util.Set;

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
import com.topie.zhongkexie.core.service.IScoreIndexCollectionService;
import com.topie.zhongkexie.core.service.IScorePaperService;
import com.topie.zhongkexie.database.core.model.ScoreIndexCollection;

/**
 * Created by wangJL on 2018/3/30.
 */
@Controller
@RequestMapping("/api/core/scoreIndexCollection")
public class ScoreIndexCollectionController {

    @Autowired
    private IScoreIndexCollectionService iScoreIndexCollectionService;
    @Autowired
	private IScorePaperService paperService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(ScoreIndexCollection scoreIndexCollection,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        PageInfo<ScoreIndexCollection> pageInfo = iScoreIndexCollectionService.selectByFilterAndPage(scoreIndexCollection, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public Result insert(ScoreIndexCollection scoreIndexCollection) {
    	scoreIndexCollection.setContentJson(paperService.getContentJson(scoreIndexCollection.getPaperId(), scoreIndexCollection.getIndexCollection(), null).toJSONString());
        int result = iScoreIndexCollectionService.saveNotNull(scoreIndexCollection);
        return result > 0 ? ResponseUtil.success() : ResponseUtil.error();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(ScoreIndexCollection scoreIndexCollection) {
    	scoreIndexCollection.setContentJson(paperService.getContentJson(scoreIndexCollection.getPaperId(), scoreIndexCollection.getIndexCollection(), null).toJSONString());
        iScoreIndexCollectionService.updateNotNull(scoreIndexCollection);
        return ResponseUtil.success();
    }

    @RequestMapping(value = "/load/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Result load(@PathVariable(value = "id") Integer id) {
        ScoreIndexCollection scoreIndexCollection = iScoreIndexCollectionService.selectByKey(id);
        return ResponseUtil.success(scoreIndexCollection);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public Result delete(@RequestParam(value = "id") Integer id) {
        iScoreIndexCollectionService.delete(id);
        return ResponseUtil.success();
    }
    
    @RequestMapping("getSelectedNodesId")
    @ResponseBody
    public Result getSelectedNodesId(Integer paperId,@RequestParam(required=false,value="id") Integer id){
    	Set set = this.iScoreIndexCollectionService.getSelectedNodesId(paperId,id);
    	 return ResponseUtil.success(set);
    }

}
