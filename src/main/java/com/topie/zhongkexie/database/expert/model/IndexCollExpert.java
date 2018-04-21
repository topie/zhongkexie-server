package com.topie.zhongkexie.database.expert.model;

import javax.persistence.*;

@Table(name = "e_index_coll_expert")
public class IndexCollExpert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "paper_id")
    private Integer paperId;

    @Column(name = "paper_expert_id")
    private Integer paperExpertId;

    @Column(name = "index_collection_id")
    private Integer indexCollectionId;

    private String name;

    @Column(name = "expert_names")
    private String expertNames;

    @Column(name = "expert_ids")
    private String expertIds;

    @Column(name = "related_field")
    private String relatedField;

    @Column(name = "field_type")
    private String fieldType;

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
     * @return paper_expert_id
     */
    public Integer getPaperExpertId() {
        return paperExpertId;
    }

    /**
     * @param paperExpertId
     */
    public void setPaperExpertId(Integer paperExpertId) {
        this.paperExpertId = paperExpertId;
    }

    /**
     * @return index_collection_id
     */
    public Integer getIndexCollectionId() {
        return indexCollectionId;
    }

    /**
     * @param indexCollectionId
     */
    public void setIndexCollectionId(Integer indexCollectionId) {
        this.indexCollectionId = indexCollectionId;
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
     * @return expert_names
     */
    public String getExpertNames() {
        return expertNames;
    }

    /**
     * @param expertNames
     */
    public void setExpertNames(String expertNames) {
        this.expertNames = expertNames;
    }

    /**
     * @return expert_ids
     */
    public String getExpertIds() {
        return expertIds;
    }

    /**
     * @param expertIds
     */
    public void setExpertIds(String expertIds) {
        this.expertIds = expertIds;
    }

    /**
     * @return related_field
     */
    public String getRelatedField() {
        return relatedField;
    }

    /**
     * @param relatedField
     */
    public void setRelatedField(String relatedField) {
        this.relatedField = relatedField;
    }

    /**
     * @return field_type
     */
    public String getFieldType() {
        return fieldType;
    }

    /**
     * @param fieldType
     */
    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }
}