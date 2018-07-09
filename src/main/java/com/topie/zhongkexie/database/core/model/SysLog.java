package com.topie.zhongkexie.database.core.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "d_log")
public class SysLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cdate;

    private String ip;

    private String title;

    private String ctype;

    private String cuser;

    private String content;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return cdate
     */
    public String getCdate() {
        return cdate;
    }

    /**
     * @param cdate
     */
    public void setCdate(String cdate) {
        this.cdate = cdate;
    }

    /**
     * @return ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return ctype
     */
    public String getCtype() {
        return ctype;
    }

    /**
     * @param ctype
     */
    public void setCtype(String ctype) {
        this.ctype = ctype;
    }

    /**
     * @return cuser
     */
    public String getCuser() {
        return cuser;
    }

    /**
     * @param cuser
     */
    public void setCuser(String cuser) {
        this.cuser = cuser;
    }

    /**
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

	@Override
	public String toString() {
		return "SysLog [id=" + id + ", cdate=" + cdate + ", ip=" + ip
				+ ", title=" + title + ", ctype=" + ctype + ", cuser=" + cuser
				+ ", content=" + content + "]";
	}
    
}