package com.topie.zhongkexie.core.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.baseservice.IService;
import com.topie.zhongkexie.database.core.model.ScorePaper;

/**
 * Created by chenguojun on 2017/4/19.
 */
public interface IScorePaperService extends IService<ScorePaper> {

    PageInfo<ScorePaper> selectByFilterAndPage(ScorePaper scorePaper, int pageNum, int pageSize);

    List<ScorePaper> selectByFilter(ScorePaper scorePaper);

    String getContentJson(Integer paperId, String title);

	void check(int id, short result);
	/**
	 * 中科协 评价表 审核列表
	 * @param scorePaper
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	PageInfo<ScorePaper> selectByFilterAndPageForCheck(ScorePaper scorePaper,
			int pageNum, int pageSize);

}
