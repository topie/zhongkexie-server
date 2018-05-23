package com.topie.zhongkexie.expert.api;

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
import com.topie.zhongkexie.database.expert.model.IndexCollExpert;
import com.topie.zhongkexie.expert.service.IIndexCollExpertService;

@Controller
@RequestMapping("/api/expert/indexColl")
public class IndexCollExpertController {
	
	 @Autowired
	    private IIndexCollExpertService iIndexCollExpertService;

	    @RequestMapping(value = "/list", method = RequestMethod.GET)
	    @ResponseBody
	    public Result list(IndexCollExpert indexCollExpert,
	            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
	            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
	        PageInfo<IndexCollExpert> pageInfo = iIndexCollExpertService.selectByFilterAndPage(indexCollExpert, pageNum, pageSize);
	        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
	    }

	    @RequestMapping(value = "/insert", method = RequestMethod.POST)
	    @ResponseBody
	    public Result insert(IndexCollExpert indexCollExpert) {
	        int result = iIndexCollExpertService.saveNotNull(indexCollExpert,false);
	        return result > 0 ? ResponseUtil.success() : ResponseUtil.error();
	    }

	    @RequestMapping(value = "/update", method = RequestMethod.POST)
	    @ResponseBody
	    public Result update(IndexCollExpert indexCollExpert) {
	        iIndexCollExpertService.updateNotNull(indexCollExpert,false);
	        return ResponseUtil.success();
	    }

	    @RequestMapping(value = "/load/{id}", method = RequestMethod.GET)
	    @ResponseBody
	    public Result load(@PathVariable(value = "id") Integer id) {
	        IndexCollExpert indexCollExpert = iIndexCollExpertService.selectByKey(id);
	        return ResponseUtil.success(indexCollExpert);
	    }

	    @RequestMapping(value = "/delete", method = RequestMethod.GET)
	    @ResponseBody
	    public Result delete(@RequestParam(value = "id") Integer id) {
	        iIndexCollExpertService.delete(id);
	        return ResponseUtil.success();
	    }
}
