package com.topie.zhongkexie.database.publicity.dao;

import java.util.List;

import tk.mybatis.mapper.common.Mapper;

import com.topie.zhongkexie.database.publicity.model.Publicity;

public interface PublicityMapper extends Mapper<Publicity> {

	List<Publicity> selectByPublicity(Publicity publicity);
}