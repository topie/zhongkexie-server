package com.topie.zhongkexie.expert.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.baseservice.impl.BaseService;
import com.topie.zhongkexie.database.core.model.User;
import com.topie.zhongkexie.database.expert.model.ExpertInfo;
import com.topie.zhongkexie.expert.service.IExpertInfoService;
import com.topie.zhongkexie.security.service.UserService;

@Service
public class ExpertInfoServiceImpl extends BaseService<ExpertInfo> implements
		IExpertInfoService {

	@Value("${zhuangjia.roleId}")
	private Integer roleId;
	@Value("${zhuangjia.userType}")
	private Integer userType;
	@Autowired
	private UserService userService;

	@Override
	public PageInfo<ExpertInfo> selectByFilterAndPage(ExpertInfo expertInfo,
			int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<ExpertInfo> list = selectByFilter(expertInfo);
		return new PageInfo<ExpertInfo>(list);
	}

	@Override
	public List<ExpertInfo> selectByFilter(ExpertInfo expertInfo) {
		Example ex = new Example(ExpertInfo.class);
		Criteria c = ex.createCriteria();
		if (!StringUtils.isEmpty(expertInfo.getRealName())) {
			c.andLike("realName", "%" + expertInfo.getRealName() + "%");
		}
		if (!StringUtils.isEmpty(expertInfo.getLoginName())) {
			c.andLike("loginName", "%" + expertInfo.getLoginName() + "%");
		}
		if (!StringUtils.isEmpty(expertInfo.getEmail())) {
			c.andLike("email", "%" + expertInfo.getEmail() + "%");
		}
		if (!StringUtils.isEmpty(expertInfo.getRelatedField())) {
			c.andEqualTo("relatedField", expertInfo.getRelatedField());
		}
		if (!StringUtils.isEmpty(expertInfo.getFieldType())) {
			c.andEqualTo("fieldType", expertInfo.getFieldType());
		}
		return getMapper().selectByExample(ex);
	}

	@Override
	public int delete(Object key) {
		ExpertInfo info = selectByKey(key);
		userService.deleteUser(info.getUserId());
		return mapper.deleteByPrimaryKey(key);
	}

	@Override
	public int saveNotNull(ExpertInfo entity,String password) {
		User user = new User();
		user.setLoginName(entity.getLoginName());
		user.setPassword(password);
		user.setEmail(entity.getEmail());
		user.setDisplayName(entity.getRealName());
		user.setContactPhone(entity.getTelPhone());
		user.setUserType(userType);
		List<Integer> roles = new ArrayList<Integer>();
		roles.add(roleId);
		user.setRoles(roles);
		userService.insertUser(user);
		entity.setUserId(user.getId());
		return mapper.insertSelective(entity);
	}
	@Override
	public int updateNotNull(ExpertInfo entity){
		Integer userId = entity.getUserId();
		if(userId!=null){
			User user = userService.selectByKey(userId);
			if(user!=null){
				user.setEmail(entity.getEmail());
				user.setContactPhone(entity.getTelPhone());
			}
		}
        return mapper.updateByPrimaryKeySelective(entity);
		
	}
}
