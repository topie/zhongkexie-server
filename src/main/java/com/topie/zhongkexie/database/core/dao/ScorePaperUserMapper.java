package com.topie.zhongkexie.database.core.dao;

import java.util.List;

import tk.mybatis.mapper.common.Mapper;

import com.topie.zhongkexie.core.dto.PagerUserDto;
import com.topie.zhongkexie.database.core.model.ScorePaperUser;

public interface ScorePaperUserMapper extends Mapper<ScorePaperUser> {

	List<PagerUserDto> selectUserCommitPaper(PagerUserDto pagerUserDto);
}