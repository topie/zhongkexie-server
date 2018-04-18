package com.topie.zhongkexie.database.core.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.topie.zhongkexie.common.handler.Sortable;

@Table(name = "d_score_item")
public class ScoreItem extends Sortable {

    private static final long serialVersionUID = 3203650793511377505L;

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 指标ID
     */
    @Column(name = "index_id")
    private Integer indexId;

    /**
     * 题目
     */
    private String title;

    private Integer type;

    @Column(name = "option_logic")
    private String optionLogic;

    @Column(name = "responsible_department")
    private String responsibleDepartment;
    
    @Column(name = "related_field")
    private String relatedField;
    
    @Column(name = "score_type")
    private String scoreType;
    @Column(name = "max_value")
    private Integer maxValue;
    @Column(name = "refer_item")
    private String referItem;
    @Column(name = "show_level")
    private Integer showLevel;
    
    
    /**
     * 题目权重
     */
    private BigDecimal weight;

    /**
     * 题目分值
     */
    private BigDecimal score;

    private Integer sort;

    public String getOptionLogic() {
        return optionLogic;
    }

    public void setOptionLogic(String optionLogic) {
        this.optionLogic = optionLogic;
    }
    /**
     * 题目回答类型：0 填空 1 单选 2 多选 3填空[多] 4填空[数字]  5单选[可填空] 6多选[可填空]
     * @return
     */
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * 获取ID
     *
     * @return id - ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置ID
     *
     * @param id ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取指标ID
     *
     * @return index_id - 指标ID
     */
    public Integer getIndexId() {
        return indexId;
    }

    /**
     * 设置指标ID
     *
     * @param indexId 指标ID
     */
    public void setIndexId(Integer indexId) {
        this.indexId = indexId;
    }

    /**
     * 获取题目
     *
     * @return title - 题目
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置题目
     *
     * @param title 题目
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    /**
     * 获取题目权重
     * @return
     */
    public BigDecimal getWeight() {
		return weight;
	}
    
    /**
     * 题目权重
     * 
     */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	/**
     * 获取题目分值
     *
     * @return score - 题目分值
     */
    public BigDecimal getScore() {
        return score;
    }

    /**
     * 设置题目分值
     *
     * @param score 题目分值
     */
    public void setScore(BigDecimal score) {
        this.score = score;
    }

	public String getResponsibleDepartment() {
		return responsibleDepartment;
	}

	public void setResponsibleDepartment(String responsibleDepartment) {
		this.responsibleDepartment = responsibleDepartment;
	}

	public String getRelatedField() {
		return relatedField;
	}

	public void setRelatedField(String relatedField) {
		this.relatedField = relatedField;
	}
	/**
	 * case "1"://统计项
		case "2"://线性打分
		case "3"://专家打分
	 * @return
	 */
	public String getScoreType() {
		return scoreType;
	}

	public void setScoreType(String scoreType) {
		this.scoreType = scoreType;
	}

	public Integer getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Integer maxValue) {
		this.maxValue = maxValue;
	}

	public String getReferItem() {
		return referItem;
	}

	public void setReferItem(String referItem) {
		this.referItem = referItem;
	}

	public Integer getShowLevel() {
		return showLevel;
	}

	public void setShowLevel(Integer showLevel) {
		this.showLevel = showLevel;
	}
	

}
