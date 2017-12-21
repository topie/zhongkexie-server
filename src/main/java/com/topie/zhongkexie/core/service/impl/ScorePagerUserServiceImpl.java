package com.topie.zhongkexie.core.service.impl;


import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.topie.zhongkexie.common.baseservice.impl.BaseService;
import com.topie.zhongkexie.core.service.IScorePagerUserService;
import com.topie.zhongkexie.database.core.dao.ScorePaperUserMapper;
import com.topie.zhongkexie.database.core.model.ScorePaperUser;
import com.topie.zhongkexie.security.utils.SecurityUtil;

@Service
public class ScorePagerUserServiceImpl  extends BaseService<ScorePaperUser> implements IScorePagerUserService
{
	
	@Inject
	ScorePaperUserMapper scorePagerUserMapper;

	@Override
	public void check(int id, short result) {
		// TODO Auto-generated method stub
		ScorePaperUser scorePagerUser = scorePagerUserMapper.selectByPagerId(id);
		if(scorePagerUser!=null)
		{
			scorePagerUser.setStatus(result);
			getMapper().updateByPrimaryKey(scorePagerUser);
		}
		else
		{
			scorePagerUser = new ScorePaperUser();
			scorePagerUser.setUserId(SecurityUtil.getCurrentUserId());
			scorePagerUser.setPaperId(id);
            scorePagerUser.setStatus(result);	
            getMapper().insert(scorePagerUser);
		}
	}
	
}
