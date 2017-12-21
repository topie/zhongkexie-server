package com.topie.zhongkexie.core.service;

import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.baseservice.IService;
import com.topie.zhongkexie.database.core.model.Message;

public interface IMessageService extends IService<Message>{
	public PageInfo<Message> selectByFilterAndPage(Message message, int pageNum,
			int pageSize) ;
}
