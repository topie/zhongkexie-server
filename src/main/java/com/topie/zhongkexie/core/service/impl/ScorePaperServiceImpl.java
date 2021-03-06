package com.topie.zhongkexie.core.service.impl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.appraise.service.IScoreAppraiseUserService;
import com.topie.zhongkexie.common.baseservice.impl.BaseService;
import com.topie.zhongkexie.common.exception.DefaultBusinessException;
import com.topie.zhongkexie.common.utils.ExcelReaderNull;
import com.topie.zhongkexie.common.utils.JavaExecScript;
import com.topie.zhongkexie.core.dto.ItemDto;
import com.topie.zhongkexie.core.dto.OptionDto;
import com.topie.zhongkexie.core.dto.PagerUserDto;
import com.topie.zhongkexie.core.dto.PaperIndexDto;
import com.topie.zhongkexie.core.exception.RuntimeBusinessException;
import com.topie.zhongkexie.core.service.IDeptService;
import com.topie.zhongkexie.core.service.IPaperExpertConfService;
import com.topie.zhongkexie.core.service.IScoreAnswerService;
import com.topie.zhongkexie.core.service.IScoreIndexCollectionService;
import com.topie.zhongkexie.core.service.IScoreIndexService;
import com.topie.zhongkexie.core.service.IScoreItemOptionService;
import com.topie.zhongkexie.core.service.IScoreItemService;
import com.topie.zhongkexie.core.service.IScorePaperService;
import com.topie.zhongkexie.database.core.dao.ScorePaperUserMapper;
import com.topie.zhongkexie.database.core.model.Attachment;
import com.topie.zhongkexie.database.core.model.Dept;
import com.topie.zhongkexie.database.core.model.PaperExpertConf;
import com.topie.zhongkexie.database.core.model.ScoreAnswer;
import com.topie.zhongkexie.database.core.model.ScoreIndex;
import com.topie.zhongkexie.database.core.model.ScoreIndexCollection;
import com.topie.zhongkexie.database.core.model.ScoreItem;
import com.topie.zhongkexie.database.core.model.ScoreItemOption;
import com.topie.zhongkexie.database.core.model.ScorePaper;
import com.topie.zhongkexie.database.core.model.User;
import com.topie.zhongkexie.database.expert.dao.ExpertDeptUserMapper;
import com.topie.zhongkexie.database.expert.model.ExpertInfo;
import com.topie.zhongkexie.expert.service.IExpertInfoService;
import com.topie.zhongkexie.expert.service.IExpertItemScoreService;
import com.topie.zhongkexie.mem.service.IMemUserScoreService;
import com.topie.zhongkexie.security.security.SecurityUser;
import com.topie.zhongkexie.security.service.UserService;
import com.topie.zhongkexie.security.utils.SecurityUtil;

/**
 * Created by chenguojun on 2017/4/19.
 */
