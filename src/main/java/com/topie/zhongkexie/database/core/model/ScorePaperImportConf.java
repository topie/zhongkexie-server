package com.topie.zhongkexie.database.core.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "d_score_paper_import_conf")
public class ScorePaperImportConf {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 附件ID
     */
    @Column(name = "file_id")
    private Integer fileId;

    /**
     * 评价表ID
     */
    @Column(name = "paper_id")
    private Integer paperId;

    /**
     * 开始行数
     */
    private Integer start;

    /**
     * 结束行数
     */
    private Integer end;

    /**
     * 名称
     */
    private String name;

    /**
     * 部门的对应关系excel->database
     */
    @Column(name = "dept_mapping")
    private String deptMapping;

    /**
     * 领域的对应关系excel->database{“事业部":"国防事业部门",“事业部1":"国防事业部门1"}
     */
    @Column(name = "field_mapping")
    private String fieldMapping;

    /**
     * 指标的列[{index:0,score:1},{index:2,score:3},{index:4,score:5},{index:6,score:7}]
     */
    @Column(name = "json_index")
    private String jsonIndex;

    /**
     * 题目的列{title:8,score:9,desc:10,org:12}
     */
    @Column(name = "json_item")
    private String jsonItem;

    @Column(name = "create_time")
    private Date createTime;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取附件ID
     *
     * @return file_id - 附件ID
     */
    public Integer getFileId() {
        return fileId;
    }

    /**
     * 设置附件ID
     *
     * @param fileId 附件ID
     */
    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    /**
     * 获取评价表ID
     *
     * @return paper_id - 评价表ID
     */
    public Integer getPaperId() {
        return paperId;
    }

    /**
     * 设置评价表ID
     *
     * @param paperId 评价表ID
     */
    public void setPaperId(Integer paperId) {
        this.paperId = paperId;
    }

    /**
     * 获取开始行数
     *
     * @return start - 开始行数
     */
    public Integer getStart() {
        return start;
    }

    /**
     * 设置开始行数
     *
     * @param start 开始行数
     */
    public void setStart(Integer start) {
        this.start = start;
    }

    /**
     * 获取结束行数
     *
     * @return end - 结束行数
     */
    public Integer getEnd() {
        return end;
    }

    /**
     * 设置结束行数
     *
     * @param end 结束行数
     */
    public void setEnd(Integer end) {
        this.end = end;
    }

    /**
     * 获取名称
     *
     * @return name - 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取部门的对应关系excel->database
     *
     * @return dept_mapping - 部门的对应关系excel->database
     */
    public String getDeptMapping() {
        return deptMapping;
    }

    /**
     * 设置部门的对应关系excel->database
     *
     * @param deptMapping 部门的对应关系excel->database
     */
    public void setDeptMapping(String deptMapping) {
        this.deptMapping = deptMapping;
    }

    /**
     * 获取领域的对应关系excel->database{“事业部":"国防事业部门",“事业部1":"国防事业部门1"}
     *
     * @return field_mapping - 领域的对应关系excel->database{“事业部":"国防事业部门",“事业部1":"国防事业部门1"}
     */
    public String getFieldMapping() {
        return fieldMapping;
    }

    /**
     * 设置领域的对应关系excel->database{“事业部":"国防事业部门",“事业部1":"国防事业部门1"}
     *
     * @param fieldMapping 领域的对应关系excel->database{“事业部":"国防事业部门",“事业部1":"国防事业部门1"}
     */
    public void setFieldMapping(String fieldMapping) {
        this.fieldMapping = fieldMapping;
    }

    /**
     * 获取指标的列[{index:0,score:1},{index:2,score:3},{index:4,score:5},{index:6,score:7}]
     *
     * @return json_index - 指标的列[{index:0,score:1},{index:2,score:3},{index:4,score:5},{index:6,score:7}]
     */
    public String getJsonIndex() {
        return jsonIndex;
    }

    /**
     * 设置指标的列[{index:0,score:1},{index:2,score:3},{index:4,score:5},{index:6,score:7}]
     *
     * @param jsonIndex 指标的列[{index:0,score:1},{index:2,score:3},{index:4,score:5},{index:6,score:7}]
     */
    public void setJsonIndex(String jsonIndex) {
        this.jsonIndex = jsonIndex;
    }

    /**
     * 获取题目的列{title:8,score:9,desc:10,org:12}
     *
     * @return json_item - 题目的列{title:8,score:9,desc:10,org:12}
     */
    public String getJsonItem() {
        return jsonItem;
    }

    /**
     * 设置题目的列{title:8,score:9,desc:10,org:12}
     *
     * @param jsonItem 题目的列{title:8,score:9,desc:10,org:12}
     */
    public void setJsonItem(String jsonItem) {
        this.jsonItem = jsonItem;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	@Override
	public String toString() {
		return "ScorePaperImportConf [id=" + id + ", fileId=" + fileId
				+ ", paperId=" + paperId + ", start=" + start + ", end=" + end
				+ ", name=" + name + ", deptMapping=" + deptMapping
				+ ", fieldMapping=" + fieldMapping + ", jsonIndex=" + jsonIndex
				+ ", jsonItem=" + jsonItem + ", createTime=" + createTime + "]";
	}
    
}