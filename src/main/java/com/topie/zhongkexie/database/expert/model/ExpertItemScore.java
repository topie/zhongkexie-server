package com.topie.zhongkexie.database.expert.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "e_pxpert_item_score")
public class ExpertItemScore {
    /**
     * 试卷ID
     */
    @Column(name = "paper_id")
    private Integer paperId;

    /**
     * 专家ID
     */
    @Column(name = "expert_id")
    private Integer expertId;

    /**
     * 专家用户ID
     */
    @Column(name = "expert_user_id")
    private Integer expertUserId;

    /**
     * 提交答案用户ID
     */
    @Column(name = "org_user_id")
    private Integer orgUserId;

    /**
     * 试题ID
     */
    @Column(name = "item_id")
    private Integer itemId;

    /**
     * 专家评分
     */
    @Column(name = "item_score")
    private BigDecimal itemScore;

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
     * 获取专家ID
     *
     * @return expert_id - 专家ID
     */
    public Integer getExpertId() {
        return expertId;
    }

    /**
     * 设置专家ID
     *
     * @param expertId 专家ID
     */
    public void setExpertId(Integer expertId) {
        this.expertId = expertId;
    }

    /**
     * 获取专家用户ID
     *
     * @return expert_user_id - 专家用户ID
     */
    public Integer getExpertUserId() {
        return expertUserId;
    }

    /**
     * 设置专家用户ID
     *
     * @param expertUserId 专家用户ID
     */
    public void setExpertUserId(Integer expertUserId) {
        this.expertUserId = expertUserId;
    }

    /**
     * 获取提交答案用户ID
     *
     * @return org_user_id - 提交答案用户ID
     */
    public Integer getOrgUserId() {
        return orgUserId;
    }

    /**
     * 设置提交答案用户ID
     *
     * @param orgUserId 提交答案用户ID
     */
    public void setOrgUserId(Integer orgUserId) {
        this.orgUserId = orgUserId;
    }

    /**
     * 获取试题ID
     *
     * @return item_id - 试题ID
     */
    public Integer getItemId() {
        return itemId;
    }

    /**
     * 设置试题ID
     *
     * @param itemId 试题ID
     */
    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    /**
     * 获取专家评分
     *
     * @return item_score - 专家评分
     */
    public BigDecimal getItemScore() {
        return itemScore;
    }

    /**
     * 设置专家评分
     *
     * @param itemScore 专家评分
     */
    public void setItemScore(BigDecimal itemScore) {
        this.itemScore = itemScore;
    }

	@Override
	public String toString() {
		return "ExpertItemScore [paperId=" + paperId + ", expertId=" + expertId
				+ ", expertUserId=" + expertUserId + ", orgUserId=" + orgUserId
				+ ", itemId=" + itemId + ", itemScore=" + itemScore + "]";
	}
}