package com.topie.zhongkexie.core.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.baseservice.impl.BaseService;
import com.topie.zhongkexie.core.service.IScoreIndexService;
import com.topie.zhongkexie.database.core.model.ScoreIndex;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * Created by chenguojun on 2017/4/19.
 */
@Service
public class ScoreIndexServiceImpl extends BaseService<ScoreIndex> implements IScoreIndexService {

    @Override
    public PageInfo<ScoreIndex> selectByFilterAndPage(ScoreIndex scoreIndex, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ScoreIndex> list = selectByFilter(scoreIndex);
        return new PageInfo<>(list);
    }

    @Override
    public List<ScoreIndex> selectByFilter(ScoreIndex scoreIndex) {
        Example example = new Example(ScoreIndex.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(scoreIndex.getName())) criteria.andLike("name", "%" + scoreIndex.getName() + "%");
        if (scoreIndex.getParentId() != null) criteria.andEqualTo("parentId", scoreIndex.getParentId());
        if (StringUtils.isNotEmpty(scoreIndex.getSortWithOutOrderBy()))
            example.setOrderByClause(scoreIndex.getSortWithOutOrderBy());
        return getMapper().selectByExample(example);
    }

}
