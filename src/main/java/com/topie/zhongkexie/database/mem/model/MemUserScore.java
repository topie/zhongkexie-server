package com.topie.zhongkexie.database.mem.model;

import java.math.BigDecimal;
import javax.persistence.*;

@Table(name = "d_mem_user_score")
public class MemUserScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    /**
     * 满意度百分比
     */
    private BigDecimal score;

    @Column(name = "item_id")
    private Integer itemId;

    @Column(name = "paper_id")
    private Integer paperId;

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
     * 获取满意度百分比
     *
     * @return score - 满意度百分比
     */
    public BigDecimal getScore() {
        return score;
    }

    /**
     * 设置满意度百分比
     *
     * @param score 满意度百分比
     */
    public void setScore(BigDecimal score) {
        this.score = score;
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

	@Override
	public String toString() {
		return "MemUserScore [id=" + id + ", userId=" + userId + ", score="
				+ score + ", itemId=" + itemId + ", paperId=" + paperId + "]";
	}
    
}