package com.topie.zhongkexie.system.api.sys;

import com.topie.zhongkexie.common.tools.cache.RedisCache;
import com.topie.zhongkexie.common.utils.PageConvertUtil;
import com.topie.zhongkexie.common.utils.ResponseUtil;
import com.topie.zhongkexie.common.utils.Result;
import com.topie.zhongkexie.database.core.model.Function;
import com.topie.zhongkexie.security.service.FunctionService;
import com.topie.zhongkexie.security.service.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by cgj on 2016/4/9.
 */
@Controller
@RequestMapping("/api/sys/function")
public class FunctionController {

    @Autowired
    UserService userService;

    @Autowired
    FunctionService functionService;

    @Autowired
    RedisCache redisCache;

    @RequestMapping(value = "/pageList", method = RequestMethod.GET)
    @ResponseBody
    public Result functions(Function function,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        PageInfo<Function> pageInfo = functionService.findFunctionList(pageNum, pageSize, function);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public Result insertFunction(Function function) {
        functionService.insertFunction(function);
        return ResponseUtil.success();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Result updateFunction(Function function) {
        if (function.getParentId() == null) function.setParentId(0);
        functionService.updateFunction(function);
        return ResponseUtil.success();
    }

    @RequestMapping(value = "/load/{functionId}", method = RequestMethod.GET)
    @ResponseBody
    public Result loadFunction(@PathVariable(value = "functionId") Integer functionId) {
        Function function = functionService.findFunctionById(functionId);
        return ResponseUtil.success(function);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(@RequestParam(value = "functionId") Integer functionId) {
        functionService.deleteFunctionById(functionId);
        return ResponseUtil.success();
    }

    @RequestMapping(value = "/treeNodes", method = RequestMethod.POST)
    @ResponseBody
    public Object treeNodes(Function function) {
        return functionService.getFunctionTreeNodes(function);
    }

}
