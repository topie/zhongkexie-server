package com.topie.zhongkexie.department.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.utils.PageConvertUtil;
import com.topie.zhongkexie.common.utils.ResponseUtil;
import com.topie.zhongkexie.common.utils.Result;
import com.topie.zhongkexie.core.service.IScoreItemService;
import com.topie.zhongkexie.database.department.model.Department;
import com.topie.zhongkexie.department.service.IDepartmentService;

@Controller
@RequestMapping("/api/department/info")
public class DepartmentController {

	@Autowired
    private IDepartmentService iDepartmentService;
	@Autowired
    private IScoreItemService iScoreItemService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(Department department,
    		@RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        PageInfo<Department> pageInfo = iDepartmentService.selectByFilterAndPage(department, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }
    
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public Result insert(Department department,String password) {
        int result = iDepartmentService.saveNotNull(department,password);
        return result > 0 ? ResponseUtil.success() : ResponseUtil.error();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(Department department) {
        iDepartmentService.updateNotNull(department);
        return ResponseUtil.success();
    }
    @RequestMapping("/paperJson")
    @ResponseBody
    public Result getPaperJson(Integer paperId){
    	Object o = iDepartmentService.getPaperJson(paperId);
    	return ResponseUtil.success(o);
    	
    }
    
    @RequestMapping(value = "/load/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Result load(@PathVariable(value = "id") Integer id) {
        Department department = iDepartmentService.selectByKey(id);
        return ResponseUtil.success(department);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public Result delete(@RequestParam(value = "id") Integer id) {
        iDepartmentService.delete(id);
        return ResponseUtil.success();
    }
}
