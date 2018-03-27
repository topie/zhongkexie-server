package com.topie.zhongkexie.core.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.baseservice.impl.BaseService;
import com.topie.zhongkexie.core.service.IScoreAnswerService;
import com.topie.zhongkexie.core.service.IScorePaperService;
import com.topie.zhongkexie.database.core.model.ScoreAnswer;
import com.topie.zhongkexie.database.core.model.ScorePaper;

/**
 * Created by chenguojun on 2017/4/19.
 */
@Service
public class ScoreAnswerServiceImpl extends BaseService<ScoreAnswer> implements
		IScoreAnswerService {
	@Autowired
	IScorePaperService iScorePaperService;

	@Override
	public PageInfo<ScoreAnswer> selectByFilterAndPage(ScoreAnswer scoreAnswer,
			int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<ScoreAnswer> list = selectByFilter(scoreAnswer);
		return new PageInfo<>(list);
	}

	@Override
	public List<ScoreAnswer> selectByFilter(ScoreAnswer scoreAnswer) {
		Example example = new Example(ScoreAnswer.class);
		Example.Criteria criteria = example.createCriteria();
		if (scoreAnswer.getIndexId() != null)
			criteria.andEqualTo("indexId", scoreAnswer.getItemId());
		if (scoreAnswer.getItemId() != null)
			criteria.andEqualTo("itemId", scoreAnswer.getItemId());
		if (scoreAnswer.getPaperId() != null)
			criteria.andEqualTo("paperId", scoreAnswer.getPaperId());
		if (scoreAnswer.getUserId() != null)
			criteria.andEqualTo("userId", scoreAnswer.getUserId());
		if (StringUtils.isNotEmpty(scoreAnswer.getSortWithOutOrderBy()))
			example.setOrderByClause(scoreAnswer.getSortWithOutOrderBy());
		return getMapper().selectByExample(example);
	}

	@Override
	public BigDecimal getUserScore(Integer paperId, Integer userId) {
		ScorePaper scorePaper = iScorePaperService.selectByKey(paperId);
		int maxFilse = scorePaper.getFalsityCountItem();// 一个指标下试题虚假次数
		int maxIndexFilse = scorePaper.getFalsityCount();// 指标虚假次数
		ScoreAnswer scoreAnswer = new ScoreAnswer();
		scoreAnswer.setPaperId(paperId);
		scoreAnswer.setUserId(userId);
		scoreAnswer.setSort_("indexId_asc");
		List<ScoreAnswer> list = selectByFilter(scoreAnswer);
		int indexId = 0;
		int count = 0;
		int indexCount = 0;
		BigDecimal totalScore = new BigDecimal(0);
		List<BigDecimal> indexScores = new ArrayList<BigDecimal>();
		for (ScoreAnswer an : list) {
			int index = an.getIndexId();
			if (index != indexId) {
				if (count >= maxFilse) {
					// 试题虚假
					for (BigDecimal arg0 : indexScores) {
						totalScore = totalScore.subtract(arg0);
					}
					indexCount++;
					if (indexCount >= maxIndexFilse) {// 指标虚假次数过多
						return new BigDecimal(0);
					}
				}
				indexId = an.getIndexId();
				count = 0;
				indexScores.clear();
			}
			if(!an.getAnswerReal()){
				count++;
			}
			BigDecimal score = an.getAnswerScore();
			indexScores.add(score);
			totalScore = totalScore.add(score);
		}
		return totalScore;

	}

}
