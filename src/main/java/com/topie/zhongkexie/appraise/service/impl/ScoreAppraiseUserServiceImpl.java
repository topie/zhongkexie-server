package com.topie.zhongkexie.appraise.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.appraise.service.IScoreAppraiseUserService;
import com.topie.zhongkexie.common.baseservice.impl.BaseService;
import com.topie.zhongkexie.database.appriase.model.ScoreAppraiseUser;

@Service
public class ScoreAppraiseUserServiceImpl extends BaseService<ScoreAppraiseUser> implements
		IScoreAppraiseUserService {

	
	@Override
	public PageInfo<ScoreAppraiseUser> selectByFilterAndPage(
			ScoreAppraiseUser scoreAppraiseUser, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<ScoreAppraiseUser> list = selectByFilter(scoreAppraiseUser);
		return new PageInfo<ScoreAppraiseUser>(list);
	}

	@Override
	public List<ScoreAppraiseUser> selectByFilter(ScoreAppraiseUser scoreAppraiseUser) {
		Example ex = new Example(ScoreAppraiseUser.class);
		Criteria c = ex.createCriteria();
		
		if (!StringUtils.isEmpty(scoreAppraiseUser.getItemStatus())) {
			c.andLike("itemStatus",  scoreAppraiseUser.getItemStatus() );
		}
		if (null!= scoreAppraiseUser.getPaperId()) {
			c.andEqualTo("paperId", scoreAppraiseUser.getPaperId());
		}if (null!= scoreAppraiseUser.getUserId()) {
			c.andEqualTo("userId", scoreAppraiseUser.getUserId());
		}
		return getMapper().selectByExample(ex);
	}



}
