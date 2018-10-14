package com.topie.zhongkexie.database.expert.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import tk.mybatis.mapper.common.Mapper;

import com.topie.zhongkexie.database.expert.model.ExpertDeptUser;

public interface ExpertDeptUserMapper extends Mapper<ExpertDeptUser> {
	/*
	 *专家打分情况    
	 * @param map
	 * @return{
	 * 	expertName
	 * 	expertId
	 * 	commited
	 * 	finished
	 * }
	 */
	List<Map> countExpertFinish(Map map);
	/*
	 *专家对应评分指标
	 * @param map
	 * @return{
	 * 	related_field
	 * 	user_id
	 * 	real_name
	 * 	scoreItem.*
	 * }
	 */
	List<Map> selectExportItemMap(@Param("paperId")Integer paperId,@Param("userId") Integer userId);
	/*
	 *查询专家对应的该试题 未完成评价的学会名称
	 * @param map
	 * @return{
	 *  login_name
	 *  display_name
	 * }
	 */
	List<Map> selectNotFinishedDept(@Param("paperId")Integer paperId,@Param("itemId")Integer itemId,@Param("expertUserId")Integer expertUserId);
	List<Map> selectFinishedDept(@Param("paperId")Integer paperId,@Param("itemId")Integer itemId,@Param("expertUserId")Integer expertUserId);
	/**
	 * 更新评价状态 包含学会
	 * @param paperId
	 * @param itemId
	 * @param expertUserId
	 * @param depts
	 * @param status
	 */
	void updateExpertUserIncludeStatus(@Param("paperId")Integer paperId,@Param("indexCollExpertId") String indexCollExpertId,
			@Param("expertUserId")Integer expertUserId,@Param("orgUserIds") Set<Integer> depts,@Param("status")  String status);
	/**
	 * 更新评价状态 不包含的学会
	 * @param paperId
	 * @param itemId
	 * @param expertUserId
	 * @param depts
	 * @param status
	 */
	void updateExpertUserExcludeStatus(@Param("paperId")Integer paperId,@Param("indexCollExpertId") String indexCollExpertId,
			@Param("expertUserId")Integer expertUserId,@Param("orgUserIds") Set<Integer> depts,@Param("status") String string);
}