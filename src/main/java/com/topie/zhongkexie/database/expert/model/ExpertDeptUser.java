package com.topie.zhongkexie.database.expert.model;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "e_expert_dept_user")
public class ExpertDeptUser {
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
     * 学会用户ID
     */
    @Column(name = "dept_user_id")
    private Integer deptUserId;

    /**
     * 指标集ID
     */
    @Column(name = "index_coll_id")
    private Integer indexCollId;

    @Column(name = "index_coll_expert_id")
    private Integer indexCollExpertId;
    
    @Column(name = "status")
    private String status;

    /**
     * @return paper_id
     */
    public Integer getPaperId() {
        return paperId;
    }

    /**
     * @param paperId
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
     * 获取学会用户ID
     *
     * @return dept_user_id - 学会用户ID
     */
    public Integer getDeptUserId() {
        return deptUserId;
    }

    /**
     * 设置学会用户ID
     *
     * @param deptUserId 学会用户ID
     */
    public void setDeptUserId(Integer deptUserId) {
        this.deptUserId = deptUserId;
    }

    /**
     * 获取指标集ID
     *
     * @return index_coll_id - 指标集ID
     */
    public Integer getIndexCollId() {
        return indexCollId;
    }

    /**
     * 设置指标集ID
     *
     * @param indexCollId 指标集ID
     */
    public void setIndexCollId(Integer indexCollId) {
        this.indexCollId = indexCollId;
    }

    /**
     * @return index_coll_expert_id
     */
    public Integer getIndexCollExpertId() {
        return indexCollExpertId;
    }

    /**
     * @param indexCollExpertId
     */
    public void setIndexCollExpertId(Integer indexCollExpertId) {
        this.indexCollExpertId = indexCollExpertId;
    }
    /**
	 * 0 未提交 1待评价 2 已评价
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * 0 未提交 1待评价 2 已评价
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
    
}