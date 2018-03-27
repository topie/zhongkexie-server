package com.topie.zhongkexie.core.dto;

import java.math.BigDecimal;
import java.util.Date;

public class PagerUserDto {
	/**
	 * 未提交审核
	 */
	public static final Short PAPERSTATUS_NEW = new Short("0");
	/**
	 * 审核中
	 */
	public static final Short PAPERSTATUS_SUBMMIT = new Short("1");
	/**
	 * 通过
	 */
	public static final Short PAPERSTATUS_EGIS = new Short("2");
	/**
	 * 退回
	 */
	public static final Short PAPERSTATUS_DISMISSAL = new Short("3");
	/**
	 * 开启
	 */
	public static final Short PAPERSTATUS_START = new Short("1");
	/**
	 * 关闭
	 */
	public static final Short PAPERSTATUS_END = new Short("0");

	private Integer id;

	/**
	 * 试卷标题
	 */
	private String title;

	/**
	 * 开始时间
	 */
	private Date begin;

	/**
	 * 结束时间
	 */
	private Date end;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改时间
	 */
	private Date updateTime;

	/**
	 * 状态 开启填报 关闭填报
	 */
	private Short status;

	/**
	 * 审批状态 新建 待审核 通过 驳回
	 */
	private Short approveStatus;

	/**
	 * 内容json
	 */
	private String contentJson;

	/**
	 * 用户提交答案 的审核状态
	 */
	private short checkStatus;
	/**
	 * 提交人 （学会）
	 */
	private String userName;
	
	private Integer paperId;
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

	
	public Integer getPaperId() {
		return paperId;
	}

	public void setPaperId(Integer paperId) {
		this.paperId = paperId;
	}


	/**
	 * 
	 * @return
	 */
	private Integer userId;

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

	public Date getBegin() {
		return begin;
	}

	public void setBegin(Date begin) {
		this.begin = begin;
	}

	public BigDecimal getScore() {
		return score;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Short getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(Short approveStatus) {
		this.approveStatus = approveStatus;
	}

	public String getContentJson() {
		return contentJson;
	}

	public void setContentJson(String contentJson) {
		this.contentJson = contentJson;
	}

	/**
	 * 用户提交答案 的审核状态
	 * 
	 * @return
	 */
	public short getCheckStatus() {
		return checkStatus;
	}

	/**
	 * 用户提交答案 的审核状态
	 * 
	 * @param checkStatus
	 */
	public void setCheckStatus(short checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
