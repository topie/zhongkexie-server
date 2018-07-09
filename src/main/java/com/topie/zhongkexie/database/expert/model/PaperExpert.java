package com.topie.zhongkexie.database.expert.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "e_papert_expert")
public class PaperExpert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "paper_id")
    private Integer paperId;

    private String name;

    @Column(name = "dept_names")
    private String deptNames;

    @Column(name = "expert_names")
    private String expertNames;

    @Column(name = "dept_ids")
    private String deptIds;

    @Column(name = "expert_ids")
    private String expertIds;

    private String type;

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
     * @return dept_names
     */
    public String getDeptNames() {
        return deptNames;
    }

    /**
     * @param deptNames
     */
    public void setDeptNames(String deptNames) {
        this.deptNames = deptNames;
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
     * @return dept_ids
     */
    public String getDeptIds() {
        return deptIds;
    }

    /**
     * @param deptIds
     */
    public void setDeptIds(String deptIds) {
        this.deptIds = deptIds;
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
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

	@Override
	public String toString() {
		return "PaperExpert [id=" + id + ", paperId=" + paperId + ", name="
				+ name + ", deptNames=" + deptNames + ", expertNames="
				+ expertNames + ", deptIds=" + deptIds + ", expertIds="
				+ expertIds + ", type=" + type + "]";
	}
    
}