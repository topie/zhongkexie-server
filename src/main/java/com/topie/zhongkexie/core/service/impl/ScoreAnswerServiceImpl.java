package com.topie.zhongkexie.core.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.baseservice.impl.BaseService;
import com.topie.zhongkexie.core.service.IScoreAnswerService;
import com.topie.zhongkexie.database.core.model.ScoreAnswer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * Created by chenguojun on 2017/4/19.
 */
@Service
public class ScoreAnswerServiceImpl extends BaseService<ScoreAnswer> implements IScoreAnswerService {

    @Override
    public PageInfo<ScoreAnswer> selectByFilterAndPage(ScoreAnswer scoreAnswer, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ScoreAnswer> list = selectByFilter(scoreAnswer);
        return new PageInfo<>(list);
    }

    @Override
    public List<ScoreAnswer> selectByFilter(ScoreAnswer scoreAnswer) {
        Example example = new Example(ScoreAnswer.class);
        Example.Criteria criteria = example.createCriteria();
        if (scoreAnswer.getIndexId() != null) criteria.andEqualTo("indexId", scoreAnswer.getItemId());
        if (scoreAnswer.getItemId() != null) criteria.andEqualTo("itemId", scoreAnswer.getItemId());
        if (scoreAnswer.getPaperId() != null) criteria.andEqualTo("paperId", scoreAnswer.getPaperId());
        if (StringUtils.isNotEmpty(scoreAnswer.getSortWithOutOrderBy()))
            example.setOrderByClause(scoreAnswer.getSortWithOutOrderBy());
        return getMapper().selectByExample(example);
    }

}
