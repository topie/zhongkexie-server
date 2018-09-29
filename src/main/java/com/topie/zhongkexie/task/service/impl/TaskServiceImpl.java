package com.topie.zhongkexie.task.service.impl;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.baseservice.impl.BaseService;
import com.topie.zhongkexie.common.exception.DefaultBusinessException;
import com.topie.zhongkexie.core.exception.RuntimeBusinessException;
import com.topie.zhongkexie.core.service.IDeptService;
import com.topie.zhongkexie.database.core.model.Dept;
import com.topie.zhongkexie.database.core.model.User;
import com.topie.zhongkexie.database.task.dao.TaskCheckUserMapper;
import com.topie.zhongkexie.database.task.dao.TaskMapper;
import com.topie.zhongkexie.database.task.dao.TaskReportUserMapper;
import com.topie.zhongkexie.database.task.dao.TaskScoreMapper;
import com.topie.zhongkexie.database.task.model.Task;
import com.topie.zhongkexie.database.task.model.TaskCheckUser;
import com.topie.zhongkexie.database.task.model.TaskReportUser;
import com.topie.zhongkexie.database.task.model.TaskScore;
import com.topie.zhongkexie.security.service.UserService;
import com.topie.zhongkexie.task.service.ITaskService;

/**
 */
@Service
public class TaskServiceImpl extends BaseService<Task> implements ITaskService {

	@Autowired
    private TaskMapper taskMapper;
	@Autowired
    private TaskReportUserMapper taskReportUserMapper;
	@Autowired
	private TaskCheckUserMapper taskCheckUserMapper;
	@Autowired
	private TaskScoreMapper taskScoreMapper;
	@Autowired
	private IDeptService iDeptService;
	@Autowired
	private UserService userService;

    @Override
    public PageInfo<Task> selectByFilterAndPage(Task task, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Task> list = selectByFilter(task);
        return new PageInfo<>(list);
    }

    @Override
    public List<Task> selectByFilter(Task task) {
        Example example = new Example(Task.class);
        Example.Criteria criteria = example.createCriteria();
        if (null!=task.getParentId())
            criteria.andEqualTo("parentId", task.getParentId());
        if (null!=task.getIndexId())
            criteria.andEqualTo("indexId", task.getIndexId());
        if (null!=task.getPaperId())
            criteria.andEqualTo("paperId", task.getPaperId());
        if (StringUtils.isNotEmpty(task.getTaskStatus()))
            criteria.andEqualTo("taskStatus", task.getTaskStatus());
        if (StringUtils.isNotEmpty(task.getTaskName()))
            criteria.andLike("taskName", task.getTaskName());
        example.setOrderByClause("id asc");
        return taskMapper.selectByExampleReport(example,StringUtils.isNotEmpty(task.getTaskDept())?Integer.valueOf(task.getTaskDept()):null);
    }
    
