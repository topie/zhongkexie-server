package com.topie.zhongkexie.database.expert.dao;

import java.util.List;
import java.util.Map;

import tk.mybatis.mapper.common.Mapper;

import com.topie.zhongkexie.database.expert.model.ExpertDeptUser;

public interface ExpertDeptUserMapper extends Mapper<ExpertDeptUser> {
	/*
	 *专家打分情况    
	 * @param map
	 * @return{
	 * 	expertName
	 * 	expertId
	 * 	commited
	 * 	finished
	 * }
	 */
	List<Map> countExpertFinish(Map map);
}