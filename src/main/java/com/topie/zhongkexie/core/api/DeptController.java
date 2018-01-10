package com.topie.zhongkexie.core.api;

import java.util.ArrayList;
import java.util.List;

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
import com.topie.zhongkexie.common.utils.TreeNode;
import com.topie.zhongkexie.core.service.IDeptService;
import com.topie.zhongkexie.database.core.model.Dept;

@Controller
@RequestMapping("/api/core/dept")
public class DeptController {

    @Autowired
    private IDeptService iDeptService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(Dept dept,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        PageInfo<Dept> pageInfo = iDeptService.selectByFilterAndPage(dept, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }
    @RequestMapping(value = "/tree", method = RequestMethod.POST)
    @ResponseBody
    public List list(Dept dept) {
        List<Dept> list = iDeptService.selectByFilter(dept);
        List<TreeNode> tree = new ArrayList<TreeNode>();
        for(Dept d:list){
        	TreeNode node= new TreeNode();
        	node.setId(d.getId());
        	node.setpId(d.getPid());
        	node.setName(d.getName());
        	node.setS(d.getType());
        	tree.add(node);
        }
        return tree;
    }
    
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public Result insert(Dept dept) {
        int result = iDeptService.saveNotNull(dept);
        return result > 0 ? ResponseUtil.success() : ResponseUtil.error();
    }

    @RequestMapping(value = "/load/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Result load(@PathVariable(value = "id") Integer id) {
        Dept dept = iDeptService.selectByKey(id);
        return ResponseUtil.success(dept);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public Result delete(@RequestParam(value = "id") Integer id) {
        iDeptService.delete(id);
        return ResponseUtil.success();
    }


}
