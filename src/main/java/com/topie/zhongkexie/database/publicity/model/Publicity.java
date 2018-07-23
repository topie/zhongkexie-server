package com.topie.zhongkexie.database.publicity.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "d_paper_publicity")
public class Publicity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 名称
     */
    private String title;

    /**
     * 评估项目Id
     */
    @Column(name = "paper_id")
    private Integer paperId;

    /**
     * 开始时间
     */
    @Column(name = "start_time")
    private Date startTime;

    /**
     * 结束时间
     */
    @Column(name = "end_time")
    private Date endTime;

    /**
     * 1公示 0 不公示
     */
    private Integer publicity;

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
     * 获取名称
     *
     * @return title - 名称
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置名称
     *
     * @param title 名称
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取评估项目Id
     *
     * @return paper_id - 评估项目Id
     */
    public Integer getPaperId() {
        return paperId;
    }

    /**
     * 设置评估项目Id
     *
     * @param paperId 评估项目Id
     */
    public void setPaperId(Integer paperId) {
        this.paperId = paperId;
    }

    /**
     * 获取开始时间
     *
     * @return start_time - 开始时间
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * 设置开始时间
     *
     * @param startTime 开始时间
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * 获取结束时间
     *
     * @return end_time - 结束时间
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 设置结束时间
     *
     * @param endTime 结束时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 获取1公示 0 不公示
     *
     * @return publicity - 1公示 0 不公示
     */
    public Integer getPublicity() {
        return publicity;
    }

    /**
     * 设置1公示 0 不公示
     *
     * @param publicity 1公示 0 不公示
     */
    public void setPublicity(Integer publicity) {
        this.publicity = publicity;
    }

	@Override
	public String toString() {
		return "Publicity [id=" + id + ", title=" + title + ", paperId="
				+ paperId + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", publicity=" + publicity + "]";
	}
    
    
}