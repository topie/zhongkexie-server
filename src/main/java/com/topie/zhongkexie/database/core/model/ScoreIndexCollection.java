package com.topie.zhongkexie.database.core.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "d_score_index_collection")
public class ScoreIndexCollection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(name = "paper_id")
    private Integer paperId;

    private String description;
    /**
     * 领域
     */
    @Column(name = "related_field")
    private String relatedField;

    @Column(name = "item_collection")
    private String itemCollection;

    @Column(name = "index_collection")
    private String indexCollection;

    @Column(name = "content_json")
    private String contentJson;

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
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
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

    public String getRelatedField() {
		return relatedField;
	}

	public void setRelatedField(String relatedField) {
		this.relatedField = relatedField;
	}

	/**
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return item_collection
     */
    public String getItemCollection() {
        return itemCollection;
    }

    /**
     * @param itemCollection
     */
    public void setItemCollection(String itemCollection) {
        this.itemCollection = itemCollection;
    }

    /**
     * @return index_collection
     */
    public String getIndexCollection() {
        return indexCollection;
    }

    /**
     * @param indexCollection
     */
    public void setIndexCollection(String indexCollection) {
        this.indexCollection = indexCollection;
    }

    /**
     * @return content_json
     */
    public String getContentJson() {
        return contentJson;
    }

    /**
     * @param contentJson
     */
    public void setContentJson(String contentJson) {
        this.contentJson = contentJson;
    }

	@Override
	public String toString() {
		return "ScoreIndexCollection [id=" + id + ", name=" + name
				+ ", paperId=" + paperId + ", description=" + description
				+ ", relatedField=" + relatedField + ", itemCollection="
				+ itemCollection + ", indexCollection=" + indexCollection
				+ "]";
	}
    
}