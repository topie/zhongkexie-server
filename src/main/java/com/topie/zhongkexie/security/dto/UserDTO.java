package com.topie.zhongkexie.security.dto;

import com.topie.zhongkexie.database.core.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by cgj on 2015/10/30.
 */
public class UserDTO extends User {
    @JsonIgnore
    public static User buildSimpleUser(String mobile, String name, String email) {
        User user = new User();
        user.setLoginName(mobile);
        user.setPassword(mobile);
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setContactPhone(mobile);
        user.setEmail(email);
        user.setDisplayName(name);
        user.setCredentialsNonExpired(true);
        return user;
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return "";
    }
}
