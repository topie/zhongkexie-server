package com.topie.zhongkexie.core.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.topie.zhongkexie.core.dto.DateEqualDto;

@Controller
@RequestMapping("/api/core/util")
public class UtilController {
	
	@RequestMapping(value="dateEqual",method=RequestMethod.GET)
	@ResponseBody
	public boolean dateEqual(DateEqualDto dated){
		if(dated.getStart()==null||dated.getEnd()==null)return true;
		boolean f = dated.getStart().before(dated.getEnd());
		return f;
	}
	
}
