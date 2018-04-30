package com.topie.zhongkexie.expert.service.impl;

import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.topie.zhongkexie.common.baseservice.impl.BaseService;
import com.topie.zhongkexie.database.expert.model.ExpertDeptUser;
import com.topie.zhongkexie.expert.service.IExpertDeptUserService;

@Service
public class ExpertDeptUserServiceImpl extends BaseService<ExpertDeptUser> implements
		IExpertDeptUserService {

	@Override
	public void deleteByIndexCollExpertId(Integer id) {
		if(id!=null){
			Example ex = new Example(ExpertDeptUser.class);
			ex.createCriteria().andEqualTo("indexCollExpertId",id);
			mapper.deleteByExample(ex);
		}
	}

	
}
