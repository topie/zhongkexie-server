package com.topie.zhongkexie.appraise.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.appraise.service.IScoreAppraiseUserService;
import com.topie.zhongkexie.common.baseservice.impl.BaseService;
import com.topie.zhongkexie.core.exception.RuntimeBusinessException;
import com.topie.zhongkexie.core.service.IDeptService;
import com.topie.zhongkexie.core.service.IScoreAnswerService;
import com.topie.zhongkexie.core.service.IScoreItemService;
import com.topie.zhongkexie.database.appriase.model.ScoreAppraiseUser;
import com.topie.zhongkexie.database.core.model.Dept;
import com.topie.zhongkexie.database.core.model.ScoreAnswer;
import com.topie.zhongkexie.database.core.model.ScoreItem;
import com.topie.zhongkexie.database.core.model.User;
import com.topie.zhongkexie.security.service.UserService;

@Service
public class ScoreAppraiseUserServiceImpl extends BaseService<ScoreAppraiseUser> implements
		IScoreAppraiseUserService {
	@Value("${item.valueHasTitle}")
	private boolean itemValueHasTitle;
	
	@Autowired
	private IScoreItemService iScoreItemService;
	@Autowired
	private IScoreAnswerService iScoreAnswerService;
	@Autowired
	private IDeptService iDeptService;
	@Autowired
	private UserService userService;
	
	@Override
	public PageInfo<ScoreAppraiseUser> selectByFilterAndPage(
			ScoreAppraiseUser scoreAppraiseUser, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<ScoreAppraiseUser> list = selectByFilter(scoreAppraiseUser);
		return new PageInfo<ScoreAppraiseUser>(list);
	}

	@Override
	public List<ScoreAppraiseUser> selectByFilter(ScoreAppraiseUser scoreAppraiseUser) {
		Example ex = new Example(ScoreAppraiseUser.class);
		Criteria c = ex.createCriteria();
		
		if (!StringUtils.isEmpty(scoreAppraiseUser.getItemStatus())) {
			c.andLike("itemStatus",  scoreAppraiseUser.getItemStatus() );
		}
		if (null!= scoreAppraiseUser.getPaperId()) {
			c.andEqualTo("paperId", scoreAppraiseUser.getPaperId());
		}if (null!= scoreAppraiseUser.getUserId()) {
			c.andEqualTo("userId", scoreAppraiseUser.getUserId());
		}if (null!= scoreAppraiseUser.getItemId()) {
			c.andEqualTo("itemId", scoreAppraiseUser.getItemId());
		}
		return getMapper().selectByExample(ex);
	}
	
	@Override
	public int updateNotNull(ScoreAppraiseUser entity,boolean b) {
		divScore(entity);
		return super.updateNotNull(entity);
	}
	/**
	 * 计算分数
	 * @param paperId
	 */
	public void divScore(Integer paperId) {
		ScoreAppraiseUser scoreAppraiseUser = new ScoreAppraiseUser();
		scoreAppraiseUser.setPaperId(paperId);
		List<ScoreAppraiseUser> list = selectByFilter(scoreAppraiseUser);
		for(ScoreAppraiseUser au:list){
			divScore(au);
		}
	
	}
	/**
	 * 计算得分
	 * @param entity
	 */
	public void divScore(ScoreAppraiseUser entity) {
		String value = entity.getItemValue();
		//先清空上次得分
		Example ex = new Example(ScoreAnswer.class);
		if(entity.getItemId()!=null && entity.getPaperId()!=null){
			ex.createCriteria().andEqualTo("itemId",entity.getItemId()).andEqualTo("paperId",entity.getPaperId());
			iScoreAnswerService.deleteByExample(ex);
		}else{
			throw new RuntimeBusinessException("请求错误 没有ItemId or paperId:ScoreAppraiseUser.id="+entity.getId());
		}
		
		if(!StringUtils.isEmpty(value)){
			if(value.endsWith(","))value = value+"end";
			String[] vs = value.split(",");
			vs[vs.length-1] = vs[vs.length-1].replace("end", "");
			int title = 0;

			ScoreItem scoreItem = iScoreItemService.selectByKey(entity.getItemId());
			if(itemValueHasTitle){
				title = 1;
			}
			int length = vs.length/(4+title);//获取任务数量 （）
			
			for(int i=0;i<vs.length;i+=4+title){
				String itemtitle = vs[i];
				String orgs100 = vs[i+title];
				updateScore(orgs100,100,entity,length,title,i,itemtitle,scoreItem);
				String orgs75 = vs[i+title+1];
				updateScore(orgs75,75,entity,length,title,i,itemtitle,scoreItem);
				String orgs50 = vs[i+title+2];
				updateScore(orgs50,50,entity,length,title,i,itemtitle,scoreItem);
				String orgs25 = vs[i+title+3];
				updateScore(orgs25,25,entity,length,title,i,itemtitle,scoreItem);
			}
		}
		
	}

	/**
	 * 根据学会名 更新分数
	 * @param orgs 学会名 已；分割
	 * @param i  分值百分比
	 * @param entity  用来获取 ScoreItem
	 * @param length  平分分值
	 * @param title 
	 * @param index index=0 update index>0 追加
	 * @param itemtitle 考核任务
	 */
	private void updateScore(String orgs, int i, ScoreAppraiseUser entity,
			int length, int title, int index, String itemtitle,
			ScoreItem scoreItem) {
		int itemId = entity.getItemId();
		BigDecimal itemScore = scoreItem.getScore().divide(new BigDecimal(length),4);
		BigDecimal answerScore = itemScore.multiply(new BigDecimal(i)).divide(new BigDecimal(100),4);
		String[] orgList = orgs.split("；");
		for (int j = 0; j < orgList.length; j++) {
			String orgName = orgList[j];
			if(StringUtils.isEmpty(orgName))continue;
			Dept dept= new Dept();
			dept.setName(orgName.trim());
			List<Dept> dList = iDeptService.selectByFilter(dept);
			if(dList.size()==0)throw new RuntimeBusinessException("学会未找到："+orgName);
			Dept d = dList.get(0);
			String loginName = d.getCode()+"002";
			User user = userService.findUserByLoginName(loginName);
			if(user==null)throw new RuntimeBusinessException("学会用户未找到："+orgName+"-登录名："+loginName);
			ScoreAnswer scoreAnswer = new ScoreAnswer();
			scoreAnswer.setUserId(user.getId());
			scoreAnswer.setItemId(itemId);
			List<ScoreAnswer> answerList = iScoreAnswerService.selectByFilter(scoreAnswer);
			if(answerList.size()>0){
				scoreAnswer = answerList.get(0);
				scoreAnswer.setItemScore(itemScore);
				scoreAnswer.setAnswerReal(true);
				String answerValue ="";
				if(index==0){//第一项任务更新 //不会执行
					scoreAnswer.setAnswerScore(answerScore);
					answerValue = title==0?"":itemtitle+",";
				}else{//后面的任务 追加分值 答案
					answerValue = scoreAnswer.getAnswerValue()+(title==0?",":","+itemtitle+",");
					scoreAnswer.setAnswerScore(scoreAnswer.getAnswerScore().add(answerScore));
				}
				if(i==100)answerValue+=orgName+",,,";
				if(i==75)answerValue+=","+orgName+",,";
				if(i==50)answerValue+=",,"+orgName+",";
				if(i==25)answerValue+=",,,"+orgName;
				scoreAnswer.setAnswerValue(answerValue);
				scoreAnswer.setPaperId(entity.getPaperId());
				iScoreAnswerService.updateNotNull(scoreAnswer);
			}else{//新增
				scoreAnswer.setItemScore(itemScore);
				scoreAnswer.setIndexId(scoreItem.getIndexId());
				scoreAnswer.setAnswerReal(true);
				String answerValue ="";
				answerValue = title==0?"":itemtitle+",";
				scoreAnswer.setAnswerScore(answerScore);
				if(i==100)answerValue+=orgName+",,,";
				if(i==75)answerValue+=","+orgName+",,";
				if(i==50)answerValue+=",,"+orgName+",";
				if(i==25)answerValue+=",,,"+orgName;
				scoreAnswer.setAnswerValue(answerValue);
				scoreAnswer.setPaperId(entity.getPaperId());
				iScoreAnswerService.saveNotNull(scoreAnswer);
			}
		}
		
	}



}
