package com.topie.zhongkexie.system.api;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.topie.zhongkexie.common.tools.cache.RedisCache;
import com.topie.zhongkexie.common.utils.ResponseUtil;
import com.topie.zhongkexie.common.utils.Result;
import com.topie.zhongkexie.database.core.model.User;
import com.topie.zhongkexie.security.constants.SecurityConstant;
import com.topie.zhongkexie.security.dto.FunctionDTO;
import com.topie.zhongkexie.security.exception.AuBzConstant;
import com.topie.zhongkexie.security.exception.AuthBusinessException;
import com.topie.zhongkexie.security.security.SecurityUser;
import com.topie.zhongkexie.security.security.UserCache;
import com.topie.zhongkexie.security.service.UserService;
import com.topie.zhongkexie.security.utils.SecurityUtil;

/**
 * Created by chenguojun on 8/23/16.
 */
@Controller
@RequestMapping("/api")
public class IndexController {

    @Autowired
    UserService userService;

    @Autowired
    RedisCache redisCache;

    @Autowired
    UserCache userCache;

    @RequestMapping(value = "/index/current", method = RequestMethod.GET)
    @ResponseBody
    public Result current() {
        SecurityUser user = SecurityUtil.getCurrentSecurityUser();
        if (user == null || StringUtils.isEmpty(user.getUsername())) {
            throw new AuthBusinessException(AuBzConstant.IS_NOT_LOGIN);
        }
        List<FunctionDTO> functionList = (List<FunctionDTO>) redisCache
                .get(SecurityConstant.FUNCTION_CACHE_PREFIX + user.getUsername());
        if (functionList == null) {
            functionList = userService.findUserFunctionByLoginName(user.getUsername());
            redisCache.set(SecurityConstant.FUNCTION_CACHE_PREFIX + user.getUsername(), functionList);
        }
        Map info = new HashMap();
        info.put("user", user);
        info.put("functionList", functionList);
        return ResponseUtil.success(info);
    }

    @RequestMapping(value = "/index/loadCurrentUser", method = RequestMethod.GET)
    @ResponseBody
    public Result loadCurrentUser() {
        SecurityUser securityUser = SecurityUtil.getCurrentSecurityUser();
        if (securityUser == null || StringUtils.isEmpty(securityUser.getUsername())) {
            throw new AuthBusinessException(AuBzConstant.IS_NOT_LOGIN);
        }
        User user = userService.findUserById(securityUser.getId());
        if(StringUtils.isEmpty(user.getUserCode())){
        	user.setUserCode(UUID.randomUUID().toString().replace("-",""));
        	userService.updateNotNull(user);
        }
        return ResponseUtil.success(user);
    }

    @RequestMapping(value = "/index/updateUser", method = RequestMethod.POST)
    @ResponseBody
    public Result updateUser(User user, @RequestParam(value = "newPassword", required = false) String newPassword) {
        Integer currentId = SecurityUtil.getCurrentUserId();
        if (currentId == null) {
            throw new AuthBusinessException(AuBzConstant.IS_NOT_LOGIN);
        }
        if (user.getId() == null) {
            throw new AuthBusinessException("未检测到用户ID");
        }
        if (currentId.intValue() != user.getId().intValue()) {
            throw new AuthBusinessException("不能修改他人用户信息");
        }
        User u = userService.findUserById(user.getId());
        if (u == null) {
            throw new AuthBusinessException("用户不存在");
        }
        user.setLoginName(u.getLoginName());
        if (userService.findExistUser(user) > 0) {
            throw new AuthBusinessException(AuBzConstant.LOGIN_NAME_EXIST);
        }
        if (!SecurityUtil.matchString(user.getPassword(), u.getPassword())) {
            throw new AuthBusinessException("旧密码不正确");
        }
        if (StringUtils.isNotEmpty(newPassword)) {
            user.setPassword(newPassword);
        }
        user.setLastPasswordReset(new Date());
        userService.updateUser(user);
        return ResponseUtil.success();
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ResponseBody
    public Result authenticationRequest() {
        Integer userId = SecurityUtil.getCurrentUserId();
        userCache.removeUserFromCacheByUserId(userId);
        return ResponseUtil.success();
    }
}
