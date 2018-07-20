package com.topie.zhongkexie.core.service;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.baseservice.IService;
import com.topie.zhongkexie.database.core.model.Attachment;
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

	JSONObject getContentJson(Integer paperId, String indexIds, String orgIds);
	/**
	 * 传入copyPaperId 复制paperId 下的所有指标和题目
	 * @param scorePaper
	 * @param copyPaperId
	 * @return
	 */
	int saveNotNull(ScorePaper scorePaper, Integer copyPaperId);
	/**
	 * 导出数据 可选择指标  选择汇总各个学会提交的答案
	 * @param paperId
	 * @param indexIds
	 * @param orgIds
	 * @param type 
	 */
	HSSFWorkbook exportPaper(Integer paperId, String indexIds, String orgIds, String type);
	/**
	 * 专家获取当前用户的可查看试卷信息
	 * @param paperId
	 * @return
	 */
	String getCurrentUserPaper(Integer paperId);

	void updateWightScore(Integer paperId);

	void updateWightUserScore(Integer paperId);
	/**
	 * 导入外部数据答案 我们的模板
	 * @param attachment
	 * @param userId 
	 * @param paperId 
	 */
	void importAnswer(Attachment attachment, Integer paperId, Integer userId);
	/**
	 * 根据外部导出数据导入 他们的模板
	 * @param attachment
	 * @param paperId
	 */
	void importAnswer(Attachment attachment, Integer paperId);

}
