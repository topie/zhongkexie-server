package com.topie.zhongkexie.core.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.baseservice.IService;
import com.topie.zhongkexie.database.core.model.PaperExpertConf;

public interface IPaperExpertConfService extends IService<PaperExpertConf> {

	PageInfo<PaperExpertConf> selectByFilterAndPage(PaperExpertConf paperExpertConf, int pageNum,
			int pageSize);
	List<PaperExpertConf> selectByFilter(PaperExpertConf paperExpertConf);


}
