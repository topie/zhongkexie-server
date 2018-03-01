package com.topie.zhongkexie.database.core.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.topie.zhongkexie.common.handler.Sortable;

@Table(name = "d_score_answer")
public class ScoreAnswer extends Sortable {

    private static final long serialVersionUID = 867646103361987015L;

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 试卷ID
     */
    @Column(name = "paper_id")
    private Integer paperId;

    /**
     * 指标ID
     */
    @Column(name = "index_id")
    private Integer indexId;

    /**
     * 题目ID
     */
    @Column(name = "item_id")
    private Integer itemId;

    /**
     * 题目分值
     */
    @Column(name = "item_score")
    private BigDecimal itemScore;

    /**
     * 回答值
     */
    @Column(name = "answer_value")
    private String answerValue;

    /**
     * 答题分值
     */
    @Column(name = "answer_score")
    private BigDecimal answerScore;

    /**
     * 答题分值判定明细
     */
    @Column(name = "answer_reason")
    private String answerReason;
    
    /**
     * 答题是否真实
     */
    @Column(name = "answer_real")
    private Boolean answerReal;

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
     * 获取用户ID
     *
     * @return user_id - 用户ID
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 设置用户ID
     *
     * @param userId 用户ID
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取试卷ID
     *
     * @return paper_id - 试卷ID
     */
    public Integer getPaperId() {
        return paperId;
    }

    /**
     * 设置试卷ID
     *
     * @param paperId 试卷ID
     */
    public void setPaperId(Integer paperId) {
        this.paperId = paperId;
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
     * 获取题目分值
     *
     * @return item_score - 题目分值
     */
    public BigDecimal getItemScore() {
        return itemScore;
    }

    /**
     * 设置题目分值
     *
     * @param itemScore 题目分值
     */
    public void setItemScore(BigDecimal itemScore) {
        this.itemScore = itemScore;
    }

    /**
     * 获取回答值
     *
     * @return answer_value - 回答值
     */
    public String getAnswerValue() {
        return answerValue;
    }

    /**
     * 设置回答值
     *
     * @param answerValue 回答值
     */
    public void setAnswerValue(String answerValue) {
        this.answerValue = answerValue;
    }

    /**
     * 获取答题分值
     *
     * @return answer_score - 答题分值
     */
    public BigDecimal getAnswerScore() {
        return answerScore;
    }

    /**
     * 设置答题分值
     *
     * @param answerScore 答题分值
     */
    public void setAnswerScore(BigDecimal answerScore) {
        this.answerScore = answerScore;
    }

    /**
     * 获取答题分值判定明细
     *
     * @return answer_reason - 答题分值判定明细
     */
    public String getAnswerReason() {
        return answerReason;
    }

    /**
     * 设置答题分值判定明细
     *
     * @param answerReason 答题分值判定明细
     */
    public void setAnswerReason(String answerReason) {
        this.answerReason = answerReason;
    }

	public Boolean getAnswerReal() {
		return answerReal;
	}

	public void setAnswerReal(Boolean answerReal) {
		this.answerReal = answerReal;
	}
    
}
