package com.topie.zhongkexie.database.core.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.topie.zhongkexie.common.handler.Sortable;

@Table(name = "d_score_paper")
public class ScorePaper extends Sortable {

    private static final long serialVersionUID = 9011743290306074744L;

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 试卷标题
     */
    private String title;

    /**
     * 开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date begin;

    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date end;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 状态
     */
    private Short status;
    
    /**
     * 总分值
     */
    private BigDecimal score;

    /**
     * 审批状态
     */
    @Column(name = "approve_status")
    private Short approveStatus;

    /**
     * 内容json
     */
    @Column(name = "content_json")
    private String contentJson;

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
     * 获取试卷标题
     *
     * @return title - 试卷标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置试卷标题
     *
     * @param title 试卷标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取开始时间
     *
     * @return begin - 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    public Date getBegin() {
        return begin;
    }

    /**
     * 设置开始时间
     *
     * @param begin 开始时间
     */
    public void setBegin(Date begin) {
        this.begin = begin;
    }

    /**
     * 获取结束时间
     *
     * @return end - 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    public Date getEnd() {
        return end;
    }

    /**
     * 设置结束时间
     *
     * @param end 结束时间
     */
    public void setEnd(Date end) {
        this.end = end;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取修改时间
     *
     * @return update_time - 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置修改时间
     *
     * @param updateTime 修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取状态
     *
     * @return status - 状态
     */
    public Short getStatus() {
        return status;
    }

    /**
     * 设置状态
     *
     * @param status 状态
     */
    public void setStatus(Short status) {
        this.status = status;
    }

    /**
     * 获取审批状态
     *
     * @return approve_status - 审批状态
     */
    public Short getApproveStatus() {
        return approveStatus;
    }

    /**
     * 设置审批状态
     *
     * @param approveStatus 审批状态
     */
    public void setApproveStatus(Short approveStatus) {
        this.approveStatus = approveStatus;
    }

    /**
     * 获取内容json
     *
     * @return content_json - 内容json
     */
    public String getContentJson() {
        return contentJson;
    }

    /**
     * 设置内容json
     *
     * @param contentJson 内容json
     */
    public void setContentJson(String contentJson) {
        this.contentJson = contentJson;
    }

	public BigDecimal getScore() {
		return score;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
	}
    
    
}
