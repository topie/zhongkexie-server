package com.topie.zhongkexie.expert.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.baseservice.IService;
import com.topie.zhongkexie.database.expert.model.PaperExpert;

public interface IPaperExpertService extends IService<PaperExpert> {

	PageInfo<PaperExpert> selectByFilterAndPage(PaperExpert paperExpert,
			int pageNum, int pageSize);

	List<PaperExpert> selectByFilter(PaperExpert paperExpert);

	int init(Integer paperId);

}
