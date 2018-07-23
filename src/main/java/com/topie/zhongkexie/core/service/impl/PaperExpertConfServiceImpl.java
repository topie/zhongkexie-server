package com.topie.zhongkexie.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.baseservice.impl.BaseService;
import com.topie.zhongkexie.core.service.IPaperExpertConfService;
import com.topie.zhongkexie.database.core.dao.PaperExpertConfMapper;
import com.topie.zhongkexie.database.core.model.PaperExpertConf;

@Service
public class PaperExpertConfServiceImpl extends BaseService<PaperExpertConf> implements
		IPaperExpertConfService {
	@Autowired
	private PaperExpertConfMapper paperExpertConfMapper;

	@Override
	public PageInfo<PaperExpertConf> selectByFilterAndPage(PaperExpertConf paperExpertConf,
			int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<PaperExpertConf> list = selectByFilter(paperExpertConf);
		return new PageInfo<PaperExpertConf>(list);
	}

	@Override
	public List<PaperExpertConf> selectByFilter(PaperExpertConf paperExpertConf) {
		return paperExpertConfMapper.selectByPaperExpertConf(paperExpertConf);
	}
	
	

	
}
