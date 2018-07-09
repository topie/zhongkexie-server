package com.topie.zhongkexie.database.core.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.topie.zhongkexie.common.handler.Sortable;

@Table(name = "d_score_item_option")
public class ScoreItemOption extends Sortable {

    private static final long serialVersionUID = 7982401157589814274L;

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 题目ID
     */
    @Column(name = "item_id")
    private Integer itemId;

    /**
     * 选项文本
     */
    @Column(name = "option_title")
    private String optionTitle;

    /**
     * 选项系数
     */
    @Column(name = "option_rate")
    private BigDecimal optionRate;

    /**
     * 选项描述
     */
    @Column(name = "option_desc")
    private String optionDesc;

    /**
     * 排序
     */
    @Column(name = "option_sort")
    private Integer optionSort;

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
     * @param id ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取题目ID
     *
     * @return item_id - 题目ID
     */
    public Integer getItemId() {
        return itemId;
    }

    /**
     * 设置题目ID
     *
     * @param itemId 题目ID
     */
    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    /**
     * 获取选项文本
     *
     * @return option_title - 选项文本
     */
    public String getOptionTitle() {
        return optionTitle;
    }

    /**
     * 设置选项文本
     *
     * @param optionTitle 选项文本
     */
    public void setOptionTitle(String optionTitle) {
        this.optionTitle = optionTitle;
    }

    /**
     * 获取选项系数
     *
     * @return option_rate - 选项系数
     */
    public BigDecimal getOptionRate() {
        return optionRate;
    }

    /**
     * 设置选项系数
     *
     * @param optionRate 选项系数
     */
    public void setOptionRate(BigDecimal optionRate) {
        this.optionRate = optionRate;
    }

    /**
     * 获取选项描述
     *
     * @return option_desc - 选项描述
     */
    public String getOptionDesc() {
        return optionDesc;
    }

    /**
     * 设置选项描述
     *
     * @param optionDesc 选项描述
     */
    public void setOptionDesc(String optionDesc) {
        this.optionDesc = optionDesc;
    }

    /**
     * 获取排序
     *
     * @return option_sort - 排序
     */
    public Integer getOptionSort() {
        return optionSort;
    }

    /**
     * 设置排序
     *
     * @param optionSort 排序
     */
    public void setOptionSort(Integer optionSort) {
        this.optionSort = optionSort;
    }

	@Override
	public String toString() {
		return "ScoreItemOption [id=" + id + ", itemId=" + itemId
				+ ", optionTitle=" + optionTitle + ", optionRate=" + optionRate
				+ ", optionDesc=" + optionDesc + ", optionSort=" + optionSort
				+ "]";
	}
    
}
