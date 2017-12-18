package com.topie.zhongkexie.database.core.model;

import com.topie.zhongkexie.common.handler.Sortable;

import javax.persistence.*;
import java.math.BigDecimal;

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

    @Column(name = "option_logic")
    private Integer optionLogic;

    /**
     * 题目分值
     */
    private BigDecimal score;

    private Integer sort;

    public Integer getOptionLogic() {
        return optionLogic;
    }

    public void setOptionLogic(Integer optionLogic) {
        this.optionLogic = optionLogic;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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
     * @param id ID
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
     * @param indexId 指标ID
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
     * @param title 题目
     */
    public void setTitle(String title) {
        this.title = title;
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
     * @param score 题目分值
     */
    public void setScore(BigDecimal score) {
        this.score = score;
    }
}
