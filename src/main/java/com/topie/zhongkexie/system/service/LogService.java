package com.topie.zhongkexie.system.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.baseservice.IService;
import com.topie.zhongkexie.database.core.model.SysLog;

public interface LogService extends IService<SysLog>{

	String CAOZUO = "1";
	String DENGLU = "0";

	PageInfo<SysLog> findByPage(int pageNum, int pageSize, SysLog sysLog);

	List<SysLog> findByFilter(SysLog sysLog);

}
