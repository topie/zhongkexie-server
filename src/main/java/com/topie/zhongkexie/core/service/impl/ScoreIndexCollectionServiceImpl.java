package com.topie.zhongkexie.core.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import tk.mybatis.mapper.util.StringUtil;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.baseservice.impl.BaseService;
import com.topie.zhongkexie.core.service.IScoreIndexCollectionService;
import com.topie.zhongkexie.database.core.model.ScoreIndexCollection;

@Service
public class ScoreIndexCollectionServiceImpl extends BaseService<ScoreIndexCollection> implements IScoreIndexCollectionService {
	
	@Override
	public List<ScoreIndexCollection> selectByFilter(ScoreIndexCollection dict) {
		Example ex = new Example(ScoreIndexCollection.class);
		Criteria c = ex.createCriteria();
		if(StringUtils.isNotEmpty(dict.getName()))c.andLike("name", "%"+dict.getName()+"%");
		if(dict.getPaperId()!=null)c.andEqualTo("paperId", dict.getPaperId());
		return getMapper().selectByExample(ex);
	}

	@Override
	public PageInfo<ScoreIndexCollection> selectByFilterAndPage(ScoreIndexCollection dict, int pageNum,
			int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<ScoreIndexCollection> list = selectByFilter(dict);
		return new PageInfo<ScoreIndexCollection>(list);
	}

	@Override
	public Set<String> getSelectedNodesId(Integer paperId, Integer id) {
		Example ex = new Example(ScoreIndexCollection.class);
		Criteria c = ex.createCriteria();
		c.andEqualTo("paperId", paperId);
		if(id!=null){
			c.andNotEqualTo("id", id);
		}
		List<ScoreIndexCollection> list = getMapper().selectByExample(ex);
		Set<String> set = new HashSet<String>();
		for(ScoreIndexCollection s:list){
			String indexs = s.getIndexCollection();
			if(StringUtil.isNotEmpty(indexs))
				for(String indexid:indexs.split(","))
					set.add(indexid);
		}
		return set;
	}

}
