package com.topie.zhongkexie.task.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.baseservice.IService;
import com.topie.zhongkexie.database.task.model.TaskIndexScore;

public interface ITaskIndexScoreService extends IService<TaskIndexScore> {

	PageInfo<TaskIndexScore> selectByFilterAndPage(TaskIndexScore task, int pageNum, int pageSize);

	List<TaskIndexScore> selectByFilter(TaskIndexScore task);

	
}
