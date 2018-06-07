package com.topie.zhongkexie.mem.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.baseservice.IService;
import com.topie.zhongkexie.core.dto.PagerUserDto;
import com.topie.zhongkexie.database.mem.model.MemUserScore;

@Service
public interface IMemUserScoreService extends IService<MemUserScore>{

	PageInfo<PagerUserDto> selectByFilterAndPage(PagerUserDto pagerUserDto,
			int pageNum, int pageSize);
	List<PagerUserDto> selectByFilter(PagerUserDto pagerUserDto);
	List<MemUserScore> selectByFilter(MemUserScore memUserScore);
	void divScore(Integer paperId);
	void divScore(MemUserScore memUserScore);

}
