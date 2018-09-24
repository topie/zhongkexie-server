package com.topie.zhongkexie.database.task.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "paper_id")
    private Integer paperId;

    @Column(name = "parent_id")
    private Integer parentId;

    @Column(name = "index_id")
    private Integer indexId;

    /**
     * 任务名称
     */
    @Column(name = "task_name")
    private String taskName;

    /**
     * 状态
     */
    @Column(name = "task_status")
    private String taskStatus;

    /**
     * 牵头部门
     */
    @Column(name = "task_dept")
    private String taskDept;

    /**
     * 是否有子任务
     */
    @Column(name = "has_child")
    private Boolean hasChild;

    /**
     * 权重
     */
    @Column(name = "task_weight")
    private BigDecimal taskWeight;

    /**
     * 分数
     */
    @Column(name = "task_score")
    private BigDecimal taskScore;

    /**
     * 描述
     */
    @Column(name = "task_desc")
    private String taskDesc;

    /**
     * 任务编号
     */
    @Column(name = "task_code")
    private String taskCode;

    /**
     * 考核部门
     */
    @Column(name = "link_dept")
    private String linkDept;

    /**
     * 值学会
     */
    @Column(name = "task_value")
    private String taskValue;

    /**
     * 四个等级的权重
     */
    @Column(name = "task_cweight")
    private String taskCweight;

    /**
     */
    @Column(name = "task_check_user")
    private String taskCheckUser;

    /**
     * 顺序
     */
    @Column(name = "task_seq")
    private Integer taskSeq;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

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
     * @return parent_id
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * @param parentId
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * @return index_id
     */
    public Integer getIndexId() {
        return indexId;
    }

    /**
     * @param indexId
     */
    public void setIndexId(Integer indexId) {
        this.indexId = indexId;
    }

    /**
     * 获取任务名称
     *
     * @return task_name - 任务名称
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * 设置任务名称
     *
     * @param taskName 任务名称
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * 获取状态
     *
     * @return task_status - 状态
     */
    public String getTaskStatus() {
        return taskStatus;
    }

    /**
     * 设置状态
     *
     * @param taskStatus 状态
     */
    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    /**
     * 获取牵头部门
     *
     * @return task_dept - 牵头部门
     */
    public String getTaskDept() {
        return taskDept;
    }

    /**
     * 设置牵头部门
     *
     * @param taskDept 牵头部门
     */
    public void setTaskDept(String taskDept) {
        this.taskDept = taskDept;
    }

    /**
     * 获取是否有子任务
     *
     * @return has_child - 是否有子任务
     */
    public Boolean getHasChild() {
        return hasChild;
    }

    /**
     * 设置是否有子任务
     *
     * @param hasChild 是否有子任务
     */
    public void setHasChild(Boolean hasChild) {
        this.hasChild = hasChild;
    }

    /**
     * 获取权重
     *
     * @return task_weight - 权重
     */
    public BigDecimal getTaskWeight() {
        return taskWeight;
    }

    /**
     * 设置权重
     *
     * @param taskWeight 权重
     */
    public void setTaskWeight(BigDecimal taskWeight) {
        this.taskWeight = taskWeight;
    }

    /**
     * 获取分数
     *
     * @return task_score - 分数
     */
    public BigDecimal getTaskScore() {
        return taskScore;
    }

    /**
     * 设置分数
     *
     * @param taskScore 分数
     */
    public void setTaskScore(BigDecimal taskScore) {
        this.taskScore = taskScore;
    }

    /**
     * 获取描述
     *
     * @return task_desc - 描述
     */
    public String getTaskDesc() {
        return taskDesc;
    }

    /**
     * 设置描述
     *
     * @param taskDesc 描述
     */
    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    /**
     * 获取任务编号
     *
     * @return task_code - 任务编号
     */
    public String getTaskCode() {
        return taskCode;
    }

    /**
     * 设置任务编号
     *
     * @param taskCode 任务编号
     */
    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    /**
     * 获取考核部门
     *
     * @return link_dept - 考核部门
     */
    public String getLinkDept() {
        return linkDept;
    }

    /**
     * 设置考核部门
     *
     * @param linkDept 考核部门
     */
    public void setLinkDept(String linkDept) {
        this.linkDept = linkDept;
    }

    /**
     * 获取值学会
     *
     * @return task_value - 值学会
     */
    public String getTaskValue() {
        return taskValue;
    }

    /**
     * 设置值学会
     *
     * @param taskValue 值学会
     */
    public void setTaskValue(String taskValue) {
        this.taskValue = taskValue;
    }

    /**
     * 获取四个等级的权重
     *
     * @return task_cweight - 四个等级的权重
     */
    public String getTaskCweight() {
        return taskCweight;
    }

    /**
     * 设置四个等级的权重
     *
     * @param taskCweight 四个等级的权重
     */
    public void setTaskCweight(String taskCweight) {
        this.taskCweight = taskCweight;
    }

    /**
     *
     */
    public String getTaskCheckUser() {
        return taskCheckUser;
    }

    /**
     *
     */
    public void setTaskCheckUser(String taskCheckUser) {
        this.taskCheckUser = taskCheckUser;
    }

    /**
     * 获取顺序
     *
     * @return task_seq - 顺序
     */
    public Integer getTaskSeq() {
        return taskSeq;
    }

    /**
     * 设置顺序
     *
     * @param taskSeq 顺序
     */
    public void setTaskSeq(Integer taskSeq) {
        this.taskSeq = taskSeq;
    }
}