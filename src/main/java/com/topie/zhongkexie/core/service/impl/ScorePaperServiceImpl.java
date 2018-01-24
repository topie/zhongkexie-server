package com.topie.zhongkexie.core.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.topie.zhongkexie.common.baseservice.impl.BaseService;
import com.topie.zhongkexie.core.dto.ItemDto;
import com.topie.zhongkexie.core.dto.OptionDto;
import com.topie.zhongkexie.core.dto.PagerUserDto;
import com.topie.zhongkexie.core.dto.PaperIndexDto;
import com.topie.zhongkexie.core.exception.RuntimeBusinessException;
import com.topie.zhongkexie.core.service.IDeptService;
import com.topie.zhongkexie.core.service.IScoreAnswerService;
import com.topie.zhongkexie.core.service.IScoreIndexService;
import com.topie.zhongkexie.core.service.IScoreItemOptionService;
import com.topie.zhongkexie.core.service.IScoreItemService;
import com.topie.zhongkexie.core.service.IScorePaperService;
import com.topie.zhongkexie.database.core.dao.ScorePaperUserMapper;
import com.topie.zhongkexie.database.core.model.Dept;
import com.topie.zhongkexie.database.core.model.ScoreAnswer;
import com.topie.zhongkexie.database.core.model.ScoreIndex;
import com.topie.zhongkexie.database.core.model.ScoreItem;
import com.topie.zhongkexie.database.core.model.ScoreItemOption;
import com.topie.zhongkexie.database.core.model.ScorePaper;
import com.topie.zhongkexie.database.core.model.User;
import com.topie.zhongkexie.security.service.UserService;

/**
 * Created by chenguojun on 2017/4/19.
 */
@Service
public class ScorePaperServiceImpl extends BaseService<ScorePaper> implements IScorePaperService {

    @Autowired
    private IScoreIndexService iScoreIndexService;

    @Autowired
    private IScoreItemService iScoreItemService;

    @Autowired
    private IScoreItemOptionService iScoreItemOptionService;
    
    @Autowired
    private ScorePaperUserMapper dScorePaperUserMapper;

    @Autowired
    private IDeptService deptService;

    @Autowired
    private IScoreAnswerService iScoreAnswerService;
    @Autowired
    private UserService userService;

    @Override
    public PageInfo<ScorePaper> selectByFilterAndPage(ScorePaper scorePaper, int pageNum, int pageSize) {
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
         if(scorePaper.getStatus()!=null)criteria.andEqualTo("status",scorePaper.getStatus());
         List ls = new ArrayList();
         ls.add(PagerUserDto.PAPERSTATUS_SUBMMIT);
         ls.add(PagerUserDto.PAPERSTATUS_EGIS);
         criteria.andIn("approveStatus",ls);
         if (StringUtils.isNotEmpty(scorePaper.getTitle())) criteria.andLike("title", "%" + scorePaper.getTitle() + "%");
         if (StringUtils.isNotEmpty(scorePaper.getSortWithOutOrderBy()))
             example.setOrderByClause(scorePaper.getSortWithOutOrderBy());
         List<ScorePaper> list  = getMapper().selectByExample(example);
         
         return new PageInfo<>(list);
	}


    @Override
    public List<ScorePaper> selectByFilter(ScorePaper scorePaper) {
        Example example = new Example(ScorePaper.class);
        Example.Criteria criteria = example.createCriteria();
        if(scorePaper.getStatus()!=null)criteria.andEqualTo("status",scorePaper.getStatus());
        if(scorePaper.getApproveStatus()!=null)criteria.andEqualTo("approveStatus",scorePaper.getApproveStatus());
        if (StringUtils.isNotEmpty(scorePaper.getTitle())) criteria.andLike("title", "%" + scorePaper.getTitle() + "%");
        if (StringUtils.isNotEmpty(scorePaper.getSortWithOutOrderBy()))
            example.setOrderByClause(scorePaper.getSortWithOutOrderBy());
        return getMapper().selectByExample(example);
    }

