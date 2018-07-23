package com.topie.zhongkexie.core.api;

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
import com.topie.zhongkexie.core.service.IPaperExpertConfService;
import com.topie.zhongkexie.database.core.model.PaperExpertConf;

@Controller
@RequestMapping("/api/paperExpertConf/info")
public class PaperExpertConfController {
	
	 @Autowired
	    private IPaperExpertConfService iPaperExpertConfService;

	    @RequestMapping(value = "/list", method = RequestMethod.GET)
	    @ResponseBody
	    public Result list(PaperExpertConf paperExpertConf,
	            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
	            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
	        PageInfo<PaperExpertConf> pageInfo = iPaperExpertConfService.selectByFilterAndPage(paperExpertConf, pageNum, pageSize);
	        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
	    }
	    @RequestMapping(value = "/getPaperOptions", method = RequestMethod.GET)
	    @ResponseBody
	    public Object getPaperOptions(PaperExpertConf paperExpertConf) {
	    	paperExpertConf.setStatus(1);
	        List<PaperExpertConf> pageInfo = iPaperExpertConfService.selectByFilter(paperExpertConf);
	        List<Map> list = new ArrayList<Map>();
	        for(PaperExpertConf c:pageInfo){
	        	Map map = new HashMap();
	        	map.put("text", c.getTitle());
	        	map.put("value",c.getPaperId());
	        	list.add(map);
	        }
	        return list;
	    }


	    @RequestMapping(value = "/update", method = RequestMethod.POST)
	    @ResponseBody
	    public Result update(PaperExpertConf paperExpertConf) {
	        PaperExpertConf p = iPaperExpertConfService.selectByKey(paperExpertConf.getId());
	        if(p!=null){
	        	iPaperExpertConfService.updateNotNull(paperExpertConf);
	        }else{
	        	iPaperExpertConfService.save(paperExpertConf);
	        }
	        return ResponseUtil.success();
	    }

	    @RequestMapping(value = "/load/{id}", method = RequestMethod.GET)
	    @ResponseBody
	    public Result load(@PathVariable(value = "id") Integer id) {
	        PaperExpertConf paperExpertConf = iPaperExpertConfService.selectByKey(id);
	        return ResponseUtil.success(paperExpertConf);
	    }

}
