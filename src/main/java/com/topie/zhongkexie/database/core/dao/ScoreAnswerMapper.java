package com.topie.zhongkexie.database.core.dao;

import org.apache.ibatis.annotations.Param;

import tk.mybatis.mapper.common.Mapper;

import com.topie.zhongkexie.database.core.model.ScoreAnswer;

public interface ScoreAnswerMapper extends Mapper<ScoreAnswer> {

	Integer selectRankingInput(@Param("itemId")Integer itemId, @Param("answer")String answer);
}