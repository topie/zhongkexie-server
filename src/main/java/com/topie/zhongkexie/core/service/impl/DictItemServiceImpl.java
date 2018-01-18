package com.topie.zhongkexie.core.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.baseservice.impl.BaseService;
import com.topie.zhongkexie.core.service.IDictItemService;
import com.topie.zhongkexie.database.core.model.DictItem;

@Service
public class DictItemServiceImpl extends BaseService<DictItem> implements IDictItemService {

	@Override
	public List<DictItem> selectByFilter(DictItem dict) {
		Example ex = new Example(DictItem.class);
		Criteria c = ex.createCriteria();
		if(StringUtils.isNotEmpty(dict.getItemName()))c.andLike("itemName", "%"+dict.getItemName()+"%");
		if(StringUtils.isNotEmpty(dict.getItemCode()))c.andEqualTo("itemCode", dict.getItemCode());
		if(dict.getDictId()!=null)c.andEqualTo("dictId", dict.getDictId());
		ex.setOrderByClause("item_seq asc");
		return getMapper().selectByExample(ex);
	}

	@Override
	public PageInfo<DictItem> selectByFilterAndPage(DictItem dict, int pageNum,
			int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<DictItem> list = selectByFilter(dict);
		return new PageInfo<DictItem>(list);
	}

}
