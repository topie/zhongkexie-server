package com.topie.zhongkexie.database.core.model;

import javax.persistence.*;

@Table(name = "d_dept")
public class Dept {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 职能
     */
    private String duty;

    /**
     * 领域
     */
    private String field;

    /**
     * 父级
     */
    private Integer pid;

    /**
     * 地址
     */
    private String address;

    /**
     * 联系电话
     */
    private String tel;

    /**
     * 联系人
     */
    private String linkman;

    /**
     * 类型 理科 工科 ...
     */
    private String type;

    /**
     * 排序
     */
    private Integer seq;

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
     * 获取编码
     *
     * @return code - 编码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置编码
     *
     * @param code 编码
     */
    public void setCode(String code) {
        this.code = code;
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
     * 获取职能
     *
     * @return duty - 职能
     */
    public String getDuty() {
        return duty;
    }

    /**
     * 设置职能
     *
     * @param duty 职能
     */
    public void setDuty(String duty) {
        this.duty = duty;
    }

    /**
     * 获取领域
     *
     * @return field - 领域
     */
    public String getField() {
        return field;
    }

    /**
     * 设置领域
     *
     * @param field 领域
     */
    public void setField(String field) {
        this.field = field;
    }

    /**
     * 获取父级
     *
     * @return pid - 父级
     */
    public Integer getPid() {
        return pid;
    }

    /**
     * 设置父级
     *
     * @param pid 父级
     */
    public void setPid(Integer pid) {
        this.pid = pid;
    }

    /**
     * 获取地址
     *
     * @return address - 地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置地址
     *
     * @param address 地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取联系电话
     *
     * @return tel - 联系电话
     */
    public String getTel() {
        return tel;
    }

    /**
     * 设置联系电话
     *
     * @param tel 联系电话
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * 获取联系人
     *
     * @return linkman - 联系人
     */
    public String getLinkman() {
        return linkman;
    }

    /**
     * 设置联系人
     *
     * @param linkman 联系人
     */
    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    /**
     * 获取类型 理科 工科 ...
     *
     * @return type - 类型 理科 工科 ...
     */
    public String getType() {
        return type;
    }

    /**
     * 设置类型 理科 工科 ...
     *
     * @param type 类型 理科 工科 ...
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取排序
     *
     * @return seq - 排序
     */
    public Integer getSeq() {
        return seq;
    }

    /**
     * 设置排序
     *
     * @param seq 排序
     */
    public void setSeq(Integer seq) {
        this.seq = seq;
    }
}