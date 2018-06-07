package com.topie.zhongkexie.database.mem.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import tk.mybatis.mapper.common.Mapper;

import com.topie.zhongkexie.core.dto.PagerUserDto;
import com.topie.zhongkexie.database.mem.model.MemUserScore;

public interface MemUserScoreMapper extends Mapper<MemUserScore> {

	List<PagerUserDto> selectByExampleEx(@Param("paperId")Integer paperId
			,@Param("userId") Integer userId,
			@Param("userName")String userName);
}