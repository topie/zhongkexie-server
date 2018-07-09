package com.topie.zhongkexie.security.security;

import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * 工程：os-app 创建人 : ChenGJ 创建时间： 2015/9/4 说明：
 */
public class SecurityUser extends User {
    private Integer id;
    private String loginName;
    private String displayName;
    private String email;
    private String contactPhone;
    private Date lastPasswordReset;
    private Integer userType;
    private String ip;
    public SecurityUser(com.topie.zhongkexie.database.core.model.User user,
                              Collection<GrantedAuthority> userGrantedAuthorities) {
        super(user.getLoginName(), user.getPassword(), user.getEnabled(),
                user.getAccountNonExpired(), user.getCredentialsNonExpired(),
                user.getAccountNonLocked(), userGrantedAuthorities);
        if (user != null) {
            setId(user.getId());
            setLoginName(user.getLoginName());
            setDisplayName(user.getDisplayName());
            setEmail(user.getEmail());
            setContactPhone(user.getContactPhone());
            setLastPasswordReset(user.getLastPasswordReset());
            setUserType(user.getUserType());
            setIp(user.getLastLoginIp());
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public Date getLastPasswordReset() {
        return lastPasswordReset;
    }

    public void setLastPasswordReset(Date lastPasswordReset) {
        this.lastPasswordReset = lastPasswordReset;
    }

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}
    
}
