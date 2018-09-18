package com.topie.zhongkexie.task.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.baseservice.impl.BaseService;
import com.topie.zhongkexie.database.task.dao.TaskMapper;
import com.topie.zhongkexie.database.task.model.Task;
import com.topie.zhongkexie.task.service.ITaskService;

/**
 */
@Service
public class TaskServiceImpl extends BaseService<Task> implements ITaskService {

    @Autowired
    private TaskMapper taskMapper;

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
        if (null!=task.getTaskTop())
            criteria.andEqualTo("taskTop", task.getTaskTop());
        if (null!=task.getIndexId())
            criteria.andEqualTo("indexId", task.getIndexId());
        if (null!=task.getPaperId())
            criteria.andEqualTo("paperId", task.getPaperId());
        if (StringUtils.isNotEmpty(task.getTaskStatus()))
            criteria.andEqualTo("taskStatus", task.getTaskStatus());
        if (StringUtils.isNotEmpty(task.getTaskName()))
            criteria.andLike("taskName", task.getTaskName());
        if (null!=task.getTaskDept())
            criteria.andEqualTo("taskDept", task.getTaskDept());
        example.setOrderByClause("task_seq asc");
        return getMapper().selectByExample(example);
    }

}
