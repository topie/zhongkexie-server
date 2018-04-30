package com.topie.zhongkexie.core.service.impl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.baseservice.impl.BaseService;
import com.topie.zhongkexie.common.exception.DefaultBusinessException;
import com.topie.zhongkexie.common.utils.ExcelReader;
import com.topie.zhongkexie.core.service.IScoreIndexService;
import com.topie.zhongkexie.core.service.IScoreItemOptionService;
import com.topie.zhongkexie.core.service.IScoreItemService;
import com.topie.zhongkexie.core.service.IScorePaperImportConfigService;
import com.topie.zhongkexie.database.core.model.Attachment;
import com.topie.zhongkexie.database.core.model.ScoreIndex;
import com.topie.zhongkexie.database.core.model.ScoreItem;
import com.topie.zhongkexie.database.core.model.ScoreItemOption;
import com.topie.zhongkexie.database.core.model.ScorePaperImportConf;
import com.topie.zhongkexie.system.service.IAttachmentService;

@Service
public class ScorePaperImportConfServiceImpl extends BaseService<ScorePaperImportConf> implements IScorePaperImportConfigService {

	@Autowired
	private IAttachmentService iattachmentService;
	@Autowired
	private IScoreIndexService iScoreIndexService;
	@Autowired
	private IScoreItemService iScoreItemService;
	@Autowired
	private IScoreItemOptionService iScoreItemOptionService;
	@Value("${xuehui.userType}")
	private Integer showLevel;
	@Override
	public PageInfo<ScorePaperImportConf> selectByFilterAndPage(ScorePaperImportConf searchModel, int pageNum,
			int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<ScorePaperImportConf> list = selectByFilter(searchModel);
		PageInfo<ScorePaperImportConf> page = new PageInfo<ScorePaperImportConf>(list);
		return page;
	}

	@Override
	public List<ScorePaperImportConf> selectByFilter(ScorePaperImportConf searchModel) {
		Example ex = new Example(ScorePaperImportConf.class);
		Criteria c = ex.createCriteria();
		if(StringUtils.isNotEmpty(searchModel.getName())) c.andLike("name", "%"+searchModel.getName()+"%");
		ex.setOrderByClause("create_time desc");
		List<ScorePaperImportConf> list = this.getMapper().selectByExample(ex); 
		return list;
	}

