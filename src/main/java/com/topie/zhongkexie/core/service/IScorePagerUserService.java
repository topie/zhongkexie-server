package com.topie.zhongkexie.core.service;

import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.baseservice.IService;
import com.topie.zhongkexie.core.dto.PagerUserDto;
import com.topie.zhongkexie.database.core.model.ScorePaper;
import com.topie.zhongkexie.database.core.model.ScorePaperUser;

/**
 * 
 * @author root
 *
 */
public interface IScorePagerUserService extends IService<ScorePaperUser>{
	/**
	 * 学会提交答案后审核
	 * @param id
	 * @param result
	 * @param feedback
	 */
	void check(int id, short result,String feedback);
	/**
	 * 获取当前学会 对应得填报员得填报信息
	 * @param scorePaper
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	PageInfo<ScorePaper> selectByFilterAndPage(ScorePaper scorePaper,
			int pageNum, int pageSize);
	/**
	 * 中科协查看所有提交得试题
	 * @param scorePaper
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	PageInfo<PagerUserDto> selectAllUserCommit(PagerUserDto pagerUserDto,
			int pageNum, int pageSize);
	/**
	 * 获取当前用户的提交状态
	 * @param paperId
	 * @return
	 */
	ScorePaperUser getCurrentUserScorePaperUser(Integer paperId);

}
