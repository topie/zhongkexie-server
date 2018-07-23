package com.topie.zhongkexie.publicity.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.baseservice.IService;
import com.topie.zhongkexie.database.publicity.model.Publicity;

public interface IPublicityService extends IService<Publicity> {

	PageInfo<Publicity> selectByFilterAndPage(Publicity publicity, int pageNum,
			int pageSize);
	List<Publicity> selectByFilter(Publicity publicity);


}
