package com.topie.zhongkexie.database.publicity.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "d_dissent")
public class Dissent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 异议学会
     */
    @Column(name = "dissent_org")
    private String dissentOrg;

    /**
     * 异议指标
     */
    @Column(name = "dissent_index")
    private String dissentIndex;

    /**
     * 异议内容
     */
    private String content;

    /**
     * 提交学会
     */
    @Column(name = "input_org")
    private String inputOrg;

    /**
     * 提交人
     */
    @Column(name = "input_user")
    private String inputUser;

    @Column(name = "input_id")
    private Integer inputId;

    /**
     * 提交时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 已读未读
     */
    @Column(name = "read_status")
    private String readStatus;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 附件
     */
    @Column(name = "file_ids")
    private String fileIds;

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
     * 获取异议学会
     *
     * @return dissent_org - 异议学会
     */
    public String getDissentOrg() {
        return dissentOrg;
    }

    /**
     * 设置异议学会
     *
     * @param dissentOrg 异议学会
     */
    public void setDissentOrg(String dissentOrg) {
        this.dissentOrg = dissentOrg;
    }

    /**
     * 获取异议指标
     *
     * @return dissent_index - 异议指标
     */
    public String getDissentIndex() {
        return dissentIndex;
    }

    /**
     * 设置异议指标
     *
     * @param dissentIndex 异议指标
     */
    public void setDissentIndex(String dissentIndex) {
        this.dissentIndex = dissentIndex;
    }

    /**
     * 获取异议内容
     *
     * @return content - 异议内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置异议内容
     *
     * @param content 异议内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取提交学会
     *
     * @return input_org - 提交学会
     */
    public String getInputOrg() {
        return inputOrg;
    }

    /**
     * 设置提交学会
     *
     * @param inputOrg 提交学会
     */
    public void setInputOrg(String inputOrg) {
        this.inputOrg = inputOrg;
    }

    /**
     * 获取提交人
     *
     * @return input_user - 提交人
     */
    public String getInputUser() {
        return inputUser;
    }

    /**
     * 设置提交人
     *
     * @param inputUser 提交人
     */
    public void setInputUser(String inputUser) {
        this.inputUser = inputUser;
    }

    /**
     * @return input_id
     */
    public Integer getInputId() {
        return inputId;
    }

    /**
     * @param inputId
     */
    public void setInputId(Integer inputId) {
        this.inputId = inputId;
    }

    /**
     * 获取提交时间
     *
     * @return create_time - 提交时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置提交时间
     *
     * @param createTime 提交时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取已读未读
     *
     * @return read_status - 已读未读
     */
    public String getReadStatus() {
        return readStatus;
    }

    /**
     * 设置已读未读
     *
     * @param readStatus 已读未读
     */
    public void setReadStatus(String readStatus) {
        this.readStatus = readStatus;
    }

    /**
     * 获取联系电话
     *
     * @return phone - 联系电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置联系电话
     *
     * @param phone 联系电话
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取邮箱
     *
     * @return email - 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置邮箱
     *
     * @param email 邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取附件
     *
     * @return file_ids - 附件
     */
    public String getFileIds() {
        return fileIds;
    }

    /**
     * 设置附件
     *
     * @param fileIds 附件
     */
    public void setFileIds(String fileIds) {
        this.fileIds = fileIds;
    }

	@Override
	public String toString() {
		return "Dissent [id=" + id + ", dissentOrg=" + dissentOrg
				+ ", dissentIndex=" + dissentIndex + ", content=" + content
				+ ", inputOrg=" + inputOrg + ", inputUser=" + inputUser
				+ ", inputId=" + inputId + ", createTime=" + createTime
				+ ", remark=" + remark + ", readStatus=" + readStatus
				+ ", phone=" + phone + ", email=" + email + ", fileIds="
				+ fileIds + "]";
	}
    
}