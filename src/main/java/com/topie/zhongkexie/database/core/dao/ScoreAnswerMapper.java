package com.topie.zhongkexie.database.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import tk.mybatis.mapper.common.Mapper;

import com.topie.zhongkexie.database.core.model.ScoreAnswer;

public interface ScoreAnswerMapper extends Mapper<ScoreAnswer> {

	Integer selectRankingInput(@Param("itemId")Integer itemId, @Param("answer")String answer);
	/*
	 * loginName,
	 * displayName,
	 * userId,
	 * count
	 */
	List<Map> selectResultTime(Map map);
	/*
	 * loginName,
	 * displayName,
	 * userId,
	 * score
	 */
	List<Map> selectPartIndexScore(Map map);
	/*
	 * loginName,
	 * displayName,
	 * userId,
	 * answerFile
	 * itemId
	 */
	List<Map> selectUserUploadFileCounts(Map map);
	
}