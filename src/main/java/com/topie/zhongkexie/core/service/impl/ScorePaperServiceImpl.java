package com.topie.zhongkexie.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.baseservice.impl.BaseService;
import com.topie.zhongkexie.core.dto.ItemDto;
import com.topie.zhongkexie.core.dto.OptionDto;
import com.topie.zhongkexie.core.dto.PaperIndexDto;
import com.topie.zhongkexie.core.service.IScoreIndexService;
import com.topie.zhongkexie.core.service.IScoreItemOptionService;
import com.topie.zhongkexie.core.service.IScoreItemService;
import com.topie.zhongkexie.core.service.IScorePaperService;
import com.topie.zhongkexie.database.core.dao.ScorePaperUserMapper;
import com.topie.zhongkexie.database.core.model.ScoreIndex;
import com.topie.zhongkexie.database.core.model.ScoreItem;
import com.topie.zhongkexie.database.core.model.ScoreItemOption;
import com.topie.zhongkexie.database.core.model.ScorePaper;

/**
 * Created by chenguojun on 2017/4/19.
 */
@Service
public class ScorePaperServiceImpl extends BaseService<ScorePaper> implements IScorePaperService {

    @Autowired
    private IScoreIndexService iScoreIndexService;

    @Autowired
    private IScoreItemService iScoreItemService;

    @Autowired
    private IScoreItemOptionService iScoreItemOptionService;
    
    @Autowired
    ScorePaperUserMapper dScorePaperUserMapper;

    @Override
    public PageInfo<ScorePaper> selectByFilterAndPage(ScorePaper scorePaper, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ScorePaper> list = selectByFilter(scorePaper);
        return new PageInfo<>(list);
    }

    @Override
    public List<ScorePaper> selectByFilter(ScorePaper scorePaper) {
        Example example = new Example(ScorePaper.class);
        Example.Criteria criteria = example.createCriteria();
        if(scorePaper.getStatus()!=null)criteria.andEqualTo("status",scorePaper.getStatus());
        if (StringUtils.isNotEmpty(scorePaper.getTitle())) criteria.andLike("title", "%" + scorePaper.getTitle() + "%");
        if (StringUtils.isNotEmpty(scorePaper.getSortWithOutOrderBy()))
            example.setOrderByClause(scorePaper.getSortWithOutOrderBy());
        return getMapper().selectByExample(example);
    }

    @Override
    public String getContentJson(Integer paperId,String title) {
        List<ScoreIndex> childIndices = new ArrayList<>();
        ScoreIndex scoreIndex = new ScoreIndex();
        scoreIndex.setPaperId(paperId);
        List<ScoreIndex> indices = iScoreIndexService.selectByFilter(scoreIndex);
        ScoreItemOption scoreItemOption = new ScoreItemOption();
        scoreItemOption.setSort_("option_sort_asc");
        List<ScoreItemOption> scoreItemOptions = iScoreItemOptionService.selectByFilter(scoreItemOption);
        for (ScoreIndex index : indices) {
            Boolean flag = true;
            for (ScoreIndex index1 : indices) {
                if (index.getId().intValue() == index1.getParentId().intValue()) {
                    flag = false;
                }
            }
            if (flag) {
                childIndices.add(index);
            }
        }
        List<PaperIndexDto> paperIndexDtos = new ArrayList<>();
        for (ScoreIndex childIndex : childIndices) {
            PaperIndexDto paperIndexDto = new PaperIndexDto();
            String path = childIndex.getName();
            path = getParentIndexString(path, childIndex, indices);
            paperIndexDto.setIndexId(childIndex.getId());
            paperIndexDto.setIndexTitle(childIndex.getName());
            paperIndexDto.setParentIndexTitle(path);
            ScoreItem scoreItem = new ScoreItem();
            scoreItem.setSort_("sort_asc");
            scoreItem.setIndexId(childIndex.getId());
            List<ItemDto> ids = new ArrayList<>();
            List<ScoreItem> scoreItems = iScoreItemService.selectByFilter(scoreItem);
            for (ScoreItem item : scoreItems) {
                ItemDto itemDto = new ItemDto();
                itemDto.setId(item.getId());
                itemDto.setTitle(item.getTitle());
                itemDto.setScore(item.getScore());
                itemDto.setItemType(item.getType());
                List<OptionDto> itemOptions = new ArrayList<>();
                for (ScoreItemOption itemOption : scoreItemOptions) {
                    if (itemOption.getItemId().intValue() == item.getId()) {
                        OptionDto optionDto = new OptionDto();
                        optionDto.setId(itemOption.getId());
                        optionDto.setTitle(itemOption.getOptionTitle());
                        itemOptions.add(optionDto);
                    }
                }
                itemDto.setItems(itemOptions);
                ids.add(itemDto);
            }
            paperIndexDto.setItems(ids);
            paperIndexDtos.add(paperIndexDto);
        }
        JSONObject jo = JSONObject.parseObject("{}");
        jo.put("title", title);
        jo.put("data", paperIndexDtos);
        return jo.toJSONString();
    }

    @Override
    public void check(int id, short result) {
        // TODO Auto-generated method stub
        ScorePaper page = getMapper().selectByPrimaryKey(id);
        page.setStatus(result);
        getMapper().updateByPrimaryKey(page);
    }

    private String getParentIndexString(String path, ScoreIndex childIndex, List<ScoreIndex> indices) {
        Integer parentId = childIndex.getParentId();
        for (ScoreIndex index : indices) {
            if (index.getId().intValue() == parentId) {
                path = index.getName() + ">" + path;
                path = getParentIndexString(path, index, indices);
            }
        }
        return path;
    }

}
