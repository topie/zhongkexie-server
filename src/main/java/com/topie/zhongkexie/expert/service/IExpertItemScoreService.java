package com.topie.zhongkexie.expert.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.topie.zhongkexie.common.baseservice.IService;
import com.topie.zhongkexie.database.core.model.ScoreAnswer;
import com.topie.zhongkexie.database.expert.model.ExpertItemScore;

public interface IExpertItemScoreService extends IService<ExpertItemScore> {

	List<ExpertItemScore> selectByFilter(ExpertItemScore entity);

	List<Map> selectScoreInfo(Integer userId, String itemId);

	BigDecimal divScore(ScoreAnswer one);
	List<Map> selectScoreCount(Integer paperId);

}
