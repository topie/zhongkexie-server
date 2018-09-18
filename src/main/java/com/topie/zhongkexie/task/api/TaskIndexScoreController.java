package com.topie.zhongkexie.task.api;

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
import com.topie.zhongkexie.database.task.model.TaskIndexScore;
import com.topie.zhongkexie.task.service.ITaskIndexScoreService;

@Controller
@RequestMapping("/api/task/indexScore")
public class TaskIndexScoreController {

    @Autowired
    private ITaskIndexScoreService iTaskIndexScoreService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(TaskIndexScore task,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        PageInfo<TaskIndexScore> pageInfo = iTaskIndexScoreService.selectByFilterAndPage(task, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }
    
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public Result insert(TaskIndexScore task) {
        int result = iTaskIndexScoreService.saveNotNull(task);
        return result > 0 ? ResponseUtil.success() : ResponseUtil.error();
    }
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(TaskIndexScore task) {
        int result = iTaskIndexScoreService.updateNotNull(task);
        return result > 0 ? ResponseUtil.success() : ResponseUtil.error();
    }

    @RequestMapping(value = "/load/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Result load(@PathVariable(value = "id") Integer id) {
        TaskIndexScore task = iTaskIndexScoreService.selectByKey(id);
        return ResponseUtil.success(task);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public Result delete(@RequestParam(value = "id") Integer id) {
        iTaskIndexScoreService.delete(id);
        return ResponseUtil.success();
    }


}
