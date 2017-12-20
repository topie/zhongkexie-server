package com.topie.zhongkexie.core.dto;

import java.util.ArrayList;
import java.util.List;

public class PaperIndexDto {

    private Integer indexId;

    private String indexTitle;

    private String parentIndexTitle;

    private List<ItemDto> items = new ArrayList<>();

    public Integer getIndexId() {
        return indexId;
    }

    public void setIndexId(Integer indexId) {
        this.indexId = indexId;
    }

    public String getIndexTitle() {
        return indexTitle;
    }

    public void setIndexTitle(String indexTitle) {
        this.indexTitle = indexTitle;
    }

    public String getParentIndexTitle() {
        return parentIndexTitle;
    }

    public void setParentIndexTitle(String parentIndexTitle) {
        this.parentIndexTitle = parentIndexTitle;
    }

    public List<ItemDto> getItems() {
        return items;
    }

    public void setItems(List<ItemDto> items) {
        this.items = items;
    }
}
