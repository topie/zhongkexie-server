package com.topie.zhongkexie.appraise.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.baseservice.IService;
import com.topie.zhongkexie.database.appriase.model.ScoreAppraiseUser;

public interface IScoreAppraiseUserService extends IService<ScoreAppraiseUser> {

	PageInfo<ScoreAppraiseUser> selectByFilterAndPage(ScoreAppraiseUser scoreAppraiseUser, int pageNum, int pageSize);
	List<ScoreAppraiseUser> selectByFilter(ScoreAppraiseUser scoreAppraiseUser);

}
