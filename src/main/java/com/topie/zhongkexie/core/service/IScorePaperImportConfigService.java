package com.topie.zhongkexie.core.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.baseservice.IService;
import com.topie.zhongkexie.database.core.model.ScorePaperImportConf;

public interface IScorePaperImportConfigService extends IService<ScorePaperImportConf>{
	public PageInfo<ScorePaperImportConf> selectByFilterAndPage(ScorePaperImportConf searchModel, int pageNum,
			int pageSize) ;

	public List<ScorePaperImportConf> selectByFilter(ScorePaperImportConf conf);

	public List<Map> insertImports(ScorePaperImportConf conf, boolean isTest);
}
