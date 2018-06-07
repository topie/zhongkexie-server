package com.topie.zhongkexie.mem.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.baseservice.impl.BaseService;
import com.topie.zhongkexie.core.dto.PagerUserDto;
import com.topie.zhongkexie.core.service.IScoreAnswerService;
import com.topie.zhongkexie.core.service.IScoreItemService;
import com.topie.zhongkexie.database.core.model.ScoreAnswer;
import com.topie.zhongkexie.database.core.model.ScoreItem;
import com.topie.zhongkexie.database.mem.dao.MemUserScoreMapper;
import com.topie.zhongkexie.database.mem.model.MemUserScore;
import com.topie.zhongkexie.mem.service.IMemUserScoreService;

@Service
public class MemUserScoreServiceImpl extends BaseService<MemUserScore>
		implements IMemUserScoreService {

	@Autowired
	private IScoreItemService iScoreItemService;
	@Autowired
	private IScoreAnswerService iScoreAnswerService;

	@Autowired
	private MemUserScoreMapper mapper;

	@Override
	public PageInfo<PagerUserDto> selectByFilterAndPage(
			PagerUserDto pagerUserDto, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		return new PageInfo<PagerUserDto>(selectByFilter(pagerUserDto));
	}

	@Override
	public List<PagerUserDto> selectByFilter(PagerUserDto pagerUserDto) {
		List<PagerUserDto> list = mapper.selectByExampleEx(
				pagerUserDto.getPaperId(), pagerUserDto.getUserId(),
				pagerUserDto.getUserName());
		return list;
	}

	@Override
	public List<MemUserScore> selectByFilter(MemUserScore memUserScore) {
		Example ex = new Example(MemUserScore.class);
		Criteria c = ex.createCriteria();
		if(memUserScore.getPaperId()!=null) c.andEqualTo("paperId",memUserScore.getPaperId());
		if(memUserScore.getUserId()!=null) c.andEqualTo("userId",memUserScore.getUserId());
		if(memUserScore.getItemId()!=null) c.andEqualTo("itemId",memUserScore.getItemId());
		return getMapper().selectByExample(ex);
	}

	@Override
	public void divScore(Integer paperId) {
		MemUserScore memUserScore = new MemUserScore();
		memUserScore.setPaperId(paperId);
		List<MemUserScore> list = selectByFilter(memUserScore);
		for(MemUserScore us:list){
			divScore(us);
		}
		
	}

	@Override
	public void divScore(MemUserScore memUserScore) {
		Integer itemId = memUserScore.getItemId();
		ScoreItem scoreItem = iScoreItemService.selectByKey(itemId);
		ScoreAnswer scoreAnswer = new ScoreAnswer();
		scoreAnswer.setUserId(memUserScore.getUserId());
		scoreAnswer.setPaperId(memUserScore.getPaperId());
		scoreAnswer.setItemId(itemId);
		List<ScoreAnswer> ls = iScoreAnswerService.selectByFilter(scoreAnswer);
		if (ls.size() > 0) {//更新
			ScoreAnswer an = ls.get(0);
			an.setAnswerReal(true);
			an.setItemScore(scoreItem.getScore());
			an.setIndexId(scoreItem.getIndexId());
			an.setAnswerScore(scoreItem.getScore()
					.multiply(memUserScore.getScore())
					.divide(new BigDecimal(100)));
			an.setAnswerReason("");
			an.setAnswerValue("");
			iScoreAnswerService.updateNotNull(an);
		}else{//新增
			scoreAnswer.setAnswerReal(true);
			scoreAnswer.setItemScore(scoreItem.getScore());
			scoreAnswer.setAnswerScore(scoreItem.getScore()
					.multiply(memUserScore.getScore())
					.divide(new BigDecimal(100)));
			scoreAnswer.setIndexId(scoreItem.getIndexId());
			scoreAnswer.setAnswerValue("");
			scoreAnswer.setAnswerReason("");
			iScoreAnswerService.save(scoreAnswer);
		}
		
	}

}
