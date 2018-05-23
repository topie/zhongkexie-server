package com.topie.zhongkexie.expert.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.topie.zhongkexie.common.baseservice.impl.BaseService;
import com.topie.zhongkexie.database.expert.model.ExpertItemScore;
import com.topie.zhongkexie.expert.service.IExpertItemScoreService;

@Service
public class ExpertItemScoreServiceImpl extends BaseService<ExpertItemScore> implements
		IExpertItemScoreService {

	@Override
	public List<ExpertItemScore> selectByFilter(ExpertItemScore entity) {
		Example ex = new Example(ExpertItemScore.class);
		Criteria c1 = ex.createCriteria();
		if(entity.getPaperId()!=null)
		c1.andEqualTo("paperId", entity.getPaperId());
		if(entity.getOrgUserId()!=null)
		c1.andEqualTo("orgUserId", entity.getOrgUserId());
		if(entity.getItemId()!=null)
		c1.andEqualTo("itemId", entity.getItemId());
		if(entity.getExpertUserId()!=null)
		c1.andEqualTo("expertUserId", entity.getExpertUserId());
		if(entity.getExpertId()!=null)
		c1.andEqualTo("expertId", entity.getExpertId());
		return getMapper().selectByExample(ex);
	}

	
}
