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
import com.topie.zhongkexie.core.service.IScorePaperImportConfigService;
import com.topie.zhongkexie.database.core.model.ScorePaperImportConf;

@Controller
@RequestMapping("/api/core/importConf")
public class ScorePaperImportConfController {

    @Autowired
    private IScorePaperImportConfigService iScorePaperImportConfigService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(ScorePaperImportConf conf,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        PageInfo<ScorePaperImportConf> pageInfo = iScorePaperImportConfigService.selectByFilterAndPage(conf, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }
    /**
     *  获取 下拉框
     * @param conf
     * @return
     */
    @RequestMapping(value = "/getOptions", method = RequestMethod.GET)
    @ResponseBody
    public Object getOptions(ScorePaperImportConf conf) {
        List<ScorePaperImportConf> page = iScorePaperImportConfigService.selectByFilter(conf);
        List<Map> list = new ArrayList<Map>();
        for(ScorePaperImportConf c:page){
        	Map map = new HashMap();
        	map.put("value", c.getId());
        	map.put("text", c.getName());
        	list.add(map);
        }
        return list;
    }
    @RequestMapping(value="/import", method = RequestMethod.POST)
    @ResponseBody
    public Result imports(ScorePaperImportConf conf){
    	List<Map> list = iScorePaperImportConfigService.insertImports(conf,false);
    	return ResponseUtil.success(list);
    }
    
   //@RequestMapping(value="/importTest", method = RequestMethod.POST)
    //@ResponseBody
//    public Result importTest(ScorePaperImportConf conf){
//    	List<Map> list = iScorePaperImportConfigService.insertImports(conf,true);
//    	return ResponseUtil.success(list);
//    }
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public Result insert(ScorePaperImportConf conf) {
        int result = iScorePaperImportConfigService.saveNotNull(conf);
        return result > 0 ? ResponseUtil.success() : ResponseUtil.error();
    }
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(ScorePaperImportConf conf) {
        int result = iScorePaperImportConfigService.updateNotNull(conf);
        return result > 0 ? ResponseUtil.success() : ResponseUtil.error();
    }
    @RequestMapping(value = "/load/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Result load(@PathVariable(value = "id") Integer id) {
        ScorePaperImportConf conf = iScorePaperImportConfigService.selectByKey(id);
        return ResponseUtil.success(conf);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public Result delete(@RequestParam(value = "id") Integer id) {
        iScorePaperImportConfigService.delete(id);
        return ResponseUtil.success();
    }


}
