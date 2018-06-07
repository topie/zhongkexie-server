package com.topie.zhongkexie.database.expert.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import tk.mybatis.mapper.common.Mapper;

import com.topie.zhongkexie.database.expert.model.ExpertItemScore;

public interface ExpertItemScoreMapper extends Mapper<ExpertItemScore> {

	List<Map> selectScoreInfo(@Param("userId")Integer userId, @Param("itemId") String itemId);
}