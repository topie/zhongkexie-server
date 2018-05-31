package com.topie.zhongkexie.database.appriase.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "d_score_appraise_user")
public class ScoreAppraiseUser {
	/**
	 * ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "item_id")
    private Integer itemId;

    @Column(name = "paper_id")
    private Integer paperId;

    @Column(name = "item_value")
    private String itemValue;

    @Column(name = "item_status")
    private String itemStatus;

    /**
     * @return user_id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * @return item_id
     */
    public Integer getItemId() {
        return itemId;
    }

    /**
     * @param itemId
     */
    public void setItemId(Integer itemId) {
        this.itemId = itemId;
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
     * @return item_value
     */
    public String getItemValue() {
        return itemValue;
    }

    /**
     * @param itemValue
     */
    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }

    /**
     * @return item_status
     */
    public String getItemStatus() {
        return itemStatus;
    }

    /**
     * @param itemStatus
     */
    public void setItemStatus(String itemStatus) {
        this.itemStatus = itemStatus;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
    
    
}