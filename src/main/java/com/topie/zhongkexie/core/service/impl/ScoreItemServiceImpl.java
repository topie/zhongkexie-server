package com.topie.zhongkexie.core.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.baseservice.impl.BaseService;
import com.topie.zhongkexie.core.service.IScoreItemService;
import com.topie.zhongkexie.database.core.dao.ScoreItemMapper;
import com.topie.zhongkexie.database.core.model.ScoreItem;

/**
 * Created by chenguojun on 2017/4/19.
 */
@Service
public class ScoreItemServiceImpl extends BaseService<ScoreItem> implements IScoreItemService {

	@Autowired
	private ScoreItemMapper scoreItemMapper;
    @Override
    public PageInfo<ScoreItem> selectByFilterAndPage(ScoreItem scoreItem, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ScoreItem> list = selectByFilter(scoreItem);
        return new PageInfo<>(list);
    }

    @Override
    public List<ScoreItem> selectByFilter(ScoreItem scoreItem) {
        Example example = new Example(ScoreItem.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(scoreItem.getTitle())) criteria.andLike("title", "%" + scoreItem.getTitle() + "%");
        if (scoreItem.getIndexId() != null) criteria.andEqualTo("indexId", scoreItem.getIndexId());
        if (scoreItem.getType() != null) criteria.andEqualTo("type", scoreItem.getType());
        if (StringUtils.isNotEmpty(scoreItem.getRelatedField())) criteria.andEqualTo("relatedField", scoreItem.getRelatedField());
        if (StringUtils.isNotEmpty(scoreItem.getSortWithOutOrderBy()))
            example.setOrderByClause(scoreItem.getSortWithOutOrderBy());
        if (StringUtils.isNotEmpty(scoreItem.getResponsibleDepartment())) {
        	return scoreItemMapper.selectByExampleEx(example,scoreItem.getResponsibleDepartment());
        }
        return getMapper().selectByExample(example);
    }

}
