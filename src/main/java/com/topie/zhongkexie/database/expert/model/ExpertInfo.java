package com.topie.zhongkexie.database.expert.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "e_expert_info")
public class ExpertInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 真实姓名
     */
    @Column(name = "real_name")
    private String realName;

    @Column(name = "login_name")
    private String loginName;

    /**
     * 通信地址
     */
    private String address;

    @Column(name = "work_units")
    private String workUnits;

    private Date birthday;

    /**
     * 电话
     */
    @Column(name = "tel_phone")
    private String telPhone;

    /**
     * 电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 职称
     */
    private String title;

    /**
     * 领域
     */
    @Column(name = "related_field")
    private String relatedField;

    /**
     * 分类
     */
    @Column(name = "field_type")
    private String fieldType;

    /**
     * 对应的用户ID
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 创建时间
     */
    @Column(name = "create_date")
    private Date createDate;

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
     * 获取真实姓名
     *
     * @return real_name - 真实姓名
     */
    public String getRealName() {
        return realName;
    }

    /**
     * 设置真实姓名
     *
     * @param realName 真实姓名
     */
    public void setRealName(String realName) {
        this.realName = realName;
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
     * 获取通信地址
     *
     * @return address - 通信地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置通信地址
     *
     * @param address 通信地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return work_units
     */
    public String getWorkUnits() {
        return workUnits;
    }

    /**
     * @param workUnits
     */
    public void setWorkUnits(String workUnits) {
        this.workUnits = workUnits;
    }

    /**
     * @return birthday
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * @param birthday
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * 获取电话
     *
     * @return tel_phone - 电话
     */
    public String getTelPhone() {
        return telPhone;
    }

    /**
     * 设置电话
     *
     * @param telPhone 电话
     */
    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }

    /**
     * 获取电话
     *
     * @return phone - 电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置电话
     *
     * @param phone 电话
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取邮箱
     *
     * @return email - 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置邮箱
     *
     * @param email 邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取职称
     *
     * @return title - 职称
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置职称
     *
     * @param title 职称
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取领域
     *
     * @return related_field - 领域
     */
    public String getRelatedField() {
        return relatedField;
    }

    /**
     * 设置领域
     *
     * @param relatedField 领域
     */
    public void setRelatedField(String relatedField) {
        this.relatedField = relatedField;
    }

    /**
     * 获取分类
     *
     * @return field_type - 分类
     */
    public String getFieldType() {
        return fieldType;
    }

    /**
     * 设置分类
     *
     * @param fieldType 分类
     */
    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    /**
     * 获取对应的用户ID
     *
     * @return user_id - 对应的用户ID
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 设置对应的用户ID
     *
     * @param userId 对应的用户ID
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取创建时间
     *
     * @return create_date - 创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 设置创建时间
     *
     * @param createDate 创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}