    @Override
    public String getContentJson(Integer paperId,String title) {
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
            List<ScoreItem> scoreItems = iScoreItemService.selectByFilter(scoreItem);
            for (ScoreItem item : scoreItems) {
                ItemDto itemDto = new ItemDto();
                itemDto.setId(item.getId());
                itemDto.setTitle(item.getTitle());
                itemDto.setScore(item.getScore());
                itemDto.setItemType(item.getType());
                List<OptionDto> itemOptions = new ArrayList<>();
                ScoreItemOption scoreItemOption = new ScoreItemOption();
    	        scoreItemOption.setSort_("option_sort_asc");
    	        scoreItemOption.setItemId(item.getId());
    	        List<ScoreItemOption> scoreItemOptions = iScoreItemOptionService.selectByFilter(scoreItemOption);
                for (ScoreItemOption itemOption : scoreItemOptions) {
                    OptionDto optionDto = new OptionDto();
                    optionDto.setId(itemOption.getId());
                    optionDto.setTitle(itemOption.getOptionTitle());
                    itemOptions.add(optionDto);
                }
                itemDto.setItems(itemOptions);
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

    @Override
    public void check(int id, short result) {
        ScorePaper page = getMapper().selectByPrimaryKey(id);
        page.setApproveStatus(result);
        getMapper().updateByPrimaryKey(page);
    }

    private String getParentIndexString(String path, ScoreIndex childIndex, List<ScoreIndex> indices) {
        Integer parentId = childIndex.getParentId();
        for (ScoreIndex index : indices) {
            if (index.getId().intValue() == parentId) {
                path = index.getName() + ">" + path;
                path = getParentIndexString(path, index, indices);
            }
        }
        return path;
    }


	@Override
	public JSONObject getContentJson(Integer paperId, String indexIds, String orgIds) {
		ScorePaper p = this.getMapper().selectByPrimaryKey(paperId);
		if(p==null)return new JSONObject();
		String title = p.getTitle();
		 List<ScoreIndex> childIndices = new ArrayList<>();
	        Example ex = new Example(ScoreIndex.class);
	        Criteria c = ex.createCriteria();
	        c.andEqualTo("paperId", paperId);
	        List list = Arrays.asList(indexIds.split(","));
	        c.andIn("id",list);
	        List<ScoreIndex> indices = iScoreIndexService.selectByExample(ex);
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
	            List<ScoreItem> scoreItems = iScoreItemService.selectByFilter(scoreItem);
	            for (ScoreItem item : scoreItems) {
	                ItemDto itemDto = new ItemDto();
	                itemDto.setId(item.getId());
	                itemDto.setTitle(item.getTitle());
	                itemDto.setScore(item.getScore());
	                itemDto.setItemType(item.getType());
	                List<OptionDto> itemOptions = new ArrayList<>();
	                ScoreItemOption scoreItemOption = new ScoreItemOption();
	    	        scoreItemOption.setSort_("option_sort_asc");
	    	        scoreItemOption.setItemId(item.getId());
	    	        List<ScoreItemOption> scoreItemOptions = iScoreItemOptionService.selectByFilter(scoreItemOption);
	                for (ScoreItemOption itemOption : scoreItemOptions) {
                        OptionDto optionDto = new OptionDto();
                        optionDto.setId(itemOption.getId());
                        optionDto.setTitle(itemOption.getOptionTitle());
                        itemOptions.add(optionDto);
	                }
	                itemDto.setItems(itemOptions);
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


	@Override
	public int saveNotNull(ScorePaper scorePaper, Integer copyPaperId) {
		int result = this.saveNotNull(scorePaper);
		int paperId = scorePaper.getId();
		ScoreIndex scoreIndex = new ScoreIndex();
		scoreIndex.setPaperId(copyPaperId);
		scoreIndex.setParentId(0);
		List<ScoreIndex> indexList = iScoreIndexService.selectByFilter(scoreIndex);
		for(ScoreIndex index:indexList){
			Integer oldid = index.getId();
			index.setId(null);
			index.setPaperId(paperId);
			iScoreIndexService.saveNotNull(index);
			saveChildIndex(paperId,index.getId(),oldid);
			
		}
		scorePaper.setContentJson(getContentJson(scorePaper.getId(),scorePaper.getTitle()));
		this.updateNotNull(scorePaper);
		return result;
	}

	/**
	 * 复制指标和题目
	 * @param paperId
	 * @param newParentId 
	 * @param oldParentId
	 */
	private void saveChildIndex(Integer paperId,Integer newParentId, Integer oldParentId) {
		ScoreIndex scoreIndex = new ScoreIndex();
		scoreIndex.setParentId(oldParentId);
		List<ScoreIndex> indexList = iScoreIndexService.selectByFilter(scoreIndex);
		for(ScoreIndex index:indexList){
			Integer oldid = index.getId();
			index.setId(null);
			index.setPaperId(paperId);
			index.setParentId(newParentId);
			iScoreIndexService.saveNotNull(index);
			saveChildIndex(paperId,index.getId(),oldid);
			
		}
		if(indexList.size()==0){//如果是最底层节点
			ScoreItem scoreItem = new ScoreItem();
			scoreItem.setIndexId(oldParentId);
			List<ScoreItem> itemList = iScoreItemService.selectByFilter(scoreItem);//查询题目
			for(ScoreItem item:itemList){
				Integer oldItemId = item.getId();
				item.setId(null);
				item.setIndexId(newParentId);
				iScoreItemService.saveNotNull(item);
				Integer newItemId = item.getId();
				ScoreItemOption scoreItemOption = new ScoreItemOption();
				scoreItemOption.setItemId(oldItemId);
				List<ScoreItemOption> optionList = iScoreItemOptionService.selectByFilter(scoreItemOption);
				for(ScoreItemOption option:optionList){//查询选项
					option.setId(null);
					option.setItemId(newItemId);
					iScoreItemOptionService.saveNotNull(option);
				}
			}
		}
		
	}


	@Override
	public HSSFWorkbook exportPaper(Integer paperId, String indexIds, String orgIds) {
		ScorePaper p = this.getMapper().selectByPrimaryKey(paperId);
		if(p==null) throw new RuntimeBusinessException("paperId不能为空");
		String title = p.getTitle();
		Example ex = new Example(ScoreIndex.class);
        Criteria c = ex.createCriteria();
        c.andEqualTo("paperId", paperId);
        List list = Arrays.asList(indexIds.split(","));
        c.andIn("id",list);
        List<ScoreIndex> indexList = iScoreIndexService.selectByExample(ex);
        List<ScoreIndex> indexParentList = new ArrayList<ScoreIndex>();
        List<ScoreIndex> indexChildList = new ArrayList<ScoreIndex>();
        for(ScoreIndex index:indexList){
        	if(index.getParentId()==0){
        		indexParentList.add(index);
        	}else{
        		indexChildList.add(index);
        	}
        }
		Example example = new Example(Dept.class);
		example.createCriteria().andIn("id", Arrays.asList(orgIds.split(",")));
		List<Dept> depts = deptService.selectByExample(example);
		String[] indexTitle = "一级指标,,二级指标,,三级指标,,四级指标,,五级指标,,六级指标,,七级指标,,八级指标,".split(",");
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("sheet1");
		sheet.autoSizeColumn(1);// 设置每个单元格宽度根据字多少自适应
		HSSFCellStyle style = getStyle(wb);
		int row = 0;//行
		for(ScoreIndex index:indexParentList){
			int col = 0;//列
			row  = exportIndex(sheet,index,indexTitle,depts,paperId,row,col,indexChildList);
		}
		List<Integer> collist =getNeedMergeCell(sheet);
		mergeCell(sheet,collist,style);
		return wb;
	}


	private void mergeCell(HSSFSheet sheet, List<Integer> collist, HSSFCellStyle style) {
		//获得总列数
		int maxRowNum=sheet.getLastRowNum();//获得总行数
		for(int j:collist){
			int startRow = 1;
			String startValue = "";
			for(int rowIndex=1;rowIndex<=maxRowNum;rowIndex++){
				HSSFCell cell = sheet.getRow(rowIndex).getCell(j);
				if(cell==null){
					if(rowIndex==maxRowNum && startRow<(rowIndex-1)){
						CellRangeAddress region = new CellRangeAddress(startRow, rowIndex-1, j, j); //参数1：起始行 参数2：终止行 参数3：起始列 参数4：终止列   
						sheet.addMergedRegion(region);  
					}
					continue;
				}
				cell.setCellStyle(style);
				String value = getCellValue(cell);
				if(StringUtils.isNotEmpty(value)){
					if(startRow<rowIndex-1){
						CellRangeAddress region = new CellRangeAddress(startRow, rowIndex-1, j, j); //参数1：起始行 参数2：终止行 参数3：起始列 参数4：终止列   
						sheet.addMergedRegion(region);  
					}
					startValue = value;
					startRow = rowIndex;
				}
				if(rowIndex==maxRowNum && startRow<(rowIndex-1)){
					CellRangeAddress region = new CellRangeAddress(startRow, rowIndex-1, j, j); //参数1：起始行 参数2：终止行 参数3：起始列 参数4：终止列   
					sheet.addMergedRegion(region);  
				}
			}
			
		}
		
	}


	private List<Integer> getNeedMergeCell(HSSFSheet sheet) {
		List<Integer> collist = new ArrayList();
		for(int i=0;;i++){
			String value = sheet.getRow(0).getCell(i).getStringCellValue();
			if(value.endsWith("指标")){
				collist.add(i);
			}
			if(value.equals("分值")){
				collist.add(i);
			}
			if(value.endsWith("题目")){
				break;
			}
			if(value.endsWith("学会")){
				break;
			}
			if(i>20){
				break;
			}
		}
		return collist;
	}


	private HSSFCellStyle getStyle(HSSFWorkbook wb) {
		HSSFCellStyle style = wb.createCellStyle(); // style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// // 创建一个居中格式
		HSSFFont fontSearch = wb.createFont();
		fontSearch.setFontHeightInPoints((short)12);;// 设置字体大小fontSearch.setFontHeightInPoints((short)
		// 13);
		style.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 居中  
		style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//垂直  
		style.setFont(fontSearch);// 将该字体样式放入style这个样式中，其他单元格样式也是这么加的，这里只是给一个例子
		//style.setBorderBottom(HSSFCellStyle.BORDER_DOUBLE);// 设置单元格下边框
		//style.setBorderLeft(HSSFCellStyle.BORDER_DOUBLE);
		return style;
	}


	private String getCellValue(HSSFCell cell) {

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


	private int exportIndex(HSSFSheet sheet, ScoreIndex index,
			String[] indexTitle,List<Dept> depts,int paperId, int row, int col,List<ScoreIndex> indexChildList) {
		col = writeIndex(sheet,index,row,col,indexTitle);
		boolean f = true;
		for(ScoreIndex one:indexChildList){
			if(index.getId().intValue()==one.getParentId().intValue()){
				row = exportIndex(sheet,one,indexTitle,depts,paperId,row,col,indexChildList);
				f = false;
			}
		}
		if(f){//如果是最底层指标添加题目
			ScoreItem scoreItem = new ScoreItem();
			scoreItem.setIndexId(index.getId());
			List<ScoreItem> items = iScoreItemService.selectByFilter(scoreItem);
			for(ScoreItem item:items){
				writeItem(sheet,item,row,col,depts);
				row++;
			}
		}
		return row;
	}


	private void writeItem(HSSFSheet sheet, ScoreItem item, int row , int col,
			List<Dept> depts) {
		if(row==0){//生成标题
			HSSFRow headRow = sheet.getRow(row)==null?sheet.createRow(row):sheet.getRow(row);
			HSSFRow dataRow = sheet.getRow(row+1)==null?sheet.createRow(row+1):sheet.getRow(row+1);
			headRow.createCell(col).setCellValue("题目");
			headRow.createCell(col+1).setCellValue("分值");
			dataRow.createCell(col).setCellValue(item.getTitle());
			dataRow.createCell(col+1).setCellValue(item.getScore()+"");
			int i = 2;
			for(Dept dept:depts){
				headRow.createCell(col+i).setCellValue(dept.getName());
				ScoreAnswer scoreAnswer = new ScoreAnswer();
				String userLoginName = dept.getCode()+"002";
				User user = userService.findUserByLoginName(userLoginName);
				String value = "";
				if(user!=null){
					scoreAnswer.setItemId(item.getId());
					scoreAnswer.setUserId(user.getId());
					List<ScoreAnswer> answers = iScoreAnswerService.selectByFilter(scoreAnswer);
					for(ScoreAnswer s:answers){
						value+= s.getAnswerValue();
					}
				}
				dataRow.createCell(col+i).setCellValue(value);
				i++;
			}
		}else{
			HSSFRow dataRow = sheet.getRow(row+1)==null?sheet.createRow(row+1):sheet.getRow(row+1);
			dataRow.createCell(col).setCellValue(item.getTitle());
			dataRow.createCell(col+1).setCellValue(item.getScore()+"");
			int i = 2;
			for(Dept dept:depts){
				String userLoginName = dept.getCode()+"002";
				User user = userService.findUserByLoginName(userLoginName);
				String value = "";
				if(user!=null){
					ScoreAnswer scoreAnswer = new ScoreAnswer();
					scoreAnswer.setItemId(item.getId());
					scoreAnswer.setUserId(user.getId());
					List<ScoreAnswer> answers = iScoreAnswerService.selectByFilter(scoreAnswer);
					for(ScoreAnswer s:answers){
						value+= s.getAnswerValue();
					}
				}
				dataRow.createCell(col+i++).setCellValue(value);
			}
		}
		
	}


	private int writeIndex(HSSFSheet sheet, ScoreIndex index, int row,
			int col, String[] indexTitle) {
		if(row==0){//生成标题
			
			HSSFRow headRow = null;
			headRow = sheet.getRow(row)==null?sheet.createRow(row):sheet.getRow(row);
			headRow.createCell(col).setCellValue(indexTitle[col]);
			headRow.createCell(col+1).setCellValue("分值");
			HSSFRow dataRow = sheet.getRow(row+1)==null?sheet.createRow(row+1):sheet.getRow(row+1);
			dataRow.createCell(col).setCellValue(index.getName());
			dataRow.createCell(col+1).setCellValue(index.getScore().intValue());
		}else{
			HSSFRow dataRow = sheet.getRow(row+1)==null?sheet.createRow(row+1):sheet.getRow(row+1);
			dataRow.createCell(col).setCellValue(index.getName());
			dataRow.createCell(col+1).setCellValue(index.getScore().intValue());
		}
		col+=2;
		
		return col;
	}

	
}
