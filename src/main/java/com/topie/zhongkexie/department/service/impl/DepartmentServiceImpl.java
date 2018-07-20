package com.topie.zhongkexie.department.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.baseservice.impl.BaseService;
import com.topie.zhongkexie.core.dto.ItemDto;
import com.topie.zhongkexie.core.dto.OptionDto;
import com.topie.zhongkexie.core.dto.PaperIndexDto;
import com.topie.zhongkexie.core.service.IScoreIndexService;
import com.topie.zhongkexie.core.service.IScoreItemOptionService;
import com.topie.zhongkexie.core.service.IScoreItemService;
import com.topie.zhongkexie.core.service.IScorePaperService;
import com.topie.zhongkexie.database.core.model.ScoreIndex;
import com.topie.zhongkexie.database.core.model.ScoreItem;
import com.topie.zhongkexie.database.core.model.ScoreItemOption;
import com.topie.zhongkexie.database.core.model.ScorePaper;
import com.topie.zhongkexie.database.core.model.User;
import com.topie.zhongkexie.database.department.model.Department;
import com.topie.zhongkexie.department.service.IDepartmentService;
import com.topie.zhongkexie.security.service.UserService;
import com.topie.zhongkexie.security.utils.SecurityUtil;

@Service
public class DepartmentServiceImpl extends BaseService<Department> implements
		IDepartmentService {
	@Value("${bumen.roleId}")
	private Integer roleId;
	@Value("${bumen.userType}")
	private Integer userType;
	@Autowired
	private UserService userService;
	@Autowired
	private IScorePaperService iScorePaperService;
	@Autowired
	private IScoreItemService iScoreItemService;
	@Autowired
	private IScoreItemOptionService iScoreItemOptionService;
	@Autowired
	private IScoreIndexService iScoreIndexService;

	@Override
	public PageInfo<Department> selectByFilterAndPage(Department department,
			int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Department> list = selectByFilter(department);
		return new PageInfo<Department>(list);
	}

	@Override
	public List<Department> selectByFilter(Department department) {
		Example ex = new Example(Department.class);
		Criteria c = ex.createCriteria();

		if (!StringUtils.isEmpty(department.getDeptName())) {
			c.andLike("deptName", "%" + department.getDeptName() + "%");
		}
		if (!StringUtils.isEmpty(department.getDeptType())) {
			c.andEqualTo("deptType", department.getDeptType());
		}
		if (null != department.getUserId()) {
			c.andEqualTo("userId", department.getUserId());
		}
		return getMapper().selectByExample(ex);
	}

	@Override
	public int saveNotNull(Department entity, String password) {
		User user = new User();
		user.setLoginName(entity.getLoginName());
		user.setPassword(password);
		user.setEmail(entity.getEmail());
		user.setDisplayName(entity.getDeptName());
		user.setContactPhone(entity.getTellPhone());
		user.setUserType(userType);
		List<Integer> roles = new ArrayList<Integer>();
		roles.add(roleId);
		user.setRoles(roles);
		userService.insertUser(user);
		entity.setUserId(user.getId());
		return mapper.insertSelective(entity);
	}

	@Override
	public int delete(Object key) {
		Department info = selectByKey(key);
		userService.deleteUser(info.getUserId());
		return mapper.deleteByPrimaryKey(key);
	}

	@Override
	public Object getPaperJson(Integer paperId) {
		ScorePaper p = this.iScorePaperService.selectByKey(paperId);
		if(p==null)return new JSONObject();
		Integer userId = SecurityUtil.getCurrentUserId();
		Department department = new Department();
		department.setUserId(userId);
		List<Department> ls = this.selectByFilter(department);
		if(ls.size()==0)return new JSONObject();
		String deptType = ls.get(0).getDeptType();
		 List<ScoreIndex> childIndices = new ArrayList<>();
	        ScoreIndex scoreIndex = new ScoreIndex();
	        scoreIndex.setPaperId(paperId);
	        List<ScoreIndex> indices = iScoreIndexService.selectByFilter(scoreIndex);
	        for (ScoreIndex index : indices) {
	            Boolean flag = true;
	            for (ScoreIndex index1 : indices) {
	                if (index.getId().intValue() == index1.getParentId().intValue()) {
	                    flag = false;
	                }
	            }
	            if (flag) {
	                childIndices.add(index);
	            }
	        }
	    List<PaperIndexDto> paperIndexDtos = new ArrayList<>();
		for (ScoreIndex childIndex : childIndices) {
			ScoreItem scoreItem = new ScoreItem();
			scoreItem.setSort_("sort_asc");
			scoreItem.setIndexId(childIndex.getId());
			scoreItem.setResponsibleDepartment(deptType);
			List<ScoreItem> scoreItems = iScoreItemService
					.selectByFilter(scoreItem);
			if(scoreItems.size()==0)continue;
			
			PaperIndexDto paperIndexDto = new PaperIndexDto();
			String path = childIndex.getName();
			path = getParentIndexString(path, childIndex, indices);
			paperIndexDto.setIndexId(childIndex.getId());
			paperIndexDto.setIndexTitle(childIndex.getName());
			paperIndexDto.setParentIndexTitle(path);
			List<ItemDto> ids = new ArrayList<>();
			for (ScoreItem item : scoreItems) {
				ItemDto itemDto = map2ItemDto(item);
				ids.add(itemDto);
			}
			paperIndexDto.setItems(ids);
			paperIndexDtos.add(paperIndexDto);
		}
		JSONObject jo = JSONObject.parseObject("{}");
		jo.put("title", p.getTitle());
		jo.put("data", paperIndexDtos);
		return jo;
	}

	private String getParentIndexString(String path, ScoreIndex childIndex,
			List<ScoreIndex> indices) {
		Integer parentId = childIndex.getParentId();
		for (ScoreIndex index : indices) {
			if (index.getId().intValue() == parentId) {
				path = index.getName() + ">" + path;
				path = getParentIndexString(path, index, indices);
			}
		}
		return path;
	}

	private ItemDto map2ItemDto(ScoreItem item) {
		ItemDto itemDto = new ItemDto();
		itemDto.setId(item.getId());
		itemDto.setTitle(item.getTitle());
		itemDto.setScore(item.getScore());
		itemDto.setItemType(item.getType());
		itemDto.setCustomItems(item.getItems());
		itemDto.setShowLevel(item.getShowLevel());
		itemDto.setScoreType(item.getScoreType());
		itemDto.setHideBtn(item.getHideBtn());
		itemDto.setPlaceholder(item.getPlaceholder());
		itemDto.setRow(item.getRow());
		itemDto.setInfo(item.getInfo());
		List<OptionDto> itemOptions = new ArrayList<>();
		ScoreItemOption scoreItemOption = new ScoreItemOption();
		scoreItemOption.setSort_("option_sort_asc");
		scoreItemOption.setItemId(item.getId());
		List<ScoreItemOption> scoreItemOptions = iScoreItemOptionService
				.selectByFilter(scoreItemOption);
		for (ScoreItemOption itemOption : scoreItemOptions) {
			OptionDto optionDto = new OptionDto();
			optionDto.setId(itemOption.getId());
			optionDto.setTitle(itemOption.getOptionTitle());
			itemOptions.add(optionDto);
		}
		itemDto.setItems(itemOptions);
		return itemDto;
	}

}
