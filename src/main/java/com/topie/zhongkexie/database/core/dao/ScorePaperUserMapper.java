package com.topie.zhongkexie.database.core.dao;

import java.util.List;

import tk.mybatis.mapper.common.Mapper;

import com.topie.zhongkexie.core.dto.PagerUserDto;
import com.topie.zhongkexie.database.core.model.ScorePaperUser;

public interface ScorePaperUserMapper extends Mapper<ScorePaperUser> {

	/**
	 * 获取已提交列表   按分数倒叙
	 * @param pagerUserDto
	 * @return
	 */
	List<PagerUserDto> selectUserCommitPaper(PagerUserDto pagerUserDto);
	
	List<ScorePaperUser> selectByPagerId(Integer paperId);
	List<PagerUserDto> selectExpertUserCommitPaper(PagerUserDto pagerUserDto);
	/**
	 * 获取公示列表   按编码排序
	 * @param pagerUserDto
	 * @return
	 */
	List<PagerUserDto> selectUserPubulicityPaper(PagerUserDto pagerUserDto);
}