package com.topie.zhongkexie.expert.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.baseservice.IService;
import com.topie.zhongkexie.database.expert.model.ExpertInfo;

public interface IExpertInfoService extends IService<ExpertInfo> {

	PageInfo<ExpertInfo> selectByFilterAndPage(ExpertInfo expertInfo,
			int pageNum, int pageSize);

	List<ExpertInfo> selectByFilter(ExpertInfo expertInfo);

	int saveNotNull(ExpertInfo entity, String password);

}