@Service
public class ScorePaperServiceImpl extends BaseService<ScorePaper> implements
		IScorePaperService {

	@Autowired
	private IScoreIndexService iScoreIndexService;
	@Autowired
	private IScoreIndexCollectionService iScoreIndexCollectionService;
	@Autowired
	private IExpertItemScoreService iExpertItemScoreService;
	@Autowired
	private IScoreItemService iScoreItemService;

	@Autowired
	private IScoreItemOptionService iScoreItemOptionService;

	@Autowired
	private ScorePaperUserMapper dScorePaperUserMapper;
	
	@Autowired
	private IPaperExpertConfService iPaperExpertConfService;

	@Autowired
	private IDeptService deptService;

	@Autowired
	private IScoreAnswerService iScoreAnswerService;
	@Autowired
	private UserService userService;
	@Autowired
	private IExpertInfoService experInfoService;

	@Autowired
	private IScoreAppraiseUserService iScoreAppraiseUserService;
	@Autowired
	private IMemUserScoreService iMemUserScoreService;
	@Autowired
	private  ExpertDeptUserMapper expertDeptUserMapper;

	@Override
	public PageInfo<ScorePaper> selectByFilterAndPage(ScorePaper scorePaper,
			int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<ScorePaper> list = selectByFilter(scorePaper);
		return new PageInfo<>(list);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PageInfo<ScorePaper> selectByFilterAndPageForCheck(
			ScorePaper scorePaper, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Example example = new Example(ScorePaper.class);
		Example.Criteria criteria = example.createCriteria();
		if (scorePaper.getStatus() != null)
			criteria.andEqualTo("status", scorePaper.getStatus());
		List ls = new ArrayList();
		ls.add(PagerUserDto.PAPERSTATUS_SUBMMIT);
		ls.add(PagerUserDto.PAPERSTATUS_EGIS);
		criteria.andIn("approveStatus", ls);
		if (StringUtils.isNotEmpty(scorePaper.getTitle()))
			criteria.andLike("title", "%" + scorePaper.getTitle() + "%");
		if (StringUtils.isNotEmpty(scorePaper.getSortWithOutOrderBy()))
			example.setOrderByClause(scorePaper.getSortWithOutOrderBy());
		List<ScorePaper> list = getMapper().selectByExample(example);

		return new PageInfo<>(list);
	}

	@Override
	public List<ScorePaper> selectByFilter(ScorePaper scorePaper) {
		Example example = new Example(ScorePaper.class);
		Example.Criteria criteria = example.createCriteria();
		if (scorePaper.getStatus() != null)
			criteria.andEqualTo("status", scorePaper.getStatus());
		if (scorePaper.getApproveStatus() != null)
			criteria.andEqualTo("approveStatus", scorePaper.getApproveStatus());
		if (StringUtils.isNotEmpty(scorePaper.getTitle()))
			criteria.andLike("title", "%" + scorePaper.getTitle() + "%");
		if (StringUtils.isNotEmpty(scorePaper.getSortWithOutOrderBy()))
			example.setOrderByClause(scorePaper.getSortWithOutOrderBy());
		return getMapper().selectByExample(example);
	}

	@Override
	public String getContentJson(Integer paperId, String title) {
		List<ScoreIndex> childIndices = new ArrayList<>();
		ScoreIndex scoreIndex = new ScoreIndex();
		scoreIndex.setPaperId(paperId);
		List<ScoreIndex> indices = iScoreIndexService
				.selectByFilter(scoreIndex);
		ScoreIndex parent = new ScoreIndex();
		parent.setId(0);
		childIndices = getLeafIndex(parent, indices);// 20180708更新为排序方式
		List<PaperIndexDto> paperIndexDtos = new ArrayList<>();
		for (ScoreIndex childIndex : childIndices) {
			PaperIndexDto paperIndexDto = new PaperIndexDto();
			String path = childIndex.getName();
			path = getParentIndexString(path, childIndex, indices);
			paperIndexDto.setIndexId(childIndex.getId());
			paperIndexDto.setIndexTitle(childIndex.getName());
			paperIndexDto.setParentIndexTitle(path);
			ScoreItem scoreItem = new ScoreItem();
			scoreItem.setSort_("sort_asc");
			scoreItem.setIndexId(childIndex.getId());
			List<ItemDto> ids = new ArrayList<>();
			List<ScoreItem> scoreItems = iScoreItemService
					.selectByFilter(scoreItem);
			for (ScoreItem item : scoreItems) {
				ItemDto itemDto = map2ItemDto(item);
				ids.add(itemDto);
			}
			paperIndexDto.setItems(ids);
			paperIndexDtos.add(paperIndexDto);
		}
		JSONObject jo = JSONObject.parseObject("{}");
		jo.put("title", title);
		jo.put("data", paperIndexDtos);
		return jo.toJSONString();
	}

	private List<ScoreIndex> getLeafIndex(ScoreIndex parent,
			List<ScoreIndex> indices) {
		List<ScoreIndex> chiled = new ArrayList<ScoreIndex>();
		List<ScoreIndex> chiled2 = new ArrayList<ScoreIndex>();
		boolean f = true;
		for (ScoreIndex index : indices) {
			if (index.getParentId().equals(parent.getId())) {
				f = false;
				chiled2.addAll(getLeafIndex(index, indices));
			}
		}
		if (f)
			chiled.add(parent);
		chiled.addAll(chiled2);
		return chiled;
	}

	@Override
	public void check(int id, short result) {
		ScorePaper page = getMapper().selectByPrimaryKey(id);
		page.setApproveStatus(result);
		getMapper().updateByPrimaryKey(page);
	}

	private String getParentIndexString(String path, ScoreIndex childIndex,
			List<ScoreIndex> indices) {
		Integer parentId = childIndex.getParentId();
		if(parentId==null)return path;
		for (ScoreIndex index : indices) {
			if (index.getId().intValue() == parentId) {
				path = index.getName() + ">" + path;
				path = getParentIndexString(path, index, indices);
			}
		}
		return path;
	}

	@Override
	public JSONObject getContentJson(Integer paperId, String indexIds,
			String orgIds) {
		ScorePaper p = this.getMapper().selectByPrimaryKey(paperId);
		if (p == null)
			return new JSONObject();
		String title = p.getTitle();
		List<ScoreIndex> childIndices = new ArrayList<>();
		Example ex = new Example(ScoreIndex.class);
		Criteria c = ex.createCriteria();
		c.andEqualTo("paperId", paperId);
		List list = Arrays.asList(indexIds.split(","));
		c.andIn("id", list);
		ex.setOrderByClause("sort asc");
		List<ScoreIndex> indices = iScoreIndexService.selectByExample(ex);

		ScoreIndex parent = new ScoreIndex();
		parent.setId(0);
		childIndices = getLeafIndex(parent, indices);// 20180708更新为排序方式
		// 修复叶子指标获取
		if (childIndices.size()==1) {
			childIndices = new ArrayList<ScoreIndex>();
			for (ScoreIndex index : indices) {
				Boolean flag = true;
				for (ScoreIndex index1 : indices) {
					if (index.getId().intValue() == index1.getParentId()
							.intValue()) {
						flag = false;
					}
				}
				if (flag) {
					childIndices.add(index);
				}
			}
		}
		//修复叶子指标获取end
		/*
		 * for (ScoreIndex index : indices) { Boolean flag = true; for
		 * (ScoreIndex index1 : indices) { if (index.getId().intValue() ==
		 * index1.getParentId().intValue()) { flag = false; } } if (flag) {
		 * childIndices.add(index); } }
		 */
		List<PaperIndexDto> paperIndexDtos = new ArrayList<>();
		for (ScoreIndex childIndex : childIndices) {
			PaperIndexDto paperIndexDto = new PaperIndexDto();
			String path = childIndex.getName();
			path = getParentIndexString(path, childIndex, indices);
			paperIndexDto.setIndexId(childIndex.getId());
			paperIndexDto.setIndexTitle(childIndex.getName());
			paperIndexDto.setParentIndexTitle(path);
			ScoreItem scoreItem = new ScoreItem();
			scoreItem.setSort_("sort_asc");
			scoreItem.setIndexId(childIndex.getId());
			List<ItemDto> ids = new ArrayList<>();
			List<ScoreItem> scoreItems = iScoreItemService
					.selectByFilter(scoreItem);
			for (ScoreItem item : scoreItems) {
				ItemDto itemDto = map2ItemDto(item);
				ids.add(itemDto);
			}
			paperIndexDto.setItems(ids);
			paperIndexDtos.add(paperIndexDto);
		}
		JSONObject jo = JSONObject.parseObject("{}");
		jo.put("title", title);
		jo.put("data", paperIndexDtos);
		return jo;
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
		itemDto.setHideUploadFile(item.getHideUploadFile());
		itemDto.setTemplateId(item.getTemplateFileId());
		itemDto.setTemplateDesc(item.getTemplateFileDesc());
		if(item.getScoreType()!=null && item.getScoreType().equals("3")){
			itemDto.setOptionLogicDesc(item.getOptionLogicDesc());
		}
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

	@Override
	public int saveNotNull(ScorePaper scorePaper, Integer copyPaperId) {
		int result = this.saveNotNull(scorePaper);
		int paperId = scorePaper.getId();
		ScoreIndex scoreIndex = new ScoreIndex();
		scoreIndex.setPaperId(copyPaperId);
		scoreIndex.setParentId(0);
		List<ScoreIndex> indexList = iScoreIndexService
				.selectByFilter(scoreIndex);
		for (ScoreIndex index : indexList) {
			Integer oldid = index.getId();
			index.setId(null);
			index.setPaperId(paperId);
			iScoreIndexService.saveNotNull(index);
			saveChildIndex(paperId, index.getId(), oldid);

		}
		scorePaper.setContentJson(getContentJson(scorePaper.getId(),
				scorePaper.getTitle()));
		this.updateNotNull(scorePaper);
		return result;
	}

	/**
	 * 复制指标和题目
	 * 
	 * @param paperId
	 * @param newParentId
	 * @param oldParentId
	 */
	private void saveChildIndex(Integer paperId, Integer newParentId,
			Integer oldParentId) {
		ScoreIndex scoreIndex = new ScoreIndex();
		scoreIndex.setParentId(oldParentId);
		List<ScoreIndex> indexList = iScoreIndexService
				.selectByFilter(scoreIndex);
		for (ScoreIndex index : indexList) {
			Integer oldid = index.getId();
			index.setId(null);
			index.setPaperId(paperId);
			index.setParentId(newParentId);
			iScoreIndexService.saveNotNull(index);
			saveChildIndex(paperId, index.getId(), oldid);

		}
		if (indexList.size() == 0) {// 如果是最底层节点
			ScoreItem scoreItem = new ScoreItem();
			scoreItem.setIndexId(oldParentId);
			List<ScoreItem> itemList = iScoreItemService
					.selectByFilter(scoreItem);// 查询题目
			for (ScoreItem item : itemList) {
				Integer oldItemId = item.getId();
				item.setId(null);
				item.setIndexId(newParentId);
				iScoreItemService.saveNotNull(item);
				Integer newItemId = item.getId();
				ScoreItemOption scoreItemOption = new ScoreItemOption();
				scoreItemOption.setItemId(oldItemId);
				List<ScoreItemOption> optionList = iScoreItemOptionService
						.selectByFilter(scoreItemOption);
				for (ScoreItemOption option : optionList) {// 查询选项
					option.setId(null);
					option.setItemId(newItemId);
					iScoreItemOptionService.saveNotNull(option);
				}
			}
		}

	}

	@Override
	public XSSFWorkbook exportPaper(Integer paperId, String indexIds,
			String orgIds, String type) {
		ScorePaper p = this.getMapper().selectByPrimaryKey(paperId);
		if (p == null)
			throw new RuntimeBusinessException("paperId不能为空");
		String title = p.getTitle();
		Example ex = new Example(ScoreIndex.class);
		Criteria c = ex.createCriteria();
		c.andEqualTo("paperId", paperId);
		if (indexIds == null || indexIds.equals("")) {
			// 查询全部指标不用过滤
		} else {// 查询部分指标
			List list = Arrays.asList(indexIds.split(","));
			c.andIn("id", list);
		}
		List<ScoreIndex> indexList = iScoreIndexService.selectByExample(ex);
		List<ScoreIndex> indexParentList = new ArrayList<ScoreIndex>();
		List<ScoreIndex> indexChildList = new ArrayList<ScoreIndex>();
		for (ScoreIndex index : indexList) {
			if (index.getParentId() == 0) {
				indexParentList.add(index);
			} else {
				indexChildList.add(index);
			}
		}
		Example example = new Example(Dept.class);
		List<Dept> depts = new ArrayList<Dept>();
		if (orgIds != null && !orgIds.equals("")) {
			example.createCriteria().andIn("id",
					Arrays.asList(orgIds.split(",")));
			depts = deptService.selectByExample(example);
		}
		String[] indexTitle = "一级指标,,二级指标,,三级指标,,四级指标,,五级指标,,六级指标,,七级指标,,八级指标,"
				.split(",");
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet("sheet1");
		sheet.autoSizeColumn(1);// 设置每个单元格宽度根据字多少自适应
		XSSFCellStyle style = getStyle(wb);
		int row = 0;// 行
		for (ScoreIndex index : indexParentList) {
			int col = 0;// 列
			row = exportIndex(sheet, index, indexTitle, depts, paperId, row,
					col, indexChildList, type);
		}
		List<Integer> collist = getNeedMergeCell(sheet);
		mergeCell(sheet, collist, style);
		sumScore(sheet);
		return wb;
	}

	private void sumScore(XSSFSheet sheet) {
		// TODO Auto-generated method stub

	}

	private void mergeCell(XSSFSheet sheet, List<Integer> collist,
			XSSFCellStyle style) {
		// 获得总列数
		int maxRowNum = sheet.getLastRowNum();// 获得总行数

		for (int j : collist) {
			int startRow = 1;
			String startValue = "";
			for (int rowIndex = 1; rowIndex <= maxRowNum; rowIndex++) {
				try {
					XSSFCell cell = sheet.getRow(rowIndex).getCell(j);
					if (cell == null) {
						if (rowIndex == maxRowNum && startRow < (rowIndex - 1)) {
							CellRangeAddress region = new CellRangeAddress(
									startRow, rowIndex - 1, j, j); // 参数1：起始行
																	// 参数2：终止行
																	// 参数3：起始列
																	// 参数4：终止列
							sheet.addMergedRegion(region);
						}
						continue;
					}
					cell.setCellStyle(style);
					String value = getCellValue(cell);
					if (StringUtils.isNotEmpty(value)) {
						if (startRow < rowIndex - 1) {
							CellRangeAddress region = new CellRangeAddress(
									startRow, rowIndex - 1, j, j); // 参数1：起始行
																	// 参数2：终止行
																	// 参数3：起始列
																	// 参数4：终止列
							sheet.addMergedRegion(region);
						}
						startValue = value;
						startRow = rowIndex;
					}
					if (rowIndex == maxRowNum && startRow < (rowIndex - 1)) {
						CellRangeAddress region = new CellRangeAddress(
								startRow, rowIndex - 1, j, j); // 参数1：起始行
																// 参数2：终止行
																// 参数3：起始列
																// 参数4：终止列
						sheet.addMergedRegion(region);
					}
				} catch (Exception e) {
				}
			}

		}

	}

	private List<Integer> getNeedMergeCell(XSSFSheet sheet) {
		List<Integer> collist = new ArrayList();
		for (int i = 0;; i++) {
			String value = sheet.getRow(0).getCell(i).getStringCellValue();
			if (value.endsWith("指标")) {
				collist.add(i);
			}
			if (value.equals("分值")) {
				collist.add(i);
			}
			if (value.endsWith("题目")) {
				break;
			}
			if (value.endsWith("学会")) {
				break;
			}
			if (i > 20) {
				break;
			}
		}
		return collist;
	}

	private XSSFCellStyle getStyle(XSSFWorkbook wb) {
		XSSFCellStyle style = wb.createCellStyle(); // style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		// // 创建一个居中格式
		XSSFFont fontSearch = wb.createFont();
		fontSearch.setFontHeightInPoints((short) 12);
		;// 设置字体大小fontSearch.setFontHeightInPoints((short)
			// 13);
		style.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 居中
		style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);// 垂直
		style.setFont(fontSearch);// 将该字体样式放入style这个样式中，其他单元格样式也是这么加的，这里只是给一个例子
		// style.setBorderBottom(XSSFCellStyle.BORDER_DOUBLE);// 设置单元格下边框
		// style.setBorderLeft(XSSFCellStyle.BORDER_DOUBLE);
		return style;
	}

	private String getCellValue(XSSFCell cell) {

		DecimalFormat nf = new DecimalFormat("0.00");// 格式化数字
		String value = "";
		switch (cell.getCellType()) {
		case XSSFCell.CELL_TYPE_STRING:
			value = cell.getStringCellValue();
			break;
		case XSSFCell.CELL_TYPE_NUMERIC:
			value = nf.format(cell.getNumericCellValue());
			break;
		}
		return value;
	}

	private int exportIndex(XSSFSheet sheet, ScoreIndex index,
			String[] indexTitle, List<Dept> depts, int paperId, int row,
			int col, List<ScoreIndex> indexChildList, String type) {
		col = writeIndex(sheet, index, row, col, indexTitle);
		boolean f = true;
		for (ScoreIndex one : indexChildList) {
			if (index.getId().intValue() == one.getParentId().intValue()) {
				row = exportIndex(sheet, one, indexTitle, depts, paperId, row,
						col, indexChildList, type);
				f = false;
			}
		}
		if (f) {// 如果是最底层指标添加题目
			ScoreItem scoreItem = new ScoreItem();
			scoreItem.setIndexId(index.getId());
			List<ScoreItem> items = iScoreItemService.selectByFilter(scoreItem);
			for (ScoreItem item : items) {
				writeItem2(sheet, item, row, col, depts, type);
				row++;
			}
		}
		return row;
	}

	/**
	 * 按配置导出统计数据
	 * 
	 * @param sheet
	 * @param item
	 * @param row
	 * @param col
	 * @param depts
	 */
	private void writeItem2(XSSFSheet sheet, ScoreItem item, int row, int col,
			List<Dept> depts, String type) {
		XSSFRow headRow = null;
		if (type != null && type.equals("withID")) {
			if (row == 0) {// 生成标题
				headRow = sheet.getRow(row) == null ? sheet.createRow(row)
						: sheet.getRow(row);
				headRow.createCell(col).setCellValue("key");
				headRow.createCell(col + 1).setCellValue("题目");
				headRow.createCell(col + 2).setCellValue("类型");
				headRow.createCell(col + 3).setCellValue("学会名称");
				headRow.createCell(col + 4).setCellValue("学会编码");
				headRow.createCell(col + 5).setCellValue("数据数量");
				headRow.createCell(col + 6).setCellValue("数据1");
				headRow.createCell(col + 7).setCellValue("数据2");
				headRow.createCell(col + 8).setCellValue("数据3");
				headRow.createCell(col + 9).setCellValue("...");
			}
			// row++;
			XSSFRow dataRow = sheet.getRow(row + 1) == null ? sheet
					.createRow(row + 1) : sheet.getRow(row + 1);
			dataRow.createCell(col).setCellValue(item.getId());
			dataRow.createCell(col + 1).setCellValue(item.getTitle());
			dataRow.createCell(col + 2).setCellValue(getType(item.getType()));
			/*
			 * }else{ XSSFRow dataRow =
			 * sheet.getRow(row+1)==null?sheet.createRow(row):sheet.getRow(row);
			 * dataRow.createCell(col).setCellValue(item.getId());
			 * dataRow.createCell(col+1).setCellValue(item.getTitle());
			 * dataRow.createCell(col+2).setCellValue(getType(item.getType()));
			 * }
			 */
			return;
		}
		if (row == 0) {// 生成标题
			headRow = sheet.getRow(row) == null ? sheet.createRow(row) : sheet
					.getRow(row);
			headRow.createCell(col).setCellValue("题目");
			headRow.createCell(col + 1).setCellValue("分值");
			headRow.createCell(col + 2).setCellValue("类型");
			// row++;
		}
		String functionBody = item.getRelatedField();// 获取导出配置
		XSSFRow dataRow = sheet.getRow(row + 1) == null ? sheet
				.createRow(row + 1) : sheet.getRow(row + 1);
		dataRow.createCell(col).setCellValue(item.getTitle());
		dataRow.createCell(col + 1).setCellValue(item.getScore() + "");
		dataRow.createCell(col + 2).setCellValue(getType(item.getScoreType()));
		if (headRow == null && StringUtils.isEmpty(functionBody.trim())
				&& (type != null && type.equals("value"))) {
			return;
		}
		int i = 3;
		for (Dept dept : depts) {
			if (headRow != null) {
				if (type == null) {
					headRow.createCell(col + i).setCellValue(
							dept.getName() + "（填报数值）");
					headRow.createCell(col + i + 1).setCellValue("得分");
				} else {
					if (type.equals("value")) {
						headRow.createCell(col + i)
								.setCellValue(dept.getName());
					} else if (type.equals("score")) {
						headRow.createCell(col + i)
								.setCellValue(dept.getName());
					} else {

						headRow.createCell(col + i)
								.setCellValue(dept.getName());
					}
				}
			}
			String userLoginName = dept.getCode() + "002";
			User user = userService.findUserByLoginName(userLoginName);
			String value = "";
			String score = "";
			if (user != null) {
				ScoreAnswer scoreAnswer = new ScoreAnswer();
				scoreAnswer.setItemId(item.getId());
				scoreAnswer.setUserId(user.getId());
				List<ScoreAnswer> answers = iScoreAnswerService
						.selectByFilter(scoreAnswer);
				for (ScoreAnswer s : answers) {
					if (type == null) {
						value = s.getAnswerValue();// 答案
//						value = JavaExecScript.jsFunction(value, functionBody);
//						if (value != null)
//							value = value.replace("NaN", "0");
						score += s.getAnswerScore();// 分数
					} else {
						if (type.equals("value")) {
							value = s.getAnswerValue();// 答案
							value = JavaExecScript.jsFunction(value,
									functionBody);
							if (value != null)
								value = value.replace("NaN", "0");
						}
						if (type.equals("score")) {
							score += s.getAnswerScore();// 分数
						}
					}
				}
			}
			if (type == null) {
				dataRow.createCell(col + i++).setCellValue(value);
				dataRow.createCell(col + i++).setCellValue(score);
			} else {
				if (type.equals("value")) {
					dataRow.createCell(col + i++).setCellValue(value);
				}
				if (type.equals("score")) {
					dataRow.createCell(col + i++).setCellValue(score);
				}
			}
		}

	}

	/*
	 * private void writeItem(XSSFSheet sheet, ScoreItem item, int row , int
	 * col, List<Dept> depts) { if(row==0){//生成标题 XSSFRow headRow =
	 * sheet.getRow(row)==null?sheet.createRow(row):sheet.getRow(row); XSSFRow
	 * dataRow =
	 * sheet.getRow(row+1)==null?sheet.createRow(row+1):sheet.getRow(row+1);
	 * headRow.createCell(col).setCellValue("题目");
	 * headRow.createCell(col+1).setCellValue("分值");
	 * headRow.createCell(col+2).setCellValue("类型");
	 * dataRow.createCell(col).setCellValue(item.getTitle());
	 * dataRow.createCell(col+1).setCellValue(item.getScore()+"");
	 * dataRow.createCell(col+2).setCellValue(getType(item.getScoreType()));
	 * //String valueFun = item.getRelatedField(); int i = 3; for(Dept
	 * dept:depts){ headRow.createCell(col+i).setCellValue(dept.getName());
	 * ScoreAnswer scoreAnswer = new ScoreAnswer(); String userLoginName =
	 * dept.getCode()+"002"; User user =
	 * userService.findUserByLoginName(userLoginName); //String value = "";
	 * String score = ""; if(user!=null){ scoreAnswer.setItemId(item.getId());
	 * scoreAnswer.setUserId(user.getId()); List<ScoreAnswer> answers =
	 * iScoreAnswerService.selectByFilter(scoreAnswer); for(ScoreAnswer
	 * s:answers){ //value+= s.getAnswerValue();//答案 score+=
	 * s.getAnswerScore();//分数 } }
	 * dataRow.createCell(col+i).setCellValue(score); i++; } }else{ XSSFRow
	 * dataRow =
	 * sheet.getRow(row+1)==null?sheet.createRow(row+1):sheet.getRow(row+1);
	 * dataRow.createCell(col).setCellValue(item.getTitle());
	 * dataRow.createCell(col+1).setCellValue(item.getScore()+"");
	 * dataRow.createCell(col+2).setCellValue(getType(item.getScoreType())); int
	 * i = 3; for(Dept dept:depts){ String userLoginName = dept.getCode()+"002";
	 * User user = userService.findUserByLoginName(userLoginName); String value
	 * = ""; if(user!=null){ ScoreAnswer scoreAnswer = new ScoreAnswer();
	 * scoreAnswer.setItemId(item.getId()); scoreAnswer.setUserId(user.getId());
	 * List<ScoreAnswer> answers =
	 * iScoreAnswerService.selectByFilter(scoreAnswer); for(ScoreAnswer
	 * s:answers){ //value+= s.getAnswerValue();//答案 value+=
	 * s.getAnswerScore();//分数 } }
	 * dataRow.createCell(col+i++).setCellValue(value); } }
	 * 
	 * }
	 */

	/*
	 * if(data.type==0)return '填空[文本]'; if(data.type==1)return '单选';
	 * if(data.type==2)return '多选'; if(data.type==3)return '填空[多]';
	 * if(data.type==4)return '填空[数字]'; if(data.type==5)return '单选[可填空]';
	 * if(data.type==6)return '多选[可填空]'; if(data.type==7)return '多填空[自定义]';
	 * if(data.type==8)return '单选[是否填空]'; if(data.type==9)return '数字[列举]';
	 * if(data.type==10)return '文本框';
	 */
	private String getType(Integer type) {
		switch (type) {
		case 0:
			return "填空[文本]";
		case 1:
			return "单选";
		case 2:
			return "多选";
		case 3:
			return "填空[多]";
		case 4:
			return "填空[数字]";
		case 5:
			return "单选[可填空]";
		case 6:
			return "多选[可填空]";
		case 7:
			return "填空[自定义]";
		case 8:
			return "单选[是否填空]";
		case 9:
			return "数字[列举]";
		case 10:
			return "文本框";

		}
		return "--";
	}

	private String getType(String scoreType) {
		switch (scoreType) {
		case "1":
			return "统计项";
		case "2":
			return "线性打分";
		case "3":
			return "专家打分";

		default:
			break;
		}
		return "- -";
	}

	private int writeIndex(XSSFSheet sheet, ScoreIndex index, int row, int col,
			String[] indexTitle) {
		if (row == 0) {// 生成标题

			XSSFRow headRow = null;
			headRow = sheet.getRow(row) == null ? sheet.createRow(row) : sheet
					.getRow(row);
			headRow.createCell(col).setCellValue(indexTitle[col]);
			headRow.createCell(col + 1).setCellValue("分值");
			XSSFRow dataRow = sheet.getRow(row + 1) == null ? sheet
					.createRow(row + 1) : sheet.getRow(row + 1);
			dataRow.createCell(col).setCellValue(index.getName());
			dataRow.createCell(col + 1).setCellValue(
					index.getScore().intValue());
		} else {
			XSSFRow dataRow = sheet.getRow(row + 1) == null ? sheet
					.createRow(row + 1) : sheet.getRow(row + 1);
			dataRow.createCell(col).setCellValue(index.getName());
			dataRow.createCell(col + 1).setCellValue(
					index.getScore().intValue());
		}
		col += 2;

		return col;
	}

	@Override
	public String getCurrentUserPaper(Integer paperId) {
		SecurityUser user = SecurityUtil.getCurrentSecurityUser();
		// 中科协用
		if (user.getUserType() == 1) {
			ScorePaper p = this.mapper.selectByPrimaryKey(paperId);
			if (p != null)
				return p.getContentJson();
		}
		// 专家
		if (user.getUserType() == 4) {
			Integer userId = user.getId();
			ExpertInfo expertInfo = experInfoService.selectByUserId(userId);
			String rf = expertInfo.getRelatedField();
			String f = expertInfo.getFieldType();

			ScoreIndexCollection scoreIndexCollection = new ScoreIndexCollection();
			scoreIndexCollection.setPaperId(paperId);
			scoreIndexCollection.setRelatedField(rf);
			List<ScoreIndexCollection> list = iScoreIndexCollectionService
					.selectByFilter(scoreIndexCollection);
			if (list.size() > 0)
				return list.get(0).getContentJson();

		}

		return null;
	}

	@Override
	public void updateWightScore(Integer paperId) {
		ScorePaper p = this.mapper.selectByPrimaryKey(paperId);
		BigDecimal paperScore = p.getScore();
		ScoreIndex scoreIndex = new ScoreIndex();
		scoreIndex.setPaperId(paperId);
		List<ScoreIndex> listIndex = iScoreIndexService
				.selectByFilter(scoreIndex);
		updateChildList(listIndex, 0, paperScore, "");
		// 更新完成之后跟新评价表 json字符串缓存
		p.setContentJson(getContentJson(paperId, p.getTitle()));
		mapper.updateByPrimaryKey(p);
		// 更新指标集合json 字符串缓存
		ScoreIndexCollection scoreIndexCollection = new ScoreIndexCollection();
		scoreIndexCollection.setPaperId(paperId);
		List<ScoreIndexCollection> ics = iScoreIndexCollectionService
				.selectByFilter(scoreIndexCollection);
		for (ScoreIndexCollection ic : ics) {
			ic.setContentJson(this.getContentJson(ic.getPaperId(),
					ic.getIndexCollection(), null).toJSONString());
			iScoreIndexCollectionService.updateAll(ic);
		}
	}

	@Override
	public void updateWightUserScore(Integer paperId) {
		ScoreIndex scoreIndex = new ScoreIndex();
		scoreIndex.setPaperId(paperId);
		List<ScoreIndex> listIndex = iScoreIndexService
				.selectByFilter(scoreIndex);
		updateChildListUserScore(listIndex, 0);
		// 计算重大任务分数
		iScoreAppraiseUserService.divScore(paperId);
		// 满意度评价分数
		iMemUserScoreService.divScore(paperId);

	}

	// 获取父id下的子集合
	private List<ScoreIndex> updateChildListUserScore(List<ScoreIndex> list,
			int pId) {
		List<ScoreIndex> reList = new ArrayList();
		for (ScoreIndex testEntity : list) {
			if (testEntity.getParentId() == pId) {// 查询下级
				reList.add(testEntity);
			}
		}
		if (reList.size() == 0) {// 最底层item
			ScoreItem scoreItem = new ScoreItem();
			scoreItem.setIndexId(pId);
			List<ScoreItem> ls = iScoreItemService.selectByFilter(scoreItem);
			for (ScoreItem item : ls) {
				ScoreAnswer scoreAnswer = new ScoreAnswer();
				scoreAnswer.setItemId(item.getId());
				List<ScoreAnswer> answers = iScoreAnswerService
						.selectByFilter(scoreAnswer);
				for (ScoreAnswer an : answers) {
					iScoreAnswerService.divScore(item, an);// 计算分数
					// BigDecimal newScore = an.getAnswerScore();
					an.setItemScore(item.getScore());
					// an.setAnswerScore(newScore);
					iScoreAnswerService.updateAll(an);
				}
			}

			return null;
		}
		for (ScoreIndex testEntity : reList) {
			updateChildListUserScore(list, testEntity.getId());
		}
		return reList;
	}

	// 获取父id下的子集合
	private List<ScoreIndex> updateChildList(List<ScoreIndex> list, int pId,
			BigDecimal totleScore, String level) {
		List<ScoreIndex> reList = new ArrayList();
		for (ScoreIndex testEntity : list) {
			if (testEntity.getParentId() == pId) {// 查询下级
				reList.add(testEntity);
			}
		}
		BigDecimal totleWeight = new BigDecimal(0);
		BigDecimal div = new BigDecimal(100);
		if (reList.size() == 0) {// 最底层item
			ScoreItem scoreItem = new ScoreItem();
			scoreItem.setIndexId(pId);
			List<ScoreItem> ls = iScoreItemService.selectByFilter(scoreItem);
			if (totleScore.compareTo(totleWeight) == 0) {// 如果是0
				for (ScoreItem testEntity : ls) {
					if (testEntity.getScore().compareTo(totleWeight) != 0) {// 总分是零
																			// 子都为0
						testEntity.setScore(totleWeight);
						iScoreItemService.updateNotNull(testEntity);
					}

				}
			} else {
				for (ScoreItem testEntity : ls) {
					totleWeight = totleWeight.add(testEntity.getWeight());
					BigDecimal s = totleScore.multiply(testEntity.getWeight())
							.divide(div, 4);
					if (testEntity.getScore().compareTo(s) != 0) {// 计算分数如果不同
																	// 就更新
						testEntity.setScore(s);
						iScoreItemService.updateNotNull(testEntity);
					}
				}
				if (totleWeight.compareTo(div) != 0) {
					throw new RuntimeBusinessException(level + "：下级指标权重之和为  （"
							+ totleWeight + "）未达到100，请检查！");
				}
			}
			return null;
		}

		if (totleScore.compareTo(totleWeight) == 0) {// 如果是0
			for (ScoreIndex testEntity : reList) {
				if (testEntity.getScore().compareTo(totleWeight) != 0) {// 总分是零
																		// 子都为0
					testEntity.setScore(totleWeight);
					iScoreIndexService.updateNotNull(testEntity);
				}
				updateChildList(list, testEntity.getId(), totleWeight, level
						+ "->" + testEntity.getName());

			}
		} else {
			for (ScoreIndex testEntity : reList) {
				totleWeight = totleWeight.add(testEntity.getWeight());
				BigDecimal s = totleScore.multiply(testEntity.getWeight())
						.divide(div, 4);
				if (testEntity.getScore().compareTo(s) != 0) {// 计算分数如果不同 就更新
					testEntity.setScore(s);
					iScoreIndexService.updateNotNull(testEntity);
				}
				updateChildList(list, testEntity.getId(), s, level + "->"
						+ testEntity.getName());
			}
			if (totleWeight.compareTo(div) != 0) {
				throw new RuntimeBusinessException(level + "：下级指标权重之和为  （"
						+ totleWeight + "）未达到100，请检查！");
			}
		}
		return reList;
	}

	@Override
	public void importAnswer(Attachment attachment, Integer paperId,
			Integer userId) {
		// try{
		String path = attachment.getAttachmentPath();
		File file = new File(path);
		List<List<Object>> list = null;
		try {
			list = ExcelReaderNull.readExcel(file);
			// print(list);
			// int orgKeyIndex = 0;
			int itemKeyIndex = 0;
			int valueLengthIndex = 0;
			int valueIndex = 0;
			int groupIndex = 0;
			List<Object> titleList = list.get(0);
			for (int i = 0; i < titleList.size(); i++) {
				if (titleList.get(i).equals("key")
						|| titleList.get(i).equals("itemId")) {
					itemKeyIndex = i;
				}
				if (titleList.get(i).equals("group")) {
					groupIndex = i;
				}
				if (titleList.get(i).equals("数据数量")
						|| titleList.get(i).equals("count")) {
					valueLengthIndex = i;
				}
				if (titleList.get(i).equals("数据1")
						|| titleList.get(i).equals("data1")) {
					valueIndex = i;
				}
			}
			// System.out.println("itemKeyIndex:"+itemKeyIndex+"   valueLengthIndex:"+valueLengthIndex+"   valueIndex:"+valueIndex);
			for (int j = 1; j < list.size(); j++) {
				List<Object> datalist = list.get(j);
				if (datalist.size() <= valueIndex)
					continue;
				// System.out.println(datalist.get(itemKeyIndex).toString());
				if (datalist.get(itemKeyIndex) == null
						|| datalist.get(itemKeyIndex).equals(""))
					continue;
				Integer itemId = Integer.valueOf(datalist.get(itemKeyIndex)
						.toString());
				// System.out.println(datalist.get(valueLengthIndex).toString());
				if (datalist.get(valueLengthIndex) == null
						|| datalist.get(valueLengthIndex).equals(""))
					continue;
				Integer dataLength = Integer.valueOf(datalist.get(
						valueLengthIndex).toString());
				StringBuilder value = new StringBuilder("");
				for (int k = 0; k < (dataLength == -1 ? datalist.size()
						- valueIndex : dataLength); k++) {
					value.append(","
							+ datalist.get(valueIndex + k).toString()
									.replace(",", "，"));
				}
				if (dataLength == -1) {// 去掉末尾的空
					String v = value.toString().replaceAll(",+$", "");
					try {
						int groupCount = Integer.valueOf(datalist.get(
								groupIndex).toString());
						int ned = v.split(",").length % groupCount;
						if (ned != 0) {
							for (int i = ned; i < groupCount; i++) {
								v = v + ",";
							}
						}
						value = new StringBuilder(v);
					} catch (Exception e) {
					}
				}
				ScoreAnswer sa = new ScoreAnswer();
				sa.setUserId(userId);
				sa.setPaperId(paperId);
				sa.setItemId(itemId);
				List<ScoreAnswer> listAw = iScoreAnswerService
						.selectByFilter(sa);
				if (listAw.size() > 0) {
					for (ScoreAnswer scoreAnswer : listAw) {
						iScoreAnswerService.delete(scoreAnswer.getId());
					}
				}
				ScoreItem scoreItem = iScoreItemService.selectByKey(itemId);
				sa.setItemScore(scoreItem.getScore());
				sa.setAnswerValue(value.toString().substring(1));
				iScoreAnswerService.divScore(scoreItem, sa);
				iScoreAnswerService.saveNotNull(sa);
			}
		} catch (IOException e) {
			throw new DefaultBusinessException(e.getMessage());
		}
		/*
		 * }catch(Exception e){ e.printStackTrace(); throw new
		 * RuntimeBusinessException("附件错误！"); }
		 */

	}

	private void print(List<List<Object>> list) {
		for (List<Object> ls : list) {
			System.out.println();
			int i = 0;
			for (Object e : ls) {
				System.out.print(i++ + "-" + e + "\t");
			}
		}

	}

	/*
	 * public static void main(String[] args) { Attachment attachment = new
	 * Attachment(); attachment .setAttachmentPath(
	 * "D:\\app_note\\qita\\中国科协\\数据\\答案导入数据\\查询结果(汇总wd)终 - 副本.xls");
	 * ScorePaperServiceImpl q = new ScorePaperServiceImpl();
	 * q.importAnswer(attachment, 2); }
	 */

	@Override
	public void importAnswer(Attachment attachment, Integer paperId) {
		Map<String, Integer> org_user = new HashMap<String, Integer>();
		String path = attachment.getAttachmentPath();
		File file = new File(path);
		List<List<Object>> list = null;
		BigDecimal ling = new BigDecimal(0);
		try {
			List key = new ArrayList<String>();
			list = ExcelReaderNull.readExcel(file);
			for (int i = 0; i < list.size(); i++) {
				if (i == 0) {
					List<Object> itemIds = list.get(0);
					List<Object> regs = list.get(1);
					List<Object> codes = list.get(2);
					List<Object> titles = list.get(3);
					for (int j = 0; j < itemIds.size() && j < regs.size()
							&& j < codes.size(); j++) {
						if (itemIds.get(j).equals("")) {
							continue;
						}
						key.add(itemIds.get(j));
						key.add(regs.get(j));
						key.add(codes.get(j));
						key.add(j);
						key.add(titles.get(j));
						// System.out.println(itemIds.get(j)+"==="+j+"=="+regs.get(j)+"==="+codes.get(j));
					}
					i = 3;
				} else {
					List<Object> row = list.get(i);
					int k = i;
					String perOrg = null;
					int perOrgIndex = 0;
					for (int j = 0; j < key.size(); j += 5) {
						String itemId = key.get(j).toString();
						String reg = key.get(j + 1).toString();
						int index = Integer.valueOf(key.get(j + 3).toString());
						String content = row.get(index).toString()
								.replace(",", "，");
						String title = key.get(j + 4).toString();
						if (itemId.equals("itemId")) {
							perOrg = content;
							perOrgIndex = Integer.valueOf(key.get(j + 3)
									.toString());
						}
						// System.out.println(itemId+":"+reg+":"+title+":-==="+index+"==="+content);
						System.out.println("i:" + i);
						for (k = i + 1; k < list.size(); k++) {
							List<Object> crow = list.get(k);
							if (crow.get(perOrgIndex).equals("")) {
								String thiscontent = crow.get(index).toString()
										.replace(",", "，");
								if (!thiscontent.equals("")) {
									content += "," + thiscontent;
								}
								// System.out.println(content);
							} else {
								System.out.println("k:" + (k - 1));
								break;
							}
						}
						if (!itemId.equals("itemId")) {
							insertAnswer(perOrg, paperId, itemId, reg, content,
									title, org_user, ling);
							// break;
						}

					}
					i = k - 1;
				}
			}
		} catch (Exception e) {
			System.out.println("导入错误");
			e.printStackTrace();
		}

	}

	private void insertAnswer(String code, Integer paperId, String itemIdStr,
			String reg, String content, String title, Map<String, Integer> map,
			BigDecimal ling) {
		Integer itemId;
		System.out.println("机构编码:" + code + "，题目ID:" + itemIdStr + "，填空规则:"
				+ reg + "，内容:" + content + "，数据标题:" + title);
		try {
			itemId = Integer.valueOf(itemIdStr);
			ScoreAnswer sa = new ScoreAnswer();
			Integer userId = map.get(code);
			if (userId == null) {
				String userLoginName = code.trim() + "002";
				User user = userService.findUserByLoginName(userLoginName);
				if (user == null)
					System.out.println("查找用户失败:" + userLoginName);
				userId = user.getId();
				map.put(code, userId);
			}
			sa.setUserId(userId);
			sa.setPaperId(paperId);
			sa.setItemId(itemId);
			List<ScoreAnswer> listAw = iScoreAnswerService.selectByFilter(sa);
			if (listAw.size() > 0) {
				/*
				 * for (ScoreAnswer scoreAnswer : listAw) {
				 * iScoreAnswerService.delete(scoreAnswer.getId()); }
				 */
				sa = listAw.get(0);

			}
			String[] regs = reg.split("_");
			String values = sa.getAnswerValue();
			if (Integer.valueOf(regs[1]) != 0) {// 限制了长度
				if (values != null) {
					String[] vs = getArr(values);
					for (int i = vs.length; i < Integer.valueOf(regs[1]); i++) {
						values += ",";
					}
				} else {
					values = "";
					for (int i = 1; i < Integer.valueOf(regs[1]); i++) {
						values += ",";
					}
				}
				String[] vs = getArr(values);
				int step = 1;
				if (regs.length == 3) {// 如果是组填空
					step = Integer.valueOf(regs[2]);
					String[] contents = getArr(content);
					int start = Integer.valueOf(regs[0]) - 1;
					for (int i = start; i < vs.length
							&& (i - start) / step < contents.length; i += step) {
						vs[i] = contents[(i - start) / (step)];
					}
				} else {
					int start = Integer.valueOf(regs[0]) - 1;
					if (vs.length > start) {
						vs[start] = content;
					}
				}
				values = getString(vs);
			} else {// 无限长度
				if (values != null) {
					if (regs.length == 3) {// 如果是组填空
						String[] vs = getArr(values);
						int start = Integer.valueOf(regs[0]) - 1;
						int step = Integer.valueOf(regs[2]);
						String[] contents = getArr(content);
						for (int i = start; i < vs.length
								&& (i - start) / step < contents.length; i += step) {
							vs[i] = contents[(i - start) / (step)];
						}
						values = getString(vs);
					} else {
						String[] vs = getArr(values);
						if (vs.length > Integer.valueOf(regs[0])) {
							vs[Integer.valueOf(regs[0]) - 1] = content;
						}
						values = getString(vs);
					}
				} else {
					values = "";
					for (int i = 1; i < Integer.valueOf(regs[0]); i++) {
						values += ",";
					}
					if (regs.length == 3) {// 如果是组填空
						int step = Integer.valueOf(regs[2]);
						String[] contents = getArr(content);
						String[] vs = new String[contents.length * step];
						for (int i = 0; i < contents.length * step; i += step) {
							vs[i] = contents[i / step];
						}
						content = getString(vs);
					}
					values = values + content;
				}
			}
			ScoreItem scoreItem = iScoreItemService.selectByKey(itemId);
			sa.setItemScore(scoreItem.getScore());
			System.out.println(values);
			sa.setAnswerValue(values);
			sa.setIndexId(scoreItem.getIndexId());
			sa.setAnswerScore(ling);
			// iScoreAnswerService.divScore(scoreItem, sa);
			if (sa.getId() == null) {
				sa.setAnswerReal(true);
				sa.setAnswerReason("");
				iScoreAnswerService.save(sa);
			} else
				iScoreAnswerService.updateNotNull(sa);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	private String[] getArr(String values) {
		List<String> list = new ArrayList<String>();
		do {
			int index = values.indexOf(",");
			if (index == -1) {
				list.add(values);
				break;
			}
			if (index == 0) {
				list.add("");
				values = values.substring(1);
			} else {
				list.add(values.substring(0, index));
				values = values.substring(index + 1);
			}
		} while (true);
		String[] vs = new String[list.size()];
		int i = 0;
		for (String s : list) {
			vs[i++] = s;
		}
		return vs;

	}

	private String getString(String[] vs) {
		StringBuilder s = new StringBuilder("");
		for (String a : vs) {
			s.append("," + a);
		}
		if (s.length() == 0)
			return "";
		return s.toString().substring(1);
	}

	@Override
	public XSSFWorkbook exportEPScore(Integer paperId, String orgIds,String scoreType) {
		ScorePaper p = this.getMapper().selectByPrimaryKey(paperId);
		if (p == null)
			throw new RuntimeBusinessException("paperId不能为空");
		Example example = new Example(Dept.class);
		List<Dept> depts = new ArrayList<Dept>();
		if (orgIds != null && !orgIds.equals("")) {
			example.createCriteria().andIn("id",
					Arrays.asList(orgIds.split(",")));
			depts = deptService.selectByExample(example);
		}
		ScoreItem scoreItem = new ScoreItem();
		scoreItem.setScoreType(scoreType);//专家评分
		List<ScoreItem> list = this.iScoreItemService.selectByFilter(paperId, scoreItem);
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet("sheet1");
		sheet.autoSizeColumn(1);// 设置每个单元格宽度根据字多少自适应
		exportItems(sheet,list,depts);
		return wb;
	}

	private void exportItems(XSSFSheet sheet, List<ScoreItem> list,
			List<Dept> depts) {
		int rownum=0;
		XSSFRow titleRow = sheet.createRow(rownum++);
		XSSFRow titleScoreRow = sheet.createRow(rownum++);
		for(int i=0;i<list.size();i++){
			titleRow.createCell(2*(i+1));
			titleRow.createCell(2*(i+1)+1).setCellValue(list.get(i).getTitle());
			titleScoreRow.createCell(2*(i+1));
			titleScoreRow.createCell(2*(i+1)+1).setCellValue(list.get(i).getScore().toString());
		}
		for(Dept dept:depts){
			XSSFRow dataRow = sheet.createRow(rownum++);
			XSSFCell cell = dataRow.createCell(0);
			cell.setCellValue(dept.getCode());
			XSSFCell cell2 = dataRow.createCell(1);
			cell2.setCellValue(dept.getName());
			Integer userId= userService.findUserByLoginName(dept.getCode()+"002").getId();
			int max = 0;
			XSSFRow maxRow = sheet.createRow(rownum++),
					minRow = sheet.createRow(rownum++),
					avgWRow = sheet.createRow(rownum++),
					avgRow = sheet.createRow(rownum++);
			
			for(int i=0;i<list.size();i++){
				ScoreItem item = list.get(i);
				ScoreAnswer answer = new ScoreAnswer();
				answer.setUserId(userId);
				answer.setItemId(item.getId());
				List<ScoreAnswer> las = this.iScoreAnswerService.selectByFilter(answer);
				if(las.size()>0){
					dataRow.createCell(2*(i+1)).setCellValue("得分");
					dataRow.createCell(2*(i+1)+1).setCellValue(las.get(0).getAnswerScore().toString());
				}
				BigDecimal itemScore =item.getScore();
				List<Map> values = iExpertItemScoreService.selectScoreInfo(userId,item.getId()+"");
				BigDecimal maxScore=null,minScore=null,avgScore=null,avgScoreW=null,totalScore=new BigDecimal("0");
				
				for(int j=0;j<values.size();j++){
					Map recored = values.get(j);
					BigDecimal score = new BigDecimal(recored.get("item_score").toString());
					if(maxScore==null||maxScore.compareTo(score)<0){
						maxScore = score;
					}
					if(minScore==null||minScore.compareTo(score)>0){
						minScore = score;
					}
					totalScore = totalScore.add(score);
					XSSFRow row =null;
					if(j+1>max){
						max++;
						row = sheet.createRow(rownum+max);
					}else{
						row = sheet.getRow(rownum+j+1);
					}
					row.createCell(2*(i+1)).setCellValue((String)recored.get("display_name"));
					row.createCell(2*(i+1)+1).setCellValue(recored.get("item_score").toString());
				}
				int count = values.size();
				if(values.size()>3){//如果超过三个专家则 去掉最高分最低分
					totalScore = totalScore.subtract(maxScore).subtract(minScore);
					count = count-2;
					maxRow.createCell(2*(i+1)).setCellValue("最大值");
					maxRow.createCell(2*(i+1)+1).setCellValue(maxScore.toString());
					minRow.createCell(2*(i+1)).setCellValue("最小值");
					minRow.createCell(2*(i+1)+1).setCellValue(minScore.toString());
				}
				if(values.size()!=0){
					avgWRow.createCell(2*(i+1)).setCellValue("平局值");
					avgRow.createCell(2*(i+1)).setCellValue("平均分");
					if(totalScore.compareTo(new BigDecimal("0"))==0){
						avgWRow.createCell(2*(i+1)+1).setCellValue(totalScore.toString());
						avgRow.createCell(2*(i+1)+1).setCellValue(totalScore.toString());
						
					}else{
						avgScoreW = totalScore.divide( new BigDecimal(count),6);
						//=======修改为百分比打分=======
						avgScore = itemScore.multiply(avgScoreW).divide(new BigDecimal(100), 4);
						avgWRow.createCell(2*(i+1)+1).setCellValue(avgScoreW.toString());
						avgRow.createCell(2*(i+1)+1).setCellValue(avgScore.setScale(4,BigDecimal.ROUND_UP).toString());
					}
				}
			}
			rownum += max+1;
		}
		
		
	}

	@Override
	public XSSFWorkbook exportEPNotFinished(Integer paperId) {
		ScorePaper p = this.getMapper().selectByPrimaryKey(paperId);
		if (p == null)
			throw new RuntimeBusinessException("paperId不能为空");
		
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet("sheet1");
		sheet.autoSizeColumn(1);// 设置每个单元格宽度根据字多少自适应
		exportItems(sheet,paperId);
		return wb;
	}

	private void exportItems(XSSFSheet sheet, Integer paperId) {
		List<Map> list = this.expertDeptUserMapper.selectExportItemMap(paperId,null);
		ArrayList<Map> allDept =this.dScorePaperUserMapper.selectAllCommit(paperId);
		int rownum=0;
		String type="";
		String itemId="";
		String zhuanjian = "";
		Integer lastExpertUserId = 0;
		XSSFRow head = sheet.createRow(rownum++);
		head.createCell(0).setCellValue("专业领域");
		head.createCell(1).setCellValue("指标");
		head.createCell(2).setCellValue("专家名称");
		head.createCell(3).setCellValue("未完成个数");
		head.createCell(4).setCellValue("未完成学会名称");
		Set<Map> set = new HashSet<Map>();
		int c = 0;
		for(Map<String,Object> map:list){
			c++;
			//String indexCollExpertId = map.get("indexCollExpertId").toString();
			String realname = map.get("real_name").toString();
			Integer expertUserId = Integer.valueOf(map.get("user_id").toString());
			String relatedType = map.get("related_field").toString();
			//更新评分状态
			if((c!=1&&!realname.equals(zhuanjian))){
				updateStatus(set,null,lastExpertUserId,paperId);
				set= new HashSet<Map>();
			}
			zhuanjian = realname;
			lastExpertUserId = expertUserId;
			//更新评分状态结束
			
			XSSFRow titleRow = sheet.createRow(rownum++);
			if(!type.equals(relatedType)){
				titleRow.createCell(0).setCellValue(relatedType);
				type=relatedType;
			}
			String item_id = map.get("id").toString();
			String title = map.get("title").toString();
			if(!itemId.equals(item_id)){
				titleRow.createCell(1).setCellValue(title);
				itemId=item_id;
			}
			//List<Map> depts = this.expertDeptUserMapper.selectNotFinishedDept(paperId, Integer.valueOf(itemId), expertUserId);
			List<Map> depts = getiNotFinishedDept(paperId, Integer.valueOf(itemId), expertUserId,allDept);
			set.addAll(depts);
			String names = getNames(depts);
			titleRow.createCell(2).setCellValue(realname);
			titleRow.createCell(3).setCellValue(depts.size());
			titleRow.createCell(4).setCellValue(names);
			//更新评分状态  最后一个
			if(c==list.size()){
				updateStatus(set,null,expertUserId,paperId);
				set= new HashSet<Map>();
			}
			//更新评分状态结束
		}
	}
	
	private List<Map> getiNotFinishedDept(Integer paperId, Integer itemId,
			Integer expertUserId, ArrayList<Map> allDept) {
		ArrayList<Map> a = (ArrayList<Map>) allDept.clone();
		List<Map> depts = this.expertDeptUserMapper.selectFinishedDept(paperId, itemId, expertUserId);
		if(depts.size()==0)return a;
		if( a.removeAll(depts)){
			return a;
		}
		return a;
	}

	@Override
	public List<Map> getiNotFinishedDept(PagerUserDto pagerUserDto) {
		Integer userId = SecurityUtil.getCurrentUserId();
		Integer paperId = pagerUserDto.getPaperId();
		if(paperId==null){
			PaperExpertConf paperExpertConf = new PaperExpertConf();
			paperExpertConf.setStatus(1);
	        List<PaperExpertConf> pageInfo = iPaperExpertConfService.selectByFilter(paperExpertConf);
	       	if(pageInfo.size()>0)
	       		paperId =  pageInfo.get(0).getPaperId();
	       	else
	       		paperId = 0;//表示没有
				
		}
		List<Map> list = this.expertDeptUserMapper.selectExportItemMap(paperId, userId);
		ArrayList<Map> allDept =this.dScorePaperUserMapper.selectAllCommit(paperId);
		for(Map m:list){
			Integer itemId = Integer.valueOf(m.get("id").toString());
			List<Map> depts = getiNotFinishedDept(paperId, itemId, userId, allDept);
			m.put("list", depts);
		}
		return list;
	}
	@Override
	public List<Map> getiNotFinishedDeptColl(PagerUserDto pagerUserDto) {
		Integer userId = SecurityUtil.getCurrentUserId();
		Integer paperId = pagerUserDto.getPaperId();
		if(paperId==null){
			PaperExpertConf paperExpertConf = new PaperExpertConf();
			paperExpertConf.setStatus(1);
	        List<PaperExpertConf> pageInfo = iPaperExpertConfService.selectByFilter(paperExpertConf);
	       	if(pageInfo.size()>0)
	       		paperId =  pageInfo.get(0).getPaperId();
	       	else
	       		paperId = 0;//表示没有
				
		}
		ScorePaper p = this.mapper.selectByPrimaryKey(paperId);
		List<Map> list = this.expertDeptUserMapper.selectExportItemMap(paperId, userId);
		ArrayList<Map> allDept =this.dScorePaperUserMapper.selectAllCommit(paperId);
		Set<Map> set = new HashSet<Map>();
		for(Map m:list){
			Integer itemId = Integer.valueOf(m.get("id").toString());
			List<Map> depts = getiNotFinishedDept(paperId, itemId, userId, allDept);
			//m.put("list", depts);
			set.addAll(depts);
		}
		for(Map m:set){
			m.put("title", p.getTitle());
			m.put("id", paperId);
			m.put("userName", m.get("display_name"));
		}
		List<Map> l = new ArrayList(set);
		Collections.sort(l,new Comparator<Map>(){

			@Override
			public int compare(Map arg0, Map arg1) {
				return arg0.get("login_name").toString().compareTo(arg1.get("login_name").toString());
			}
			
		});
		return l;
	}
	private void updateStatus(Set<Map> depts, String indexCollExpertId,
			Integer expertUserId, Integer paperId) {
		Set<Integer> set = new HashSet<Integer>();
		for(Map m: depts){
			set.add(Integer.valueOf(m.get("userId").toString()));
		}
		if(depts.size()==0){
			this.expertDeptUserMapper.updateExpertUserIncludeStatus(paperId,indexCollExpertId,expertUserId,null,"1");
			return ;
		}
		this.expertDeptUserMapper.updateExpertUserIncludeStatus(paperId,indexCollExpertId,expertUserId,set,"0");
		this.expertDeptUserMapper.updateExpertUserExcludeStatus(paperId,indexCollExpertId,expertUserId,set,"1");
		
		
		
	}

	private String getNames(List<Map> depts) {
		if(depts.size()==0)return "";
		StringBuilder s = new StringBuilder("");
		for(Map m:depts){
			s.append(","+m.get("display_name"));
		}
		return s.toString().substring(1);
	}

	@Override
	public PageInfo<ScorePaper> selectCurrentUserHistoryPaper(
			ScorePaper scorePaper, int pageNum, int pageSize) {
		SecurityUser user = SecurityUtil.getCurrentSecurityUser();
		Integer type = user.getUserType();
		// 不是科协用户
		if (type != 7) {
			return new PageInfo<ScorePaper>();
		}
		Integer userId = user.getId();
		if(!user.getLoginName().endsWith("002")){
			String userLoginName = user.getLoginName().substring(0,user.getLoginName().length()-3)+"002";
			User cuser = userService.findUserByLoginName(userLoginName);
			if (cuser == null){
				System.out.println("查找用户失败:" + userLoginName);
			}else{
				userId = cuser.getId();
			}
			
		}
		PageHelper.startPage(pageNum, pageSize);
		List<ScorePaper> list = dScorePaperUserMapper.selectHistoryPaperByUserId(userId);
		
		return new PageInfo<ScorePaper>(list);
	}


}
