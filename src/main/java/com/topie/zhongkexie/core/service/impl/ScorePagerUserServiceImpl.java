package com.topie.zhongkexie.core.service.impl;


import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.baseservice.impl.BaseService;
import com.topie.zhongkexie.common.exception.DefaultBusinessException;
import com.topie.zhongkexie.core.dto.PagerUserDto;
import com.topie.zhongkexie.core.service.IScoreAnswerService;
import com.topie.zhongkexie.core.service.IScorePagerUserService;
import com.topie.zhongkexie.core.service.IScorePaperService;
import com.topie.zhongkexie.database.core.dao.ScorePaperUserMapper;
import com.topie.zhongkexie.database.core.model.ScorePaper;
import com.topie.zhongkexie.database.core.model.ScorePaperUser;
import com.topie.zhongkexie.database.expert.model.ExpertDeptUser;
import com.topie.zhongkexie.expert.service.IExpertDeptUserService;
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
	@Autowired
	IScoreAnswerService iScoreAnswerService;
	@Autowired
	private IExpertDeptUserService iExpertDeptUserService;

	@Override
	public void check(int id, short result,String feedback) {
		ScorePaperUser scorePagerUser = new ScorePaperUser();
    	SecurityUser user = SecurityUtil.getCurrentSecurityUser();
    	String CUname = user.getLoginName();
    	String Mname = CUname ;
    	int userId = user.getId();
    	//TODO 审核员 查看 填报员填报得信息 Mname = CUname.substring(0,CUname.lastIndexOf("001"))+"002";  
    	if(CUname.endsWith("001")){//如果学会审核员 （通过 退回）
    		Mname = CUname.substring(0,CUname.lastIndexOf("001"))+"002";
    		userId = userService.findUserByLoginName(Mname).getId();
    	}else if(CUname.endsWith("002")&& PagerUserDto.PAPERSTATUS_SUBMMIT.equals(result)){//填报员 提交审核
    		System.out.println(CUname+"=》提交审核");
    	}else if(CUname.equals("admin")){
    	}
    	else {
    		throw new DefaultBusinessException("学会管理员才能审核!");
    	}
    	scorePagerUser.setUserId(userId);
    	scorePagerUser.setPaperId(id);
    	ScorePaperUser su = getMapper().selectOne(scorePagerUser);
		if(su!=null)
		{
			scorePagerUser.setStatus(result);
			if(PagerUserDto.PAPERSTATUS_EGIS.equals(result)){
				ExpertDeptUser entity = new ExpertDeptUser();
				entity.setStatus("1");
				Example example = new Example(ExpertDeptUser.class);
				example.createCriteria().andEqualTo("paperId", id).andEqualTo("deptUserId",userId);
				iExpertDeptUserService.updateByExampleSelective(entity, example);
			}
			scorePagerUser.setFeedback(feedback);
			getMapper().updateByPrimaryKey(scorePagerUser);
		}
		else
		{
			scorePagerUser = new ScorePaperUser();
			scorePagerUser.setUserId(userId);
			scorePagerUser.setPaperId(id);
            scorePagerUser.setStatus(result);	
			scorePagerUser.setFeedback(feedback);
            getMapper().insert(scorePagerUser);
		}
	}
	@Override
	public void check(int id, short result,int userId ,String feedback) {
		ScorePaperUser scorePagerUser = new ScorePaperUser();
    	scorePagerUser.setUserId(userId);
    	scorePagerUser.setPaperId(id);
    	ScorePaperUser su = getMapper().selectOne(scorePagerUser);
		if(su!=null)
		{
			scorePagerUser.setStatus(result);
			scorePagerUser.setFeedback(feedback);
			getMapper().updateByPrimaryKey(scorePagerUser);
			if(PagerUserDto.PAPERSTATUS_NEW.equals(result)){
				ExpertDeptUser entity = new ExpertDeptUser();
				entity.setStatus("0");
				Example example = new Example(ExpertDeptUser.class);
				example.createCriteria().andEqualTo("paperId", id).andEqualTo("deptUserId",userId);
				iExpertDeptUserService.updateByExampleSelective(entity, example);
			}
		}
		else
		{
			scorePagerUser = new ScorePaperUser();
			scorePagerUser.setUserId(userId);
			scorePagerUser.setPaperId(id);
            scorePagerUser.setStatus(result);	
			scorePagerUser.setFeedback(feedback);
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
        	else if(CUname.equals("admin")){
        		
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
        	sp.setApproveStatus(status);
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

	@Override
	public ScorePaperUser getCurrentUserScorePaperUser(Integer paperId) {
		ScorePaperUser scorePagerUser = new ScorePaperUser();
    	SecurityUser user = SecurityUtil.getCurrentSecurityUser();
    	String CUname = user.getLoginName();
    	String Mname = CUname ;
    	int userId = user.getId();
    	//TODO 审核员 查看 填报员填报得信息 Mname = CUname.substring(0,CUname.lastIndexOf("001"))+"002";  
    	if(CUname.endsWith("001")){//如果学会审核员 （通过 退回）
    		Mname = CUname.substring(0,CUname.lastIndexOf("001"))+"002";
    		userId = userService.findUserByLoginName(Mname).getId();
    	}else if(CUname.endsWith("002")){//填报员 提交审核
    		System.out.println(CUname+"=》提交审核");
    	}else if(CUname.equals("admin")){
    		
    	}
    	else {
    		return null;
    	}
    	scorePagerUser.setUserId(userId);
    	scorePagerUser.setPaperId(paperId);
    	ScorePaperUser su = getMapper().selectOne(scorePagerUser);
    	return su;
	}
	@Override
	public void updatePaperScore(Integer paperId) {
		List<ScorePaperUser> list = this.scorePagerUserMapper.selectByPagerId(paperId);
		for(ScorePaperUser pu:list){
			Integer userId = pu.getUserId();
			BigDecimal score = this.iScoreAnswerService.getUserScore(paperId, userId);
			pu.setScore(score);
			this.updateNotNull(pu);
		}
		
	}
	@Override
	public PageInfo<PagerUserDto> selectZJUserCommit(PagerUserDto pagerUserDto,
			int pageNum, int pageSize) {
		SecurityUser user = SecurityUtil.getCurrentSecurityUser();
		Integer type = user.getUserType();
		if(type==1){//中科协用户
			PageHelper.startPage(pageNum, pageSize);
			List<PagerUserDto> list = this.scorePagerUserMapper.selectUserCommitPaper(pagerUserDto);
			return new PageInfo<PagerUserDto>(list);
		}if(type==4){//专家用户
			pagerUserDto.setUserId(user.getId());
			PageHelper.startPage(pageNum, pageSize);
			List<PagerUserDto> list = this.scorePagerUserMapper.selectExpertUserCommitPaper(pagerUserDto);
			return new PageInfo<PagerUserDto>(list);
			
		}
		
		 return new PageInfo<PagerUserDto>();
	}

	
}
