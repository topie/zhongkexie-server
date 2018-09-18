package com.topie.zhongkexie.database.task.model;

import java.math.BigDecimal;
import javax.persistence.*;

@Table(name = "t_task_index_score")
public class TaskIndexScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "paper_id")
    private Integer paperId;

    @Column(name = "index_id")
    private Integer indexId;

    private BigDecimal score;

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
     * @return index_id
     */
    public Integer getIndexId() {
        return indexId;
    }

    /**
     * @param indexId
     */
    public void setIndexId(Integer indexId) {
        this.indexId = indexId;
    }

    /**
     * @return score
     */
    public BigDecimal getScore() {
        return score;
    }

    /**
     * @param score
     */
    public void setScore(BigDecimal score) {
        this.score = score;
    }
}