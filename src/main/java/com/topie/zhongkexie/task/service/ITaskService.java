package com.topie.zhongkexie.task.service;

import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.baseservice.IService;
import com.topie.zhongkexie.database.core.model.User;
import com.topie.zhongkexie.database.task.model.Task;
import com.topie.zhongkexie.database.task.model.TaskScore;

public interface ITaskService extends IService<Task> {

	PageInfo<Task> selectByFilterAndPage(Task task, int pageNum, int pageSize);

	List<Task> selectByFilter(Task task);
	List<Task> selectByFilterCheck(Task task);

	int updateStatus(Task t);

	User testOrg(String org);

	PageInfo<Map> selectScoreCountsByFilterAndPage(Task task,
			int pageNum, int pageSize);

	List<TaskScore> selectByFilter(TaskScore taskScore);
	 void divScore(Task entity);

	XSSFWorkbook taskScoreInfoExport(Task task);

	
}
