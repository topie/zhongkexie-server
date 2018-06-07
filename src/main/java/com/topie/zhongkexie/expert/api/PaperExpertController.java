package com.topie.zhongkexie.expert.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import tk.mybatis.mapper.entity.Example;

import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.utils.PageConvertUtil;
import com.topie.zhongkexie.common.utils.ResponseUtil;
import com.topie.zhongkexie.common.utils.Result;
import com.topie.zhongkexie.database.expert.model.ExpertDeptUser;
import com.topie.zhongkexie.database.expert.model.PaperExpert;
import com.topie.zhongkexie.expert.service.IExpertDeptUserService;
import com.topie.zhongkexie.expert.service.IExpertItemScoreService;
import com.topie.zhongkexie.expert.service.IPaperExpertService;
import com.topie.zhongkexie.security.utils.SecurityUtil;

@Controller
@RequestMapping("/api/expert/paper")
public class PaperExpertController {
	
	 @Autowired
	 private IPaperExpertService iPaperExpertService;
	 @Autowired
	 private IExpertDeptUserService iExpertDeptUserService;
	 @Autowired
	 private IExpertItemScoreService iExpertItemScoreService;

	    @RequestMapping(value = "/list", method = RequestMethod.GET)
	    @ResponseBody
	    public Result list(PaperExpert paperExpert,
	            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
	            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
	        PageInfo<PaperExpert> pageInfo = iPaperExpertService.selectByFilterAndPage(paperExpert, pageNum, pageSize);
	        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
	    }

	    @RequestMapping(value = "/insert", method = RequestMethod.POST)
	    @ResponseBody
	    public Result insert(PaperExpert paperExpert) {
	        int result = iPaperExpertService.saveNotNull(paperExpert);
	        return result > 0 ? ResponseUtil.success() : ResponseUtil.error();
	    }
	    @RequestMapping(value = "/init", method = RequestMethod.POST)
	    @ResponseBody
	    public Result init(PaperExpert paperExpert) {
	    	Integer paperId = paperExpert.getPaperId();
	    	String type = paperExpert.getType();
	    	int result = this.iPaperExpertService.init(paperId,type);
	    	return result > 0 ? ResponseUtil.success() : ResponseUtil.error();
	    }

	    @RequestMapping(value = "/update", method = RequestMethod.POST)
	    @ResponseBody
	    public Result update(PaperExpert paperExpert) {
	        iPaperExpertService.updateNotNull(paperExpert);
	        return ResponseUtil.success();
	    }

	    @RequestMapping(value = "/load/{id}", method = RequestMethod.GET)
	    @ResponseBody
	    public Result load(@PathVariable(value = "id") Integer id) {
	        PaperExpert paperExpert = iPaperExpertService.selectByKey(id);
	        return ResponseUtil.success(paperExpert);
	    }

	    @RequestMapping(value = "/delete", method = RequestMethod.GET)
	    @ResponseBody
	    public Result delete(@RequestParam(value = "id") Integer id) {
	        iPaperExpertService.delete(id);
	        return ResponseUtil.success();
	    }
	    /**
	     * 专家标记为完成评分
	     * @param id
	     * @return
	     */
	    @RequestMapping(value = "/zjFinish", method = RequestMethod.GET)
	    @ResponseBody
	    public Result zjFinish(Integer paperId,Integer userId,String status) {
	    	Example ex = new Example(ExpertDeptUser.class);
	    	Integer cUserId = SecurityUtil.getCurrentUserId();
	    	ex.createCriteria().andEqualTo("deptUserId",userId)
	    		.andEqualTo("expertUserId",cUserId)
	    		.andEqualTo("paperId",paperId);
	    	ExpertDeptUser du = new ExpertDeptUser();
	    	du.setStatus(status);
	    	int i = iExpertDeptUserService.updateByExampleSelective(du, ex);
	        return ResponseUtil.success(i);
	    }
	    /**
	     * 获取一个题的专家打分情况
	     * @param userId
	     * @param itemId
	     * @return
	     */
	    @RequestMapping(value = "/getScoreInfo", method = RequestMethod.GET)
	    @ResponseBody
	    public Result getScoreInfo(Integer userId,String itemId) {
	    	List<Map> list = iExpertItemScoreService.selectScoreInfo(userId,itemId);
	    	
	        return ResponseUtil.success(list);
	    }
}
