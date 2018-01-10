package com.topie.zhongkexie.core.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.baseservice.IService;
import com.topie.zhongkexie.database.core.model.Dept;


public interface IDeptService extends IService<Dept> {

	List<Dept> selectByFilter(Dept message);

	PageInfo<Dept> selectByFilterAndPage(Dept dept, int pageNum, int pageSize);

}
