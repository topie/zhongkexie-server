package com.topie.zhongkexie.core.api;

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
import com.topie.zhongkexie.core.service.IDictItemService;
import com.topie.zhongkexie.database.core.model.DictItem;

@Controller
@RequestMapping("/api/core/dictItem")
public class DictItemController {

    @Autowired
    private IDictItemService iDictItemService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(DictItem dictItem,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        PageInfo<DictItem> pageInfo = iDictItemService.selectByFilterAndPage(dictItem, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public Result insert(DictItem dictItem) {
        int result = iDictItemService.saveNotNull(dictItem);
        return result > 0 ? ResponseUtil.success() : ResponseUtil.error();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(DictItem dictItem) {
        int result = iDictItemService.updateAll(dictItem);
        return result > 0 ? ResponseUtil.success() : ResponseUtil.error();
    }
    @RequestMapping(value = "/load/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Result load(@PathVariable(value = "id") Integer id) {
        DictItem dictItem = iDictItemService.selectByKey(id);
        return ResponseUtil.success(dictItem);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public Result delete(@RequestParam(value = "id") Integer id) {
        iDictItemService.delete(id);
        return ResponseUtil.success();
    }


}
