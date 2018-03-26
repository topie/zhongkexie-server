package com.topie.zhongkexie.core.api;

import java.util.ArrayList;
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
import com.topie.zhongkexie.common.utils.TreeNode;
import com.topie.zhongkexie.core.service.IScoreIndexService;
import com.topie.zhongkexie.database.core.model.ScoreIndex;

/**
 * Created by chenguojun on 2017/4/19.
 */
@Controller
@RequestMapping("/api/core/scoreIndex")
public class ScoreIndexController {

    @Autowired
    private IScoreIndexService iScoreIndexService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(ScoreIndex scoreIndex,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        PageInfo<ScoreIndex> pageInfo = iScoreIndexService.selectByFilterAndPage(scoreIndex, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public Result insert(ScoreIndex scoreIndex) {
        int result = iScoreIndexService.saveNotNull(scoreIndex);
        return result > 0 ? ResponseUtil.success() : ResponseUtil.error();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(ScoreIndex scoreIndex) {
        iScoreIndexService.updateNotNull(scoreIndex);
        return ResponseUtil.success();
    }

    @RequestMapping(value = "/load/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Result load(@PathVariable(value = "id") Integer id) {
        ScoreIndex scoreIndex = iScoreIndexService.selectByKey(id);
        return ResponseUtil.success(scoreIndex);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public Result delete(@RequestParam(value = "id") Integer id) {
    	ScoreIndex scoreIndex = new ScoreIndex();
    	scoreIndex.setParentId(id);
    	List list = iScoreIndexService.selectByFilter(scoreIndex);
    	if(list.size()>0){
    		return ResponseUtil.error("请先删除下级节点");
    	}
        iScoreIndexService.delete(id);
        return ResponseUtil.success();
    }

    @RequestMapping(value = "/treeNodes", method = RequestMethod.POST)
    @ResponseBody
    public Object treeNodes(ScoreIndex scoreIndex) {
        List<TreeNode> nodes = new ArrayList<>();
        List<ScoreIndex> list = iScoreIndexService.selectByFilter(scoreIndex);
        for (ScoreIndex nodeInfo : list) {
            TreeNode treeNode = new TreeNode();
            treeNode.setId(nodeInfo.getId());
            treeNode.setpId(nodeInfo.getParentId());
            treeNode.setName(nodeInfo.getName());
            treeNode.setS(nodeInfo.getScore().toString());
            nodes.add(treeNode);
        }
        return nodes;
    }

}
