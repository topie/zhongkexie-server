package com.topie.zhongkexie.core.service;

import java.math.BigDecimal;
import java.util.List;

import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.baseservice.IService;
import com.topie.zhongkexie.database.core.model.ScoreAnswer;
import com.topie.zhongkexie.database.core.model.ScoreItem;

/**
 * Created by chenguojun on 2017/4/19.
 */
public interface IScoreAnswerService extends IService<ScoreAnswer> {

    PageInfo<ScoreAnswer> selectByFilterAndPage(ScoreAnswer scoreAnswer, int pageNum, int pageSize);

    List<ScoreAnswer> selectByFilter(ScoreAnswer scoreAnswer);

    BigDecimal getUserScore(Integer paperId, Integer userId);
    /**
     * 获取 这个答案的排名
     * @param itemId
     * @param answer
     * @return
     */
	String getAnswerOfRanking(Integer itemId, String answer);
	/**
	 * 计算分数
	 * @param scoreItem
	 * @param sa
	 */
	void divScore(ScoreItem scoreItem, ScoreAnswer sa);

}
