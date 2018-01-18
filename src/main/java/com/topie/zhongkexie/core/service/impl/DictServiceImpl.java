package com.topie.zhongkexie.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.baseservice.impl.BaseService;
import com.topie.zhongkexie.core.service.IDictItemService;
import com.topie.zhongkexie.core.service.IDictService;
import com.topie.zhongkexie.database.core.model.Dict;
import com.topie.zhongkexie.database.core.model.DictItem;

@Service
public class DictServiceImpl extends BaseService<Dict> implements IDictService {
	
	@Autowired
	private IDictItemService itemService;
	@Override
	public List<Dict> selectByFilter(Dict dict) {
		Example ex = new Example(Dict.class);
		Criteria c = ex.createCriteria();
		if(StringUtils.isNotEmpty(dict.getDictName()))c.andLike("dictName", "%"+dict.getDictName()+"%");
		if(StringUtils.isNotEmpty(dict.getDictCode()))c.andEqualTo("dictCode", dict.getDictCode());
		if(StringUtils.isNotEmpty(dict.getDictType()))c.andEqualTo("dictType", dict.getDictType());
		if(dict.getDictStatus()!= null)c.andEqualTo("dictStatus", dict.getDictStatus());
		ex.setOrderByClause("dict_seq asc");
		return getMapper().selectByExample(ex);
	}

	@Override
	public PageInfo<Dict> selectByFilterAndPage(Dict dict, int pageNum,
			int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Dict> list = selectByFilter(dict);
		return new PageInfo<Dict>(list);
	}

	@Override
	public List<DictItem> selectItemsByDictCode(String code) {
		Dict dict = new Dict();
		dict.setDictCode(code);
		List<Dict> dicts = selectByFilter(dict);
		if(dicts.size()==0)
			return new ArrayList<DictItem>();
		DictItem dictitem = new DictItem();
		dictitem.setDictId(dicts.get(0).getDictId());
		return itemService.selectByFilter(dictitem);
	}

}
