package com.topie.zhongkexie.expert.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.baseservice.impl.BaseService;
import com.topie.zhongkexie.core.service.IScoreIndexCollectionService;
import com.topie.zhongkexie.database.core.model.ScoreIndexCollection;
import com.topie.zhongkexie.database.expert.model.IndexCollExpert;
import com.topie.zhongkexie.expert.service.IIndexCollExpertService;

@Service
public class IndexCollExpertServiceImpl extends BaseService<IndexCollExpert> implements
		IIndexCollExpertService {
	@Autowired
	private IScoreIndexCollectionService iScoreIndexCollectionService;

	@Override
	public PageInfo<IndexCollExpert> selectByFilterAndPage(IndexCollExpert indexCollExpert,
			int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<IndexCollExpert> list = selectByFilter(indexCollExpert);
		return new PageInfo<IndexCollExpert>(list);
	}

	@Override
	public List<IndexCollExpert> selectByFilter(IndexCollExpert indexCollExpert) {
		Example ex = new Example(IndexCollExpert.class);
		Criteria c = ex.createCriteria();
		if (!StringUtils.isEmpty(indexCollExpert.getName())) {
			c.andLike("name", "%" + indexCollExpert.getName() + "%");
		}
		if (indexCollExpert.getPaperId()!=null) {
			c.andEqualTo("paper_id", indexCollExpert.getPaperId());
		}
		if (indexCollExpert.getPaperExpertId()!=null) {
			c.andEqualTo("paperExpertId", indexCollExpert.getPaperExpertId());
		}
		return getMapper().selectByExample(ex);
	}

	@Override
	public int init(Integer paperId,Integer paperExpertId,String fieldType) {
		ScoreIndexCollection search = new ScoreIndexCollection();
		search.setPaperId(paperId);
		List<ScoreIndexCollection> list = iScoreIndexCollectionService.selectByFilter(search);
		for(ScoreIndexCollection item:list){
			IndexCollExpert pe = new IndexCollExpert();
			pe.setName(item.getName()+"指标组");
			pe.setPaperId(paperId);
			pe.setPaperExpertId(paperExpertId);
			pe.setRelatedField(item.getRelatedField());
			pe.setFieldType(fieldType);
			this.saveNotNull(pe);
		}
		return list.size();
	}

}
