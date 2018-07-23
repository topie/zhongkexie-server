package com.topie.zhongkexie.publicity.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.baseservice.IService;
import com.topie.zhongkexie.database.publicity.model.Dissent;

public interface IDissentService extends IService<Dissent> {

	PageInfo<Dissent> selectByFilterAndPage(Dissent dissent, int pageNum,
			int pageSize);
	List<Dissent> selectByFilter(Dissent dissent);


}
