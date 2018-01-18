package com.topie.zhongkexie.core.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.baseservice.IService;
import com.topie.zhongkexie.database.core.model.DictItem;


public interface IDictItemService extends IService<DictItem> {

	List<DictItem> selectByFilter(DictItem dictitem);

	PageInfo<DictItem> selectByFilterAndPage(DictItem dictitem, int pageNum, int pageSize);

}
