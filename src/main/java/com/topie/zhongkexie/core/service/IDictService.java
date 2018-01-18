package com.topie.zhongkexie.core.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.baseservice.IService;
import com.topie.zhongkexie.database.core.model.Dict;
import com.topie.zhongkexie.database.core.model.DictItem;


public interface IDictService extends IService<Dict> {

	
	List<Dict> selectByFilter(Dict dict);

	PageInfo<Dict> selectByFilterAndPage(Dict dict, int pageNum, int pageSize);

	List<DictItem> selectItemsByDictCode(String code);

}
