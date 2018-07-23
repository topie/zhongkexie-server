package com.topie.zhongkexie.core.dto;

import java.math.BigDecimal;
import java.util.List;

public class ItemDto {

	private Integer id;

	private String title;

	private String type = "item";

	private Integer itemType;

	private BigDecimal weight;
	private BigDecimal score;
	private String scoreType;
	private Integer showLevel;
	private Integer row;
	private String placeholder;
	private Boolean hideBtn;
	private String info;
	private Boolean hideUploadFile;

	private List<OptionDto> items;
	private String customItems;

	public Integer getItemType() {
		return itemType;
	}

	public void setItemType(Integer itemType) {
		this.itemType = itemType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Boolean getHideBtn() {
		return hideBtn;
	}

	public void setHideBtn(Boolean hideBtn) {
		this.hideBtn = hideBtn;
	}

	public String getCustomItems() {
		return customItems;
	}

	public void setCustomItems(String customItems) {
		this.customItems = customItems;
	}

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

	public Integer getShowLevel() {
		return showLevel;
	}

	public void setShowLevel(Integer showLevel) {
		this.showLevel = showLevel;
	}

	public List<OptionDto> getItems() {
		return items;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public void setItems(List<OptionDto> items) {
		this.items = items;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BigDecimal getScore() {
		return score;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
	}

	public Boolean getHideUploadFile() {
		return hideUploadFile;
	}

	public void setHideUploadFile(Boolean hideUploadFile) {
		this.hideUploadFile = hideUploadFile;
	}
	
}
