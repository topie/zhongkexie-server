package com.topie.zhongkexie.expert.service;

import java.util.List;

import com.topie.zhongkexie.common.baseservice.IService;
import com.topie.zhongkexie.database.expert.model.ExpertItemScore;

public interface IExpertItemScoreService extends IService<ExpertItemScore> {

	List<ExpertItemScore> selectByFilter(ExpertItemScore entity);

}
