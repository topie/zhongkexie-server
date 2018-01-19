package com.topie.zhongkexie.core.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.baseservice.impl.BaseService;
import com.topie.zhongkexie.core.dto.ItemDto;
import com.topie.zhongkexie.core.dto.OptionDto;
import com.topie.zhongkexie.core.dto.PagerUserDto;
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
    
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
	public PageInfo<ScorePaper> selectByFilterAndPageForCheck(
			ScorePaper scorePaper, int pageNum, int pageSize) {
    	 PageHelper.startPage(pageNum, pageSize);
    	 Example example = new Example(ScorePaper.class);
         Example.Criteria criteria = example.createCriteria();
         if(scorePaper.getStatus()!=null)criteria.andEqualTo("status",scorePaper.getStatus());
         List ls = new ArrayList();
         ls.add(PagerUserDto.PAPERSTATUS_SUBMMIT);
         ls.add(PagerUserDto.PAPERSTATUS_EGIS);
         criteria.andIn("approveStatus",ls);
         if (StringUtils.isNotEmpty(scorePaper.getTitle())) criteria.andLike("title", "%" + scorePaper.getTitle() + "%");
         if (StringUtils.isNotEmpty(scorePaper.getSortWithOutOrderBy()))
             example.setOrderByClause(scorePaper.getSortWithOutOrderBy());
         List<ScorePaper> list  = getMapper().selectByExample(example);
         
         return new PageInfo<>(list);
	}


    @Override
    public List<ScorePaper> selectByFilter(ScorePaper scorePaper) {
        Example example = new Example(ScorePaper.class);
        Example.Criteria criteria = example.createCriteria();
        if(scorePaper.getStatus()!=null)criteria.andEqualTo("status",scorePaper.getStatus());
        if(scorePaper.getApproveStatus()!=null)criteria.andEqualTo("approveStatus",scorePaper.getApproveStatus());
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
                ScoreItemOption scoreItemOption = new ScoreItemOption();
    	        scoreItemOption.setSort_("option_sort_asc");
    	        scoreItemOption.setItemId(item.getId());
    	        List<ScoreItemOption> scoreItemOptions = iScoreItemOptionService.selectByFilter(scoreItemOption);
                for (ScoreItemOption itemOption : scoreItemOptions) {
                    OptionDto optionDto = new OptionDto();
                    optionDto.setId(itemOption.getId());
                    optionDto.setTitle(itemOption.getOptionTitle());
                    itemOptions.add(optionDto);
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
        ScorePaper page = getMapper().selectByPrimaryKey(id);
        page.setApproveStatus(result);
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


	@Override
	public JSONObject getContentJson(Integer paperId, String indexIds, String orgIds) {
		ScorePaper p = this.getMapper().selectByPrimaryKey(paperId);
		if(p==null)return new JSONObject();
		String title = p.getTitle();
		 List<ScoreIndex> childIndices = new ArrayList<>();
	        Example ex = new Example(ScoreIndex.class);
	        Criteria c = ex.createCriteria();
	        c.andEqualTo("paperId", paperId);
	        List list = Arrays.asList(indexIds.split(","));
	        c.andIn("id",list);
	        List<ScoreIndex> indices = iScoreIndexService.selectByExample(ex);
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
	                ScoreItemOption scoreItemOption = new ScoreItemOption();
	    	        scoreItemOption.setSort_("option_sort_asc");
	    	        scoreItemOption.setItemId(item.getId());
	    	        List<ScoreItemOption> scoreItemOptions = iScoreItemOptionService.selectByFilter(scoreItemOption);
	                for (ScoreItemOption itemOption : scoreItemOptions) {
                        OptionDto optionDto = new OptionDto();
                        optionDto.setId(itemOption.getId());
                        optionDto.setTitle(itemOption.getOptionTitle());
                        itemOptions.add(optionDto);
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
	        return jo;
	}

	
}
