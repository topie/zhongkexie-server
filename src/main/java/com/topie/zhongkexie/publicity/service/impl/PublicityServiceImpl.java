package com.topie.zhongkexie.publicity.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.baseservice.impl.BaseService;
import com.topie.zhongkexie.database.publicity.dao.PublicityMapper;
import com.topie.zhongkexie.database.publicity.model.Publicity;
import com.topie.zhongkexie.publicity.service.IPublicityService;

@Service
public class PublicityServiceImpl extends BaseService<Publicity> implements
		IPublicityService {
	@Autowired
	private PublicityMapper publicityMapper;

	@Override
	public PageInfo<Publicity> selectByFilterAndPage(Publicity publicity,
			int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Publicity> list = selectByFilter(publicity);
		return new PageInfo<Publicity>(list);
	}

	@Override
	public List<Publicity> selectByFilter(Publicity publicity) {
		return publicityMapper.selectByPublicity(publicity);
	}
	
	

	
}
