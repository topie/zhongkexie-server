package com.topie.zhongkexie.system.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.baseservice.impl.BaseService;
import com.topie.zhongkexie.database.core.model.SysLog;
import com.topie.zhongkexie.system.service.LogService;

@Service
public class LogServiceImpl extends BaseService<SysLog> implements LogService {

	@Override
	public PageInfo<SysLog> findByPage(int pageNum, int pageSize, SysLog sysLog) {
		PageHelper.startPage(pageNum,pageSize);
		List<SysLog> list = findByFilter(sysLog);
		return new PageInfo<SysLog>(list);
	}

	@Override
	public List<SysLog> findByFilter(SysLog sysLog) {
		Example ex = new Example(SysLog.class);
		Criteria c = ex.createCriteria();
		if(!StringUtils.isEmpty(sysLog.getTitle()))
			c.andLike("title", "%"+sysLog.getTitle()+"%");
		if(!StringUtils.isEmpty(sysLog.getCtype()))
			c.andEqualTo("ctype", sysLog.getCtype());
		ex.setOrderByClause("cdate desc");
		return getMapper().selectByExample(ex);
	}

}
