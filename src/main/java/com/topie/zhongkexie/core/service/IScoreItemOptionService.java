package com.topie.zhongkexie.core.service;

import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.baseservice.IService;
import com.topie.zhongkexie.database.core.model.ScoreItemOption;

import java.util.List;

/**
 * Created by chenguojun on 2017/4/19.
 */
public interface IScoreItemOptionService extends IService<ScoreItemOption> {

    PageInfo<ScoreItemOption> selectByFilterAndPage(ScoreItemOption scoreItemOption, int pageNum, int pageSize);

    List<ScoreItemOption> selectByFilter(ScoreItemOption scoreItemOption);

}
