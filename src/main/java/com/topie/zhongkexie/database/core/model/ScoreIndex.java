package com.topie.zhongkexie.database.core.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.topie.zhongkexie.common.handler.Sortable;

@Table(name = "d_score_index")
public class ScoreIndex extends Sortable {

    private static final long serialVersionUID = 6554177671217597217L;

    /**
     * 节点ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 父节点ID
     */
    @Column(name = "parent_id")
    private Integer parentId;
    /**
     * 评价表Id
     */
    @Column(name = "paper_id")
    private Integer paperId;

    /**
     * 指标名称
     */
    private String name;

    /**
     * 指标权重
     */
    private BigDecimal weight;

    /**
     * 指标分值
     */
    private BigDecimal score;

    private Integer sort;

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * 获取节点ID
     *
     * @return id - 节点ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置节点ID
     *
     * @param id 节点ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取父节点ID
     *
     * @return parent_id - 父节点ID
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * 设置父节点ID
     *
     * @param parentId 父节点ID
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取指标名称
     *
     * @return name - 指标名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置指标名称
     *
     * @param name 指标名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 指标权重
     */
    public BigDecimal getWeight() {
		return weight;
	}

    /**
     * 指标权重
     */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	/**
     * 获取指标分值
     *
     * @return score - 指标分值
     */
    public BigDecimal getScore() {
        return score;
    }

    /**
     * 设置指标分值
     *
     * @param score 指标分值
     */
    public void setScore(BigDecimal score) {
        this.score = score;
    }
    /**
     * 评价表ID
     * @return
     */
	public Integer getPaperId() {
		return paperId;
	}
	/**
     * 评价表ID
     * 
     */
	public void setPaperId(Integer paperId) {
		this.paperId = paperId;
	}

	@Override
	public String toString() {
		return "ScoreIndex [id=" + id + ", parentId=" + parentId + ", paperId="
				+ paperId + ", name=" + name + ", weight=" + weight
				+ ", score=" + score + ", sort=" + sort + "]";
	}
    
}
