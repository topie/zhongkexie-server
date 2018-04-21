package com.topie.zhongkexie.core.service;

import java.util.List;
import java.util.Set;

import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.baseservice.IService;
import com.topie.zhongkexie.database.core.model.ScoreIndexCollection;

public interface IScoreIndexCollectionService extends IService<ScoreIndexCollection>{

	PageInfo<ScoreIndexCollection> selectByFilterAndPage(
			ScoreIndexCollection scoreIndexCollection, int pageNum, int pageSize);

	List<ScoreIndexCollection> selectByFilter(ScoreIndexCollection scoreIndexCollection);

	Set<String> getSelectedNodesId(Integer paperId, Integer id);

}
