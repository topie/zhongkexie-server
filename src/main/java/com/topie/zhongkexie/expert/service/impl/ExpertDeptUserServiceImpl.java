package com.topie.zhongkexie.expert.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.baseservice.impl.BaseService;
import com.topie.zhongkexie.core.service.IPaperExpertConfService;
import com.topie.zhongkexie.database.core.model.PaperExpertConf;
import com.topie.zhongkexie.database.expert.dao.ExpertDeptUserMapper;
import com.topie.zhongkexie.database.expert.model.ExpertDeptUser;
import com.topie.zhongkexie.expert.service.IExpertDeptUserService;
import com.topie.zhongkexie.security.utils.SecurityUtil;

@Service
public class ExpertDeptUserServiceImpl extends BaseService<ExpertDeptUser> implements
		IExpertDeptUserService {

	@Autowired
	private ExpertDeptUserMapper expertDeptUsermapper;
	@Autowired
	private IPaperExpertConfService iPaperExpertConfService;

	@Override
	public void deleteByIndexCollExpertId(Integer id) {
		if(id!=null){
			Example ex = new Example(ExpertDeptUser.class);
			ex.createCriteria().andEqualTo("indexCollExpertId",id);
			mapper.deleteByExample(ex);
		}
	}

	@Override
	public List<Map> countExpertFinish(Map map) {
		return expertDeptUsermapper.countExpertFinish(map);
	}

	@Override
	public PageInfo<Map> countExpertFinishPage(Map map, int pageNum, int pageSize) {
		setCurrentPaperId(map);
		PageHelper.startPage(pageNum,pageSize);
		List<Map> list = countExpertFinish(map);
		return new PageInfo<Map>(list);
	}

	private void setCurrentPaperId(Map map) {
		PaperExpertConf paperExpertConf = new PaperExpertConf();
		paperExpertConf.setStatus(1);
        List<PaperExpertConf> pageInfo = iPaperExpertConfService.selectByFilter(paperExpertConf);
       	if(pageInfo.size()>0)
       		map.put("paperId", pageInfo.get(0).getPaperId());
       	else
       		map.put("paperId", 0);//表示没有
		
	}

	@Override
	public Map countCurrentExpertFinish() {
		Map map = new HashMap();
		Integer userId = SecurityUtil.getCurrentUserId();
		map.put("expertId", userId);
		setCurrentPaperId( map);
		List<Map> list = countExpertFinish(map);
		if(list.size()>0)return list.get(0);
		map.put("finished", 0);
		map.put("commited", 0);
		return map;
	}

	
}
