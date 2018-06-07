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
import com.topie.zhongkexie.common.utils.JavaExecScript;
import com.topie.zhongkexie.core.service.IScoreAnswerService;
import com.topie.zhongkexie.core.service.IScoreItemOptionService;
import com.topie.zhongkexie.core.service.IScoreItemService;
import com.topie.zhongkexie.core.service.IScorePaperService;
import com.topie.zhongkexie.database.core.dao.ScoreAnswerMapper;
import com.topie.zhongkexie.database.core.model.ScoreAnswer;
import com.topie.zhongkexie.database.core.model.ScoreItem;
import com.topie.zhongkexie.database.core.model.ScoreItemOption;
import com.topie.zhongkexie.database.core.model.ScorePaper;
import com.topie.zhongkexie.expert.service.IExpertItemScoreService;

/**
 * Created by chenguojun on 2017/4/19.
 */
@Service
public class ScoreAnswerServiceImpl extends BaseService<ScoreAnswer> implements
		IScoreAnswerService {
	@Autowired
	IScorePaperService iScorePaperService;
	@Autowired
	IScoreItemService iScoreItemService;
	@Autowired
	ScoreAnswerMapper scoreAnswerMapper;
	@Autowired
	private IScoreItemOptionService iScoreItemOptionService;
	@Autowired
	private IExpertItemScoreService iExpertItemScoreService;
	
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

	@Override
	public String getAnswerOfRanking(Integer itemId, String answer) {
		ScoreItem item = this.iScoreItemService.selectByKey(itemId);
		String scoreType = item.getScoreType();
		switch(scoreType){
			case "1"://统计项
				break;
			case "2"://线性打分
				break;
			case "3"://专家打分
				return "";
			default:break;
		}
		Integer type = item.getType();
		switch(type){
			case 0://填空
				return "";
			case 1://单选
				
			case 2://多选
				return ranking_check(itemId,answer);
			case 3://填空 多
				return "";
			case 4://数字填空
				return ranking_input(itemId,answer);
			default:break;
		}
		
		return "";
	}

	private String ranking_input(Integer itemId, String answer) {
		Integer rank = this.scoreAnswerMapper.selectRankingInput(itemId,answer);
		ScoreAnswer scoreAnswer = new ScoreAnswer();
		scoreAnswer.setItemId(itemId);
		int s = this.mapper.selectCount(scoreAnswer);
		double r = (double)rank/s*100;
		r =new BigDecimal(r).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue(); 
		//return "超过了"+r+"%的用户，共"+s;
		return "共"+s+"个学会填报，超过了"+r+"%的学会";
	}

	private String ranking_check(Integer itemId, String answer) {
		ScoreAnswer scoreAnswer = new ScoreAnswer();
		scoreAnswer.setItemId(itemId);
		int s = this.mapper.selectCount(scoreAnswer);
		scoreAnswer.setAnswerValue(answer);
		int checks = this.mapper.selectCount(scoreAnswer);
		double rank = (double)checks/s*100;
		rank =new BigDecimal(rank).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue(); 
		//return rank+"%的用户选择了此选项，共"+s;
		return "共"+s+"个学会填报，其中"+rank+"%的学会选择此选项";
	}

	@Override
	public void divScore(ScoreItem scoreItem, ScoreAnswer sa) {
		if (scoreItem.getType() == 0) {
			// 填空
			String logic = scoreItem.getOptionLogic();
		} else if (scoreItem.getType() == 1) {
			// 单选
			Integer optionId = Integer.parseInt(sa.getAnswerValue());
			ScoreItemOption option = iScoreItemOptionService
					.selectByKey(optionId);
			sa.setAnswerScore(scoreItem.getScore().multiply(
					option.getOptionRate()));
		} else if (scoreItem.getType() == 2) {
			// 多选
			
		}
		Integer indexId = scoreItem.getIndexId();
		sa.setIndexId(indexId);
		// 计算分数
		if (scoreItem.getScoreType().equals("2")) {// 线性打分
			try {
				BigDecimal s = getScore(scoreItem,sa);
				if(s!=null)
					sa.setAnswerScore(s);
			} catch (Exception e) {
				sa.setAnswerScore(new BigDecimal("0"));
				System.err.println("线性打分项评分时出现异常：");
				e.printStackTrace();
			}
		} else if(scoreItem.getScoreType().equals("3")){//专家打分项
			sa.setAnswerScore(iExpertItemScoreService.divScore(sa));
			
		}else if(scoreItem.getScoreType().equals("1")){// 统计项 
			sa.setAnswerScore(new BigDecimal("0"));
		}else{//其他
			
		}
		
	}
	
	private BigDecimal getScore(ScoreItem scoreItem,ScoreAnswer sa) {
		BigDecimal score = null;
		String functionBody = scoreItem.getOptionLogic();
		if(StringUtils.isEmpty(functionBody.trim())){
			return null;
		}
		ScoreAnswer referItemValue = new ScoreAnswer();
		ScoreItemOption scoreItemOption = new ScoreItemOption();
		scoreItemOption.setItemId(scoreItem.getId());
		List<ScoreItemOption> opts = this.iScoreItemOptionService
				.selectByFilter(scoreItemOption);
		score = JavaExecScript.jsFunction(sa, opts, referItemValue,
				functionBody);
		return score;

	}

}
