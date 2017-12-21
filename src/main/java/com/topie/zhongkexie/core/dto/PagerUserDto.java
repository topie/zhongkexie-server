package com.topie.zhongkexie.core.dto;

import java.util.Date;

public class PagerUserDto {

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
	     * 状态
	     */
	    private Short status;

	    /**
	     * 审批状态
	     */
	    private Short approveStatus;

	    /**
	     * 内容json
	     */
	    private String contentJson;
	    
	    /**
	     * 审核状态
	     */
	    private short checkStatus;

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

		public short getCheckStatus() {
			return checkStatus;
		}

		public void setCheckStatus(short checkStatus) {
			this.checkStatus = checkStatus;
		}
	    
}
