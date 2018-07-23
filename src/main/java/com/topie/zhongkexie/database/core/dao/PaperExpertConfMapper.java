package com.topie.zhongkexie.database.core.dao;

import java.util.List;

import tk.mybatis.mapper.common.Mapper;

import com.topie.zhongkexie.database.core.model.PaperExpertConf;

public interface PaperExpertConfMapper extends Mapper<PaperExpertConf> {

	List<PaperExpertConf> selectByPaperExpertConf(
			PaperExpertConf paperExpertConf);
}