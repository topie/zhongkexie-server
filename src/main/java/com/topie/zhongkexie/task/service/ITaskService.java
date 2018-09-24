package com.topie.zhongkexie.task.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.baseservice.IService;
import com.topie.zhongkexie.database.task.model.Task;

public interface ITaskService extends IService<Task> {

	PageInfo<Task> selectByFilterAndPage(Task task, int pageNum, int pageSize);

	List<Task> selectByFilter(Task task);
	List<Task> selectByFilterCheck(Task task);

	int updateStatus(Task t);

	
}
