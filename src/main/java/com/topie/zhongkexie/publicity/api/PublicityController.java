package com.topie.zhongkexie.publicity.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.topie.zhongkexie.database.publicity.model.Publicity;
import com.topie.zhongkexie.publicity.service.IPublicityService;

@Controller
@RequestMapping("/api/publicity/info")
public class PublicityController {
	
	 @Autowired
	    private IPublicityService iPublicityService;

	    @RequestMapping(value = "/list", method = RequestMethod.GET)
	    @ResponseBody
	    public Result list(Publicity publicity,
	            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
	            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
	        PageInfo<Publicity> pageInfo = iPublicityService.selectByFilterAndPage(publicity, pageNum, pageSize);
	        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
	    }
	    @RequestMapping(value = "/getPaperOptions", method = RequestMethod.GET)
	    @ResponseBody
	    public Object getPaperOptions(Publicity publicity) {
	    	publicity.setPublicity(1);
	        List<Publicity> pageInfo = iPublicityService.selectByFilter(publicity);
	        List<Map> list = new ArrayList<Map>();
	        for(Publicity c:pageInfo){
	        	Map map = new HashMap();
	        	map.put("text", c.getTitle());
	        	map.put("value",c.getPaperId());
	        	list.add(map);
	        }
	        return list;
	    }


	    @RequestMapping(value = "/update", method = RequestMethod.POST)
	    @ResponseBody
	    public Result update(Publicity publicity) {
	        Publicity p = iPublicityService.selectByKey(publicity.getId());
	        if(p!=null){
	        	iPublicityService.updateNotNull(publicity);
	        }else{
	        	iPublicityService.save(publicity);
	        }
	        return ResponseUtil.success();
	    }

	    @RequestMapping(value = "/load/{id}", method = RequestMethod.GET)
	    @ResponseBody
	    public Result load(@PathVariable(value = "id") Integer id) {
	        Publicity publicity = iPublicityService.selectByKey(id);
	        return ResponseUtil.success(publicity);
	    }

}
