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
import com.topie.zhongkexie.common.utils.TreeNode;
import com.topie.zhongkexie.core.service.IDictItemService;
import com.topie.zhongkexie.core.service.IDictService;
import com.topie.zhongkexie.database.core.model.Dict;
import com.topie.zhongkexie.database.core.model.DictItem;

@Controller
@RequestMapping("/api/core/dict")
public class DictController {

    @Autowired
    private IDictService iDictService;
    @Autowired
    private IDictItemService iDictItemService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(Dict dict,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        PageInfo<Dict> pageInfo = iDictService.selectByFilterAndPage(dict, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }
    @RequestMapping(value = "/treeNodes", method = RequestMethod.POST)
    @ResponseBody
    public List list(Dict dict) {
        List<Dict> list = iDictService.selectByFilter(dict);
        List<TreeNode> tree = new ArrayList<TreeNode>();
        for(Dict d:list){
        	TreeNode node= new TreeNode();
        	node.setId(d.getDictId());
        	node.setpId(0);
        	node.setName(d.getDictName());
        	tree.add(node);
        }
        return tree;
    }
    
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public Result insert(Dict dict) {
        int result = iDictService.saveNotNull(dict);
        return result > 0 ? ResponseUtil.success() : ResponseUtil.error();
    }
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(Dict dict) {
        int result = iDictService.updateAll(dict);
        return result > 0 ? ResponseUtil.success() : ResponseUtil.error();
    }

    @RequestMapping(value = "/load/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Result load(@PathVariable(value = "id") Integer id) {
        Dict dict = iDictService.selectByKey(id);
        return ResponseUtil.success(dict);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public Result delete(@RequestParam(value = "id") Integer id) {
        iDictService.delete(id);
        return ResponseUtil.success();
    }
    
    @RequestMapping(value = "/getItems", method = RequestMethod.GET)
    @ResponseBody
    public Object getItems(@RequestParam(value = "code") String code) {
        List<DictItem> list = iDictService.selectItemsByDictCode(code);
        List<Map> items = new ArrayList<Map>();
        for(DictItem item:list){
        	Map m = new HashMap();
        	m.put("text", item.getItemName());
        	m.put("value", item.getItemCode());
        	items.add(m);
        }
        return items;
    }
    @RequestMapping(value = "/getItemName", method = RequestMethod.GET)
    @ResponseBody
    public Object getItemName(@RequestParam(value = "code") String code) {
    	DictItem item = new DictItem();
    	item.setItemCode(code);
        List<DictItem> list = iDictItemService.selectByFilter(item);
       if(list.size()>0)
    	   return list.get(0).getItemName();
        return "";
    }


}
