package com.topie.zhongkexie.core.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.baseservice.impl.BaseService;
import com.topie.zhongkexie.core.dto.IndexDto;
import com.topie.zhongkexie.core.dto.ItemDto;
import com.topie.zhongkexie.core.dto.OptionDto;
import com.topie.zhongkexie.core.service.IScoreIndexService;
import com.topie.zhongkexie.core.service.IScoreItemOptionService;
import com.topie.zhongkexie.core.service.IScoreItemService;
import com.topie.zhongkexie.core.service.IScorePaperService;
import com.topie.zhongkexie.database.core.model.ScoreIndex;
import com.topie.zhongkexie.database.core.model.ScoreItem;
import com.topie.zhongkexie.database.core.model.ScoreItemOption;
import com.topie.zhongkexie.database.core.model.ScorePaper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

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
        if (StringUtils.isNotEmpty(scorePaper.getTitle())) criteria.andLike("title", "%" + scorePaper.getTitle() + "%");
        if (StringUtils.isNotEmpty(scorePaper.getSortWithOutOrderBy()))
            example.setOrderByClause(scorePaper.getSortWithOutOrderBy());
        return getMapper().selectByExample(example);
    }

    @Override
    public String getContentJson(String title) {
        ScoreIndex scoreIndex = new ScoreIndex();
        scoreIndex.setSort_("sort_asc");
        List<ScoreIndex> indices = iScoreIndexService.selectByFilter(scoreIndex);

        ScoreItem scoreItem = new ScoreItem();
        scoreIndex.setSort_("sort_asc");
        List<ScoreItem> scoreItems = iScoreItemService.selectByFilter(scoreItem);

        ScoreItemOption scoreItemOption = new ScoreItemOption();
        scoreItemOption.setSort_("option_sort_asc");
        List<ScoreItemOption> scoreItemOptions = iScoreItemOptionService.selectByFilter(scoreItemOption);

        List<IndexDto> indexDtos = new ArrayList<>();
        for (ScoreIndex index : indices) {
            IndexDto indexDto = new IndexDto();
            indexDto.setId(index.getId());
            indexDto.setPid(index.getParentId());
            indexDto.setTitle(index.getName());
            indexDto.setScore(index.getScore());
            List<ItemDto> ids = new ArrayList<>();
            for (ScoreItem item : scoreItems) {
                if (item.getIndexId().intValue() == index.getId()) {
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
            }
            indexDto.setIds(ids);
            indexDtos.add(indexDto);
        }
        JSONObject jo = JSONObject.parseObject("{}");
        jo.put("title", title);
        JSONArray jsonArray = JSONArray.parseArray("[]");
        for (IndexDto index : indexDtos) {
            if (index.getPid() == 0) {
                JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(index));
                jsonObject.put("type", "index");
                getJson(jsonObject, indexDtos, index);
                jsonObject.remove("ids");
                jsonArray.add(jsonObject);
            }
        }
        jo.put("data", jsonArray);
        return jo.toJSONString();
    }

    private void getJson(JSONObject jsonObject, List<IndexDto> indices, IndexDto index) {
        if (index != null) {
            for (IndexDto i : indices) {
                if (i.getPid().intValue() == index.getId().intValue()) {
                    JSONObject io = JSONObject.parseObject(JSONObject.toJSONString(i));
                    io.remove("ids");
                    if (jsonObject.get("items") != null) {
                        JSONArray items = (JSONArray) jsonObject.get("items");
                        io.put("type", "index");
                        items.add(io);
                        getJson(io, indices, i);
                    } else {
                        JSONArray items = JSONArray.parseArray("[]");
                        items.add(io);
                        io.put("type", "index");
                        jsonObject.put("items", items);
                    }
                    getJson(io, indices, i);
                }
            }
            if (jsonObject.get("items") == null) {
                jsonObject.put("items", index.getIds());
            }
        }
    }

}
