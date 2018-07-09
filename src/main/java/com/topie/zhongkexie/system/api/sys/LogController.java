
package com.topie.zhongkexie.system.api.sys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.utils.PageConvertUtil;
import com.topie.zhongkexie.common.utils.ResponseUtil;
import com.topie.zhongkexie.database.core.model.SysLog;
import com.topie.zhongkexie.system.service.LogService;

@Controller
@RequestMapping("/api/sys/log")
public class LogController {
	
	@Autowired
	private LogService logService;
	
	@RequestMapping("list")
	@ResponseBody
	public Object list(SysLog sysLog,
		@RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
        @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
    PageInfo<SysLog> pageInfo = logService.findByPage(pageNum, pageSize, sysLog);
    return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
	}

}
