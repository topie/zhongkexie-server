package com.topie.zhongkexie.publicity.api;

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
import com.topie.zhongkexie.database.publicity.model.Dissent;
import com.topie.zhongkexie.publicity.service.IDissentService;
import com.topie.zhongkexie.security.utils.SecurityUtil;

@Controller
@RequestMapping("/api/dissent/info")
public class DissentController {
	
	 @Autowired
	    private IDissentService iDessientService;
	 	/**
	 	 * 所有提交的列表
	 	 * @param dissent
	 	 * @param pageNum
	 	 * @param pageSize
	 	 * @return
	 	 */
	    @RequestMapping(value = "/list", method = RequestMethod.GET)
	    @ResponseBody
	    public Result list(Dissent dissent,
	            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
	            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
	        PageInfo<Dissent> pageInfo = iDessientService.selectByFilterAndPage(dissent, pageNum, pageSize);
	        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
	    }
	    /**
	     * 当前用户的提交
	     * @param dissent
	     * @param pageNum
	     * @param pageSize
	     * @return
	     */
	    @RequestMapping(value = "/cuserList", method = RequestMethod.GET)
	    @ResponseBody
	    public Result cuserList(Dissent dissent,
	            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
	            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
	    	Integer userId = SecurityUtil.getCurrentUserId();
	    	dissent.setInputId(userId);
	        PageInfo<Dissent> pageInfo = iDessientService.selectByFilterAndPage(dissent, pageNum, pageSize);
	        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
	    }

	    @RequestMapping(value = "/insert", method = RequestMethod.POST)
	    @ResponseBody
	    public Result insert(Dissent dissent) {
	        int result = iDessientService.saveNotNull(dissent);
	        return result > 0 ? ResponseUtil.success() : ResponseUtil.error();
	    }

	    @RequestMapping(value = "/update", method = RequestMethod.POST)
	    @ResponseBody
	    public Result update(Dissent dissent) {
	        iDessientService.updateNotNull(dissent);
	        return ResponseUtil.success();
	    }

	    @RequestMapping(value = "/load/{id}", method = RequestMethod.GET)
	    @ResponseBody
	    public Result load(@PathVariable(value = "id") Integer id) {
	        Dissent dissent = iDessientService.selectByKey(id);
	        return ResponseUtil.success(dissent);
	    }

	    @RequestMapping(value = "/delete", method = RequestMethod.GET)
	    @ResponseBody
	    public Result delete(@RequestParam(value = "id") Integer id) {
	        iDessientService.delete(id);
	        return ResponseUtil.success();
	    }
}