	@Override
	public List<Map> insertImports(ScorePaperImportConf conf, boolean isTest) {
		List<Map> errorList = new ArrayList<Map>();
		int paperId = conf.getPaperId();
		int fileId = conf.getFileId();
		conf = this.getMapper().selectByPrimaryKey(conf.getId());
		Attachment att = iattachmentService.selectByKey(fileId);
		String path = att.getAttachmentPath();
		File file = new File(path);
    	List<List<Object>> list = null;
		try {
			list = ExcelReader.readExcel(file);
		} catch (IOException e) {
			throw new DefaultBusinessException(e.getMessage());
		}
    	int i=0;
    	int start=conf.getStart();
    	int end=conf.getEnd();
    	//指标的列
    	String jsonindex= conf.getJsonIndex();
    	//题目的列
    	String jsonitem =conf.getJsonItem();
    	//field mapping
    	String fieldMap =conf.getFieldMapping();
    	//department mapping
    	String deptMap =conf.getDeptMapping();
    	JSONObject deptMapping = JSON.parseObject(deptMap);
    	Map<Integer,Integer> map = new HashMap<Integer,Integer>();//记录当前列数 最后一次更新的 指标ID 供下级取Pid
    	String orgName=null;
    	JSONArray jsonIndex = JSONArray.parseArray(jsonindex);
    	JSONObject jsonItem = JSONObject.parseObject(jsonitem);
    	for(List<Object> ls: list){
    		i++;
    		if(i<start) 
    			continue;
    		if(i>end) 
    			break;
    		//System.out.println("第"+ i +"行");
    		try{
	    		for(int j=0;j<jsonIndex.size();j++){
	    			try{
	    			JSONObject jo = (JSONObject)jsonIndex.get(j);
	    			int index_col = jo.getIntValue("index");//获取指标列数
	    			String index =ls.get(index_col)==null?null:(String)ls.get(index_col);//获取指标名称
	    			if(index==null){//没有指标 则不添加
	    				
	    			}else{//添加指标
	    				index = index.trim();
	    				Integer score_col = jo.getString("score")==null?null:jo.getInteger("score");//获取指标 分值列数
	    				Integer weight_col = jo.getString("weight")==null?null:jo.getInteger("weight");//获取指标 weight列数
	    				String score = "0";
	    				String weight = "0";
	    				if(score_col!=null){
	    					score = ls.get(score_col)==null||ls.get(score_col).equals("")?"0":(String)ls.get(score_col);//获取指标 分值
	    				}
	    				if(weight_col!=null){
	    					weight = ls.get(weight_col)==null||ls.get(weight_col).equals("")?"0":(String)ls.get(weight_col);//获取指标 权重
	    				}
	    				//插入操作
	    				if(j==0){//若果是第一列  则pid=0 为顶级指标
	    					ScoreIndex entity = new ScoreIndex();
	    					if(StringUtils.isEmpty(index)){
	    						throw new DefaultBusinessException("第"+i+"行，第"+(char)('A'+index_col)+"列,指标名称为空");
	    					}
	    					entity.setName(index);
	    					try {
	    						entity.setScore(new BigDecimal(score));
							} catch (Exception e) {
								entity.setScore(new BigDecimal("0"));
								throw new DefaultBusinessException("第"+i+"行，第"+(char)('A'+score_col)+"列,解析数字时发生错误->【"+score+"】");
							}
	    					try {
	    						entity.setWeight(new BigDecimal(weight));
							} catch (Exception e) {
								entity.setWeight(new BigDecimal("0"));
								throw new DefaultBusinessException("第"+i+"行，第"+(char)('A'+weight_col)+"列,解析数字时发生错误->【"+weight+"】");
							}
	    					entity.setPaperId(paperId);
	    					entity.setParentId(0);
	    					entity.setSort(i);
	    					this.iScoreIndexService.saveNotNull(entity);
	    					map.put(j, entity.getId());//记录当前列数 最后一次更新的 指标ID 供下级取Pid
	    				}else{
	    					int pid = map.get(j-1);
	    					ScoreIndex entity = new ScoreIndex();
	    					entity.setName(index);
	    					try {
	    						entity.setScore(new BigDecimal(score));
							} catch (Exception e) {
								entity.setScore(new BigDecimal("0"));
								throw new DefaultBusinessException("第"+i+"行，第"+(char)('A'+score_col)+"列,解析数字时发生错误->【"+score+"】");
							}
	    					if(StringUtils.isEmpty(index)){
	    						throw new DefaultBusinessException("第"+i+"行，第"+(char)('A'+index_col)+"列,指标名称为空");
	    					}
	    					try {
	    						entity.setWeight(new BigDecimal(weight));
							} catch (Exception e) {
								entity.setWeight(new BigDecimal("0"));
								throw new DefaultBusinessException("第"+i+"行，第"+(char)('A'+score_col)+"列,解析数字时发生错误->【"+score+"】");
							}
	    					entity.setPaperId(paperId);
	    					entity.setParentId(pid);
	    					entity.setSort(i);
	    					this.iScoreIndexService.saveNotNull(entity);
	    					map.put(j, entity.getId());//记录当前列数 最后一次更新的 指标ID 供下级取Pid
	    				}
	    			}
	    			}catch(Exception e){
	        			e.printStackTrace();
	        			Map m = new HashMap();
	        			m.put("index", i);
	        			m.put("msg", e.getMessage());
	        			errorList.add(m);
	        		}
	    		}
	    		int pid = map.get(jsonIndex.size()-1);
	    		String title =ls.get(jsonItem.getIntValue("title"))==null?null:(String)ls.get(jsonItem.getIntValue("title"));
	    		String score =jsonItem.getString("score")==null?"0":ls.get(jsonItem.getIntValue("score"))==null?"0":(String)ls.get(jsonItem.getIntValue("score"));
	    		String weight =jsonItem.getString("weight")==null?"0":ls.get(jsonItem.getIntValue("weight"))==null?"0":(String)ls.get(jsonItem.getIntValue("weight"));
	    		String desc =jsonItem.getString("desc")==null?"":ls.get(jsonItem.getIntValue("desc"))==null?null:(String)ls.get(jsonItem.getIntValue("desc"));
	    		String org =jsonItem.getString("org")==null?"":ls.get(jsonItem.getIntValue("org"))==null?null:(String)ls.get(jsonItem.getIntValue("org"));
	    		String type =jsonItem.getString("type")==null?null:ls.get(jsonItem.getIntValue("type"))==null?null:(String)ls.get(jsonItem.getIntValue("type"));
	    		if(StringUtils.isEmpty(org)){
	    			org = orgName;
	    		}else{
	    			orgName = org;
	    		}
	    		if(title!=null){
	    			title = title.trim();
	    			if(title.endsWith(";")||title.endsWith(".")||title.endsWith("；")||title.endsWith("。")){
	    				title = title.substring(0, title.length()-1);
	    			}
	    		}
	    		//如果有mapping 
	    		org = deptMapping.getString(org)==null?org:deptMapping.getString(org);
	    		ScoreItem entity = new ScoreItem();
	    		entity.setIndexId(pid);
	    		entity.setTitle(title);
	    		if(StringUtils.isEmpty(title)){
	    			throw new DefaultBusinessException("第"+i+"行，第"+(char)('A'+jsonItem.getIntValue("title"))+"列,题目名称为空");
	    		}
	    		entity.setType(0);//填空
	    		if(title.contains("是否")){
	    			entity.setType(1);//单选
	    		}
	    		if(title.contains("列举")||title.contains("举例")){
	    			entity.setType(3);//多项填空
	    		}
	    		if(type!=null){
	    			if(type.contains("是否")||type.contains("单选")){
	    				entity.setType(1);//单选
	    			}
	    			if(type.contains("列举")||type.contains("举例")||type.contains("多项填空")){
	    				entity.setType(3);//多项填空
	    			}
	    			if(type.contains("多选")){
	    				entity.setType(2);//多选
	    			}
	    			if(type.contains("数字")){
	    				entity.setType(4);//数字
	    			}
	    			if(type.contains("专家评分")||type.contains("专家打分")||type.contains("专家评价打分")){
	    				entity.setScoreType("3");
	    			}
	    			if(type.contains("线性")){
	    				entity.setScoreType("2");
	    			}
	    		}
	    		if(desc!=null){
	    			if(desc.contains("专家评分")||desc.contains("专家打分")||type.contains("专家评价打分")){
	    				entity.setScoreType("3");
	    			}
	    			if(desc.contains("线性")){
	    				entity.setScoreType("2");
	    			}
	    			if(desc.contains("统计")){
	    				entity.setScoreType("1");
	    			}
	    		}
	    		entity.setOptionLogic(desc);
	    		try {
					entity.setScore(new BigDecimal(score));
				} catch (Exception e) {
					entity.setScore(new BigDecimal("0"));
					throw new DefaultBusinessException("第"+i+"行，第"+(char)('A'+jsonItem.getIntValue("score"))+"列,解析数字时发生错误->【"+score+"】");
				}
	    		try {
					entity.setWeight(new BigDecimal(weight));
				} catch (Exception e) {
					entity.setWeight(new BigDecimal("0"));
					throw new DefaultBusinessException("第"+i+"行，第"+(char)('A'+jsonItem.getIntValue("score"))+"列,解析数字时发生错误->【"+score+"】");
				}
	    		entity.setSort(i);
	    		entity.setShowLevel(showLevel);
	    		entity.setResponsibleDepartment(org);
	    		this.iScoreItemService.saveNotNull(entity);
	    		if(title.contains("是否")||(type!=null&&type.contains("是否"))){
	    			int itemId = entity.getId();
	    			ScoreItemOption option = new ScoreItemOption();
	    			option.setItemId(itemId);
	    			option.setOptionTitle("是");
	    			option.setOptionRate(new BigDecimal(score));
	    			iScoreItemOptionService.saveNotNull(option);
	    			ScoreItemOption option1 = new ScoreItemOption();
	    			option1.setItemId(itemId);
	    			option1.setOptionTitle("否");
	    			option1.setOptionRate(BigDecimal.valueOf(0l));
	    			iScoreItemOptionService.saveNotNull(option1);
	    		}
    		}catch(Exception e){
    			e.printStackTrace();
    			Map m = new HashMap();
    			m.put("index", i);
    			m.put("msg", e.getMessage());
    			errorList.add(m);
    		}
    	}
		
		return errorList;
	}

}