    @Override
    public List<Task> selectByFilterCheck(Task task) {
        Example example = new Example(Task.class);
        Example.Criteria criteria = example.createCriteria();
        if (null!=task.getParentId())
            criteria.andEqualTo("parentId", task.getParentId());
        if (null!=task.getIndexId())
            criteria.andEqualTo("indexId", task.getIndexId());
        if (null!=task.getPaperId())
            criteria.andEqualTo("paperId", task.getPaperId());
        if (StringUtils.isNotEmpty(task.getTaskStatus()))
            criteria.andEqualTo("taskStatus", task.getTaskStatus());
        if (StringUtils.isNotEmpty(task.getTaskName()))
            criteria.andLike("taskName", task.getTaskName());
        example.setOrderByClause("id asc");
        return taskMapper.selectByExampleCheck(example,StringUtils.isNotEmpty(task.getTaskDept())?Integer.valueOf(task.getTaskDept()):null);
    }
    @Override
    public int saveNotNull(Task entity) {
    	String dept = entity.getTaskDept();
    	int i = super.saveNotNull(entity);
    	if(StringUtils.isNotEmpty(dept)){
    		for(String s:dept.split(",")){
    			if(StringUtils.isNotEmpty(s)){
	    			TaskReportUser arg0 = new TaskReportUser();
	    			arg0.setTaskId(entity.getId());
	    			arg0.setUserId(Integer.valueOf(s));
	    			this.taskReportUserMapper.insertSelective(arg0);
    			}
    		}
    	}
    	String checkUser = entity.getTaskCheckUser();
    	if(StringUtils.isNotEmpty(checkUser)){
    		for(String s:checkUser.split(",")){
    			if(StringUtils.isNotEmpty(s)){
		    		TaskCheckUser arg0 = new TaskCheckUser();
					arg0.setTaskId(entity.getId());
					arg0.setUserId(Integer.valueOf(s));
					this.taskCheckUserMapper.insertSelective(arg0);
    			}
    		}
    		
    	}
    	return i;
    }
    @Override
    public int updateNotNull(Task entity) {
    	
    	int o=super.updateNotNull(entity);
    	entity = this.taskMapper.selectByPrimaryKey(entity.getId());
    	String dept = entity.getTaskDept();
    	Example ex = new Example(TaskReportUser.class);
    	ex.createCriteria().andEqualTo("taskId",entity.getId());
    	this.taskReportUserMapper.deleteByExample(ex);
    	if(StringUtils.isNotEmpty(dept)){
    		for(String s:dept.split(",")){
    			if(StringUtils.isNotEmpty(s)){
	    			TaskReportUser arg0 = new TaskReportUser();
	    			arg0.setTaskId(entity.getId());
	    			arg0.setUserId(Integer.valueOf(s));
	    			this.taskReportUserMapper.insertSelective(arg0);
    			}
    		}
    		
    	}
    	Example ex1 = new Example(TaskCheckUser.class);
    	ex1.createCriteria().andEqualTo("taskId",entity.getId());
    	this.taskCheckUserMapper.deleteByExample(ex1);
    	String checkUser = entity.getTaskCheckUser();
    	if(StringUtils.isNotEmpty(checkUser)){
    		for(String s:checkUser.split(",")){
    			if(StringUtils.isNotEmpty(s)){
		    		TaskCheckUser arg0 = new TaskCheckUser();
					arg0.setTaskId(entity.getId());
					arg0.setUserId(Integer.valueOf(s));
					this.taskCheckUserMapper.insertSelective(arg0);
	    		}
    		}
    		
    	}
    	return o;
    }
    /**
	 * 计算得分
	 * @param entity
	 */
	public void divScore(Task entity) {
		String value = entity.getTaskValue();
		Integer taskId = entity.getId();
		//先清空上次得分
		Example ex = new Example(TaskScore.class);
		ex.createCriteria().andEqualTo("taskId",taskId);
		taskScoreMapper.deleteByExample(ex);
		
		if(!StringUtils.isEmpty(value)){
			if(value.endsWith(","))value = value+"end";
			String[] vs = value.split(",");
			vs[vs.length-1] = vs[vs.length-1].replace("end", "");
			if(vs.length!=4){
				throw new DefaultBusinessException("填写学会有误");
			}
			//int length = vs.length/(4);//获取任务数量 （）
			String scores = entity.getTaskCweight();
			if(StringUtils.isEmpty(scores)||scores.split(",").length!=4){
				throw new DefaultBusinessException("四个等级权重设置错误-》"+scores);
			}
			String[] ss = scores.split(",");
			for(int i=0;i<vs.length;i+=4){
				Set<String> names = new HashSet<String>();//去重复如果一个学会多次出现，则只记一次最高分
				String orgs100 = vs[i];
				updateScore(orgs100,new BigDecimal(ss[i]),entity,i,names);
				String orgs75 = vs[i+1];
				updateScore(orgs75,new BigDecimal(ss[i+1]),entity,i+1,names);
				String orgs50 = vs[i+2];
				updateScore(orgs50,new BigDecimal(ss[i+2]),entity,i+2,names);
				String orgs25 = vs[i+3];
				updateScore(orgs25,new BigDecimal(ss[i+3]),entity,i+3,names);
			}
		}
		
	}

	/**
	 * 根据学会名 更新分数
	 * @param orgs 学会名 已；分割
	 * @param i  分值百分比
	 * @param entity  Task
	 */
	private void updateScore(String orgs, BigDecimal i, Task entity,int type,Set<String> names) {
		int taskId = entity.getId();
		int paperId = entity.getPaperId();
		BigDecimal itemScore = entity.getTaskScore();
		BigDecimal answerScore = itemScore.multiply(i).divide(new BigDecimal(100),4);
		String[] orgList = orgs.split("；");
		for (int j = 0; j < orgList.length; j++) {
			String orgName = orgList[j];
			if(StringUtils.isEmpty(orgName))continue;
			User user = testOrg(orgName);
			if(names.contains(orgName)){
				continue;
			}
			names.add(orgName);
			//开始保存
			TaskScore score = new TaskScore();
			score.setTaskId(taskId);
			score.setPaperId(paperId);
			score.setUserId(user.getId());
			score.setScore(answerScore);
			score.setValue(type+"");
			taskScoreMapper.insertSelective(score);
		}
		
	}

	@Override
	public int updateStatus(Task t) {
		int i = this.updateNotNull(t);
		t = this.selectByKey(t.getId());
		if(t.getTaskStatus().equals("2"));
			divScore(t);
		return i;
	}

	@Override
	public User testOrg(String org) {
		Dept dept= new Dept();
		dept.setName(org.trim());
		List<Dept> dList = iDeptService.selectByFilter(dept);
		if(dList.size()==0)throw new RuntimeBusinessException("学会未找到："+org);
		Dept d = dList.get(0);
		String loginName = d.getCode()+"002";
		User user = userService.findUserByLoginName(loginName);
		if(user==null)throw new RuntimeBusinessException("学会用户未找到："+org+"-登录名："+loginName);
		return user;
	}

	@Override
	public PageInfo<Map> selectScoreCountsByFilterAndPage(Task task,
			int pageNum, int pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		List<Map> list = this.taskMapper.selectScoreCountsByFilter(task);
		
		return new PageInfo<Map>(list);
	}

	@Override
	public List<TaskScore> selectByFilter(TaskScore taskScore) {
		Example ex = new Example(TaskScore.class);
		Criteria c = ex.createCriteria();
		if(taskScore.getPaperId()!=null)
			c.andEqualTo("paperId", taskScore.getPaperId());
		if(taskScore.getUserId()!=null)
			c.andEqualTo("userId", taskScore.getUserId());
		if(taskScore.getTaskId()!=null)
			c.andEqualTo("taskId", taskScore.getTaskId());
		List<TaskScore> list = taskScoreMapper.selectByExample(ex);
		return list;
	}



}
