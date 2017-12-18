package com.topie.zhongkexie.core.service;

import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.baseservice.IService;
import com.topie.zhongkexie.database.core.model.ScoreIndex;

import java.util.List;

/**
 * Created by chenguojun on 2017/4/19.
 */
public interface IScoreIndexService extends IService<ScoreIndex> {

    PageInfo<ScoreIndex> selectByFilterAndPage(ScoreIndex scoreIndex, int pageNum, int pageSize);

    List<ScoreIndex> selectByFilter(ScoreIndex scoreIndex);

}
