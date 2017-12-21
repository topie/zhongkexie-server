package com.topie.zhongkexie.database.core.model;

import javax.persistence.*;

@Table(name = "d_common_statics")
public class CommonStatics {
    @Id
    @Column(name = "s_key")
    private String sKey;

    @Column(name = "s_value")
    private String sValue;

    @Column(name = "s_description")
    private String sDescription;

    /**
     * @return s_key
     */
    public String getsKey() {
        return sKey;
    }

    /**
     * @param sKey
     */
    public void setsKey(String sKey) {
        this.sKey = sKey;
    }

    /**
     * @return s_value
     */
    public String getsValue() {
        return sValue;
    }

    /**
     * @param sValue
     */
    public void setsValue(String sValue) {
        this.sValue = sValue;
    }

    /**
     * @return s_description
     */
    public String getsDescription() {
        return sDescription;
    }

    /**
     * @param sDescription
     */
    public void setsDescription(String sDescription) {
        this.sDescription = sDescription;
    }
}