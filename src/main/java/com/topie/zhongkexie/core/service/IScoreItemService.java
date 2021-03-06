package com.topie.zhongkexie.core.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.baseservice.IService;
import com.topie.zhongkexie.database.core.model.ScoreItem;

/**
 * Created by chenguojun on 2017/4/19.
 */
public interface IScoreItemService extends IService<ScoreItem> {

    PageInfo<ScoreItem> selectByFilterAndPage(ScoreItem scoreItem, int pageNum, int pageSize);

    List<ScoreItem> selectByFilter(ScoreItem scoreItem);

	String selectPath(Integer itemId);

	List<ScoreItem> selectByFilter(Integer paperId, ScoreItem scoreItem);

}
