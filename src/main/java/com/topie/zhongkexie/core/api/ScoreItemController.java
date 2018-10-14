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
import com.topie.zhongkexie.core.service.IScoreItemService;
import com.topie.zhongkexie.database.core.model.ScoreItem;

/**
 * Created by chenguojun on 2017/4/19.
 */
@Controller
@RequestMapping("/api/core/scoreItem")
public class ScoreItemController {

    @Autowired
    private IScoreItemService iScoreItemService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(ScoreItem scoreItem,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        PageInfo<ScoreItem> pageInfo = iScoreItemService.selectByFilterAndPage(scoreItem, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public Result insert(ScoreItem scoreItem) {
        int result = iScoreItemService.saveNotNull(scoreItem);
        return result > 0 ? ResponseUtil.success() : ResponseUtil.error();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(ScoreItem scoreItem) {
        iScoreItemService.updateNotNull(scoreItem);
        return ResponseUtil.success();
    }

    @RequestMapping(value = "/load/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Result load(@PathVariable(value = "id") Integer id) {
        ScoreItem scoreItem = iScoreItemService.selectByKey(id);
        return ResponseUtil.success(scoreItem);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public Result delete(@RequestParam(value = "id") Integer id) {
        iScoreItemService.delete(id);
        return ResponseUtil.success();
    }
    
    @RequestMapping(value = "/getTreeNode")
    @ResponseBody
    public Object list(ScoreItem scoreItem) {
    	List<TreeNode> nodes = new ArrayList<>();
        List<ScoreItem> list = iScoreItemService.selectByFilter(scoreItem);
        for (ScoreItem nodeInfo : list) {
            TreeNode treeNode = new TreeNode();
            treeNode.setId(-nodeInfo.getId());
            treeNode.setpId(nodeInfo.getIndexId());
            treeNode.setName(nodeInfo.getTitle());
            treeNode.setS(nodeInfo.getScore().toString());
            treeNode.setType(nodeInfo.getScoreType());
            nodes.add(treeNode);
        }
        return nodes;
    }

}
