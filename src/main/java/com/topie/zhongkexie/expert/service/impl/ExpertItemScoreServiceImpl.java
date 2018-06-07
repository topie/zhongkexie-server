package com.topie.zhongkexie.expert.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.topie.zhongkexie.common.baseservice.impl.BaseService;
import com.topie.zhongkexie.database.core.model.ScoreAnswer;
import com.topie.zhongkexie.database.expert.dao.ExpertItemScoreMapper;
import com.topie.zhongkexie.database.expert.model.ExpertItemScore;
import com.topie.zhongkexie.expert.service.IExpertItemScoreService;

@Service
public class ExpertItemScoreServiceImpl extends BaseService<ExpertItemScore> implements
		IExpertItemScoreService {
	@Autowired
	private ExpertItemScoreMapper expertItemScoreMapper;

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

	@Override
	public List<Map> selectScoreInfo(Integer userId, String itemId) {
		return expertItemScoreMapper.selectScoreInfo(userId,itemId);
	}

	@Override
	public BigDecimal divScore(ScoreAnswer one) {
		ExpertItemScore entity =new ExpertItemScore();
		entity.setItemId(one.getItemId());
		entity.setOrgUserId(one.getUserId());
		List<ExpertItemScore> lis = selectByFilter(entity );
		BigDecimal score = new BigDecimal(0);
		for(ExpertItemScore is:lis){
			score  = score.add(is.getItemScore());
		}
		if(lis.size()!=0){
			score = score.divide( new BigDecimal(lis.size()),4);
		}
		return score;
	}

	
}
