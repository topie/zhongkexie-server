package com.topie.zhongkexie.database.core.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.topie.zhongkexie.common.handler.Sortable;

@Table(name = "d_score_item")
public class ScoreItem extends Sortable {

	private static final long serialVersionUID = 3203650793511377505L;

	/**
	 * ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 指标ID
	 */
	@Column(name = "index_id")
	private Integer indexId;

	/**
	 * 题目
	 */
	private String title;

	private Integer type;

	@Column(name = "option_logic_desc")
	private String optionLogicDesc;

	@Column(name = "option_logic")
	private String optionLogic;

	@Column(name = "responsible_department")
	private String responsibleDepartment;

	@Column(name = "related_field")
	private String relatedField;

	@Column(name = "score_type")
	private String scoreType;
	@Column(name = "row")
	private Integer row;
	@Column(name = "placeholder")
	private String placeholder;
	@Column(name = "items")
	private String items;
	@Column(name = "show_level")
	private Integer showLevel;
	@Column(name = "hide_btn")
	private Boolean hideBtn;
	private String info;

	/**
	 * 题目权重
	 */
	private BigDecimal weight;

	/**
	 * 题目分值
	 */
	private BigDecimal score;

	private Integer sort;

	public String getOptionLogic() {
		return optionLogic;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public void setOptionLogic(String optionLogic) {
		this.optionLogic = optionLogic;
	}

	/**
	 * 题目回答类型：0 填空 1 单选 2 多选 3填空[多] 4填空[数字] 5单选[可填空] 6多选[可填空]7填空[自定义]
	 * 
	 * @return
	 */
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Boolean getHideBtn() {
		return hideBtn;
	}

	public void setHideBtn(Boolean hideBtn) {
		this.hideBtn = hideBtn;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	/**
	 * 获取ID
	 *
	 * @return id - ID
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 设置ID
	 *
	 * @param id
	 *            ID
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 获取指标ID
	 *
	 * @return index_id - 指标ID
	 */
	public Integer getIndexId() {
		return indexId;
	}

	/**
	 * 设置指标ID
	 *
	 * @param indexId
	 *            指标ID
	 */
	public void setIndexId(Integer indexId) {
		this.indexId = indexId;
	}

	/**
	 * 获取题目
	 *
	 * @return title - 题目
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 设置题目
	 *
	 * @param title
	 *            题目
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 获取题目权重
	 * 
	 * @return
	 */
	public BigDecimal getWeight() {
		return weight;
	}

	/**
	 * 题目权重
	 * 
	 */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	/**
	 * 获取题目分值
	 *
	 * @return score - 题目分值
	 */
	public BigDecimal getScore() {
		return score;
	}

	/**
	 * 设置题目分值
	 *
	 * @param score
	 *            题目分值
	 */
	public void setScore(BigDecimal score) {
		this.score = score;
	}

	public String getResponsibleDepartment() {
		return responsibleDepartment;
	}

	public void setResponsibleDepartment(String responsibleDepartment) {
		this.responsibleDepartment = responsibleDepartment;
	}

	public String getRelatedField() {
		return relatedField;
	}

	public void setRelatedField(String relatedField) {
		this.relatedField = relatedField;
	}

	/**
	 * case "1"://统计项 case "2"://线性打分 case "3"://专家打分
	 * 
	 * @return
	 */
	public String getScoreType() {
		return scoreType;
	}

	public void setScoreType(String scoreType) {
		this.scoreType = scoreType;
	}

	public Integer getRow() {
		return row;
	}

	public void setRow(Integer row) {
		this.row = row;
	}

	public String getPlaceholder() {
		return placeholder;
	}

	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}

	public String getOptionLogicDesc() {
		return optionLogicDesc;
	}

	public void setOptionLogicDesc(String optionLogicDesc) {
		this.optionLogicDesc = optionLogicDesc;
	}

	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
	}

	public Integer getShowLevel() {
		return showLevel;
	}

	public void setShowLevel(Integer showLevel) {
		this.showLevel = showLevel;
	}

	@Override
	public String toString() {
		return "ScoreItem [id=" + id + ", indexId=" + indexId + ", title="
				+ title + ", type=" + type + ", optionLogicDesc="
				+ optionLogicDesc + ", optionLogic=" + optionLogic
				+ ", responsibleDepartment=" + responsibleDepartment
				+ ", relatedField=" + relatedField + ", scoreType=" + scoreType
				+ ", row=" + row + ", placeholder=" + placeholder + ", items="
				+ items + ", showLevel=" + showLevel + ", hideBtn=" + hideBtn
				+ ", weight=" + weight + ", score=" + score + ", sort=" + sort
				+ "]";
	}
	

}
