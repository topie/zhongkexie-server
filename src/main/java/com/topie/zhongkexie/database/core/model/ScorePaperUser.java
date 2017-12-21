package com.topie.zhongkexie.database.core.model;

import javax.persistence.*;

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
     *
     * @return status - ״̬
     */
    public Short getStatus() {
        return status;
    }

    /**
     * ����״̬
     *
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