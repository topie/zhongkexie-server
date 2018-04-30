package com.topie.zhongkexie.expert.service;

import com.topie.zhongkexie.common.baseservice.IService;
import com.topie.zhongkexie.database.expert.model.ExpertDeptUser;

public interface IExpertDeptUserService extends IService<ExpertDeptUser> {

	void deleteByIndexCollExpertId(Integer id);


}
