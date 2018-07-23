package com.topie.zhongkexie.publicity.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.baseservice.impl.BaseService;
import com.topie.zhongkexie.database.publicity.model.Dissent;
import com.topie.zhongkexie.publicity.service.IDissentService;
import com.topie.zhongkexie.security.security.SecurityUser;
import com.topie.zhongkexie.security.utils.SecurityUtil;

@Service
public class DissentServiceImpl extends BaseService<Dissent> implements
		IDissentService {

	@Override
	public PageInfo<Dissent> selectByFilterAndPage(Dissent dissent,
			int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Dissent> list = this.selectByFilter(dissent);
		return new PageInfo<Dissent>(list);
	}

	@Override
	public List<Dissent> selectByFilter(Dissent dissent) {
		Example ex = new Example(Dissent.class);
		Criteria c = ex.createCriteria();
		if(!StringUtils.isEmpty(dissent.getInputOrg())){
			c.andLike("inputOrg", "%"+dissent.getInputOrg()+"%");
		}
		if(!StringUtils.isEmpty(dissent.getDissentOrg())){
			c.andLike("dissentOrg", "%"+dissent.getDissentOrg()+"%");
		}
		if(!StringUtils.isEmpty(dissent.getDissentIndex())){
			c.andLike("dissentIndex", "%"+dissent.getDissentIndex()+"%");
		}
		if(!StringUtils.isEmpty(dissent.getInputUser())){
			c.andLike("inputUser", "%"+dissent.getInputUser()+"%");
		}
		if(null != dissent.getInputId()){ 
			c.andEqualTo("inputId", dissent.getInputId());
		}
		ex.setOrderByClause("id desc");
		List<Dissent> list = getMapper().selectByExample(ex);
		return list;
	}
	
	@Override
	public int saveNotNull(Dissent entity) {
		SecurityUser user = SecurityUtil.getCurrentSecurityUser();
		entity.setCreateTime(new Date());
		entity.setInputId(user.getId());
		entity.setInputOrg(user.getDisplayName());
		entity.setInputUser(user.getLinkMan());
		entity.setEmail(user.getEmail());
		entity.setPhone(user.getContactPhone());
		entity.setReadStatus("0");
		return super.saveNotNull(entity);
	}

	
}
