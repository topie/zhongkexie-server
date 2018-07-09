package com.topie.zhongkexie.database.core.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "d_inbox")
public class Inbox {
    /**
     * 消息id
     */
    @Id
    @Column(name = "ib_id")
    private String ibId;

    /**
     * 创建时间
     */
    @Column(name = "ib_create_time")
    private String ibCreateTime;

    /**
     * 创建用户ID
     */
    @Column(name = "ib_create_user_id")
    private Integer ibCreateUserId;

    /**
     * 创建人
     */
    @Column(name = "ib_from_user")
    private String ibFromUser;

    /**
     * 是否有附件 0没有 1有
     */
    @Column(name = "ib_has_attachment")
    private String ibHasAttachment;

    /**
     * 主题
     */
    @Column(name = "ib_subject")
    private String ibSubject;

    /**
     * 内容
     */
    @Column(name = "ib_content")
    private String ibContent;

    /**
     * 发送给
     */
    @Column(name = "ib_to_users")
    private String ibToUsers;

    /**
     * 获取消息id
     *
     * @return ib_id - 消息id
     */
    public String getIbId() {
        return ibId;
    }

    /**
     * 设置消息id
     *
     * @param ibId 消息id
     */
    public void setIbId(String ibId) {
        this.ibId = ibId;
    }

    /**
     * 获取创建时间
     *
     * @return ib_create_time - 创建时间
     */
    public String getIbCreateTime() {
        return ibCreateTime;
    }

    /**
     * 设置创建时间
     *
     * @param ibCreateTime 创建时间
     */
    public void setIbCreateTime(String ibCreateTime) {
        this.ibCreateTime = ibCreateTime;
    }

    /**
     * 获取创建用户ID
     *
     * @return ib_create_user_id - 创建用户ID
     */
    public Integer getIbCreateUserId() {
        return ibCreateUserId;
    }

    /**
     * 设置创建用户ID
     *
     * @param ibCreateUserId 创建用户ID
     */
    public void setIbCreateUserId(Integer ibCreateUserId) {
        this.ibCreateUserId = ibCreateUserId;
    }

    /**
     * 获取创建人
     *
     * @return ib_from_user - 创建人
     */
    public String getIbFromUser() {
        return ibFromUser;
    }

    /**
     * 设置创建人
     *
     * @param ibFromUser 创建人
     */
    public void setIbFromUser(String ibFromUser) {
        this.ibFromUser = ibFromUser;
    }

    /**
     * 获取是否有附件 0没有 1有
     *
     * @return ib_has_attachment - 是否有附件 0没有 1有
     */
    public String getIbHasAttachment() {
        return ibHasAttachment;
    }

    /**
     * 设置是否有附件 0没有 1有
     *
     * @param ibHasAttachment 是否有附件 0没有 1有
     */
    public void setIbHasAttachment(String ibHasAttachment) {
        this.ibHasAttachment = ibHasAttachment;
    }

    /**
     * 获取主题
     *
     * @return ib_subject - 主题
     */
    public String getIbSubject() {
        return ibSubject;
    }

    /**
     * 设置主题
     *
     * @param ibSubject 主题
     */
    public void setIbSubject(String ibSubject) {
        this.ibSubject = ibSubject;
    }

    /**
     * 获取内容
     *
     * @return ib_content - 内容
     */
    public String getIbContent() {
        return ibContent;
    }

    /**
     * 设置内容
     *
     * @param ibContent 内容
     */
    public void setIbContent(String ibContent) {
        this.ibContent = ibContent;
    }

    /**
     * 获取发送给
     *
     * @return ib_to_users - 发送给
     */
    public String getIbToUsers() {
        return ibToUsers;
    }

    /**
     * 设置发送给
     *
     * @param ibToUsers 发送给
     */
    public void setIbToUsers(String ibToUsers) {
        this.ibToUsers = ibToUsers;
    }

	@Override
	public String toString() {
		return "Inbox [ibId=" + ibId + ", ibCreateTime=" + ibCreateTime
				+ ", ibCreateUserId=" + ibCreateUserId + ", ibFromUser="
				+ ibFromUser + ", ibHasAttachment=" + ibHasAttachment
				+ ", ibSubject=" + ibSubject + ", ibContent=" + ibContent
				+ ", ibToUsers=" + ibToUsers + "]";
	}
}