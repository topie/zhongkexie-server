package com.topie.zhongkexie.core.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.baseservice.impl.BaseService;
import com.topie.zhongkexie.core.service.IDeptService;
import com.topie.zhongkexie.database.core.model.Dept;

@Service
public class DeptServiceImpl extends BaseService<Dept> implements IDeptService {

	@Override
	public List<Dept> selectByFilter(Dept dept) {
		Example ex = new Example(Dept.class);
		Criteria c = ex.createCriteria();
		if(StringUtils.isNotEmpty(dept.getName()))c.andLike("name", "%"+dept.getName()+"%");
		if(StringUtils.isNotEmpty(dept.getField()))c.andEqualTo("field", dept.getField());
		if(StringUtils.isNotEmpty(dept.getDuty()))c.andEqualTo("duty", dept.getDuty());
		if(StringUtils.isNotEmpty(dept.getType()))c.andEqualTo("type", dept.getType());
		if(dept.getPid()!=null && dept.getPid()!=-1) c.andEqualTo("pid",dept.getPid());
		ex.setOrderByClause("seq asc");
		return getMapper().selectByExample(ex);
	}

	@Override
	public PageInfo<Dept> selectByFilterAndPage(Dept dept, int pageNum,
			int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Dept> list = selectByFilter(dept);
		return new PageInfo<Dept>(list);
	}

}
