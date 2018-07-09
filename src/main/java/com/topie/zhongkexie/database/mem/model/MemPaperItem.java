package com.topie.zhongkexie.database.mem.model;

import javax.persistence.*;

@Table(name = "d_mem_paper_item")
public class MemPaperItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "paper_id")
    private Integer paperId;

    @Column(name = "item_id")
    private Integer itemId;

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

	@Override
	public String toString() {
		return "MemPaperItem [id=" + id + ", paperId=" + paperId + ", itemId="
				+ itemId + "]";
	}
    
}