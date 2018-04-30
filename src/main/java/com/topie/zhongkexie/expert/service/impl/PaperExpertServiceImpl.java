package com.topie.zhongkexie.expert.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.baseservice.impl.BaseService;
import com.topie.zhongkexie.core.service.IDeptService;
import com.topie.zhongkexie.core.service.IDictService;
import com.topie.zhongkexie.database.core.model.Dept;
import com.topie.zhongkexie.database.core.model.DictItem;
import com.topie.zhongkexie.database.expert.model.ExpertDeptUser;
import com.topie.zhongkexie.database.expert.model.IndexCollExpert;
import com.topie.zhongkexie.database.expert.model.PaperExpert;
import com.topie.zhongkexie.expert.service.IExpertDeptUserService;
import com.topie.zhongkexie.expert.service.IIndexCollExpertService;
import com.topie.zhongkexie.expert.service.IPaperExpertService;

@Service
public class PaperExpertServiceImpl extends BaseService<PaperExpert> implements
		IPaperExpertService {
	@Autowired
	private IDictService iDictService;
	@Autowired
	private IDeptService iDeptService;
	@Autowired
	private IIndexCollExpertService iIndexCollExpertService;
	@Autowired
	private IExpertDeptUserService iExpertDeptUserService;
	
	@Value("${xuehui.isType}")
	private String xuehuiISType;//是否按学会分类

	@Override
	public PageInfo<PaperExpert> selectByFilterAndPage(PaperExpert paperExpert,
			int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<PaperExpert> list = selectByFilter(paperExpert);
		return new PageInfo<PaperExpert>(list);
	}

	@Override
	public List<PaperExpert> selectByFilter(PaperExpert paperExpert) {
		Example ex = new Example(PaperExpert.class);
		Criteria c = ex.createCriteria();
		if (!StringUtils.isEmpty(paperExpert.getName())) {
			c.andLike("name", "%" + paperExpert.getName() + "%");
		}
		if (!StringUtils.isEmpty(paperExpert.getType())) {
			c.andEqualTo("type", paperExpert.getType());
		}
		if (paperExpert.getPaperId()!=null) {
			c.andEqualTo("paperId", paperExpert.getPaperId());
		}
		return getMapper().selectByExample(ex);
	}

	@Override
	public int init(Integer paperId,String type) {
		deleteAllConfig(paperId);
		if(type!=null){
			List<DictItem> list = iDictService.selectItemsByDictCode("ZYFL");
			for(DictItem item:list){
				Dept dept = new Dept();
				dept.setType(item.getItemCode());
				List<Dept> depts = iDeptService.selectByFilter(dept);
				String ids = "";
				String names = "";
				for(Dept d:depts){
					ids+=","+d.getId();
					names+=","+d.getName();
				}
				if(ids.length()>0){
					ids = ids.substring(1);
					names = names.substring(1);
				}
				PaperExpert pe = new PaperExpert();
				pe.setDeptIds(ids);
				pe.setDeptNames(names);
				pe.setPaperId(paperId);
				pe.setName(item.getItemName()+"学会组");
				pe.setType(item.getItemCode());
				this.saveNotNull(pe);
				iIndexCollExpertService.init(paperId, pe.getId(), item.getItemCode());
			}
			return list.size();
		}else{
			Dept dept = new Dept();
			List<Dept> depts = iDeptService.selectByFilter(dept);
			String ids = "";
			String names = "";
			for(Dept d:depts){
				ids+=","+d.getId();
				names+=","+d.getName();
			}
			if(ids.length()>0){
				ids = ids.substring(1);
				names = names.substring(1);
			}
			PaperExpert pe = new PaperExpert();
			pe.setDeptIds(ids);
			pe.setDeptNames(names);
			pe.setPaperId(paperId);
			pe.setName("全国学会组");
			this.saveNotNull(pe);
			iIndexCollExpertService.init(paperId, pe.getId(), null);
		}
		return 1;
	}

	private void deleteAllConfig(Integer paperId) {
		Example ex = new Example(PaperExpert.class);
		ex.createCriteria().andEqualTo("paperId", paperId);
		getMapper().deleteByExample(ex);
		
		Example ex1 = new Example(IndexCollExpert.class);
		ex1.createCriteria().andEqualTo("paperId", paperId);
		iIndexCollExpertService.deleteByExample(ex1);

		Example ex2 = new Example(ExpertDeptUser.class);
		ex2.createCriteria().andEqualTo("paperId", paperId);
		iExpertDeptUserService.deleteByExample(ex2);
		
	}

}
