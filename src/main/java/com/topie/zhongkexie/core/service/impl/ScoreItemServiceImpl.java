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
import com.topie.zhongkexie.database.core.dao.ScoreIndexMapper;
import com.topie.zhongkexie.database.core.dao.ScoreItemMapper;
import com.topie.zhongkexie.database.core.model.ScoreIndex;
import com.topie.zhongkexie.database.core.model.ScoreItem;

/**
 * Created by chenguojun on 2017/4/19.
 */
@Service
public class ScoreItemServiceImpl extends BaseService<ScoreItem> implements IScoreItemService {

	@Autowired
	private ScoreItemMapper scoreItemMapper;
	@Autowired
	private ScoreIndexMapper scoreIndexMapper;
    @Override
    public PageInfo<ScoreItem> selectByFilterAndPage(ScoreItem scoreItem, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ScoreItem> list = selectByFilter(scoreItem);
        return new PageInfo<>(list);
    }

    @Override
    public List<ScoreItem> selectByFilter(ScoreItem scoreItem) {
        return selectByFilter(null, scoreItem);
    }

	@Override
	public String selectPath(Integer itemId) {
		ScoreItem item = selectByKey(itemId);
		if(item==null)return null;
		String path = getPath(item.getIndexId());
		path = path.substring(1)+"_"+item.getTitle().substring(0,item.getTitle().length()>10?10:item.getTitle().length())
				.replaceAll("[\\,，。\\.；\\;、]","");
		return path;
	}

	private String getPath(Integer indexId) {
		String path = "";
		
		ScoreIndex index = scoreIndexMapper.selectByPrimaryKey(indexId);
		if(index==null){
			return path;
		}else{
			path = getPath(index.getParentId())+"_"+index.getName();
		}
		return path;
	}

	@Override
	public List<ScoreItem> selectByFilter(Integer paperId, ScoreItem scoreItem) {
		Example example = new Example(ScoreItem.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(scoreItem.getTitle())) criteria.andLike("title", "%" + scoreItem.getTitle() + "%");
        if (scoreItem.getIndexId() != null) criteria.andEqualTo("indexId", scoreItem.getIndexId());
        if (scoreItem.getType() != null) criteria.andEqualTo("type", scoreItem.getType());
        if (StringUtils.isNotEmpty(scoreItem.getScoreType())) criteria.andEqualTo("scoreType", scoreItem.getScoreType());
        if (StringUtils.isNotEmpty(scoreItem.getRelatedField())) criteria.andEqualTo("relatedField", scoreItem.getRelatedField());
        if (StringUtils.isNotEmpty(scoreItem.getSortWithOutOrderBy())){
            example.setOrderByClause(scoreItem.getSortWithOutOrderBy());
        }else{
        	example.setOrderByClause("sort asc");
        }
        if (StringUtils.isNotEmpty(scoreItem.getResponsibleDepartment())||paperId!=null) {
        	return scoreItemMapper.selectByExampleEx(example,scoreItem.getResponsibleDepartment(),paperId);
        }
        return getMapper().selectByExample(example);
		
	}

}
