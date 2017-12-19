package com.topie.zhongkexie.core.service;

import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.baseservice.IService;
import com.topie.zhongkexie.database.core.model.ScoreAnswer;

import java.util.List;

/**
 * Created by chenguojun on 2017/4/19.
 */
public interface IScoreAnswerService extends IService<ScoreAnswer> {

    PageInfo<ScoreAnswer> selectByFilterAndPage(ScoreAnswer scoreAnswer, int pageNum, int pageSize);

    List<ScoreAnswer> selectByFilter(ScoreAnswer scoreAnswer);

}
