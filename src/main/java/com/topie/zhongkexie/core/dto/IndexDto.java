package com.topie.zhongkexie.core.dto;

import java.math.BigDecimal;
import java.util.List;

public class IndexDto {

    private Integer id;

    private Integer pid;

    private String title;

    private BigDecimal weight;

    private BigDecimal score;

    private List<ItemDto> ids;

    public List<ItemDto> getIds() {
        return ids;
    }

    public void setIds(List<ItemDto> ids) {
        this.ids = ids;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }
}
