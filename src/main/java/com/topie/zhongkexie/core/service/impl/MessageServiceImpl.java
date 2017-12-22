package com.topie.zhongkexie.core.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.baseservice.impl.BaseService;
import com.topie.zhongkexie.common.exception.DefaultBusinessException;
import com.topie.zhongkexie.common.utils.date.DateStyle;
import com.topie.zhongkexie.common.utils.date.DateUtil;
import com.topie.zhongkexie.core.service.ICommonStaticsService;
import com.topie.zhongkexie.core.service.IMessageService;
import com.topie.zhongkexie.core.service.IScorePaperService;
import com.topie.zhongkexie.core.statics.CommonStaticsS;
import com.topie.zhongkexie.database.core.dao.MessageMapper;
import com.topie.zhongkexie.database.core.model.Message;
import com.topie.zhongkexie.database.core.model.ScorePaper;
import com.topie.zhongkexie.security.utils.SecurityUtil;

@Service
public class MessageServiceImpl extends BaseService<Message> implements IMessageService {

	@Autowired
	private IScorePaperService iScorePaperService;
	@Autowired
	private ICommonStaticsService iCommonStaticsService; 
	@Autowired
	private MessageMapper messageMapper;
	
	@Override
	public PageInfo<Message> selectByFilterAndPage(Message message, int pageNum,
			int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Example ex = new Example(Message.class);
		Criteria c = ex.createCriteria();
		if(StringUtils.isNotEmpty(message.getTitle())) c.andLike("title", "%"+message.getTitle()+"%");
		if(StringUtils.isNotEmpty(message.getCreateUser())) c.andLike("createUser", "%"+message.getCreateUser()+"%");
		if(null!=message.getSpId())c.andEqualTo("spId",message.getSpId());
		ex.setOrderByClause("create_time desc");
		List<Message> list = this.messageMapper.selectByExample(ex); 
		PageInfo<Message> page = new PageInfo<Message>(list);
		return page;
	}

	@Override
	public int save(Message message) {
		Message m = new Message();
		m.setSpId(message.getSpId());
		if(selectByFilterAndPage(m,1,5).getTotal()>0){
			return 0;
		}
		setDetail(message);
		return super.save(message);
	}

	@Override
	public int saveNotNull(Message message) {
		setDetail(message);
		return super.saveNotNull(message);
	}

	private void setDetail(Message message) {
		message.setCreateUserId(SecurityUtil.getCurrentUserId());
    	message.setCreateUser(SecurityUtil.getCurrentUserName());
    	message.setCreateTime(DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
    	String title="";
    	String content="";
    	try{
    		title = iCommonStaticsService.selectByKey(CommonStaticsS.MESSAGE_TITLE_TEMPLATE).getsValue();
    	    content = iCommonStaticsService.selectByKey(CommonStaticsS.MESSAGE_CONTENT_TEMPLATE).getsValue();
    	}catch(NullPointerException e){
    		throw new DefaultBusinessException("通知模板未设置！");
    	}
    	ScorePaper sp = iScorePaperService.selectByKey(message.getSpId());
    	title = title.replace("${paperName}",sp.getTitle());
    	message.setTitle(title);
    	content = content.replace("${paperName}", sp.getTitle())
    			.replace("${startTime}", DateUtil.DateToString(sp.getBegin(),DateStyle.YYYY_MM_DD))
    			.replace("${endTime}", DateUtil.DateToString(sp.getEnd(),DateStyle.YYYY_MM_DD));
    	message.setContent(content);
		
	}

	

}
