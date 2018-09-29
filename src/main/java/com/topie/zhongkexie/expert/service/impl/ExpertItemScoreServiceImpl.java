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
		BigDecimal max = null;
		BigDecimal min = null;
		for(ExpertItemScore is:lis){
			if(max==null || max.compareTo(is.getItemScore())<0)
				max = is.getItemScore();
			if(min==null || min.compareTo(is.getItemScore())>0)
				min = is.getItemScore();
			score  = score.add(is.getItemScore());
		}
		int count = lis.size();
		if(lis.size()>3){//如果超过三个专家则 去掉最高分最低分
			score = score.subtract(max).subtract(min);
			count = count-2;
		}
		if(lis.size()!=0){
			score = score.divide( new BigDecimal(count),6);
		}
		//=======修改为百分比打分=======
		score = one.getItemScore().multiply(score).divide(new BigDecimal(100), 4);
		return score;
	}

	@Override
	public List<Map> selectScoreCount(Integer paperId) {
		return this.expertItemScoreMapper.selectScoreCount(paperId);
	}
	

	
}
