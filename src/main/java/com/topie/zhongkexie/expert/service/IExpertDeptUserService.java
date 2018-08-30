package com.topie.zhongkexie.expert.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.baseservice.IService;
import com.topie.zhongkexie.database.expert.model.ExpertDeptUser;

public interface IExpertDeptUserService extends IService<ExpertDeptUser> {

	void deleteByIndexCollExpertId(Integer id);
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
	
	PageInfo<Map> countExpertFinishPage(Map map,int pageNum,int pageSize);
	/**
	 * 获取当前用户（专家）的 情况
	 * @return
	 */
	Map countCurrentExpertFinish();


}
