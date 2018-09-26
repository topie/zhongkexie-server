package com.topie.zhongkexie.task.api;

import java.math.BigDecimal;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
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
import com.topie.zhongkexie.database.task.model.Task;
import com.topie.zhongkexie.database.task.model.TaskIndexScore;
import com.topie.zhongkexie.security.utils.SecurityUtil;
import com.topie.zhongkexie.task.service.ITaskIndexScoreService;
import com.topie.zhongkexie.task.service.ITaskService;

@Controller
@RequestMapping("/api/task/task")
public class TaskController {

    @Autowired
    private ITaskService iTaskService;
    @Autowired
    private ITaskIndexScoreService iTaskIndexScoreService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(Task task,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        PageInfo<Task> pageInfo = iTaskService.selectByFilterAndPage(task, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }
    @RequestMapping(value = "/deptTask", method = RequestMethod.GET)
    @ResponseBody
    public Result list(Task task){
    	Integer user = SecurityUtil.getCurrentUserId();
    	task.setTaskDept(user.toString());
        List<Task> list = iTaskService.selectByFilter(task);
        return ResponseUtil.success(list);
    }
    @RequestMapping(value = "/allTask", method = RequestMethod.GET)
    @ResponseBody
    public Result allTask(Task task){
        List<Task> list = iTaskService.selectByFilter(task);
        return ResponseUtil.success(list);
    }
    @RequestMapping(value = "/deptCheckTask", method = RequestMethod.GET)
    @ResponseBody
    public Result deptCheckTask(Task task){
    	Integer user = SecurityUtil.getCurrentUserId();
    	task.setTaskDept(user.toString());
        List<Task> list = iTaskService.selectByFilterCheck(task);
        return ResponseUtil.success(list);
    }
    
    @RequestMapping(value = "/deptCommit")
    @ResponseBody
    public Result commit(HttpServletRequest request){
    	Enumeration<String> e = request.getParameterNames();
    	while (e.hasMoreElements()) {
			String p = e.nextElement();
			if(p.startsWith("org_")){
				String[] orgs = request.getParameterValues(p);
				String taskId = p.replace("org_", "");
				String weightp = "weight_"+taskId;
				String[] weights = request.getParameterValues(weightp);
				String orgsString = "";
				for(int i=0;i<orgs.length;i++){
					String org = orgs[i].replace(",", "；").replace("，", "；").replace(";", "；").replace(" ", "").trim();
					for(String o:org.split("；")){
						this.iTaskService.testOrg(o.trim());
					}
					orgsString+=","+org;
				}
				orgsString = orgsString.substring(1);
				String weightsString = "";
				for(int i=0;i<weights.length;i++){
					weightsString+=","+weights[i];
				}
				weightsString = weightsString.substring(1);
				Task task = new Task();
				task.setId(Integer.valueOf(taskId));
				task.setTaskCweight(weightsString);
				task.setTaskValue(orgsString);
		        int result = iTaskService.updateNotNull(task);
			}
		}
    	
        return ResponseUtil.success();
    }
    
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public Result insert(Task task) {
    	if(task.getParentId()==0){
	    	TaskIndexScore taskIndex = new TaskIndexScore();
	    	taskIndex.setIndexId(task.getIndexId());
	    	List<TaskIndexScore> list = iTaskIndexScoreService.selectByFilter(taskIndex);
	    	if(list.size()==0){
	    		return ResponseUtil.error("未设置指标分数");
	    	}if(list.size()>1){
	    		return ResponseUtil.error("设置指标分数太多");
	    	}
	    	taskIndex = list.get(0);
	    	BigDecimal score = taskIndex.getScore();
	    	score = score.multiply(task.getTaskWeight()).divide(new BigDecimal("100"));
	    	task.setTaskScore(score);
    	}else{
    		Task parent = iTaskService.selectByKey(task.getParentId());
    		if(parent==null){
	    		return ResponseUtil.error("没有找到父任务");
    		}
    		BigDecimal score = parent.getTaskScore();
    		score = score.multiply(task.getTaskWeight()).divide(new BigDecimal("100"));
	    	task.setTaskScore(score);
    	}
        int result = iTaskService.saveNotNull(task);
        return result > 0 ? ResponseUtil.success() : ResponseUtil.error();
    }
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(Task task) {
    	if(task.getParentId()==0){
	    	TaskIndexScore taskIndex = new TaskIndexScore();
	    	taskIndex.setIndexId(task.getIndexId());
	    	List<TaskIndexScore> list = iTaskIndexScoreService.selectByFilter(taskIndex);
	    	if(list.size()==0){
	    		return ResponseUtil.error("未设置指标分数");
	    	}if(list.size()>1){
	    		return ResponseUtil.error("设置指标分数太多");
	    	}
	    	taskIndex = list.get(0);
	    	BigDecimal score = taskIndex.getScore();
	    	score = score.multiply(task.getTaskWeight()).divide(new BigDecimal("100"));
	    	task.setTaskScore(score);
	    }else{
			Task parent = iTaskService.selectByKey(task.getParentId());
			if(parent==null){
	    		return ResponseUtil.error("没有找到父任务");
			}
			BigDecimal score = parent.getTaskScore();
			score = score.multiply(task.getTaskWeight()).divide(new BigDecimal("100"));
	    	task.setTaskScore(score);
		}
        int result = iTaskService.updateNotNull(task);
        return result > 0 ? ResponseUtil.success() : ResponseUtil.error();
    }

    @RequestMapping(value = "/load/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Result load(@PathVariable(value = "id") Integer id) {
        Task task = iTaskService.selectByKey(id);
        return ResponseUtil.success(task);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public Result delete(@RequestParam(value = "id") Integer id) {
        iTaskService.delete(id);
        return ResponseUtil.success();
    }
    @RequestMapping(value = "/updateStatus")
    @ResponseBody
    public Result updateStatus(String taskIds,String status) {
        if(StringUtils.isNotEmpty(taskIds)){
        	for(String s:taskIds.split(",")){
        		Task t = new Task();
        		t.setId(Integer.valueOf(s));
        		t.setTaskStatus(status);
                int result = iTaskService.updateStatus(t);
        	}
        }
        return ResponseUtil.success();
    }


}
