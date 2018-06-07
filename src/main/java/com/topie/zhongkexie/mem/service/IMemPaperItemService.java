package com.topie.zhongkexie.mem.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.baseservice.IService;
import com.topie.zhongkexie.database.mem.model.MemPaperItem;

@Service
public interface IMemPaperItemService extends IService<MemPaperItem>{

	PageInfo<MemPaperItem> selectByFilterAndPage(MemPaperItem memPaperItem,
			int pageNum, int pageSize);
	
	List<MemPaperItem> selectByFilter(MemPaperItem memPaperItem);

}
