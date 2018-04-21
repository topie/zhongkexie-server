package com.topie.zhongkexie.expert.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.baseservice.IService;
import com.topie.zhongkexie.database.expert.model.IndexCollExpert;

public interface IIndexCollExpertService extends IService<IndexCollExpert> {

	PageInfo<IndexCollExpert> selectByFilterAndPage(IndexCollExpert indexCollExpert,
			int pageNum, int pageSize);

	List<IndexCollExpert> selectByFilter(IndexCollExpert indexCollExpert);

	int init(Integer paperId,Integer paperExpertId,String fieldType);

}
