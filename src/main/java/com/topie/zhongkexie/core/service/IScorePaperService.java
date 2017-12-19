package com.topie.zhongkexie.core.service;

import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.baseservice.IService;
import com.topie.zhongkexie.database.core.model.ScorePaper;

import java.util.List;

/**
 * Created by chenguojun on 2017/4/19.
 */
public interface IScorePaperService extends IService<ScorePaper> {

    PageInfo<ScorePaper> selectByFilterAndPage(ScorePaper scorePaper, int pageNum, int pageSize);

    List<ScorePaper> selectByFilter(ScorePaper scorePaper);

    String getContentJson(String title);

	void check(int id, short result);

}
