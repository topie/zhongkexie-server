package com.topie.zhongkexie.database.task.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import com.topie.zhongkexie.database.task.model.Task;

public interface TaskMapper extends Mapper<Task> {

	List<Task> selectByExampleReport( @Param("example")Example example , @Param("taskDept")Integer taskDept);
	List<Task> selectByExampleCheck( @Param("example")Example example , @Param("checkDept")Integer checkDept);
}