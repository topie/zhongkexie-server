package com.topie.zhongkexie.core.service.impl;


import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.baseservice.impl.BaseService;
import com.topie.zhongkexie.common.exception.DefaultBusinessException;
import com.topie.zhongkexie.core.dto.PagerUserDto;
import com.topie.zhongkexie.core.service.IScorePagerUserService;
import com.topie.zhongkexie.core.service.IScorePaperService;
import com.topie.zhongkexie.database.core.dao.ScorePaperUserMapper;
import com.topie.zhongkexie.database.core.model.ScorePaper;
import com.topie.zhongkexie.database.core.model.ScorePaperUser;
import com.topie.zhongkexie.security.security.SecurityUser;
import com.topie.zhongkexie.security.service.UserService;
import com.topie.zhongkexie.security.utils.SecurityUtil;

@Service
public class ScorePagerUserServiceImpl  extends BaseService<ScorePaperUser> implements IScorePagerUserService
{
	
	@Inject
	ScorePaperUserMapper scorePagerUserMapper;
	@Autowired
	IScorePaperService iScorePaperService;
	@Autowired
	UserService userService;

	@Override
	public void check(int id, short result) {
		ScorePaperUser scorePagerUser = new ScorePaperUser();
    	SecurityUser user = SecurityUtil.getCurrentSecurityUser();
    	String CUname = user.getLoginName();
    	String Mname = CUname ;
    	int userId = user.getId();
    	//TODO 审核员 查看 填报员填报得信息 Mname = CUname.substring(0,CUname.lastIndexOf("-001"))+"-002";  
    	if(CUname.endsWith("001")){//如果学会审核员
    		Mname = CUname.substring(0,CUname.lastIndexOf("001"))+"002";
    		userId = userService.findUserByLoginName(Mname).getId();
    	}else {
    		throw new DefaultBusinessException("学会管理员才能审核!");
    	}
    	scorePagerUser.setUserId(userId);
    	scorePagerUser.setPaperId(id);
    	ScorePaperUser su = getMapper().selectOne(scorePagerUser);
		if(su!=null)
		{
			scorePagerUser.setStatus(result);
			getMapper().updateByPrimaryKey(scorePagerUser);
		}
		else
		{
			scorePagerUser = new ScorePaperUser();
			scorePagerUser.setUserId(userId);
			scorePagerUser.setPaperId(id);
            scorePagerUser.setStatus(result);	
            getMapper().insert(scorePagerUser);
		}
	}

	@Override
    public PageInfo<ScorePaper> selectByFilterAndPage(ScorePaper scorePaper, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ScorePaper> list = iScorePaperService.selectByFilter(scorePaper);
        PageInfo<ScorePaper> page = new PageInfo<>(list);
        for(ScorePaper sp:page.getList()){
        	ScorePaperUser scorePagerUser = new ScorePaperUser();
        	SecurityUser user = SecurityUtil.getCurrentSecurityUser();
        	String CUname = user.getLoginName();
        	String Mname = CUname ;
        	int userId = user.getId();
        	//TODO 审核员 查看 填报员填报得信息 Mname = CUname.substring(0,CUname.lastIndexOf("-001"))+"-002";  
        	if(CUname.endsWith("001")){//如果学会审核员
        		Mname = CUname.substring(0,CUname.lastIndexOf("001"))+"002";
        		userId = userService.findUserByLoginName(Mname).getId();
        	}else if(CUname.endsWith("002")){
        		//Mname = CUname ;
        	}
        	else{//如果不是学会审核员 返回空 
        		//TODO 如果不是学会审核员 返回空 是否正确
        		return new PageInfo<>(); 
        	}
        	scorePagerUser.setUserId(userId);
        	scorePagerUser.setPaperId(sp.getId());
        	ScorePaperUser su = getMapper().selectOne(scorePagerUser);
        	Short status = new Short("0");
        	if(su!=null){
        		status = su.getStatus();
        	}
        	sp.setStatus(status);
        }
        return page;
    }

	@Override
	public PageInfo<PagerUserDto> selectAllUserCommit(PagerUserDto pagerUserDto,
			int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<PagerUserDto> list = this.scorePagerUserMapper.selectUserCommitPaper(pagerUserDto);
		
		return new PageInfo<PagerUserDto>(list);
	}

	
}
