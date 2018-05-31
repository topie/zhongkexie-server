package com.topie.zhongkexie.department.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.baseservice.IService;
import com.topie.zhongkexie.database.department.model.Department;

public interface IDepartmentService extends IService<Department> {

	PageInfo<Department> selectByFilterAndPage(Department department, int pageNum, int pageSize);
	
	List<Department> selectByFilter(Department department);
	
	int saveNotNull(Department department, String password);

	Object getPaperJson(Integer paperId);

}
