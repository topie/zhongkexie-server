package com.topie.zhongkexie.database.department.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "d_department")
public class Department {
    @Id
    private Integer id;

    /**
     * 负责人
     */
    @Column(name = "link_man")
    private String linkMan;

    /**
     * 部门名称
     */
    @Column(name = "dept_name")
    private String deptName;

    /**
     * 部门类型
     */
    @Column(name = "dept_type")
    private String deptType;

    private String email;

    @Column(name = "tell_phone")
    private String tellPhone;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "login_name")
    private String loginName;

    @Column(name = "seq_num")
    private Integer seqNum;

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
     * 获取负责人
     *
     * @return link_man - 负责人
     */
    public String getLinkMan() {
        return linkMan;
    }

    /**
     * 设置负责人
     *
     * @param linkMan 负责人
     */
    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    /**
     * 获取部门名称
     *
     * @return dept_name - 部门名称
     */
    public String getDeptName() {
        return deptName;
    }

    /**
     * 设置部门名称
     *
     * @param deptName 部门名称
     */
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    /**
     * 获取部门类型
     *
     * @return dept_type - 部门类型
     */
    public String getDeptType() {
        return deptType;
    }

    /**
     * 设置部门类型
     *
     * @param deptType 部门类型
     */
    public void setDeptType(String deptType) {
        this.deptType = deptType;
    }

    /**
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return tell_phone
     */
    public String getTellPhone() {
        return tellPhone;
    }

    /**
     * @param tellPhone
     */
    public void setTellPhone(String tellPhone) {
        this.tellPhone = tellPhone;
    }

    /**
     * @return user_id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * @return login_name
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * @param loginName
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    /**
     * @return seq_num
     */
    public Integer getSeqNum() {
        return seqNum;
    }

    /**
     * @param seqNum
     */
    public void setSeqNum(Integer seqNum) {
        this.seqNum = seqNum;
    }

	@Override
	public String toString() {
		return "Department [id=" + id + ", linkMan=" + linkMan + ", deptName="
				+ deptName + ", deptType=" + deptType + ", email=" + email
				+ ", tellPhone=" + tellPhone + ", userId=" + userId
				+ ", loginName=" + loginName + ", seqNum=" + seqNum + "]";
	}
}