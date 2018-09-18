package com.topie.zhongkexie.task.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.baseservice.impl.BaseService;
import com.topie.zhongkexie.database.task.dao.TaskIndexScoreMapper;
import com.topie.zhongkexie.database.task.model.TaskIndexScore;
import com.topie.zhongkexie.task.service.ITaskIndexScoreService;

/**
 */
@Service
public class TaskIndexScoreServiceImpl extends BaseService<TaskIndexScore> implements ITaskIndexScoreService {

    @Autowired
    private TaskIndexScoreMapper taskMapper;

    @Override
    public PageInfo<TaskIndexScore> selectByFilterAndPage(TaskIndexScore task, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<TaskIndexScore> list = selectByFilter(task);
        return new PageInfo<>(list);
    }

    @Override
    public List<TaskIndexScore> selectByFilter(TaskIndexScore task) {
        Example example = new Example(TaskIndexScore.class);
        Example.Criteria criteria = example.createCriteria();
        if(task.getIndexId()!=null){
        	criteria.andEqualTo("indexId", task.getIndexId());
        }if(task.getPaperId()!=null){
        	criteria.andEqualTo("paperId", task.getPaperId());
        }
        return getMapper().selectByExample(example);
    }

}
