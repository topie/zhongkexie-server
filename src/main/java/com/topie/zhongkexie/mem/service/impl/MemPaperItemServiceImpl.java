package com.topie.zhongkexie.mem.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.baseservice.impl.BaseService;
import com.topie.zhongkexie.database.mem.model.MemPaperItem;
import com.topie.zhongkexie.mem.service.IMemPaperItemService;

@Service
public class MemPaperItemServiceImpl extends BaseService<MemPaperItem> implements IMemPaperItemService{

	@Override
	public PageInfo<MemPaperItem> selectByFilterAndPage(
			MemPaperItem memPaperItem, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<MemPaperItem> list = selectByFilter(memPaperItem);
		return new PageInfo<MemPaperItem>(list);
	}

	@Override
	public List<MemPaperItem> selectByFilter(MemPaperItem memPaperItem) {
		Example ex = new Example(MemPaperItem.class);
		Criteria c = ex.createCriteria();
		if(memPaperItem.getPaperId()!=null)c.andEqualTo("paperId",memPaperItem.getPaperId());
		if(memPaperItem.getItemId()!=null) c.andEqualTo("itemId", memPaperItem.getItemId());
		return getMapper().selectByExample(ex);
	}

}
