package com.topie.zhongkexie.database.core.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "d_score_paper_user")
public class ScorePaperUser {
    /**
     * �Ծ�ID
     */
    @Id
    @Column(name = "paper_id")
    private Integer paperId;

    /**
     * �û�ID
     */
    @Id
    @Column(name = "user_id")
    private Integer userId;

    /**
     * ״̬
     */
    private Short status;

    private String feedback;
    /**
	 * 总分
	 */
	private BigDecimal score;
	/**
	 * 主观分 附加分
	 */
	private BigDecimal subjectiveScore;

	
    public BigDecimal getSubjectiveScore() {
		return subjectiveScore;
	}

	public void setSubjectiveScore(BigDecimal subjectiveScore) {
		this.subjectiveScore = subjectiveScore;
	}

	/**
     * ��ȡ�Ծ�ID
     *
     * @return paper_id - �Ծ�ID
     */
    public Integer getPaperId() {
        return paperId;
    }

    /**
     * �����Ծ�ID
     *
     * @param paperId �Ծ�ID
     */
    public void setPaperId(Integer paperId) {
        this.paperId = paperId;
    }

    
    public BigDecimal getScore() {
		return score;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
	}

	/**
     * ��ȡ�û�ID
     *
     * @return user_id - �û�ID
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * �����û�ID
     *
     * @param userId �û�ID
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * ��ȡ״̬
     *0 未提交 1 未审核 2通过 3 退回
     * @return status - ״̬
     */
    public Short getStatus() {
        return status;
    }

    /**
     * ����״̬
     * 0 未提交 1 未审核 2通过 3 退回
     * @param status ״̬
     */
    public void setStatus(Short status) {
        this.status = status;
    }

    /**
     * @return feedback
     */
    public String getFeedback() {
        return feedback;
    }

    /**
     * @param feedback
     */
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}