package com.topie.zhongkexie.database.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import com.topie.zhongkexie.database.core.model.ScoreItem;

public interface ScoreItemMapper extends Mapper<ScoreItem> {

	List<ScoreItem> selectByExampleEx(@Param("example")Example example,
			@Param("responsibleDepartment") String responsibleDepartment,@Param("paperId") Integer paperId);
}