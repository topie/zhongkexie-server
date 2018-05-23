package com.topie.zhongkexie.expert.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import tk.mybatis.mapper.util.StringUtil;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.baseservice.impl.BaseService;
import com.topie.zhongkexie.core.service.IDeptService;
import com.topie.zhongkexie.core.service.IScoreIndexCollectionService;
import com.topie.zhongkexie.database.core.model.Dept;
import com.topie.zhongkexie.database.core.model.ScoreIndexCollection;
import com.topie.zhongkexie.database.core.model.User;
import com.topie.zhongkexie.database.expert.model.ExpertDeptUser;
import com.topie.zhongkexie.database.expert.model.ExpertInfo;
import com.topie.zhongkexie.database.expert.model.IndexCollExpert;
import com.topie.zhongkexie.expert.service.IExpertDeptUserService;
import com.topie.zhongkexie.expert.service.IExpertInfoService;
import com.topie.zhongkexie.expert.service.IIndexCollExpertService;
import com.topie.zhongkexie.expert.service.IPaperExpertService;
import com.topie.zhongkexie.security.service.UserService;

@Service
public class IndexCollExpertServiceImpl extends BaseService<IndexCollExpert> implements
		IIndexCollExpertService {
	@Autowired
	private IScoreIndexCollectionService iScoreIndexCollectionService;
	@Autowired
	private IPaperExpertService iPaperExpertService;
	@Autowired
	private IExpertInfoService iExpertInfoService;
	@Autowired
	private IDeptService iDeptService;
	@Autowired
	private UserService userService;
	@Autowired
	private IExpertDeptUserService iExpertDeptUserService;

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
	
	@Override
	public int updateNotNull(IndexCollExpert entity,boolean avg) {
		int g = super.updateNotNull(entity);
		if(StringUtil.isNotEmpty(entity.getExpertIds())){
			iExpertDeptUserService.deleteByIndexCollExpertId(entity.getId());
			insertEx(entity,avg);
		}
		return g;
	}
	 
	@Override
	public int saveNotNull(IndexCollExpert entity,boolean avg) {
		int j = mapper.insertSelective(entity);
		if(StringUtil.isNotEmpty(entity.getExpertIds())){
			insertEx(entity,avg);
		}
		return j;
	}
	/**
	 * 
	 * @param entity
	 * @param avg 是否平均分配
	 */
	private void insertEx(IndexCollExpert entity,boolean avg) {
		String exIds = entity.getExpertIds();
		Example example = new Example(ExpertInfo.class);
		example.createCriteria().andIn("id", Arrays.asList(exIds.split(",")));
		List<ExpertInfo> experts = iExpertInfoService.selectByExample(example);
		String deptIds = iPaperExpertService.selectByKey(entity.getPaperExpertId()).getDeptIds();
		Example example1 = new Example(Dept.class);
		example1.createCriteria().andIn("id", Arrays.asList(deptIds.split(",")));
		List<Dept> depts = iDeptService.selectByExample(example1);
		if(avg){//平均分配
			for(int i=0;i<depts.size();i++){
				int index = i%experts.size();
				ExpertInfo info = experts.get(index);
				Dept d = depts.get(i);
				User u = userService.findUserByLoginName(d.getCode().trim()+"002");
				if(u==null){
						System.err.println("缺少用户:"+d.getCode()+"-"+d.getName());
					continue;
				}
				ExpertDeptUser du = new ExpertDeptUser();
				du.setDeptUserId(u.getId());
				du.setExpertId(info.getId());
				du.setExpertUserId(info.getUserId());
				du.setPaperId(entity.getPaperId());
				du.setIndexCollId(entity.getIndexCollectionId());
				du.setIndexCollExpertId(entity.getId());
				iExpertDeptUserService.saveNotNull(du);
			}
		}else{//
			for(int k=0;k<experts.size();k++){
				ExpertInfo info = experts.get(k);
				for(int i=0;i<depts.size();i++){
					Dept d = depts.get(i);
					User u = userService.findUserByLoginName(d.getCode().trim()+"002");
					if(u==null){
							System.err.println("缺少用户:"+d.getCode()+"-"+d.getName());
						continue;
					}
					ExpertDeptUser du = new ExpertDeptUser();
					du.setDeptUserId(u.getId());
					du.setExpertId(info.getId());
					du.setExpertUserId(info.getUserId());
					du.setPaperId(entity.getPaperId());
					du.setIndexCollId(entity.getIndexCollectionId());
					du.setIndexCollExpertId(entity.getId());
					iExpertDeptUserService.saveNotNull(du);
				}
			}
		}
		
	}


}
