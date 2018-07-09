package com.topie.zhongkexie.database.core.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "d_dict_item")
public class DictItem {
    @Id
    @Column(name = "item_id")
    private Integer itemId;

    @Column(name = "item_code")
    private String itemCode;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "item_seq")
    private Integer itemSeq;

    @Column(name = "dict_id")
    private Integer dictId;

    @Column(name = "item_pid")
    private Integer itemPid;

    @Column(name = "item_desc")
    private String itemDesc;

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
     * @return item_code
     */
    public String getItemCode() {
        return itemCode;
    }

    /**
     * @param itemCode
     */
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    /**
     * @return item_name
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * @param itemName
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * @return item_seq
     */
    public Integer getItemSeq() {
        return itemSeq;
    }

    /**
     * @param itemSeq
     */
    public void setItemSeq(Integer itemSeq) {
        this.itemSeq = itemSeq;
    }

    /**
     * @return dict_id
     */
    public Integer getDictId() {
        return dictId;
    }

    /**
     * @param dictId
     */
    public void setDictId(Integer dictId) {
        this.dictId = dictId;
    }

    /**
     * @return item_pid
     */
    public Integer getItemPid() {
        return itemPid;
    }

    /**
     * @param itemPid
     */
    public void setItemPid(Integer itemPid) {
        this.itemPid = itemPid;
    }

    /**
     * @return item_desc
     */
    public String getItemDesc() {
        return itemDesc;
    }

    /**
     * @param itemDesc
     */
    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

	@Override
	public String toString() {
		return "DictItem [itemId=" + itemId + ", itemCode=" + itemCode
				+ ", itemName=" + itemName + ", itemSeq=" + itemSeq
				+ ", dictId=" + dictId + ", itemPid=" + itemPid + ", itemDesc="
				+ itemDesc + "]";
	}
    
}