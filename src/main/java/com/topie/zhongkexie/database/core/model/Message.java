package com.topie.zhongkexie.database.core.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "d_message")
public class Message {
    /**
     * ID
     */
    @Id
    @Column(name = "m_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer mId;

    /**
     * 评价表ID
     */
    @Column(name = "sp_id")
    private Integer spId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private String createTime;

    /**
     * 创建用户
     */
    @Column(name = "create_user")
    private String createUser;

    /**
     * 创建用户ID
     */
    @Column(name = "create_user_id")
    private Integer createUserId;

    private String title;

    private String content;

    private String type;
    
    private String status;
    
    @Column(name = "download_file_id")
    private String downloadFileId;

    /**
     * 获取ID
     *
     * @return m_id - ID
     */
    public Integer getmId() {
        return mId;
    }

    /**
     * 设置ID
     *
     * @param mId ID
     */
    public void setmId(Integer mId) {
        this.mId = mId;
    }

    /**
     * 获取评价表ID
     *
     * @return sp_id - 评价表ID
     */
    public Integer getSpId() {
        return spId;
    }

    /**
     * 设置评价表ID
     *
     * @param spId 评价表ID
     */
    public void setSpId(Integer spId) {
        this.spId = spId;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取创建用户
     *
     * @return create_user - 创建用户
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 设置创建用户
     *
     * @param createUser 创建用户
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 获取创建用户ID
     *
     * @return create_user_id - 创建用户ID
     */
    public Integer getCreateUserId() {
        return createUserId;
    }

    /**
     * 设置创建用户ID
     *
     * @param createUserId 创建用户ID
     */
    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

	public String getDownloadFileId() {
		return downloadFileId;
	}

	public void setDownloadFileId(String downloadFileId) {
		this.downloadFileId = downloadFileId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Message [mId=" + mId + ", spId=" + spId + ", createTime="
				+ createTime + ", createUser=" + createUser + ", createUserId="
				+ createUserId + ", title=" + title + ", content=" + content
				+ ", type=" + type + ", status=" + status + ", downloadFileId="
				+ downloadFileId + "]";
	}
	
    
}