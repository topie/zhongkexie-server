package com.topie.zhongkexie.core.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.baseservice.impl.BaseService;
import com.topie.zhongkexie.core.service.IScoreItemOptionService;
import com.topie.zhongkexie.database.core.model.ScoreItemOption;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * Created by chenguojun on 2017/4/19.
 */
@Service
public class ScoreItemOptionServiceImpl extends BaseService<ScoreItemOption> implements IScoreItemOptionService {

    @Override
    public PageInfo<ScoreItemOption> selectByFilterAndPage(ScoreItemOption scoreItemOption, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ScoreItemOption> list = selectByFilter(scoreItemOption);
        return new PageInfo<>(list);
    }

    @Override
    public List<ScoreItemOption> selectByFilter(ScoreItemOption scoreItemOption) {
        Example example = new Example(ScoreItemOption.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(scoreItemOption.getOptionTitle()))
            criteria.andLike("title", "%" + scoreItemOption.getOptionTitle() + "%");
        if (scoreItemOption.getItemId() != null) criteria.andEqualTo("itemId", scoreItemOption.getItemId());
        if (StringUtils.isNotEmpty(scoreItemOption.getSortWithOutOrderBy()))
            example.setOrderByClause(scoreItemOption.getSortWithOutOrderBy());
        return getMapper().selectByExample(example);
    }

}
